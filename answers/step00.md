# step00 정답과 해설

> 노트에 먼저 답을 적은 뒤에 여기를 보세요. 아래 에러 메시지들은 이 저장소에서
> 실제로 `javac 21` 로 컴파일해서 그대로 옮긴 것입니다.

---

## 문제 1

|  | `.java` | `.class` |
|---|---|---|
| 정체 | 사람이 읽고 쓰는 **소스코드** (그냥 텍스트 파일) | JVM이 읽는 **바이트코드** (사람은 못 읽음) |
| 만드는 명령 | 내가 직접 타이핑 | `javac Hello.java` |
| 실행하는 명령 | 실행 못 함 | `java Hello` ← **`.class` 를 붙이지 않는다** |

`java Hello` 에서 `.class` 를 안 붙이는 게 헷갈리는 지점입니다. `javac` 는 **파일**을
받고(`Hello.java`), `java` 는 **클래스 이름**을 받습니다(`Hello`).

## 문제 2

문제가 있습니다. **`public` 클래스의 이름은 파일명과 정확히 같아야 합니다.**
파일명이 `Hello.java` 인데 클래스명이 `hello` (소문자) 라서 에러입니다.

```
Hello.java:1: error: class hello is public, should be declared in a file named hello.java
public class hello {
       ^
```

에러 메시지가 아주 친절합니다. "hello.java 라는 파일에 선언되어야 한다"고 대놓고 알려줍니다.

고치는 방법은 둘 중 하나:
- 클래스명을 `Hello` 로 바꾸기 (← 이게 정답. 자바 클래스명은 **대문자로 시작**하는 게 관례)
- 파일명을 `hello.java` 로 바꾸기

## 문제 3

코드에 잘못된 곳은 **4군데**입니다.

| 줄 | 잘못된 것 | 이유 |
|---|---|---|
| 3 | `println("첫 번째")` | 세미콜론 누락 |
| 4 | `System.out.Println` | `P` 가 대문자. 자바는 대소문자를 구분한다 |
| 5 | `system.out.println` | `s` 가 소문자. `System` 은 클래스 이름이라 대문자 |
| 6 | `'네 번째'` | 작은따옴표는 **문자 하나(char)** 만 담는다. 문자열은 `"..."` |

### 그런데 컴파일하면 이렇게 나옵니다

```
Quiz.java:3: error: ';' expected
Quiz.java:6: error: unclosed character literal
Quiz.java:6: error: unclosed character literal
Quiz.java:6: error: not a statement
4 errors
```

**4번 줄과 5번 줄의 에러는 아예 안 나왔습니다.** 왜일까요?

컴파일은 크게 두 단계로 진행됩니다.

```
① 문법 검사(파싱)          ② 의미 검사
   "문장 구조가 말이 되나"      "그런 이름이 실제로 있나, 타입이 맞나"
   세미콜론, 따옴표, 중괄호      Println 이라는 메서드가 있나? system 이라는 게 있나?
```

**①에서 실패하면 ②로 넘어가지 않습니다.** 3번 줄(세미콜론)과 6번 줄(따옴표)은 ①단계
에러라서 여기서 멈췄고, `Println`/`system` 은 ②단계라서 아직 검사조차 되지 않았습니다.

그래서 실무에서 컴파일은 이렇게 씁니다: **에러 하나 고치고 → 다시 컴파일 → 또 고치고.**
"에러 개수가 줄지 않는데?" 가 아니라 "**새 에러가 나오면 앞 단계는 통과한 것**" 입니다.

참고로 6번 줄 하나에서 에러 3개가 나온 이유는, `'네 번째'` 안에 글자가 여러 개라서
컴파일러가 따옴표 짝을 엉뚱하게 묶었기 때문입니다. **에러 개수 ≠ 실수 개수** 입니다.

## 문제 4

```java
public static void main(String[] args) {
```

- `public` — 외부(JVM)에서 부를 수 있어야 하니까
- `static` — 객체를 만들지 않은 상태에서 불려야 하니까 (6장에서 제대로 배웁니다)
- `void` — 돌려주는 값이 없다
- `String[] args` — 실행할 때 넘긴 값들이 담기는 배열

`args` 는 이름일 뿐이라 `String[] a` 로 써도 됩니다. 나머지 4개는 바꿀 수 없습니다.

## 문제 5

```java
// 한 줄 주석. 이 줄 끝까지 무시된다

/* 여러 줄 주석.
   중간에 뭘 써도 무시된다 */

/** 문서화 주석(javadoc). 이 클래스/메서드가 뭐하는지 설명할 때 쓴다 */
```

---

## 손코딩 과제

과제 1, 3의 모범답안은 `answers/step00/Step00Answers.java` 입니다.

```bash
./run answers/step00/Step00Answers.java 안녕
```

### 과제 2 — 일부러 낸 에러들의 실제 메시지

**1. 세미콜론 누락**
```
BrokenOnPurpose.java:3: error: ';' expected
        System.out.println("첫 줄")
                                 ^
```
`^` 가 정확히 세미콜론이 있어야 할 자리를 가리킵니다.

**2. 클래스명 불일치**
```
BrokenOnPurpose.java:1: error: class Broken is public, should be declared in a file named Broken.java
```

**3. `printLn` (대문자 L)**
```
BrokenOnPurpose.java:3: error: cannot find symbol
        System.out.printLn("안녕");
                  ^
  symbol:   method printLn(String)
  location: variable out of type PrintStream
```
`cannot find symbol` 은 앞으로 **가장 자주 보게 될 에러**입니다. 뜻은 딱 하나:
**"그런 이름을 나는 모른다."** 원인은 거의 항상 오타, 대소문자, 또는 선언을 안 한 것입니다.
아래 `symbol:` 줄이 "내가 못 찾은 이름"을 정확히 알려주니 거기를 보세요.

참고로 `system.out.println` (소문자 s) 은 다른 메시지가 나옵니다:
```
BrokenOnPurpose.java:3: error: package system does not exist
```
컴파일러가 `system` 을 클래스가 아니라 **패키지 이름**으로 해석했기 때문입니다.

**4. 닫는 중괄호 누락**
```
BrokenOnPurpose.java:4: error: reached end of file while parsing
}
 ^
```
여기가 중요합니다. **에러가 4번 줄을 가리키지만 실수는 4번 줄에 없습니다.**
빠뜨린 `}` 는 어디에 있어야 했는지 컴파일러가 알 수 없습니다. 파일 끝까지 읽고서야
"괄호가 안 닫혔네"를 알아채기 때문에, 위치는 **항상 파일 끝**으로 나옵니다.

> **여기서 얻어갈 원칙:** 에러가 가리키는 줄은 "컴파일러가 이상함을 알아챈 위치"이고,
> "내가 실수한 위치"가 아닐 수 있습니다. 괄호 계열 에러는 줄 번호를 믿지 말고
> **들여쓰기를 보면서 짝을 세는 게** 빠릅니다.

### 과제 3 — args 없이 실행하면?

```bash
./run answers/step00/Step00Answers.java     # 뒤에 아무것도 안 붙이고
```
`args[0]` 을 그냥 읽으면 이렇게 죽습니다.
```
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index 0 out of bounds for length 0
```

이건 **컴파일 에러가 아니라 실행 에러(예외)** 입니다. 둘의 차이를 지금부터 구분하세요.

| | 컴파일 에러 | 실행 에러(예외) |
|---|---|---|
| 언제 | `javac` 단계 | `java` 로 돌리는 중 |
| 메시지 시작 | `error:` | `Exception in thread "main"` |
| 뜻 | 문법/타입이 틀렸다 | 문법은 맞는데 **돌려보니** 문제가 생겼다 |
