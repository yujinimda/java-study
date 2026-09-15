// step02 정답 확인용 프로그램 — 출력 예상 문제 + 손코딩 과제 모범답안
// 실행: ./run answers/step02/Step02Answers.java
public class Step02Answers {
    public static void main(String[] args) {
        System.out.println("=========== 출력 예상 문제 ===========");

        // ── 문제 1: 증감연산자 ──
        System.out.println("[문제 1]");
        int i = 5;
        System.out.println(i++);        // 5를 쓴 뒤 i=6
        System.out.println(i);          // 6
        System.out.println(++i);        // i=7로 올린 뒤 7을 쓴다
        System.out.println(i);          // 7
        System.out.println(i++ + ++i);  // i++ → 7 (i=8), ++i → 9 (i=9), 7+9
        System.out.println(i);          // 9

        // ── 문제 2: 산술변환 ──
        System.out.println("[문제 2]");
        byte a = 10, b = 20;
        System.out.println(a + b);      // byte+byte 인데 결과는 int
        // byte c = a + b;              // 에러: int를 byte에 넣을 수 없다
        byte c = (byte) (a + b);        // 이렇게 캐스팅하면 된다
        System.out.println(c);
        char x = 'a';
        System.out.println(x + 1);
        System.out.println(10 / 3);
        System.out.println(10 % 3);
        System.out.println(-10 % 3);
        System.out.println(10.0 / 3);

        // ── 문제 3: 단축평가 ──
        System.out.println("[문제 3]");
        int k = 0;
        boolean r1 = (false && (++k > 0));
        System.out.println(k);
        boolean r2 = (true || (++k > 0));
        System.out.println(k);
        boolean r3 = (true && (++k > 0));
        System.out.println(k);
        System.out.println(r1 + " " + r2 + " " + r3);

        // ── 문제 4: 삼항 연산자 ──
        System.out.println("[문제 4]");
        int score = 85;
        System.out.println(score >= 90 ? "A" : score >= 80 ? "B" : "C");
        int p = 5, q = 3;
        System.out.println(p > q ? p : q);
        System.out.println(p > q ? "크다" : 0);   // 컴파일은 된다 (아래 md 참고)

        // ── 문제 5: 대입 연산자 ──
        System.out.println("[문제 5]");
        int m = 10;
        m += 3;  System.out.println(m);
        m -= 5;  System.out.println(m);
        m *= 2;  System.out.println(m);
        m /= 3;  System.out.println(m);
        m %= 4;  System.out.println(m);

        // ── 문제 6: == 함정 ──
        System.out.println("[문제 6]");
        System.out.println(1 == 1.0);
        System.out.println('A' == 65);
        String s1 = "hi";
        String s2 = "hi";
        System.out.println(s1 == s2);
        String s3 = new String("hi");
        System.out.println(s1 == s3);
        System.out.println(s1.equals(s3));

        // ── 문제 7 ──
        // 컴파일 에러 2개 + 실행 에러 2개. 실행하면 프로그램이 죽으므로 여기서는 돌리지 않습니다.
        // answers/step02.md 참고.

        System.out.println();
        System.out.println("=========== 손코딩 과제 ===========");

        // ── 과제 1: Calculator ──
        System.out.println("[과제 1]");
        int n1 = 17, n2 = 5;
        System.out.println(n1 + " + " + n2 + " = " + (n1 + n2));
        System.out.println(n1 + " - " + n2 + " = " + (n1 - n2));
        System.out.println(n1 + " * " + n2 + " = " + (n1 * n2));
        System.out.println(n1 + " / " + n2 + " = " + (n1 / n2));
        System.out.println(n1 + " % " + n2 + " = " + (n1 % n2));
        System.out.println(n1 + " / " + n2 + " = " + ((double) n1 / n2) + " (정확한 값)");
        // 괄호가 필요한 이유: + 는 왼쪽부터 계산되므로 괄호가 없으면 문자열에 붙어버린다

        // ── 과제 2: EvenOdd ──
        System.out.println("[과제 2]");
        int n = 7;
        System.out.println(n + "은 " + (n % 2 == 0 ? "짝수" : "홀수"));

        // ── 과제 3: Max3 ──
        System.out.println("[과제 3]");
        int v1 = 12, v2 = 47, v3 = 33;
        int maxAB = v1 > v2 ? v1 : v2;              // 먼저 둘 중 큰 걸 고르고
        int max = maxAB > v3 ? maxAB : v3;           // 그걸 세 번째와 비교
        System.out.println("최대값 = " + max);
        // 한 줄로 쓰면: (v1 > v2 ? v1 : v2) > v3 ? (v1 > v2 ? v1 : v2) : v3
        // 읽기 어려워지므로 위처럼 쪼개는 게 낫다

        // ── 과제 4: DigitSum ──
        System.out.println("[과제 4]");
        int num = 4827;
        int d1000 = num / 1000;          // 4
        int d100 = num / 100 % 10;       // 48 % 10 = 8
        int d10 = num / 10 % 10;         // 482 % 10 = 2
        int d1 = num % 10;               // 7
        System.out.println(num + " 의 자리수 합 = " + (d1000 + d100 + d10 + d1));
        System.out.println("  각 자리: " + d1000 + ", " + d100 + ", " + d10 + ", " + d1);

        // ── 과제 5: LeapYear ──
        System.out.println("[과제 5]");
        printLeap(2024);
        printLeap(1900);
        printLeap(2000);

        // ── 과제 6: ShortCircuit ──
        System.out.println("[과제 6]");
        int count = 0;
        boolean t1 = (false && (++count > 0));
        System.out.println("false && ... 이후 count = " + count + "  (오른쪽을 아예 안 봤다)");
        boolean t2 = (true && (++count > 0));
        System.out.println("true  && ... 이후 count = " + count + "  (오른쪽을 봤다)");
        boolean t3 = (true || (++count > 0));
        System.out.println("true  || ... 이후 count = " + count + "  (오른쪽을 안 봤다)");
    }

    // 메서드는 step06에서 배웁니다. 지금은 "같은 코드를 세 번 안 쓰기 위한 도구" 정도로 보세요.
    static void printLeap(int year) {
        // 한국어로 먼저: (4의 배수이고 100의 배수가 아니다) 또는 (400의 배수다)
        boolean isLeap = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
        System.out.println(year + "년은 " + (isLeap ? "윤년" : "윤년 아님"));
    }
}
