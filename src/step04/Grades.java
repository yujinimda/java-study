// 과제 4 — Grades.java

// String[] names = {"지니", "민수", "서연"};
// int[] scores = {88, 92, 75};

// 두 배열을 같은 인덱스끼리 짝지어 출력하세요.

// 지니: 88점
// 민수: 92점
// 서연: 75점

// 배열 두 개를 나란히 쓰는 방식입니다. step05에서는 이걸 클래스 하나로 묶게 됩니다.

  public class Grades {
      public static void main(String[] args) {
          String[] names = {"지니", "민수", "서연"};
          int[] scores = {88, 92, 75};

          for (int i = 0; i < names.length; i++) {
              System.out.println(names[i] + ": " + scores[i] + "점");
          }
      }
  }