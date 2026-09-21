# step25 — 파일 · 직렬화

**책 범위: 15장 「입출력(I/O)」 4~5절**

step24에서는 파일 **안의 내용**을 읽고 썼습니다. 이 단계에서는 파일 **자체**를 다룹니다 —
있는지 확인하고, 크기를 재고, 목록을 얻고, 지우는 일입니다.

그리고 **직렬화**를 배웁니다. step05~09에서 만든 `Student` 객체를 파일에 통째로 저장했다가
다시 꺼내오는 것입니다. 지금까지는 프로그램이 끝나면 객체가 사라졌는데, 여기서 처음으로
**살아남게** 됩니다.

---

## 1. 읽을 것

- 15.4 파일 (`File` 클래스)
  - `File` 인스턴스 생성, 경로 관련 메서드
  - 파일 속성 조회 (`exists`, `length`, `isFile`, `isDirectory`, `lastModified`)
  - 파일·디렉토리 생성·삭제 (`createNewFile`, `mkdir`, `mkdirs`, `delete`)
  - 디렉토리 목록 (`list`, `listFiles`)
- 15.5 직렬화 (Serialization)
  - 직렬화란, `ObjectInputStream` / `ObjectOutputStream`
  - **직렬화가 가능한 클래스 (`Serializable`)**
  - **`transient` 와 직렬화**
  - **직렬화 가능한 클래스의 버전 (`serialVersionUID`)**

### 함께 읽을 글 ([Inpa Dev](https://inpa.tistory.com/category/Language/Java))

- [☕ 자바 직렬화(Serializable) - 완벽 마스터하기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%A7%81%EB%A0%AC%ED%99%94Serializable-%EC%99%84%EB%B2%BD-%EB%A7%88%EC%8A%A4%ED%84%B0%ED%95%98%EA%B8%B0)
  — **이 단계의 핵심 글.** `serialVersionUID` 를 왜 직접 써야 하는지가 나옵니다.
- [☕ 자바 역직렬화 방어 기법 - 총정리 모음](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B0%9D%EC%B2%B4-%EC%97%AD%EC%A7%81%EB%A0%AC%ED%99%94-%EB%B0%A9%EC%96%B4-%EA%B8%B0%EB%B2%95-%EC%B4%9D%EC%A0%95%EB%A6%AC-%EB%AA%A8%EC%9D%8C)
  — 직렬화가 왜 보안 문제가 되는지. 아래 「직렬화 주의점」과 같이 보면 좋습니다.
- [☕ 싱글톤 객체가 깨져버리는 경우 (역직렬화/리플렉션)](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%8B%B1%EA%B8%80%ED%86%A4-%EA%B0%9D%EC%B2%B4-%EA%B9%A8%EB%9C%A8%EB%A6%AC%EB%8A%94-%EB%B0%A9%EB%B2%95-%EC%97%AD%EC%A7%81%EB%A0%AC%ED%99%94-%EB%A6%AC%ED%94%8C%EB%A0%89%EC%85%98)
  — 심화. 역직렬화가 **생성자를 거치지 않는다**는 점이 문제의 뿌리입니다. 문제 8과 연결됩니다.

## 2. 손으로 정리할 것

1. **`File` 객체 ≠ 실제 파일.**

   ```java
   File f = new File("nope.txt");   // 파일이 없어도 이 줄은 에러가 안 난다
   f.exists();                       // false
   ```

   `new File(...)` 은 **경로를 담은 객체**를 만들 뿐, 파일을 만들지 않습니다.
   실제로 만들려면 `createNewFile()` 을 따로 불러야 합니다.

2. **`File` 과 `Files`/`Path` 비교.** 책은 `File` 위주지만 **실무는 `Files`/`Path`** 입니다.

   | 하려는 일 | `File` (구식) | `Files` / `Path` (자바 7+) |
   |---|---|---|
   | 전체 읽기 | 스트림으로 직접 | `Files.readString(path)` |
   | 전체 쓰기 | 스트림으로 직접 | `Files.writeString(path, s)` |
   | 줄 단위 읽기 | `BufferedReader` | `Files.readAllLines(path)` |
   | 존재 확인 | `f.exists()` | `Files.exists(path)` |
   | 삭제 실패 시 | `false` 반환 (조용함) | **예외를 던짐** |

   `File` 의 메서드들은 실패해도 `false` 만 돌려줘서 **왜 실패했는지 모릅니다.**
   `Files` 는 예외로 알려줍니다. 그게 갈아탄 가장 큰 이유입니다.

3. **직렬화가 무엇인지** 한 줄로.

   ```
   직렬화(serialize)     객체 → 바이트 스트림   (파일·네트워크로 보낼 수 있는 형태)
   역직렬화(deserialize) 바이트 스트림 → 객체
   ```

   JS의 `JSON.stringify` / `JSON.parse` 와 목적이 같습니다. 다만 자바 직렬화는
   **사람이 못 읽는 바이너리**이고, 자바끼리만 주고받을 수 있습니다.

4. **저장되지 않는 것 3가지.** 왜 안 되는지 한 줄씩.
   - `static` 필드
   - `transient` 가 붙은 필드
   - `Serializable` 을 구현하지 않은 타입의 필드

## 3. 문제

### 문제 1 — File 객체와 실제 파일
```java
File f = new File("nope.txt");     // 없는 파일
System.out.println(f.exists());
System.out.println(f.length());
System.out.println(f.getName());
```
에러가 안 납니다. 각 줄의 결과를 적고, **왜 에러가 아닌지** 설명하세요.

### 문제 2 — 파일 정보
```java
File f = new File("sample.txt");   // "hello\n자바\n" 이 들어 있다
System.out.println(f.length());
```
`13` 이 나옵니다. 글자 수는 9개(`hello`+개행+`자바`+개행 = 5+1+2+1)인데 왜 13일까요?
step24 문제 2와 같은 이야기입니다.

### 문제 3 — 경로 메서드
```java
File f = new File("src/step25/Data.java");
System.out.println(f.getName());
System.out.println(f.getParent());
System.out.println(f.getPath());
System.out.println(f.getAbsolutePath());
```
`getPath()` 와 `getAbsolutePath()` 의 차이는 무엇인가요?

### 문제 4 — Serializable 없이 저장
```java
class Plain {          // Serializable 을 구현하지 않음
    int x = 1;
}

oos.writeObject(new Plain());
```
컴파일은 되고 **실행할 때** 죽습니다. 예외 이름을 쓰세요.

```
java.io.NotSerializableException: Plain
```

`Serializable` 인터페이스에는 **메서드가 하나도 없습니다.** 그런데도 구현을 요구하는
이유는 무엇일까요? (힌트: "이 클래스는 저장해도 됩니다"라는 표시)

### 문제 5 — transient ⭐
```java
class Student implements Serializable {
    String name;
    int score;
    transient String password;
    static String school = "자바고";
}

Student s = new Student("지니", 95, "secret1234");
// 저장 → 불러오기
```

```
저장 전: Student{name=지니, score=95, password=secret1234, school=자바고}
불러온 후: Student{name=지니, score=95, password=null, school=???}
```

`password` 가 `null` 이 된 이유는? 그리고 저장과 불러오기 **사이에**
`Student.school = "바뀐학교";` 를 하면 불러온 객체의 `school` 은 무엇일까요?

`transient` 를 쓰는 실제 상황을 두 가지 들어보세요.

### 문제 6 — 같은 객체인가
```java
Student s = new Student("지니", 95, "secret");
// 저장
Student loaded = (Student) ois.readObject();

System.out.println(s == loaded);
System.out.println(s.name.equals(loaded.name));
```
step02·step07의 `==` vs `equals` 가 여기서 또 나옵니다.

그리고 `readObject()` 의 반환 타입이 `Object` 라서 `(Student)` 캐스팅이 필요합니다.
이게 step08의 **다운캐스팅**입니다. 캐스팅을 잘못하면 무슨 예외가 나나요?

### 문제 7 — serialVersionUID ⭐
`serialVersionUID = 1L` 로 저장한 파일을, 클래스를 고쳐서 `2L` 로 만든 뒤 읽으면
이렇게 죽습니다.

```
java.io.InvalidClassException: Data; local class incompatible:
stream classdesc serialVersionUID = 1, local class serialVersionUID = 2
```

`serialVersionUID` 를 **직접 안 쓰면** 자바가 클래스 내용을 보고 자동으로 계산합니다.
그게 왜 위험한지 설명하세요. (힌트: 필드를 하나 추가하면 값이 바뀝니다)

### 문제 8 — 생성자를 안 거친다
```java
class Data implements Serializable {
    int x;
    Data() {
        System.out.println("생성자 호출됨");
        x = 100;
    }
}
```
저장했다가 역직렬화하면 **"생성자 호출됨" 이 찍히지 않습니다.**

객체가 생성자 없이 만들어진다는 뜻인데, 이게 왜 문제가 될 수 있을까요?
(step06에서 생성자로 **값을 검증**하는 과제를 했다면 그 검증이 통째로 건너뛰어집니다)

### 문제 9 — Files 로 바꿔 쓰기
아래를 `Files`/`Path` 로 바꿔 쓰세요.

```java
// 전체 읽기
BufferedReader br = new BufferedReader(new FileReader("a.txt"));
StringBuilder sb = new StringBuilder();
String line;
while ((line = br.readLine()) != null) sb.append(line).append("\n");
br.close();
```

몇 줄이 몇 줄로 줄어드나요?

## 4. 손코딩 과제

`src/step25/` 에 씁니다.

> **파일 생성 위치** — step24와 같습니다. 상대 경로는 프로젝트 루트 기준이라
> `"src/step25/data.ser"` 처럼 적으면 폴더 안에 생깁니다.
> `.ser` 파일은 바이너리라 깃에 올릴 이유가 없습니다. `.gitignore` 에
> `*.ser` 를 넣어두면 깔끔합니다.

### 과제 1 — `FileInfo.java`

파일 하나의 정보를 전부 출력하세요.

```java
File f = new File("src/step25/FileInfo.java");   // 자기 자신
```

출력할 것: 이름 / 경로 / 절대경로 / 크기 / 파일인지 / 디렉토리인지 / 읽기·쓰기 가능 여부 /
마지막 수정 시각 (step13의 `LocalDateTime` 으로 변환해서 사람이 읽을 수 있게)

그다음 **없는 파일**로 같은 코드를 돌려서 어떤 값이 나오는지 비교하세요.

### 과제 2 — `ListJavaFiles.java`

디렉토리를 순회해서 `.java` 파일만 찾으세요.

1. `src/` 안의 **직계 목록**만 출력 (`listFiles()`)
2. 하위 폴더까지 **전부 뒤져서** `.java` 파일만 출력

2번은 재귀가 필요합니다. 폴더를 만나면 그 안으로 다시 들어가는 구조입니다.

```java
static void findJava(File dir) {
    File[] files = dir.listFiles();
    if (files == null) return;          // 이 줄이 왜 필요한지 주석으로
    for (File f : files) {
        if (f.isDirectory()) findJava(f);
        else if (f.getName().endsWith(".java")) System.out.println(f.getPath());
    }
}
```

**파일 개수와 전체 크기 합계**도 같이 출력하세요.

> `listFiles()` 가 `null` 을 반환하는 경우가 있습니다. 그 줄이 없으면 step05에서 만난
> `NullPointerException` 이 납니다.

### 과제 3 — `StudentSave.java` ⭐

step05~09에서 만든 `Student` 를 파일에 저장했다가 불러오세요.

1. `Student` 에 `implements Serializable` 추가
2. `private static final long serialVersionUID = 1L;` 추가
3. 학생 3명을 만들어 **`List<Student>` 통째로** 저장
4. 프로그램을 다시 실행하면 저장된 목록을 불러와 출력

```
=== 저장 ===
지니 / 95
민수 / 82
서연 / 71
파일 크기 = ??? 바이트

=== 불러오기 ===
지니 / 95
민수 / 82
서연 / 71
```

`List` 를 통째로 저장할 수 있는 이유는 `ArrayList` 가 이미 `Serializable` 을
구현하고 있기 때문입니다. `unzip -p $JAVA_HOME/lib/src.zip 'java.base/java/util/ArrayList.java'`
로 선언부를 직접 확인해보세요. step00에서 배운 그 명령입니다.

### 과제 4 — `TransientTest.java`

`transient` 효과를 눈으로 확인하세요.

- 필드 4개: 일반 필드 2개, `transient` 1개, `static` 1개
- 저장 전 상태 출력
- 저장 → **`static` 필드 값을 바꾸기** → 불러오기
- 불러온 뒤 상태 출력

```
저장 전:   name=지니, score=95, password=secret1234, school=자바고
불러온 후: name=지니, score=95, password=null,       school=바뀐학교
```

`password` 는 왜 `null` 이고 `school` 은 왜 바뀐 값인지 **주석으로 두 줄** 남기세요.
둘의 이유가 다릅니다.

### 과제 5 — `VersionMismatch.java`

문제 7을 직접 재현하세요. step00의 "일부러 에러 내기"와 같은 방식입니다.

1. `serialVersionUID = 1L` 인 클래스로 저장
2. 클래스를 고쳐 **필드를 하나 추가**하고 `serialVersionUID = 2L` 로 변경
3. 이전 파일을 읽으면 `InvalidClassException` 발생
4. 에러 메시지 전문을 `notes/errors.md` 에 기록

그다음 `serialVersionUID` 를 **1L 로 되돌리고** 필드는 추가한 채로 읽어보세요.
어떻게 되는지, 추가된 필드에는 무엇이 들어있는지 확인하세요.

### 과제 6 — `SimpleDB.java` (선택)

지금까지 배운 것을 합친 미니 프로그램입니다.

```
1. 학생 추가
2. 전체 목록
3. 이름으로 검색
4. 저장하고 종료
```

- `Scanner` 또는 `BufferedReader` 로 메뉴 입력 (step03·step24)
- `ArrayList<Student>` 로 관리 (step14)
- 검색 결과는 `Optional` 로 반환 (step23)
- 종료할 때 직렬화로 저장, 시작할 때 불러오기 (step25)
- 파일이 없는 첫 실행도 처리 (step10 예외처리)

**다시 실행했을 때 이전 데이터가 남아 있으면** 성공입니다.

---

## 직렬화 주의점

실무에서는 자바 직렬화를 **잘 안 씁니다.** 이유를 알아두면 좋습니다.

1. **자바끼리만 됩니다.** 다른 언어·시스템과는 못 주고받습니다. 그래서 JSON 을 씁니다.
2. **클래스가 바뀌면 깨집니다.** 문제 7에서 본 그 상황이 운영 중에 터집니다.
3. **보안 문제가 있습니다.** 역직렬화는 생성자를 건너뛰고 객체를 만들기 때문에,
   조작된 바이트를 읽히면 의도하지 않은 코드가 실행될 수 있습니다. 실제로 유명한
   취약점들이 여기서 나왔습니다.

그래서 요즘은 **JSON 직렬화(Jackson, Gson)** 를 쓰는 게 일반적입니다.
다만 자바 직렬화를 알아야 하는 이유는 두 가지입니다.

- **원리가 같습니다.** "객체를 어떻게 저장 가능한 형태로 바꾸나"는 JSON 도 마찬가지입니다
- 오래된 코드에 남아 있어서 **읽을 줄은 알아야** 합니다

## 5. 자가 점검

- [ ] `new File(...)` 이 실제 파일을 만들지 않는다는 걸 안다
- [ ] `File` 과 `Files`/`Path` 중 무엇을 쓸지 판단할 수 있다
- [ ] 디렉토리를 재귀로 순회할 수 있다
- [ ] `listFiles()` 가 `null` 을 줄 수 있다는 걸 안다
- [ ] 객체를 파일에 저장하고 다시 불러올 수 있다
- [ ] `transient` 와 `static` 이 저장되지 않는 이유를 각각 설명할 수 있다
- [ ] `serialVersionUID` 를 직접 써야 하는 이유를 안다
- [ ] 역직렬화가 생성자를 거치지 않는다는 걸 안다

---

다음은 step26(16장 네트워킹)입니다. **책의 마지막 장**입니다.
