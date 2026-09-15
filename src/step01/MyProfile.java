// 과제 1 — MyProfile.java

// String name, int age, double height, char bloodType, boolean isStudent
// 5개 변수를 선언·초기화하고 아래처럼 한 줄로 출력하세요.

// 지니 / 28세 / 165.5cm / A형 / 학생아님

// boolean 을 "학생" / "학생아님" 으로 바꿔 찍는 건 아직 배우지 않았습니다.
// isStudent 값을 그대로 출력해도 됩니다. (조건문은 step03)


public class MyProfile {
  public static void main(String[] args){
    String name = "지니";
    int age = 28;
    double height = 165.5;
    char bloodType = 'A';
    boolean isStudent = false;


    System.out.println(name + " / " + age + "세" + " / " + height + "cm" + " / " + bloodType + "형" +  " / " + isStudent );
      }
}


