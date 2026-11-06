# step05 — 클래스와 객체, 변수와 메서드

**책 범위: 6장 「객체지향개념 I」 1~3절**

여기서부터 문제 형태가 바뀝니다. 지금까지는 "출력을 맞혀라" 였는데,
이제부터는 **"스펙을 줄 테니 클래스를 설계해라"** 입니다.

step04 과제 4에서 `names[]` 와 `scores[]` 를 나란히 놓고 인덱스로 짝지었던 걸 기억하세요.
그게 불편했던 이유가 이 장의 출발점입니다.

---

## 1. 읽을 것

- 6.1 객체지향언어 (역사·특징은 가볍게)
- 6.2 클래스와 객체 (정의와 의미, **속성과 기능**, 인스턴스의 생성과 사용)
- 6.3 변수와 메서드
  - **선언 위치에 따른 변수의 종류** (클래스변수 / 인스턴스변수 / 지역변수) ← 제일 중요
  - 메서드 (선언부와 구현부, 호출, `return`, 호출 스택)
  - **기본형 매개변수 vs 참조형 매개변수** ← step04 문제 6의 연장
  - 클래스 메서드(`static`)와 인스턴스 메서드

### 함께 읽을 글 ([Inpa Dev](https://inpa.tistory.com/category/Language/Java))

- [☕ 자바 객체 지향(OOP) 클래스 문법 - 한방 가이드](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B0%9D%EC%B2%B4-%EC%A7%80%ED%96%A5OOP-%ED%81%B4%EB%9E%98%EC%8A%A4-%EB%AC%B8%EB%B2%95-%F0%9F%92%AF-%EC%B4%9D%EC%A0%95%EB%A6%AC)
— 6장 전체에 대응하는 핵심 글.
- [☕ 그림으로 보는 자바 코드의 메모리 영역 (스택 &amp; 힙)](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B7%B8%EB%A6%BC%EC%9C%BC%EB%A1%9C-%EB%B3%B4%EB%8A%94-%EC%9E%90%EB%B0%94-%EC%BD%94%EB%93%9C%EC%9D%98-%EB%A9%94%EB%AA%A8%EB%A6%AC-%EC%98%81%EC%97%AD%EC%8A%A4%ED%83%9D-%ED%9E%99)
— **여기서 필수.** 객체가 어디에 생기고 참조변수가 뭘 담는지.
- [☕ 자바는 Call by reference 개념이 없다 ❓](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%9E%90%EB%B0%94%EB%8A%94-Call-by-reference-%EA%B0%9C%EB%85%90%EC%9D%B4-%EC%97%86%EB%8B%A4-%E2%9D%93)
— 메서드에 객체를 넘길 때 무슨 일이 일어나는지. 문제 5와 짝입니다.
- [☕ 자바 String 타입 특징 이해하기 (String Pool)](https://inpa.tistory.com/entry/JAVA-%E2%98%95-String-%ED%83%80%EC%9E%85-%ED%95%9C-%EB%88%88%EC%97%90-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0-String-Pool-%EB%AC%B8%EC%9E%90%EC%97%B4-%EB%B9%84%EA%B5%90)
— step02 문제 6에서 미뤄뒀던 `s1 == s2` 의 답이 이제 읽힙니다.

## 2. 손으로 정리할 것

1. **클래스 = 설계도, 객체 = 그걸로 만든 것.** 붕어빵 틀과 붕어빵.
 같은 클래스로 만든 객체 3개가 각각 다른 값을 가질 수 있다는 걸 그림으로.
2. **변수 3종 표.** 이 표가 6장의 뼈대입니다.
  
  | 종류                | 선언 위치        | 생성 시점       | 사는 곳     |
  | ----------------- | ------------ | ----------- | -------- |
  | 클래스 변수 (`static`) | 클래스 안, 메서드 밖 | 클래스가 로딩될 때  | 메서드 영역   |
  | 인스턴스 변수           | 클래스 안, 메서드 밖 | `new` 할 때마다 | 힙 (객체 안) |
  | 지역 변수             | 메서드 안        | 메서드가 실행될 때  | 스택       |
  

   `**static` 이 붙었냐 안 붙었냐**가 위 둘을 가릅니다.
3. **메서드 구조**를 부위별로 이름 붙여 쓰세요.
  ```java
   public int add(int a, int b) { return a + b; }
          ─┬─  ─┬─  ─────┬─────   ────┬────
        반환타입 이름   매개변수        구현부
  ```

   `void` 는 무슨 뜻인가? `return` 이 없어도 되는 경우는?  
    
  void: 돌려 줄 값 없음 화면에 찍는게 목적이지 값을 받아올 필요가 없기 때문에 물론 이때로 리턴을 쓸수있는데 이땐 반환한다는 뜻은 아니고 여기서 끝내라이다.  
  return이 필요없는 이유는 반환타입이 void일 경우만 그외에는 반드시 있어야한다  
  

1. `new` **가 하는 일** 두 가지. (힌트: 힙에 무엇을 만들고, 무엇을 돌려주는가):  
  
힙에 원본 객체를 만든다, 그 객체의 주소를 돌려준다.

## 3. 문제

### 문제 1 — 변수의 종류 구분

```java
public class Test {
    int a = 1;
    static int b = 2;

    public void method() {
        int c = 3;
    }
}
```

`a`, `b`, `c` 는 각각 무슨 변수인가요? 셋 중 **객체를 만들지 않고도 쓸 수 있는 것**은?

a는 인스턴스 변수

b는 클래스 변수

c는 지역변수  
  
객체를 만들지 않고도 쓸수있는건 클래스 변수

### 문제 2 — 객체마다 따로냐 같이 쓰냐

```java
class Counter {
    int instanceCount = 0; < 힙영역 (객체 안)
    static int staticCount = 0; < 메서드 영역
}

Counter c1 = new Counter(); 뉴 객체
Counter c2 = new Counter(); 뉴 객체
c1.instanceCount++; 1
c1.staticCount++; 1
c2.instanceCount++; 1
c2.staticCount++; 2

System.out.println(c2.instanceCount);
System.out.println(c2.staticCount);
```

두 값이 다릅니다. 왜인지 **그림으로** 설명하세요.






### 문제 3 — 참조변수 복사

```java
class Point { int x = 0; }

Point p1 = new Point();
Point p2 = p1;
p2.x = 100;
System.out.println(p1.x);  

Point p3 = new Point();
System.out.println(p3.x);
```

step04 문제 6과 같은 이야기입니다.

### 문제 4 — null

```java
Point p = null;
System.out.println(p);
System.out.println(p.x);
```

두 번째 줄에서 무슨 일이 일어나나요? 에러 이름을 정확히 쓰세요.

### 문제 5 — 매개변수 ⭐

```java
static void change(int n) { n = 100; }
static void change(int[] arr) { arr[0] = 100; }

int a = 1;
change(a);
System.out.println(a);

int[] b = {1};
change(b);
System.out.println(b[0]);
```

하나는 바뀌고 하나는 안 바뀝니다. **넘어간 게 각각 무엇인지**로 설명하세요.

### 문제 6 — static 에서 인스턴스 멤버 호출

```java
public class Test {
    int a = 1;
    static int b = 2;

    public static void main(String[] args) {
        System.out.println(b);
        System.out.println(a);
    }
}
```

한 줄이 컴파일 에러입니다. 어느 줄이고 왜인가요? 고치는 방법 2가지를 쓰세요.

### 문제 7 — 호출 스택

```java
static void a() { System.out.println("a 시작"); b(); System.out.println("a 끝"); }
static void b() { System.out.println("b 시작"); System.out.println("b 끝"); }
// main 에서 a() 호출
```

출력 순서를 적고, **호출 스택이 쌓였다 줄어드는 그림**을 그리세요.

## 4. 손코딩 과제 — 스펙 보고 설계하기

여기부터는 **정답 코드가 하나가 아닙니다.** 스펙을 만족하면 됩니다.

### 과제 1 — `Student.java` + `StudentTest.java`

step04 과제 4를 클래스로 다시 만듭니다.

`**Student` 클래스 스펙**

- 인스턴스 변수: `String name`, `int korean`, `int english`, `int math`
- 메서드 `int getTotal()` — 세 과목 합
- 메서드 `double getAverage()` — 평균
- 메서드 `void printInfo()` — `지니 / 총점 255 / 평균 85.0` 형태로 출력

`**StudentTest` 클래스 (main 이 여기 있습니다)**

- `Student` 객체를 3개 만들어 값을 채우고 각각 `printInfo()` 호출

파일 두 개로 나누세요. `./run step05/StudentTest.java` 로 돌리면 `Student.java` 도
같이 컴파일됩니다.

> step04에서 배열 두 개를 인덱스로 짝지었던 것과 비교해보세요. **어느 쪽이 읽기 쉬운가요?**

### 과제 2 — `Rectangle.java` + `RectangleTest.java`

- 인스턴스 변수: `double width`, `double height`
- `double getArea()` — 넓이
- `double getPerimeter()` — 둘레
- `boolean isSquare()` — 정사각형이면 `true`

`RectangleTest` 에서 서로 다른 사각형 2개를 만들어 결과를 출력하세요.

### 과제 3 — `Counter.java` + `CounterTest.java`

`static` 을 눈으로 확인하는 과제입니다.

- 인스턴스 변수 `int count`
- **클래스 변수** `static int totalCount`
- 메서드 `void increase()` — 둘 다 1씩 증가

`CounterTest` 에서 객체 3개를 만들고 각각 `increase()` 를 2번씩 호출한 뒤,
각 객체의 `count` 와 `Counter.totalCount` 를 출력하세요.

기대: 각 객체 `count` 는 2, `totalCount` 는 6.
**왜 그런지 한 줄 주석으로 쓰세요.**

### 과제 4 — `Calculator.java` + `CalculatorTest.java`

메서드의 매개변수·반환값 연습.

- `int add(int a, int b)`, `int subtract(...)`, `int multiply(...)`
- `double divide(int a, int b)` — **0으로 나누면 어떻게 할지 직접 정하세요.**
(예외처리는 8장이라, 지금은 `0` 을 반환하고 메시지를 찍는 식으로)
- 메서드를 전부 `static` 으로 만들고, `CalculatorTest` 에서 객체 없이 호출해보세요.

> `static` 메서드는 왜 객체 없이 부를 수 있는지 주석으로 한 줄.

### 과제 5 — `BankAccount.java` + `BankAccountTest.java`

- 인스턴스 변수: `String owner`, `int balance`
- `void deposit(int amount)` — 입금
- `void withdraw(int amount)` — 출금. **잔액보다 크면 거부하고 메시지 출력**
- `void printBalance()`

`BankAccountTest` 에서 입금·출금을 섞어 호출하고, 잔액 부족 상황도 한 번 만드세요.

## 5. 자가 점검

- [ ] 클래스와 객체의 차이를 한 문장으로 말할 수 있다
- [ ] 변수 3종(클래스/인스턴스/지역)을 선언 위치로 구분할 수 있다
- [ ] `static` 이 붙으면 무엇이 달라지는지 안다
- [ ] 참조변수 두 개가 같은 객체를 가리킬 때의 그림을 그릴 수 있다
- [ ] 기본형과 참조형을 메서드에 넘겼을 때의 차이를 설명할 수 있다
- [ ] `static` 메서드에서 인스턴스 변수를 못 쓰는 이유를 안다
- [ ] `NullPointerException` 이 언제 나는지 안다

