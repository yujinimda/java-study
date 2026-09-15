// step03 과제 7 모범답안 — Scanner 로 입력 받기
// 실행: ./run answers/step03/NumberGame.java
// 빠져나오려면 정답(42)을 맞히거나 Ctrl+C
import java.util.Scanner;

public class NumberGame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        final int answer = 42;
        int input;

        System.out.println("1~100 사이의 숫자를 맞혀보세요.");

        // do-while 을 쓰는 이유: 입력을 최소 한 번은 받아야 비교할 값이 생긴다.
        // while 로 쓰면 조건에서 쓸 input 을 미리 만들어둬야 해서 어색해진다.
        do {
            System.out.print("입력> ");
            input = sc.nextInt();

            if (input > answer) {
                System.out.println("더 작게");
            } else if (input < answer) {
                System.out.println("더 크게");
            } else {
                System.out.println("정답!");
            }
        } while (input != answer);

        sc.close();
    }
}
