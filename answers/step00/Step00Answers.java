// step00 손코딩 과제 모범답안
// 실행: ./run answers/step00/Step00Answers.java 안녕
public class Step00Answers {
    public static void main(String[] args) {
        // ── 과제 1: Hello.java ──
        System.out.println("이름: 지니");
        System.out.println("나이: 28");
        System.out.println("좋아하는 것: 커피");

        System.out.println();

        // ── 과제 3: Args.java ──
        // args 는 실행할 때 뒤에 붙인 값들이 담기는 통로다.
        System.out.println("넘어온 값의 개수: " + args.length);
        if (args.length > 0) {          // if 는 step03에서 배웁니다
            System.out.println("args[0] = " + args[0]);
        } else {
            System.out.println("넘긴 값이 없어서 args[0] 을 읽으면 에러가 납니다.");
        }

        // ── 과제 2: BrokenOnPurpose.java ──
        // 일부러 에러를 내는 과제라 정답 코드가 없습니다.
        // 대신 각 실수가 어떤 에러 메시지로 나오는지는 answers/step00.md 에 정리돼 있습니다.
    }
}
