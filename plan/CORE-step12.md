# step12 — 래퍼 클래스 · 유용한 클래스

**책 범위: 9장 「java.lang패키지와 유용한 클래스」 3~5절**

step01 문제 4에서 이걸 만났습니다.

```java
System.out.println(0.1 + 0.2);        // 0.30000000000000004
System.out.println(0.1 + 0.2 == 0.3); // false
```

그때 "돈 계산에 `double` 을 쓰지 말고 `BigDecimal` 을 쓴다"고만 적어뒀습니다.
**이 단계가 그 해결편입니다.**

그리고 step02 문제 4에서 이것도 있었습니다.

```java
System.out.println(a > b ? "크다" : 0);   // 컴파일이 된다
```

`String` 과 `int` 를 섞었는데 통과한 이유가 **오토박싱**이고, 그것도 여기서 나옵니다.

---

## 1. 읽을 것

- 9.3 유용한 클래스
  - **Math 클래스** — 주요 메서드, `round()` 의 동작
  - **래퍼(wrapper) 클래스** — Number 클래스, 문자열을 숫자로 변환하기,
    **오토박싱 & 언박싱**
- 9.4 **BigInteger 클래스** (생성, 변환, 주요 메서드)
- 9.5 **BigDecimal 클래스** (생성, 변환, 주요 메서드, 반올림 모드)

### 함께 읽을 글 ([Inpa Dev](https://inpa.tistory.com/category/Language/Java))

- [☕ 자바 Wrapper 클래스와 Boxing & UnBoxing 총정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-wrapper-class-Boxing-UnBoxing)
  — **이 단계의 중심 글.** 문제 1~4와 짝입니다.
- [☕ BigInteger & BigDecimal 사용법 정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-BigInteger-BigDecimal-%EC%9E%90%EB%A3%8C%ED%98%95-%EC%82%AC%EC%9A%A9%EB%B2%95-%EC%B4%9D%EC%A0%95%EB%A6%AC)
  — **과제 3의 배경.**
- [☕ 실수 표현(부동 소수점) 원리 한눈에 이해하기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%8B%A4%EC%88%98-%ED%91%9C%ED%98%84%EB%B6%80%EB%8F%99-%EC%86%8C%EC%88%98%EC%A0%90-%EC%9B%90%EB%A6%AC-%ED%95%9C%EB%88%88%EC%97%90-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0)
  — step01에서 읽었던 글. **"왜 오차가 생기나"를 이미 봤다면 이번엔 뒷부분(해결 방법)** 을 보면 됩니다.
- [☕ 개발자들을 괴롭히는 자바 NULL 파헤치기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B0%9C%EB%B0%9C%EC%9E%90%EB%93%A4%EC%9D%84-%EA%B4%B4%EB%A1%AD%ED%9E%88%EB%8A%94-NULL-%ED%8C%8C%ED%97%A4%EC%B9%98%EA%B8%B0)
  — 문제 6(언박싱 NPE)과 연결됩니다. 기본형에는 `null` 이 없는데 래퍼에는 있다는 게 시작점입니다.

## 2. 손으로 정리할 것

1. **기본형 8개와 래퍼 클래스 8개 짝표.** step01에서 그린 기본형 표 옆에 한 칸 더 그리세요.

   | 기본형 | 래퍼 클래스 |
   |---|---|
   | `byte` | `Byte` |
   | `short` | |
   | `int` | **`Integer`** ← 이름이 다름 |
   | `long` | |
   | `float` | |
   | `double` | |
   | `char` | **`Character`** ← 이름이 다름 |
   | `boolean` | |

   대부분 첫 글자만 대문자인데 `int → Integer`, `char → Character` 둘만 다릅니다.

2. **래퍼 클래스가 왜 필요한가** 두 가지.
   - 기본형은 **객체가 아니라서** 컬렉션(`ArrayList` 등)에 못 넣는다 → step14에서 체감합니다
   - 기본형에는 **`null` 이 없다.** "값이 없음"을 표현하려면 래퍼가 필요하다

3. **오토박싱 / 언박싱** 화살표.
   ```
   int  ──오토박싱(autoboxing)──▶  Integer
   int  ◀──언박싱(unboxing)──────  Integer
   ```
   자바 5부터 자동으로 됩니다. 그전에는 `Integer.valueOf(10)` 처럼 직접 써야 했습니다.

4. **`double` 을 쓰면 안 되는 경우** 한 줄. 그리고 **`BigDecimal` 생성자 규칙**.
   ```java
   new BigDecimal(0.1)     // ❌ double 을 받으므로 오차가 그대로 들어온다
   new BigDecimal("0.1")   // ✅ 문자열로 주면 정확하다
   ```
   이게 `BigDecimal` 에서 가장 많이 하는 실수입니다.

5. **`==` 와 `equals` 정리 3단계.** 지금까지 배운 걸 한 표로 모으세요.

   | 비교 대상 | `==` | `equals` |
   |---|---|---|
   | 기본형 (`int`) | 값 비교 ○ | 쓸 수 없음 |
   | `String` | 주소 비교 (위험) | 내용 비교 ○ |
   | 래퍼 (`Integer`) | 주소 비교 (**함정 있음**) | 값 비교 ○ |
   | `BigDecimal` | 주소 비교 | 값 + **소수점 자릿수**까지 비교 |

## 3. 문제

### 문제 1 — 오토박싱
```java
Integer boxed = 10;
int unboxed = boxed;
System.out.println(boxed + " / " + unboxed);
System.out.println(boxed + 5);
```
`Integer` 는 객체인데 `+ 5` 가 됩니다. 무슨 일이 일어난 건가요?

### 문제 2 — Integer 캐시 ⭐
```java
Integer a = 127, b = 127;
Integer c = 128, d = 128;

System.out.println(a == b);
System.out.println(c == d);
System.out.println(c.equals(d));
```

**127은 `true` 인데 128은 `false` 입니다.**

자바는 자주 쓰는 `-128 ~ 127` 범위의 `Integer` 객체를 **미리 만들어두고 재사용**합니다
(Integer 캐시). 그 범위를 벗어나면 매번 새 객체를 만듭니다.

> step11에서 본 String Pool 과 똑같은 구조입니다. 자주 쓰는 걸 모아두고 공유하는 것.
> 그리고 결론도 같습니다 — **`==` 말고 `equals`.**

### 문제 3 — parseInt vs valueOf
```java
int p = Integer.parseInt("100");
Integer v = Integer.valueOf("100");
```
둘 다 되는데 **반환 타입이 다릅니다.** 각각 무엇인가요?

### 문제 4 — 변환 실패
```java
System.out.println(Integer.parseInt("100"));
System.out.println(Integer.parseInt("10.5"));
System.out.println(Double.parseDouble("10.5"));
```
2번째 줄에서 죽습니다.
```
Exception in thread "main" java.lang.NumberFormatException: For input string: "10.5"
```
**컴파일 에러가 아니라 실행 에러**인 이유는? 그리고 step10에서 배운 걸로 어떻게 막나요?

### 문제 5 — Math ⭐
```java
System.out.println(Math.round(3.5));
System.out.println(Math.round(-3.5));
System.out.println(Math.round(3.4));
System.out.println(Math.ceil(3.1));
System.out.println(Math.floor(3.9));
System.out.println(Math.abs(-5));
System.out.println(Math.pow(2, 10));
System.out.println(Math.sqrt(16));
```

**`Math.round(-3.5)` 가 관문입니다.** `-4` 가 아닙니다.

`round()` 는 "0.5를 더하고 버림" 으로 동작합니다. `-3.5 + 0.5 = -3.0` 이라 `-3` 이 됩니다.
그리고 `ceil`·`floor`·`pow`·`sqrt` 의 반환 타입이 `double` 이라 `4.0` 처럼 소수점이 붙습니다.

### 문제 6 — 언박싱 NPE
```java
Integer n = null;
int m = n;
```
컴파일은 되는데 실행하면 `NullPointerException` 이 납니다. 왜일까요?

> `int m = n;` 은 내부적으로 `int m = n.intValue();` 가 됩니다. `n` 이 `null` 이니 죽습니다.
> **기본형에는 `null` 이 없어서 생기는 함정**입니다.

### 문제 7 — BigDecimal 생성자 ⭐
```java
System.out.println(new BigDecimal(0.1));
System.out.println(new BigDecimal("0.1"));
```
결과가 완전히 다릅니다. 하나는 이렇게 나옵니다.
```
0.1000000000000000055511151231257827021181583404541015625
```
**어느 쪽이고 왜인가요?** (step01 문제 4에서 본 그 숫자입니다)

### 문제 8 — BigDecimal 비교
```java
BigDecimal x = new BigDecimal("1.0");
BigDecimal y = new BigDecimal("1.00");
System.out.println(x.equals(y));
System.out.println(x.compareTo(y) == 0);
```
`equals` 가 `false` 입니다. **값은 같은데** 왜일까요?

`BigDecimal` 의 `equals` 는 **소수점 자릿수(scale)까지** 비교합니다.
값만 비교하려면 `compareTo` 를 써야 합니다. 실무에서 자주 걸리는 함정입니다.

### 문제 9 — BigDecimal 나눗셈
```java
BigDecimal r = new BigDecimal("10").divide(new BigDecimal("3"));
```
실행하면 예외가 납니다.
```
ArithmeticException: Non-terminating decimal expansion; no exact representable decimal result.
```
10 ÷ 3 = 3.333... 이 끝나지 않는데 `BigDecimal` 은 **정확한 값만 담으려** 하기 때문입니다.
고치려면 자릿수와 반올림 방식을 알려줘야 합니다.
```java
new BigDecimal("10").divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP)   // 3.33
```

## 4. 손코딩 과제

`src/step12/` 에 씁니다.

### 과제 1 — `IntegerCache.java`

문제 2를 직접 확인하는 과제입니다.

- `Integer a = 127, b = 127;` → `a == b` 출력
- `Integer c = 128, d = 128;` → `c == d` 출력
- `c.equals(d)` 출력
- `Integer.valueOf(128) == Integer.valueOf(128)` 출력

그리고 **반복문으로 경계를 직접 찾으세요.**

```java
for (int i = 125; i <= 130; i++) {
    Integer x = i, y = i;
    System.out.println(i + " : " + (x == y));
}
```

`true` 가 `false` 로 바뀌는 지점이 어디인지 확인하세요.

### 과제 2 — `Convert.java`

문자열 ↔ 숫자 변환 연습입니다.

- `"100"` → `int` (`Integer.parseInt`)
- `"10.5"` → `double` (`Double.parseDouble`)
- `int 100` → `String` **두 가지 방법** (`String.valueOf(n)` 과 `n + ""`)
- `"abc"` 를 `parseInt` 에 넣어 **예외를 잡아서** `"숫자가 아닙니다"` 출력 (step10 활용)
- `Integer.toBinaryString(10)` 으로 2진수 출력
- `Integer.parseInt("1010", 2)` 로 2진수 문자열 → 숫자

마지막 두 개는 step01 2.4절(진법)에서 가볍게 넘어갔던 부분입니다.

### 과제 3 — `MoneyCalc.java` ⭐

**이 단계의 핵심 과제입니다.** 같은 계산을 `double` 과 `BigDecimal` 로 각각 해서 비교하세요.

```java
// 1) 0.1 을 10번 더하기
double d = 0;
for (int i = 0; i < 10; i++) d += 0.1;
System.out.println("double     : " + d);
// BigDecimal 로도 같은 계산을 해서 출력

// 2) 1.03 - 0.42
// 3) 4.35 * 100  ← 원 단위를 전 단위로 바꾸는 상황
// 4) (int)(4.35 * 100)  ← 여기가 진짜 사고
```

`double` 쪽 결과가 이렇게 나옵니다.

```
0.1 x 10          = 0.9999999999999999
1.03 - 0.42       = 0.6100000000000001
4.35 * 100        = 434.99999999999994
(int)(4.35 * 100) = 434          ← 435 여야 하는데!
```

마지막이 실제로 돈 관련 버그가 나는 모양입니다. **435원이 434원이 됩니다.**
step01 과제 4(`CastLab`)에서 `(int) 3.99` 가 3이었던 것 — **버림**이라서 이렇게 됩니다.

`BigDecimal` 로 같은 계산을 해서 `435` 가 나오는 걸 확인하세요.

> `BigDecimal` 은 반드시 **문자열 생성자**로 만드세요. `new BigDecimal("4.35")`.

### 과제 4 — `BigNumber.java`

`long` 으로 안 되는 계산을 해보는 과제입니다.

- `Long.MAX_VALUE` 를 출력 (약 922경)
- 20자리 숫자 두 개를 `BigInteger` 로 만들어 곱하기
- **20! (팩토리얼)** 을 `long` 과 `BigInteger` 로 각각 계산해서 비교

`long` 으로 21! 이상을 계산하면 오버플로가 나서 **음수가 나오거나 값이 틀립니다.**
step01 문제 3(`byte` 오버플로)과 같은 일이 `long` 에서도 일어나는 것을 확인하세요.

### 과제 5 — `MathLab.java`

`Math` 메서드를 한 번씩 써보세요. 특히 아래 두 가지를 확인하세요.

- `Math.round(3.5)` 와 `Math.round(-3.5)` — 음수에서 예상과 다릅니다
- 반환 타입 — `Math.round()` 는 `long`, `Math.ceil()` 은 `double`

```java
int r = Math.round(3.5);   // 컴파일 에러? 확인해보세요
```

에러가 난다면 메시지를 읽어보세요. step01에서 제일 많이 봤던 그 문장일 겁니다.
`Math.round(double)` 은 `long` 을 돌려주고, `Math.round(float)` 은 `int` 를 돌려줍니다.

그리고 `Math.random()` 으로 **1~6 사이 주사위**를 10번 굴려 출력하세요.

```java
int dice = (int)(Math.random() * 6) + 1;
```

`Math.random()` 은 `0.0 이상 1.0 미만` 을 줍니다. 왜 `* 6` 하고 `+ 1` 하는지 종이에 적어보세요.

### 과제 6 — `BankAccount` 금액 정밀도 (선택)

step10까지 만든 `BankAccount` 의 잔액을 `int` 에서 `BigDecimal` 로 바꿔보세요.
`deposit`·`withdraw` 안의 `+`, `-`, 비교 연산이 전부 메서드 호출로 바뀝니다.

```java
balance = balance.add(amount);              // +
balance = balance.subtract(amount);         // -
if (amount.compareTo(balance) > 0) { ... }  // >
```

**연산자를 못 쓰는 게 `BigDecimal` 의 단점**입니다. 그래서 정확도가 꼭 필요한 곳에만 씁니다.

## 5. 자가 점검

- [ ] 기본형 8개의 래퍼 클래스 이름을 쓸 수 있다
- [ ] 래퍼 클래스가 필요한 이유 두 가지를 안다
- [ ] 오토박싱과 언박싱이 무엇인지 안다
- [ ] `Integer` 를 `==` 로 비교하면 안 되는 이유를 안다
- [ ] `parseInt` 와 `valueOf` 의 반환 타입 차이를 안다
- [ ] `Math.round(-3.5)` 가 `-3` 인 이유를 설명할 수 있다
- [ ] `new BigDecimal(0.1)` 과 `new BigDecimal("0.1")` 의 차이를 안다
- [ ] 돈 계산에 `double` 을 쓰면 안 되는 이유를 예시로 보여줄 수 있다

---

다음은 step13(10장) — 날짜와 시간, 형식화입니다.
책은 `Calendar` 중심이지만 실무는 `java.time` 을 씁니다. 그 차이도 같이 봅니다.
