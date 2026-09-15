# JDK 디렉토리 구조 — 책 그림 vs 내 JDK 21

책이나 강의에 나오는 JDK 폴더 스크린샷은 대부분 **JDK 8 (윈도우, Oracle JDK)** 입니다.
우리가 쓰는 건 **JDK 21 (macOS, Temurin)** 이라 구조가 꽤 다릅니다.
아래는 이 저장소에서 실제로 `ls`, `du` 로 확인한 값입니다.

---

## 1. 내 JDK 21 최상위 — 실제 목록

```
/Library/Java/JavaVirtualMachines/temurin-21.jdk/Contents/Home/
├── bin/       924K    도구 29개 (javac, java, jshell ...)
├── conf/      152K    설정 파일 (보안, 로깅, 네트워크)
├── include/   224K    C언어 헤더 파일 (JNI용)
├── jmods/      79M    모듈 파일 69개 (jlink 재료)
├── legal/     2.2M    라이선스
├── lib/       249M    핵심. 표준 라이브러리 + JVM 본체
├── man/       780K    man 페이지
├── NOTICE
└── release            이 JDK의 버전 정보 (텍스트)
```

`echo $JAVA_HOME` 이 가리키는 곳이 바로 이 `Home` 폴더입니다.

## 2. JDK 8 그림과 무엇이 다른가

| | JDK 8 (책 그림) | JDK 21 (지금) | 왜 |
|---|---|---|---|
| `jre/` | **있음** | **없음** | JDK 9의 모듈 시스템 이후 폐지 |
| `lib/rt.jar` | **있음** | **없음** | `lib/modules` 로 통합 |
| `conf/` | 없음 (`jre/lib` 안에 흩어져 있었음) | **있음** | 설정 파일을 한 곳으로 모음 |
| `jmods/` | 없음 | **있음** | 모듈 시스템과 함께 추가 |
| `docs/` | 있음 | 없음 | 별도 다운로드로 분리 |
| `javafx-src.zip` | 있음 | 없음 | JavaFX가 JDK에서 분리(JDK 11부터) |
| `src.zip` | 있음 | **있음** | 그대로 있음 (아래 5번 참고) |
| `include/` | 있음 | **있음** | 그대로 |
| `bin/` | 있음 | **있음** | 도구가 늘어남 |

직접 확인해보면:
```bash
ls $JAVA_HOME/jre          # No such file or directory
ls $JAVA_HOME/lib/rt.jar   # No such file or directory
```

### `jre/` 가 왜 사라졌나

JDK 8까지는 이런 관계였습니다.

```
JDK (개발용, javac 포함)
 └── jre/ (실행용, java 포함)      ← JDK 안에 JRE가 통째로 들어 있었다
```

그리고 사용자에게 프로그램을 배포하려면 **JRE를 따로 깔게** 했습니다.
문제는 JRE가 통짜 덩어리라, "Hello World 하나 돌리는데 표준 라이브러리 전체가 필요"했습니다.

JDK 9에서 **모듈 시스템(Project Jigsaw)** 이 들어오면서 표준 라이브러리가 69개 모듈로
쪼개졌습니다. 그 결과 `jlink` 라는 도구로 **필요한 모듈만 골라 나만의 맞춤 런타임을
만드는 방식**으로 바뀌었고, 미리 만들어둔 `jre/` 폴더는 존재 이유가 없어져 삭제됐습니다.

```bash
java --list-modules | wc -l      # 69
```

> 요약: **"JRE를 따로 배포한다" → "필요한 만큼 런타임을 직접 조립한다"** 로 바뀐 것.
> 그래서 요즘은 "JRE를 설치하세요" 라는 말을 거의 듣지 않습니다.

## 3. `bin/` — 도구 29개

```
jar        javap       jdeps       jlink      jrunscript   jwebserver
jarsigner  jcmd        jfr         jmap       jshell       keytool
java       jconsole    jhsdb       jmod       jstack       serialver
javac      jdb         jimage      jpackage   jstat        rmiregistry
javadoc    jdeprscan   jinfo       jps        jstatd
```

**지금 알아야 할 건 3개뿐입니다.**

| 도구 | 하는 일 |
|---|---|
| `javac` | `.java` → `.class` **컴파일러** |
| `java` | `.class` 를 **실행** (JVM 실행기) |
| `jshell` | 한 줄씩 즉시 실행해보는 대화형 도구 (아래 참고) |

나중에 만나게 될 것들: `javadoc`(문서 생성), `jar`(배포용 묶음), `jdb`(디버거),
`jlink`/`jpackage`(배포용 런타임·설치파일 만들기), `jconsole`/`jmap`/`jstack`(성능 진단).

### `jshell` — 지금 당장 유용합니다

컴파일 없이 한 줄씩 바로 확인할 수 있습니다. **step01~02의 출력 예상 문제를
검산할 때** 파일 만들 필요 없이 이걸 쓰면 편합니다.

```
$ jshell
jshell> 7 / 2
$1 ==> 3

jshell> (char)('A' + 1)
$2 ==> 'B'

jshell> /exit
```

단, **답을 손으로 먼저 적은 다음에** 쓰세요. 너무 편해서 생각을 건너뛰기 쉽습니다.

## 4. `lib/` — 실질적 본체 (249M)

| 파일/폴더 | 크기 | 정체 |
|---|---|---|
| `modules` | **134M** | 표준 라이브러리 전체가 담긴 단일 이미지. **JDK 8의 `rt.jar` 후계자** |
| `src.zip` | 52M | 표준 라이브러리의 **소스코드** (5번 참고) |
| `server/` | 45M | **JVM 본체** (HotSpot 엔진). 실제로 바이트코드를 실행하는 그것 |
| `ct.sym` | 10M | `javac --release N` 이 옛 버전 API를 검사할 때 쓰는 정보 |
| `lib*.dylib` | — | 폰트·그래픽 등 OS에 붙는 네이티브 라이브러리 |

`System.out.println` 을 쓸 때, 그 `System` 클래스는 `lib/modules` 안에서 나옵니다.
따로 다운로드가 필요 없던 이유가 이 134MB입니다.

## 5. `lib/src.zip` — 자바 표준 라이브러리의 소스코드 (★)

여기가 제일 재밌는 부분입니다. **JDK 안에 표준 클래스들의 자바 소스가 그대로
들어 있습니다.** `String`, `System`, `Object` 같은 걸 직접 읽을 수 있습니다.

```bash
# 목록 보기
unzip -l $JAVA_HOME/lib/src.zip | grep 'java/lang/String.java'
#   195912  java.base/java/lang/String.java

# 실제로 읽기
unzip -p $JAVA_HOME/lib/src.zip 'java.base/java/lang/String.java' | less
```

실제로 열어보면 이렇게 시작합니다.

```java
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence,
               Constable, ConstantDesc {
```

`String` 이 `final` 클래스라는 것, 인터페이스 여러 개를 구현한다는 것 —
**7장(상속·인터페이스)을 배우고 나면 이 한 줄이 읽힙니다.** 그때 다시 열어보세요.
파일 크기가 195KB, 즉 5천 줄이 넘습니다. `String` 하나가 그렇습니다.

| 클래스 | 소스 크기 |
|---|---|
| `String.java` | 195 KB |
| `System.java` | 118 KB |
| `Integer.java` | 82 KB |
| `Object.java` | 28 KB |

> IDE에서 표준 클래스를 `Cmd+클릭` 하면 소스가 열리는데, 그게 바로 이 `src.zip` 을
> 읽는 것입니다.

## 6. `include/` — C언어 헤더

```
jni.h  jvmti.h  jawt.h  jdwpTransport.h  classfile_constants.h  jvmticmlr.h  darwin/
```

자바에서 C/C++ 코드를 호출해야 할 때(**JNI**, Java Native Interface) 쓰는 헤더입니다.
책 그림의 설명 "네이티브 코드 프로그래밍에 필요한 C언어 헤더 파일" 이 정확히 이겁니다.
**거의 안 씁니다.** OS 기능을 직접 건드려야 하는 특수한 경우에만 필요합니다.

## 7. `jmods/` — 모듈 파일 69개

```
java.base.jmod  java.compiler.jmod  java.datatransfer.jmod  java.desktop.jmod ...
```

`jlink` 로 맞춤 런타임을 만들 때 쓰는 **재료**입니다. 일반 개발에는 손댈 일이 없습니다.
`java.base` 가 가장 중요한 모듈로, `String`·`System`·`int` 같은 기본이 다 여기 있습니다.
그래서 `import java.lang.*` 은 **안 써도 자동으로 적용**됩니다.

## 8. macOS만의 차이 — 번들 구조

윈도우는 `C:\Program Files\Java\jdk-21\` 아래에 바로 `bin`, `lib` 가 있습니다.
macOS는 앱 번들 규칙 때문에 한 겹 더 들어갑니다.

```
temurin-21.jdk/                 ← 여기까지가 "번들"
└── Contents/
    ├── Home/                   ← JAVA_HOME. 여기서부터 bin, lib ...
    ├── Info.plist              ← 번들 메타정보
    ├── MacOS/
    └── _CodeSignature/          ← 애플 코드 서명
```

**`JAVA_HOME` 은 `Contents/Home` 까지 가리켜야 합니다.** 맥에서 자바 경로 설정이
헷갈리는 이유가 이 한 겹입니다.

---

## 직접 둘러보는 명령 모음

```bash
echo $JAVA_HOME                          # 내 JDK 위치
ls $JAVA_HOME                            # 최상위 구조
ls $JAVA_HOME/bin                        # 도구 목록
du -sh $JAVA_HOME/*/                     # 폴더별 용량
cat $JAVA_HOME/release                   # 이 JDK의 상세 버전 정보
java --list-modules | head -20           # 표준 모듈 목록
unzip -l $JAVA_HOME/lib/src.zip | head   # 표준 라이브러리 소스 목록
```

## 다른 버전과 비교해보기

이 맥에는 JDK 17, 21, 25가 함께 깔려 있습니다. `$JAVA_HOME` 대신
`$(/usr/libexec/java_home -v 17)` 을 넣으면 JDK 17 폴더를 같은 방식으로 둘러볼 수 있습니다.

```bash
ls $(/usr/libexec/java_home -v 17)/bin       # JDK 17의 도구 목록
```

실제로 17과 21의 `bin/` 을 비교해보면 **차이가 딱 하나입니다.**

| | 도구 개수 |
|---|---|
| JDK 17 | 28개 |
| JDK 21 | 29개 (`jwebserver` 추가) |

`jwebserver` 는 JDK 18에서 들어온 간단한 정적 파일 웹서버입니다.
**메이저 버전이 4개나 올라갔는데 도구는 하나만 늘었습니다.** JDK의 큰 구조는 9에서
한 번 크게 바뀐 뒤로 안정돼 있다는 뜻입니다. 그래서 책이 JDK 8 기준이어도
1~7장 학습에는 문제가 없습니다.

## 이 JDK의 정체를 확인하는 파일

```bash
cat $JAVA_HOME/release
```
```
IMPLEMENTOR="Eclipse Adoptium"
IMPLEMENTOR_VERSION="Temurin-21.0.11+10"
JAVA_VERSION="21.0.11"
JAVA_VERSION_DATE="2026-04-21"
OS_ARCH="aarch64"
MODULES="java.base java.compiler ... "     ← 포함된 모듈 69개가 전부 적혀 있다
```

`OS_ARCH="aarch64"` 는 애플 실리콘(M시리즈)용이라는 뜻입니다. 인텔 맥이면 `x86_64` 입니다.
자바 폴더가 여러 개일 때 **"이게 정확히 뭐지?"** 를 확인하는 가장 빠른 방법입니다.
