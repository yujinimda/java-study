// 과제 4 — CastLab.java

// 형변환 실험실. 아래를 각각 한 줄씩 출력하세요.

// double 3.99 를 int 로 바꾼 값 (소수점이 반올림되는지 버려지는지 확인)

// int 65 를 char 로 바꾼 값

// char '9' 를 int 로 바꾼 값 (9가 나오는지 확인 → 안 나옵니다. 왜?)

// char '9' 에서 숫자 9를 얻는 방법 (힌트: '9' - '0')

// int 300 을 byte 로 바꾼 값

// 3번과 5번은 결과가 예상과 다를 겁니다. 왜 그런지 노트에 쓰세요. 이게 과제의 핵심입니다.

public class CastLab {
  public static void main (String[] args){
    System.out.println( (int) 3.99 );  
    System.out.println( (char) 65 );  
    System.out.println( (int) '9' );  
    System.out.println( (int) '9' - '0' ); 
    System.out.println( (byte) 300 );  
  }
}