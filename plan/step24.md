# step24 — 입출력 스트림 · 보조 스트림

**책 범위: 15장 「입출력(I/O)」 1~3절**

## 먼저 짚고 갈 것 — 스트림이 두 개입니다

step21~23에서 배운 `Stream` 과 이 장의 "스트림"은 **이름만 같고 완전히 다른 것**입니다.
지금 혼동을 정리하지 않으면 15장 내내 헷갈립니다.

| | 14장 `java.util.stream.Stream` | 15장 `java.io.InputStream` 계열 |
|---|---|---|
| 하는 일 | **컬렉션 데이터를 가공** (filter/map) | **외부와 데이터를 주고받음** (파일·네트워크) |
| 다루는 것 | 메모리 안의 객체들 | 바이트 또는 문자 |
| 언제 나옴 | 자바 8 | 자바 1.0 |
| 예 | `list.stream().filter(...)` | `new FileReader("a.txt")` |

**여기서 스트림은 "데이터가 흐르는 통로"** 라는 뜻입니다. 수도관을 떠올리면 됩니다.
한쪽 끝은 파일이나 키보드, 다른 쪽 끝은 내 프로그램입니다.

Node.js 의 `fs` 모듈과 목적이 같습니다. 다만 자바는 **통로의 종류를 직접 골라야** 합니다.

---

## 1. 읽을 것

- 15.1 자바에서의 입출력
  - 입출력이란, **스트림(stream)**, 바이트기반 스트림
  - `InputStream` / `OutputStream` 과 그 자손들
- 15.2 바이트기반 보조스트림
  - 보조 스트림이란, `FilterInputStream` / `FilterOutputStream`
  - **`BufferedInputStream` / `BufferedOutputStream`**
  - `DataInputStream` / `DataOutputStream`
  - `SequenceInputStream`, `PrintStream` (가볍게)
- 15.3 문자기반 스트림
  - `Reader` / `Writer`, `FileReader` / `FileWriter`
  - **`InputStreamReader` / `OutputStreamWriter`** (다리 역할)
  - **`BufferedReader` / `BufferedWriter`**
  - 표준 입출력 `System.in` / `System.out` / `System.err`

### 함께 읽을 글

[Inpa Dev](https://inpa.tistory.com/category/Language/Java) 블로그에는 입출력 스트림만
다룬 글이 없습니다. step24는 책과 아래 문제·과제로 진행합니다.

다만 step10에서 읽은
[☕ 자바 Try With Resource 예외 처리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%98%88%EC%99%B8-%EC%B2%98%EB%A6%AC-Try-With-Resource-%EB%AC%B8%EB%B2%95)
를 여기서 다시 보면 좋습니다. **스트림은 반드시 닫아야 하는 자원**이라
`try-with-resources` 의 진짜 쓰임새가 이 장입니다.

## 2. 손으로 정리할 것

1. **바이트 기반 vs 문자 기반** 표. 최상위 클래스 이름이 다릅니다.

   | | 읽기 | 쓰기 | 단위 | 쓰는 곳 |
   |---|---|---|---|---|
   | 바이트 기반 | `InputStream` | `OutputStream` | 1 byte | 이미지·동영상·모든 파일 |
   | 문자 기반 | `Reader` | `Writer` | 1 char (2 byte) | 텍스트 |

   **클래스 이름만 보면 어느 쪽인지 알 수 있습니다.**
   - 이름이 `~Stream` 으로 끝난다 → 바이트 기반
   - 이름이 `~Reader` / `~Writer` 로 끝난다 → 문자 기반

2. **기반 스트림 vs 보조 스트림.**

   ```
   기반 스트림   실제 데이터를 주고받는다        FileReader, FileInputStream
   보조 스트림   기반 스트림을 감싸 기능을 더한다  BufferedReader, DataInputStream
   ```

   보조 스트림은 **혼자 못 씁니다.** 항상 기반 스트림을 감쌉니다.

   ```java
   new BufferedReader( new FileReader("a.txt") )
        보조 스트림          기반 스트림
   ```

3. **`InputStreamReader` 가 하는 일.** 바이트 기반과 문자 기반을 잇는 다리입니다.

   ```
   byte 들 ──▶ InputStreamReader ──▶ char 들
              (문자셋을 보고 조립)
   ```

   `System.in` 은 바이트 기반(`InputStream`)이라, 문자로 읽으려면 이 다리가 필요합니다.

   ```java
   new BufferedReader(new InputStreamReader(System.in))
   ```
   이 한 줄을 통째로 외워두면 편합니다.

4. **`try-with-resources` 형태.** step10에서 배운 것을 여기 형태로 다시 쓰세요.

   ```java
   try (BufferedReader br = new BufferedReader(new FileReader("a.txt"))) {
       // 여기서 읽는다
   } catch (IOException e) {
       // 처리
   }
   // close() 를 안 써도 자동으로 닫힌다
   ```

## 3. 문제

### 문제 1 — 클래스 이름으로 구분하기
아래를 **바이트 기반**과 **문자 기반**으로 나누세요.

```
FileInputStream   FileReader   BufferedWriter   DataOutputStream
PrintStream       FileWriter   BufferedInputStream   InputStreamReader
```

`InputStreamReader` 는 어느 쪽인가요? 이름이 섞여 있는 이유도 생각해보세요.

### 문제 2 — 한글이 깨지는 이유 ⭐
```java
// 파일에 "안녕하세요 hello" 를 써둔 상태

try (FileReader fr = new FileReader("hangul.txt")) {
    int c;
    while ((c = fr.read()) != -1) System.out.print((char) c);
}
System.out.println();

try (FileInputStream fis = new FileInputStream("hangul.txt")) {
    int b;
    while ((b = fis.read()) != -1) System.out.print((char) b);
}
```

한쪽은 정상, 한쪽은 깨집니다.

```
FileReader      : 안녕하세요 hello
FileInputStream : ìëíì¸ì hello
```

**왜 깨지는지** 설명하세요. 힌트는 아래 숫자입니다.

```
파일 크기(바이트) = 21
문자열 길이(문자) = 11
```

한글 한 글자가 UTF-8 에서 **몇 바이트**를 차지하는지 계산해보세요.

### 문제 3 — read() 의 반환값
```java
int c = fr.read();
```
`read()` 가 `char` 가 아니라 **`int` 를 반환하는 이유**는 무엇인가요?
그리고 반복문 조건이 `!= -1` 인 이유는?

### 문제 4 — 보조 스트림 단독 사용
```java
BufferedReader br = new BufferedReader();
```
컴파일 에러입니다. 이유를 한 줄로 쓰세요.

### 문제 5 — readLine 의 끝
```java
try (BufferedReader br = new BufferedReader(new FileReader("lines.txt"))) {
    System.out.println("[" + br.readLine() + "]");
    System.out.println("[" + br.readLine() + "]");
    System.out.println("[" + br.readLine() + "]");
    System.out.println("[" + br.readLine() + "]");
}
```
파일에 3줄이 들어 있습니다. 네 번째 `readLine()` 은 무엇을 반환하나요?
그래서 반복문을 어떻게 쓰게 되는지도 적으세요.

### 문제 6 — 인코딩 불일치 ⭐
```java
// EUC-KR 로 "한글" 을 쓴 파일을 기본 설정으로 읽으면
try (BufferedReader br = new BufferedReader(new FileReader("euckr.txt"))) {
    System.out.println(br.readLine());
}
```
결과가 `�ѱ�` 처럼 깨집니다. 파일 크기를 비교하면 원인이 보입니다.

```
EUC-KR 로 저장한 "한글" = 4 바이트
UTF-8 로 저장한 "한글" = 6 바이트
```

제대로 읽으려면 어떤 클래스를 끼워야 하나요?

> JDK 18부터 기본 문자셋이 **UTF-8 로 고정**됐습니다(JEP 400). 직접 확인해보세요.
> ```java
> System.out.println(Charset.defaultCharset());   // UTF-8
> ```
> 그 전 버전에서는 OS 설정을 따라가서 "내 컴퓨터에서는 되는데" 문제가 잦았습니다.

### 문제 7 — 자원 닫기
```java
FileWriter fw = new FileWriter("a.txt");
fw.write("안녕");
// close() 를 안 했다
```
파일에 내용이 안 쓰여 있을 수 있습니다. 왜인가요?
(힌트: 버퍼가 가득 차거나 닫힐 때 실제로 기록됩니다)

`close()` 를 직접 부르는 것과 `try-with-resources` 중 어느 쪽이 안전한지,
**예외가 났을 때**를 기준으로 생각해보세요.

### 문제 8 — 버퍼의 효과
아래 두 코드는 같은 일을 합니다. 어느 쪽이 빠를지 예상하고 이유를 쓰세요.

```java
// A
FileReader fr = new FileReader("big.txt");
while (fr.read() != -1) { }

// B
BufferedReader br = new BufferedReader(new FileReader("big.txt"));
while (br.read() != -1) { }
```

## 4. 손코딩 과제

`src/step24/` 에 씁니다.

> **파일 생성 위치 안내** — 과제에서 만드는 `.txt` 파일은 `./run` 을 실행한
> **프로젝트 루트**에 생깁니다. `new FileWriter("a.txt")` 는 상대 경로라
> 현재 작업 디렉토리 기준이기 때문입니다.
> 루트가 지저분해지는 게 싫으면 `"src/step24/a.txt"` 처럼 경로를 적어주세요.
> `.gitignore` 에 `*.txt` 를 넣을지는 직접 정하시면 됩니다.

### 과제 1 — `WriteRead.java`

가장 기본입니다. 파일에 쓰고 다시 읽으세요.

- `FileWriter` 로 여러 줄 쓰기
- `FileReader` 로 한 글자씩 읽어 출력
- 둘 다 `try-with-resources` 로

그다음 **같은 파일에 다시 쓰기**를 해보세요. 내용이 덮어써지는 걸 확인한 뒤,
이어쓰기(`append`) 로 바꾸세요.

```java
new FileWriter("a.txt")        // 덮어쓰기
new FileWriter("a.txt", true)  // 이어쓰기
```

### 과제 2 — `LineReader.java`

한 글자씩 읽는 건 불편합니다. `BufferedReader` 로 **한 줄씩** 읽으세요.

```java
String line;
int n = 1;
while ((line = br.readLine()) != null) {
    System.out.println(n++ + ": " + line);
}
```

`while` 조건에 대입과 비교가 같이 들어간 형태입니다. step02의 우선순위·괄호가
여기서 필요합니다. **괄호를 빼면 어떻게 되는지** 한 번 확인해보세요.

읽을 파일로 **자기가 쓴 `.java` 파일**을 지정하면 재밌습니다.

```java
new FileReader("src/step24/LineReader.java")
```

### 과제 3 — `HangulBroken.java` ⭐

문제 2를 직접 재현하는 과제입니다. **일부러 깨뜨려보는 게 목적**입니다.

1. `FileWriter` 로 한글이 섞인 문장을 파일에 쓰기
2. `FileReader` 로 읽어 출력 → 정상
3. `FileInputStream` 으로 읽어 `(char)` 로 캐스팅해 출력 → 깨짐
4. 파일 크기(`new File(...).length()`)와 문자열 길이(`.length()`)를 비교해 출력

```
FileReader      : 안녕하세요 hello
FileInputStream : ìëíì¸ì hello
파일 크기(바이트) = 21
문자열 길이(문자) = 11
```

깨진 이유를 주석으로 두 줄 남기세요. step00의 "일부러 에러 내기"와 같은 방식입니다.

### 과제 4 — `FileCopy.java`

파일을 복사하세요. **바이트 기반**으로 만들어야 합니다.

```java
try (FileInputStream in = new FileInputStream("원본");
     FileOutputStream out = new FileOutputStream("복사본")) {
    int b;
    while ((b = in.read()) != -1) {
        out.write(b);
    }
}
```

세 가지를 확인하세요.

1. 텍스트 파일이 제대로 복사되는지 (원본과 복사본 크기 비교)
2. **이미지 파일도 복사되는지** — 아무 `.png` 를 넣고 열어보세요
3. `FileReader`/`FileWriter` 로 바꾸면 이미지가 깨지는지

3번이 **"텍스트가 아니면 문자 기반 스트림을 쓰면 안 된다"** 를 몸으로 아는 부분입니다.

그다음 `BufferedInputStream` / `BufferedOutputStream` 으로 감싸고
`System.currentTimeMillis()` 로 시간을 재서 비교하세요.

### 과제 5 — `Keyboard.java`

`System.in` 을 `BufferedReader` 로 읽으세요.

```java
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
String line = br.readLine();
```

step03에서 쓴 `Scanner` 와 비교해보세요.

|  | `Scanner` | `BufferedReader` |
|---|---|---|
| 읽는 단위 | 타입별 (`nextInt`, `next`) | 항상 `String` |
| 숫자로 쓰려면 | 바로 됨 | `Integer.parseInt()` 필요 (step12) |
| 속도 | 느림 | 빠름 (버퍼) |
| 예외 | 없음 | `IOException` 처리 필요 |

숫자 두 개를 입력받아 합을 출력하는 프로그램을 **두 방식으로 각각** 만들어보세요.

```bash
./run step24/Keyboard.java
```

### 과제 6 — `LogWriter.java` (선택)

지금까지 배운 걸 합칩니다.

- 실행할 때마다 `log.txt` 에 **한 줄씩 이어서** 기록
- 각 줄에 시간을 붙이기 (step13의 `LocalDateTime` + `DateTimeFormatter`)
- 파일이 없으면 만들고, 있으면 이어쓰기
- 프로그램 끝에 **지금까지 쌓인 전체 로그**를 읽어서 출력

```
[2026-09-21 14:03:11] 프로그램 실행됨
[2026-09-21 14:05:42] 프로그램 실행됨
```

## 5. 자가 점검

- [ ] 14장 `Stream` 과 15장 스트림이 다른 것임을 설명할 수 있다
- [ ] 클래스 이름만 보고 바이트 기반인지 문자 기반인지 구분한다
- [ ] 기반 스트림과 보조 스트림의 관계를 코드로 쓸 수 있다
- [ ] 한글이 깨지는 상황을 직접 만들고 원인을 설명할 수 있다
- [ ] `new BufferedReader(new InputStreamReader(System.in))` 를 외워서 쓸 수 있다
- [ ] `readLine()` 이 `null` 을 반환하는 시점을 안다
- [ ] `try-with-resources` 로 스트림을 닫을 수 있다
- [ ] 이미지 파일을 문자 스트림으로 다루면 안 되는 이유를 안다

---

다음은 step25(15장 4~5절)입니다. 파일 자체를 다루는 `File`·`Files` 와,
객체를 통째로 파일에 저장하는 **직렬화**를 배웁니다.
