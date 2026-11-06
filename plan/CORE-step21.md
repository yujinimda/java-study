# step21 — 람다식 · 함수형 인터페이스

**책 범위: 14장 「람다와 스트림」 1~2절**

step09 과제 5에서 이렇게 썼습니다.

```java
Movable m = new Movable() {
    public void move() { System.out.println("움직인다"); }
};
```

메서드 하나 넘기려고 다섯 줄을 썼죠. 람다를 쓰면 한 줄입니다.

```java
Movable m = () -> System.out.println("움직인다");
```

자바스크립트 화살표 함수와 모양도 생각도 거의 같습니다. **이 단계는 JS 경험이
그대로 통하는 구간**입니다. 다만 자바에는 "타입"이 있어서, 람다를 담을 그릇
(함수형 인터페이스)이 필요하다는 점만 다릅니다.

---

## 1. 읽을 것

- 14.1 람다식
  - 람다식이란, **람다식 작성하기**, 람다식의 예
  - **함수형 인터페이스** — 람다식과 함수형 인터페이스, `@FunctionalInterface`
  - 함수형 인터페이스 타입의 매개변수와 반환타입
- 14.2 `java.util.function` 패키지
  - **표준 함수형 인터페이스** (`Supplier`, `Consumer`, `Function`, `Predicate`, `BiFunction` …)
  - 매개변수가 2개인 것, 기본형을 쓰는 것
  - 함수의 합성 (`andThen`, `compose`)
  - **메서드 참조 (`::`)** 와 생성자 참조

### 함께 읽을 글 ([Inpa Dev](https://inpa.tistory.com/category/Language/Java))

- [☕ 람다 표현식(Lambda Expression) 완벽 정리](https://inpa.tistory.com/entry/%E2%98%95-Lambda-Expression)
  — 이 단계의 핵심 글. 익명 클래스에서 람다로 줄어드는 과정이 단계별로 나옵니다.
- [☕ 함수형 인터페이스 표준 API 총정리](https://inpa.tistory.com/entry/%E2%98%95-%ED%95%A8%EC%88%98%ED%98%95-%EC%9D%B8%ED%84%B0%ED%8E%98%EC%9D%B4%EC%8A%A4-API)
  — `Supplier`/`Consumer`/`Function`/`Predicate` 를 표로 정리한 글. 문제 4와 짝입니다.
- [☕ 함수 디스크립터(Function Descriptor) 란?](https://inpa.tistory.com/entry/%F0%9F%9A%80-Function-Descriptor)
  — "어떤 람다가 어떤 인터페이스에 들어갈 수 있는가"를 판단하는 기준.
- [☕ 람다식을 더 짧게 - 메소드::참조 문법](https://inpa.tistory.com/entry/JAVA8-%E2%98%95-%EB%9E%8C%EB%8B%A4%EC%8B%9D%EC%9D%84-%EB%8D%94-%EC%A7%A7%EA%B2%8C-%EB%A9%94%EC%86%8C%EB%93%9C-%EC%B0%B8%EC%A1%B0Method-Reference)
  — 문제 7(3단계 줄이기)의 마지막 단계.
- [☕ 람다식 리팩토링 하기 (Comparator 축약 원리)](https://inpa.tistory.com/entry/%E2%98%95-%EB%9E%8C%EB%8B%A4%EC%8B%9D-%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81-Comparator)
  — step16 의 `Comparator` 가 람다로 짧아지는 과정. 과제 4와 짝입니다.

## 2. 손으로 정리할 것

1. **익명 클래스 → 람다** 로 줄이는 과정을 단계별로 쓰세요.

   ```java
   // ① 익명 클래스
   Movable m = new Movable() {
       public void move() { System.out.println("이동"); }
   };

   // ② 어차피 메서드가 하나뿐이니 이름·제어자·반환타입을 지운다
   Movable m = () -> { System.out.println("이동"); };

   // ③ 몸통이 한 줄이면 중괄호와 세미콜론도 지운다
   Movable m = () -> System.out.println("이동");
   ```

2. **자바스크립트 화살표 함수와 비교 표.** JS 를 아신다면 이 표 하나로 정리됩니다.

   | | 자바스크립트 | 자바 |
   |---|---|---|
   | 기본형 | `(a, b) => a + b` | `(a, b) -> a + b` |
   | 화살표 | `=>` | `->` |
   | 한 줄 반환 | `return` 생략 | `return` 생략 |
   | 여러 줄 | `{ ... return x; }` | `{ ... return x; }` |
   | 담는 곳 | 아무 변수나 | **함수형 인터페이스 타입만** |
   | 타입 | 없음 | 있음 (생략 가능, 추론됨) |

   가장 큰 차이는 **담는 그릇**입니다. JS 는 함수를 그냥 변수에 넣지만,
   자바는 그 람다가 **어떤 인터페이스의 구현인지** 정해져 있어야 합니다.

3. **람다를 쓸 수 있는 조건.** 딱 하나입니다.

   ```
   추상 메서드가 정확히 1개인 인터페이스  =  함수형 인터페이스
   ```

   `@FunctionalInterface` 를 붙이면 컴파일러가 이 조건을 검사해줍니다.
   (step07 의 `@Override` 와 같은 성격입니다 — 실수를 컴파일 단계에서 잡아줍니다.)

4. **표준 함수형 인터페이스 4종.** "받는 게 있나 / 주는 게 있나" 로 외우세요.

   | 인터페이스 | 매개변수 | 반환 | 메서드 | 한 줄 설명 |
   |---|---|---|---|---|
   | `Supplier<T>` | 없음 | `T` | `get()` | 만들어서 준다 |
   | `Consumer<T>` | `T` | 없음 | `accept()` | 받아서 쓰고 끝 |
   | `Function<T,R>` | `T` | `R` | `apply()` | 받아서 바꿔서 준다 |
   | `Predicate<T>` | `T` | `boolean` | `test()` | 받아서 참/거짓 판단 |
   | `BiFunction<T,U,R>` | `T`, `U` | `R` | `apply()` | 두 개 받아서 하나 준다 |

5. **메서드 참조 4종**을 예시와 함께 적으세요.

   ```
   static 메서드      ClassName::staticMethod      Integer::parseInt
   인스턴스 메서드     obj::method                  System.out::println
   타입의 메서드      ClassName::instanceMethod    String::compareTo
   생성자             ClassName::new               ArrayList::new
   ```

## 3. 문제

### 문제 1 — 람다로 줄이기
아래를 람다로 바꾸세요.

```java
Runnable r = new Runnable() {
    public void run() { System.out.println("실행"); }
};
```

step19 에서 쓰레드를 만들 때 계속 봤던 그 형태입니다.
`new Thread(r).start()` 도 한 줄로 합쳐보세요.

### 문제 2 — 매개변수가 있는 람다
아래 셋은 전부 같은 뜻입니다. 왜 생략이 가능한지 설명하세요.

```java
① Function<Integer, Integer> f = (Integer n) -> { return n * 2; };
② Function<Integer, Integer> f = (n) -> { return n * 2; };
③ Function<Integer, Integer> f = n -> n * 2;
```

**괄호를 생략할 수 없는 경우**는 언제인가요? (힌트: 매개변수가 0개거나 2개일 때)

### 문제 3 — 함수형 인터페이스가 아니면 ⭐
```java
interface Two {
    void a();
    void b();
}

Two t = () -> System.out.println("hi");
```

컴파일 에러입니다. 실제 메시지입니다.

```
error: incompatible types: Two is not a functional interface
    multiple non-overriding abstract methods found in interface Two
```

**추상 메서드가 2개라 안 됩니다.** 왜 1개여야만 하는지 한 문장으로 설명하세요.
(힌트: `() -> ...` 만 보고 컴파일러가 `a()` 를 구현한 건지 `b()` 를 구현한 건지
알 수 있나요?)

### 문제 4 — 표준 함수형 인터페이스
아래 각 상황에 맞는 인터페이스를 고르세요.

```
① 랜덤 숫자를 하나 만들어서 돌려준다
② 문자열을 받아서 화면에 출력만 한다
③ 숫자를 받아서 제곱해서 돌려준다
④ 숫자를 받아서 짝수인지 판단한다
⑤ 두 숫자를 받아서 더한 값을 돌려준다
```

보기: `Supplier` / `Consumer` / `Function` / `Predicate` / `BiFunction`

### 문제 5 — 호출 메서드 이름
```java
Supplier<String> sup = () -> "안녕";
Consumer<String> con = s -> System.out.println(s);
Function<Integer,Integer> fn = n -> n * 2;
Predicate<Integer> pre = n -> n > 0;

System.out.println(sup.???);
con.???("hi");
System.out.println(fn.???(5));
System.out.println(pre.???(-3));
```

`???` 에 들어갈 메서드 이름을 각각 쓰세요. 손정리 4번 표를 보지 말고 해보세요.

### 문제 6 — 변수 캡처
```java
int count = 0;
Runnable r = () -> System.out.println(count);
count = 10;
r.run();
```

컴파일 에러입니다. 람다 안에서 쓰는 지역변수는 **사실상 final** 이어야 합니다.
JS 클로저는 바깥 변수를 자유롭게 바꿀 수 있는데 자바는 왜 막을까요?

(힌트: step20 을 떠올려보세요. 람다가 **다른 쓰레드**에서 실행될 수도 있습니다.)

### 문제 7 — 3단계로 줄이기 ⭐
같은 정렬을 세 가지로 씁니다. ②와 ③을 채우세요.

```java
List<String> list = new ArrayList<>(Arrays.asList("banana", "apple", "cherry"));

// ① 익명 클래스
list.sort(new Comparator<String>() {
    public int compare(String a, String b) { return a.compareTo(b); }
});

// ② 람다
list.sort( ??? );

// ③ 메서드 참조
list.sort( ??? );
```

셋 다 결과는 같습니다.

```
[apple, banana, cherry]
```

### 문제 8 — 메서드 참조 판별
아래를 메서드 참조로 바꿀 수 있는 것과 없는 것으로 나누세요.

```java
① s -> System.out.println(s)
② n -> Integer.parseInt(n)
③ n -> n * 2
④ (a, b) -> a.compareTo(b)
⑤ () -> new ArrayList<>()
```

바꿀 수 있는 것은 어떻게 쓰는지도 적으세요.

> 규칙: **람다의 매개변수를 그대로 다른 메서드에 넘기기만 할 때** 바꿀 수 있습니다.
> 중간에 계산이 끼면(③) 못 바꿉니다.

## 4. 손코딩 과제

`src/step21/` 에 씁니다.

### 과제 1 — `LambdaBasic.java` (step09 과제 2·5 이어서)

step09 의 `Movable` 인터페이스를 그대로 씁니다.

```java
@FunctionalInterface
interface Movable { void move(); }
```

- `Car`, `Bicycle`, `Airplane` 을 **클래스 파일로 만들지 않고 전부 람다로** 만드세요
- `Movable[]` 배열에 담아 반복문으로 `move()` 호출

step09 에서 파일 3개를 만들었던 것이 **변수 3개**로 줄어듭니다.
`@FunctionalInterface` 를 붙이고, 일부러 메서드를 하나 더 추가해서 에러도 확인하세요.

### 과제 2 — `FunctionalLab.java`

표준 함수형 인터페이스 5종을 하나씩 써보세요.

```java
Supplier<String> sup = ...;      // 오늘 날짜 문자열을 만들어 준다 (step13 연결)
Consumer<String> con = ...;      // 받은 문자열을 대괄호로 감싸 출력
Function<String,Integer> fn = ...; // 문자열 길이를 돌려준다
Predicate<String> pre = ...;     // 빈 문자열인지 판단
BiFunction<Integer,Integer,Integer> bi = ...; // 두 수 중 큰 값
```

각각 호출해서 결과를 출력하세요. **메서드 이름이 전부 다르다**는 걸 손으로 겪는 게
과제의 목적입니다 (`get` / `accept` / `apply` / `test` / `apply`).

### 과제 3 — `MethodRef.java`

문제 8의 5개를 직접 컴파일해보세요.

- 바꿀 수 있는 것은 **람다 버전과 메서드 참조 버전을 나란히** 쓰고 결과가 같은지 확인
- 바꿀 수 없는 것은 **왜 안 되는지** 주석으로

`forEach(System.out::println)` 도 꼭 써보세요. 앞으로 가장 많이 쓸 형태입니다.

### 과제 4 — `SortLab.java` (step16 이어서) ⭐

step16 에서 만든 `Student` 리스트를 다시 씁니다.

같은 정렬을 **네 가지**로 쓰세요.

```java
// ① Comparable 구현 (step16)
Collections.sort(students);

// ② 익명 클래스 Comparator (step16)
students.sort(new Comparator<Student>() { ... });

// ③ 람다
students.sort((a, b) -> ...);

// ④ Comparator.comparing + 메서드 참조
students.sort(Comparator.comparing(Student::getName));
```

④가 가장 짧고 읽기 좋습니다. **점수 내림차순**도 만들어보세요
(`Comparator.comparingInt(Student::getScore).reversed()`).

### 과제 5 — `CustomFunctional.java`

표준 인터페이스를 쓰지 않고 **직접 함수형 인터페이스를 정의**하세요.

```java
@FunctionalInterface
interface Calculator {
    int operate(int a, int b);
}
```

- 더하기·빼기·곱하기·나누기를 각각 람다로 만들어 변수에 담으세요
- `static int run(Calculator c, int a, int b)` 메서드를 만들어 호출하세요

```java
System.out.println(run(add, 10, 3));    // 13
System.out.println(run(divide, 10, 3)); // 3
```

step09 과제 4(`Payment` 인터페이스로 갈아끼우기)와 같은 구조인데, **구현 클래스를
안 만들어도 된다**는 점이 다릅니다.

### 과제 6 — `Composition.java` (선택)

함수 합성을 써보세요.

```java
Function<Integer,Integer> double_ = n -> n * 2;
Function<Integer,Integer> plus10 = n -> n + 10;

System.out.println(double_.andThen(plus10).apply(5));   // ?
System.out.println(double_.compose(plus10).apply(5));   // ?
```

두 결과가 다릅니다. **먼저 실행되는 쪽**이 어디인지 확인하고 주석으로 정리하세요.

## 5. 자가 점검

- [ ] 익명 클래스를 람다로 3단계에 걸쳐 줄일 수 있다
- [ ] 함수형 인터페이스의 조건(추상 메서드 1개)을 안다
- [ ] `@FunctionalInterface` 가 무엇을 검사해주는지 안다
- [ ] `Supplier`/`Consumer`/`Function`/`Predicate` 를 상황에 맞게 고를 수 있다
- [ ] 각 인터페이스의 호출 메서드 이름을 안다
- [ ] 메서드 참조로 바꿀 수 있는 람다를 판별할 수 있다
- [ ] 람다가 잡는 지역변수가 사실상 final 이어야 하는 이유를 안다
- [ ] `Comparator` 를 람다와 메서드 참조로 쓸 수 있다

---

다음은 step22(14장 3~4절, 스트림)입니다. 지금 만든 람다를 **컬렉션에 꽂아서**
`filter().map().collect()` 로 쓰게 됩니다. 자바스크립트 배열 메서드와 아주 비슷합니다.
