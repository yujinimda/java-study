# step20 — 동기화 · wait/notify

**책 범위: 13장 「쓰레드(thread)」 4~5절**

step19 에서 쓰레드를 만들어 돌렸습니다. 지금까지는 각자 자기 일만 했죠.
이 단계는 **둘이 같은 물건을 동시에 건드릴 때** 무슨 일이 생기는지를 다룹니다.

이건 자바스크립트를 아무리 오래 해도 만날 수 없는 문제입니다. JS 는 실행 주체가
하나라서 "중간에 끼어들기"가 원천적으로 없거든요. 자바에서는 생깁니다. 그리고
**에러가 안 나고 조용히 값만 틀립니다.** step01 의 오버플로처럼요.

---

## 1. 읽을 것

- 13.4 쓰레드의 동기화
  - **임계 영역(critical section)과 잠금(lock)**
  - `synchronized` 를 이용한 동기화 — **메서드 전체** vs **블럭**
  - 경쟁 상태(race condition)
- 13.5 `wait()` 와 `notify()`
  - `wait()`, `notify()`, `notifyAll()`
  - 기아 현상과 경쟁 상태
  - `Lock` 과 `Condition` (가볍게 — 개념만)
- `volatile` (가볍게 — "값을 캐시하지 말고 매번 읽어라" 정도만)

### 함께 읽을 글

step19 와 마찬가지로 [Inpa Dev](https://inpa.tistory.com/category/Language/Java) 에
13장 관련 글은 없습니다. 책과 아래 문제로 진행합니다.

step05 에서 링크했던
[☕ 그림으로 보는 자바 코드의 메모리 영역 (스택 & 힙)](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B7%B8%EB%A6%BC%EC%9C%BC%EB%A1%9C-%EB%B3%B4%EB%8A%94-%EC%9E%90%EB%B0%94-%EC%BD%94%EB%93%9C%EC%9D%98-%EB%A9%94%EB%AA%A8%EB%A6%AC-%EC%98%81%EC%97%AD%EC%8A%A4%ED%83%9D-%ED%9E%99)
을 다시 보면 도움이 됩니다. **쓰레드마다 스택은 따로지만 힙은 같이 쓴다** — 이 한 줄이
이 장의 모든 문제의 원인입니다.

## 2. 손으로 정리할 것

1. **왜 문제가 생기는가.** `count++` 한 줄이 실제로는 세 단계라는 걸 그리세요.

   ```
   count++  →  ① count 값을 읽는다
               ② 1을 더한다
               ③ 다시 count 에 쓴다
   ```

   쓰레드 A 가 ①을 한 직후 B 가 끼어들면 무슨 일이 생기는지 표로 추적하세요.

   | 시점 | 쓰레드 A | 쓰레드 B | count |
   |---|---|---|---|
   | 1 | 읽기 (0) | | 0 |
   | 2 | | 읽기 (0) | 0 |
   | 3 | +1 → 1 | | 0 |
   | 4 | 쓰기 | | 1 |
   | 5 | | +1 → 1 | 1 |
   | 6 | | 쓰기 | **1** ← 두 번 더했는데 1 |

2. **쓰레드마다 따로인 것 vs 같이 쓰는 것.** step05 의 메모리 그림과 연결하세요.

   ```
   스택  →  쓰레드마다 하나씩 (지역변수는 안전하다)
   힙    →  전부 공유          (객체의 필드가 위험하다)
   ```

3. **`synchronized` 두 가지 형태**를 나란히 쓰세요.

   ```java
   // 메서드 전체
   public synchronized void method() { ... }

   // 블럭만
   public void method() {
       synchronized (this) { ... }     // 잠글 범위를 좁힐 수 있다
   }
   ```

   **블럭 형태가 더 나은 경우**는 언제일까요? (힌트: 잠긴 구간이 길수록 느려집니다)

4. **`wait()` 와 `notify()`** 를 한 줄씩. 그리고 **반드시 지켜야 할 것** 두 가지.

   - `wait()` — 가진 잠금을 ___ 하고 기다린다
   - `notify()` — 기다리는 쓰레드 중 ___ 를 깨운다
   - 둘 다 **`synchronized` 블럭 안**에서만 부를 수 있다
   - `if` 가 아니라 **`while`** 로 조건을 검사해야 한다

5. **`sleep()` 과 `wait()` 의 차이.** 헷갈리기 쉽습니다.

   | | `sleep()` | `wait()` |
   |---|---|---|
   | 잠금을 | 계속 쥐고 있다 | **놓는다** |
   | 깨우는 법 | 시간이 지나면 | `notify()` 로 |
   | 어디 소속 | `Thread` | `Object` |

## 3. 문제

### 문제 1 — 경쟁 상태 ⭐

```java
static int count = 0;

public static void main(String[] args) throws Exception {
    Thread[] ts = new Thread[10];
    for (int i = 0; i < 10; i++) {
        ts[i] = new Thread(() -> {
            for (int j = 0; j < 10000; j++) count++;
        });
    }
    for (Thread t : ts) t.start();
    for (Thread t : ts) t.join();
    System.out.println(count);
}
```

10개 쓰레드가 10000번씩 더하니 **100000** 이 나와야 합니다.

실제로 세 번 돌린 결과입니다.

```
시도 1 — 98767
시도 2 — 99971
시도 3 — 99991
```

**매번 다르고, 전부 100000 보다 작습니다.** 직접 여러 번 돌려보세요.
그리고 손정리 1번의 표로 왜 값이 사라지는지 설명하세요.

> 값이 100000 이 나오는 경우도 있습니다. 운이 좋았을 뿐이고, 고쳐진 게 아닙니다.
> **가끔 맞는 게 항상 틀리는 것보다 위험합니다.** 테스트를 통과해버리니까요.

### 문제 2 — `synchronized` 로 해결

문제 1에서 `count++` 를 아래처럼 바꾸면 항상 100000 이 나옵니다.

```java
static int count = 0;
static synchronized void increase() { count++; }
```

**왜 해결되는지** 한 문장으로 쓰세요. 그리고 이 방식의 **대가**는 무엇일까요?
(힌트: 한 번에 한 쓰레드만 들어갈 수 있다면, 속도는?)

### 문제 3 — 잔액이 음수가 된다 ⭐

step05 에서 만든 `BankAccount` 입니다.

```java
class Account {
    private int balance = 1000;
    void withdraw(int amount) {
        if (balance >= amount) {          // ① 검사
            Thread.sleep(1);              // ② (실제로는 DB 조회 등)
            balance -= amount;            // ③ 출금
        }
    }
}
```

1000원인 계좌에서 쓰레드 10개가 **각각 200원씩** 출금하면, 5번만 성공해야 합니다.
실제 결과입니다.

```
시도 1 — 잔액: -1000
시도 2 — 잔액: -1000
시도 3 — 잔액: -800
```

**잔액이 음수가 됐습니다.** `if (balance >= amount)` 로 분명히 검사했는데도요.
①과 ③ 사이에 다른 쓰레드가 끼어드는 그림을 그려서 설명하세요.

이런 패턴을 **check-then-act** 라고 하고, 동기화가 필요한 대표적인 경우입니다.

### 문제 4 — `synchronized` 위치
```java
class Counter {
    private int a = 0;
    private int b = 0;
    public synchronized void incA() { a++; }
    public synchronized void incB() { b++; }
}
```

`a` 와 `b` 는 서로 상관없는 값인데도, 한 쓰레드가 `incA()` 를 쓰는 동안
다른 쓰레드는 `incB()` 를 **못 씁니다.** 왜일까요?

(힌트: `synchronized` 메서드는 무엇을 잠그나요?)

### 문제 5 — `wait()` 를 잘못 부르면
```java
Object lock = new Object();
lock.wait();
```

실행하면 이렇게 죽습니다.

```
java.lang.IllegalMonitorStateException: current thread is not owner
```

"owner 가 아니다" 는 게 무슨 뜻일까요? 고치려면 어떻게 해야 하나요?

### 문제 6 — `if` 가 아니라 `while`
```java
// A
synchronized (lock) {
    if (box.isEmpty()) lock.wait();
    String item = box.remove(0);
}

// B
synchronized (lock) {
    while (box.isEmpty()) lock.wait();
    String item = box.remove(0);
}
```

교과서는 항상 **B** 를 씁니다. A 가 위험한 이유를 생각해보세요.

(힌트: `wait()` 에서 깨어난 시점에 조건이 **아직도** 참이라는 보장이 있나요?
`notifyAll()` 로 여럿이 한꺼번에 깨어나면 어떻게 되나요?)

### 문제 7 — `sleep` vs `wait`
```java
synchronized (lock) {
    Thread.sleep(1000);    // ①
}
synchronized (lock) {
    lock.wait(1000);       // ②
}
```

둘 다 1초를 기다리지만 결정적인 차이가 있습니다. **다른 쓰레드가 그 1초 동안
`lock` 을 쓸 수 있는 쪽**은 어디인가요?

### 문제 8 — 지역변수는 안전한가
```java
class Job implements Runnable {
    public void run() {
        int localCount = 0;              // ①
        for (int i = 0; i < 10000; i++) localCount++;
        System.out.println(localCount);
    }
}
```

쓰레드 10개가 이 `run()` 을 동시에 돌려도 **항상 10000 이 찍힙니다.**
문제 1과 뭐가 다른가요? 손정리 2번과 연결해서 답하세요.

## 4. 손코딩 과제

`src/step20/` 에 씁니다.

### 과제 1 — `RaceCondition.java` ⭐

문제 1을 직접 재현하세요.

- 동기화 **없는** 카운터와 `synchronized` 카운터를 **한 프로그램에서 같이** 돌리세요
- 결과를 나란히 출력하고, **5번 이상 실행**해서 값이 어떻게 달라지는지 기록하세요

```
시도 1 — 동기화 없음: 98767 / synchronized: 100000
시도 2 — 동기화 없음: 99971 / synchronized: 100000
```

> 위 숫자는 예시일 뿐이고, 실행할 때마다 다릅니다. 왼쪽은 매번 다른 값이,
> 오른쪽은 항상 100000 이 나오면 성공입니다.

### 과제 2 — `SyncBlock.java`

과제 1의 `synchronized` 메서드를 **`synchronized` 블럭**으로 바꿔보세요.

```java
static final Object lock = new Object();

static void increase() {
    // 여기는 잠그지 않아도 되는 코드
    synchronized (lock) {
        count++;           // 여기만 잠근다
    }
}
```

두 방식의 결과가 같은지 확인하고, **잠그는 범위를 좁히는 게 왜 좋은지** 주석으로 쓰세요.

### 과제 3 — `SafeAccount.java` ⭐

문제 3의 계좌를 고치세요.

1. 먼저 **고치지 않은 상태**로 돌려서 잔액이 음수가 되는 걸 확인
2. `synchronized` 를 붙여서 잔액이 **0 밑으로 안 내려가는지** 확인

```
[동기화 전] 최종 잔액: -1000   ← 10번 다 출금됨
[동기화 후] 최종 잔액: 0       ← 5번만 성공
```

step08 과제 3(캡슐화)에서 만든 `Account` 를 이어서 쓰면 좋습니다.
**캡슐화만으로는 이 문제를 못 막는다**는 것도 확인하세요.

### 과제 4 — `ProducerConsumer.java`

`wait()` / `notify()` 의 대표 예제입니다.

```
생산자  →  [ 박스 (최대 3개) ]  →  소비자
```

- 생산자는 물건을 만들어 박스에 넣습니다. **박스가 꽉 차면 기다립니다.**
- 소비자는 박스에서 꺼내 씁니다. **박스가 비면 기다립니다.**

실행 예시입니다.

```
  생산: 물건1 (박스 1)
  소비: 물건1 (박스 0)
  생산: 물건2 (박스 1)
  생산: 물건3 (박스 2)
  소비: 물건2 (박스 1)
  ...
```

> 순서는 매번 다릅니다. **박스 개수가 0~3 범위를 벗어나지 않는 것**이 성공 기준입니다.

조건 검사는 반드시 `while` 로 하세요 (문제 6).

### 과제 5 — `DeadlockDemo.java` (선택)

잠금을 잘못 쓰면 **둘 다 영원히 멈추는** 교착 상태(deadlock)가 생깁니다.

```java
// 쓰레드 A: lock1 잡고 → lock2 를 원함
synchronized (lock1) { Thread.sleep(100); synchronized (lock2) { ... } }

// 쓰레드 B: lock2 잡고 → lock1 을 원함
synchronized (lock2) { Thread.sleep(100); synchronized (lock1) { ... } }
```

직접 만들어서 프로그램이 안 끝나는 걸 확인하세요. (`Ctrl+C` 로 종료)

그다음 **두 쓰레드가 같은 순서로 잠그도록** 고쳐서 해결하세요.

> 동기화는 공짜가 아닙니다. 잘못 쓰면 느려지고, 더 잘못 쓰면 멈춥니다.

## 5. 자가 점검

- [ ] `count++` 가 원자적이지 않은 이유를 3단계로 설명할 수 있다
- [ ] 경쟁 상태를 직접 재현해봤다
- [ ] 쓰레드마다 스택은 따로, 힙은 공유라는 걸 안다
- [ ] 지역변수가 안전한 이유를 설명할 수 있다
- [ ] `synchronized` 메서드와 블럭을 둘 다 쓸 수 있다
- [ ] check-then-act 가 왜 위험한지 안다
- [ ] `wait()` 를 `synchronized` 밖에서 부르면 안 되는 이유를 안다
- [ ] `sleep()` 과 `wait()` 의 차이(잠금을 놓는가)를 안다

---

다음은 step21(14장 1~2절, 람다식)입니다. step09 의 익명 클래스가
**한 줄로 줄어드는** 문법을 배웁니다. 자바스크립트 화살표 함수를 아신다면 반가울 겁니다.
