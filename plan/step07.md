# step07 — 상속과 오버라이딩

**책 범위: 7장 「객체지향개념 II」 1~3절**

6장이 "클래스 하나를 잘 만드는 법"이었다면, 7장은 **"클래스 여러 개를 엮는 법"** 입니다.
자바를 어렵게 느끼는 대부분의 이유가 여기 있고, 넘기면 뒤가 수월해집니다.

---

## 1. 읽을 것

- 7.1 상속
  - 상속이란, 클래스 간의 관계 — **상속(is-a)** vs **포함(has-a)**
  - 단일 상속, **`Object` 클래스** (모든 클래스의 조상)
- 7.2 오버라이딩
  - 오버라이딩의 조건, **오버로딩 vs 오버라이딩**
  - `super`, `super()`
- 7.3 package 와 import
  - 패키지 선언, `import` 문, `static import`

### 함께 읽을 글 ([Inpa Dev](https://inpa.tistory.com/category/Language/Java))

- [☕ 자바 Object 클래스와 메서드 오버라이딩](https://inpa.tistory.com/entry/JAVA-%E2%98%95-Object-%ED%81%B4%EB%9E%98%EC%8A%A4%EC%99%80-%EC%83%81%EC%9C%84-%EB%A9%94%EC%84%9C%EB%93%9C-%EC%9E%AC%EC%A0%95%EC%9D%98-%ED%99%9C%EC%9A%A9-%EC%B4%9D%EC%A0%95%EB%A6%AC)
  — 모든 클래스의 조상. 오버라이딩의 출발점.
- [☕ 자바 toString 오버라이딩 - 완벽 이해하기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-toString-%EB%A9%94%EC%84%9C%EB%93%9C-%EC%9E%AC%EC%A0%95%EC%9D%98-%EC%99%84%EB%B2%BD-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0)
  — step05에서 객체를 `println` 했을 때 이상한 글자가 나온 이유.
- [☕ 자바 equals / hashCode 오버라이딩 - 완벽 이해하기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-equals-hashCode-%EB%A9%94%EC%84%9C%EB%93%9C-%EA%B0%9C%EB%85%90-%ED%99%9C%EC%9A%A9-%ED%8C%8C%ED%97%A4%EC%B9%98%EA%B8%B0)
  — **step02 문제 6(`==` vs `equals`)이 여기서 완결됩니다.**
- [☕ 자바 객체의 hashCode는 고유하지 않다 ❌](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B0%9D%EC%B2%B4%EC%9D%98-hashCode%EB%8A%94-%EA%B3%A0%EC%9C%A0%ED%95%98%EC%A7%80-%EC%95%8A%EB%8B%A4-%E2%9D%8C)
  — 위 글의 함정편.
- [☕ 자바 clone 메서드 재정의 (얕은 복사 & 깊은 복사)](https://inpa.tistory.com/entry/JAVA-%E2%98%95-Object-%ED%81%B4%EB%9E%98%EC%8A%A4-clone-%EB%A9%94%EC%84%9C%EB%93%9C-%EC%96%95%EC%9D%80-%EB%B3%B5%EC%82%AC-%EA%B9%8A%EC%9D%80-%EB%B3%B5%EC%82%AC)
  — 참조형 복사의 함정. step04 문제 6의 연장.

## 2. 손으로 정리할 것

1. **상속 vs 포함**을 문장으로 구분하는 법.
   ```
   상속(is-a)   "고양이는 동물이다"      class Cat extends Animal
   포함(has-a)  "자동차는 엔진을 가진다"  class Car { Engine engine; }
   ```
   헷갈리면 **"~는 ~이다" 가 자연스러운지** 읽어보세요. 아니면 포함입니다.

2. **오버로딩 vs 오버라이딩 표.** 이름이 비슷해서 계속 헷갈립니다.

   | | 오버로딩 | 오버라이딩 |
   |---|---|---|
   | 뜻 | 새로 정의 | 덮어쓰기 |
   | 어디서 | 같은 클래스 안 | 상속받은 클래스에서 |
   | 이름 | 같다 | 같다 |
   | 매개변수 | **달라야** 한다 | **같아야** 한다 |
   | 반환 타입 | 상관없다 | **같아야** 한다 |

3. **오버라이딩 조건 3개.** (선언부, 접근제어자, 예외)

4. **`super` 와 `super()`.** `this` / `this()` 와 짝지어 4개를 한 표로.

5. **단일 상속.** 자바가 다중 상속을 막은 이유를 한 줄로.

## 3. 문제

### 문제 1 — 상속과 멤버
```java
class Parent {
    int x = 10;
    void print() { System.out.println("Parent"); }
}
class Child extends Parent {
    int y = 20;
}

Child c = new Child();
System.out.println(c.x);
System.out.println(c.y);
c.print();
```
`Child` 에는 `x` 도 `print()` 도 없는데 왜 되나요?

### 문제 2 — 오버라이딩
```java
class Parent {
    void print() { System.out.println("Parent"); }
}
class Child extends Parent {
    void print() { System.out.println("Child"); }
}

Child c = new Child();
c.print();
```
그리고 `Child` 의 `print()` 안에서 **부모 것도 같이** 부르려면?

### 문제 3 — 생성자 호출 순서 ⭐
```java
class A {
    A() { System.out.println("A 생성자"); }
}
class B extends A {
    B() { System.out.println("B 생성자"); }
}
new B();
```
`B()` 안에 `super()` 를 안 썼는데도 "A 생성자" 가 찍힙니다. 왜인가요?

### 문제 4 — super() 가 자동으로 안 되는 경우
```java
class A {
    A(int x) { System.out.println("A: " + x); }
}
class B extends A {
    B() { System.out.println("B"); }
}
```
컴파일 에러입니다. step06 문제 3(기본 생성자)과 같은 이야기입니다. 이유와 고치는 법은?

### 문제 5 — Object 의 메서드
```java
class Point { int x = 1, y = 2; }

Point p = new Point();
System.out.println(p);
System.out.println(p.toString());
```
두 줄의 결과가 같습니다. 왜일까요? 그리고 출력된 이상한 글자(`Point@1b6d3586`)는 무슨 뜻인가요?

### 문제 6 — equals ⭐
```java
class Point {
    int x, y;
    Point(int x, int y) { this.x = x; this.y = y; }
}

Point p1 = new Point(1, 2);
Point p2 = new Point(1, 2);
System.out.println(p1 == p2);
System.out.println(p1.equals(p2));
```
둘 다 `false` 입니다. **`equals` 를 썼는데도 `false` 인 이유**를 설명하세요.
(힌트: `Object` 의 `equals` 는 원래 무엇을 비교하나요?)

### 문제 7 — 오버라이딩 조건 위반
```java
class Parent {
    public int getValue() { return 1; }
}
class Child extends Parent {
    ① int getValue() { return 2; }
    ② public double getValue() { return 2.0; }
    ③ public int getValue(int a) { return 2; }
}
```
①②③ 각각 무엇이 되나요? (오버라이딩 / 오버로딩 / 에러)

### 문제 8 — 참조변수의 타입
```java
class Parent { void a() {} }
class Child extends Parent { void a() {} void b() {} }

Parent p = new Child();
p.a();
p.b();
```
한 줄이 컴파일 에러입니다. 어느 줄이고 왜인가요?
**이게 step08(다형성) 전체의 핵심 질문입니다.**

## 4. 손코딩 과제

### 과제 1 — `Animal` 계층

```
Animal (name, age / speak(), printInfo())
 ├── Dog   speak() 오버라이딩 → "멍멍"
 ├── Cat   speak() 오버라이딩 → "야옹"
 └── Bird  speak() 오버라이딩 → "짹짹" + fly() 추가
```

- `Animal` 에 생성자 `Animal(String name, int age)`
- 각 자식 클래스는 `super(...)` 로 부모 생성자 호출
- `AnimalTest` 에서 셋을 만들어 `speak()` 와 `printInfo()` 호출

파일 5개(`Animal`, `Dog`, `Cat`, `Bird`, `AnimalTest`)로 나누세요.

### 과제 2 — `toString()` 오버라이딩

step05·06에서 만든 `Student` 에 `toString()` 을 추가하세요.

```java
@Override
public String toString() {
    return name + " / 총점 " + getTotal();
}
```

그다음 `System.out.println(student);` 를 해보세요. **`printInfo()` 없이도** 정보가 찍힙니다.

> `@Override` 는 "이건 오버라이딩입니다" 라고 컴파일러에게 알리는 표시입니다.
> 오타로 오버라이딩이 안 되면 **컴파일 에러로 잡아줍니다.** 습관을 들이세요.

### 과제 3 — `equals()` 오버라이딩

`Point` 클래스를 만들고, `x` 와 `y` 가 같으면 같은 점으로 취급하도록 `equals` 를 오버라이딩하세요.

```java
@Override
public boolean equals(Object obj) {
    // obj 가 Point 인지 확인하고, x·y 를 비교
}
```

오버라이딩 전후로 `p1.equals(p2)` 결과가 달라지는 걸 확인하세요.

> `Object` 타입으로 받는 이유는 step08(다형성)에서 풀립니다. 지금은 형태만 따라 쓰세요.

### 과제 4 — 상속 vs 포함 판단

아래를 상속과 포함 중 무엇으로 만들지 정하고, 그렇게 구현하세요. **판단 근거를 주석으로.**

1. `Circle` 과 `Shape`
2. `Car` 와 `Engine`
3. `Student` 와 `Person`
4. `Computer` 와 `CPU`

### 과제 5 — 패키지 실습 `com/example/` (선택)

7.3 절 실습입니다. 폴더 구조로 패키지를 만들어보세요.

```
src/step07/com/example/Hello.java     ← package com.example;
```

`./run` 이 `package` 선언을 자동으로 인식하므로 그냥 평소처럼 돌리면 됩니다.

```bash
./run step07/com/example/Hello.java
```

## 5. 자가 점검

- [ ] 상속(is-a)과 포함(has-a)을 문장으로 구분할 수 있다
- [ ] 오버로딩과 오버라이딩을 표로 비교할 수 있다
- [ ] 자식 생성자에서 `super()` 가 자동 호출되는 것을 안다
- [ ] 부모에 기본 생성자가 없으면 왜 에러가 나는지 안다
- [ ] 모든 클래스가 `Object` 를 상속한다는 걸 안다
- [ ] `Object` 의 `equals` 가 원래 무엇을 비교하는지 안다
- [ ] `@Override` 를 왜 붙이는지 안다
