# step11 — Object · String 계열

**책 범위: 9장 「java.lang패키지와 유용한 클래스」 1~2절**

step07에서 `toString()` 과 `equals()` 를 오버라이딩했습니다. 그때는 형태만 따라 썼는데,
**원래 그 메서드들이 뭐였는지**를 여기서 봅니다.

그리고 step02 문제 6에서 미뤄뒀던 것이 있습니다.

```java
String s1 = "hi", s2 = "hi";
String s3 = new String("hi");
s1 == s2   // true
s1 == s3   // false
```

이걸 "반대로 이해하고 있었다"고 적으셨죠. **String Pool** 이 그 답이고, 이 단계에서 완결됩니다.

---

## 1. 읽을 것

- 9.1 java.lang 패키지
  - **Object 클래스** — `equals()`, `hashCode()`, `toString()`, `clone()`, `getClass()`
  - **String 클래스** — 변경 불가능한(immutable) 클래스, 문자열의 비교, 문자열 리터럴,
    빈 문자열, String 클래스의 주요 메서드, `join()` 과 `StringJoiner`, 문자열과 기본형 간의 변환
  - **StringBuffer 클래스** — 변경 가능, 비교, 주요 메서드
  - **StringBuilder 클래스**

### 함께 읽을 글 ([Inpa Dev](https://inpa.tistory.com/category/Language/Java))

- [☕ 자바 String 타입 특징 이해하기 (String Pool & 문자열 비교)](https://inpa.tistory.com/entry/JAVA-%E2%98%95-String-%ED%83%80%EC%9E%85-%ED%95%9C-%EB%88%88%EC%97%90-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0-String-Pool-%EB%AC%B8%EC%9E%90%EC%97%B4-%EB%B9%84%EA%B5%90)
  — **step02 문제 6의 완전한 답.** 문제 3·4와 짝입니다.
- [☕ 자바 Object 클래스와 메서드 오버라이딩](https://inpa.tistory.com/entry/JAVA-%E2%98%95-Object-%ED%81%B4%EB%9E%98%EC%8A%A4%EC%99%80-%EC%83%81%EC%9C%84-%EB%A9%94%EC%84%9C%EB%93%9C-%EC%9E%AC%EC%A0%95%EC%9D%98-%ED%99%9C%EC%9A%A9-%EC%B4%9D%EC%A0%95%EB%A6%AC)
  — step07에서 읽었다면 복습, 안 읽었다면 여기서 읽으면 됩니다.
- [☕ 자바 toString 오버라이딩 - 완벽 이해하기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-toString-%EB%A9%94%EC%84%9C%EB%93%9C-%EC%9E%AC%EC%A0%95%EC%9D%98-%EC%99%84%EB%B2%BD-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0)
  — 객체를 `println` 했을 때 `Point@1b6d3586` 이 나오던 이유.
- [☕ 자바 equals / hashCode 오버라이딩 - 완벽 이해하기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-equals-hashCode-%EB%A9%94%EC%84%9C%EB%93%9C-%EA%B0%9C%EB%85%90-%ED%99%9C%EC%9A%A9-%ED%8C%8C%ED%97%A4%EC%B9%98%EA%B8%B0)
  — **과제 5의 배경.** `equals` 만 오버라이딩하면 왜 부족한지.
- [☕ 자바 객체의 hashCode는 고유하지 않다 ❌](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B0%9D%EC%B2%B4%EC%9D%98-hashCode%EB%8A%94-%EA%B3%A0%EC%9C%A0%ED%95%98%EC%A7%80-%EC%95%8A%EB%8B%A4-%E2%9D%8C)
  — 위 글의 함정편.
- [☕ String / StringBuffer / StringBuilder 차이점 & 성능 비교](https://inpa.tistory.com/entry/JAVA-%E2%98%95-String-StringBuffer-StringBuilder-%EC%B0%A8%EC%9D%B4%EC%A0%90-%EC%84%B1%EB%8A%A5-%EB%B9%84%EA%B5%90)
  — **과제 4와 짝.** 직접 측정해본 뒤 읽으면 더 잘 들어옵니다.
- [☕ 자바 split / StringTokenizer - 문자열 자르기 비교](https://inpa.tistory.com/entry/JAVA-%E2%98%95-Split-StringTokenizer-%EB%AC%B8%EC%9E%90%EC%97%B4-%EC%9E%90%EB%A5%B4%EA%B8%B0-%EB%B9%84%EA%B5%90%ED%95%98%EA%B8%B0)
  — 과제 2에서 `split()` 을 씁니다.
- [☕ 자바 clone 메서드 재정의 (얕은 복사 & 깊은 복사)](https://inpa.tistory.com/entry/JAVA-%E2%98%95-Object-%ED%81%B4%EB%9E%98%EC%8A%A4-clone-%EB%A9%94%EC%84%9C%EB%93%9C-%EC%96%95%EC%9D%80-%EB%B3%B5%EC%82%AC-%EA%B9%8A%EC%9D%80-%EB%B3%B5%EC%82%AC)
  — step04 문제 6(배열 참조 복사)의 연장. 여유가 있을 때.

## 2. 손으로 정리할 것

1. **Object 의 메서드 5개**와 각각이 원래 무엇을 하는지.

   | 메서드 | 원래 동작 (오버라이딩 전) |
   |---|---|
   | `toString()` | |
   | `equals(Object)` | |
   | `hashCode()` | |
   | `getClass()` | |
   | `clone()` | |

   `toString()` 과 `equals()` 는 step07에서 직접 오버라이딩해봤습니다. **오버라이딩 전에는
   무엇을 했는지**를 채우는 게 이 표의 목적입니다.

2. **String Pool 그림.** step02 문제 6을 이 그림으로 다시 설명해보세요.

   ```
              스택              힙
                          ┌─────────────────┐
     s1 ─┐                │  String Pool    │
         ├───────────────▶│     "hi"        │   ← 리터럴은 여기서 공유
     s2 ─┘                └─────────────────┘
                          ┌─────────────────┐
     s3 ────────────────▶ │  "hi" (새 객체)  │   ← new 는 따로 만든다
                          └─────────────────┘
   ```

3. **String 이 불변(immutable)이라는 것.** 아래 코드에서 `"hello"` 객체 자체는
   바뀌지 않는다는 걸 그림으로 그리세요.

   ```java
   String a = "hello";
   a = a + " world";     // "hello" 를 고친 게 아니라 새 문자열을 만들어 a 가 그쪽을 가리킴
   ```

   그리고 **불변이라서 좋은 점** 하나. (힌트: 여러 곳이 같은 객체를 공유해도 안전하다)

4. **String / StringBuffer / StringBuilder 표.**

   | | 변경 가능? | 동기화(thread-safe) | 언제 쓰나 |
   |---|---|---|---|
   | `String` | × | — | |
   | `StringBuffer` | ○ | ○ | |
   | `StringBuilder` | ○ | × | |

   혼자 쓰는 코드에서는 `StringBuilder` 가 기본입니다. (`StringBuffer` 는 동기화 때문에 조금 느립니다)

5. **`equals` 와 `hashCode` 규약** 한 줄.
   ```
   equals 가 true 인 두 객체는 hashCode 도 반드시 같아야 한다
   ```
   이걸 안 지키면 무슨 일이 생기는지는 문제 8에서 확인합니다.

## 3. 문제

### 문제 1 — Object 의 기본 동작
```java
class Point { int x = 1, y = 2; }

Point p = new Point();
System.out.println(p);
System.out.println(p.toString());
System.out.println(p.getClass().getName());
```
앞의 두 줄이 같은 결과인 이유는? 그리고 `Point@1b6d3586` 에서 `@` 뒤의 숫자는 무엇인가요?

### 문제 2 — String 불변 ⭐
```java
String a = "hello";
String b = a;
a = a + " world";
System.out.println("a = " + a);
System.out.println("b = " + b);

String s = "hi";
s.toUpperCase();
System.out.println("s = " + s);
System.out.println("s.toUpperCase() = " + s.toUpperCase());
```

`b` 의 값과 `s` 의 값이 관문입니다.
**`s.toUpperCase()` 를 호출했는데 `s` 가 안 바뀌는 이유**를 설명하세요.

> step04 문제 6에서 배열은 참조를 공유해서 한쪽을 바꾸면 다른 쪽도 바뀌었습니다.
> `String` 은 참조형인데도 그런 일이 안 일어납니다. 왜일까요?

### 문제 3 — StringBuilder 는 다르다
```java
StringBuilder sb = new StringBuilder("hi");
StringBuilder sb2 = sb;
sb.append(" there");
System.out.println("sb  = " + sb);
System.out.println("sb2 = " + sb2);
```
문제 2의 `String` 과 결과가 다릅니다. 무엇이 다른가요?

### 문제 4 — String Pool
```java
String s1 = "hi";
String s2 = "hi";
String s3 = new String("hi");
String s4 = s3.intern();

System.out.println(s1 == s2);
System.out.println(s1 == s3);
System.out.println(s1 == s4);
System.out.println(s1.equals(s3));
```
step02 문제 6의 확장입니다. `intern()` 이 무엇을 하는지도 쓰세요.

### 문제 5 — String 메서드
```java
String s = "Hello World";
System.out.println(s.length());
System.out.println(s.charAt(0));
System.out.println(s.substring(6));
System.out.println(s.substring(0, 5));
System.out.println(s.indexOf("o"));
System.out.println(s.lastIndexOf("o"));
System.out.println(s.replace("World", "Java"));
System.out.println(s);
```
**마지막 줄이 관문입니다.** `replace` 를 했는데 `s` 는 어떻게 되나요?

그리고 `substring(0, 5)` 가 `"Hello"` 인 것 — **끝 인덱스는 포함되지 않습니다.**
step04에서 배열 인덱스가 0부터였던 것과 같은 계열의 규칙입니다.

### 문제 6 — 컴파일 에러 / 실행 에러 찾기
```java
String s = "abc";
System.out.println(s.length);
System.out.println(s.charAt(5));
int[] arr = new int[3];
System.out.println(arr.length());
```
4줄 중 3줄이 문제입니다. **컴파일 에러와 실행 에러를 구분**하세요.

> `length` 와 `length()` — step04 자가 점검에 있던 그 항목입니다.

### 문제 7 — 문자열 ↔ 숫자
```java
String s = "100";
int a = Integer.parseInt(s);
String b = String.valueOf(a);
String c = a + "";
System.out.println(a + 1);
System.out.println(b + 1);
System.out.println(c + 1);
```
step01 문제 2의 `"" + ch + 1` 과 같은 이야기입니다.

### 문제 8 — equals 만 오버라이딩하면 ⭐
```java
class P1 {
    int x;
    P1(int x) { this.x = x; }
    @Override public boolean equals(Object o) {
        if (!(o instanceof P1)) return false;
        return this.x == ((P1) o).x;
    }
}

HashSet<P1> set = new HashSet<>();
set.add(new P1(1));
set.add(new P1(1));
System.out.println(set.size());
```
`equals` 로는 같은 객체인데 `size()` 가 **2** 가 나옵니다.
`hashCode()` 도 오버라이딩하면 **1** 이 됩니다.

**왜 그런지** 설명하세요. (힌트: `HashSet` 은 중복 검사를 할 때 `hashCode` 를 먼저 봅니다)

> `HashSet` 은 step15에서 제대로 배웁니다. 지금은 "규약을 안 지키면 컬렉션에서 오작동한다"
> 정도만 확인하면 됩니다.

### 문제 9 — 성능 예상
```java
String s = "";
for (int i = 0; i < 50000; i++) s += "a";
```
이 코드가 느린 이유를 **String 이 불변이라는 것**과 연결해서 설명하세요.
반복이 한 번 돌 때마다 무슨 일이 일어나나요?

## 4. 손코딩 과제

`src/step11/` 에 씁니다.

### 과제 1 — `StringLab.java`

String 주요 메서드를 한 번씩 써보는 실험실입니다. `String s = "Hello World";` 로 시작해
아래를 각각 출력하세요.

```
length / charAt / substring / indexOf / lastIndexOf / toUpperCase / toLowerCase
replace / contains / startsWith / endsWith / trim / isEmpty / isBlank / equalsIgnoreCase
```

그리고 **마지막에 원본 `s` 를 다시 출력**해서 하나도 안 바뀐 것을 확인하세요.
그게 불변의 증거입니다.

### 과제 2 — `WordCount.java`

```java
String text = "the quick brown fox jumps over the lazy dog";
```

- `split(" ")` 으로 단어 배열을 만들고 단어 개수를 출력
- 가장 긴 단어를 찾아 출력 (step04 과제 2의 `MinMax` 와 같은 패턴입니다)
- 각 단어의 첫 글자를 대문자로 바꿔 다시 이어붙이기
  → `The Quick Brown Fox ...`

첫 글자 대문자 만들기 힌트:
```java
word.substring(0, 1).toUpperCase() + word.substring(1)
```

### 과제 3 — `StringJoin.java`

`String[] names = {"지니", "민수", "서연"};` 을 `지니, 민수, 서연` 으로 만드세요.

**세 가지 방법**으로 각각 만들고 결과가 같은지 확인하세요.

1. `for` 문 + `+=` — 마지막 쉼표를 안 붙이려면 조건이 필요합니다
2. `StringBuilder` + `append`
3. `String.join(", ", names)`

3번이 한 줄로 끝납니다. 1번을 먼저 해봐야 왜 편한지 느껴집니다.

### 과제 4 — `StringPerf.java` ⭐

**직접 측정하는 과제입니다.**

```java
int N = 50000;

long t1 = System.currentTimeMillis();
String s = "";
for (int i = 0; i < N; i++) s += "a";
long t2 = System.currentTimeMillis();

StringBuilder sb = new StringBuilder();
for (int i = 0; i < N; i++) sb.append("a");
String r = sb.toString();
long t3 = System.currentTimeMillis();

System.out.println("String  += : " + (t2 - t1) + "ms");
System.out.println("StringBuilder : " + (t3 - t2) + "ms");
```

수십 배 차이가 납니다. (실행하는 컴퓨터마다 숫자는 다릅니다)

`N` 을 10000 / 50000 / 100000 으로 바꿔가며 돌려보세요. **`String` 쪽만 급격히 느려지는 것**을
확인하는 게 목적입니다. 왜 그런지는 문제 9의 답과 같습니다.

> 반복문 안에서 문자열을 `+` 로 이어붙이지 말라는 규칙이 여기서 나옵니다.
> 반복문 밖에서 몇 개 이어붙이는 건 괜찮습니다 — 컴파일러가 알아서 `StringBuilder` 로 바꿔줍니다.

### 과제 5 — `Point.java` equals + hashCode (step07 과제 3 개선)

step07에서 `equals()` 만 오버라이딩했습니다. 이제 `hashCode()` 도 같이 오버라이딩하세요.

```java
@Override
public boolean equals(Object o) {
    if (this == o) return true;              // 같은 객체면 바로 true (빠른 종료)
    if (!(o instanceof Point)) return false; // 타입이 다르면 false
    Point p = (Point) o;                     // 다운캐스팅 (step08)
    return this.x == p.x && this.y == p.y;
}

@Override
public int hashCode() {
    return Objects.hash(x, y);               // import java.util.Objects;
}

@Override
public String toString() {
    return "Point(" + x + ", " + y + ")";
}
```

`PointTest` 에서 확인할 것:
- `p1.equals(p2)` 가 `true`
- `p1.hashCode() == p2.hashCode()` 가 `true`
- `HashSet` 에 같은 좌표를 두 번 넣었을 때 크기가 **1**
- `System.out.println(p1)` 이 `Point(1, 2)`

**`hashCode()` 를 주석 처리하고 다시 돌려서** `HashSet` 크기가 2로 바뀌는 것도 확인하세요.

### 과제 6 — `Student.java` 마무리 (선택)

step05·06·07에서 계속 고쳐온 `Student` 에 `equals`·`hashCode`·`toString` 을 전부 넣으세요.
같은 이름·같은 점수면 같은 학생으로 취급합니다.

## 5. 자가 점검

- [ ] `Object` 의 `toString()` 이 원래 무엇을 출력하는지 안다
- [ ] `Object` 의 `equals()` 가 원래 무엇을 비교하는지 안다
- [ ] String Pool 을 그림으로 그려 `s1 == s2` 를 설명할 수 있다
- [ ] String 이 불변이라는 것과, 그래서 `s.toUpperCase()` 가 `s` 를 안 바꾸는 것을 안다
- [ ] `length` 와 `length()` 를 구분한다
- [ ] 반복문에서 문자열을 `+` 로 잇지 않는 이유를 설명할 수 있다
- [ ] `equals` 를 오버라이딩하면 `hashCode` 도 해야 하는 이유를 안다
- [ ] `StringBuilder` 와 `StringBuffer` 중 언제 무엇을 쓰는지 안다

---

다음은 step12(9장 3~5절) — 래퍼 클래스와 `BigDecimal` 입니다.
step01 문제 4에서 `0.1 + 0.2 != 0.3` 이었던 것의 **해결편**이 거기 있습니다.
