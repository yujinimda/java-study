// 과제 1 — Calculator.java

// int a = 17, b = 5; 로 사칙연산 + 나머지 5줄을 출력하세요.

// 17 + 5 = 22
// 17 - 5 = 12
// 17 * 5 = 85
// 17 / 5 = 3
// 17 % 5 = 2

public class Calculator {
  public static void main(String[] args){
  int a = 17;
  int b = 5;
   System.out.println (a + " + " + b + " = " + (a + b));
   System.out.println (a + " - " + b + " = " + (a - b));
   System.out.println (a + " * " + b + " = " + (a * b));
   System.out.println (a + " / " + b + " = " + ((double) a / b));
   System.out.println (a + " % " + b + " = " + (a % b));
  }
}