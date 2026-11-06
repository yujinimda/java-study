# step09 — 추상클래스 · 인터페이스 · 내부클래스

**책 범위: 7장 「객체지향개념 II」 6~8절**

step08 과제 4에서 `Shape` 를 만들 때 이런 생각이 들었을 겁니다.

> "`Shape` 자체의 넓이는 뭐지? 그냥 0을 리턴해야 하나?"

그 어색함의 답이 **추상클래스**입니다. 그리고 "상속은 하나만 되는데 여러 성격을 주고 싶다"
는 문제의 답이 **인터페이스**입니다.

**이 단계가 객체지향의 마지막이자 목표 지점입니다.**

---

## 1. 읽을 것

- 7.6 추상클래스
  - 추상클래스란, **추상 메서드**, 추상클래스의 작성
- 7.7 인터페이스
  - 인터페이스의 선언·상속·구현, **다중 상속**
  - **인터페이스를 이용한 다형성**, 인터페이스의 장점
  - **디폴트 메서드와 static 메서드** (자바 8 이후)
- 7.8 내부 클래스
  - 내부 클래스의 종류와 특징, 선언, **익명 클래스**

### 함께 읽을 글 ([Inpa Dev](https://inpa.tistory.com/category/Language/Java))

- [☕ 추상 클래스(Abstract) 용도 완벽 이해하기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%B6%94%EC%83%81-%ED%81%B4%EB%9E%98%EC%8A%A4Abstract-%EC%9A%A9%EB%8F%84-%EC%99%84%EB%B2%BD-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0)
  — 문법보다 **용도**에 초점을 둔 글.
- [☕ 인터페이스(Interface) 문법 & 활용 - 완벽 가이드](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%9D%B8%ED%84%B0%ED%8E%98%EC%9D%B4%EC%8A%A4Interface%EC%9D%98-%EC%A0%95%EC%84%9D-%ED%83%84%ED%83%84%ED%95%98%EA%B2%8C-%EA%B0%9C%EB%85%90-%EC%A0%95%EB%A6%AC)
- [☕ 인터페이스 vs 추상클래스 용도 차이점 - 완벽 이해](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%9D%B8%ED%84%B0%ED%8E%98%EC%9D%B4%EC%8A%A4-vs-%EC%B6%94%EC%83%81%ED%81%B4%EB%9E%98%EC%8A%A4-%EC%B0%A8%EC%9D%B4%EC%A0%90-%EC%99%84%EB%B2%BD-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0)
  — **둘 다 배운 뒤 이 글로 정리하면 깔끔합니다.**
- [☕ 내부 클래스(Inner Class) 장점 & 종류 총정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EB%82%B4%EB%B6%80-%ED%81%B4%EB%9E%98%EC%8A%A4Inner-Class-%EC%9E%A5%EC%A0%90-%EC%A2%85%EB%A5%98)
- [☕ 내부 클래스는 static 으로 선언 안하면 큰일 난다](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%9E%90%EB%B0%94%EC%9D%98-%EB%82%B4%EB%B6%80-%ED%81%B4%EB%9E%98%EC%8A%A4%EB%8A%94-static-%EC%9C%BC%EB%A1%9C-%EC%84%A0%EC%96%B8%ED%95%98%EC%9E%90)
  — 위 글의 실전편 (메모리 누수).
- [☕ 익명 클래스(Anonymous Class) 사용법 총정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%9D%B5%EB%AA%85-%ED%81%B4%EB%9E%98%EC%8A%A4Anonymous-Class-%EC%82%AC%EC%9A%A9%EB%B2%95-%EB%A7%88%EC%8A%A4%ED%84%B0%ED%95%98%EA%B8%B0)
  — 14장 람다로 이어지는 다리.

## 2. 손으로 정리할 것

1. **추상클래스를 쓰는 이유** 두 가지.
   - 자식이 **반드시 구현하도록 강제**한다
   - 그 자체로는 **객체를 만들 수 없게** 막는다 (`new Shape()` 금지)

2. **인터페이스 vs 추상클래스 표.**

   | | 추상클래스 | 인터페이스 |
   |---|---|---|
   | 키워드 | `abstract class` | `interface` |
   | 상속/구현 | `extends` (하나만) | `implements` (**여러 개**) |
   | 필드 | 일반 필드 가능 | `public static final` 만 |
   | 메서드 | 일반 메서드 가능 | 추상 메서드 (+ default, static) |
   | 관계 | **is-a** (같은 종류) | **can-do** (할 수 있는 것) |

3. **"is-a" vs "can-do"** 로 고르는 법.
   ```
   Dog is-a Animal        → 추상클래스 Animal
   Dog can-do Swimmable   → 인터페이스 Swimmable
   ```

4. **내부 클래스 4종** 이름만 적어두세요.
   인스턴스 / static / 지역 / 익명

## 3. 문제

### 문제 1 — 추상클래스는 객체를 못 만든다
```java
abstract class Shape {
    abstract double getArea();
    void print() { System.out.println("넓이: " + getArea()); }
}

Shape s = new Shape();
```
컴파일 에러입니다. 이유는? 그리고 `Shape` 에 **일반 메서드 `print()` 도 있는 것**은
문제가 없나요?

### 문제 2 — 추상 메서드 미구현
```java
abstract class Shape { abstract double getArea(); }
class Circle extends Shape { }
```
컴파일 에러입니다. 고치는 방법 **2가지**를 쓰세요.

### 문제 3 — 추상클래스도 타입이 된다
```java
abstract class Shape { abstract double getArea(); }
class Circle extends Shape { double getArea() { return 3.14; } }

Shape s = new Circle();
System.out.println(s.getArea());
```
`new Shape()` 는 안 되는데 `Shape s` 는 됩니다. 모순 아닌가요? 설명하세요.

### 문제 4 — 인터페이스의 기본값
```java
interface Movable {
    int SPEED = 10;
    void move();
}
```
`SPEED` 앞에 아무것도 안 썼는데 자동으로 붙는 제어자 3개는? `move()` 에 붙는 것은?

### 문제 5 — 다중 구현
```java
interface Swimmable { void swim(); }
interface Flyable { void fly(); }

class Duck extends Animal implements Swimmable, Flyable { ... }
```
`extends` 는 하나인데 `implements` 는 여러 개입니다. **왜 인터페이스는 여러 개가 되나요?**
(힌트: 구현 내용이 없으면 충돌할 게 없다)

### 문제 6 — 인터페이스 타입 참조
```java
Swimmable s = new Duck();
s.swim();
s.fly();
```
한 줄이 에러입니다. step08 문제 1과 같은 규칙입니다.

### 문제 7 — 익명 클래스
```java
interface Greeting { void hello(); }

Greeting g = new Greeting() {
    public void hello() { System.out.println("안녕"); }
};
g.hello();
```
인터페이스는 객체를 못 만드는데 `new Greeting()` 이 있습니다. 무슨 일이 일어난 건가요?

### 문제 8 — 내부 클래스 접근
```java
class Outer {
    private int x = 10;
    class Inner {
        void print() { System.out.println(x); }
    }
}
```
`x` 가 `private` 인데 `Inner` 에서 읽힙니다. 왜일까요?

### 문제 9 — 선택 판단
아래를 **추상클래스와 인터페이스 중 무엇으로** 만들지 정하고 이유를 쓰세요.

1. 모든 도형이 가진 `getArea()`
2. 일부 동물만 할 수 있는 `swim()`
3. 모든 직원이 가진 `name`, `salary` 와 `calculatePay()`
4. 저장 가능한 것들의 `save()`

## 4. 손코딩 과제

### 과제 1 — `Shape` 를 추상클래스로 (step08 과제 4 개선)

step08의 `Shape` 를 `abstract` 로 바꾸세요.

```java
abstract class Shape {
    abstract double getArea();          // 구현 없음 — 자식이 반드시 만들어야 함
    abstract String getName();
    void print() {                       // 일반 메서드는 가질 수 있다
        System.out.println(getName() + ": " + getArea());
    }
}
```

바뀐 점을 확인하세요.
- `new Shape()` 가 이제 **컴파일 에러**입니다
- `getArea()` 를 빼먹은 자식도 **컴파일 에러**입니다
- `print()` 는 자식마다 안 만들어도 됩니다 (공통 코드)

> step08에서 `Shape` 에 억지로 `return 0;` 을 넣었다면, 그게 왜 나쁜 설계였는지 보입니다.

### 과제 2 — `Movable` 인터페이스

```java
interface Movable { void move(); }
```

`Car`, `Bicycle`, `Airplane` 이 각각 구현하게 하고, `Movable[]` 배열에 담아
반복문으로 `move()` 를 호출하세요.

**세 클래스는 서로 상속 관계가 아닙니다.** 그런데도 한 배열에 담깁니다 — 이게 인터페이스의 힘입니다.

### 과제 3 — 다중 구현 `Duck`

```
Animal (추상클래스)     ← step07의 그것
Swimmable (인터페이스)  swim()
Flyable (인터페이스)    fly()

Duck extends Animal implements Swimmable, Flyable
Fish extends Animal implements Swimmable
Eagle extends Animal implements Flyable
```

`AnimalTest` 에서 `Animal[]` 배열에 셋을 담고, 돌면서
- 전부 `speak()`
- `instanceof Swimmable` 이면 `swim()`
- `instanceof Flyable` 이면 `fly()`

step08의 `instanceof` 가 여기서 제대로 쓰입니다.

### 과제 4 — 인터페이스로 갈아끼우기 ⭐

인터페이스의 진짜 가치를 보는 과제입니다.

```java
interface Payment {
    void pay(int amount);
}
```

`CardPayment`, `CashPayment`, `KakaoPayment` 를 만들고, 아래 메서드를 작성하세요.

```java
static void checkout(Payment p, int amount) {
    p.pay(amount);
}
```

`checkout(new CardPayment(), 10000);` 처럼 호출합니다.

**결제 수단을 하나 더 추가해도 `checkout` 은 안 고쳐집니다.** 직접 하나 더 만들어서
확인하세요. 실무에서 인터페이스를 쓰는 이유가 이것입니다.

### 과제 5 — 익명 클래스

과제 2의 `Movable` 을 구현하는 클래스를 **파일로 만들지 않고** 그 자리에서 만드세요.

```java
Movable m = new Movable() {
    public void move() { System.out.println("이름 없는 탈것이 움직인다"); }
};
m.move();
```

한 번만 쓸 구현을 위해 파일을 만들지 않아도 되는 방법입니다.
**14장 람다가 이걸 더 짧게 쓰는 문법입니다.**

### 과제 6 — 종합: `Library` (선택)

객체지향 전체를 쓰는 마무리 과제입니다.

```
추상클래스 Item (title, id / abstract String getType(), void printInfo())
 ├── Book     (author, pages)
 ├── DVD      (runtime)
 └── Magazine (issueNumber)

인터페이스 Borrowable (borrow(), returnItem(), isAvailable())
  → Book 과 DVD 만 구현 (Magazine 은 대출 불가)
```

`Library` 클래스가 `Item[]` 을 가지고,
- 전체 목록 출력
- 대출 가능한 것만 출력 (`instanceof Borrowable`)
- 대출/반납 처리

step04(배열) ~ step09가 전부 들어갑니다.

## 5. 자가 점검

- [ ] 추상클래스로 객체를 못 만드는 이유를 안다
- [ ] 추상클래스가 일반 메서드를 가질 수 있다는 걸 안다
- [ ] 인터페이스의 필드에 자동으로 붙는 제어자를 안다
- [ ] 인터페이스는 여러 개 구현할 수 있는 이유를 설명할 수 있다
- [ ] is-a / can-do 로 추상클래스와 인터페이스를 고를 수 있다
- [ ] 인터페이스 타입 배열에 서로 무관한 클래스를 담을 수 있다
- [ ] 익명 클래스가 무엇을 대신하는지 안다

---

## 여기까지 오면

**객체지향(1~7장)이 끝납니다.** 처음 목표였던 지점입니다.

step00에서 "지금은 안 읽힌다"고 했던 이 한 줄을 다시 열어보세요.

```bash
unzip -p $JAVA_HOME/lib/src.zip 'java.base/java/lang/String.java' | less
```

```java
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence, ...
```

`final` 클래스라는 것(step08), `implements` 가 여러 개라는 것(step09) — 이제 읽힙니다.

다음은 step10(8장 예외처리)입니다.
