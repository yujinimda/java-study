# step15 — Set 과 Map

**책 범위: 11장 「컬렉션 프레임웍」 3~4절**

step07에서 `equals()` 와 `hashCode()` 를 오버라이딩하면서 이런 생각이 들었을 겁니다.

> "`equals` 만 고치면 되지, `hashCode` 는 왜 같이 고치라는 거지?"

**그 답이 이 단계에 있습니다.** `HashSet` 과 `HashMap` 이 `hashCode` 로 물건을 찾기
때문입니다. 오버라이딩을 빼먹으면 중복이 안 걸러지는 걸 직접 보게 됩니다.

---

## 1. 읽을 것

- 11.3 `HashSet`
  - `Set` 의 특징(순서 없음·중복 없음), **`equals()` 와 `hashCode()` 의 역할**
- 11.3 `TreeSet`
  - 이진 탐색 트리, **정렬된 상태로 저장**, 범위 검색
- 11.4 `HashMap` 과 `Hashtable`
  - 키와 값, **해싱(hashing)** 과 해시함수, `HashMap` 의 메서드
  - **`keySet()` / `values()` / `entrySet()`**
- 11.4 `TreeMap`, `LinkedHashMap`

### 함께 읽을 글 ([Inpa Dev](https://inpa.tistory.com/category/Language/Java))

- [☕ 자바 equals / hashCode 오버라이딩 - 완벽 이해하기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-equals-hashCode-%EB%A9%94%EC%84%9C%EB%93%9C-%EA%B0%9C%EB%85%90-%ED%99%9C%EC%9A%A9-%ED%8C%8C%ED%97%A4%EC%B9%98%EA%B8%B0)
  — **이 단계의 필수 글.** step07에서 읽었다면 이제 "왜 둘을 같이 고쳐야 하는지"가
  실제 상황과 함께 읽힙니다.
- [☕ 자바 객체의 hashCode는 고유하지 않다 ❌](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B0%9D%EC%B2%B4%EC%9D%98-hashCode%EB%8A%94-%EA%B3%A0%EC%9C%A0%ED%95%98%EC%A7%80-%EC%95%8A%EB%8B%A4-%E2%9D%8C)
  — 해시가 겹치면(충돌) 어떻게 되는지. `HashMap` 내부 동작과 이어집니다.
- [☕ 개발자들을 괴롭히는 자바 NULL 파헤치기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B0%9C%EB%B0%9C%EC%9E%90%EB%93%A4%EC%9D%84-%EA%B4%B4%EB%A1%AD%ED%9E%88%EB%8A%94-NULL-%ED%8C%8C%ED%97%A4%EC%B9%98%EA%B8%B0)
  — `map.get()` 이 없는 키에 `null` 을 돌려주는 것이 사고의 단골입니다. 문제 6과 짝입니다.
- [☕ 자바 Wrapper 클래스와 Boxing & UnBoxing 총정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-wrapper-class-Boxing-UnBoxing)
  — `Map<String, Integer>` 에서 값을 꺼내 1 더하는 코드가 흔한데, 그때 박싱이 일어납니다.

## 2. 손으로 정리할 것

1. **`Set` 3종 / `Map` 3종 비교표.** 고를 때 이 표만 보면 됩니다.

   | 구현체 | 순서 | 정렬 | 속도 |
   |---|---|---|---|
   | `HashSet` | 없음 | | 빠름 |
   | `LinkedHashSet` | **넣은 순서** | | |
   | `TreeSet` | | **값 순서** | 느림 |
   | `HashMap` | 없음 | | 빠름 |
   | `LinkedHashMap` | **넣은 순서** | | |
   | `TreeMap` | | **키 순서** | 느림 |

   기본은 `HashSet` / `HashMap` 입니다. 순서가 필요할 때만 다른 걸 씁니다.

2. **`HashSet` 이 중복을 거르는 순서.** 번호로 쓰세요.

   ```
   ① 넣으려는 객체의 hashCode() 를 구한다
   ② 그 해시값 자리에 이미 뭔가 있나 본다
      └ 없으면 → 넣는다 (끝)
   ③ 있으면 equals() 로 진짜 같은지 비교한다
      └ 같으면 → 안 넣는다 (중복)
      └ 다르면 → 같은 자리에 같이 넣는다 (충돌)
   ```

   **`hashCode` 가 먼저, `equals` 는 나중**입니다. 그래서 `hashCode` 를 안 고치면
   ①에서 이미 다른 자리로 가버려 `equals` 는 불리지도 않습니다.

3. **`Map` 을 순회하는 3가지 방법**을 코드로 적어두세요.

   ```java
   for (String key : map.keySet())            { ... map.get(key) ... }
   for (Integer value : map.values())          { ... }
   for (Map.Entry<String,Integer> e : map.entrySet()) { e.getKey(); e.getValue(); }
   ```

   키와 값이 **둘 다** 필요하면 어느 것이 나은지, 그 이유도 한 줄로.

4. **JS 와의 대응.**

   | JavaScript | 자바 |
   |---|---|
   | `new Set()` | `new HashSet<>()` |
   | `set.add(x)` / `set.has(x)` | `set.add(x)` / `set.contains(x)` |
   | `obj[key] = v` / `map.set(k,v)` | `map.put(k, v)` |
   | `obj[key]` / `map.get(k)` | `map.get(k)` |
   | `key in obj` / `map.has(k)` | `map.containsKey(k)` |
   | `Object.keys(obj)` | `map.keySet()` |
   | `obj[key] ?? 0` | `map.getOrDefault(k, 0)` |

## 3. 문제

### 문제 1 — `Set` 의 기본 성질
```java
Set<String> s = new HashSet<>();
System.out.println(s.add("apple"));
System.out.println(s.add("banana"));
System.out.println(s.add("apple"));
System.out.println(s.size());
```
`add` 가 `boolean` 을 돌려줍니다. 각각 무슨 값이고 무슨 뜻인가요?

### 문제 2 — 순서
```java
List<String> src = Arrays.asList("banana", "apple", "cherry", "date");
System.out.println(new HashSet<>(src));
System.out.println(new LinkedHashSet<>(src));
System.out.println(new TreeSet<>(src));
```
실제 결과입니다.
```
[banana, date, apple, cherry]
[banana, apple, cherry, date]
[apple, banana, cherry, date]
```
`HashSet` 의 순서가 **넣은 순서도 아니고 사전순도 아닙니다.** 왜 이런 순서인가요?
그리고 `HashSet` 의 순서를 **믿고 코드를 짜면 안 되는** 이유를 쓰세요.

### 문제 3 — `equals`·`hashCode` 를 안 고치면 ⭐⭐

이 단계에서 **가장 중요한 문제**입니다.

```java
class Point {
    int x, y;
    Point(int x, int y) { this.x = x; this.y = y; }
    public String toString() { return "(" + x + "," + y + ")"; }
}

Set<Point> s = new HashSet<>();
s.add(new Point(1,2));
s.add(new Point(1,2));
s.add(new Point(1,2));
System.out.println(s.size());
System.out.println(s);
```

같은 좌표를 세 번 넣었는데 결과는 이렇습니다.
```
3
[(1,2), (1,2), (1,2)]
```

**`Set` 은 중복을 안 담는다면서요?** 무슨 일이 일어난 건지 손정리 2번의 순서로 설명하세요.

### 문제 4 — `equals` 만 고치면 ⭐
```java
class Point3 {
    int x, y;
    Point3(int x, int y) { this.x = x; this.y = y; }
    @Override public boolean equals(Object o) {
        if (!(o instanceof Point3)) return false;
        Point3 p = (Point3) o;
        return x == p.x && y == p.y;
    }
    // hashCode 는 오버라이딩하지 않음
}

Set<Point3> s = new HashSet<>();
s.add(new Point3(1,2));
s.add(new Point3(1,2));
System.out.println(s.size());
System.out.println(new Point3(1,2).equals(new Point3(1,2)));
System.out.println(s.contains(new Point3(1,2)));
```

실제 결과입니다.
```
2
true
false
```

**`equals` 는 `true` 인데 `contains` 는 `false`** 입니다. 이 모순이 왜 생기나요?

> 여기서 나오는 규칙: **`equals` 를 오버라이딩하면 `hashCode` 도 반드시 같이 한다.**
> 이유를 이제 한 문장으로 말할 수 있어야 합니다.

### 문제 5 — 둘 다 고치면
```java
@Override public boolean equals(Object o) { ... }
@Override public int hashCode() { return Objects.hash(x, y); }
```
문제 3의 코드에 위 둘을 넣으면 `s.size()` 가 몇이 되나요?
그리고 `HashMap` 의 **키**로 쓸 때도 같은 문제가 생깁니다.
```java
Map<Point,String> m = new HashMap<>();
m.put(new Point(1,2), "첫번째");
System.out.println(m.get(new Point(1,2)));
```
오버라이딩 전에는 `null`, 후에는 `첫번째` 입니다. 왜인가요?

> `Objects.hash(x, y)` 는 여러 필드를 묶어 해시값 하나를 만들어주는 도구입니다.
> `import java.util.Objects;` 가 필요합니다.

### 문제 6 — `Map` 기본과 `null`
```java
Map<String,Integer> m = new HashMap<>();
m.put("apple", 1);
m.put("banana", 2);
m.put("apple", 100);
System.out.println(m);
System.out.println(m.size());
System.out.println(m.get("apple"));
System.out.println(m.get("kiwi"));
System.out.println(m.containsKey("kiwi"));
System.out.println(m.getOrDefault("kiwi", 0));
```
같은 키에 두 번 `put` 하면 어떻게 되나요? 그리고 `get("kiwi")` 의 결과가
**예외가 아니라 `null`** 인 게 왜 위험한지 쓰세요.
(힌트: `int count = m.get("kiwi");` 라고 쓰면 어떻게 될까요)

### 문제 7 — `Map` 순회
```java
Map<String,Integer> m = new HashMap<>();
m.put("banana", 2); m.put("apple", 1); m.put("cherry", 3);

System.out.println(m.keySet());
System.out.println(m.values());
System.out.println(m.entrySet());
```
실제 결과입니다.
```
[banana, apple, cherry]
[2, 1, 3]
[banana=2, apple=1, cherry=3]
```
`entrySet()` 의 원소 타입은 무엇인가요? 그걸로 키와 값을 각각 꺼내는 메서드 이름은?

### 문제 8 — `TreeSet` 에 정렬 기준이 없으면
```java
class Point { int x, y; /* Comparable 구현 안 함 */ }
Set<Point> s = new TreeSet<>();
s.add(new Point(1,2));
```
컴파일은 되는데 실행하면 죽습니다. 예외 이름과 이유를 쓰세요.
(힌트: `TreeSet` 은 정렬해서 담는데, 무엇을 기준으로 정렬해야 할까요)

> 해결은 step16의 `Comparable` / `Comparator` 입니다.

### 문제 9 — 어떤 컬렉션을 쓸까
아래 상황에 맞는 컬렉션을 고르고 이유를 쓰세요.

1. 접속한 사용자 ID 목록 — 중복 없이, 순서 상관없음
2. 학생 이름 → 점수
3. 단어별 등장 횟수
4. 로그를 시간 순서대로 쌓기
5. 사전순으로 정렬된 단어 목록

## 4. 손코딩 과제

`src/step15/` 에 씁니다.

### 과제 1 — `SetBasic.java`

`String[] words = {"apple","banana","apple","cherry","banana","date"};` 에서
**중복을 제거**하세요.

- `HashSet` / `LinkedHashSet` / `TreeSet` **세 가지로 각각** 해보고 결과를 나란히 출력
- 셋의 순서가 어떻게 다른지 주석으로 정리

```
HashSet:       [banana, date, apple, cherry]
LinkedHashSet: [apple, banana, cherry, date]
TreeSet:       [apple, banana, cherry, date]
```

> `LinkedHashSet` 과 `TreeSet` 이 이 예제에선 우연히 같습니다.
> 입력 순서를 바꿔서 다시 돌려보면 달라집니다. 직접 확인해보세요.

### 과제 2 — `DedupeChars.java`

`String str = "hello world";` 에서 **중복 문자를 제거**한 결과를 출력하세요.

```
원본:   hello world
중복제거: helo wrd     (순서 유지)
```

순서를 유지해야 하니 어떤 `Set` 을 쓸지 먼저 정하세요.
`str.toCharArray()` 로 돌리고, 결과는 `StringBuilder` 로 이어붙이면 깔끔합니다.

### 과제 3 — `WordCount.java` ⭐

문자열에서 **단어별 등장 횟수**를 세세요. `HashMap` 의 대표 용도입니다.

```java
String text = "the quick brown fox jumps over the lazy dog the fox";
```

```
the: 3
fox: 2
quick: 1
...
```

핵심은 "이미 있으면 +1, 없으면 1" 입니다. 세 가지 방법을 **모두** 써보세요.

```java
// 방법 1 — containsKey 로 확인
if (map.containsKey(w)) map.put(w, map.get(w) + 1);
else                    map.put(w, 1);

// 방법 2 — getOrDefault (더 짧음)
map.put(w, map.getOrDefault(w, 0) + 1);

// 방법 3 — merge
map.merge(w, 1, Integer::sum);
```

단어 쪼개기는 `text.split(" ")` 입니다.

확장: 결과를 **횟수 많은 순**으로 출력해보세요. (step16에서 배울 정렬이 필요합니다.
지금은 `TreeMap` 으로 **이름순** 정렬까지만 해도 됩니다)

### 과제 4 — `PointSet.java` (step07 과제 3 이어서) ⭐

step07에서 `equals()` 를 오버라이딩한 `Point` 클래스를 가져오세요.

**세 가지 버전을 만들어 비교합니다.**

| 클래스 | `equals` | `hashCode` |
|---|---|---|
| `PointA` | × | × |
| `PointB` | ○ | × |
| `PointC` | ○ | ○ |

각각을 `HashSet` 에 같은 값으로 3번씩 넣고 `size()` 를 출력하세요.

```
PointA size = 3    ← 중복이 안 걸러짐
PointB size = 3    ← equals 만으로는 부족
PointC size = 1    ← 이제야 제대로
```

그리고 `contains(new PointX(1,2))` 도 각각 확인하세요.
**이 과제가 step07의 "왜 둘을 같이 고치나"에 대한 답입니다.**

### 과제 5 — `StudentMap.java` (step14 과제 6 이어서)

step14에서 `List<Student>` 로 학생을 관리했습니다. 이번엔 **이름으로 바로 찾도록** 바꾸세요.

```java
Map<String, Student> students = new HashMap<>();
students.put("지니", new Student("지니", 90, 85, 80));
```

- 학생 3명 등록
- 이름으로 한 명 조회해서 출력
- 없는 이름을 조회했을 때 **`NullPointerException` 이 안 나도록** 처리
- `entrySet()` 으로 전체 출력

> `List` 에서 이름으로 찾으려면 전부 훑어야 하는데, `Map` 은 한 번에 찾습니다.
> 그게 `Map` 을 쓰는 이유입니다.

### 과제 6 — `PhoneBook.java`

간단한 전화번호부를 만드세요.

- `Map<String,String>` 에 이름 → 번호 5개 저장
- 이름으로 검색 (없으면 "등록되지 않은 번호입니다")
- 이름 삭제
- 전체 목록을 **이름순**으로 출력 (어떤 `Map` 을 쓰면 정렬이 공짜인가요?)
- 특정 번호를 가진 사람 찾기 (값으로 검색 — `Map` 이 잘 못하는 일입니다)

> 마지막 항목을 해보면 `Map` 이 **키 → 값** 방향으로만 빠르다는 걸 알게 됩니다.

## 5. 자가 점검

- [ ] `Set` 3종 / `Map` 3종을 순서와 정렬 기준으로 구분할 수 있다
- [ ] `HashSet` 이 중복을 거르는 순서(`hashCode` → `equals`)를 말할 수 있다
- [ ] `equals` 만 오버라이딩하면 왜 부족한지 설명할 수 있다
- [ ] `HashSet` 의 순서를 믿으면 안 되는 이유를 안다
- [ ] `Map` 순회 3가지 방법을 쓸 수 있다
- [ ] `map.get()` 이 `null` 을 돌려줄 때의 위험을 안다
- [ ] 상황에 맞는 컬렉션을 고를 수 있다
- [ ] `getOrDefault` 로 "없으면 기본값" 패턴을 쓸 수 있다

---

다음은 step16(`Iterator` · `Comparator` · `Collections`)입니다. 이번 단계에서 미뤄둔
**정렬 기준 지정**과 **반복 중 삭제**를 거기서 다룹니다.
