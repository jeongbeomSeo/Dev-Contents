import java.util.NoSuchElementException;

// 문자열이 전부 영어 소문자로만 이루어졌다고 판단하고 진행
// Root Node는 아무런 문자열(접두사)도 포함하지 않고 모든 문자열의 접두사들을 자식 배열로 갖고 있어야 한다.
public class Trie_Using_Array {
  public static void main(String[] args) {
    Trie_Using_Array trie = new Trie_Using_Array();
    trie.insert("bar");
    trie.insert("bag");
    trie.insert("bark");
    trie.insert("main");
    trie.insert("battle");
    trie.insert("show");
    trie.insert("dog");
    trie.insert("do");
    trie.insert("door");

    System.out.println(trie.find("bag") ? "Yes!, bag is exist!" : "No, bag does not exist..");
    System.out.println(trie.find("baga") ? "Yes!, baga is exist!" : "No, baga does not exist..");
    System.out.println("Trie 내 문자열 전체 출력");
    trie.printTrie();

    System.out.println("LPM 찾기!! ");
    String input = "showroom";
    System.out.println("[" + input + "] LPM - " + trie.getLPM(input));

    input = "battlefield";
    System.out.println("[" + input + "] LPM - " + trie.getLPM(input));

    input = "d";
    System.out.println("[" + input + "] LPM - " + trie.getLPM(input));


    System.out.println("Trie 내 일부 문자 삭제 테스트");
    try {
      trie.delete("do");
      trie.delete("doll");

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    System.out.println("Trie 내 문자열 전체 출력");
    trie.printTrie();
  }

  Node root;
  static final int ALPHABET_SIZE = 26;    // a-z는 26개

  // Root Node의 내용 정의
  public Trie_Using_Array() {
    this.root = new Node();
    this.root.val = ' ';
  }

  private static class Node {
    Node[] child = new Node[ALPHABET_SIZE];     // 뒤로 연결되는 문자열 a-z 소문자를 index화하여 저장하는 배역(26개)
    boolean isTerminal = false;   // 현재 노드가 문자 완성이 되는 노드인지 여부
    int childNum = 0;             // 현재 노드에 연결된 문자열의 개수
    char val;                     // 현재 노드의 값
  }

  private int charToInt(char c) {
    return c - 'a';   // 여기서는 소문자만 있으므로 'a'를 빼면 된다.
  }

  // 전체 문자열을 쪼개서 각 Node에 저장하는 메소드
  public void insert(String str) {
    int length = str.length();
    Node current = this.root;       // 루트 부터 시작해서 내려감
    for(int i = 0; i < length; i++) {
      char c = str.charAt(i);       // 전체 문자열의 일부 단어 추출
      int num = this.charToInt(c);  // 추출한 단어를 숫자로 변환

      if(current.child[num] == null) {    // 기존에 null이면 연결 문자열로 처음 추가되는 것
        current.child[num] = new Node();
        current.child[num].val = c;
        current.childNum++;
      }

      current = current.child[num];   // 자식 노드로 넘어감
    }
    current.isTerminal = true;
  }

  // 사용자가 호출 시 사용, 간편히 문자열만 전달
  // 반복문으로 노드를 순황하여 문자열 존재 여부 판단
  public boolean find(String str) {
    int length = str.length();
    Node current = this.root;   // 현재 노드 설정

    for(int i = 0; i < length; i++) {
      char c = str.charAt(i);
      int num = this.charToInt(c);
      // 문자열의 일부를 추출했는데 null이라면 false를 반환
      if(current.child[num] == null) {
        return false;
      }
      current = current.child[num];
    }
    return current != null && current.isTerminal; // 문자열의 마지막이라면 true
  }

  // 모든 저장된 단어의 내역을 알기 쉽게 프린트해주는 메소드
  private char intToChar(int i) {
    return (char)(i + (int)'a');
  }

  public void printTrie() {   // 사용자가 간편히 호출만 하면 되는 메소드
    this.printTrie(this.root, 0, new StringBuilder());
  }

  // 내부에서 재귀적으로 순환하여 노드에 저장된 값들 추출해주는 프린트
  private void printTrie(Node node, int idx, StringBuilder sb) {
    Node current = node;
    Node[] child = current.child;
    StringBuilder builder = new StringBuilder(sb);

    // 루트 노드에는 저장된 것이 없으므로 그 외의 경우에만 append
    if(!current.equals(this.root)) {
      builder.append(intToChar(idx));
    }

    // 완성 노드라면 프린팅하면 된다.
    if(current.isTerminal) {
      System.out.println(builder);
    }
    // 완성 노드가 아니라면 isTermianl이 false일 것이고, 그렇다는 것은 자식 노드가 존재한다는 의미

    // 완선된 노드들을 순환하기 위해 반복문 사용
    for(int i = 0; i < ALPHABET_SIZE; i++) {
      // null 이라면 거기에는 저장된 것이 없다는 의미이므로 건너 뜀
      if(current.child[i] == null) {
        continue;
      }
      printTrie(child[i], i, builder);    // 재귀적으로 순환
    }
  }

  /* 삭제하기
  * 1) 삭제할 문자가 다른 문자의 접두사인 경우 -> isTerminal을 false 변경
  * - Do는 Door의 접두사가 된다. 따라서, Do를 삭제한다면 D, o에서 o에 isTerminal만 false로 변경한다.
  * - 단순히 삭제하면 Door또한 사라지게 된다.
  * 2) 삭제할 문자가 Unique하여 다른 문자와 연관이 없는 경우 -> 관련 모든 노드 삭제
  * 3) 삭제할 문자의 일부가 전체 삭제 문자의 접두사인 경우 -> 다른 문자에 영향가지 않는 곳까지만 삭제
  * - Door를 삭제하려고 한다면 Do가 있으니, 전체 삭제를 할 수 없고 Door에서 뒤의 o,r만 삭제해야 한다.
  * */

  // 사용자가 호출시 사용하는 메소드
  public void delete(String str) {
    delete(this.root, str, 0);
  }

  // 내부적으로 재귀를 통해 삭제를 진행하는 메소드
  private void delete(Node current, String str, int idx) {
    int leng = str.length();

    // 자식이 없는데 String의 length의 끝까지 오지 않았다면 예외 발생
    // 끝까지 갔는데 해당 노드가 terminal가 아니라면 해당 단어를 저장하지 않은 것이므로 예외 발생
    if(current == null || (current.childNum == 0 && idx != leng) || (idx == leng && !current.isTerminal)) {
      throw new NoSuchElementException("Value " + str + " does not exist in Trie!");
    }

    // 문자열의 마지막에 다다른 경우
    if(idx == leng) {
      current.isTerminal = false;

      // 자식이 없는데 문자의 마지막이었다면 해당 문자만 저장된 것이므로 null 처리
      if(current.childNum == 0) {
        current = null;
      }
    } else {
      char c = str.charAt(idx);
      int num = charToInt(c);

      // 삭제 후 돌아오는 부분
      delete(current.child[num], str, idx + 1);

      // child가 null처리 되었다면 자식 노드의 수가 하나 줄어든 것이므로 -- 처리
      if(current.child[num] == null) current.childNum--;

      // 현재 노드의 자식이 없고, 단어의 마지막도 아니라면 삭제해야 한다.
      if(current.childNum == 0 && !current.isTerminal) {
        current = null;
      }
    }
  }

  // LPM을 찾는 메소드
  public String getLPM(String input) {
    // 반환할 String 값을 지정
    String ret = "";
    int leng = input.length();

    Node current = this.root;

    int matchIdx = 0;
    for(int i = 0; i < leng; i++) {
      char c = input.charAt(i);
      int num = charToInt(c);

      // 자식이 null이 아니면 해당 자식 노드로 이동
      if(current.child[num] != null) {
        ret += c;   // 해당 value를 반환 값에 추가
        current = current.child[num]; // 자식 노드로 이동

        // 해당 자식 노드가 단어의 끝이라면 Trie에 저장된 것임.
        // i + 1을 matchIdx에 저장하여 subString에 활용할 것으로 준비

        // 해당 자식 노드가 단어의 끝이라면 Trie에 저장된 것임.
        // i + 1을 matchIdx에 저장하여 subString에 활용할 것으로 준비
        if(current.isTerminal) {
          matchIdx = i + 1;
        }
      } else {
        break;
      }
    }
    // 반목물을 다 돌았는데 현재 위치가 단어의 마지막이 아니라면
    if (!current.isTerminal) {
      // matchIdx로 subString을 해야한다.
      return ret.substring(0, matchIdx);
    // 반목문을 다돌았는데 마지막이라면 해당 단어가 LPM
    } else {
      return ret;
    }
  }
}