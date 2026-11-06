# step14 — 컬렉션 개요 · List

**책 범위: 11장 「컬렉션 프레임웍」 1~2절**

step04에서 배열을 배울 때 이런 제약을 만났습니다. **`new int[5]` 는 영원히 5칸**이고,
`push` 도 `map` 도 `filter` 도 없다는 것.

자바스크립트 배열에 해당하는 건 자바 배열이 아니라 **`ArrayList`** 입니다.
이 단계에서 그걸 만납니다.

---

## 1. 읽을 것

- 11.1 컬렉션 프레임웍
  - 컬렉션 프레임웍의 핵심 인터페이스 (**`Collection` / `List` / `Set` / `Map`**)
  - `Collection` 인터페이스의 메서드
- 11.2 `ArrayList`
  - `ArrayList` 의 메서드, **배열의 복사**, `Vector` 와의 차이
- 11.2 `LinkedList`
  - 배열의 단점, 연결리스트, **`ArrayList` 와 `LinkedList` 의 성능 비교**
- 11.2 `Stack` 과 `Queue`
  - `Stack` 과 `Queue` 의 메서드, **인터페이스 `Queue` 를 구현한 `LinkedList`**

### 함께 읽을 글 ([Inpa Dev](https://inpa.tistory.com/category/Language/Java))

- [☕ 자바 Wrapper 클래스와 Boxing & UnBoxing 총정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-wrapper-class-Boxing-UnBoxing)
  — **이 단계에서 꼭 필요합니다.** `List<int>` 가 왜 에러이고 `List<Integer>` 여야 하는지,
  그리고 `list.get(0) == list.get(1)` 이 값에 따라 달라지는 이유(문제 7)가 여기 있습니다.
- [☕ 그림으로 보는 자바 코드의 메모리 영역 (스택 & 힙)](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B7%B8%EB%A6%BC%EC%9C%BC%EB%A1%9C-%EB%B3%B4%EB%8A%94-%EC%9E%90%EB%B0%94-%EC%BD%94%EB%93%9C%EC%9D%98-%EB%A9%94%EB%AA%A8%EB%A6%AC-%EC%98%81%EC%97%AD%EC%8A%A4%ED%83%9D-%ED%9E%99)
  — `ArrayList` 와 `LinkedList` 의 성능 차이가 왜 나는지는 메모리 구조를 알아야 보입니다.
- [☕ 자바 split / StringTokenizer - 문자열 자르기 비교](https://inpa.tistory.com/entry/JAVA-%E2%98%95-Split-StringTokenizer-%EB%AC%B8%EC%9E%90%EC%97%B4-%EC%9E%90%EB%A5%B4%EA%B8%B0-%EB%B9%84%EA%B5%90%ED%95%98%EA%B8%B0)
  — 문자열을 잘라 리스트에 담을 때 씁니다. step04에서 봤다면 복습입니다.

## 2. 손으로 정리할 것

1. **컬렉션 계층도**를 그리세요. 이 그림 하나가 11장 전체의 지도입니다.

   ```
                  Collection (인터페이스)
                 ╱      │       ╲
              List     Set    (Queue)
             ╱   ╲     ╱  ╲
      ArrayList  Linked  HashSet  TreeSet
                 List

        Map (인터페이스)  ← Collection 을 상속하지 않는다!
       ╱        ╲
   HashMap    TreeMap
   ```

   **`Map` 만 `Collection` 밖에 있습니다.** 이유를 한 줄로 쓰세요.
   (힌트: 나머지는 값을 하나씩 담는데, `Map` 은 무엇을 담나요?)

2. **`List` / `Set` / `Map` 세 줄 요약.**

   | | 순서 | 중복 | 담는 것 |
   |---|---|---|---|
   | `List` | | | |
   | `Set` | | | |
   | `Map` | | | |

3. **JS 배열 ↔ 자바 대응표.** 프론트에서 쓰던 것과 짝지어 두면 빨리 익습니다.

   | JavaScript | 자바 배열 | 자바 `ArrayList` |
   |---|---|---|
   | `arr.push(x)` | 없음 (크기 고정) | `list.add(x)` |
   | `arr.length` | `arr.length` | `list.size()` |
   | `arr[0]` | `arr[0]` | `list.get(0)` |
   | `arr[0] = x` | `arr[0] = x` | `list.set(0, x)` |
   | `arr.splice(i,1)` | 없음 | `list.remove(i)` |
   | `arr.includes(x)` | 없음 | `list.contains(x)` |
   | `arr.indexOf(x)` | 없음 | `list.indexOf(x)` |

   **자바 배열 칸이 대부분 "없음"** 이라는 게 핵심입니다.

4. **`ArrayList` vs `LinkedList` 구조 그림.**

   ```
   ArrayList    [ 0 ][ 1 ][ 2 ][ 3 ]     칸이 쭉 붙어 있다 → 몇 번 칸인지로 바로 찾음
                                          중간에 끼우려면 뒤를 전부 밀어야 함

   LinkedList   [0]→[1]→[2]→[3]          각 칸이 다음 칸 주소만 들고 있다
                                          끼우기는 주소만 바꾸면 끝
                                          3번을 찾으려면 0부터 따라가야 함
   ```

5. **`<>` 안에 쓰는 것.** `List<String>` 의 `<String>` 이 무슨 뜻인지 한 줄로.
   > 이게 **제네릭**이고 제대로 배우는 건 step17입니다. 지금은
   > **"이 리스트에는 `String` 만 넣겠다고 미리 약속하는 것"** 정도로 충분합니다.

## 3. 문제

손으로 답을 적은 뒤 타이핑해서 확인하세요.

### 문제 1 — `ArrayList` 기본
```java
List<String> l = new ArrayList<>();
l.add("a"); l.add("b"); l.add("c");
System.out.println(l);
System.out.println(l.size());
System.out.println(l.get(0));
l.set(1, "B");
System.out.println(l);
l.add(1, "X");
System.out.println(l);
l.remove("X");
System.out.println(l);
System.out.println(l.contains("c"));
System.out.println(l.indexOf("c"));
```
`System.out.println(l)` 이 **주소가 아니라 내용**을 찍는 게 배열과 다릅니다.
step04 문제 3에서 `int[]` 를 그냥 찍으면 `[I@1b6d3586` 이 나왔던 것과 비교해보세요.

### 문제 2 — 선언 타입과 생성 타입
```java
List<String> a = new ArrayList<>();
ArrayList<String> b = new ArrayList<>();
List<String> c = new LinkedList<>();
ArrayList<String> d = new LinkedList<>();
```
한 줄이 컴파일 에러입니다. 어느 줄이고 왜인가요?
그리고 `List` 로 받는 것과 `ArrayList` 로 받는 것 중 **어느 쪽이 나은지** 생각해보세요.
(힌트: step08 다형성, 매개변수 타입)

### 문제 3 — `List<int>`
```java
List<int> nums = new ArrayList<>();
```
컴파일 에러입니다. 실제 메시지는 이렇습니다.
```
error: unexpected type
  required: reference
  found:    int
```
`required: reference` 가 무슨 뜻인가요? 그리고 어떻게 고치나요?

### 문제 4 — `remove` 함정 ⭐
```java
List<Integer> r = new ArrayList<>(Arrays.asList(10, 20, 30));
r.remove(1);
System.out.println(r);

List<Integer> r2 = new ArrayList<>(Arrays.asList(10, 20, 30));
r2.remove(Integer.valueOf(20));
System.out.println(r2);
```
**두 `remove` 가 하는 일이 다릅니다.** 왜 다른지 설명하세요.
(힌트: step06 오버로딩. `remove(int)` 와 `remove(Object)` 두 개가 있습니다)

### 문제 5 — `Arrays.asList`
```java
List<String> fixed = Arrays.asList("p", "q");
fixed.add("r");
```
컴파일은 되는데 실행하면 죽습니다. 예외 이름을 쓰세요.
그리고 `new ArrayList<>(Arrays.asList("p","q"))` 로 감싸면 왜 되는지도 쓰세요.

### 문제 6 — `Stack` 과 `Queue`
```java
Stack<Integer> st = new Stack<>();
st.push(1); st.push(2); st.push(3);
System.out.println(st.peek());
System.out.println(st.pop());
System.out.println(st);

Queue<Integer> q = new LinkedList<>();
q.offer(1); q.offer(2); q.offer(3);
System.out.println(q.peek());
System.out.println(q.poll());
System.out.println(q);
```
`Stack` 과 `Queue` 에서 꺼내지는 값이 다릅니다. 각각 **LIFO / FIFO** 중 무엇인가요?

> `Queue` 는 인터페이스라 `new Queue()` 가 안 됩니다. `LinkedList` 가 `Queue` 를
> 구현했기 때문에 `new LinkedList<>()` 를 담는 것입니다. step09 인터페이스의 실제 사례입니다.

### 문제 7 — `Integer` 캐시 ⭐
```java
List<Integer> n1 = new ArrayList<>(Arrays.asList(100, 100));
System.out.println(n1.get(0) == n1.get(1));

List<Integer> n2 = new ArrayList<>(Arrays.asList(1000, 1000));
System.out.println(n2.get(0) == n2.get(1));
System.out.println(n2.get(0).equals(n2.get(1)));
```
**100은 `true`, 1000은 `false`** 입니다. step02 문제 6의 `String` 리터럴 이야기와 같은 구조입니다.

자바는 `-128 ~ 127` 범위의 `Integer` 를 미리 만들어두고 **재사용**합니다. 그 밖은 매번 새 객체입니다.

> 결론은 step02와 같습니다. **`==` 말고 `equals`.** 숫자라도 `Integer` 는 객체입니다.

### 문제 8 — 성능 예상
`ArrayList` 와 `LinkedList` 각각에 10만 개를 넣고 두 작업을 해봅니다.

| 작업 | `ArrayList` | `LinkedList` |
|---|---|---|
| 맨 앞에 삽입 `add(0, x)` | ? | ? |
| 인덱스로 조회 `get(i)` | ? | ? |

어느 쪽이 빠를지 **손정리 4번의 그림을 보고** 예상해서 적으세요.
실제 측정은 과제 3에서 합니다.

## 4. 손코딩 과제

`src/step14/` 에 씁니다. 컬렉션을 쓰려면 파일 맨 위에 `import java.util.*;` 가 필요합니다.

### 과제 1 — `ListBasic.java`

`ArrayList<String>` 을 만들어 아래를 순서대로 해보세요. 매번 리스트 전체를 출력합니다.

1. `"사과"`, `"바나나"`, `"체리"` 추가
2. 1번 인덱스를 `"블루베리"` 로 교체
3. 맨 앞에 `"딸기"` 삽입
4. `"체리"` 삭제
5. 크기 출력
6. 향상된 for문으로 하나씩 출력

> step04 배열로 같은 걸 하려면 몇 줄이 필요했을지 생각해보세요.

### 과제 2 — `Grades2.java` (step04 과제 4 개선) ⭐

step04에서는 배열 **두 개**를 인덱스로 짝지었습니다.

```java
String[] names = {"지니", "민수", "서연"};
int[] scores = {88, 92, 75};
```

이번엔 `ArrayList` 로 바꾸고, **사람을 추가·삭제할 수 있게** 만드세요.

- `List<String> names` / `List<Integer> scores`
- 세 명을 넣고 출력
- 한 명을 **추가**하고 다시 출력 (배열로는 못 하던 것)
- 한 명을 **삭제**하고 다시 출력

```
지니: 88점
민수: 92점
서연: 75점
--- 추가 후 ---
...
```

> 그런데 리스트가 여전히 **두 개**입니다. 한쪽만 삭제하면 짝이 어긋납니다.
> 이 문제는 step05에서 배운 **클래스**로 풀립니다 — 과제 6에서 합니다.

### 과제 3 — `ListPerf.java`

`ArrayList` 와 `LinkedList` 의 속도를 **직접 측정**하세요.

```java
final int N = 100_000;
List<Integer> al = new ArrayList<>();
List<Integer> ll = new LinkedList<>();

long t = System.nanoTime();
for (int i = 0; i < N; i++) al.add(0, i);     // 맨 앞에 삽입
long ms = (System.nanoTime() - t) / 1_000_000;
System.out.println("ArrayList 앞쪽 삽입: " + ms + " ms");
```

같은 방식으로 4가지를 재세요.

| | `ArrayList` | `LinkedList` |
|---|---|---|
| 앞쪽 삽입 `add(0, i)` | | |
| 인덱스 조회 `get(i)` | | |

차이가 **수백 배**로 벌어집니다. 문제 8에서 예상한 것과 맞는지 확인하고,
**왜 그런지** 손정리 4번 그림으로 설명을 주석에 쓰세요.

> `100_000` 처럼 숫자에 `_` 를 넣을 수 있습니다. 자릿수를 읽기 쉬우라고 있는 문법이고
> 값에는 영향이 없습니다.

### 과제 4 — `BracketCheck.java`

`Stack` 으로 괄호 짝이 맞는지 검사하세요.

```
"(()())"      → true
"(()"         → false
"())("        → false
"{[()]}"      → true   (확장)
```

방법:
- 여는 괄호를 만나면 `push`
- 닫는 괄호를 만나면 `pop` 해서 짝이 맞는지 확인
- 다 돌고 나서 스택이 **비어 있어야** 참

`str.charAt(i)` 로 한 글자씩 꺼냅니다(step04 과제 6에서 쓴 것).
**빈 스택에 `pop` 하면 예외가 납니다.** 그 경우를 먼저 막으세요.

### 과제 5 — `QueueSim.java`

`Queue` 로 대기열을 만드세요.

- 손님 5명을 `offer` 로 줄 세우기
- `poll` 로 3명 처리하면서 "○○님 입장" 출력
- 남은 대기 인원과 다음 차례(`peek`) 출력

### 과제 6 — `StudentList.java` (step05 + step14 합치기) ⭐

과제 2에서 리스트 두 개가 어긋날 수 있던 문제를 **클래스로** 해결합니다.

step05에서 만든 `Student` 클래스를 가져와서(없으면 새로 만들어서),

```java
List<Student> students = new ArrayList<>();
students.add(new Student("지니", 90, 85, 80));
```

- 학생 3명을 넣고 전부 출력 (step07에서 `toString()` 을 오버라이딩했다면 그대로 찍힙니다)
- **평균이 85점 이상인 학생만** 출력
- 전체 평균 구하기

> 이름과 점수가 **한 객체 안에** 있으니 어긋날 수가 없습니다.
> `List` + 클래스 조합이 실무에서 가장 많이 쓰는 형태입니다.

## 5. 자가 점검

- [ ] `Collection` / `List` / `Set` / `Map` 계층도를 그릴 수 있다
- [ ] `Map` 이 `Collection` 을 상속하지 않는 이유를 안다
- [ ] 배열과 `ArrayList` 의 차이를 세 가지 이상 말할 수 있다
- [ ] `List<int>` 가 안 되는 이유를 안다
- [ ] `remove(1)` 과 `remove(Integer.valueOf(1))` 의 차이를 안다
- [ ] `ArrayList` 와 `LinkedList` 중 언제 무엇을 쓸지 판단할 수 있다
- [ ] `Stack`(LIFO)과 `Queue`(FIFO)의 차이를 안다
- [ ] `Integer` 를 `==` 로 비교하면 안 되는 이유를 안다

---

다음은 step15(`Set` 과 `Map`)입니다. step07에서 `equals` 와 `hashCode` 를 오버라이딩했던
이유가 거기서 드러납니다.
