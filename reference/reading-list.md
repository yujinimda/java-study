# 참고 글 색인 — Inpa Dev 블로그 (단계별 배치)

출처
- [Language/Java](https://inpa.tistory.com/category/Language/Java) — 43편
- [Language/Java (JVM)](https://inpa.tistory.com/category/Language/Java%20%28JVM%29) — 13편

총 56편을 우리 커리큘럼 step00~09에 맞춰 배치했습니다. 각 단계의 `plan/stepNN.md`
「읽을 것」에도 해당 글이 직접 링크돼 있으니, 진행하면서 거기서 바로 열면 됩니다.
이 파일은 전체 지도입니다.

작성 시기는 2022~2023년입니다. 문법·개념 설명은 지금도 유효하고, 버전·설치·도구를
다루는 글에는 아래에 확인한 차이를 적어뒀습니다.

---

## step00 — 자바 시작하기 (1장)

| 글 | 메모 |
|---|---|
| [☕ JAVA 소스 코드 구조 - 한눈에 이해하기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%9E%90%EB%B0%94-%EC%86%8C%EC%8A%A4-%EC%BD%94%EB%93%9C-%EA%B5%AC%EC%A1%B0-%ED%95%9C%EB%88%88%EC%97%90-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0) | `public class` / `main` 껍데기가 왜 그렇게 생겼는지. step00 문제 4와 직결 |
| [☕ JDK / JRE / JVM 개념 & 구성 원리 총정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-JDK-JRE-JVM-%EA%B0%9C%EB%85%90-%EA%B5%AC%EC%84%B1-%EC%9B%90%EB%A6%AC-%F0%9F%92%AF-%EC%99%84%EB%B2%BD-%EC%B4%9D%EC%A0%95%EB%A6%AC) | C언어와 비교한 JVM 설명이 좋음. **아래 확인 사항 참고** |
| [☕ JDK & JRE 초간단 설치 및 환경 변수 설정](https://inpa.tistory.com/entry/JAVA-%F0%9F%93%9A-JDK-JRE-%ED%99%98%EA%B2%BD%EB%B3%80%EC%88%98-%EC%84%A4%EC%B9%98) | **아래 확인 사항 참고** |

### 확인 사항 (직접 검증한 내용)

**JDK/JRE/JVM 글의 「JDK 디렉토리 구성요소」** — `bin`, `include`, `lib` 세 개까지
설명합니다. JDK 9 이후에 생긴 `conf/`, `jmods/` 와, `jre/` 폴더가 사라진 이유(모듈 시스템)는
다루지 않습니다. 실제 JDK 21을 열어본 결과는 [`jdk-structure.md`](jdk-structure.md) 에
있으니 같이 보면 그림이 맞춰집니다. 그 글의 "JRE가 JDK 11부터 따로 제공되지 않는다"는
맞는 서술입니다(오라클이 별도 JRE 배포를 중단).

**설치 글** — 2022년 기준이라 "Java SE 8, 8u202 이하 권장"이 나옵니다. 당시 오라클 JDK
라이선스 이슈 때문이었고, 지금은 Temurin 같은 무료 배포판이 표준이라 그 제약이 없습니다.
이 맥에는 이미 Temurin 21이 깔려 있어서 설치 단계는 건너뛸 수 있습니다.
(환경변수 `JAVA_HOME` 개념 설명 자체는 그대로 유효합니다)

---

## step01 — 변수와 타입 (2장)

| 글 | 메모 |
|---|---|
| [☕ JAVA 기본 자료형 & 데이터 타입 - 한눈에 정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B8%B0%EB%B3%B8-%EC%9E%90%EB%A3%8C%ED%98%95-%EC%A2%85%EB%A5%98-%EC%B4%9D%EC%A0%95%EB%A6%AC-int-double-char-String) | 기본형 8개 표. step01 「손으로 정리할 것」 1번과 짝 |
| [☕ JAVA 변수의 기본형 & 참조형 타입 차이 이해하기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EB%B3%80%EC%88%98%EC%9D%98-%EA%B8%B0%EB%B3%B8%ED%98%95-%EC%B0%B8%EC%A1%B0%ED%98%95-%ED%83%80%EC%9E%85) | `char` vs `String` 이 왜 다른 종류인지 |
| [☕ JAVA 타입 형변환 원리 & 방법 - 완벽 정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%ED%83%80%EC%9E%85-%ED%98%95%EB%B3%80%ED%99%98-%EC%A2%85%EB%A5%98-%EB%B0%A9%EB%B2%95-%F0%9F%92%AF-%EC%B4%9D%EC%A0%95%EB%A6%AC) | step01 과제 4(CastLab) 전체 배경 |
| [☕ 실수 표현(부동 소수점) 원리 한눈에 이해하기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%8B%A4%EC%88%98-%ED%91%9C%ED%98%84%EB%B6%80%EB%8F%99-%EC%86%8C%EC%88%98%EC%A0%90-%EC%9B%90%EB%A6%AC-%ED%95%9C%EB%88%88%EC%97%90-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0) | step01 문제 4(`0.1+0.2`)의 완전한 답. 2진수 변환·IEEE 754 부분은 이론이라 나중에 다시 봐도 됨 |
| [☕ BigInteger & BigDecimal 사용법 정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-BigInteger-BigDecimal-%EC%9E%90%EB%A3%8C%ED%98%95-%EC%82%AC%EC%9A%A9%EB%B2%95-%EC%B4%9D%EC%A0%95%EB%A6%AC) | 위 글의 해결편. "돈 계산에 double 쓰지 말라"의 실제 대안 |
| [☕ 자바 Wrapper 클래스와 Boxing & UnBoxing 총정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-wrapper-class-Boxing-UnBoxing) | `int` ↔ `Integer`. step02 문제 4에서 삼항 연산자가 `Object` 가 됐던 이유 |

---

## step02 — 연산자 (3장)

| 글 | 메모 |
|---|---|
| [☕ 자바 String 타입 특징 이해하기 (String Pool & 문자열 비교)](https://inpa.tistory.com/entry/JAVA-%E2%98%95-String-%ED%83%80%EC%9E%85-%ED%95%9C-%EB%88%88%EC%97%90-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0-String-Pool-%EB%AC%B8%EC%9E%90%EC%97%B4-%EB%B9%84%EA%B5%90) | **step02 문제 6의 답.** `s1 == s2` 는 true인데 `s1 == s3` 는 false인 이유 |
| [☕ 그림으로 보는 자바 코드의 메모리 영역 (스택 & 힙)](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B7%B8%EB%A6%BC%EC%9C%BC%EB%A1%9C-%EB%B3%B4%EB%8A%94-%EC%9E%90%EB%B0%94-%EC%BD%94%EB%93%9C%EC%9D%98-%EB%A9%94%EB%AA%A8%EB%A6%AC-%EC%98%81%EC%97%AD%EC%8A%A4%ED%83%9D-%ED%9E%99) | 위 글과 한 세트. `==` 가 "같은 상자냐"를 묻는다는 게 그림으로 나옴 |

## step03 — 조건문과 반복문 (4장)

이 블로그에는 제어문(`if`/`for`/`while`)만 다루는 글이 없습니다. step03은 책 + 우리 문제로
진행하고, 대신 여기서 시간을 아껴 위 String Pool 글을 미리 읽어두면 step05가 편해집니다.

---

## step04 — 배열 (5장)

| 글 | 메모 |
|---|---|
| [☕ JAVA 배열(Array) 완벽 다루기 가이드](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%9E%90%EB%B0%94-%EB%B0%B0%EC%97%B4Array-%EB%AC%B8%EB%B2%95-%EC%9D%91%EC%9A%A9-%EC%B4%9D%EC%A0%95%EB%A6%AC) | 1·2차원 배열, `Arrays` 유틸까지 |
| [☕ String / StringBuffer / StringBuilder 차이점 & 성능 비교](https://inpa.tistory.com/entry/JAVA-%E2%98%95-String-StringBuffer-StringBuilder-%EC%B0%A8%EC%9D%B4%EC%A0%90-%EC%84%B1%EB%8A%A5-%EB%B9%84%EA%B5%90) | 반복문에서 문자열을 `+` 로 이어붙이면 안 되는 이유 |
| [☕ 자바 split / StringTokenizer - 문자열 자르기 비교](https://inpa.tistory.com/entry/JAVA-%E2%98%95-Split-StringTokenizer-%EB%AC%B8%EC%9E%90%EC%97%B4-%EC%9E%90%EB%A5%B4%EA%B8%B0-%EB%B9%84%EA%B5%90%ED%95%98%EA%B8%B0) | 문자열 → 배열 |

---

## step05~06 — 클래스와 객체 / 생성자 (6장)

| 글 | 메모 |
|---|---|
| [☕ 자바 객체 지향(OOP) 클래스 문법 - 한방 가이드](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B0%9D%EC%B2%B4-%EC%A7%80%ED%96%A5OOP-%ED%81%B4%EB%9E%98%EC%8A%A4-%EB%AC%B8%EB%B2%95-%F0%9F%92%AF-%EC%B4%9D%EC%A0%95%EB%A6%AC) | 6장 전체에 대응하는 핵심 글 |
| [☕ 그림으로 보는 자바 코드의 메모리 영역 (스택 & 힙)](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B7%B8%EB%A6%BC%EC%9C%BC%EB%A1%9C-%EB%B3%B4%EB%8A%94-%EC%9E%90%EB%B0%94-%EC%BD%94%EB%93%9C%EC%9D%98-%EB%A9%94%EB%AA%A8%EB%A6%AC-%EC%98%81%EC%97%AD%EC%8A%A4%ED%83%9D-%ED%9E%99) | 객체가 어디에 생기고 참조변수가 뭘 담는지. **여기서 필수** |
| [☕ 자바는 Call by reference 개념이 없다 ❓](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%9E%90%EB%B0%94%EB%8A%94-Call-by-reference-%EA%B0%9C%EB%85%90%EC%9D%B4-%EC%97%86%EB%8B%A4-%E2%9D%93) | 메서드에 객체를 넘길 때 무슨 일이 일어나는지 |
| [☕ 자바 가변 인수(VarArgs) - 매개변수를 동적으로](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B0%80%EB%B3%80-%EC%9D%B8%EC%88%98Varargs-%EB%A7%A4%EA%B0%9C%EB%B3%80%EC%88%98%EB%A5%BC-%EB%8F%99%EC%A0%81%EC%9C%BC%EB%A1%9C) | 오버로딩과 함께 보면 좋음 |
| [☕ JVM 내부 구조 & 메모리 영역 총정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-JVM-%EB%82%B4%EB%B6%80-%EA%B5%AC%EC%A1%B0-%EB%A9%94%EB%AA%A8%EB%A6%AC-%EC%98%81%EC%97%AD-%EC%8B%AC%ED%99%94%ED%8E%B8) | 위 「스택 & 힙」의 심화판 |
| [☕ 클래스는 언제 메모리에 로딩 & 초기화 되는가 ❓](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%ED%81%B4%EB%9E%98%EC%8A%A4%EB%8A%94-%EC%96%B8%EC%A0%9C-%EB%A9%94%EB%AA%A8%EB%A6%AC%EC%97%90-%EB%A1%9C%EB%94%A9-%EC%B4%88%EA%B8%B0%ED%99%94-%EB%90%98%EB%8A%94%EA%B0%80-%E2%9D%93) | 6장 「변수의 초기화」·`static` 과 연결 |

---

## step07 — 상속과 오버라이딩 (7장 1~3절)

| 글 | 메모 |
|---|---|
| [☕ 자바 Object 클래스와 메서드 오버라이딩](https://inpa.tistory.com/entry/JAVA-%E2%98%95-Object-%ED%81%B4%EB%9E%98%EC%8A%A4%EC%99%80-%EC%83%81%EC%9C%84-%EB%A9%94%EC%84%9C%EB%93%9C-%EC%9E%AC%EC%A0%95%EC%9D%98-%ED%99%9C%EC%9A%A9-%EC%B4%9D%EC%A0%95%EB%A6%AC) | 모든 클래스의 조상. 오버라이딩의 출발점 |
| [☕ 자바 toString 오버라이딩 - 완벽 이해하기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-toString-%EB%A9%94%EC%84%9C%EB%93%9C-%EC%9E%AC%EC%A0%95%EC%9D%98-%EC%99%84%EB%B2%BD-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0) | 객체를 `println` 했을 때 주소가 찍히는 이유 |
| [☕ 자바 equals / hashCode 오버라이딩 - 완벽 이해하기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-equals-hashCode-%EB%A9%94%EC%84%9C%EB%93%9C-%EA%B0%9C%EB%85%90-%ED%99%9C%EC%9A%A9-%ED%8C%8C%ED%97%A4%EC%B9%98%EA%B8%B0) | step02 문제 6의 `equals` 가 여기서 완결됨 |
| [☕ 자바 객체의 hashCode는 고유하지 않다 ❌](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B0%9D%EC%B2%B4%EC%9D%98-hashCode%EB%8A%94-%EA%B3%A0%EC%9C%A0%ED%95%98%EC%A7%80-%EC%95%8A%EB%8B%A4-%E2%9D%8C) | 위 글의 함정편 |
| [☕ 자바 clone 메서드 재정의 (얕은 복사 & 깊은 복사)](https://inpa.tistory.com/entry/JAVA-%E2%98%95-Object-%ED%81%B4%EB%9E%98%EC%8A%A4-clone-%EB%A9%94%EC%84%9C%EB%93%9C-%EC%96%95%EC%9D%80-%EB%B3%B5%EC%82%AC-%EA%B9%8A%EC%9D%80-%EB%B3%B5%EC%82%AC) | 참조형 복사의 함정 |

---

## step08 — 제어자와 다형성 (7장 4~6절)

| 글 | 메모 |
|---|---|
| [☕ JAVA 업캐스팅 & 다운캐스팅 - 완벽 이해하기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%97%85%EC%BA%90%EC%8A%A4%ED%8C%85-%EB%8B%A4%EC%9A%B4%EC%BA%90%EC%8A%A4%ED%8C%85-%ED%95%9C%EB%B0%A9-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0) | **다형성의 핵심.** step01의 기본형 형변환과 같은 구조라는 게 보임 |

---

## step09 — 추상클래스 · 인터페이스 · 내부클래스 (7장 7~9절)

| 글 | 메모 |
|---|---|
| [☕ 추상 클래스(Abstract) 용도 완벽 이해하기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%B6%94%EC%83%81-%ED%81%B4%EB%9E%98%EC%8A%A4Abstract-%EC%9A%A9%EB%8F%84-%EC%99%84%EB%B2%BD-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0) | 문법보다 **용도**에 초점 |
| [☕ 인터페이스(Interface) 문법 & 활용 - 완벽 가이드](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%9D%B8%ED%84%B0%ED%8E%98%EC%9D%B4%EC%8A%A4Interface%EC%9D%98-%EC%A0%95%EC%84%9D-%ED%83%84%ED%83%84%ED%95%98%EA%B2%8C-%EA%B0%9C%EB%85%90-%EC%A0%95%EB%A6%AC) | |
| [☕ 인터페이스 vs 추상클래스 용도 차이점 - 완벽 이해](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%9D%B8%ED%84%B0%ED%8E%98%EC%9D%B4%EC%8A%A4-vs-%EC%B6%94%EC%83%81%ED%81%B4%EB%9E%98%EC%8A%A4-%EC%B0%A8%EC%9D%B4%EC%A0%90-%EC%99%84%EB%B2%BD-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0) | 둘 다 배운 뒤 이 글로 정리하면 깔끔함 |
| [☕ 내부 클래스(Inner Class) 장점 & 종류 총정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EB%82%B4%EB%B6%80-%ED%81%B4%EB%9E%98%EC%8A%A4Inner-Class-%EC%9E%A5%EC%A0%90-%EC%A2%85%EB%A5%98) | |
| [☕ 내부 클래스는 static 으로 선언 안하면 큰일 난다](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%9E%90%EB%B0%94%EC%9D%98-%EB%82%B4%EB%B6%80-%ED%81%B4%EB%9E%98%EC%8A%A4%EB%8A%94-static-%EC%9C%BC%EB%A1%9C-%EC%84%A0%EC%96%B8%ED%95%98%EC%9E%90) | 위 글의 실전편 (메모리 누수) |
| [☕ 익명 클래스(Anonymous Class) 사용법 총정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%9D%B5%EB%AA%85-%ED%81%B4%EB%9E%98%EC%8A%A4Anonymous-Class-%EC%82%AC%EC%9A%A9%EB%B2%95-%EB%A7%88%EC%8A%A4%ED%84%B0%ED%95%98%EA%B8%B0) | 람다로 이어지는 다리 |

---

# 7장 이후 (이 커리큘럼 범위 밖 — 계속 갈 때의 지도)

객체지향까지 끝낸 뒤 어디로 가면 되는지 미리 그려둡니다.

## 8장 예외처리

| 글 |
|---|
| [☕ 자바 에러(Error) 와 예외 클래스(Exception) 이해하기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%97%90%EB%9F%ACError-%EC%99%80-%EC%98%88%EC%99%B8-%ED%81%B4%EB%9E%98%EC%8A%A4Exception-%F0%9F%92%AF-%EC%B4%9D%EC%A0%95%EB%A6%AC) |
| [☕ 자바 예외 처리(try catch) 문법 & 응용 정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%98%88%EC%99%B8-%EC%B2%98%EB%A6%ACException-%EB%AC%B8%EB%B2%95-%EC%9D%91%EC%9A%A9-%EC%A0%95%EB%A6%AC) |
| [☕ 자바 예외 던지기(throw) & 예외 연결(Chained Exception)](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%98%88%EC%99%B8-%EB%8D%98%EC%A7%80%EA%B8%B0throw-%EC%98%88%EC%99%B8-%EC%97%B0%EA%B2%B0Chained-Exception) |
| [☕ 자바 Try With Resource 예외 처리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%98%88%EC%99%B8-%EC%B2%98%EB%A6%AC-Try-With-Resource-%EB%AC%B8%EB%B2%95) |
| [☕ Exception Handling - 예외를 처리하는 3가지 기법](https://inpa.tistory.com/entry/JAVA-%E2%98%95-Exception-Handling-%EC%98%88%EC%99%B8%EB%A5%BC-%EC%B2%98%EB%A6%AC%ED%95%98%EB%8A%94-3%EA%B0%80%EC%A7%80-%EA%B8%B0%EB%B2%95) |
| [☕ (Un)Checked Exception과 트랜잭션의 오해와 진실](https://inpa.tistory.com/entry/JAVA-%E2%98%95-UnChecked-Exception%EA%B3%BC-%ED%8A%B8%EB%9E%9C%EC%9E%AD%EC%85%98%EC%9D%98-%EC%98%A4%ED%95%B4%EC%99%80-%EC%A7%84%EC%8B%A4) |

> step03 과제 7(`NumberGame`)에서 숫자 아닌 값을 넣으면 `InputMismatchException` 으로
> 죽는데, 그걸 막는 방법이 이 묶음입니다.

## 9~11장 (java.lang, 유용한 클래스, 컬렉션)

| 글 |
|---|
| [☕ 자바 Enum 열거형 타입 문법 & 응용 정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%97%B4%EA%B1%B0%ED%98%95Enum-%ED%83%80%EC%9E%85-%EB%AC%B8%EB%B2%95-%ED%99%9C%EC%9A%A9-%EC%A0%95%EB%A6%AC) |
| [☕ 자바 정규식(Regular Expression) 사용법 정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%A0%95%EA%B7%9C%EC%8B%9DRegular-Expression-%EC%82%AC%EC%9A%A9%EB%B2%95-%EC%A0%95%EB%A6%AC) |
| [☕ 개발자들을 괴롭히는 자바 NULL 파헤치기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B0%9C%EB%B0%9C%EC%9E%90%EB%93%A4%EC%9D%84-%EA%B4%B4%EB%A1%AD%ED%9E%88%EB%8A%94-NULL-%ED%8C%8C%ED%97%A4%EC%B9%98%EA%B8%B0) |

## 12장 제네릭

| 글 |
|---|
| [☕ 자바 제네릭(Generics) 개념 & 문법 정복하기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%A0%9C%EB%84%A4%EB%A6%ADGenerics-%EA%B0%9C%EB%85%90-%EB%AC%B8%EB%B2%95-%EC%A0%95%EB%B3%B5%ED%95%98%EA%B8%B0) |
| [☕ 자바 제네릭의 공변성 & 와일드카드 완벽 이해](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%A0%9C%EB%84%A4%EB%A6%AD-%EC%99%80%EC%9D%BC%EB%93%9C-%EC%B9%B4%EB%93%9C-extends-super-T-%EC%99%84%EB%B2%BD-%EC%9D%B4%ED%95%B4) |
| [☕ 자바 제네릭 타입 소거 컴파일 과정 알아보기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%A0%9C%EB%84%A4%EB%A6%AD-%ED%83%80%EC%9E%85-%EC%86%8C%EA%B1%B0-%EC%BB%B4%ED%8C%8C%EC%9D%BC-%EA%B3%BC%EC%A0%95-%EC%95%8C%EC%95%84%EB%B3%B4%EA%B8%B0) |
| [☕ 힙 오염 (Heap Pollution) 이란?](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%A0%9C%EB%84%A4%EB%A6%AD-%ED%9E%99-%EC%98%A4%EC%97%BC-Heap-Pollution-%EC%9D%B4%EB%9E%80) |

## 14장 람다 & 스트림

| 글 |
|---|
| [☕ 람다 표현식(Lambda Expression) 완벽 정리](https://inpa.tistory.com/entry/%E2%98%95-Lambda-Expression) |
| [☕ 함수형 인터페이스 표준 API 총정리](https://inpa.tistory.com/entry/%E2%98%95-%ED%95%A8%EC%88%98%ED%98%95-%EC%9D%B8%ED%84%B0%ED%8E%98%EC%9D%B4%EC%8A%A4-API) |
| [☕ 함수 디스크립터(Function Descriptor) 란?](https://inpa.tistory.com/entry/%F0%9F%9A%80-Function-Descriptor) |
| [☕ 람다식을 더 짧게 - 메소드::참조 문법](https://inpa.tistory.com/entry/JAVA8-%E2%98%95-%EB%9E%8C%EB%8B%A4%EC%8B%9D%EC%9D%84-%EB%8D%94-%EC%A7%A7%EA%B2%8C-%EB%A9%94%EC%86%8C%EB%93%9C-%EC%B0%B8%EC%A1%B0Method-Reference) |
| [☕ 람다식 리팩토링 하기 (Comparator 축약 원리)](https://inpa.tistory.com/entry/%E2%98%95-%EB%9E%8C%EB%8B%A4%EC%8B%9D-%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81-Comparator) |

## 심화 (직렬화 · 리플렉션 · GC)

| 글 |
|---|
| [☕ 자바 직렬화(Serializable) - 완벽 마스터하기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%A7%81%EB%A0%AC%ED%99%94Serializable-%EC%99%84%EB%B2%BD-%EB%A7%88%EC%8A%A4%ED%84%B0%ED%95%98%EA%B8%B0) |
| [☕ 자바 역직렬화 방어 기법 - 총정리 모음](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B0%9D%EC%B2%B4-%EC%97%AD%EC%A7%81%EB%A0%AC%ED%99%94-%EB%B0%A9%EC%96%B4-%EA%B8%B0%EB%B2%95-%EC%B4%9D%EC%A0%95%EB%A6%AC-%EB%AA%A8%EC%9D%8C) |
| [☕ 싱글톤 객체가 깨져버리는 경우 (역직렬화/리플렉션)](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%8B%B1%EA%B8%80%ED%86%A4-%EA%B0%9D%EC%B2%B4-%EA%B9%A8%EB%9C%A8%EB%A6%AC%EB%8A%94-%EB%B0%A9%EB%B2%95-%EC%97%AD%EC%A7%81%EB%A0%AC%ED%99%94-%EB%A6%AC%ED%94%8C%EB%A0%89%EC%85%98) |
| [☕ 누구나 쉽게 배우는 Reflection API 사용법](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EB%88%84%EA%B5%AC%EB%82%98-%EC%89%BD%EA%B2%8C-%EB%B0%B0%EC%9A%B0%EB%8A%94-Reflection-API-%EC%82%AC%EC%9A%A9%EB%B2%95) |
| [☕ 누구나 쉽게 배우는 Dynamic Proxy 다루기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EB%88%84%EA%B5%AC%EB%82%98-%EC%89%BD%EA%B2%8C-%EB%B0%B0%EC%9A%B0%EB%8A%94-Dynamic-Proxy-%EB%8B%A4%EB%A3%A8%EA%B8%B0) |
| [☕ 가비지 컬렉션 GC 동작 원리 & 종류 총정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B0%80%EB%B9%84%EC%A7%80-%EC%BB%AC%EB%A0%89%EC%85%98GC-%EB%8F%99%EC%9E%91-%EC%9B%90%EB%A6%AC-%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98-%F0%9F%92%AF-%EC%B4%9D%EC%A0%95%EB%A6%AC) |
| [☕ 가비지 컬렉션 GC 튜닝 절차 맛보기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B0%80%EB%B9%84%EC%A7%80-%EC%BB%AC%EB%A0%89%EC%85%98-GC-%ED%8A%9C%EB%8B%9D-%EB%A7%9B%EB%B3%B4%EA%B8%B0) |

---

## 읽고 나서 하면 좋은 것

글을 읽었으면 **`jshell` 로 바로 확인**해보세요. 예를 들어 부동소수점 글을 읽은 뒤:

```
$ jshell
jshell> 0.1 + 0.2
$1 ==> 0.30000000000000004
jshell> /exit
```

읽은 걸 손으로 한 번 두드리면 훨씬 오래 남습니다. `jshell` 은 `notes/errors.md` 를
채우기에도 편합니다.
