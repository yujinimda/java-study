# step01 — 변수와 타입, 그리고 형변환

**책 범위: 2장 「변수(variable)」 전체**

여기서 배우는 "타입" 개념이 뒤의 모든 장의 바닥입니다. 특히 **형변환**은 지금 손으로
많이 써봐야 6~7장(객체지향)에서 나오는 참조형 형변환이 덜 낯설어집니다.

---

## 1. 읽을 것

- 2.1 변수 (선언과 초기화, 변수의 명명규칙)
- 2.2 변수의 타입 (기본형 / 참조형)
- 2.3 상수와 리터럴 (`final`, 리터럴 접미사 `L` `f` `d`)
- 2.4 진법 → **가볍게 읽고 넘어가세요.** 2진수/16진수 표기법(`0b`, `0x`)만 눈에 익히면 됩니다
- 2.5 기본형 (8개 타입, 범위, 오버플로)
- 2.6 형변환 (자동 형변환 / 명시적 형변환)

### 함께 읽을 글 ([Inpa Dev](https://inpa.tistory.com/category/Language/Java))

- [☕ JAVA 기본 자료형 &amp; 데이터 타입 - 한눈에 정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B8%B0%EB%B3%B8-%EC%9E%90%EB%A3%8C%ED%98%95-%EC%A2%85%EB%A5%98-%EC%B4%9D%EC%A0%95%EB%A6%AC-int-double-char-String)
— 기본형 8개. 아래 「손으로 정리할 것」 1번의 표와 짝입니다.
- [☕ JAVA 변수의 기본형 &amp; 참조형 타입 차이 이해하기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EB%B3%80%EC%88%98%EC%9D%98-%EA%B8%B0%EB%B3%B8%ED%98%95-%EC%B0%B8%EC%A1%B0%ED%98%95-%ED%83%80%EC%9E%85)
— `char` 와 `String` 이 왜 다른 종류인지.
- [☕ JAVA 타입 형변환 원리 &amp; 방법 - 완벽 정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%ED%83%80%EC%9E%85-%ED%98%95%EB%B3%80%ED%99%98-%EC%A2%85%EB%A5%98-%EB%B0%A9%EB%B2%95-%F0%9F%92%AF-%EC%B4%9D%EC%A0%95%EB%A6%AC)
— **과제 4(CastLab) 전체의 배경.**
- [☕ 실수 표현(부동 소수점) 원리 한눈에 이해하기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%8B%A4%EC%88%98-%ED%91%9C%ED%98%84%EB%B6%80%EB%8F%99-%EC%86%8C%EC%88%98%EC%A0%90-%EC%9B%90%EB%A6%AC-%ED%95%9C%EB%88%88%EC%97%90-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0)
— **문제 4(`0.1 + 0.2`)의 완전한 답.** 2진수 변환·IEEE 754 부분은 이론이라
나중에 다시 봐도 됩니다.
- [☕ BigInteger &amp; BigDecimal 사용법 정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-BigInteger-BigDecimal-%EC%9E%90%EB%A3%8C%ED%98%95-%EC%82%AC%EC%9A%A9%EB%B2%95-%EC%B4%9D%EC%A0%95%EB%A6%AC)
— 위 글의 해결편. "돈 계산에 `double` 쓰지 말라"의 실제 대안.
- [☕ 자바 Wrapper 클래스와 Boxing &amp; UnBoxing 총정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-wrapper-class-Boxing-UnBoxing)
— `int` ↔ `Integer`. step02 문제 4에서 삼항 연산자 결과가 `Object` 가 된 이유가 여기 있습니다.

## 2. 손으로 정리할 것

1. **기본형 8개 표**를 노트에 직접 그리세요. 외우려 하지 말고, 표를 손으로 채우세요.
  
  | 분류  | 타입                          | 크기             | 대략의 범위                          |
  | --- | --------------------------- | -------------- | ------------------------------- |
  | 논리  | `boolean`                   | 1byte          | 1바이트이고 그냥 참, 거짓만 판별             |
  | 문자  | `char`                      | 2byte          | 0 ~ 65535 (문자 하나)               |
  | 정수  | `byte` `short` `int` `long` | 1, 2, 4, 8 바이트 | 각자 다른데 큰거는 조넘어서까지 인트랑 롱만 쓰임 요즘은 |
  | 실수  | `float` `double`            | 4바이트 / 8바이트    | 이거 좀 컸던거같애                      |
  
2. **형변환 방향 화살표.** 값 손실이 없는 방향(자동 형변환)을 화살표로 그리세요.
  ```
   byte → short → int → long → float → double
                  char ↗
  ```

   이 화살표 **방향대로 가면 캐스팅 생략 가능**, 거꾸로 가면 `(타입)` 을 직접 써야 함.  
  -&gt;작은게 손실없으니까 반대 방향
3. `**int` 와 `long` 리터럴**: `long big = 10000000000;` 이 왜 에러인지 한 문장으로.  
-&gt; 10000000000L
4. `**char` 와 `String` 의 차이**: 따옴표, 저장하는 것, 기본형/참조형 여부.  
-&gt; char은 문자하나고(작은따옴표, 기본형 진짜 저장, 힙에저장) String는 문자열인 객체(큰따옴표, 참조형, 스택에만 저장하고 이미 힙 저장되어있는거 참조만함)

## 3. 출력 예상 문제

**컴파일하지 말고** 노트에 손으로 답을 적으세요. 전부 적은 뒤에 타이핑해서 확인합니다.
(코드를 손으로 옮겨 쓰면서 답을 적으면 더 좋습니다)

### 문제 1

```java
int a = 7, b = 2;
System.out.println(a / b); -> 3
System.out.println(a % b); -> 1
System.out.println((double) a / b); -> 3.5 한쪽이 double이면 소수를 담을수있음
System.out.println(a / (double) b); -> 3.5
System.out.println((double) (a / b)); -> 3.0 (이미 3이 된 뒤에 형 변환해서)
```

마지막 두 개가 다른 이유까지 쓰세요.  -&gt; ??근데 double이 뭔데  

3. float / double = 소수를 담는 타입

  ┌────────┬───────┬──────────────────┐

  │        │ 크기  │      정밀도      │

  ├────────┼───────┼──────────────────┤

  │ float  │ 4byte │ 소수점 약 7자리  │

  ├────────┼───────┼──────────────────┤

  │ double │ 8byte │ 소수점 약 15자리 │

### 문제 2

```java
char ch = 'A';
int i = ch;
System.out.println(i); 65
System.out.println(ch + 1); 66
System.out.println((char) (ch + 1)); B
System.out.println("" + ch + 1); A1
```

### 문제 3

```java
byte b = 127;
b++;
System.out.println(b);
```

왜 이런 값이 나오는지 설명하세요. (오버플로)  
-&gt; byte범위가 128~127인데 1을 더하면 갈곳이 없고 최솟값으로 한바퀴 돈다 그래서 조용히 틀린값이 된다.

### 문제 4

```java
System.out.println(0.1 + 0.2);
System.out.println(0.1 + 0.2 == 0.3);
float f = 0.1f;
double d = f;
System.out.println(d);
```

마지막 줄이 왜 `0.1` 이 아닌지 설명하세요. (힌트: `float` 은 0.1을 정확히 담지 못합니다)  

-&gt;   0.1 + 0.2        → 0.30000000000000004

  0.1 + 0.2 == 0.3 → false

  d                → 0.10000000149011612  

-&gt; float의 0.1은 그냥 처음부터                 

  0.10000000149011612 이거였던거야? 안그러면

  되잖아 근데  

### 문제 5 — 컴파일 에러 찾기

에러가 나는 줄을 **모두** 찾고, 고치는 방법을 쓰세요. (8줄 중 7줄이 에러입니다)

노트에 다 적은 뒤 컴파일해보면 **에러가 하나만** 나올 겁니다. 하나 고치고 다시 컴파일,
또 고치고 다시 컴파일 — 이걸 반복해서 7개를 다 뽑아내세요.
컴파일러가 에러를 한 번에 다 보여주지 않는 건 정상이고, 실제 개발도 이렇게 합니다.

```java
int a;
System.out.println(a);
long big = 10000000000;
float f = 3.14;
char c = "A";
int x = 3.5;
final int MAX = 10;
MAX = 20;
byte small = 200;

```

```java
int a = 10;
System.out.println(a);
long big = 10000000000L;
float f = 3.14f;
char c = 'A';
float x = 3.5f;
final int MAX = 10;
MAX = 20;
int small = 200;
```



문제 6

```java
int i = 10;
double d = i;
int back = (int) d; 언박싱인가? 그건가?
```

  아까 그린 화살표가 이 규칙입니다

  byte → short → int → long → float → double

  - 방향대로 가면 → 자동 (캐스팅 생략 가능)

  - 거꾸로 가면 → (타입) 을 직접 써야 함

## 4. 손코딩 과제

종이에 먼저 전부 쓴 뒤 `src/step01/` 에 옮기세요. **파일 하나에 클래스 하나**로 만드세요.

### 과제 1 — `MyProfile.java`

`String name`, `int age`, `double height`, `char bloodType`, `boolean isStudent`
5개 변수를 선언·초기화하고 아래처럼 한 줄로 출력하세요.

```
지니 / 28세 / 165.5cm / A형 / 학생아님
```

`boolean` 을 "학생" / "학생아님" 으로 바꿔 찍는 건 아직 배우지 않았습니다.
`isStudent` 값을 그대로 출력해도 됩니다. (조건문은 step03)

### 과제 2 — `TimeConvert.java`

`int totalSeconds = 10000;` 하나만 주어졌을 때, 이걸 **시 / 분 / 초** 로 나눠 출력하세요.

```
10000초 = 2시간 46분 40초
```

`/` 와 `%` 만 씁니다. 종이에서 먼저 계산식을 세우세요. (10000 / 3600 은? 나머지는?)

### 과제 3 — `Swap.java`

`int a = 10; int b = 20;` 의 값을 서로 바꿔서 출력하세요.
`temp` 변수를 쓰는 방식으로, 종이에 **각 줄 실행 후 a, b, temp 값을 표로** 적어가며 쓰세요.


| 줄   | a   | b   | temp |
| --- | --- | --- | ---- |
| 초기  | 10  | 20  | -    |
| ... |     |     |      |


### 과제 4 — `CastLab.java`

형변환 실험실. 아래를 각각 한 줄씩 출력하세요.

1. `double 3.99` 를 `int` 로 바꾼 값 (소수점이 반올림되는지 버려지는지 확인)
2. `int 65` 를 `char` 로 바꾼 값
3. `char '9'` 를 `int` 로 바꾼 값 (9가 나오는지 확인 → 안 나옵니다. 왜?)
4. `char '9'` 에서 **숫자 9를 얻는 방법** (힌트: `'9' - '0'`)
5. `int 300` 을 `byte` 로 바꾼 값

3번과 5번은 결과가 예상과 다를 겁니다. **왜 그런지 노트에 쓰세요.** 이게 과제의 핵심입니다.

### 과제 5 — `CircleArea.java`

반지름을 `final double PI = 3.14159;` 와 함께 써서 원의 면적과 둘레를 출력하세요.
`final` 을 쓰는 이유를 한 줄 주석으로 붙이세요.

## 5. 자가 점검

- [ ] 기본형 8개를 책 안 보고 쓸 수 있다
- [ ] 자동 형변환이 되는 방향을 화살표로 그릴 수 있다
- [ ] `long`, `float` 리터럴에 접미사가 왜 필요한지 설명할 수 있다
- [ ] `7 / 2` 가 3인 이유와, 3.5를 얻는 방법 2가지를 안다
- [ ] `char` 가 정수와 연산되면 무슨 일이 일어나는지 설명할 수 있다
- [ ] `0.1 + 0.2 != 0.3` 인 이유를 한 문장으로 말할 수 있다

