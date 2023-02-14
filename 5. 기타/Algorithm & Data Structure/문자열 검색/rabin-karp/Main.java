import java.util.ArrayList;
import java.util.List;

public class Main {

  // 가장 기본적인 코드
  public List<Integer> find(String parent, String pattern) {
    int parentHash = 0, patternHash = 0, power = 1;

    int parentLen = parent.length();
    int patternLen = pattern.length();

    // 패턴이 일치하는 위치를 저장할 리스트
    List<Integer> idxList = new ArrayList<>();

    for(int i = 0; i <= parentLen - patternLen; i++) {

      if(i == 0) {
        // 전체 문자열에 첫 파트의 Hash 코드 구하기
        // 패턴 문자열 Hash 코드 구하기
        for(int j = 0; j < patternLen; j++){
          // charAt안의 숫자는 둘 다 동일해야 하므로 patterLen을 써줘야 하는 것에 주의하자.
          // 또한 오른쪽 숫자부터 왼쪽으로 power를 높게 적용해야 아랫식이 적용되므로 이 또한 유의하자.
          parentHash = parentHash + parent.charAt(patternLen - 1 - j) * power;
          patternHash = patternHash + pattern.charAt(patternLen - 1 - j) * power;

          // power: 가장 앞에 있는 문자의 해시 값을 구할 때의 power 값으로 고정
          if(j < patternLen - 1) {
            power *= 2;
          }
        }
      } else {
        // 긴글 Hash 값 = 2 * (이전 파트 Hash 값 - 이전 파트에서 가장 앞에 있는 문자) + 새롭게 들어운 문자
        parentHash = (parentHash - parent.charAt(i - 1) * power) * 2
                + parent.charAt(i + parentLen - 1);
      }

      // Hash 코드 일치
      if(parentHash == patternHash) {
        boolean found = true;

        // 다시 한번 문자열이 맞는지 검사
        for(int j = 0; j < patternLen; j++) {
          if(parent.charAt(i + j) != pattern.charAt(j)) {
            found = false;
            break;
          }
        }
        if(found) {
          idxList.add(i + 1);
        }
      }
    }
    return idxList;
  }

  // MOD를 이용한 Rabin-Karp Algorithm
  List<Integer> findStringPattern(String parent, String pattern) {
    final int MOD = 100000007;

    List<Integer> idxList = new ArrayList<>();

    int parentSize = parent.length();
    int patternSize = pattern.length();

    long parentHash = 0, patternHash = 0, power = 1;

    for(int i = 0; i <= parentSize - patternSize; i++) {
      if(i == 0) {
        for(int j = 0; j < patternSize; j++) {
          parentHash = (parentHash + (parent.charAt(patternSize - 1- j) * power)) % MOD;
          patternHash = (patternHash + (pattern.charAt(patternSize - 1- j) * power)) % MOD;

          if(j < patternSize - 1) {
            power = (power * 31) % MOD;
          }
        }
      } else {
        parentHash = 31 * (parentHash - 31 * parent.charAt(i - 1) * power % MOD) + parent.charAt(i + patternSize - 1);
        parentHash  %= MOD;
      }

      if(parentHash == patternHash) {
        boolean find = true;
        for(int j = 0 ; j < patternSize; j++) {
          if(parent.charAt(i + j) != pattern.charAt(j)) {
            find = false;
            break;
          }
        }
        if(find) idxList.add(i + 1);
      }
    }
    return idxList;
  }

  public static void main(String[] args) {

  }
}
