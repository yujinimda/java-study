# step18 — 열거형과 애너테이션

**책 범위: 12장 「지네릭스, 열거형, 애너테이션」 2~3절**

step07에서 `@Override` 를 붙였습니다. "오버라이딩이라고 컴파일러에게 알리는 표시"
라고만 하고 넘어갔죠. **그게 애너테이션이고, 여기서 정체가 밝혀집니다.**

그리고 열거형(`enum`)은 **"정해진 값 몇 개 중 하나"** 를 표현하는 도구입니다.
`String` 으로 상태를 관리하다 오타로 고생해본 적이 있다면 바로 와닿을 겁니다.

---

## 1. 읽을 것

- 12.2 열거형(enum)
  - 열거형이란?, **열거형의 정의와 사용**
  - 열거형에 멤버 추가하기 (**생성자·필드·메서드**)
  - 열거형의 이해 (내부적으로 어떻게 동작하는가)
- 12.3 애너테이션(annotation)
  - 애너테이션이란?, **표준 애너테이션** (`@Override`, `@Deprecated`, `@SuppressWarnings`)
  - **메타 애너테이션** (`@Target`, `@Retention`, `@Documented`, `@Inherited`)
  - 애너테이션 타입 정의하기, 애너테이션의 요소

### 함께 읽을 글 ([Inpa Dev](https://inpa.tistory.com/category/Language/Java))

- [☕ 자바 Enum 열거형 타입 문법 & 응용 정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%97%B4%EA%B1%B0%ED%98%95Enum-%ED%83%80%EC%9E%85-%EB%AC%B8%EB%B2%95-%ED%99%9C%EC%9A%A9-%EC%A0%95%EB%A6%AC)
  — 12.2절 전체에 대응합니다. 실무 활용 예시가 풍부합니다.
- [☕ 누구나 쉽게 배우는 Reflection API 사용법](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EB%88%84%EA%B5%AC%EB%82%98-%EC%89%BD%EA%B2%8C-%EB%B0%B0%EC%9A%B0%EB%8A%94-Reflection-API-%EC%82%AC%EC%9A%A9%EB%B2%95)
  — **애너테이션은 리플렉션과 짝**입니다. 실행 중에 애너테이션을 읽으려면 리플렉션이
  필요합니다(과제 6). 어렵다면 과제 6을 건너뛰고 나중에 봐도 됩니다.
- [☕ 자바 Object 클래스와 메서드 오버라이딩](https://inpa.tistory.com/entry/JAVA-%E2%98%95-Object-%ED%81%B4%EB%9E%98%EC%8A%A4%EC%99%80-%EC%83%81%EC%9C%84-%EB%A9%94%EC%84%9C%EB%93%9C-%EC%9E%AC%EC%A0%95%EC%9D%98-%ED%99%9C%EC%9A%A9-%EC%B4%9D%EC%A0%95%EB%A6%AC)
  — step07 복습. `@Override` 가 무엇을 검사하는지(문제 7) 이해하는 데 필요합니다.

## 2. 손으로 정리할 것

1. **`enum` 이 없으면 어떻게 했을지** 적어보세요.

   ```java
   // 옛날 방식 — 상수로
   public static final int MON = 0;
   public static final int TUE = 1;

   void setDay(int day) { ... }
   setDay(99);          // 컴파일 통과! 99는 요일이 아닌데
   setDay(MON);         // 이것도 그냥 0
   ```

   ```java
   // enum 방식
   enum Day { MON, TUE, WED }
   void setDay(Day day) { ... }
   setDay(99);          // 컴파일 에러 — 애초에 못 넣음
   ```

   **`enum` 은 "이 셋 중 하나"를 타입으로 강제하는 것**입니다. 한 줄로 정리하세요.

2. **`enum` 의 정체.** 문법은 특별해 보이지만 실은 **클래스**입니다.

   ```
   enum Day { MON, TUE, WED }
        ↓ 대략 이런 뜻
   class Day {
       static final Day MON = new Day();   // 상수 하나하나가 Day 객체
       static final Day TUE = new Day();
       static final Day WED = new Day();
   }
   ```

   그래서 **필드와 메서드를 가질 수 있고**(손정리 4), `==` 로 비교해도 됩니다(문제 3).

3. **`enum` 기본 메서드 4개.**

   | 메서드 | 하는 일 | 예시 결과 |
   |---|---|---|
   | `name()` | 상수 이름 문자열 | `"TUE"` |
   | `ordinal()` | 선언 순서 (0부터) | `1` |
   | `values()` | 전체 상수 배열 | `[MON, TUE, WED]` |
   | `valueOf("X")` | 문자열 → 상수 | `Day.WED` |

   > `ordinal()` 은 **순서에 의존하는 코드를 만들지 마세요.** 상수 하나를 중간에
   > 끼워넣으면 번호가 전부 밀립니다. 값이 필요하면 손정리 4처럼 필드를 직접 붙입니다.

4. **필드를 가진 `enum` 형태**를 통째로 적어두세요. 실무에서 가장 많이 쓰는 모양입니다.

   ```java
   enum Status {
       READY("준비"), RUNNING("실행중"), DONE("완료");   // ← 세미콜론 필수
       private final String label;
       Status(String label) { this.label = label; }      // 생성자는 private
       public String getLabel() { return label; }
   }
   ```

   상수 목록 뒤의 **세미콜론**과, 생성자가 **`private`** 라는 점이 포인트입니다.

5. **표준 애너테이션 3개**의 용도를 한 줄씩.

   | 애너테이션 | 누구에게 하는 말 |
   |---|---|
   | `@Override` | 컴파일러야, 이거 오버라이딩 맞는지 **검사해줘** |
   | `@Deprecated` | 이거 낡았으니 **쓰지 마** (경고) |
   | `@SuppressWarnings` | 이 경고는 **알고 있으니 숨겨줘** |

## 3. 문제

### 문제 1 — `enum` 기본
```java
enum Day { MON, TUE, WED }

Day d = Day.TUE;
System.out.println(d);
System.out.println(d.name());
System.out.println(d.ordinal());
System.out.println(Arrays.toString(Day.values()));
System.out.println(Day.valueOf("WED"));
```
`println(d)` 가 주소가 아니라 `TUE` 로 나옵니다. 왜일까요?
(힌트: step07에서 배운 그 메서드를 `enum` 이 이미 오버라이딩해뒀습니다)

### 문제 2 — 없는 상수
```java
Day.valueOf("SUN");
```
실행하면 이렇게 죽습니다.
```
IllegalArgumentException: No enum constant Day.SUN
```
**컴파일 에러가 아니라 실행 에러**입니다. 왜 컴파일 때 못 잡을까요?

### 문제 3 — `==` 와 `equals` ⭐
```java
Day d = Day.TUE;
System.out.println(d == Day.TUE);
System.out.println(d.equals(Day.TUE));
```
**둘 다 `true`** 입니다.

step02와 step14에서 "객체는 `==` 로 비교하지 마라"고 배웠는데, `enum` 은 예외입니다.
**`enum` 에서는 `==` 를 쓰는 게 권장**됩니다. 왜 안전할까요?
(힌트: 손정리 2. `enum` 상수는 몇 개나 만들어지나요?)

### 문제 4 — `compareTo`
```java
System.out.println(Day.MON.compareTo(Day.WED));
```
결과는 `-2` 입니다. 무엇을 뺀 값인가요?
그리고 `enum` 을 `TreeSet` 에 넣으면 무슨 순서로 정렬될까요? (step16 참고)

### 문제 5 — 필드를 가진 `enum`
```java
enum Status {
    READY("준비"), RUNNING("실행중"), DONE("완료");
    private final String label;
    Status(String label) { this.label = label; }
    public String getLabel() { return label; }
}

for (Status s : Status.values()) {
    System.out.println(s + " (" + s.ordinal() + ") → " + s.getLabel());
}
```
출력을 예상해서 적으세요. 그리고 생성자를 `public` 으로 바꾸면 어떻게 되나요?

### 문제 6 — `switch` 에서 `enum` ⭐
```java
Day d = Day.TUE;
switch (d) {
    case MON: System.out.println("월요일"); break;
    case TUE: System.out.println("화요일"); break;
    default:  System.out.println("기타");
}
```
`case Day.MON:` 이 아니라 **`case MON:`** 입니다. `switch` 괄호 안의 타입을 보고
컴파일러가 이미 `Day` 인 걸 알기 때문에 앞을 생략합니다.

**여기서 책과 실제 환경이 갈립니다.** 책(JDK 8 기준)은 `case Day.MON:` 이 **에러**라고
합니다. 실제로 JDK 17까지는 그랬습니다.
```
error: an enum switch case label must be the unqualified name of an enumeration constant
```

그런데 **JDK 21에서는 컴파일이 됩니다.** 직접 확인해보세요.
```bash
./run step18/DayEnum.java                        # 통과
javac --release 17 src/step18/DayEnum.java       # 위 에러 발생
```

JDK 21의 패턴 매칭 `switch` 가 들어오면서 규칙이 완화된 것입니다.
**책이 틀린 게 아니라 시점이 다릅니다.** step00의 JDK 폴더 그림과 같은 상황입니다.

> 관례는 여전히 `case MON:` 입니다. 짧고, 옛 버전에서도 돌아가니까요.
> step03에서 배운 `break` 규칙도 그대로 적용됩니다. 빠뜨리면 흘러내려갑니다.

### 문제 7 — `@Override` 가 잡아주는 것 ⭐
```java
class Parent { public String toString() { return "P"; } }

class Child extends Parent {
    @Override public String toStrng() { return "C"; }    // 오타!
}
```
컴파일 에러입니다.
```
error: method does not override or implement a method from a supertype
```

그런데 `@Override` 를 **빼면** 컴파일이 통과하고, `new Child()` 를 출력하면 `P` 가 나옵니다.

**오버라이딩한 줄 알았는데 새 메서드를 만든 것**이죠. 이 버그가 왜 찾기 어려운지,
그리고 `@Override` 를 항상 붙여야 하는 이유를 한 줄로 쓰세요.

### 문제 8 — `@Deprecated`
```java
class Old {
    @Deprecated static void oldWay() { System.out.println("옛날 방식"); }
}
Old.oldWay();
```
컴파일하면 **에러가 아니라 경고**입니다.
```
warning: [deprecation] oldWay() in Old has been deprecated
```
왜 에러로 막지 않고 경고만 줄까요? (힌트: 이미 그 메서드를 쓰고 있는 코드가 세상에 많다면)

### 문제 9 — 메타 애너테이션
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface MyTest { String value() default "설명없음"; }
```
- `@interface` 와 `interface` 는 무엇이 다른가요?
- `@Target(ElementType.METHOD)` 를 지우면 무엇이 달라지나요?
- `@Retention` 의 세 값(`SOURCE` / `CLASS` / `RUNTIME`) 중
  **실행 중에 읽으려면** 무엇이어야 하나요?

## 4. 손코딩 과제

`src/step18/` 에 씁니다.

### 과제 1 — `DayEnum.java`

`enum Day { MON, TUE, WED, THU, FRI, SAT, SUN }` 을 만들고,

- `values()` 로 전체를 출력 (이름과 `ordinal()` 같이)
- `valueOf("FRI")` 로 하나 꺼내기
- 없는 문자열을 `valueOf` 해보고 **예외 확인**
- `switch` 로 주말/평일 판별해서 출력

```
MON(0) 평일
...
SAT(5) 주말
SUN(6) 주말
```

> `switch` 에서 `case SAT: case SUN:` 처럼 묶는 건 step03에서 배운 fall-through 활용입니다.

### 과제 2 — `Pizza.java` (필드 붙인 enum) ⭐

값을 가진 `enum` 을 만드세요.

```java
enum Pizza {
    SMALL(10000, 20), MEDIUM(15000, 30), LARGE(20000, 40);
    // 가격, 지름(cm)
}
```

- 생성자, `getPrice()`, `getSize()`
- `int priceWithTax()` — 부가세 10% 붙인 가격을 계산하는 **메서드**
- `values()` 로 전체 메뉴판 출력

```
SMALL  : 10000원 (20cm) → 세금포함 11000원
MEDIUM : 15000원 (30cm) → 세금포함 16500원
LARGE  : 20000원 (40cm) → 세금포함 22000원
```

> `enum` 이 **메서드를 가질 수 있다**는 게 상수와 결정적으로 다른 점입니다.
> `static final int` 로는 이렇게 못 합니다.

### 과제 3 — `OrderStatus.java` (step05 BankAccount 이어서)

step05·06·08에서 만든 `BankAccount` 나 새 주문 클래스에 **상태**를 붙이세요.

```java
enum OrderStatus { PENDING, PAID, SHIPPED, DELIVERED, CANCELLED }
```

- `Order` 클래스에 `OrderStatus status` 필드
- `void next()` — 다음 상태로 진행 (`PENDING → PAID → SHIPPED → DELIVERED`)
- `void cancel()` — `CANCELLED` 로. 단 **이미 배송됐으면 거부**
- 상태를 `String` 으로 관리했을 때와 비교하는 주석 쓰기

```java
// String status = "paid";  ← 오타 나면? "Paid" 와 "paid" 가 다른 값?
// OrderStatus.PAID         ← 오타는 컴파일 에러
```

### 과제 4 — `ErrorCode.java` (step10 이어서)

step10에서 예외처리를 배웠습니다. **에러 코드를 `enum` 으로** 정리하세요.

```java
enum ErrorCode {
    NOT_FOUND(404, "찾을 수 없습니다"),
    FORBIDDEN(403, "권한이 없습니다"),
    SERVER_ERROR(500, "서버 오류");
    // 코드, 메시지
}
```

- step10에서 만든 사용자 정의 예외에 `ErrorCode` 를 담기
- 예외를 잡았을 때 코드와 메시지를 같이 출력
- `values()` 로 전체 에러 코드 표 출력

> 실무 API 서버가 정확히 이 형태로 에러를 관리합니다.
> step10을 아직 안 했다면 예외 없이 `enum` 부분만 만들어도 됩니다.

### 과제 5 — `OverrideTest.java`

`@Override` 가 잡아주는 것을 **직접 확인**하세요.

1. `toString()` 을 **오타 내서** 오버라이딩하고 `@Override` 를 붙여보기 → 컴파일 에러 확인
2. `@Override` 를 **떼고** 같은 코드를 컴파일 → 통과하는지 확인
3. 출력해보고 **어느 쪽 `toString` 이 불리는지** 확인

에러 메시지를 `notes/errors.md` 에 적으세요.

`@Deprecated` 도 붙여보고, 컴파일 옵션에 따라 경고가 어떻게 달라지는지 확인해보세요.

```bash
javac -Xlint:deprecation 파일.java
```

### 과제 6 — `MyTest.java` (선택, 난이도 높음)

애너테이션을 **직접 만들고 실행 중에 읽어보세요.**

```java
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface MyTest { String value() default "설명없음"; }

public class MyTestRunner {
    @MyTest("덧셈 검사")
    public void testAdd() { System.out.println("1+1=2 확인"); }

    public static void main(String[] args) throws Exception {
        MyTest m = MyTestRunner.class
                     .getMethod("testAdd")
                     .getAnnotation(MyTest.class);
        System.out.println("애너테이션 값: " + m.value());
    }
}
```

`getMethod(...).getAnnotation(...)` 이 **리플렉션**입니다.
`@Retention(RUNTIME)` 을 `SOURCE` 로 바꿔보고 **결과가 어떻게 달라지는지** 확인하세요.

> JUnit 같은 테스트 도구가 `@Test` 붙은 메서드를 찾아 실행하는 원리가 정확히 이것입니다.
> 지금 다 이해 못 해도 되고, **"애너테이션은 프로그램이 읽을 수 있는 메모"** 라는 감만
> 가져가면 충분합니다.

## 5. 자가 점검

- [ ] `enum` 이 상수(`static final int`)보다 나은 점을 설명할 수 있다
- [ ] `enum` 상수 하나하나가 객체라는 걸 안다
- [ ] `name()` / `ordinal()` / `values()` / `valueOf()` 를 쓸 수 있다
- [ ] `ordinal()` 에 의존하면 안 되는 이유를 안다
- [ ] `enum` 에 필드와 메서드를 붙일 수 있다
- [ ] `enum` 비교에 `==` 를 써도 되는 이유를 안다
- [ ] `@Override` 가 무엇을 검사하는지 설명할 수 있다
- [ ] `@Retention` 의 `RUNTIME` 이 왜 필요한지 안다

---

다음은 step19(쓰레드)입니다. 지금까지 코드가 위에서 아래로 한 줄씩 흘렀다면,
거기서는 **여러 흐름이 동시에** 도는 세계로 들어갑니다.
