# step08 — 제어자와 다형성

**책 범위: 7장 「객체지향개념 II」 4~5절**

step07 문제 8에서 막힌 것 — `Parent p = new Child();` 가 왜 되는지, 그런데 왜
`p.b()` 는 안 되는지. 그 답이 **다형성**이고 이 장의 전부입니다.

다형성은 객체지향에서 가장 중요한 개념이고, 실무 코드가 이걸로 굴러갑니다.

---

## 1. 읽을 것

- 7.4 제어자
  - `static`, `final`, `abstract`
  - **접근 제어자** (`private`, `default`, `protected`, `public`)
  - **캡슐화** — 접근 제어자를 쓰는 이유
  - 제어자의 조합 (같이 못 쓰는 조합)
- 7.5 다형성
  - 다형성이란, **참조변수의 형변환** (업캐스팅 / 다운캐스팅)
  - **`instanceof` 연산자**
  - 매개변수의 다형성
  - 여러 종류의 객체를 배열로 다루기

### 함께 읽을 글 ([Inpa Dev](https://inpa.tistory.com/category/Language/Java))

- [☕ JAVA 업캐스팅 & 다운캐스팅 - 완벽 이해하기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%97%85%EC%BA%90%EC%8A%A4%ED%8C%85-%EB%8B%A4%EC%9A%B4%EC%BA%90%EC%8A%A4%ED%8C%85-%ED%95%9C%EB%B0%A9-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0)
  — **이 단계의 핵심 글.** step01의 기본형 형변환과 구조가 같다는 게 보입니다.
- [☕ 클래스는 언제 메모리에 로딩 & 초기화 되는가 ❓](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%ED%81%B4%EB%9E%98%EC%8A%A4%EB%8A%94-%EC%96%B8%EC%A0%9C-%EB%A9%94%EB%AA%A8%EB%A6%AC%EC%97%90-%EB%A1%9C%EB%94%A9-%EC%B4%88%EA%B8%B0%ED%99%94-%EB%90%98%EB%8A%94%EA%B0%80-%E2%9D%93)
  — `static` 제어자의 동작 배경.

## 2. 손으로 정리할 것

1. **접근 제어자 4개 표.** 좁은 것부터 넓은 것 순으로.

   | 제어자 | 같은 클래스 | 같은 패키지 | 자식 클래스 | 전체 |
   |---|---|---|---|---|
   | `private` | ○ | × | × | × |
   | (default) | ○ | ○ | × | × |
   | `protected` | ○ | ○ | ○ | × |
   | `public` | ○ | ○ | ○ | ○ |

2. **캡슐화 공식.** 이 조합을 통째로 외우세요.
   ```
   필드는 private  +  getter/setter 는 public
   ```
   왜 그러는지 한 줄로. (힌트: 값을 넣기 전에 **검사**할 자리를 만든다)

3. **형변환 방향 그림.** step01의 그 화살표와 나란히 놓고 비교하세요.
   ```
   기본형:  byte → short → int → long → float → double
   참조형:  Child ──────▶ Parent          (업캐스팅, 자동)
           Child ◀────── Parent          (다운캐스팅, (타입) 필요)
   ```
   **작은 것 → 큰 것이 자동**이라는 구조가 같습니다.

4. **참조변수 타입이 정하는 것 vs 실제 객체가 정하는 것.**
   ```
   Parent p = new Child();
          ↑            ↑
   쓸 수 있는 멤버 결정   실제 실행되는 메서드 결정
   ```
   이 두 줄이 다형성의 전부입니다. 크게 써두세요.

## 3. 문제

### 문제 1 — 업캐스팅
```java
class Parent { void a() { System.out.println("Parent.a"); } }
class Child extends Parent {
    void a() { System.out.println("Child.a"); }
    void b() { System.out.println("Child.b"); }
}

Parent p = new Child();
p.a();
p.b();
```
`p.a()` 는 뭐가 찍히고, `p.b()` 는 왜 에러인가요?

### 문제 2 — 반대 방향
```java
Child c = new Parent();
```
이건 왜 안 되나요? 한 문장으로.

### 문제 3 — 다운캐스팅
```java
Parent p = new Child();
Child c = p;
Child c2 = (Child) p;
c2.b();
```
2번째 줄은 에러, 3번째 줄은 됩니다. 차이는?

### 문제 4 — 잘못된 다운캐스팅
```java
Parent p = new Parent();
Child c = (Child) p;
```
**컴파일은 통과하고 실행할 때 죽습니다.** 예외 이름을 쓰세요.
그리고 이걸 막으려면 뭘 써야 하나요?

### 문제 5 — instanceof
```java
Parent p1 = new Child();
Parent p2 = new Parent();

System.out.println(p1 instanceof Parent);
System.out.println(p1 instanceof Child);
System.out.println(p2 instanceof Parent);
System.out.println(p2 instanceof Child);
```

### 문제 6 — 멤버 접근 규칙 ⭐
```java
class Parent {
    int x = 10;
    void print() { System.out.println("Parent"); }
}
class Child extends Parent {
    int x = 20;
    void print() { System.out.println("Child"); }
}

Parent p = new Child();
System.out.println(p.x);
p.print();
```
**변수와 메서드의 결과가 다릅니다.** 이게 이 장에서 제일 헷갈리는 부분입니다.
```
변수(필드)  → 참조변수 타입을 따라간다
메서드      → 실제 객체를 따라간다
```

### 문제 7 — 접근 제어자
```java
class Account {
    private int balance = 1000;
}

Account a = new Account();
System.out.println(a.balance);
```
컴파일 에러입니다. `balance` 를 읽으려면 어떻게 해야 하나요?

### 문제 8 — 제어자 조합
아래 중 **말이 안 되는 조합**을 고르고 이유를 쓰세요.
```
① public static
② private abstract
③ final abstract
④ static final
⑤ private static
```

### 문제 9 — 매개변수의 다형성
```java
static void feed(Animal a) { a.eat(); }

feed(new Dog());
feed(new Cat());
feed(new Bird());
```
`Dog`, `Cat`, `Bird` 각각에 대해 메서드를 따로 만들지 않아도 됩니다.
**이게 다형성의 실용적 가치**입니다. 왜 되는지 한 줄로 쓰세요.

## 4. 손코딩 과제

### 과제 1 — `Animal` 배열 (step07 과제 1 이어서)

step07에서 만든 `Dog`, `Cat`, `Bird` 를 **한 배열에** 담으세요.

```java
Animal[] animals = new Animal[3];
animals[0] = new Dog("바둑이", 3);
animals[1] = new Cat("나비", 2);
animals[2] = new Bird("짹이", 1);

for (Animal a : animals) {
    a.speak();      // 각각 다른 소리가 난다
}
```

**타입이 다른 객체들이 한 배열에 들어가는 것**, 그리고 **같은 코드가 객체마다 다르게 도는 것**
— 이 두 가지를 눈으로 확인하는 게 과제의 목적입니다.

### 과제 2 — `instanceof` 로 분기

위 배열을 돌면서, **`Bird` 일 때만** `fly()` 를 부르세요.

```java
for (Animal a : animals) {
    a.speak();
    if (a instanceof Bird) {
        ((Bird) a).fly();     // 다운캐스팅이 필요한 이유를 주석으로
    }
}
```

괄호 위치를 주의하세요. `(Bird) a.fly()` 로 쓰면 다릅니다.

### 과제 3 — 캡슐화 `Account.java`

step05·06의 `BankAccount` 를 캡슐화하세요.

- 모든 필드를 `private` 으로
- `getBalance()` 는 있고 **`setBalance()` 는 만들지 마세요**
- 잔액 변경은 `deposit()` / `withdraw()` 로만
- `deposit()` 에서 **0 이하 금액은 거부**

`AccountTest` 에서 `account.balance = 999999;` 를 시도해보고 컴파일 에러를 확인하세요.
**그게 캡슐화가 일하는 순간입니다.**

### 과제 4 — `Shape` 계층 + 다형성

```
Shape (getArea(), getName())
 ├── Circle      (radius)
 ├── Rectangle   (width, height)
 └── Triangle    (base, height)
```

- 각 도형이 `getArea()` 를 오버라이딩
- `Shape[] shapes` 배열에 넣고 반복문으로 넓이 출력
- **넓이 합계**도 구하세요

```
원: 78.54
사각형: 20.00
삼각형: 12.50
합계: 111.04
```

> 도형이 하나 더 늘어도 **반복문 코드는 안 바뀝니다.** 그게 다형성의 값어치입니다.

### 과제 5 — `printAll` 메서드

과제 4의 배열을 받아서 전부 출력하는 **메서드 하나**를 만드세요.

```java
static void printAll(Shape[] shapes) { ... }
```

매개변수 타입이 `Shape[]` 인데 안에는 `Circle`·`Rectangle`·`Triangle` 이 섞여 있습니다.
**매개변수의 다형성**을 직접 쓰는 과제입니다.

## 5. 자가 점검

- [ ] 접근 제어자 4개의 범위를 표로 그릴 수 있다
- [ ] 캡슐화가 무엇을 막아주는지 설명할 수 있다
- [ ] `Parent p = new Child();` 가 되는 이유를 안다
- [ ] 업캐스팅은 자동, 다운캐스팅은 명시적인 이유를 안다
- [ ] `ClassCastException` 을 막는 방법(`instanceof`)을 안다
- [ ] 변수는 참조변수 타입, 메서드는 실제 객체를 따른다는 걸 안다
- [ ] 여러 자식 객체를 부모 타입 배열에 담을 수 있다
