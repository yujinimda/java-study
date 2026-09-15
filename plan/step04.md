# step04 — 배열

**책 범위: 5장 「배열(array)」 전체**

지금까지는 변수 하나에 값 하나였습니다. 배열은 **값 여러 개를 한 이름으로 묶는** 첫 도구입니다.
그리고 **배열이 참조형이라는 점**이 step05(객체)로 넘어가는 다리가 됩니다.

---

## 1. 읽을 것

- 5.1 배열이란? (선언과 생성, 인덱스, `length`, 초기화, 출력)
- 5.1 배열의 활용 (총합·평균, 최대·최소, 섞기)
- 5.2 String 배열 (선언과 생성, 초기화, `char` 배열과 `String`)
- 5.3 다차원 배열 (2차원 배열, 초기화, 가변 배열)

### 함께 읽을 글 ([Inpa Dev](https://inpa.tistory.com/category/Language/Java))

- [☕ JAVA 배열(Array) 완벽 다루기 가이드](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%9E%90%EB%B0%94-%EB%B0%B0%EC%97%B4Array-%EB%AC%B8%EB%B2%95-%EC%9D%91%EC%9A%A9-%EC%B4%9D%EC%A0%95%EB%A6%AC)
  — 1·2차원 배열과 `Arrays` 유틸까지.
- [☕ String / StringBuffer / StringBuilder 차이점 & 성능 비교](https://inpa.tistory.com/entry/JAVA-%E2%98%95-String-StringBuffer-StringBuilder-%EC%B0%A8%EC%9D%B4%EC%A0%90-%EC%84%B1%EB%8A%A5-%EB%B9%84%EA%B5%90)
  — 반복문에서 문자열을 `+` 로 이어붙이면 안 되는 이유. 과제 6과 연결됩니다.
- [☕ 자바 split / StringTokenizer - 문자열 자르기 비교](https://inpa.tistory.com/entry/JAVA-%E2%98%95-Split-StringTokenizer-%EB%AC%B8%EC%9E%90%EC%97%B4-%EC%9E%90%EB%A5%B4%EA%B8%B0-%EB%B9%84%EA%B5%90%ED%95%98%EA%B8%B0)
  — 문자열 → 배열.
- [☕ 그림으로 보는 자바 코드의 메모리 영역 (스택 & 힙)](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B7%B8%EB%A6%BC%EC%9C%BC%EB%A1%9C-%EB%B3%B4%EB%8A%94-%EC%9E%90%EB%B0%94-%EC%BD%94%EB%93%9C%EC%9D%98-%EB%A9%94%EB%AA%A8%EB%A6%AC-%EC%98%81%EC%97%AD%EC%8A%A4%ED%83%9D-%ED%9E%99)
  — **배열이 왜 참조형인지**가 여기서 풀립니다. 문제 6과 짝입니다.

## 2. 손으로 정리할 것

1. **선언과 생성이 두 단계**라는 것. 각각 무슨 일이 일어나는지 한 줄씩.
   ```java
   int[] arr;              // 선언 — 참조변수만 만든다 (스택)
   arr = new int[5];       // 생성 — 실제 방 5칸을 만든다 (힙)
   int[] arr2 = new int[5];  // 한 줄로
   ```

2. **인덱스는 0부터.** 길이가 5면 유효한 인덱스는 **0~4**. 그림으로 그리세요.
   ```
   인덱스:  0    1    2    3    4
          [10] [20] [30] [40] [50]   length = 5
   ```

3. **기본값 표.** 배열을 만들면 각 칸이 무엇으로 채워지는지.

   | 타입 | 기본값 |
   |---|---|
   | `int`, `long` 등 정수 | |
   | `double`, `float` | |
   | `char` | |
   | `boolean` | |
   | 참조형 (`String` 등) | |

4. **`length` 와 `length()` 의 차이.** 배열은 어느 쪽이고 문자열은 어느 쪽인가.

## 3. 출력 예상 문제

손으로 답을 적은 뒤 타이핑해서 확인하세요.

### 문제 1 — 기본값
```java
int[] a = new int[3];
double[] d = new double[2];
boolean[] b = new boolean[2];
String[] s = new String[2];
System.out.println(a[0]);
System.out.println(d[0]);
System.out.println(b[0]);
System.out.println(s[0]);
```

### 문제 2 — length 와 인덱스
```java
int[] arr = {10, 20, 30, 40, 50};
System.out.println(arr.length);
System.out.println(arr[0]);
System.out.println(arr[arr.length - 1]);
System.out.println(arr[5]);
```
마지막 줄은 무슨 일이 일어나나요? **컴파일 에러인지 실행 에러인지**도 구분하세요.

### 문제 3 — 배열 출력
```java
int[] arr = {1, 2, 3};
System.out.println(arr);
System.out.println(Arrays.toString(arr));
char[] ch = {'a', 'b', 'c'};
System.out.println(ch);
```
`int[]` 와 `char[]` 의 결과가 다릅니다. (`Arrays` 를 쓰려면 `import java.util.Arrays;` 필요)

### 문제 4 — 반복문으로 순회
```java
int[] arr = {3, 1, 4};
for (int i = 0; i < arr.length; i++) {
    System.out.print(arr[i] + " ");
}
System.out.println();
for (int n : arr) {
    System.out.print(n + " ");
}
```
두 번째가 **향상된 for문(for-each)** 입니다. 언제 쓸 수 없는지도 생각해보세요.

### 문제 5 — 2차원 배열
```java
int[][] arr = {
    {1, 2, 3},
    {4, 5, 6}
};
System.out.println(arr.length);
System.out.println(arr[0].length);
System.out.println(arr[1][2]);
```

### 문제 6 — 배열은 참조형 ⭐
```java
int[] a = {1, 2, 3};
int[] b = a;
b[0] = 99;
System.out.println(a[0]);

int x = 1;
int y = x;
y = 99;
System.out.println(x);
```
두 결과가 다릅니다. **왜 다른지 그림으로 그려서** 설명하세요. 이 문제가 step05의 예고편입니다.

### 문제 7 — 컴파일 에러 찾기
```java
int[] a = new int[3];
a = {1, 2, 3};
int[] b = new int[3]{1, 2, 3};
int c[] = {1, 2, 3};
```
4줄 중 2줄이 에러입니다. 어느 줄이고 왜인가요?

## 4. 손코딩 과제

`src/step04/` 에 씁니다.

### 과제 1 — `ArrayBasic.java`
`int[] scores = {88, 92, 75, 100, 63};` 으로 아래를 출력하세요.
```
개수: 5
총합: 418
평균: 83.6
```
평균이 정수로 나오면 step01의 그 함정입니다.

### 과제 2 — `MinMax.java`
같은 배열에서 **최댓값과 최솟값**을 찾으세요.

요령: `max` 를 **첫 번째 원소로 시작**해놓고, 돌면서 더 큰 걸 만나면 갈아끼웁니다.
`max = 0` 으로 시작하면 음수만 있는 배열에서 틀립니다.

### 과제 3 — `Reverse.java`
`int[] arr = {1, 2, 3, 4, 5};` 를 **거꾸로 출력**하세요. (`5 4 3 2 1`)

그다음 **배열 자체를 뒤집어서** 저장하는 것도 해보세요. step01의 `Swap` 이 여기서 쓰입니다.
양 끝에서부터 가운데로 좁혀오며 서로 바꿉니다.

### 과제 4 — `Grades.java`
`String[] names = {"지니", "민수", "서연"};`
`int[] scores = {88, 92, 75};`

두 배열을 **같은 인덱스끼리 짝지어** 출력하세요.
```
지니: 88점
민수: 92점
서연: 75점
```
배열 두 개를 나란히 쓰는 방식입니다. step05에서는 이걸 **클래스 하나**로 묶게 됩니다.

### 과제 5 — `Gugudan2D.java`
2차원 배열 `int[][] dan = new int[9][9];` 에 구구단 값을 채워넣고 출력하세요.
(step03의 구구단을 배열로 옮기는 것입니다)

### 과제 6 — `CharCount.java`
`String str = "hello world";` 에서 **`l` 이 몇 번 나오는지** 세세요.

힌트: `str.charAt(i)` 로 i번째 글자를 꺼냅니다. `str.length()` 로 길이를 얻습니다.
(배열이 아니라 문자열이라 `length()` — 괄호가 붙습니다)

확장: `str.toCharArray()` 로 `char[]` 로 바꿔서도 해보세요.

## 5. 자가 점검

- [ ] 배열 선언·생성을 두 단계로 나눠 설명할 수 있다
- [ ] 길이 5인 배열의 유효 인덱스가 0~4인 이유를 안다
- [ ] `ArrayIndexOutOfBoundsException` 이 컴파일 에러가 아닌 이유를 안다
- [ ] `length`(배열)와 `length()`(문자열)를 구분한다
- [ ] 배열 두 개가 같은 곳을 가리킬 수 있다는 걸 그림으로 그릴 수 있다
- [ ] 향상된 for문을 쓸 수 있고, 못 쓰는 경우도 안다
