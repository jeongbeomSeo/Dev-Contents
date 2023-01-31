import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class Trie_Using_Map {
  static class Node {
    // 자식 노드
    Map<Character, Node> childNode = new HashMap<>();
    // 단어의 끝인지 아닌지 체크
    boolean isTerminal;
  }

    static class Trie {
      // Trie 자료 구조를 생성할 때 rootNode는 기본적으로 생성
      Node rootNode = new Node();

      // Trie에 문자열 저장
      void insert(String str) {
        // Trie 자료 구조는 항상 rootNode부터 시작
        Node node = this.rootNode;

        // 문자열의 각 단어마다 가져와서 자식 노드 중에 있는지 체크
        // 없으면 자식 노드 새로 생성
        for(int i = 0; i < str.length(); i++) {
          node = node.childNode.computeIfAbsent(str.charAt(i), key -> new Node());
        }

        // 저장 할 문자열의 마지막 단어에 매핑되는 노드에 단어의 끝임을 명시
        node.isTerminal = true;
      }

      // Trie에서 문자열 검색
      boolean search(String str) {
        // Trie 자료 구조는 항상 rootNode 부터 시작
        Node node = this.rootNode;

        // 문자열의 각 단어마다 노드가 존재하는지 체크
        for(int i = 0; i < str.length(); i++) {
          // 문자열의 각 단어에 매핑된 노드가 존재하면 가져오고 아니면 null
          node = node.childNode.getOrDefault(str.charAt(i), null);
          if(node == null) {
            // node가 null이면 현재 Trie에 해당 문자열은 없음
            return false;
          }
        }
        // 문자열의 마지막 단어까지 매핑된 노듣가 존재한다고해서 무조건 문자열이 존재하는게 아님
        // busy를 Trie에 저장했으면, bus의 마지막 s단어에 매핑 된 노드는 존재하지만 Trie에 저장된 건 아니다
        // 그러므로 현재 노드가 단어의 끝인지 아닌지 체크하는 변수로 리턴
        return node.isTerminal;
      }

      void delete(String str, Node current, int idx) {
        int leng = str.length();
    
        if((current.childNode.isEmpty() && idx != leng) || (idx == leng && !current.isTerminal)) {
          throw new NoSuchElementException("Value" + str + " does not exists!");
        }
    
        if(idx == leng) {
          current.isTerminal = false;
    
          if(current.childNode.isEmpty())
            current = null;
        } else {
          char c = str.charAt(idx);
    
          delete(str, current.childNode.get(c), idx + 1);
    
          if(current.childNode.isEmpty() && !current.isTerminal)
            current = null;
        }
      }
    }
  public static void main(String[] args) {
    // Trie 자료 구조 생성
    Trie trie = new Trie();

    // Trie에 문자열 저장
    trie.insert("kakao");
    trie.insert("busy");
    trie.insert("card");
    trie.insert("cap");

    // Trie에 저장 된 문자열 확인
    System.out.println(trie.search("bus"));		  // false
    System.out.println(trie.search("busy"));    // true
    System.out.println(trie.search("kakao"));   // true
    System.out.println(trie.search("cap"));     // true

  }
}

// 참고한 사이트
// [Java] Map Interface의 유용한 메서드를 알아보자!(https://codingnojam.tistory.com/39)
// [Algorithm] Trie를 Java로 구현해보자!(https://codingnojam.tistory.com/40)