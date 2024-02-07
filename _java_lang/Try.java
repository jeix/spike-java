
import java.io.File;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class Try {
  
  public static void main(String[] args) {

    if (false) {
      // 숫자 파싱
      // null
      String s = null;
      try {
          System.out.println(Integer.valueOf(s));
      } catch (NumberFormatException nfe) {
          nfe.printStackTrace(); //-> java.lang.NumberFormatException: null
      }
      // 정상 숫자
      s = "141652569";
      System.out.println(Integer.valueOf(s).toString()); //-> 141652569
    }
    
    if (false) {
      // 첫 글자와 마지막 글자 제거
      String s = "[12345]";
      System.out.println(s.substring(1, s.length()-1)); //-> 12345
    }
    
    if (false) {
      // Arrays.asList
      List<String> list = Arrays.asList("77332dc7-a617-4a63-83a8-89f2c195598f","eaf14bd8-56f0-4659-b79b-e2f12c37814c","cb433d09-0aad-4cb6-9180-c70e9a313081").subList(0, 3);
      System.out.println(list.size()); //-> 3
      // 리스트 to 배열
      String[] array = list.toArray(new String[] {});
      System.out.println(String.join(",", array));
          //-> 77332dc7-a617-4a63-83a8-89f2c195598f,eaf14bd8-56f0-4659-b79b-e2f12c37814c,cb433d09-0aad-4cb6-9180-c70e9a313081
      // 배열 to 리스트
      System.out.println(Arrays.asList(array));
          //-> [77332dc7-a617-4a63-83a8-89f2c195598f, eaf14bd8-56f0-4659-b79b-e2f12c37814c, cb433d09-0aad-4cb6-9180-c70e9a313081]
    }
    
    if (false) {
      // 오브젝트스토리지 버킷 내 파일 경로를 윈도우 경로로 변환
      String bucketName = "sisto-bbox";
      String objectName = "111111/emggps_.dat";
      String downloadPath = "D:\\zzz\\s3test\\" + bucketName + "\\" + objectName.replaceAll("/", "\\\\");
      System.out.println(downloadPath);
          //-> D:\zzz\s3test\sisto-bbox\111111\emggps_.dat
    }
    
    if (false) {
      // 파일시스템 속성
      System.out.println(File.pathSeparator); //-> :
      System.out.println(File.separator); //-> /
      // 파일 경로
      Path path = Paths.get("opt", "local", "bin");
      System.out.println(path.toString()); //-> opt/local/bin
    }
    
    if (false)  {
      // String#split
      String s = "12.34.56.78:/n1234_123";
      String[] sa = s.split(":/");
      System.out.println("["+sa[0]+"]["+sa[1]+"]"); //-> [12.34.56.78][n1234_123]
    }
    
    if (false) {
      // 마스킹
      // 첫번째 글자만 남기고 마스킹
      List<String> names = Arrays.asList("가", "가나", "가나다", "가나다라");
      for (String name : names) {
        String masked = name.substring(0, 1) + name.substring(1).replaceAll(".", "*");
        System.out.println(masked);
            //-> 가
            //-> 가*
            //-> 가**
            //-> 가***
      }
      // 첫번째 글자와 마지막 글자 사이를 마스킹
      names = Arrays.asList("가나", "가나다", "가나다라");
      for (String name : names) {
        int len = name.length();
        String masked = name.substring(0, 1) + name.substring(1, len - 1).replaceAll(".", "*") + name.substring(len - 1);
        System.out.println(masked);
            //-> 가나
            //-> 가*다
            //-> 가**라
      }
    }

    if (false) {
      // 사용자 입력글 처리
      String s = "안녕 세계\n안녕 <거기>\n거기 서라";
      // 태그 제거
      s = s.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
      // 줄바꿈을 문단으로 변환
      s = "<p>" + s.replaceAll("\\n", "</p><p>") + "</p>";
      System.out.println(s);
          //-> <p>안녕 세계</p><p>안녕 &lt;거기&gt;</p><p>거기 서라</p>
    }
  }
}