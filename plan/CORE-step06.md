# step06 — 오버로딩 · 생성자 · 초기화

**책 범위: 6장 「객체지향개념 I」 4~6절**

step05에서 객체를 만들 때 이렇게 했습니다.

```java
Student s = new Student();
s.name = "지니";
s.korean = 90;
```

객체를 만든 **다음에** 값을 하나씩 채워 넣었죠. 생성자를 배우면 **만들면서 동시에** 채웁니다.

```java
Student s = new Student("지니", 90, 85, 80);
```

---

## 1. 읽을 것

- 6.4 메서드 오버로딩 (조건, `println` 의 예)
- 6.5 생성자
  - 생성자란, **기본 생성자**, 매개변수가 있는 생성자
  - **생성자에서 다른 생성자 호출 `this()`**
  - **참조변수 `this`**
  - 생성자를 이용한 인스턴스 복사
- 6.6 변수의 초기화
  - 명시적 초기화, **초기화 블럭**, 멤버변수의 초기화 순서

### 함께 읽을 글 ([Inpa Dev](https://inpa.tistory.com/category/Language/Java))

- [☕ 자바 가변 인수(VarArgs) - 매개변수를 동적으로](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B0%80%EB%B3%80-%EC%9D%B8%EC%88%98Varargs-%EB%A7%A4%EA%B0%9C%EB%B3%80%EC%88%98%EB%A5%BC-%EB%8F%99%EC%A0%81%EC%9C%BC%EB%A1%9C)
  — 오버로딩의 사촌. `sum(1,2)` 와 `sum(1,2,3)` 을 한 메서드로 받는 법.
- [☕ 클래스는 언제 메모리에 로딩 & 초기화 되는가 ❓](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%ED%81%B4%EB%9E%98%EC%8A%A4%EB%8A%94-%EC%96%B8%EC%A0%9C-%EB%A9%94%EB%AA%A8%EB%A6%AC%EC%97%90-%EB%A1%9C%EB%94%A9-%EC%B4%88%EA%B8%B0%ED%99%94-%EB%90%98%EB%8A%94%EA%B0%80-%E2%9D%93)
  — 6.6 초기화 순서의 배경. 문제 7과 짝입니다.
- [☕ JVM 내부 구조 & 메모리 영역 총정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-JVM-%EB%82%B4%EB%B6%80-%EA%B5%AC%EC%A1%B0-%EB%A9%94%EB%AA%A8%EB%A6%AC-%EC%98%81%EC%97%AD-%EC%8B%AC%ED%99%94%ED%8E%B8)
  — step05의 「스택 & 힙」을 소화했다면 이제 읽을 만합니다.

## 2. 손으로 정리할 것

1. **오버로딩 성립 조건 3개.** 그리고 **성립하지 않는 경우**도 같이.
   - 이름이 같다
   - 매개변수의 **개수 또는 타입**이 다르다
   - **반환 타입은 조건이 아니다** ← 여기가 함정

2. **생성자의 특징 3가지.**
   - 이름이 ___ 와 같다
   - 반환 타입이 ___
   - 언제 호출되는가

3. **기본 생성자가 자동으로 생기는 조건.** 그리고 **안 생기는 경우**는 언제인가.
   (이게 step06에서 제일 많이 당하는 함정입니다)

4. **`this` 와 `this()` 의 차이**를 한 줄씩.
   ```java
   this.name = name;   // this  → ?
   this(name, 0, 0);   // this() → ?
   ```

5. **초기화 순서**를 번호로. (클래스 변수 / 인스턴스 변수 각각)
   ```
   인스턴스 변수:  기본값 → 명시적 초기화 → 초기화 블럭 → 생성자
   ```

## 3. 문제

### 문제 1 — 오버로딩 성립 판정
아래 중 `void print(int a)` 와 **오버로딩이 성립하는 것**을 모두 고르세요.
```java
① void print(int a, int b)
② void print(double a)
③ int print(int a)
④ void print(int b)
⑤ void print(String a)
```

### 문제 2 — 어느 메서드가 불리나
```java
static void show(int a)    { System.out.println("int"); }
static void show(double a) { System.out.println("double"); }
static void show(String a) { System.out.println("String"); }

show(10);
show(3.14);
show('A');
show(10L);
```
마지막 두 개가 관문입니다. `char` 와 `long` 을 받는 메서드가 없는데 뭐가 불릴까요?
(힌트: step01의 자동 형변환 화살표)

### 문제 3 — 기본 생성자 ⭐
```java
class A {
    int x;
}
class B {
    int x;
    B(int x) { this.x = x; }
}

A a = new A();      // ①
B b1 = new B(10);   // ②
B b2 = new B();     // ③
```
③이 컴파일 에러입니다. **①은 되는데 왜 ③은 안 되나요?**

### 문제 4 — this
```java
class Point {
    int x, y;
    Point(int x, int y) {
        x = x;
        y = y;
    }
}
Point p = new Point(10, 20);
System.out.println(p.x + ", " + p.y);
```
에러는 안 나는데 결과가 이상합니다. 왜이고 어떻게 고치나요?

### 문제 5 — this()
```java
class Car {
    String color;
    int door;

    Car() {
        this("흰색", 4);
        System.out.println("기본 생성자");
    }
    Car(String color, int door) {
        this.color = color;
        this.door = door;
        System.out.println("매개변수 생성자");
    }
}
Car c = new Car();
System.out.println(c.color + " " + c.door);
```
출력 **순서**까지 정확히 적으세요.

### 문제 6 — this() 제약
```java
Car(String color) {
    System.out.println("시작");
    this(color, 4);
}
```
컴파일 에러입니다. 이유는? (규칙 하나만 알면 됩니다)

### 문제 7 — 초기화 순서
```java
class Init {
    int a = 1;
    { a = 2; System.out.println("초기화 블럭: " + a); }
    Init() { a = 3; System.out.println("생성자: " + a); }
}
Init i = new Init();
System.out.println("최종: " + i.a);
```

### 문제 8 — static 초기화 블럭
```java
class Sample {
    static int s = 1;
    static { s = 2; System.out.println("static 블럭"); }
    int i = 10;
    { System.out.println("인스턴스 블럭"); }
    Sample() { System.out.println("생성자"); }
}

Sample s1 = new Sample();
Sample s2 = new Sample();
```
"static 블럭" 은 **몇 번** 찍히나요? 전체 출력 순서를 적으세요.

## 4. 손코딩 과제

### 과제 1 — `Student.java` 개선 (step05 과제 1 이어서)

step05에서 만든 `Student` 에 생성자를 추가하세요.

- 생성자 ① `Student(String name, int korean, int english, int math)`
- 생성자 ② `Student(String name)` — 점수는 전부 0으로. **`this()` 를 써서 ①을 호출**
- 생성자 ③ 기본 생성자 — 이름 `"이름없음"`, 점수 0. 역시 `this()` 활용

`StudentTest` 에서 세 생성자를 모두 써서 객체를 만들고 출력하세요.

> step05 버전과 비교해보세요. `s.name = ...` 을 4줄 쓰던 게 한 줄이 됩니다.

### 과제 2 — `Rectangle.java` 개선 (step05 과제 2 이어서)

- 생성자 `Rectangle(double width, double height)`
- 생성자 `Rectangle(double size)` — **정사각형용.** `this()` 로 위 생성자 호출
- 기본 생성자 — 1 × 1

매개변수 이름을 필드와 **똑같이** 쓰고 `this.` 로 구분하세요. (문제 4의 그 함정을 직접 겪어보기)

### 과제 3 — `Time.java`

오버로딩과 생성자를 같이 쓰는 과제입니다.

- 인스턴스 변수: `int hour, minute, second`
- 생성자 ① `Time(int hour, int minute, int second)`
- 생성자 ② `Time(int totalSeconds)` — **step01 과제 2(TimeConvert)를 여기로 옮기세요**
- `void print()` — `02:46:40` 형태로 출력

`Time t = new Time(10000);` 이 `02:46:40` 을 찍으면 성공입니다.

> 두 생성자의 매개변수 **개수**가 달라서 오버로딩이 성립합니다.

### 과제 4 — `Calculator.java` 오버로딩

step05의 `Calculator` 에 오버로딩을 추가하세요.

- `int add(int a, int b)`
- `double add(double a, double b)`
- `int add(int a, int b, int c)`
- `int add(int[] arr)` — 배열 전체 합

`add(1, 2)` / `add(1.5, 2.5)` / `add(1, 2, 3)` / `add(new int[]{1,2,3,4})` 를
모두 호출해 어느 게 불리는지 확인하세요.

### 과제 5 — `BankAccount.java` 개선 (step05 과제 5 이어서)

- 생성자 `BankAccount(String owner, int balance)`
- 생성자 `BankAccount(String owner)` — 잔액 0부터 시작
- **클래스 변수** `static int accountCount` — 계좌가 만들어질 때마다 1 증가
  (생성자 안에서 증가시키세요)

`BankAccountTest` 에서 계좌 3개를 만들고 `BankAccount.accountCount` 를 출력해
3이 나오는지 확인하세요.

## 5. 자가 점검

- [ ] 오버로딩 성립 조건을 말할 수 있고, 반환 타입이 조건이 아님을 안다
- [ ] 생성자의 특징 3가지를 쓸 수 있다
- [ ] 기본 생성자가 언제 안 생기는지 안다
- [ ] `this` 와 `this()` 를 구분해서 쓸 수 있다
- [ ] `this()` 가 생성자의 첫 줄에만 올 수 있는 이유를 안다
- [ ] 인스턴스 변수의 초기화 순서를 말할 수 있다
- [ ] `static` 초기화 블럭이 한 번만 실행되는 이유를 안다
