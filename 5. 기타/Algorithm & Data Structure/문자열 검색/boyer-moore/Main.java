// Boyer-Moore법으로 문자열을 검색 (Bad Character Heuristic)
public class Main {
  static int bmMatch(String txt, String pat) {
    int pt, pp;                   // txt 커서, pat 커서
    int txtLen = txt.length();    // txt의 문자 개수 
    int patLen = pat.length();    // pat의 문자 개수 
    int[] skip = new int[Character.MAX_VALUE + 1];    // 건너뛰기 표 

    // 건너뛰기 표 만들기 
    for(pt = 0; pt <= Character.MAX_VALUE; pt++)
      skip[pt] = patLen;

    for(pt = 0; pt < patLen - 1; pt++)
      skip[pat.charAt(pt)] = patLen - pt - 1;   // pt == patLen - 1

    // 검색 
    while(pt < txtLen) {
      pp = patLen - 1;     // pat의 끝 문자에 주목 
      while (txt.charAt(pt) == pat.charAt(pp)) {
        if(pp == 0)
          return pt;    // 검색 성공 
        pt--;
 
      // 따라서, pp = patLen - 1 로 옮겨주고 pt의 경우 (patLen - 1) - pp)만큼 움직였으므로 pt += (patLen - 1) - pp) + 1 로 해주면서 결과적으론 처음 상황에서 한 칸 Shift된 상황인 것이다.
      pt += (skip[txt.charAt(pp)] > patLen - pp) ? skip[txt.charAt(pp)] : patLen - pp;
    }
    return -1;    // 검색 실패 
  }
  
  // (Bad Character Heuristic + Good Suffix Heuristic)
  int perfectBmMatch(String txt, String pat) {
    int txtLen = txt.length();
    int patLen = pat.length();
    int[] badSkip = new int[Character.MAX_VALUE + 1];
    int[] goodSkip = new int[patLen];

    int shift = 0, pt = 0;

    // badSkip initiallize
    // 전부 -1로 초기화
    for(; pt <= Character.MAX_VALUE + 1; pt++)
      badSkip[pt] = -1;

    // value == Index
    for(pt = 0; pt < patLen - 1; pt++)
      badSkip[pat.charAt(pt)] = pt;       // pt = patLen - 1

    // goodSkip initiallize

    for(int i = 0; i < patLen; i++) {
      goodSkip[i] = patLen;
    }

    int pp = 0;
    while (pt > 0) {
        while (pp >= 0 && pat.charAt(pt + pp) == pat.charAt(pp)) {
        pp--;
      }
      if(pp < 0) {
        for(; pt + pp >= 0; pp--) {
          goodSkip[pt + pp] = pt;
        }
      }
      else {
        goodSkip[pt + pp] = pt;
      }
      pp = patLen - pt;
      pt--;
    }
    
    while (shift <= txtLen - patLen) {
      pp = patLen - 1;
      while(pp >= 0 && txt.charAt(shift + pp) == pat.charAt(pp))
        pp--;
      
      if(pp < 0 ) {
        return shift;
        // 만약 여러개 구하고 싶다면
        // List<Integer>에 add 해주고 이후에도 진행하고 싶다면
        // shift += goodskip[0]
      }

      // badShift 구하는 과정
      // 여기서 max function을 사용하는 이유는 확실히 양수를 얻기 위함에 있다.
      // Negative Shift를 얻을 수 있는데 만약, 비교 과정에서 불일치할 때의 txt의 bad Character가 pattern에서 pp index보다 오른쪽에 위치해 있다면
      // 음수가 나올 것이다. (pp, badSkip Ele = index) 참고: https://greatzzo.tistory.com/8
      int badShift = Math.max(pp - badSkip[txt.charAt(shift + pp)], 1);
      int goodShift = goodSkip[pp];

      shift = goodShift > badShift ? goodShift : badShift;
    }
    return shift;
  }
}