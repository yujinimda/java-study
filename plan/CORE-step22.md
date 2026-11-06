# step22 — 스트림

**책 범위: 14장 「람다와 스트림」 3~4절**

step21 에서 만든 람다를 **컬렉션에 꽂는** 단계입니다.

step14~16 에서 리스트를 다룰 때 이렇게 썼죠.

```java
List<String> result = new ArrayList<>();
for (String name : names) {
    if (name.length() == 2) result.add(name.toUpperCase());
}
```

스트림을 쓰면 이렇게 됩니다.

```java
List<String> result = names.stream()
    .filter(n -> n.length() == 2)
    .map(String::toUpperCase)
    .collect(Collectors.toList());
```

자바스크립트의 `arr.filter().map()` 과 거의 같습니다. **다만 두 가지가 다릅니다** —
자바 스트림은 **한 번 쓰면 끝**이고, **최종 연산을 붙이기 전까지는 아무 일도 안 합니다.**
이 두 가지가 이 단계의 핵심입니다.

---

## 1. 읽을 것

- 14.3 스트림(stream)
  - 스트림이란, **스트림의 특징** (일회용, 지연 연산, 원본을 바꾸지 않음)
  - **스트림 만들기** — 컬렉션, 배열, `Stream.of()`, `Stream.iterate()`/`generate()`
  - 기본형 스트림 (`IntStream`, `LongStream`, `DoubleStream`)
- 14.4 스트림의 연산
  - **중간 연산** — `filter`, `map`, `sorted`, `distinct`, `limit`, `skip`, `peek`, `flatMap`
  - **최종 연산** — `forEach`, `count`, `sum`, `reduce`, `min`/`max`,
    `anyMatch`/`allMatch`/`noneMatch`, `collect`
  - `Optional` 은 step23 에서 다룹니다

### 함께 읽을 글 ([Inpa Dev](https://inpa.tistory.com/category/Language/Java))

블로그의 14장 글은 **람다 쪽에 몰려 있어서** 스트림만 다룬 글은 목록에 없습니다.
step21 에서 읽은 글들이 스트림의 바탕이 되므로, 아직 안 읽었다면 아래 두 개를 먼저 보세요.

- [☕ 람다 표현식(Lambda Expression) 완벽 정리](https://inpa.tistory.com/entry/%E2%98%95-Lambda-Expression)
  — `filter(n -> ...)` 안에 들어가는 게 결국 람다입니다.
- [☕ 함수형 인터페이스 표준 API 총정리](https://inpa.tistory.com/entry/%E2%98%95-%ED%95%A8%EC%88%98%ED%98%95-%EC%9D%B8%ED%84%B0%ED%8E%98%EC%9D%B4%EC%8A%A4-API)
  — `filter` 는 `Predicate`, `map` 은 `Function` 을 받습니다. 표를 옆에 두고 보면
  어떤 메서드가 무엇을 받는지 예측할 수 있습니다.

전체 글 목록: [`reference/reading-list.md`](../reference/reading-list.md)

## 2. 손으로 정리할 것

1. **자바스크립트 배열 메서드와 비교 표.** 대부분 이름까지 같습니다.

   | 하는 일 | 자바스크립트 | 자바 스트림 |
   |---|---|---|
   | 걸러내기 | `arr.filter(x => ...)` | `.filter(x -> ...)` |
   | 바꾸기 | `arr.map(x => ...)` | `.map(x -> ...)` |
   | 하나씩 꺼내기 | `arr.forEach(...)` | `.forEach(...)` |
   | 누적 | `arr.reduce(...)` | `.reduce(...)` |
   | 정렬 | `arr.sort(...)` | `.sorted(...)` |
   | 개수 | `arr.length` | `.count()` |
   | 하나라도 | `arr.some(...)` | `.anyMatch(...)` |
   | 전부 | `arr.every(...)` | `.allMatch(...)` |
   | 결과 받기 | 그냥 배열로 나옴 | **`.collect()` 가 필요** |

2. **JS 와 다른 점 3가지.** 이게 이 단계에서 제일 많이 당하는 부분입니다.

   ```
   ① 일회용        한 번 쓴 스트림은 다시 못 쓴다
   ② 지연 연산      최종 연산이 붙기 전까지 아무 일도 안 한다
   ③ 원본 불변      .sorted() 해도 원본 리스트는 그대로다
   ```

   ③은 JS `arr.sort()` 가 **원본을 바꾸는 것**과 반대입니다.

3. **중간 연산 vs 최종 연산** 을 가르는 기준.

   ```
   중간 연산  →  Stream 을 돌려준다   →  계속 이어붙일 수 있다  →  여러 번 가능
   최종 연산  →  Stream 이 아닌 걸 돌려준다  →  여기서 끝난다   →  딱 한 번
   ```

   반환 타입만 보면 구분됩니다. 아래를 분류해보세요.

   ```
   filter  map  count  sorted  forEach  distinct  collect  limit  reduce  anyMatch
   ```

4. **스트림 만드는 방법 4가지**를 적으세요.

   ```java
   list.stream()                          // 컬렉션에서
   Arrays.stream(arr)                     // 배열에서
   Stream.of("a", "b", "c")               // 값을 직접
   Stream.iterate(1, n -> n * 2)          // 규칙으로 (무한 → limit 필수)
   IntStream.range(1, 5)                  // 숫자 범위
   ```

## 3. 문제

### 문제 1 — 기본 파이프라인
```java
List<Integer> nums = Arrays.asList(5, 3, 8, 1, 9, 3);

System.out.println(nums.stream().filter(n -> n > 3).map(n -> n * 10)
                       .collect(Collectors.toList()));
System.out.println(nums.stream().distinct().sorted()
                       .collect(Collectors.toList()));
System.out.println(nums.stream().limit(3).collect(Collectors.toList()));
System.out.println(nums.stream().skip(3).collect(Collectors.toList()));
System.out.println(nums.stream().count());
```

손으로 답을 적은 뒤 확인하세요. `distinct` 와 `sorted` 의 **순서**에 주의하세요.

### 문제 2 — 스트림은 일회용 ⭐
```java
List<String> names = Arrays.asList("지니", "민수", "서연");
Stream<String> s = names.stream();

System.out.println(s.count());
System.out.println(s.count());
```

두 번째 줄에서 죽습니다. 실제 메시지입니다.

```
java.lang.IllegalStateException: stream has already been operated upon or closed
```

**JS 배열은 몇 번이든 `.filter()` 를 다시 걸 수 있는데** 자바 스트림은 안 됩니다.
왜 그런지 생각해보고, 두 번 쓰려면 어떻게 해야 하는지 쓰세요.
(힌트: `names.stream()` 을 다시 부르면 됩니다. 스트림은 **데이터가 아니라 통로**입니다.)

### 문제 3 — 지연 연산 ⭐
```java
List<String> names = Arrays.asList("지니", "민수", "서연");

System.out.println("--- 중간 연산만 ---");
Stream<String> lazy = names.stream().peek(n -> System.out.println("  peek: " + n));
System.out.println("(여기까지 뭐가 찍혔나요?)");
```

실제 출력입니다.

```
--- 중간 연산만 ---
(여기까지 뭐가 찍혔나요?)
```

**`peek` 이 하나도 안 찍혔습니다.** 중간 연산만 있으면 아무 일도 일어나지 않습니다.
최종 연산이 붙어야 그제서야 데이터가 흐릅니다. 왜 이렇게 설계했을까요?
(힌트: 100만 개 중 앞 3개만 필요하면, 100만 개를 다 처리할 필요가 있나요?)

### 문제 4 — 요소 단위로 흐른다
```java
Stream.of("x", "y")
      .peek(s -> System.out.println("  peek:" + s))
      .forEach(s -> System.out.println("  forEach:" + s));
```

실제 출력입니다.

```
  peek:x
  forEach:x
  peek:y
  forEach:y
```

`peek` 이 전부 끝난 뒤 `forEach` 가 시작되는 게 **아닙니다.** 한 요소가
파이프라인 끝까지 갔다가 다음 요소가 출발합니다. 반복문으로 바꿔 쓰면
어떤 모양이 되는지 그려보세요.

### 문제 5 — `count()` 의 함정 (JDK 9+)
```java
long c = names.stream().peek(n -> System.out.println("  peek: " + n)).count();
System.out.println("count=" + c);
```

`peek` 이 찍힐 것 같지만 실제로는 이렇게 나옵니다.

```
count=3
```

`count()` 는 **"개수만 세면 되는데 굳이 요소를 흘려보낼 필요가 없다"** 고 판단해서
중간 연산을 건너뜁니다. `filter` 를 하나 끼우면 그때는 찍힙니다.

```java
names.stream().peek(...).filter(n -> n.length() == 2).count();
//   peek: 지니 / peek: 민수 / peek: 서연  ← 이번엔 찍힘
```

`filter` 가 있고 없고에 따라 왜 달라지는지 설명하세요.
(힌트: 걸러내야 할 게 있으면 개수를 미리 알 수 없습니다.)

> `peek` 은 **디버깅 용도**이고, 여기에 중요한 로직을 넣으면 안 되는 이유가 이것입니다.

### 문제 6 — 원본이 안 바뀐다
```java
List<Integer> nums = new ArrayList<>(Arrays.asList(3, 1, 2));
nums.stream().sorted().collect(Collectors.toList());
System.out.println(nums);
```

`[1, 2, 3]` 이 아니라 `[3, 1, 2]` 가 나옵니다. JS 의 `arr.sort()` 와 정반대입니다.
정렬된 결과를 쓰려면 어떻게 해야 하나요?

### 문제 7 — 최종 연산들
```java
List<Integer> nums = Arrays.asList(5, 3, 8, 1, 9, 3);

System.out.println(nums.stream().anyMatch(n -> n > 8));
System.out.println(nums.stream().allMatch(n -> n > 0));
System.out.println(nums.stream().reduce(0, Integer::sum));
System.out.println(nums.stream().max(Integer::compare).get());
```

`reduce(0, Integer::sum)` 의 `0` 은 무엇일까요? 그리고 `max()` 뒤에 `.get()` 이
붙는 이유는? (후자는 step23 에서 제대로 다룹니다.)

### 문제 8 — 기본형 스트림
```java
List<Integer> nums = Arrays.asList(5, 3, 8, 1, 9, 3);

System.out.println(nums.stream().mapToInt(Integer::intValue).sum());
System.out.println(IntStream.of(1, 2, 3, 4, 5).sum());
System.out.println(IntStream.range(1, 5).boxed().collect(Collectors.toList()));
System.out.println(IntStream.of(1, 2, 3, 4).average().getAsDouble());
```

`Stream<Integer>` 에는 `sum()` 이 없고 `IntStream` 에는 있습니다. 왜 따로 있을까요?
(힌트: step12 의 오토박싱 — 매번 `int` 를 `Integer` 로 감싸면 느려집니다.)

`IntStream.range(1, 5)` 가 5를 **포함하지 않는** 것도 확인하세요.

## 4. 손코딩 과제

`src/step22/` 에 씁니다.

### 과제 1 — `StreamBasic.java`

문제 1의 코드를 직접 돌려보고, 아래를 추가로 만드세요.

- `{5, 3, 8, 1, 9, 3}` 에서 **짝수만 골라 제곱**해서 리스트로
- **중복을 뺀 뒤 내림차순** 정렬
- **가장 큰 값 2개**만 (`sorted` + `limit` 조합)

`import java.util.stream.*;` 와 `import java.util.*;` 가 필요합니다.

### 과제 2 — `ForToStream.java` ⭐

**step14~16 에서 반복문으로 짠 것을 스트림으로 다시 쓰는 과제**입니다.

step15 과제(단어 빈도수)나 step16 과제(학생 정렬)에서 쓴 반복문 3개를 골라,
아래처럼 **나란히** 두세요.

```java
// 반복문 버전
List<String> result1 = new ArrayList<>();
for (String s : list) {
    if (s.length() > 2) result1.add(s.toUpperCase());
}

// 스트림 버전
List<String> result2 = list.stream()
    .filter(s -> s.length() > 2)
    .map(String::toUpperCase)
    .collect(Collectors.toList());

System.out.println(result1.equals(result2));   // true 가 나와야 한다
```

결과가 같은지 `equals` 로 검증하세요. **어느 쪽이 읽기 쉬운지** 주석으로 한 줄 남기고요.

> 스트림이 항상 더 좋은 건 아닙니다. 단순한 반복은 `for` 가 더 읽기 쉬울 때도 많습니다.

### 과제 3 — `StreamOnce.java`

문제 2를 직접 재현하세요.

- 스트림을 변수에 담아 두 번 쓰고 `IllegalStateException` 을 확인
- `try-catch` 로 잡아서 메시지를 출력 (step10 연결)
- 제대로 고친 버전도 같이 쓰기

```java
try {
    System.out.println(s.count());
    System.out.println(s.count());
} catch (IllegalStateException e) {
    System.out.println("예외: " + e.getMessage());
}
```

### 과제 4 — `LazyLab.java` ⭐

문제 3·4·5를 한 파일에서 전부 확인하세요.

1. 중간 연산만 → 아무것도 안 찍힘
2. `forEach` 붙임 → 요소 단위로 번갈아 찍힘
3. `peek + count` → `peek` 이 생략됨
4. `peek + filter + count` → `peek` 이 실행됨

각 결과 아래에 **왜 그런지** 주석 한 줄씩 붙이세요.
이 네 가지를 눈으로 본 것이 이 단계에서 제일 크게 남습니다.

### 과제 5 — `StudentStream.java` (step16·21 이어서)

step16·21 에서 쓴 `Student` 리스트를 스트림으로 다루세요.

```
- 평균 점수
- 80점 이상인 학생 이름만 리스트로
- 점수 내림차순 상위 3명
- 점수가 가장 높은 학생 한 명
- 전원이 60점을 넘는가?
```

`mapToInt(Student::getScore).average()` 같은 형태를 쓰게 됩니다.
`Comparator.comparingInt(Student::getScore)` 는 step21 에서 쓴 그것입니다.

### 과제 6 — `InfiniteStream.java` (선택)

무한 스트림을 만들어보세요.

```java
Stream.iterate(1, n -> n * 2).limit(10).forEach(System.out::println);
```

`limit` 을 **빼면 어떻게 되는지** 확인해보세요 (`Ctrl+C` 로 종료).

그리고 지연 연산 덕분에 이게 가능하다는 걸 연결해서 생각해보세요 —
무한한 데이터인데도 `limit(10)` 만 있으면 10개만 만들어집니다.
**필요한 만큼만 흐른다**는 게 지연 연산의 값어치입니다.

## 5. 자가 점검

- [ ] 반복문을 `filter`/`map`/`collect` 로 바꿔 쓸 수 있다
- [ ] 중간 연산과 최종 연산을 반환 타입으로 구분할 수 있다
- [ ] 스트림이 일회용인 것을 직접 확인했다
- [ ] 최종 연산이 없으면 아무 일도 안 일어나는 것을 확인했다
- [ ] 요소가 파이프라인을 하나씩 통과한다는 걸 안다
- [ ] 스트림이 원본을 바꾸지 않는다는 걸 안다 (JS `sort` 와 반대)
- [ ] `IntStream` 이 따로 있는 이유를 오토박싱과 연결해 설명할 수 있다
- [ ] `peek` 이 디버깅 용도인 이유를 안다

---

다음은 step23(14장 5~6절)입니다. `max()` 뒤에 붙던 `.get()` 의 정체(**`Optional`**)와,
`collect()` 로 **그룹화·통계**까지 하는 방법을 다룹니다.
