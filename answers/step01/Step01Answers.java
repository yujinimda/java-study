// step01 정답 확인용 프로그램 — 출력 예상 문제 + 손코딩 과제 모범답안
// 실행: ./run answers/step01/Step01Answers.java
public class Step01Answers {
    public static void main(String[] args) {
        System.out.println("=========== 출력 예상 문제 ===========");

        // ── 문제 1 ──
        System.out.println("[문제 1]");
        int a = 7, b = 2;
        System.out.println(a / b);
        System.out.println(a % b);
        System.out.println((double) a / b);
        System.out.println(a / (double) b);
        System.out.println((double) (a / b));

        // ── 문제 2 ──
        System.out.println("[문제 2]");
        char ch = 'A';
        int i = ch;
        System.out.println(i);
        System.out.println(ch + 1);
        System.out.println((char) (ch + 1));
        System.out.println("" + ch + 1);

        // ── 문제 3 ──
        System.out.println("[문제 3]");
        byte bt = 127;
        bt++;
        System.out.println(bt);

        // ── 문제 4 ──
        System.out.println("[문제 4]");
        System.out.println(0.1 + 0.2);
        System.out.println(0.1 + 0.2 == 0.3);
        float f = 0.1f;
        double d = f;
        System.out.println(d);

        // ── 문제 5 ──
        // 전부 컴파일 에러라서 실행할 수 없습니다. answers/step01.md 참고.

        // ── 문제 6 ──
        System.out.println("[문제 6]");
        int n = 10;
        double dn = n;          // 자동 형변환 (int → double, 손실 없음)
        int back = (int) dn;    // 명시적 형변환 필요 (double → int, 손실 가능)
        System.out.println(dn + " / " + back);

        System.out.println();
        System.out.println("=========== 손코딩 과제 ===========");

        // ── 과제 1: MyProfile ──
        System.out.println("[과제 1]");
        String name = "지니";
        int age = 28;
        double height = 165.5;
        char bloodType = 'A';
        boolean isStudent = false;
        System.out.println(name + " / " + age + "세 / " + height + "cm / "
                + bloodType + "형 / 학생여부=" + isStudent);

        // ── 과제 2: TimeConvert ──
        System.out.println("[과제 2]");
        int totalSeconds = 10000;
        int hour = totalSeconds / 3600;              // 3600초 = 1시간
        int minute = totalSeconds % 3600 / 60;       // 시간을 뺀 나머지에서 분
        int second = totalSeconds % 60;              // 60으로 나눈 나머지가 초
        System.out.println(totalSeconds + "초 = " + hour + "시간 " + minute + "분 " + second + "초");

        // ── 과제 3: Swap ──
        System.out.println("[과제 3]");
        int x = 10;
        int y = 20;
        System.out.println("바꾸기 전: x=" + x + ", y=" + y);
        int temp = x;   // temp=10
        x = y;          // x=20
        y = temp;       // y=10
        System.out.println("바꾼 후:   x=" + x + ", y=" + y);

        // ── 과제 4: CastLab ──
        System.out.println("[과제 4]");
        System.out.println("1) (int) 3.99      = " + (int) 3.99);
        System.out.println("2) (char) 65       = " + (char) 65);
        System.out.println("3) (int) '9'       = " + (int) '9');
        System.out.println("4) '9' - '0'       = " + ('9' - '0'));
        System.out.println("5) (byte) 300      = " + (byte) 300);

        // ── 과제 5: CircleArea ──
        System.out.println("[과제 5]");
        final double PI = 3.14159;   // final: 한 번 정한 뒤 바꿀 수 없게 잠근다
        double radius = 5.0;
        System.out.println("반지름 " + radius);
        System.out.println("면적   = " + (PI * radius * radius));
        System.out.println("둘레   = " + (2 * PI * radius));
    }
}
