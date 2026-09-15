public class Broken{
  public static void main (String args[]){
    System.out.println("에러 테스트");
  }
}

// zini@yujinui-MacBookPro java-study % ./run BrokenOnPurpose.java
// 파일이 없습니다: BrokenOnPurpose.java

// 에러 테스트
// zini@yujinui-MacBookPro java-study % ./run step00/BrokenOnPurpose.java
// src/step00/BrokenOnPurpose.java:3: error: ';' expected
//     System.out.println("에러 테스트")
//                                 ^
// 1 error

// ── 컴파일 실패. 에러 메시지를 notes/errors.md 에 적어두세요. ──
// // zini@yujinui-MacBookPro java-study % 
// ─ 컴파일 실패. 에러 메시지를 notes/errors.md 에 적어두세요. ──
// zini@yujinui-MacBookPro java-study % ./run step00/BrokenOnPurpose.java
// src/step00/BrokenOnPurpose.java:3: error: cannot find symbol
//     System.out.printLn("에러 테스트");
//               ^
//   symbol:   method printLn(String)
//   location: variable out of type PrintStream
// 1 error

// ── 컴파일 실패. 에러 메시지를 notes/errors.md 에 적어두세요. ──
// zini@yujinui-MacBookPro java-study % 
// ── 컴파일 실패. 에러 메시지를 notes/errors.md 에 적어두세요. ──
// zini@yujinui-MacBookPro java-study % ./run step00/BrokenOnPurpose.java
// src/step00/BrokenOnPurpose.java:5: error: reached end of file while parsing
// }
//  ^
// 1 error

// ── 컴파일 실패. 에러 메시지를 notes/errors.md 에 적어두세요. ──
// zini@yujinui-MacBookPro java-study % 


// ── 컴파일 실패. 에러 메시지를 notes/errors.md 에 적어두세요. ──
// zini@yujinui-MacBookPro java-study % ./run step00/BrokenOnPurpose.java
// src/step00/BrokenOnPurpose.java:1: error: class Broken is public, should be declared in a file named Broken.java
// public class Broken{
//        ^
// 1 error

// ── 컴파일 실패. 에러 메시지를 notes/errors.md 에 적어두세요. ──
// zini@yujinui-MacBookPro java-study % 