# step10 — 예외처리

**책 범위: 8장 「예외처리(exception handling)」 전체**

지금까지 프로그램이 죽은 적이 여러 번 있었습니다.

| 언제 | 무엇 |
|---|---|
| step02 문제 7 | `10 / 0` → `ArithmeticException` |
| step03 과제 7 | `NumberGame` 에 숫자 아닌 값 입력 → `InputMismatchException` |
| step04 문제 2 | `arr[5]` (길이 5인 배열) → `ArrayIndexOutOfBoundsException` |
| step05 문제 4 | `null` 인 참조변수의 멤버 접근 → `NullPointerException` |
| step08 문제 4 | 잘못된 다운캐스팅 → `ClassCastException` |

그때마다 "8장에서 배웁니다" 하고 넘어갔습니다. **여기가 그 8장입니다.**
지금까지 만난 예외들이 한 체계로 정리되고, 프로그램이 죽는 대신 **계속 돌게 만드는 법**을 배웁니다.

---

## 1. 읽을 것

- 8.1 프로그램 오류 (컴파일 에러 / 런타임 에러 / 논리적 에러, **Error 와 Exception**)
- 8.2 예외처리하기 — `try-catch`
  - `try-catch` 문에서의 흐름, 예외의 발생과 catch 블럭
  - `printStackTrace()` 와 `getMessage()`
  - **멀티 catch 블럭** (`|`)
  - 예외 발생시키기 — **`throw`**
  - **checked 예외 / unchecked 예외**
  - 메서드에 예외 선언하기 — **`throws`**
  - `finally` 블럭, **try-with-resources**
  - **사용자정의 예외** 만들기
  - 예외 되던지기(re-throwing), **연결된 예외(chained exception)**

### 함께 읽을 글 ([Inpa Dev](https://inpa.tistory.com/category/Language/Java))

- [☕ 자바 에러(Error) 와 예외 클래스(Exception) 이해하기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%97%90%EB%9F%ACError-%EC%99%80-%EC%98%88%EC%99%B8-%ED%81%B4%EB%9E%98%EC%8A%A4Exception-%F0%9F%92%AF-%EC%B4%9D%EC%A0%95%EB%A6%AC)
  — **제일 먼저 읽으면 좋은 글.** 아래 「손으로 정리할 것」 1번의 계층도가 여기 있습니다.
- [☕ 자바 예외 처리(try catch) 문법 & 응용 정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%98%88%EC%99%B8-%EC%B2%98%EB%A6%ACException-%EB%AC%B8%EB%B2%95-%EC%9D%91%EC%9A%A9-%EC%A0%95%EB%A6%AC)
  — 문법 전반. 문제 1~4와 짝입니다.
- [☕ 자바 예외 던지기(throw) & 예외 연결(Chained Exception)](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%98%88%EC%99%B8-%EB%8D%98%EC%A7%80%EA%B8%B0throw-%EC%98%88%EC%99%B8-%EC%97%B0%EA%B2%B0Chained-Exception)
  — `throw` / `throws` 구분과 과제 4의 배경.
- [☕ 자바 Try With Resource 예외 처리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%98%88%EC%99%B8-%EC%B2%98%EB%A6%AC-Try-With-Resource-%EB%AC%B8%EB%B2%95)
  — `finally` 로 자원을 닫던 옛날 방식과 비교해서 읽으면 좋습니다. 문제 7과 짝입니다.
- [☕ Exception Handling - 예외를 처리하는 3가지 기법](https://inpa.tistory.com/entry/JAVA-%E2%98%95-Exception-Handling-%EC%98%88%EC%99%B8%EB%A5%BC-%EC%B2%98%EB%A6%AC%ED%95%98%EB%8A%94-3%EA%B0%80%EC%A7%80-%EA%B8%B0%EB%B2%95)
  — "잡을 것인가, 넘길 것인가, 바꿔 던질 것인가". 문법을 다 배운 뒤에 읽으면 판단 기준이 생깁니다.
- [☕ (Un)Checked Exception과 트랜잭션의 오해와 진실](https://inpa.tistory.com/entry/JAVA-%E2%98%95-UnChecked-Exception%EA%B3%BC-%ED%8A%B8%EB%9E%9C%EC%9E%AD%EC%85%98%EC%9D%98-%EC%98%A4%ED%95%B4%EC%99%80-%EC%A7%84%EC%8B%A4)
  — 스프링 실무 이야기라 지금은 어렵습니다. checked/unchecked 구분이 실무에서 왜 중요한지만 훑어도 됩니다.

## 2. 손으로 정리할 것

1. **예외 계층도**를 직접 그리세요. 지금까지 만난 예외들을 제자리에 넣어보세요.

   ```
                        Object
                          │
                       Throwable
                     ┌────┴────┐
                  Error      Exception
              (복구 불가)    ┌───┴────────────┐
           OutOfMemoryError │          RuntimeException
           StackOverflowError│            (unchecked)
                            │          ┌──┴──┬──────┬─────────┐
                     IOException   Arithmetic  NullPointer  ClassCast
                     (checked)     Exception   Exception    Exception
                                            ArrayIndexOutOfBounds
   ```

   `RuntimeException` **아래에 있으면 unchecked**, 그 밖의 `Exception` 은 checked 입니다.

2. **checked vs unchecked 표.** 이 구분이 8장의 절반입니다.

   | | checked 예외 | unchecked 예외 |
   |---|---|---|
   | 부모 | `Exception` (RuntimeException 제외) | `RuntimeException` |
   | 처리 강제 | **컴파일러가 강제한다** | 강제하지 않는다 |
   | 안 잡으면 | **컴파일 에러** | 컴파일은 되고 실행 중에 죽는다 |
   | 성격 | 외부 요인 (파일 없음, 네트워크 끊김) | 내 코드의 버그 (null, 인덱스 초과) |
   | 예 | `IOException`, `ClassNotFoundException` | `NullPointerException`, `ArithmeticException` |

3. **`throw` 와 `throws` 의 차이**를 한 줄씩. 글자 하나 차이라 계속 헷갈립니다.

   ```java
   void m() throws IOException {        // throws — "나 이런 예외 낼 수 있음" 선언
       throw new IOException("실패");    // throw  — 실제로 지금 던진다
   }
   ```

4. **`try-catch-finally` 실행 흐름**을 두 경우로 나눠 화살표로 그리세요.
   - 예외가 **안 난** 경우: try 전부 → ? → ?
   - 예외가 **난** 경우: try 도중 중단 → ? → ?

   그리고 **`finally` 는 어느 경우에도 실행된다**는 것.

5. **예외를 잡았을 때 하면 안 되는 것** 한 줄.
   ```java
   catch (Exception e) { }   // 아무것도 안 함 — 문제가 조용히 사라진다
   ```
   이걸 "예외 삼키기(swallowing)" 라고 부릅니다. 왜 위험한지 한 문장으로 쓰세요.

## 3. 문제

### 문제 1 — try-catch-finally 흐름
```java
System.out.println("시작");
try {
    int[] arr = new int[3];
    arr[5] = 10;
    System.out.println("try 끝");
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("catch: " + e.getMessage());
} finally {
    System.out.println("finally");
}
System.out.println("끝");
```
출력을 **순서대로** 적으세요. `"try 끝"` 이 찍히는지가 관문입니다.

### 문제 2 — getMessage() vs toString()
```java
try { int c = 10 / 0; }
catch (ArithmeticException e) {
    System.out.println(e.getMessage());
    System.out.println(e);
}
```
두 줄의 차이를 적으세요.

### 문제 3 — catch 순서 ⭐
```java
try { int c = 10 / 0; }
catch (Exception e) { }
catch (ArithmeticException e) { }
```
컴파일 에러입니다. 에러 메시지는 이렇습니다.
```
error: exception ArithmeticException has already been caught
```
**왜인지** 설명하고, 고치는 방법을 쓰세요. (힌트: step08의 다형성 — `ArithmeticException` 은 `Exception` 이기도 합니다)

### 문제 4 — checked 예외
```java
static void risky() throws Exception { }

public static void main(String[] args) {
    risky();
}
```
컴파일 에러입니다.
```
error: unreported exception Exception; must be caught or declared to be thrown
```
고치는 방법 **2가지**를 쓰세요. (메시지에 답이 들어 있습니다)

### 문제 5 — unchecked 는 왜 안 막히나
```java
static void risky2() { int c = 10 / 0; }

public static void main(String[] args) {
    risky2();
}
```
문제 4와 달리 **컴파일은 통과하고 실행할 때 죽습니다.** 차이가 무엇인가요?

### 문제 6 — finally 와 return ⭐
```java
static int test() {
    try { return 1; }
    finally { System.out.println("finally 실행"); }
}
static int test2() {
    try { return 1; }
    finally { return 2; }
}
// main 에서
System.out.println("test()  = " + test());
System.out.println("test2() = " + test2());
```
출력을 순서대로 적으세요. `test2()` 의 결과가 관문입니다.

> `finally` 에 `return` 을 쓰면 `try` 의 `return` 을 덮어씁니다. 그래서 실무에서는
> `finally` 안에 `return` 을 쓰지 않습니다.

### 문제 7 — try-with-resources
```java
class MyResource implements AutoCloseable {
    private String name;
    MyResource(String name) { this.name = name; System.out.println(name + " 열림"); }
    void use() { System.out.println(name + " 사용"); }
    public void close() { System.out.println(name + " 닫힘"); }
}

try (MyResource a = new MyResource("A"); MyResource b = new MyResource("B")) {
    a.use(); b.use();
    throw new RuntimeException("문제 발생");
} catch (RuntimeException e) {
    System.out.println("catch: " + e.getMessage());
}
```
출력을 순서대로 적으세요. **닫히는 순서**가 관문입니다.

### 문제 8 — 멀티 catch
```java
for (int i = 0; i < 2; i++) {
    try {
        if (i == 0) { int c = 1 / 0; }
        else { int[] a = new int[1]; a[3] = 1; }
    } catch (ArithmeticException | ArrayIndexOutOfBoundsException e) {
        System.out.println(e.getClass().getSimpleName() + " → " + e.getMessage());
    }
}
```
그리고 아래는 왜 에러일까요?
```java
catch (Exception | ArithmeticException e) { }
```

### 문제 9 — 판단 문제
아래 상황에서 **예외를 잡을지(catch), 넘길지(throws), 아니면 애초에 예외를 안 쓸지** 고르고 이유를 쓰세요.

1. 사용자가 숫자 대신 글자를 입력했다
2. 배열 인덱스를 잘못 계산해서 범위를 넘었다
3. 읽으려는 설정 파일이 없다
4. 출금액이 잔액보다 크다

## 4. 손코딩 과제

`src/step10/` 에 씁니다.

### 과제 1 — `ExceptionZoo.java`

**지금까지 만난 예외 5종을 일부러 발생시키고 전부 잡으세요.**

```java
public class ExceptionZoo {
    public static void main(String[] args) {
        // 1. ArithmeticException      (step02)
        // 2. ArrayIndexOutOfBoundsException (step04)
        // 3. NullPointerException     (step05)
        // 4. ClassCastException       (step08)
        // 5. NumberFormatException    (Integer.parseInt("abc"))
    }
}
```

각각 `try-catch` 로 감싸고, 잡은 뒤 `e.getClass().getSimpleName()` 과 `e.getMessage()` 를
출력하세요. **프로그램이 죽지 않고 5개를 전부 찍고 정상 종료**하면 성공입니다.

> 지금까지는 예외 하나가 나면 거기서 프로그램이 끝났습니다. 잡으면 계속 돕니다.
> 그 차이를 눈으로 확인하는 게 이 과제입니다.

### 과제 2 — `SafeDivide.java`

```java
static int divide(int a, int b)
```

`b` 가 0이면 예외를 잡아 메시지를 찍고 `0` 을 반환하도록 만드세요.
`divide(10, 2)`, `divide(10, 0)` 을 각각 호출해 **두 번째에도 프로그램이 안 죽는지** 확인하세요.

그다음 `finally` 를 추가해서 호출할 때마다 `"계산 시도 완료"` 가 찍히게 하세요.

### 과제 3 — `NumberGame.java` 개선 (step03 과제 7 이어서)

step03에서 만든 숫자 맞히기 게임은 숫자가 아닌 값을 넣으면 이렇게 죽었습니다.

```
Exception in thread "main" java.util.InputMismatchException
```

이제 고칩니다.

- `sc.nextInt()` 를 `try` 로 감싸고 `InputMismatchException` 을 잡으세요
- 잡으면 `"숫자만 입력하세요"` 를 찍고 **다시 입력받게** 하세요
- `sc.next()` 로 잘못된 입력을 버려야 무한루프에 빠지지 않습니다 (직접 겪어보세요)
- `import java.util.InputMismatchException;` 필요

```
입력> abc
숫자만 입력하세요
입력> 50
더 작게
입력> 42
정답!
```

### 과제 4 — `BankAccount.java` 개선 (step08 과제 3 이어서) ⭐

**사용자정의 예외**를 만드는 과제입니다.

```java
// InsufficientBalanceException.java
class InsufficientBalanceException extends Exception {
    private int shortage;                       // 얼마나 부족한지
    public InsufficientBalanceException(String msg, int shortage) {
        super(msg);                             // 부모(Exception)에게 메시지 전달
        this.shortage = shortage;
    }
    public int getShortage() { return shortage; }
}
```

`withdraw()` 를 이렇게 바꾸세요.

```java
void withdraw(int amount) throws InsufficientBalanceException {
    if (amount > balance) {
        throw new InsufficientBalanceException("잔액 부족", amount - balance);
    }
    balance -= amount;
}
```

`BankAccountTest` 에서 호출하면 **컴파일러가 처리를 강제**합니다(checked 예외라서).
`try-catch` 로 잡아 부족액을 출력하세요.

```
잡음: 잔액 부족
부족액: 4000
```

> step08에서는 잔액 부족일 때 그냥 `System.out.println("잔액 부족")` 을 찍고 넘어갔습니다.
> 그러면 **호출한 쪽은 성공했는지 실패했는지 알 수 없습니다.** 예외를 던지면 호출한 쪽이
> 반드시 알게 됩니다. 이게 예외를 쓰는 이유입니다.

### 과제 5 — `Rethrow.java` (선택)

**연결된 예외(chained exception)** 실습입니다.

```java
static void low() {
    throw new ArithmeticException("낮은 층에서 발생");
}
static void high() {
    try {
        low();
    } catch (ArithmeticException e) {
        throw new RuntimeException("높은 층에서 감쌈", e);   // 원인을 같이 넘긴다
    }
}
```

`main` 에서 `high()` 를 호출하고 잡아서, `e.getMessage()` 와 `e.getCause().getMessage()` 를
각각 출력하세요. **원래 원인이 보존되는 것**을 확인하는 게 목적입니다.

## 5. 자가 점검

- [ ] `Error` 와 `Exception` 의 차이를 말할 수 있다
- [ ] checked 예외와 unchecked 예외를 구분하고, 각각 어떻게 되는지 안다
- [ ] `catch` 블럭을 여러 개 쓸 때 **자식 예외를 먼저** 써야 하는 이유를 안다
- [ ] `throw` 와 `throws` 를 구분해서 쓸 수 있다
- [ ] `finally` 가 언제 실행되는지 안다
- [ ] `try-with-resources` 가 무엇을 자동으로 해주는지 안다
- [ ] 사용자정의 예외를 만들고 던질 수 있다
- [ ] 빈 `catch` 블럭이 왜 위험한지 설명할 수 있다

---

다음은 step11(9장 1~2절) — `Object` 와 `String` 클래스입니다.
step07에서 `toString()` 과 `equals()` 를 오버라이딩했던 것의 배경이 거기서 나옵니다.
