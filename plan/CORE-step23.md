# step23 — Optional · collect · 그룹화

**책 범위: 14장 「람다와 스트림」 5~6절**

step22에서 스트림으로 걸러내고 변환하는 것까지 했습니다. 그런데 `findFirst()` 를 쓰면
결과가 값이 아니라 `Optional` 이라는 낯선 상자로 나옵니다. 그 상자가 무엇인지,
그리고 스트림의 결과를 **원하는 모양으로 모으는** `collect()` 가 이 단계의 내용입니다.

`Optional` 은 step05에서 만난 `NullPointerException` 에 대한 자바의 대답이기도 합니다.

---

## 1. 읽을 것

- 14.5 Optional
  - `Optional<T>` 와 `OptionalInt`·`OptionalLong`·`OptionalDouble`
  - 생성: `of()`, `ofNullable()`, `empty()`
  - 꺼내기: `get()`, `orElse()`, `orElseGet()`, `orElseThrow()`
  - 확인: `isPresent()`, `isEmpty()`, `ifPresent()`
  - 가공: `map()`, `filter()`, `flatMap()`
- 14.6 스트림의 최종연산 — `collect()` 와 `Collectors`
  - 변환: `toList()`, `toSet()`, `toMap()`
  - 통계: `counting()`, `summingInt()`, `averagingInt()`, `summarizingInt()`
  - 문자열 결합: `joining()`
  - **그룹화: `groupingBy()`, `partitioningBy()`**
  - 리듀싱: `reducing()`

### 함께 읽을 글 ([Inpa Dev](https://inpa.tistory.com/category/Language/Java))

- [☕ 개발자들을 괴롭히는 자바 NULL 파헤치기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B0%9C%EB%B0%9C%EC%9E%90%EB%93%A4%EC%9D%84-%EA%B4%B4%EB%A1%AD%ED%9E%88%EB%8A%94-NULL-%ED%8C%8C%ED%97%A4%EC%B9%98%EA%B8%B0)
  — **이 단계의 배경 글.** `null` 이 왜 문제이고 `Optional` 이 무엇을 해결하는지.
  step05에서 만난 `NullPointerException` 이 여기서 정리됩니다.

## 2. 손으로 정리할 것

1. **`Optional` 은 "값이 없을 수도 있다"를 타입으로 드러낸 것.**

   ```
   String  name          →  null 일 수도 있는데 코드만 봐선 모른다
   Optional<String> name →  없을 수 있다고 타입이 말해준다
   ```

   JS의 옵셔널 체이닝과 목적이 같습니다. 문법만 다릅니다.

   ```js
   // JavaScript
   const city = user?.address?.city ?? "없음";
   ```
   ```java
   // Java
   String city = Optional.ofNullable(user)
                         .map(User::getAddress)
                         .map(Address::getCity)
                         .orElse("없음");
   ```

2. **생성 메서드 3개의 차이.** 특히 `of()` 에 `null` 을 넣으면 어떻게 되는지.

   | 메서드 | 값이 있을 때 | 값이 `null` 일 때 |
   |---|---|---|
   | `Optional.of(v)` | `Optional[v]` | |
   | `Optional.ofNullable(v)` | `Optional[v]` | |
   | `Optional.empty()` | — | |

3. **`orElse` 와 `orElseGet` 의 차이**를 한 줄씩. (아래 문제 3에서 확인합니다)

4. **`groupingBy` 가 만드는 것.** 반환 타입을 빈칸에 채우세요.

   ```java
   Map<____, ____> result = list.stream().collect(Collectors.groupingBy(분류기준));
   ```

5. **Optional 안티패턴 3가지.** 왜 하면 안 되는지 한 줄씩.
   - `optional.get()` 을 바로 부르기
   - 필드 타입으로 `Optional` 쓰기
   - 매개변수 타입으로 `Optional` 쓰기

## 3. 문제

### 문제 1 — Optional 생성과 출력
```java
Optional<String> o1 = Optional.of("hello");
Optional<String> o2 = Optional.empty();
Optional<String> o3 = Optional.ofNullable(null);

System.out.println(o1);
System.out.println(o2);
System.out.println(o3);
System.out.println(o1.isPresent());
System.out.println(o2.isEmpty());
```
`Optional` 을 그냥 출력하면 무엇이 찍히나요? `toString()` 이 오버라이딩돼 있습니다(step07).

### 문제 2 — of 와 ofNullable
```java
Optional<String> a = Optional.ofNullable(null);   // ①
Optional<String> b = Optional.of(null);           // ②
```
①은 되고 ②는 실행 중 죽습니다. **예외 이름**을 쓰세요.
그리고 `of()` 가 일부러 그렇게 만들어진 이유를 한 줄로.

### 문제 3 — orElse vs orElseGet ⭐
```java
static String expensive() {
    System.out.println("  >> expensive() 실행됨");
    return "계산된 기본값";
}

Optional<String> has = Optional.of("값있음");

System.out.println(has.orElse(expensive()));
System.out.println(has.orElseGet(() -> expensive()));
```
**값이 있는데도 한쪽은 `expensive()` 가 실행됩니다.** 어느 쪽이고 왜인가요?

step02의 단축평가와 같은 이야기입니다. 무거운 기본값이나 DB 조회를 기본값으로 쓸 때
어느 것을 골라야 하는지 한 줄로 쓰세요.

### 문제 4 — map 과 filter
```java
Optional<String> o1 = Optional.of("hello");
Optional<String> o2 = Optional.empty();

System.out.println(o1.map(String::toUpperCase));
System.out.println(o2.map(String::toUpperCase));
System.out.println(o2.map(String::toUpperCase).orElse("없음"));
System.out.println(o1.filter(s -> s.length() > 10));
```
비어 있는 `Optional` 에 `map` 을 걸면 어떻게 되나요? **`null` 체크 없이 이어 쓸 수 있는
이유**가 여기 있습니다.

### 문제 5 — 안티패턴 찾기
```java
Optional<Student> found = repository.findByName("지니");
System.out.println(found.get().getScore());
```
컴파일도 되고 값이 있으면 잘 돕니다. 그런데 **`Optional` 을 쓴 의미가 없습니다.**
왜인지, 그리고 어떻게 고치는지 쓰세요.

### 문제 6 — Collectors 기본
```java
record Student(String name, int score) {}

List<Student> students = List.of(
    new Student("지니", 95),
    new Student("민수", 82),
    new Student("서연", 71),
    new Student("현우", 88),
    new Student("하윤", 55)
);

System.out.println(students.stream().map(Student::name)
    .collect(Collectors.joining(", ")));
System.out.println(students.stream().collect(Collectors.counting()));
System.out.println(students.stream().collect(Collectors.summingInt(Student::score)));
System.out.println(students.stream().collect(Collectors.averagingInt(Student::score)));
```
`counting()` 의 반환 타입이 `int` 가 아니라는 점도 확인하세요.

### 문제 7 — groupingBy ⭐
```java
Map<String, Long> countByGrade = students.stream()
    .collect(Collectors.groupingBy(
        s -> s.score() >= 90 ? "A" : s.score() >= 80 ? "B" : s.score() >= 70 ? "C" : "F",
        Collectors.counting()
    ));
System.out.println(countByGrade);
```
step02의 삼항 연산자 중첩(과제 6의 `Grade`)이 여기서 분류 기준으로 쓰입니다.

결과가 `{A=1, B=2, C=1, F=1}` 로 나왔다면, **이 순서가 보장되는지** 생각해보세요.
`groupingBy` 가 기본으로 만드는 `Map` 의 구현체는 무엇인가요? (step15)

### 문제 8 — partitioningBy
```java
Map<Boolean, List<String>> pass = students.stream()
    .collect(Collectors.partitioningBy(
        s -> s.score() >= 80,
        Collectors.mapping(Student::name, Collectors.toList())
    ));
System.out.println(pass);
```
`groupingBy` 로도 같은 걸 할 수 있는데 `partitioningBy` 가 따로 있는 이유는?
(힌트: 해당하는 게 하나도 없을 때 키가 어떻게 되는지)

### 문제 9 — findFirst 와 Optional
```java
Optional<Student> first = students.stream().filter(s -> s.score() > 90).findFirst();
Optional<Student> none  = students.stream().filter(s -> s.score() > 99).findFirst();

System.out.println(first);
System.out.println(none);
System.out.println(none.map(Student::name).orElse("없음"));
```
`findFirst()` 가 값을 직접 반환하지 않고 `Optional` 로 감싸는 이유를 한 줄로 쓰세요.

## 4. 손코딩 과제

`src/step23/` 에 씁니다.

### 과제 1 — `NpeToOptional.java`

먼저 **NPE 가 나는 코드**를 직접 만들어 확인하세요.

```java
class Address { String city; }
class User { String name; Address address; }

User user = new User();      // address 는 null
System.out.println(user.address.city);   // 여기서 죽는다
```

예외 이름과 메시지를 `notes/errors.md` 에 적은 뒤, 아래 세 가지로 각각 고치세요.

1. `if (user.address != null)` 로 방어
2. `Optional.ofNullable(...).map(...).orElse(...)` 로 변환
3. 값이 없으면 예외를 던지도록 `orElseThrow()`

**세 방법의 코드 길이와 읽기 쉬움을 비교해서 주석으로 한 줄** 남기세요.

> JDK 14부터 NPE 메시지가 아주 친절해졌습니다. 어느 부분이 `null` 인지까지 알려줍니다.
> 실제로 출력을 확인해보세요.

### 과제 2 — `OrElseLab.java`

문제 3을 직접 돌려보는 실험실입니다.

- `expensive()` 메서드가 호출될 때 콘솔에 표시를 남기도록 만드세요
- 값이 **있을 때** / **없을 때** 각각에 대해 `orElse` 와 `orElseGet` 을 모두 호출
- 총 4가지 경우의 `expensive()` 실행 여부를 표로 정리해서 주석으로 남기세요

|  | 값이 있을 때 | 값이 없을 때 |
|---|---|---|
| `orElse(expensive())` | | |
| `orElseGet(() -> expensive())` | | |

### 과제 3 — `GradeGroup.java` ⭐

step05~06에서 만든 `Student` 를 다시 씁니다. (또는 `record` 로 간단히 만들어도 됩니다)

학생 8명 정도를 리스트에 담고 아래를 모두 출력하세요.

1. 이름만 뽑아 `", "` 로 이어붙이기 (`joining`)
2. 등급별(A/B/C/F) **인원수** (`groupingBy` + `counting`)
3. 등급별 **학생 이름 목록** (`groupingBy` + `mapping`)
4. 등급별 **평균 점수** (`groupingBy` + `averagingInt`)
5. 80점 기준 **합격/불합격 분리** (`partitioningBy`)
6. 전체 통계 한 줄 (`summarizingInt`)

```
이름: 지니, 민수, 서연, 현우, 하윤
등급별 인원: {A=1, B=2, C=1, F=1}
등급별 명단: {A=[지니], B=[민수, 현우], C=[서연], F=[하윤]}
등급별 평균: {A=95.0, B=85.0, C=71.0, F=55.0}
합격/불합격: {false=[서연, 하윤], true=[지니, 민수, 현우]}
전체 통계: IntSummaryStatistics{count=5, sum=391, min=55, average=78.200000, max=95}
```

> 등급 분류 기준을 **메서드로 빼내면** `groupingBy(this::toGrade)` 처럼 읽기 좋아집니다.
> step22의 메서드 참조가 여기서 쓰입니다.

### 과제 4 — `FindStudent.java`

`Optional` 을 반환하는 메서드를 직접 만드세요.

```java
static Optional<Student> findByName(List<Student> list, String name) {
    // 스트림으로 찾아서 Optional 로 반환
}
```

호출하는 쪽에서 세 가지 방식으로 처리하세요.

- `ifPresent()` — 있으면 출력, 없으면 아무것도 안 함
- `ifPresentOrElse()` — 있으면 출력, 없으면 "없습니다" 출력
- `orElseThrow()` — 없으면 예외 (step10의 사용자정의 예외를 써도 좋습니다)

**`get()` 은 한 번도 쓰지 마세요.** 그게 이 과제의 조건입니다.

### 과제 5 — `WordStat.java`

문자열 한 덩어리를 받아 통계를 내세요.

```java
String text = "the quick brown fox jumps over the lazy dog the end";
```

1. 단어별 등장 횟수 (`groupingBy` + `counting`)
2. 길이별로 단어 묶기 (`groupingBy(String::length)`)
3. 가장 긴 단어 (`max` → `Optional` 반환)
4. 3글자 이하 단어만 `", "` 로 이어붙이기

step15의 `HashMap` 으로 직접 세던 것을 **한 줄로** 할 수 있다는 걸 확인하는 과제입니다.

## 5. 자가 점검

- [ ] `Optional` 이 무엇을 타입으로 드러내는지 설명할 수 있다
- [ ] `of()` 와 `ofNullable()` 을 구분해서 쓸 수 있다
- [ ] `orElse` 와 `orElseGet` 의 차이를 실행 결과로 보여줄 수 있다
- [ ] 비어 있는 `Optional` 에 `map` 을 걸어도 안전한 이유를 안다
- [ ] `get()` 을 바로 부르면 안 되는 이유를 안다
- [ ] `groupingBy` 로 분류 + 집계를 한 번에 할 수 있다
- [ ] `groupingBy` 와 `partitioningBy` 를 구분할 수 있다
- [ ] `collect(Collectors.joining())` 으로 문자열을 조립할 수 있다

---

다음은 step24(15장 입출력 스트림)입니다. **이름은 스트림인데 14장 스트림과 전혀 다른
것**이라 도입부부터 주의가 필요합니다.
