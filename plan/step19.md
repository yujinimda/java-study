# step19 — 쓰레드 기본

**책 범위: 13장 「쓰레드(thread)」 1~3절**

지금까지 만든 프로그램은 전부 **위에서 아래로 한 줄씩** 실행됐습니다. 쓰레드를 쓰면
**여러 줄기가 동시에** 돕니다.

프론트엔드를 하셨다면 여기서 한 번 헷갈립니다. 자바스크립트는 **싱글 쓰레드 + 이벤트 루프**라
`async/await` 로 "기다리는 동안 다른 일 하기"를 하지만, 실행 주체는 하나입니다. 자바는
**진짜로 여러 쓰레드가 동시에** 돕니다. 그래서 JS 에는 없는 문제가 생깁니다 —
**둘이 같은 데이터를 동시에 건드리면 값이 깨집니다.** 그게 step20 의 주제입니다.

이 단계는 쓰레드를 **만들고 돌리는 것**까지만 합니다.

---

## 1. 읽을 것

- 13.1 프로세스와 쓰레드
  - 멀티태스킹과 멀티쓰레딩, 멀티쓰레딩의 장단점
- 13.2 쓰레드의 구현과 실행
  - **`Thread` 상속 vs `Runnable` 구현** (두 가지 방법)
  - **`start()` 와 `run()` 의 차이** ← 이 장의 첫 번째 함정
  - 싱글쓰레드와 멀티쓰레드
- 13.3 쓰레드의 우선순위 / 쓰레드 그룹 / 데몬 쓰레드
  - **쓰레드의 상태** (NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED)
  - 쓰레드의 실행제어 — `sleep()`, `interrupt()`, `join()`

### 함께 읽을 글

[Inpa Dev](https://inpa.tistory.com/category/Language/Java) 블로그에는 13장(쓰레드)만
다루는 글이 없습니다. step19·20 은 책과 아래 문제로 진행합니다.

여유가 있으면 step09 에서 링크한
[☕ 익명 클래스(Anonymous Class) 사용법 총정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%9D%B5%EB%AA%85-%ED%81%B4%EB%9E%98%EC%8A%A4Anonymous-Class-%EC%82%AC%EC%9A%A9%EB%B2%95-%EB%A7%88%EC%8A%A4%ED%84%B0%ED%95%98%EA%B8%B0)
를 다시 보면 좋습니다. 쓰레드를 만들 때 `new Runnable() { ... }` 형태를 계속 쓰게 되고,
step21 에서 그게 람다로 짧아집니다.

전체 글 목록: [`reference/reading-list.md`](../reference/reading-list.md)

## 2. 손으로 정리할 것

1. **프로세스와 쓰레드**를 한 줄씩. (힌트: 프로세스는 ___ , 쓰레드는 그 안에서 ___)

   ```
   프로세스  =  실행 중인 프로그램 하나 (자기 메모리를 따로 가짐)
   쓰레드    =  그 프로세스 안에서 도는 실행 줄기 (메모리를 같이 씀)
                                      └─ 여기가 step20 문제의 씨앗
   ```

2. **쓰레드 만드는 두 가지 방법**을 나란히 쓰세요.

   ```java
   // ① Thread 를 상속
   class MyThread extends Thread {
       public void run() { ... }
   }
   new MyThread().start();

   // ② Runnable 을 구현
   class MyJob implements Runnable {
       public void run() { ... }
   }
   new Thread(new MyJob()).start();
   ```

   **②를 더 많이 씁니다.** 왜 그럴까요? (힌트: step07 — 자바는 상속이 하나뿐입니다)

3. **`start()` 와 `run()` 의 차이**를 한 문장씩. 이게 이 장의 핵심입니다.

   ```
   start()  →  ___________________
   run()    →  ___________________
   ```

4. **쓰레드의 상태 6개**를 적고, 화살표로 이어보세요.

   ```
   NEW  ──start()──▶  RUNNABLE  ──끝──▶  TERMINATED
                         │  ▲
            sleep/wait/  │  │  시간이 다 되거나 notify
            join 등      ▼  │
                  WAITING / TIMED_WAITING / BLOCKED
   ```

5. **`sleep` · `join` · `interrupt`** 를 한 줄씩.
   - `sleep(ms)` — 지정한 시간만큼 ___
   - `join()` — 그 쓰레드가 ___ 까지 기다린다
   - `interrupt()` — 자고 있는 쓰레드를 ___

## 3. 문제

### 문제 1 — `start()` vs `run()` ⭐
```java
class MyThread extends Thread {
    public void run() {
        System.out.println("실행한 쓰레드: " + Thread.currentThread().getName());
    }
}

public static void main(String[] args) {
    System.out.println("main 쓰레드: " + Thread.currentThread().getName());
    new MyThread().start();
    new MyThread().run();
}
```

실제 출력입니다.

```
main 쓰레드: main
실행한 쓰레드: Thread-0
실행한 쓰레드: main
```

**`run()` 을 직접 부르면 새 쓰레드가 안 생깁니다.** 그냥 평범한 메서드 호출이라
`main` 쓰레드에서 실행됩니다. 왜 그런지 설명하세요.

### 문제 2 — `start()` 를 두 번
```java
MyThread t = new MyThread();
t.start();
t.join();
t.start();
```

마지막 줄에서 예외가 납니다.

```
Exception in thread "main" java.lang.IllegalThreadStateException
```

**이미 끝난 쓰레드인데도** 다시 시작이 안 됩니다. 문제 1의 상태 그림으로 설명하세요.
(힌트: `TERMINATED` 에서 `NEW` 로 돌아가는 화살표가 있나요?)

### 문제 3 — 실행 순서
```java
for (int i = 1; i <= 3; i++) {
    final int n = i;
    new Thread(() -> System.out.println("쓰레드 " + n)).start();
}
System.out.println("main 끝");
```

출력 순서를 예상해보세요. 그리고 **여러 번 실행**해보세요.

이 문제의 답은 **"매번 다르다"** 입니다. 순서를 정하는 건 우리가 아니라 OS 의
스케줄러라서, 같은 코드가 실행할 때마다 다른 순서로 찍힙니다. `main 끝` 이 제일
먼저 나오는 경우도 흔합니다.

### 문제 4 — `join()`
```java
Thread t = new Thread(() -> {
    for (int i = 0; i < 3; i++) System.out.println("작업 중...");
});
t.start();
System.out.println("작업 끝!");
```

`작업 끝!` 이 먼저 찍힐 수 있습니다. 항상 작업이 다 끝난 뒤에 찍히게 하려면
어디에 무엇을 넣어야 하나요?

### 문제 5 — 쓰레드 상태
```java
Thread t = new Thread(() -> {
    try { Thread.sleep(200); } catch (InterruptedException e) {}
});
System.out.println(t.getState());   // ①
t.start();
Thread.sleep(50);
System.out.println(t.getState());   // ②
t.join();
System.out.println(t.getState());   // ③
```

①②③ 에 무엇이 찍힐까요? ②가 `RUNNABLE` 이 아니라는 게 관문입니다.

### 문제 6 — `interrupt()`
```java
Thread t = new Thread(() -> {
    try {
        Thread.sleep(5000);
        System.out.println("5초 다 잤다");
    } catch (InterruptedException e) {
        System.out.println("깨어남: " + e);
    }
});
t.start();
Thread.sleep(50);
t.interrupt();
```

5초를 기다리지 않고 바로 끝납니다. 출력되는 예외 메시지는 무엇일까요?
그리고 `sleep()` 에 **왜 `try-catch` 가 강제**되는지 step10 과 연결해서 설명하세요.

### 문제 7 — 데몬 쓰레드
```java
Thread daemon = new Thread(() -> {
    while (true) {
        try { Thread.sleep(100); System.out.println("데몬 동작중"); }
        catch (InterruptedException e) { return; }
    }
});
daemon.setDaemon(true);
daemon.start();
Thread.sleep(250);
System.out.println("main 끝");
```

`while(true)` 인데 프로그램이 끝납니다. 왜일까요?
`setDaemon(true)` 를 지우면 어떻게 되나요?

### 문제 8 — 두 방식의 차이
```java
① class A extends Thread implements Runnable { }
② class B extends Animal implements Runnable { }
③ class C extends Animal extends Thread { }
```

셋 중 **불가능한 것**을 고르고, 그게 `Runnable` 을 더 많이 쓰는 이유와
어떻게 연결되는지 설명하세요.

## 4. 손코딩 과제

`src/step19/` 에 씁니다.

### 과제 1 — `TwoWays.java`

같은 일을 하는 쓰레드를 **두 가지 방법으로** 만드세요.

- `CounterThread extends Thread` — 1부터 5까지 출력
- `CounterJob implements Runnable` — 똑같이 1부터 5까지 출력

`TwoWays` 에서 둘 다 `start()` 하고, 각 쓰레드 이름(`Thread.currentThread().getName()`)도
같이 찍으세요.

```
[Thread-0] 1
[Thread-1] 1
[Thread-0] 2
...
```

> 순서가 섞여서 나오는 게 정상입니다. 섞이지 않았다면 여러 번 더 돌려보세요.

### 과제 2 — `StartVsRun.java`

문제 1을 직접 확인하는 과제입니다.

- `start()` 로 호출한 경우와 `run()` 으로 호출한 경우를 나란히 실행
- 각각 `Thread.currentThread().getName()` 을 출력해서 **어느 쓰레드에서 도는지** 확인
- 결과를 주석으로 한 줄 정리

### 과제 3 — `Shuffle.java` ⭐

**실행할 때마다 결과가 다른 것**을 눈으로 확인하는 과제입니다.

쓰레드 5개를 만들어 각각 자기 번호를 출력하게 하고, `./run` 을 **10번** 돌려보세요.

```bash
./run step19/Shuffle.java
```

몇 번째 실행에서 순서가 달라졌는지 `notes/errors.md` 나 주석에 적어두세요.

> 지금까지 배운 코드는 전부 "같은 입력 → 같은 출력" 이었습니다. 쓰레드부터는
> **그게 깨집니다.** 이 감각을 갖는 게 과제의 목적입니다.

### 과제 4 — `JoinOrder.java`

과제 3 과 같은 코드에 `join()` 을 넣어 **순서를 보장**하세요.

```java
Thread[] ts = new Thread[5];
// ... 만들고
for (Thread t : ts) t.start();
for (Thread t : ts) t.join();    // 여기가 핵심
System.out.println("전부 끝났습니다");
```

그리고 아래 두 가지를 비교해보세요.

```java
// A: 전부 start 한 뒤 전부 join
for (Thread t : ts) t.start();
for (Thread t : ts) t.join();

// B: 하나 start 하고 바로 join
for (Thread t : ts) { t.start(); t.join(); }
```

**B 는 사실상 쓰레드를 쓰는 의미가 없습니다.** 왜인지 주석으로 쓰세요.

### 과제 5 — `Timer.java`

`sleep()` 으로 1초씩 세는 카운트다운을 만드세요.

```
5
4
3
2
1
끝!
```

`Thread.sleep(1000)` 은 `InterruptedException` 을 던지므로 `try-catch` 가 필요합니다
(step10 의 checked 예외입니다).

### 과제 6 — `DownloadSimulator.java` (선택)

멀티쓰레드의 이점을 체감하는 과제입니다.

- "파일 3개를 다운로드" 한다고 가정하고, 각각 `sleep(1000)` 으로 1초 걸리게 만드세요
- **순차 실행**과 **쓰레드 3개 동시 실행**의 총 소요 시간을 비교하세요

```java
long start = System.currentTimeMillis();
// ... 작업
System.out.println("걸린 시간: " + (System.currentTimeMillis() - start) + "ms");
```

순차는 약 3000ms, 동시는 약 1000ms 가 나옵니다.

## 5. 자가 점검

- [ ] 프로세스와 쓰레드의 차이를 말할 수 있다
- [ ] 쓰레드를 만드는 두 가지 방법을 쓸 수 있다
- [ ] `Runnable` 을 더 많이 쓰는 이유를 상속 규칙과 연결해 설명할 수 있다
- [ ] `start()` 와 `run()` 의 차이를 출력으로 보여줄 수 있다
- [ ] `start()` 를 두 번 부르면 왜 예외인지 안다
- [ ] 쓰레드 실행 순서가 보장되지 않는다는 걸 직접 확인했다
- [ ] `join()` 으로 순서를 보장할 수 있다
- [ ] 데몬 쓰레드가 언제 끝나는지 안다

---

다음은 step20(13장 4~5절, 동기화)입니다. 쓰레드 여러 개가 **같은 데이터를 동시에
건드릴 때** 무슨 일이 생기는지 직접 보게 됩니다.
