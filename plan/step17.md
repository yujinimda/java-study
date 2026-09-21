# step17 — 지네릭스

**책 범위: 12장 「지네릭스, 열거형, 애너테이션」 1절**

step14부터 이렇게 써왔습니다.

```java
List<String> names = new ArrayList<>();
Map<String, Integer> count = new HashMap<>();
```

`<>` 안에 타입을 적으면 그 타입만 들어간다는 것만 알고 썼죠.
**이 단계에서 그게 무엇이고, 직접 만드는 법을 배웁니다.**

---

## 1. 읽을 것

- 12.1 지네릭스(Generics)
  - 지네릭스란?, **지네릭 클래스의 선언**, 타입 변수 `T`
  - **제한된 지네릭 클래스** (`<T extends Number>`)
  - **와일드 카드** (`<?>`, `<? extends T>`, `<? super T>`)
  - **지네릭 메서드**
  - 지네릭 타입의 형변환, **지네릭 타입의 제거(타입 소거)**

### 함께 읽을 글 ([Inpa Dev](https://inpa.tistory.com/category/Language/Java))

- [☕ 자바 제네릭(Generics) 개념 & 문법 정복하기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%A0%9C%EB%84%A4%EB%A6%ADGenerics-%EA%B0%9C%EB%85%90-%EB%AC%B8%EB%B2%95-%EC%A0%95%EB%B3%B5%ED%95%98%EA%B8%B0)
  — 12.1절 전체에 대응하는 글. 먼저 읽으면 책이 수월합니다.
- [☕ 자바 제네릭의 공변성 & 와일드카드 완벽 이해](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%A0%9C%EB%84%A4%EB%A6%AD-%EC%99%80%EC%9D%BC%EB%93%9C-%EC%B9%B4%EB%93%9C-extends-super-T-%EC%99%84%EB%B2%BD-%EC%9D%B4%ED%95%B4)
  — `? extends` / `? super` 가 헷갈릴 때 보세요. 문제 6·7과 짝입니다.
- [☕ 자바 제네릭 타입 소거 컴파일 과정 알아보기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%A0%9C%EB%84%A4%EB%A6%AD-%ED%83%80%EC%9E%85-%EC%86%8C%EA%B1%B0-%EC%BB%B4%ED%8C%8C%EC%9D%BC-%EA%B3%BC%EC%A0%95-%EC%95%8C%EC%95%84%EB%B3%B4%EA%B8%B0)
  — **제네릭의 여러 제약이 전부 여기서 나옵니다.** 문제 8을 풀고 나서 읽으면 이해가 빠릅니다.
- [☕ 힙 오염 (Heap Pollution) 이란?](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%A0%9C%EB%84%A4%EB%A6%AD-%ED%9E%99-%EC%98%A4%EC%97%BC-Heap-Pollution-%EC%9D%B4%EB%9E%80)
  — 타입 소거의 부작용. 어렵다면 나중에 다시 봐도 됩니다.
- [☕ 자바 업캐스팅 & 다운캐스팅 - 완벽 이해하기](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%97%85%EC%BA%90%EC%8A%A4%ED%8C%85-%EB%8B%A4%EC%9A%B4%EC%BA%90%EC%8A%A4%ED%8C%85-%ED%95%9C%EB%B0%A9-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0)
  — step08 복습. `List<Object> = List<String>` 이 왜 안 되는지(문제 5)를 이해하려면 필요합니다.

## 2. 손으로 정리할 것

1. **제네릭이 없으면 무슨 일이 생기는지** 코드로 적어두세요.

   ```java
   List raw = new ArrayList();      // 제네릭 없이 (raw type)
   raw.add("문자열");
   raw.add(42);                      // 아무거나 들어감
   String s = (String) raw.get(1);  // 컴파일 통과, 실행 중 ClassCastException
   ```

   **제네릭은 "실행 중에 터질 일을 컴파일 때 잡아주는" 장치**입니다. 한 줄로 정리하세요.

2. **타입 변수 관례.** 의미는 없고 관례일 뿐이지만 읽을 때 도움이 됩니다.

   | 문자 | 뜻 |
   |---|---|
   | `T` | Type |
   | `E` | Element (컬렉션 원소) |
   | `K` / `V` | Key / Value |
   | `N` | Number |
   | `R` | Return |

3. **세 가지 문법 자리**를 구분해서 적어두세요. 헷갈리기 쉽습니다.

   ```java
   class Box<T> { ... }                        // ① 제네릭 클래스 — 클래스명 뒤
   static <T> void print(T[] arr) { ... }      // ② 제네릭 메서드 — 반환타입 앞
   void f(List<? extends Number> list) { ... } // ③ 와일드카드 — 매개변수 자리
   ```

   ②의 `<T>` 가 **`void` 앞**에 있다는 게 포인트입니다. 빠뜨리면 컴파일 에러입니다.

4. **와일드카드 3종.** PECS 라는 암기법이 있습니다.

   ```
   <?>              아무 타입이나. 읽기만 가능
   <? extends T>    T 와 그 자손만.  꺼내기(Producer) 전용 → 넣을 수 없다
   <? super T>      T 와 그 조상만.  넣기(Consumer) 전용

   PECS = Producer Extends, Consumer Super
          "꺼내 쓸 거면 extends, 넣을 거면 super"
   ```

5. **타입 소거 한 줄.** `List<String>` 이 컴파일되면 `<String>` 은 어떻게 되나요?
   (힌트: 실행 중에는 그냥 `List` 입니다)

## 3. 문제

### 문제 1 — 제네릭이 막아주는 것
```java
List raw = new ArrayList();
raw.add("문자열");
raw.add(42);
System.out.println(raw);

List<String> typed = new ArrayList<>();
typed.add("문자열");
typed.add(42);
```
위는 되고 아래는 컴파일 에러입니다. 실제 메시지입니다.
```
error: incompatible types: int cannot be converted to String
```
`raw` 쪽은 컴파일할 때 **경고**만 나옵니다.
```
Note: uses unchecked or unsafe operations.
```
이 경고가 무슨 뜻이고, 왜 에러가 아닌지 생각해보세요.

### 문제 2 — 제네릭 클래스 읽기
```java
class Box<T> {
    private T item;
    public void set(T item) { this.item = item; }
    public T get() { return item; }
}

Box<String> b1 = new Box<>();
b1.set("사과");
String s = b1.get();

Box<Integer> b2 = new Box<>();
b2.set(42);
b2.set("바나나");
```
마지막 줄이 컴파일 에러입니다. `T` 가 `Box<Integer> b2` 에서 무엇으로 정해졌나요?

### 문제 3 — `<>` 를 비워두는 것 (다이아몬드)
```java
Box<String> a = new Box<String>();
Box<String> b = new Box<>();
Box<String> c = new Box();
```
셋 다 컴파일됩니다. 차이가 무엇이고, 실무에서 어느 것을 쓰나요?

### 문제 4 — 제한된 제네릭
```java
class NumBox<T extends Number> {
    private T item;
    public void set(T item) { this.item = item; }
    public double doubled() { return item.doubleValue() * 2; }
}

NumBox<Integer> a = new NumBox<>();
NumBox<Double> b = new NumBox<>();
NumBox<String> c = new NumBox<>();
```
마지막 줄이 에러입니다. 그리고 `<T extends Number>` 가 없다면
`item.doubleValue()` 는 왜 안 될까요?

> `extends` 라고 쓰지만 **인터페이스에도 씁니다.** `<T extends Comparable<T>>` 처럼요.
> `implements` 를 쓰지 않는 게 예외적입니다.

### 문제 5 — 제네릭은 공변이 아니다 ⭐
```java
List<String> strings = new ArrayList<>();
List<Object> objects = strings;
```
컴파일 에러입니다.
```
error: incompatible types: List<String> cannot be converted to List<Object>
```

step08에서 `Object o = new String("hi");` 는 **됐습니다**(업캐스팅).
그런데 `List<String>` → `List<Object>` 는 안 됩니다.

**왜 막았을까요?** 만약 허용된다면 아래 코드가 가능해집니다.
```java
objects.add(42);              // List<Object> 니까 Integer 도 OK
String s = strings.get(0);    // 그런데 strings 로 꺼내면?
```

배열은 이걸 허용해서 **실행 중에 터집니다.** 직접 확인해보세요.
```java
Object[] arr = new String[2];
arr[0] = 42;    // 컴파일 통과 → 실행 중 ArrayStoreException
```

**배열은 실행 중에, 제네릭은 컴파일 때.** 어느 쪽이 나은가요?

### 문제 6 — `? extends`
```java
static double sum(List<? extends Number> list) {
    double total = 0;
    for (Number n : list) total += n.doubleValue();
    return total;
}

sum(new ArrayList<Integer>());
sum(new ArrayList<Double>());
sum(new ArrayList<String>());
```
마지막 줄만 에러입니다. 그리고 `List<Number>` 로 선언했다면 `List<Integer>` 를
넘길 수 있었을까요? (문제 5와 이어집니다)

### 문제 7 — `? extends` 에는 왜 못 넣나 ⭐
```java
static void f(List<? extends Number> list) {
    list.add(1);
}
```
컴파일 에러입니다. 실제 메시지입니다.
```
error: incompatible types: int cannot be converted to CAP#1
  where CAP#1 is a fresh type-variable:
    CAP#1 extends Number from capture of ? extends Number
```

`list` 는 `List<Integer>` 일 수도, `List<Double>` 일 수도 있습니다.
**정체를 모르는데 뭔가를 넣으면 위험**하다는 게 이유입니다.

그럼 넣으려면 어떤 와일드카드를 써야 하나요?

### 문제 8 — 타입 소거 ⭐
```java
List<String> ls = new ArrayList<>();
List<Integer> li = new ArrayList<>();
System.out.println(ls.getClass().getName());
System.out.println(li.getClass().getName());
System.out.println(ls.getClass() == li.getClass());
```
실제 결과입니다.
```
java.util.ArrayList
java.util.ArrayList
true
```

**실행 중에는 `<String>` 과 `<Integer>` 구분이 사라집니다.**
제네릭은 컴파일러가 검사하고 지워버리는 것이기 때문입니다.

이것 때문에 아래 두 줄이 에러입니다. 각각 왜인지 설명하세요.
```java
class E<T> { T[] arr = new T[10]; }        // error: generic array creation
o instanceof List<String>                   // error: cannot be safely cast
```

### 문제 9 — 제네릭 메서드
```java
static void printA(Object[] arr) { for (Object o : arr) System.out.print(o + " "); }
static <T> void printB(T[] arr)  { for (T o : arr) System.out.print(o + " "); }
```
둘 다 동작합니다. `printB` 처럼 제네릭 메서드로 만들면 **무엇이 더 좋은가요?**
(힌트: 반환값이 있는 메서드를 상상해보세요)

## 4. 손코딩 과제

`src/step17/` 에 씁니다.

### 과제 1 — `Box.java` + `BoxTest.java`

제네릭 클래스를 직접 만드세요.

**`Box<T>` 스펙**
- 필드 `T item`
- `void set(T item)` / `T get()`
- `boolean isEmpty()`
- `void print()` — 담긴 것을 출력

**`BoxTest`**
- `Box<String>`, `Box<Integer>`, `Box<Student>` 세 가지로 써보세요
- `Box<Integer>` 에 문자열을 넣어보고 **컴파일 에러를 확인**하세요

> 클래스 하나로 모든 타입을 담는 것 — 그게 제네릭의 값어치입니다.
> 제네릭 없이 만들려면 `StringBox`, `IntBox`, `StudentBox` 를 따로 만들어야 합니다.

### 과제 2 — `Pair.java`

타입 변수를 **두 개** 쓰는 연습입니다.

```java
class Pair<K, V> { ... }
```

- 필드 `K key`, `V value`
- 생성자, getter
- `toString()` 오버라이딩 → `(키=값)` 형태

`Pair<String,Integer>`, `Pair<Integer,String>` 으로 각각 만들어 출력하세요.

> `Map.Entry<K,V>` 가 이것과 같은 구조입니다. step15에서 쓴 게 이거였습니다.

### 과제 3 — `GenericMethod.java`

제네릭 **메서드**를 만드세요. 클래스는 제네릭이 아니어도 됩니다.

```java
public class GenericMethod {
    static <T> void printArray(T[] arr) { ... }
    static <T> T first(T[] arr) { ... }
    static <T> void swap(T[] arr, int i, int j) { ... }

    public static void main(String[] args) {
        Integer[] nums = {1, 2, 3};
        String[] strs = {"a", "b", "c"};
        // 같은 메서드로 둘 다 처리
    }
}
```

`<T>` 를 **반환타입 앞**에 쓰는 걸 잊지 마세요. 빠뜨리면 이런 에러가 납니다.
```
error: cannot find symbol
  symbol: class T
```

### 과제 4 — `NumBox.java` (제한된 제네릭)

```java
class NumBox<T extends Number> { ... }
```

- 여러 숫자를 담는 `List<T>` 필드
- `void add(T n)`
- `double sum()` — 전부 더해서 반환
- `double average()`

`NumBox<Integer>`, `NumBox<Double>` 로 각각 써보고,
`NumBox<String>` 을 시도해서 **컴파일 에러를 확인**하세요.

> `<T extends Number>` 가 없으면 `sum()` 안에서 `doubleValue()` 를 못 씁니다.
> **"이 타입은 최소한 Number 다"** 라고 알려줘야 그 메서드를 쓸 수 있습니다.

### 과제 5 — `Wildcard.java` ⭐

와일드카드를 직접 써서 차이를 확인하세요.

```java
static double sumAll(List<? extends Number> list) { ... }   // 꺼내기 전용
static void addNumbers(List<? super Integer> list) { ... }  // 넣기 전용
```

- `sumAll` 에 `List<Integer>`, `List<Double>`, `List<Long>` 을 각각 넘겨보기
- `sumAll` 안에서 `list.add(...)` 를 시도해서 **컴파일 에러 확인**
- `addNumbers` 에 `List<Integer>`, `List<Number>`, `List<Object>` 를 넘겨보기
- `addNumbers` 에 `List<Double>` 을 넘겨보고 **에러 확인**

각 에러 메시지를 `notes/errors.md` 에 모으세요. **PECS 가 왜 그런 이름인지**
직접 겪어보는 게 이 과제입니다.

### 과제 6 — `TypeErasure.java`

타입 소거를 눈으로 확인하세요.

```java
List<String> ls = new ArrayList<>();
List<Integer> li = new ArrayList<>();
System.out.println(ls.getClass() == li.getClass());   // true
```

그리고 아래 코드를 각각 시도해서 **컴파일 에러 메시지를 수집**하세요.

1. `class E<T> { T[] arr = new T[10]; }`
2. `if (obj instanceof List<String>) { ... }`
3. 제네릭 클래스 안에서 `static T field;`

세 에러의 **원인이 전부 같습니다.** 무엇인지 주석으로 쓰세요.

> 심화: `javap -c` 로 컴파일된 바이트코드를 열어보면 `<String>` 이 사라진 걸
> 직접 볼 수 있습니다. step00에서 `javap` 를 써봤던 그 도구입니다.

## 5. 자가 점검

- [ ] 제네릭이 없으면 무슨 문제가 생기는지 설명할 수 있다
- [ ] 제네릭 클래스를 직접 선언할 수 있다
- [ ] 제네릭 메서드에서 `<T>` 를 어디에 쓰는지 안다
- [ ] `<T extends Number>` 로 타입을 제한하는 이유를 안다
- [ ] `List<String>` 을 `List<Object>` 에 못 넣는 이유를 안다
- [ ] `? extends` 와 `? super` 를 상황에 맞게 고를 수 있다
- [ ] `? extends` 에 값을 못 넣는 이유를 설명할 수 있다
- [ ] 타입 소거가 무엇이고, 그 때문에 생기는 제약을 안다

---

다음은 step18(열거형과 애너테이션)입니다. step07에서 아무 생각 없이 붙였던
`@Override` 가 정확히 무엇인지 거기서 다룹니다.
