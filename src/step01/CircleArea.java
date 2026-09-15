// 과제 5 — CircleArea.java

// 반지름을 final double PI = 3.14159; 와 함께 써서 원의 면적과 둘레를 출력하세요.
// final 을 쓰는 이유를 한 줄 주석으로 붙이세요.


  public class CircleArea {
    public static void main(String[] args) {
      final double PI = 3.14159;   
      // 절대 바뀌지 않는 값: 상수
      double radius = 5.0;

      System.out.println("면적 = " + (PI * radius * radius));
      System.out.println("둘레 = " + (2 * PI * radius));
    }
  }