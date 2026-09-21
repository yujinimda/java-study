# step16 — Iterator · Comparator · Collections

**책 범위: 11장 「컬렉션 프레임웍」 5~6절**

step15 문제 8에서 `TreeSet` 에 `Point` 를 넣으니 실행 중에 죽었습니다.
**"무엇을 기준으로 정렬하라는 건지 모르겠다"** 는 게 이유였죠.

그 기준을 알려주는 것이 `Comparable` 과 `Comparator` 입니다.
그리고 컬렉션을 **안전하게 순회하며 삭제하는 법**도 여기서 배웁니다.

---

## 1. 읽을 것

- 11.5 `Iterator`, `ListIterator`, `Enumeration`
  - `Iterator` 의 세 메서드, **`ListIterator` 의 양방향 순회**
- 11.6 `Comparator` 와 `Comparable`
  - `Comparable` 인터페이스 (`compareTo`), `Comparator` 인터페이스 (`compare`)
  - **정렬 기준을 클래스 안에 둘 것인가 밖에 둘 것인가**
- 11장 마무리 — `Arrays` 와 `Collections` 유틸리티 메서드
  - `sort`, `reverse`, `shuffle`, `max`, `min`, `unmodifiableList` 등

### 함께 읽을 글 ([Inpa Dev](https://inpa.tistory.com/category/Language/Java))

- [☕ 익명 클래스(Anonymous Class) 사용법 총정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%9D%B5%EB%AA%85-%ED%81%B4%EB%9E%98%EC%8A%A4Anonymous-Class-%EC%82%AC%EC%9A%A9%EB%B2%95-%EB%A7%88%EC%8A%A4%ED%84%B0%ED%95%98%EA%B8%B0)
  — **`Comparator` 는 익명 클래스로 쓰는 게 가장 흔합니다.** step09에서 배운 문법이
  실제로 쓰이는 첫 자리입니다.
- [☕ 람다식 리팩토링 하기 (Comparator 축약 원리)](https://inpa.tistory.com/entry/%E2%98%95-%EB%9E%8C%EB%8B%A4%EC%8B%9D-%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81-Comparator)
  — 익명 클래스로 쓴 `Comparator` 가 람다로 어떻게 줄어드는지. **step21의 예고편**이라
  지금은 "이렇게 짧아지는구나" 정도로 훑어도 됩니다.
- [☕ 자바 equals / hashCode 오버라이딩 - 완벽 이해하기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-equals-hashCode-%EB%A9%94%EC%84%9C%EB%93%9C-%EA%B0%9C%EB%85%90-%ED%99%9C%EC%9A%A9-%ED%8C%8C%ED%97%A4%EC%B9%98%EA%B8%B0)
  — `compareTo` 가 `0` 인 것과 `equals` 가 `true` 인 것이 일치해야 하는 이유(문제 8).

## 2. 손으로 정리할 것

1. **`Iterator` 의 세 메서드**와 쓰는 모양을 통째로 외우세요.

   ```java
   Iterator<String> it = list.iterator();
   while (it.hasNext()) {      // 다음 게 있나?
       String s = it.next();   // 하나 꺼내고 한 칸 전진
       if (조건) it.remove();  // 방금 꺼낸 것을 삭제 (안전)
   }
   ```

   향상된 for문(`for (String s : list)`)은 **내부적으로 이 `Iterator` 를 씁니다.**
   그래서 for-each 에서 `list.remove()` 를 하면 문제가 생깁니다(문제 3).

2. **`Comparable` vs `Comparator` 표.** 이름이 비슷해서 계속 헷갈립니다.

   | | `Comparable` | `Comparator` |
   |---|---|---|
   | 메서드 | `compareTo(T o)` | `compare(T a, T b)` |
   | 매개변수 개수 | **1개** (자기 자신과 비교) | **2개** |
   | 어디에 쓰나 | **클래스 안에** 구현 | **바깥에 따로** 만듦 |
   | 기준 개수 | 하나 (기본 정렬) | 여러 개 가능 |
   | 비유 | "나는 원래 이 순서로 정렬된다" | "이번엔 이 기준으로 정렬해줘" |

3. **`compareTo` / `compare` 의 반환값 규칙.** 숫자 자체가 아니라 **부호**가 의미입니다.

   ```
   음수  →  앞에 온다 (내가 더 작다)
    0   →  순서 같음
   양수  →  뒤에 온다 (내가 더 크다)
   ```

   그래서 오름차순은 `this.score - o.score`, 내림차순은 `o.score - this.score` 입니다.

   > 큰 수를 다룰 때 뺄셈은 **오버플로** 위험이 있습니다(step01 문제 3).
   > 안전한 건 `Integer.compare(a, b)` 입니다.

4. **`Collections` 주요 메서드**를 용도별로 적어두세요.

   ```
   정렬·순서   sort  reverse  shuffle  swap  rotate
   찾기        max  min  binarySearch  frequency
   보호        unmodifiableList  synchronizedList
   ```

   `Arrays` 는 **배열**용, `Collections` 는 **컬렉션**용입니다. 이름이 비슷하니 주의하세요.

## 3. 문제

### 문제 1 — `Iterator` 기본
```java
List<String> list = new ArrayList<>(Arrays.asList("a","b","c"));
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    System.out.print(it.next() + " ");
}
System.out.println();
System.out.println(it.hasNext());
```
마지막 줄이 무엇이고, 여기서 `it.next()` 를 한 번 더 부르면 어떻게 되나요?

### 문제 2 — for-each 는 무엇의 줄임인가
```java
for (String s : list) { System.out.println(s); }
```
이 코드가 내부적으로 어떤 코드로 바뀌는지 `Iterator` 로 풀어 쓰세요.
그리고 **for-each 로는 삭제를 못 하는 이유**를 이 그림으로 설명하세요.

### 문제 3 — `ConcurrentModificationException` ⭐⭐
```java
List<String> l = new ArrayList<>(Arrays.asList("a","b","c","d"));
for (String s : l) {
    if (s.equals("b")) l.remove(s);
}
System.out.println(l);
```
실행하면 이렇게 죽습니다.
```
Exception in thread "main" java.util.ConcurrentModificationException
```
**이름을 직역하면 "동시 수정 예외"** 입니다. 혼자 돌리는 코드인데 왜 "동시"인가요?
(힌트: 순회 중인 `Iterator` 모르게 리스트가 바뀐 것)

### 문제 4 — 같은 코드인데 예외가 안 나는 경우 ⭐
```java
List<String> l = new ArrayList<>(Arrays.asList("a","b","c"));
for (String s : l) {
    if (s.equals("b")) l.remove(s);
}
System.out.println(l);
```
문제 3과 **똑같은 코드인데 원소가 4개에서 3개로 줄었을 뿐**입니다.
그런데 예외가 안 나고 `[a, c]` 가 출력됩니다.

**끝에서 두 번째를 지우면 예외가 안 납니다.** 왜 그럴까요?
(힌트: `hasNext()` 는 "현재 위치 != 크기" 로 판단합니다. 지우면 크기가 1 줄어듭니다)

> 이게 이 예외가 **위험한 이유**입니다. 테스트에선 통과하고 실제 데이터에서 터집니다.

### 문제 5 — 인덱스 for 로 삭제하면
```java
List<String> l = new ArrayList<>(Arrays.asList("a","b","b","c"));
for (int i = 0; i < l.size(); i++) {
    if (l.get(i).equals("b")) l.remove(i);
}
System.out.println(l);
```
예외는 안 나는데 결과가 `[a, b, c]` 입니다. **`b` 가 하나 남았습니다.**
왜 하나를 놓쳤는지 인덱스를 추적해서 설명하세요.

| i | 리스트 상태 | 보는 값 | 동작 |
|---|---|---|---|
| 0 | `[a,b,b,c]` | a | 통과 |
| 1 | `[a,b,b,c]` | b | 삭제 → `[a,b,c]` |
| 2 | `[a,b,c]` | ? | ? |

### 문제 6 — 안전한 삭제
```java
Iterator<String> it = l.iterator();
while (it.hasNext()) {
    if (it.next().equals("b")) it.remove();
}
```
이건 `[a, c]` 로 제대로 됩니다. `l.remove()` 와 `it.remove()` 의 차이가 무엇인가요?

### 문제 7 — `Comparable` 정렬
```java
class Student implements Comparable<Student> {
    String name; int score;
    Student(String n, int s) { name = n; score = s; }
    public String toString() { return name + "(" + score + ")"; }
    @Override public int compareTo(Student o) { return this.score - o.score; }
}

List<Student> list = new ArrayList<>(Arrays.asList(
    new Student("지니", 88), new Student("민수", 92), new Student("서연", 75)));
Collections.sort(list);
System.out.println(list);
```
결과는 `[서연(75), 지니(88), 민수(92)]` 입니다.
**내림차순**으로 바꾸려면 `compareTo` 를 어떻게 고치나요?

### 문제 8 — `compareTo` 와 `equals` 의 약속
`TreeSet` 에 학생 두 명을 넣는데 점수가 같습니다.
```java
Set<Student> s = new TreeSet<>();
s.add(new Student("지니", 90));
s.add(new Student("민수", 90));
System.out.println(s.size());
```
`compareTo` 가 점수만 비교하므로 두 학생이 **같다고 판정**되어 크기가 1이 됩니다.
이름이 다른데도요.

`TreeSet` 은 중복 판정에 `equals` 가 아니라 **`compareTo`** 를 씁니다.
이 문제를 고치려면 `compareTo` 를 어떻게 바꿔야 하나요?
(힌트: 점수가 같으면 다음 기준으로 이름을 비교)

### 문제 9 — `Comparator` 로 여러 기준
```java
list.sort(new Comparator<Student>() {
    public int compare(Student a, Student b) {
        return a.name.compareTo(b.name);
    }
});
```
`Comparable` 이 이미 점수순으로 정해져 있는데도 이름순으로 정렬됩니다.
**`Comparator` 가 `Comparable` 을 덮어쓰는 것**인가요, 아니면 다른 것인가요?

그리고 아래 두 줄의 결과를 예상하세요.
```java
list.sort(Comparator.comparingInt((Student s) -> s.score));
list.sort(Comparator.comparingInt((Student s) -> s.score).reversed());
```

> `->` 는 람다이고 step21에서 제대로 배웁니다. 지금은
> `Comparator.comparingInt(...)` 가 "이 값을 기준으로 정렬" 이라는 것만 알면 됩니다.

## 4. 손코딩 과제

`src/step16/` 에 씁니다.

### 과제 1 — `IteratorBasic.java`

`List<String>` 을 만들고 **세 가지 방법**으로 순회해 같은 결과를 내세요.

1. 인덱스 `for`
2. 향상된 `for`
3. `Iterator`

그다음 각 방법으로 **`"b"` 를 삭제**해보고 결과를 비교하세요.

```
인덱스 for  → 결과는? (문제 5)
향상된 for  → 예외? (문제 3)
Iterator   → 정상
```

**세 경우를 직접 겪는 게 이 과제의 목적입니다.** 예외가 나는 건 정상이니
`notes/errors.md` 에 메시지를 적어두세요.

### 과제 2 — `SafeRemove.java`

`List<Integer>` 에 1~20을 넣고 **짝수만 모두 삭제**하세요.

- `Iterator` 로 안전하게
- 결과가 `[1, 3, 5, ..., 19]` 인지 확인

확장: `removeIf` 라는 더 짧은 방법도 있습니다. 찾아서 써보고 둘을 비교하세요.

### 과제 3 — `SortStudents.java` (step14 과제 6 이어서) ⭐

step14에서 만든 `List<Student>` 를 여러 기준으로 정렬합니다.

**3-1. `Comparable` 로 기본 정렬**
- `Student` 가 `Comparable<Student>` 를 구현하도록 수정
- 기준은 **총점 내림차순** (점수 높은 사람이 앞)
- `Collections.sort(list)` 로 정렬

**3-2. `Comparator` 로 다른 기준들**
익명 클래스로 세 가지를 각각 만들어 정렬하세요.
- 이름 가나다순
- 국어 점수순
- **총점 같으면 이름순** (2차 기준)

```java
Comparator<Student> byName = new Comparator<Student>() {
    public int compare(Student a, Student b) {
        return a.name.compareTo(b.name);
    }
};
list.sort(byName);
```

> 정렬 기준이 **하나**면 `Comparable`, **여러 개**거나 **클래스를 못 고칠 때**는 `Comparator`.
> 이 판단 기준을 주석으로 남기세요.

### 과제 4 — `WordCountSorted.java` (step15 과제 3 이어서)

step15에서 단어 빈도수를 셌지만 **순서가 뒤죽박죽**이었습니다.
이번엔 **많이 나온 순으로** 정렬해 출력하세요.

```java
Map<String,Integer> count = ...;                      // step15 결과
List<Map.Entry<String,Integer>> entries =
    new ArrayList<>(count.entrySet());                // Map → List
entries.sort(new Comparator<Map.Entry<String,Integer>>() {
    public int compare(Map.Entry<String,Integer> a, Map.Entry<String,Integer> b) {
        return b.getValue() - a.getValue();           // 내림차순
    }
});
```

```
the: 3
fox: 2
brown: 1
...
```

**`Map` 자체는 정렬이 안 되니 `List` 로 꺼내서 정렬**하는 게 요령입니다.
확장: 횟수가 같으면 단어 가나다순으로.

### 과제 5 — `CollectionsUtil.java`

`Collections` 와 `Arrays` 유틸을 하나씩 써보세요.

`List<Integer> n = new ArrayList<>(Arrays.asList(5, 3, 9, 1, 7));` 로,

- `Collections.sort(n)` / `Collections.reverse(n)`
- `Collections.max(n)` / `Collections.min(n)`
- `Collections.shuffle(n)` — 돌릴 때마다 결과가 달라지는지 확인
- `Collections.frequency(n, 3)`
- `Collections.unmodifiableList(n)` 로 감싼 뒤 `add` 를 시도 → **어떤 예외가 나는지 확인**

마지막 항목은 step14 과제에서 만난 `Arrays.asList` 와 같은 예외입니다.

### 과제 6 — `TreeSetStudent.java` (step15 문제 8 해결)

step15에서 `TreeSet<Point>` 가 실행 중에 죽었던 문제를 해결하세요.

- `Point` 가 `Comparable<Point>` 를 구현하도록 수정
- 기준은 **x 먼저, 같으면 y**
- `TreeSet<Point>` 에 여러 점을 넣고 정렬되어 나오는지 확인
- 같은 좌표를 두 번 넣었을 때 **하나만 남는지** 확인

```
[(1,1), (1,2), (2,1), (3,5)]
```

> step15에서는 `hashCode`/`equals` 로 중복을 걸렀는데, `TreeSet` 은 `compareTo` 로
> 거릅니다. **같은 "중복 제거"인데 판단 도구가 다릅니다.**

## 5. 자가 점검

- [ ] `Iterator` 의 세 메서드를 외워서 쓸 수 있다
- [ ] 향상된 for문이 `Iterator` 의 줄임이라는 걸 안다
- [ ] `ConcurrentModificationException` 이 나는 이유를 설명할 수 있다
- [ ] 끝에서 두 번째를 지우면 예외가 안 나는 이유를 안다
- [ ] 컬렉션에서 안전하게 삭제하는 방법을 안다
- [ ] `Comparable` 과 `Comparator` 를 상황에 맞게 고를 수 있다
- [ ] `compareTo` 반환값의 부호가 뜻하는 바를 안다
- [ ] `Map` 을 값 기준으로 정렬하는 방법을 안다

---

다음은 step17(지네릭스)입니다. step14부터 계속 써온 `<String>` 이 정확히 무엇인지
거기서 밝혀집니다.
