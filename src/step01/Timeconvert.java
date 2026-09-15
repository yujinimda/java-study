// 과제 2 — TimeConvert.java

// int totalSeconds = 10000; 하나만 주어졌을 때, 이걸 시 / 분 / 초 로 나눠 출력하세요.

// 10000초 = 2시간 46분 40초

// / 와 % 만 씁니다. 종이에서 먼저 계산식을 세우세요. (10000 / 3600 은? 나머지는?)

public class TimeConvert {
  public static void main (String[] args) {
    int totalSeconds = 10000;
    int hour = totalSeconds / 3600;
    int rest = totalSeconds % 3600;
    int minute = rest / 60;
    int second = rest % 60;
    

    System.out.println(hour + "시간" + minute + "분" + second + "초");
  }
}
