public class Main {
  static int kmpMatch(String txt, String pat) {
    int pt = 1;   // txt 커서
    int pp = 0;   // pat 커서
    int[] pi = new int[pat.length() + 1];   // 건너뛰기 표

    // 건너뛰기 표를 만듭니다.
    // 표를 만들떄는 일치하는 숫자 갯수 라는 개념이 강하게 생각됨 
    pi[pt] = 0;
    while (pt != pat.length()) {
      if (pat.charAt(pt) == pat.charAt(pp))
        pi[++pt] = ++pp;
      else if (pp == 0)
        pi[++pt] = pp;
      else
        pp = pi[pp];
    }

    // 검색
    // 검색 과정에선 커서의 개념이 강하게 생각됨
    pt = pp = 0;
    while (pt != txt.length() && pp != pat.length()) {
      if (txt.charAt(pt) == pat.charAt(pp)) {
        pt++;
        pp++;
      } else if (pp == 0)
        pt++;
      else
        pp = pi[pp];
    }

    if(pp == pat.length())
      return pt - pp;
    
    // fail Search
    return -1;
  }


  // 다른 방식 

  static void searchKmpPi(String txt, String pat) {

    int[] pi = new int[pat.length() + 1];   // 건너뛰기 표

    getPi(pat, pi);

    kmp(txt, pat, pi);

  }

  // 패턴 일치 저장 배열
  static void getPi(String pat, int[] pi){
    // j = 접두사, i 접미사
    int j = 0;

    // 패턴을 돌면서 체크
    for(int i = 1; i < pat.length(); i++){
        // 처음 접두사가 아니면서 일치하지 않으면, 반복되는 패턴의 앞부분으로 변경
        // 만약 반복되는 패턴이 없으면, j = 0까지 돌아갈거임
        while(j > 0 && pat.charAt(i) != pat.charAt(j)){
            j = pi[j-1];
        }
        // 일치할때, 접두사의 길이(경계) 저장
        // 현재 맞은 idx의 +1은 길이이자, 다음 체크할 idx가 됨
        if(pat.charAt(i) == pat.charAt(j)){
            pi[i] = ++j;
        }
    }
  }

  public static void kmp(String all, String pat, int[] pi){
    // 패턴 내 일치체크 idx
    int j = 0;
    // 전체 문자열 돌기
    for (int i = 0; i < all.length(); i++) {
      // 맞는 위치가 나올 때까지 j - 1칸의 공통 부분 위치로 이동
      while(j > 0 && all.charAt(i) != pat.charAt(j)) {
        j = pi[j - 1];
      }
      // 맞는 경우 패턴의 끝까지 확인했으면 정답
      if(all.charAt(i) == pat.charAt(j)) {
        if(j == pat.length() - 1) {
          // Success Code 
          break;
        // 아니면 패턴의 다음 문자를 확인
        } else{
          j++;
        }
      }
    }
  }
}
