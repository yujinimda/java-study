// 과제 3 — Swap.java

// int a = 10; int b = 20; 의 값을 서로 바꿔서 출력하세요.
// temp 변수를 쓰는 방식으로, 종이에 각 줄 실행 후 a, b, temp 값을 표로 적어가며 쓰세요.

public class Swap {
  public static void main (String[] args){
    int a = 10;
    int b = 20;
    int temp = a;
    a = b;
    b = temp;
    System.out.println(a + " " + b);
  }
}











