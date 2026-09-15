# step03 — 조건문과 반복문

**책 범위: 4장 「조건문과 반복문」 전체**

여기가 "프로그램을 짠다"는 감각이 처음 생기는 단계입니다. 문법 자체는 간단한데
**중첩 반복문**에서 대부분이 한 번 무너집니다. 그래서 이 단계는 종이에
**실행 순서를 표로 추적(trace)하는 연습**을 많이 넣었습니다. 이 습관이 나중에 디버깅 실력이 됩니다.

---

## 1. 읽을 것

- 4.1 조건문 — `if`, `if-else`, `if-else if`, 중첩 `if`
- 4.2 `switch` 문 — **`break` 를 빼면 어떻게 되는지(fall-through)** 를 꼭 확인
- 4.3 반복문 — `for`, `while`, `do-while`
- 4.4 `break` 와 `continue`, 그리고 이름 붙은 반복문(labeled)
- 중간에 나오는 **`Scanner`** 사용법 (`new Scanner(System.in)`, `nextInt()`, `nextLine()`)

### 함께 읽을 글

[Inpa Dev](https://inpa.tistory.com/category/Language/Java) 블로그에는 제어문(`if`/`for`/`while`)
만 다루는 글이 없습니다. step03은 책 + 아래 문제로 진행합니다.

여유가 있으면 step02에서 링크한
[☕ String Pool & 문자열 비교](https://inpa.tistory.com/entry/JAVA-%E2%98%95-String-%ED%83%80%EC%9E%85-%ED%95%9C-%EB%88%88%EC%97%90-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0-String-Pool-%EB%AC%B8%EC%9E%90%EC%97%B4-%EB%B9%84%EA%B5%90)
와 [☕ 스택 & 힙 메모리 영역](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B7%B8%EB%A6%BC%EC%9C%BC%EB%A1%9C-%EB%B3%B4%EB%8A%94-%EC%9E%90%EB%B0%94-%EC%BD%94%EB%93%9C%EC%9D%98-%EB%A9%94%EB%AA%A8%EB%A6%AC-%EC%98%81%EC%97%AD%EC%8A%A4%ED%83%9D-%ED%9E%99)
를 이때 읽어두면 step05(클래스와 객체)가 훨씬 수월해집니다.

전체 글 목록: [`reference/reading-list.md`](../reference/reading-list.md)

## 2. 손으로 정리할 것

1. **`for` 문의 실행 순서**를 번호로 쓰세요.
   ```
   for (초기화; 조건식; 증감식) { 블록 }
   ```
   → 초기화는 몇 번 실행되는가? 조건식 → 블록 → 증감식 중 어느 순서로 도는가?
   **화살표로 그리세요.** 이 그림 하나가 반복문 문제의 절반입니다.

2. **`while` 로 바꿔쓰기.** 위 `for` 를 `while` 로 옮긴 형태를 나란히 적으세요.
   ```java
   int i = 0;                  // 초기화
   while (i < 5) {             // 조건식
       ...                     // 블록
       i++;                    // 증감식
   }
   ```

3. **`break` vs `continue`** 한 문장씩. "break 는 ___ / continue 는 ___"

4. **`switch` 의 제약**: 조건식에 쓸 수 있는 타입, `case` 값의 조건(변수 가능?).

5. **`do-while` 을 쓰는 유일한 이유**를 한 줄로. (힌트: 최소 몇 번 실행되는가)

## 3. 출력 예상 문제

**반드시 노트에 손으로 추적하고 나서** 컴파일하세요. 여기서 컴파일 먼저 하면
이 단계는 아무것도 안 남습니다.

### 문제 1 — for 기본
```java
for (int i = 0; i < 3; i++) {
    System.out.println("i = " + i);
}
System.out.println("끝: " + 3);
```
그리고 `i < 3` 을 `i <= 3` 으로 바꾸면 몇 번 도나요?

### 문제 2 — 중첩 for (출력을 정확히)
```java
for (int i = 1; i <= 3; i++) {
    for (int j = 1; j <= 2; j++) {
        System.out.println(i + "-" + j);
    }
}
```
총 몇 줄이 출력되나요? 계산식으로 답하세요.

### 문제 3 — break / continue
```java
for (int i = 1; i <= 5; i++) {
    if (i == 3) continue;
    if (i == 5) break;
    System.out.print(i + " ");
}
```

### 문제 4 — 중첩에서의 break
```java
for (int i = 1; i <= 3; i++) {
    for (int j = 1; j <= 3; j++) {
        if (j == 2) break;
        System.out.print(i + "" + j + " ");
    }
}
```
`break` 가 **어느 반복문**을 빠져나가는지가 핵심입니다.

### 문제 5 — switch fall-through
```java
int n = 2;
switch (n) {
    case 1:
        System.out.println("하나");
    case 2:
        System.out.println("둘");
    case 3:
        System.out.println("셋");
        break;
    case 4:
        System.out.println("넷");
    default:
        System.out.println("기타");
}
```
`break` 가 없는 게 함정입니다. 출력이 몇 줄인지 정확히 적으세요.

### 문제 6 — while / do-while 차이
```java
int i = 10;
while (i < 5) {
    System.out.println("while: " + i);
    i++;
}

int j = 10;
do {
    System.out.println("do-while: " + j);
    j++;
} while (j < 5);
```

### 문제 7 — 무한루프 찾기
아래 셋 중 무한루프는 어느 것이고, 왜인가요?
```java
// A
for (int i = 0; i < 5; i--) { }
// B
int i = 0;
while (i < 5) { System.out.println(i); }
// C
for (int i = 0; i < 5; i++) ;
```
C는 무한루프인가요? 세미콜론의 의미를 설명하세요.

### 문제 8 — 스코프
```java
for (int i = 0; i < 3; i++) { }
System.out.println(i);
```
왜 에러인가요? 고치려면?

## 4. 손코딩 과제

종이에 먼저 다 쓰고 `src/step03/` 으로 옮기세요.
**과제 3부터는 종이에서 추적 표를 만들지 않으면 거의 확실히 틀립니다.**

### 과제 1 — `Gugudan.java`
`int n = 7;` 의 구구단을 출력하세요.
```
7 x 1 = 7
7 x 2 = 14
...
7 x 9 = 63
```
그다음 **2단부터 9단까지 전부** 출력하도록 중첩 반복문으로 확장하세요.

### 과제 2 — `SumTo100.java`
1부터 100까지의 합을 구하세요. `for` 로 한 번, `while` 로 한 번, **두 방식 모두** 쓰세요.
(답: 5050) 두 코드를 나란히 놓고 어디가 어떻게 대응되는지 종이에 선으로 이어보세요.

### 과제 3 — `Stars.java`
아래 3가지를 각각 출력하세요. `System.out.print` (개행 없음) 와
`System.out.println()` (개행만) 을 조합합니다.

```
(1) 사각형        (2) 왼쪽 삼각형     (3) 오른쪽 정렬 삼각형
*****             *                      *
*****             **                    **
*****             ***                  ***
*****             ****                ****
*****             *****              *****
```

(3)은 **공백을 몇 개 찍어야 하는지**를 `i` 로 표현하는 게 관문입니다.
종이에 표를 만드세요:

| 줄 i | 공백 개수 | 별 개수 |
|---|---|---|
| 1 | 4 | 1 |
| 2 | 3 | 2 |
| 3 | ? | ? |

### 과제 4 — `Prime.java`
`int n = 97;` 이 소수인지 판별하세요.
2부터 `n-1` 까지 나눠보고, 하나라도 나누어지면 소수가 아닙니다.

- `boolean isPrime = true;` 를 두고 시작
- 나누어지는 걸 찾으면 `false` 로 바꾸고 `break`

확장: `n` 을 100까지 돌려서 **100 이하 소수를 모두** 출력하세요. (총 25개)

### 과제 5 — `Gcd.java`
두 수의 최대공약수를 구하세요. `int a = 48, b = 18;` (답: 6)

두 가지 방법을 각각:
1. **작은 수부터 1까지 내려가며** 둘 다 나누어지는 첫 수 찾기 (`for` + `break`)
2. **유클리드 호제법**: `a % b` 를 반복 (`while (b != 0) { ... }`)

2번은 종이에 추적 표를 꼭 만드세요:

| 반복 | a | b | a % b |
|---|---|---|---|
| 1 | 48 | 18 | 12 |
| 2 | 18 | 12 | ? |
| 3 | ? | ? | ? |

### 과제 6 — `Grade.java`
`int score = 87;` 을 등급으로 바꿔 출력하세요. (90↑ A, 80↑ B, 70↑ C, 나머지 F)

**세 가지 방법으로 각각 작성하세요.** 같은 로직을 세 문법으로 쓰는 연습입니다.
1. `if - else if - else`
2. 삼항 연산자 중첩 (step02 복습)
3. `switch (score / 10)` — `case 10: case 9:` 로 fall-through 활용

### 과제 7 — `NumberGame.java` (Scanner 첫 사용)
`Scanner` 로 사용자 입력을 받아 정답을 맞히는 프로그램.

```java
import java.util.Scanner;   // 클래스 선언보다 위, 맨 첫 줄에

public class NumberGame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int answer = 42;
        // do-while 로 정답이 나올 때까지 반복
        // 입력값이 정답보다 크면 "작게", 작으면 "크게", 같으면 "정답!" 후 종료
    }
}
```

`while` 이 아니라 **`do-while`** 이 자연스러운 이유를 주석으로 한 줄 쓰세요.

실행하면 입력 대기 상태가 됩니다. 숫자를 치고 엔터. 빠져나오려면 `Ctrl+C`.

```bash
./run step03/NumberGame.java
```

### 과제 8 — `Fizzbuzz.java`
1부터 30까지 출력하되, 3의 배수는 `Fizz`, 5의 배수는 `Buzz`,
**둘 다인 수는 `FizzBuzz`** 를 출력하세요.

조건 순서를 잘못 잡으면 15에서 틀립니다. 종이에서 15를 먼저 확인하세요.

## 5. 자가 점검

- [ ] `for` 문의 실행 순서를 화살표로 그릴 수 있다
- [ ] 같은 로직을 `for` / `while` 로 서로 바꿔 쓸 수 있다
- [ ] 중첩 반복문에서 `break` 가 어디까지 빠져나가는지 안다
- [ ] `switch` 에서 `break` 를 빼면 무슨 일이 생기는지 설명할 수 있다
- [ ] 별 삼각형(공백 정렬)을 종이에서 표로 풀어서 코드로 옮길 수 있다
- [ ] `Scanner` 로 입력을 받는 3줄(`import`, `new`, `nextInt`)을 외워서 쓸 수 있다
- [ ] `notes/errors.md` 가 계속 쌓이고 있다

---

## 여기까지 오면

기초 문법(1~4장)이 끝납니다. **step03을 마치고 알려주세요.**
그때 진행 속도와 어디서 막혔는지를 보고 step04(배열)부터 객체지향까지를 만듭니다.
객체지향은 문제 스타일이 좀 달라져야 해서(스펙을 주고 클래스를 설계하게), 미리 만들지 않았습니다.
