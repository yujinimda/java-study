// step03 정답 확인용 프로그램 — 출력 예상 문제 + 손코딩 과제 모범답안
// 실행: ./run answers/step03/Step03Answers.java
// (과제 7 NumberGame 은 입력을 받아야 하므로 별도 파일입니다: ./run answers/step03/NumberGame.java)
public class Step03Answers {
    public static void main(String[] args) {
        System.out.println("=========== 출력 예상 문제 ===========");

        // ── 문제 1 ──
        System.out.println("[문제 1]");
        for (int i = 0; i < 3; i++) {
            System.out.println("i = " + i);
        }
        System.out.println("끝: " + 3);

        // ── 문제 2 ──
        System.out.println("[문제 2]");
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 2; j++) {
                System.out.println(i + "-" + j);
            }
        }

        // ── 문제 3 ──
        System.out.println("[문제 3]");
        for (int i = 1; i <= 5; i++) {
            if (i == 3) continue;   // 이번 회차만 건너뛰고 다음 회차로
            if (i == 5) break;      // 반복문 자체를 끝낸다
            System.out.print(i + " ");
        }
        System.out.println();

        // ── 문제 4 ──
        System.out.println("[문제 4]");
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if (j == 2) break;  // 안쪽 for 만 빠져나온다. 바깥은 계속 돈다
                System.out.print(i + "" + j + " ");
            }
        }
        System.out.println();

        // ── 문제 5: switch fall-through ──
        System.out.println("[문제 5]");
        int sn = 2;
        switch (sn) {
            case 1:
                System.out.println("하나");
            case 2:
                System.out.println("둘");      // 여기서 시작
            case 3:
                System.out.println("셋");      // break 가 없어서 흘러내려온다
                break;                          // 여기서 탈출
            case 4:
                System.out.println("넷");
            default:
                System.out.println("기타");
        }

        // ── 문제 6: while vs do-while ──
        System.out.println("[문제 6]");
        int i6 = 10;
        while (i6 < 5) {                  // 조건이 처음부터 false → 한 번도 실행 안 됨
            System.out.println("while: " + i6);
            i6++;
        }
        int j6 = 10;
        do {                              // 조건을 나중에 보므로 최소 1번은 실행된다
            System.out.println("do-while: " + j6);
            j6++;
        } while (j6 < 5);

        // ── 문제 7: 무한루프 ──
        System.out.println("[문제 7]");
        // A, B 는 실행하면 멈추지 않으므로 돌리지 않습니다. md 참고.
        for (int i = 0; i < 5; i++) ;      // C: 세미콜론이 곧 빈 몸통. 5번 돌고 끝. 출력 없음
        System.out.println("C는 5번 돌고 정상 종료 (출력이 없을 뿐)");

        // ── 문제 8: 스코프 ──
        // for 안에서 선언한 i 는 for 를 벗어나면 사라진다. 밖에서 쓰려면 밖에 선언해야 한다.
        int outside = 0;
        for (outside = 0; outside < 3; outside++) { }
        System.out.println("[문제 8] for 밖에 선언하면 쓸 수 있다: " + outside);

        System.out.println();
        System.out.println("=========== 손코딩 과제 ===========");

        // ── 과제 1: Gugudan ──
        System.out.println("[과제 1] 7단");
        int dan = 7;
        for (int j = 1; j <= 9; j++) {
            System.out.println(dan + " x " + j + " = " + (dan * j));
        }
        System.out.println("[과제 1] 2~9단 전체");
        for (int d = 2; d <= 9; d++) {
            for (int j = 1; j <= 9; j++) {
                System.out.print(d + "x" + j + "=" + (d * j) + "\t");
            }
            System.out.println();   // 한 단이 끝나면 줄바꿈
        }

        // ── 과제 2: SumTo100 ──
        System.out.println("[과제 2]");
        int sumFor = 0;
        for (int j = 1; j <= 100; j++) {
            sumFor += j;
        }
        int sumWhile = 0;
        int w = 1;                  // 초기화
        while (w <= 100) {           // 조건식
            sumWhile += w;           // 블록
            w++;                     // 증감식
        }
        System.out.println("for   = " + sumFor);
        System.out.println("while = " + sumWhile);

        // ── 과제 3: Stars ──
        System.out.println("[과제 3-1] 사각형");
        for (int r = 1; r <= 5; r++) {
            for (int cc = 1; cc <= 5; cc++) {
                System.out.print("*");
            }
            System.out.println();
        }
        System.out.println("[과제 3-2] 왼쪽 삼각형");
        for (int r = 1; r <= 5; r++) {
            for (int cc = 1; cc <= r; cc++) {   // 별 개수 = 줄 번호
                System.out.print("*");
            }
            System.out.println();
        }
        System.out.println("[과제 3-3] 오른쪽 정렬 삼각형");
        for (int r = 1; r <= 5; r++) {
            for (int s = 1; s <= 5 - r; s++) {  // 공백 개수 = 5 - 줄 번호
                System.out.print(" ");
            }
            for (int cc = 1; cc <= r; cc++) {   // 별 개수 = 줄 번호
                System.out.print("*");
            }
            System.out.println();
        }

        // ── 과제 4: Prime ──
        System.out.println("[과제 4]");
        int target = 97;
        boolean isPrime = true;
        for (int j = 2; j < target; j++) {
            if (target % j == 0) {
                isPrime = false;
                break;              // 하나라도 나누어지면 더 볼 필요 없다
            }
        }
        System.out.println(target + "은 " + (isPrime ? "소수" : "소수 아님"));

        System.out.print("100 이하 소수: ");
        int primeCount = 0;
        for (int cand = 2; cand <= 100; cand++) {
            boolean prime = true;
            for (int j = 2; j < cand; j++) {
                if (cand % j == 0) {
                    prime = false;
                    break;
                }
            }
            if (prime) {
                System.out.print(cand + " ");
                primeCount++;
            }
        }
        System.out.println("\n총 " + primeCount + "개");

        // ── 과제 5: Gcd ──
        System.out.println("[과제 5]");
        int ga = 48, gb = 18;

        // 방법 1: 작은 수부터 1까지 내려가며 둘 다 나누어지는 첫 수
        int smaller = ga < gb ? ga : gb;
        int gcd1 = 1;
        for (int j = smaller; j >= 1; j--) {
            if (ga % j == 0 && gb % j == 0) {
                gcd1 = j;
                break;
            }
        }
        System.out.println("방법1 (완전탐색)   = " + gcd1);

        // 방법 2: 유클리드 호제법 — a % b 를 반복하며 b 가 0이 되면 a 가 답
        int ea = 48, eb = 18;
        while (eb != 0) {
            int r = ea % eb;
            ea = eb;
            eb = r;
        }
        System.out.println("방법2 (유클리드)   = " + ea);

        // ── 과제 6: Grade ──
        System.out.println("[과제 6]");
        int score = 87;

        // 6-1) if - else if
        String g1;
        if (score >= 90)      g1 = "A";
        else if (score >= 80) g1 = "B";
        else if (score >= 70) g1 = "C";
        else                  g1 = "F";
        System.out.println("if       → " + g1);

        // 6-2) 삼항 연산자 중첩
        String g2 = score >= 90 ? "A" : score >= 80 ? "B" : score >= 70 ? "C" : "F";
        System.out.println("삼항     → " + g2);

        // 6-3) switch (score / 10) — fall-through 를 일부러 활용
        String g3;
        switch (score / 10) {
            case 10:
            case 9:
                g3 = "A"; break;
            case 8:
                g3 = "B"; break;
            case 7:
                g3 = "C"; break;
            default:
                g3 = "F";
        }
        System.out.println("switch   → " + g3);

        // ── 과제 8: FizzBuzz ──
        System.out.println("[과제 8]");
        for (int j = 1; j <= 30; j++) {
            // 15의 배수를 먼저 검사해야 한다. 3부터 검사하면 15가 Fizz 로 끝나버린다
            if (j % 15 == 0)      System.out.print("FizzBuzz ");
            else if (j % 3 == 0)  System.out.print("Fizz ");
            else if (j % 5 == 0)  System.out.print("Buzz ");
            else                  System.out.print(j + " ");
        }
        System.out.println();
    }
}
