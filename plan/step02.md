# step02 — 연산자

**책 범위: 3장 「연산자(operator)」 전체**

3장은 양이 많지만 겁먹을 필요 없습니다. **증감연산자(전위/후위)** 와 **단축평가(&&, ||)**,
이 두 개만 확실히 잡으면 나머지는 쓰다 보면 익습니다. 비트 연산자는 가볍게 지나가세요.

---

## 1. 읽을 것

- 3.1 연산자와 피연산자 / 3.2 연산자의 종류 / 3.3 연산자의 우선순위와 결합규칙
- 3.4 단항 연산자 (`++`, `--` **전위/후위 차이 중요**, 부호, `(타입)` 캐스트)
- 3.5 산술 연산자 (`/` `%`, **산술변환** — int보다 작은 타입은 int로 승격된다)
- 3.6 비교 연산자 (`==` vs `equals` 는 맛만)
- 3.7 논리 연산자 (`&&` `||` `!`, **단축평가**)
- 3.8 그 외 (조건 연산자 `? :`, 대입 연산자 `+=` 등)
- 비트 연산자(`&` `|` `^` `<<` `>>`) → **개념만 훑고 넘어가세요.** 지금 안 외워도 됩니다

### 함께 읽을 글 ([Inpa Dev](https://inpa.tistory.com/category/Language/Java))

- [☕ 자바 String 타입 특징 이해하기 (String Pool & 문자열 비교)](https://inpa.tistory.com/entry/JAVA-%E2%98%95-String-%ED%83%80%EC%9E%85-%ED%95%9C-%EB%88%88%EC%97%90-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0-String-Pool-%EB%AC%B8%EC%9E%90%EC%97%B4-%EB%B9%84%EA%B5%90)
  — **아래 문제 6의 답입니다.** `s1 == s2` 는 `true` 인데 `s1 == s3` 는 `false` 인 이유.
- [☕ 그림으로 보는 자바 코드의 메모리 영역 (스택 & 힙)](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B7%B8%EB%A6%BC%EC%9C%BC%EB%A1%9C-%EB%B3%B4%EB%8A%94-%EC%9E%90%EB%B0%94-%EC%BD%94%EB%93%9C%EC%9D%98-%EB%A9%94%EB%AA%A8%EB%A6%AC-%EC%98%81%EC%97%AD%EC%8A%A4%ED%83%9D-%ED%9E%99)
  — 위 글과 한 세트. `==` 가 "같은 상자냐"를 묻는다는 게 그림으로 나옵니다.
  step05(객체)에서 다시 볼 글이라, 지금은 그림만 보고 넘어가도 됩니다.

## 2. 손으로 정리할 것

1. **우선순위 큰 덩어리만** 순서대로 쓰세요. 세부까지 외우지 말고 이 정도만.
   ```
   단항(++ -- ! (타입))  >  산술(* / % 그다음 + -)  >  비교(< > 그다음 == !=)
     >  논리(&& 그다음 ||)  >  삼항(? :)  >  대입(= += ...)
   ```
   그리고 **결합 방향**: 대입만 오른쪽→왼쪽, 나머지는 왼쪽→오른쪽.

2. **전위 / 후위 증감** 을 한 문장씩. "`++i` 는 ___ 하고 값을 쓴다 / `i++` 는 ___"

3. **산술변환 규칙** 2줄:
   - 두 피연산자의 타입이 다르면 **큰 쪽으로** 맞춘다
   - `int` 보다 작은 타입(`byte`, `short`, `char`)은 무조건 **`int` 로 올린다**

4. **단축평가**: `false && ???` 는 왜 뒤를 안 보는가. `true || ???` 는?
   이게 왜 실무에서 중요한지 한 줄. (힌트: 뒤에 위험한 연산이 있을 때)

## 3. 출력 예상 문제

노트에 **손으로** 답을 적은 뒤 타이핑해서 확인하세요.

### 문제 1 — 증감연산자
```java
int i = 5;
System.out.println(i++);
System.out.println(i);
System.out.println(++i);
System.out.println(i);
System.out.println(i++ + ++i);
System.out.println(i);
```
마지막 두 개는 종이에서 **한 단계씩 i 값을 적어가며** 풀어야 합니다.

### 문제 2 — 산술변환
```java
byte a = 10, b = 20;
System.out.println(a + b);
// byte c = a + b;   ← 이 줄은 왜 에러일까?
char x = 'a';
System.out.println(x + 1);
System.out.println(10 / 3);
System.out.println(10 % 3);
System.out.println(-10 % 3);
System.out.println(10.0 / 3);
```

### 문제 3 — 단축평가
```java
int i = 0;
boolean r1 = (false && (++i > 0));
System.out.println(i);
boolean r2 = (true || (++i > 0));
System.out.println(i);
boolean r3 = (true && (++i > 0));
System.out.println(i);
```

### 문제 4 — 삼항 연산자
```java
int score = 85;
System.out.println(score >= 90 ? "A" : score >= 80 ? "B" : "C");
int a = 5, b = 3;
System.out.println(a > b ? a : b);
System.out.println(a > b ? "크다" : 0);   // 이 줄은 되나요?
```

### 문제 5 — 대입 연산자
```java
int i = 10;
i += 3;   System.out.println(i);
i -= 5;   System.out.println(i);
i *= 2;   System.out.println(i);
i /= 3;   System.out.println(i);
i %= 4;   System.out.println(i);
```

### 문제 6 — `==` 함정
```java
System.out.println(1 == 1.0);
System.out.println('A' == 65);
String s1 = "hi";
String s2 = "hi";
System.out.println(s1 == s2);
String s3 = new String("hi");
System.out.println(s1 == s3);
System.out.println(s1.equals(s3));
```
마지막 3개는 지금 완전히 이해 못 해도 됩니다. **"문자열 비교는 `equals`"** 만 손에 새기세요.
(이유는 step05에서 참조형을 배우면 풀립니다)

### 문제 7 — 컴파일 에러 찾기
```java
int a = 5;
if (a = 3) { }
boolean b = 1;
int c = 10 / 0;
double d = 10 / 0;
```
넷 중 **컴파일 에러**와 **실행 에러**를 구분하세요. 다릅니다.

## 4. 손코딩 과제

종이에 먼저 쓰고 `src/step02/` 로 옮기세요.

### 과제 1 — `Calculator.java`
`int a = 17, b = 5;` 로 사칙연산 + 나머지 5줄을 출력하세요.

```
17 + 5 = 22
17 - 5 = 12
17 * 5 = 85
17 / 5 = 3
17 % 5 = 2
```

그리고 **정확한 나눗셈 결과(3.4)** 를 출력하는 줄을 하나 더 추가하세요.

### 과제 2 — `EvenOdd.java`
`int n = 7;` 이 홀수인지 짝수인지를 **삼항 연산자만 써서** 한 줄로 출력하세요.
(if 는 아직 안 배웠습니다)

```
7은 홀수
```

### 과제 3 — `Max3.java`
`int a, b, c` 세 수 중 최대값을 **삼항 연산자를 중첩해서** 구하세요.
종이에서 먼저 "a와 b 중 큰 걸 고르고, 그걸 c와 비교" 순서로 쪼개세요.

### 과제 4 — `DigitSum.java`
`int num = 4827;` 의 **각 자리 숫자의 합**을 구하세요. (4+8+2+7 = 21)
반복문 없이 `/` 와 `%` 만으로 4자리 각각을 뽑아내세요.

```
4827 의 자리수 합 = 21
```

종이에서 먼저 표를 만드세요:

| 자리 | 뽑는 식 | 값 |
|---|---|---|
| 천의 자리 | `num / 1000` | 4 |
| 백의 자리 | ? | 8 |
| 십의 자리 | ? | 2 |
| 일의 자리 | ? | 7 |

### 과제 5 — `LeapYear.java`
`int year = 2024;` 가 윤년인지 판별하세요. 조건은 세 개고, 논리 연산자로 엮습니다.

- 4로 나누어지면 윤년
- 단, 100으로 나누어지면 윤년 아님
- 단, 400으로 나누어지면 윤년

종이에서 **먼저 한국어 문장으로** 조건식을 쓰고, 그다음 `&&` `||` 로 옮기세요.
2024 / 1900 / 2000 세 값으로 각각 테스트하세요. (답: 윤년 / 아님 / 윤년)

### 과제 6 — `ShortCircuit.java`
단축평가를 **직접 눈으로 확인**하세요.
`int count = 0;` 을 두고, `&&` 의 오른쪽에 `++count > 0` 을 넣은 경우와
왼쪽 조건이 `false` 일 때 `count` 가 안 늘어나는 걸 출력으로 보여주세요.

## 5. 자가 점검

- [ ] `i++` 와 `++i` 의 차이를 예시 출력과 함께 설명할 수 있다
- [ ] `byte + byte` 의 결과 타입이 `int` 인 이유를 안다
- [ ] `10 / 3` 과 `10.0 / 3` 의 차이를 즉시 말할 수 있다
- [ ] `&&` 의 단축평가를 코드로 보여줄 수 있다
- [ ] 삼항 연산자를 중첩해서 3분기를 쓸 수 있다
- [ ] 문자열 비교에 `==` 를 쓰면 안 되는 것을 기억한다
