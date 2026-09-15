# step00 — 자바 시작하기, 그리고 컴파일 사이클

**책 범위: 1장 「자바를 시작하기 전에」 전체**

이 단계의 목표는 문법이 아니라 **"내가 쓴 글자가 어떻게 실행되는가"** 를 손으로 그릴 수
있게 되는 것입니다. 여기가 흐릿하면 뒤에 나오는 모든 에러 메시지가 마법처럼 보입니다.

---

## 1. 읽을 것

- 1.1 자바란?: 객체지향언어 쉬운언어 어디서든 사용하기 쉬운언어여서 20년동안 사랑받는언어 컴파일이 필요없다.
- 1.2 자바의 특징 (특히 **JVM** 과 "플랫폼 독립적" 이 무슨 뜻인지)  어디서나 실행가능하다 / 자바는 어느 플랫폼에 상관없이 개발언어로 사용될수있다 왜냐면 jvm이 중간에서 클래스로 컴파일된걸 한번 더 그 플랫폼에 맞게 번역해주기 때문에
- 1.3 JDK 설치 → **설치는 이미 끝나 있으니 읽고 넘어가세요** (Temurin JDK 21 사용 중)
  - 책의 **JDK 디렉토리 구성** 그림은 JDK 8 기준이라 지금 내 폴더와 다릅니다.
  실제 내 JDK를 열어본 결과는 [`reference/jdk-structure.md`](../reference/jdk-structure.md)
  에 정리해뒀습니다. (`jre/` 가 왜 없어졌는지, `src.zip` 으로 표준 라이브러리
  소스코드를 읽는 방법까지)
- 1.4 자바로 프로그램 작성하기 (`javac`, `java` 명령)
- 1.5 자바 프로그램의 실행 순서 / 주석

### 함께 읽을 글 ([Inpa Dev](https://inpa.tistory.com/category/Language/Java))

- [☕ JAVA 소스 코드 구조 - 한눈에 이해하기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%9E%90%EB%B0%94-%EC%86%8C%EC%8A%A4-%EC%BD%94%EB%93%9C-%EA%B5%AC%EC%A1%B0-%ED%95%9C%EB%88%88%EC%97%90-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0)
— `public class` / `main` 껍데기가 왜 그렇게 생겼는지. **아래 문제 4와 직결됩니다.**
- [☕ JDK / JRE / JVM 개념 &amp; 구성 원리 총정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-JDK-JRE-JVM-%EA%B0%9C%EB%85%90-%EA%B5%AC%EC%84%B1-%EC%9B%90%EB%A6%AC-%F0%9F%92%AF-%EC%99%84%EB%B2%BD-%EC%B4%9D%EC%A0%95%EB%A6%AC)
— C언어와 비교해서 JVM이 왜 필요한지 설명하는 부분이 좋습니다. 책의
「JDK 디렉토리 구성요소」 그림도 이 글에 있습니다.
단, 폴더 설명이 `bin`·`include`·`lib` 까지라 JDK 9 이후 변화(`conf/`, `jmods/`,
`jre/` 삭제)는 안 나옵니다. 그 부분은 아래 1.3의 `jdk-structure.md` 와 함께 보세요.
- [☕ JDK &amp; JRE 초간단 설치 및 환경 변수 설정](https://inpa.tistory.com/entry/JAVA-%F0%9F%93%9A-JDK-JRE-%ED%99%98%EA%B2%BD%EB%B3%80%EC%88%98-%EC%84%A4%EC%B9%98)
— `JAVA_HOME` 환경변수 개념 설명용. 2022년 글이라 "Java SE 8 권장" 부분은 지금
기준과 다릅니다(당시 오라클 라이선스 이슈. 지금은 Temurin이 표준).
이 맥에는 Temurin 21이 이미 깔려 있습니다.

전체 글 목록과 단계별 배치: [`reference/reading-list.md`](../reference/reading-list.md)

## 2. 손으로 정리할 것

노트에 아래 3개를 직접 쓰세요.

1. **컴파일 사이클 그림.** 아래를 보지 말고 먼저 그려보고, 다 그린 뒤에 비교하세요.
  ```
   Hello.java  ──javac──▶  Hello.class  ──java──▶  실행
   (사람이 읽는 소스)      (JVM이 읽는 바이트코드)     (JVM 안에서 돈다)
  ```
2. `**javac` 와 `java` 의 차이**를 한 문장씩. "javac는 _바이트(클래스)__를 만든다 / java는  _바이트코드__를 실행한다"
3. **JVM 이 중간에 끼어 있어서 얻는 것**을 한 문장으로. (힌트: 같은 `.class` 파일을  
에서도 윈도우에서도 돌릴 수 있다는 게 왜 가능한가): 어떤 플랫폼에서도 작업 + 작동가능하다.

## 3. 출력 예상 / 에러 찾기 문제

**컴파일하지 말고** 노트에 손으로 답을 적으세요. 다 적은 뒤에 확인합니다.

### 문제 1

`.java` 파일과 `.class` 파일의 차이는? 각각을 만드는 명령은?  
자바파일은 컴파일전 클래스파일은 컴파일후  
자바파일은 사람이 손으로 쓰는거 클래스는 컴파일 되어서 jvm에서 읽을수있는

.java 파일은 `.class를 만들고` `.class`은 클래스를 실행한다

### 문제 2

`Hello.java` 라는 파일에 아래처럼 썼습니다. 문제가 있나요? 있다면 무엇인가요?  
hello -&gt; Hello로 맞춰야하나? 잘 모르겠네

```java
public class hello {
    public static void main(String[] args) {
        System.out.println("hi");
    }
}
```

### 문제 3

아래 코드에는 **잘못된 곳이 4군데** 있습니다. 모두 찾고 이유를 쓰세요.

그리고 다 찾은 뒤에 실제로 컴파일해보세요. **javac가 알려주는 에러는 당신이 찾은 4개와
다를 겁니다.** 왜 그런지도 생각해보세요. (이 문제의 진짜 목적입니다)

```java
public class Quiz {
    public static void main(String[] args) {
        System.out.println("첫 번째") //; 이거
        System.out.Println("두 번째"); // P 이거
        system.out.println("세 번째"); // s 이거
        System.out.println('네 번째'); // ' 이거 
    }
}
```

### 문제 4

아래 빈칸을 채워서 main 메서드 선언을 완성하세요. 외워서 쓸 수 있어야 합니다.

```java
__public____ ___static___ ___void___ main(____String__[] args) {
```

### 문제 5

주석 3가지 (`//`, `/* */`, `/** */`) 를 각각 한 줄 예시와 함께 쓰세요.  

// 간단하게 알리고 싶을때 주석  

/* 약간 단위를 나타낼때 목차를 나타낼때 주석 */  

`/** 가장 상단에 설명쓸때 쓰는 주석 */`

## 4. 손코딩 과제

**종이에 먼저 전부 쓴 다음** `src/step00/` 에 옮겨 적고 `./run` 으로 돌리세요.

### 과제 1 — `Hello.java`

자기 이름, 나이, 좋아하는 것을 각각 한 줄로 출력하는 프로그램. 출력 예:

```
이름: 지니
나이: 28
좋아하는 것: 커피
```

```bash
./run step00/Hello.java
```

### 과제 2 — `BrokenOnPurpose.java` (이게 진짜 과제입니다)

**일부러 에러를 내보세요.** 아래 4가지를 하나씩 만들어 컴파일하고, 나온 에러 메시지를
**그대로** `notes/errors.md` 에 옮겨 적으세요. 한 번에 하나씩, 확인하고 고친 뒤 다음 것.

1. 세미콜론 하나를 빼기
2. 파일명은 `BrokenOnPurpose.java` 인데 클래스명을 `Broken` 으로 쓰기
3. `System.out.println` 을 `System.out.printLn` (대문자 L) 로 쓰기
4. 닫는 중괄호 `}` 하나를 빼기

에러 메시지 4개를 모으는 게 이 단계의 진짜 결과물입니다. 초보가 막히는 지점은
문법을 몰라서가 아니라, **에러 메시지를 읽는 법을 몰라서**입니다.

특히 4번은 에러가 **엉뚱한 줄**을 가리킵니다. 그게 왜인지 생각해보세요.

### 과제 3 — `Args.java`

실행할 때 넘긴 값을 출력해보세요.

```java
System.out.println(args[0]);
```

```bash
./run step00/Args.java 안녕
```

`args` 가 뭔지 지금 완전히 이해하지 않아도 됩니다. "main이 바깥에서 값을 받는 통로" 정도만.

## 5. 자가 점검

넘어가기 전에 아래를 책 안 보고 할 수 있는지 확인하세요.

- [x] 빈 화면에서 `public class ... main ...` 껍데기를 처음부터 손으로 쓸 수 있다 public class Test { public static void main (String args[]){... } }
- [x] 파일명과 public 클래스명이 같아야 하는 이유를 설명할 수 있다 -&gt; 그래야 자바가 클래스를 매칭 시킬수있다
- [ ] `javac` 와 `java` 를 각각 언제 쓰는지 안다 -&gt; javac는 컴파일용, java는 그냥 사람이 쓰는거
- [x] 컴파일 에러 메시지에서 **줄 번호**를 찾아 그 근처를 볼 줄 안다 모르겠는데
- [x] `notes/errors.md` 에 에러 3개 이상 적혀 있다 그냥 파일 마다 주석에 달았어

