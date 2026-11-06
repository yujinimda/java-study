# step13 — 날짜와 시간 · 형식화

**책 범위: 10장 「날짜와 시간 & 형식화」 전체**

step12에서 숫자를 정확하게 다루는 법을 배웠습니다. 이번엔 **날짜와 시간**, 그리고
**보기 좋게 출력하는 법**입니다.

step12 과제 3에서 `4.35 * 100` 이 `434.99999999999994` 로 나왔던 것처럼,
날짜와 숫자 출력에도 사람이 읽기 좋은 형태로 바꿔주는 도구가 따로 있습니다.
`1234567` 을 `1,234,567` 로 만드는 그런 일입니다.

> **책과 실무가 가장 크게 어긋나는 장입니다.** 『자바의 정석 3판』은 `Calendar` 와
> `SimpleDateFormat` 을 중심으로 설명하는데, 이것들은 지금 **레거시**입니다.
> 자바 8(2014년)에 들어온 `java.time` 패키지가 실무 표준입니다.
> 책의 `Calendar` 부분은 "옛날 코드를 읽을 때 필요한 지식" 으로 보고,
> 새로 쓰는 코드는 `java.time` 으로 연습하는 구성으로 잡았습니다.

---

## 1. 읽을 것

- 10.1 날짜와 시간
  - `Calendar` 와 `Date` — **가볍게 읽으세요.** 아래 문제 1의 함정만 확인하면 됩니다
  - `Calendar` 의 `get()`, `set()`, `add()`, `roll()`
- 10.2 형식화 클래스
  - **`DecimalFormat`** — 숫자 형식화 (실무에서 계속 씁니다)
  - `SimpleDateFormat` — 날짜 형식화 (레거시. `DateTimeFormatter` 와 짝지어 보세요)
  - `ChoiceFormat`, `MessageFormat` — 가볍게
- **`java.time` 패키지** (책 3판에는 없거나 부록에 있습니다. 아래 2번 표로 정리하세요)
  - `LocalDate`, `LocalTime`, `LocalDateTime`
  - `Period` (날짜 차이), `Duration` (시간 차이)
  - `DateTimeFormatter`

### 함께 읽을 글

이 단계에 해당하는 [Inpa Dev](https://inpa.tistory.com/category/Language/Java) 글은
목록에 없습니다. 날짜·시간은 책 + 아래 공식 문서로 진행합니다.

- [java.time 패키지 공식 문서](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/time/package-summary.html)
  — 영어지만 클래스 목록과 한 줄 설명만 훑어도 전체 그림이 잡힙니다.
- `jshell` 로 바로 확인하는 게 제일 빠릅니다.
  ```
  jshell> import java.time.*
  jshell> LocalDate.now()
  jshell> LocalDate.of(2026, 9, 21).plusDays(10)
  ```
  step00에서 본 그 `jshell` 입니다. 날짜 메서드는 종류가 많아서 하나씩 쳐보는 게 빠릅니다.

## 2. 손으로 정리할 것

1. **`java.time` 클래스 3개**를 언제 쓰는지.

   | 클래스 | 담는 것 | 예 |
   |---|---|---|
   | `LocalDate` | 날짜만 | `2026-09-21` |
   | `LocalTime` | 시간만 | `14:30` |
   | `LocalDateTime` | 날짜 + 시간 | `2026-09-21T14:30` |

   생일은 어느 것? 알람 시각은? 주문 일시는?

2. **`Period` vs `Duration`.**
   ```
   Period    날짜 차이 (년/월/일)   — LocalDate 끼리
   Duration  시간 차이 (시/분/초)   — LocalTime, LocalDateTime 끼리
   ```

3. **레거시 vs 현대 짝표.** 옛날 코드를 읽을 때 필요합니다.

   | 레거시 (책) | 현대 (실무) |
   |---|---|
   | `Date`, `Calendar` | `LocalDate`, `LocalDateTime` |
   | `SimpleDateFormat` | `DateTimeFormatter` |
   | 가변(mutable) | **불변(immutable)** |
   | thread-safe 아님 | thread-safe |
   | 월이 **0부터** 시작 | 월이 **1부터** 시작 |

   마지막 줄이 `Calendar` 를 버린 가장 큰 이유 중 하나입니다. 문제 1에서 확인합니다.

4. **`java.time` 객체는 불변**입니다. step11에서 `String` 이 불변이었던 것과 같습니다.
   ```java
   LocalDate d = LocalDate.of(2026, 9, 21);
   d.plusDays(10);              // d 는 안 바뀐다 — 반환값을 안 받으면 의미가 없다
   d = d.plusDays(10);          // 이렇게 다시 담아야 한다
   ```
   step11 문제 2의 `s.toUpperCase()` 와 **똑같은 함정**입니다.

5. **`DecimalFormat` 패턴 기호** 2개.
   ```
   0   자리가 비면 0을 채운다      ("0.00" → 3.0 이면 "3.00")
   #   자리가 비면 비워둔다        ("0.##" → 3.0 이면 "3")
   ,   3자리 구분                  ("#,###" → 1234567 이면 "1,234,567")
   ```

## 3. 문제

### 문제 1 — Calendar 의 월 ⭐
```java
Calendar c = Calendar.getInstance();
c.set(2026, 9, 21);
System.out.println(c.get(Calendar.MONTH));

SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
System.out.println(sdf.format(c.getTime()));
```

`set(2026, 9, 21)` 로 **9월**을 넣었다고 생각했는데 출력은 `2026-10-21` 입니다.

`Calendar` 는 **월을 0부터** 셉니다. 0=1월, 9=**10월**.
그래서 옛날 코드에는 `c.set(2026, 8, 21)` 이나 `Calendar.SEPTEMBER` 같은 게 보입니다.

```java
LocalDate d = LocalDate.of(2026, 9, 21);   // java.time 은 그냥 9월
```

이 한 가지만으로도 `java.time` 을 쓸 이유가 됩니다.

### 문제 2 — LocalDate 기본
```java
LocalDate d = LocalDate.of(2026, 9, 21);
System.out.println(d);
System.out.println(d.getYear());
System.out.println(d.getMonthValue());
System.out.println(d.getMonth());
System.out.println(d.getDayOfWeek());
System.out.println(d.isLeapYear());
System.out.println(d.lengthOfMonth());
```
`getMonthValue()` 와 `getMonth()` 의 결과가 다릅니다. 각각 무엇이 나올까요?

> `isLeapYear()` — step02 과제 5에서 윤년 판별을 `&&` `||` 로 직접 짰습니다.
> 이미 만들어진 메서드가 있었던 셈입니다. **직접 짜본 뒤에 알게 되는 게 순서상 낫습니다.**

### 문제 3 — 불변 ⭐
```java
LocalDate d = LocalDate.of(2026, 9, 21);
d.plusDays(10);
System.out.println(d);

LocalDate d2 = d.plusDays(10);
System.out.println(d2);
System.out.println(d);
```
step11 문제 2와 같은 함정입니다. 세 줄의 출력을 적으세요.

### 문제 4 — 날짜 계산
```java
LocalDate d = LocalDate.of(2026, 9, 21);
System.out.println(d.plusDays(10));
System.out.println(d.plusMonths(3));
System.out.println(d.minusYears(1));
System.out.println(d.withDayOfMonth(1));
```
`plusDays(10)` 이 `2026-10-01` 입니다. 9월이 30일까지라 달을 넘어갑니다.
**월말 계산을 직접 안 해도 되는 것**이 이 클래스를 쓰는 이유입니다.

### 문제 5 — Period 와 Duration
```java
LocalDate birth = LocalDate.of(1998, 3, 15);
LocalDate today = LocalDate.of(2026, 9, 21);

Period p = Period.between(birth, today);
System.out.println(p.getYears() + "년 " + p.getMonths() + "개월 " + p.getDays() + "일");
System.out.println(ChronoUnit.DAYS.between(birth, today));

Duration du = Duration.between(LocalTime.of(9, 0), LocalTime.of(17, 30));
System.out.println(du.toHours());
System.out.println(du.toMinutes());
```

`Period` 의 `getYears/getMonths/getDays` 는 **쪼개진 값**입니다.
"총 며칠" 을 원하면 `ChronoUnit.DAYS.between()` 을 씁니다. 이 둘을 헷갈리면 안 됩니다.

`import java.time.temporal.ChronoUnit;` 이 필요합니다.

### 문제 6 — 형식화
```java
LocalDate d = LocalDate.of(2026, 9, 21);
LocalDateTime dt = LocalDateTime.of(2026, 9, 21, 14, 30, 0);

DateTimeFormatter f1 = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

System.out.println(d.format(f1));
System.out.println(dt.format(f2));
System.out.println(LocalDate.parse("2026-12-25"));
System.out.println(LocalDate.parse("2026/12/25", DateTimeFormatter.ofPattern("yyyy/MM/dd")));
```
`format` 은 날짜 → 문자열, `parse` 는 문자열 → 날짜입니다. 어느 쪽이 어느 쪽인지 정리하세요.

> `MM` 은 월, `mm` 은 분입니다. 대소문자가 다른 뜻이라 자주 틀립니다.
> `HH` 는 24시간제, `hh` 는 12시간제입니다.

### 문제 7 — DecimalFormat
```java
System.out.println(new DecimalFormat("#,###").format(1234567));
System.out.println(new DecimalFormat("#,###.00").format(1234567.891));
System.out.println(new DecimalFormat("0.##").format(3.14159));
System.out.println(new DecimalFormat("0.##").format(3.0));
System.out.println(new DecimalFormat("#,##0원").format(50000));
System.out.println(new DecimalFormat("0.0%").format(0.256));
```
`0` 과 `#` 의 차이가 3·4번째 줄에서 드러납니다.

### 문제 8 — printf
```java
System.out.printf("%,d%n", 1234567);
System.out.printf("%.2f%n", 3.14159);
System.out.printf("%05d%n", 42);
System.out.printf("%-10s|%n", "왼쪽");
System.out.printf("%10s|%n", "오른쪽");
```
`%n` 은 줄바꿈입니다. `%-10s` 와 `%10s` 의 차이를 적으세요.

> `DecimalFormat` 과 `printf` 는 하는 일이 겹칩니다. 간단한 출력엔 `printf`,
> 같은 형식을 여러 번 재사용할 땐 `DecimalFormat` 을 쓰는 편입니다.

### 문제 9 — 잘못된 날짜
```java
LocalDate.of(2026, 2, 30);
LocalDate.parse("2026-13-01");
```
둘 다 실행 중에 예외가 납니다. 첫 번째는 이렇습니다.
```
DateTimeException: Invalid date 'FEBRUARY 30'
```
**컴파일 에러가 아닌 이유**는? (step10 문제 5와 같은 이야기입니다)

## 4. 손코딩 과제

`src/step13/` 에 씁니다.

### 과제 1 — `DateLab.java`

```java
LocalDate today = LocalDate.now();
```

- 오늘 날짜, 연·월·일, 요일 출력
- 오늘부터 100일 뒤
- 이번 달의 1일과 마지막 날 (`withDayOfMonth(1)`, `with(TemporalAdjusters.lastDayOfMonth())`
  또는 `lengthOfMonth()` 활용)
- 올해가 윤년인지

`import java.time.LocalDate;` 가 필요합니다.

### 과제 2 — `Birthday.java` ⭐

**생일까지 남은 날짜를 계산하세요.**

```java
LocalDate birth = LocalDate.of(1998, 3, 15);
LocalDate today = LocalDate.now();
```

출력할 것:
1. 지금까지 산 날 수 (`ChronoUnit.DAYS.between`)
2. 만 나이 (`Period.between(birth, today).getYears()`)
3. **올해 생일까지 남은 날짜** — 이미 지났으면 **내년 생일까지**

3번이 관문입니다. 종이에서 먼저 순서를 세우세요.

```
① 올해 생일 = birth 의 월·일 + 올해 연도       (withYear 활용)
② 올해 생일이 오늘보다 이전이면 → 내년 생일로 (plusYears(1))
③ 오늘부터 그 날까지 며칠?
```

②에서 `isBefore()` 를 씁니다. step03의 `if` 를 여기서 다시 쓰게 됩니다.

### 과제 3 — `DateFormat.java`

날짜 문자열 변환 연습입니다.

- `"2026-09-21"` 문자열을 `LocalDate` 로 파싱
- 그것을 `2026년 09월 21일 (월요일)` 형태로 출력
- `"2026/09/21"` 처럼 슬래시 형식도 파싱 (`DateTimeFormatter.ofPattern`)
- `LocalDateTime.now()` 를 `yyyy-MM-dd HH:mm:ss` 로 출력

요일 한글 출력 힌트:
```java
d.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)
// import java.time.format.TextStyle;  import java.util.Locale;
```

### 과제 4 — `NumberFormat.java`

step12 과제 3의 `MoneyCalc` 결과를 **읽기 좋게** 출력하는 과제입니다.

- `1234567` → `1,234,567`
- `1234567` → `1,234,567원`
- `0.256` → `25.6%`
- `3.14159` → `3.14`
- `42` → `00042` (5자리 0 채우기)

`DecimalFormat` 으로 한 번, `printf` 로 한 번 — **두 방법 모두** 해보세요.

### 과제 5 — `Calendar 비교` `LegacyVsModern.java`

같은 일을 두 방식으로 해서 비교하는 과제입니다.

**"2026년 9월 21일" 을 만들어 `2026-09-21` 로 출력하기**

1. `Calendar` + `SimpleDateFormat` 으로
2. `LocalDate` + `DateTimeFormatter` 로

1번에서 **월에 무엇을 넣어야 하는지** 직접 겪어보세요(문제 1). 코드 줄 수와 헷갈리는 정도를
주석으로 비교해두면 나중에 옛날 코드를 볼 때 도움이 됩니다.

```java
import java.util.Calendar;
import java.text.SimpleDateFormat;
```

### 과제 6 — `Timer.java` (선택)

step11 과제 4에서 `System.currentTimeMillis()` 로 시간을 쟀습니다.
이번엔 `Duration` 으로 재보세요.

```java
LocalDateTime start = LocalDateTime.now();
// 오래 걸리는 작업 (예: 1억 번 반복)
LocalDateTime end = LocalDateTime.now();
Duration d = Duration.between(start, end);
System.out.println(d.toMillis() + "ms");
```

## 5. 자가 점검

- [ ] `LocalDate` / `LocalTime` / `LocalDateTime` 을 상황에 맞게 고를 수 있다
- [ ] `Calendar` 의 월이 0부터 시작한다는 것을 안다
- [ ] `java.time` 객체가 불변이라 `d.plusDays(10)` 만으로는 안 바뀐다는 것을 안다
- [ ] `Period` 와 `ChronoUnit.DAYS.between()` 의 차이를 안다
- [ ] `format` 과 `parse` 의 방향을 구분한다
- [ ] 패턴에서 `MM`(월)과 `mm`(분)을 구분한다
- [ ] `DecimalFormat` 의 `0` 과 `#` 차이를 안다
- [ ] 숫자 3자리 콤마를 두 가지 방법으로 찍을 수 있다

---

**여기까지가 9~10장, 자바 표준 라이브러리의 기초입니다.**

다음은 step14(11장 1~2절) — **컬렉션 프레임웍**입니다.
step04에서 배열을 쓰면서 불편했던 것(크기가 고정이라 `push` 가 안 되는 것)이
`ArrayList` 로 해결됩니다. 프론트엔드의 JS 배열에 해당하는 게 자바 배열이 아니라
`ArrayList` 라는 것도 거기서 나옵니다.
