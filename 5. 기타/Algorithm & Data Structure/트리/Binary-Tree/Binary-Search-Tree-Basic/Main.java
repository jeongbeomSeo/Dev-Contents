import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Node {
  private Node left;
  private Node right;
  private int value;

  public Node (int value) {
    this.value = value;
    this.left = null;
    this.right = null;
  }

  public Node getLeft() {
    return left;
  }

  public void setLeft(Node left) {
    this.left = left;
  }

  public Node getRight() {
    return right;
  }

  public void setRight(Node right) {
    this.right = right;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }
}

/*
조건 3가지
1. 어떤 노드 N을 기준으로 왼쪽 서브 트리 노드의 모든 키 값은 노드 N의 키 값보다 작아야 한다.
2. 오른쪽 서브 트리 노드의 키 값은 노드 N의 키 값보다 커야 한다.
3. 같은 키 값을 가지는 노드는 존재하지 않는다.
*/
class BinaryTree {
  Node root;

  private boolean find(int value) {
    Node curNode = root;

    while (curNode != null) {
      if (curNode.getValue() == value) {
        return true;
      } else if (curNode.getValue() > value) {
        curNode = curNode.getLeft();
      } else {
        curNode = curNode.getRight();
      }
    }
    return false;
  }

  public boolean insert(int value) {
    Node newNode = new Node(value); // 값을 받아서 새로운 newNode 생성

    if(find(value)) { // find Method를 호출하여 같은 value를 가진 Node가 존재하는지 확인
      return false;
    }

    if(root == null) {
      root = newNode;
      return true;
    }

    Node curNode = root;
    Node parent;
    while (true) {
      parent = curNode;

      if (value < curNode.getValue()) {
        curNode = curNode.getLeft();
        if(curNode == null) {
          parent.setLeft(newNode);
          return true;
        }
      } else {
        curNode = curNode.getRight();
        if(curNode == null) {
          parent.setRight(newNode);
          return true;
        }
      }
    }
  }

  // 전위 탐색: Root -> Left -> Right
  // 전위 탐색 => 깊이 우선 탐색(DFS)가 된다.(Stack 활용)
  private List<Integer> preOrderTraverse(Node focusNode, List<Integer> integers) {
    if(focusNode != null) {
      integers.add(focusNode.getValue());
      preOrderTraverse(focusNode.getLeft(), integers);
      preOrderTraverse(focusNode.getRight(), integers);
    }
    return integers;
  }

  // 중위 탐색: Left -> Root -> Right
  // 중위 탐색 => 오름차순 정렬
  private List<Integer> inOrderTraverse(Node focusNode, List<Integer> integers) {
    if(focusNode != null) {
      inOrderTraverse(focusNode.getLeft(), integers);
      integers.add(focusNode.getValue());
      inOrderTraverse(focusNode.getRight(), integers);
    }
    return integers;
  }

  // 후위 탐색: Left -> Right -> Root
  private List<Integer> postOrderTraverse(Node focusNode, List<Integer> integers) {
    if(focusNode != null) {
      postOrderTraverse(focusNode.getLeft(), integers);
      postOrderTraverse(focusNode.getRight(), integers);
      integers.add(focusNode.getValue());
    }
    return integers;
  }

  // 너비 우선 탐색(BFS) -> Queue를 이용
  public List<Integer> BFS(Node focusNode) {
    if(focusNode == null) {
      return null;
    }
    List<Integer> result = new ArrayList<>();
    Queue<Node> queue = new LinkedList<>();

    queue.add(focusNode);

    while (!queue.isEmpty()) {
      int size = queue.size();
      for(int i = 0 ; i < size; i++) {
        Node curNode = queue.poll();

        if(curNode.getLeft() != null) {
          queue.add(curNode.getLeft());
        }
        if(curNode.getRight() != null) {
          queue.add(curNode.getRight());
        }
        result.add(curNode.getValue());
      }
    }
    return result;
  }
}

public class Main {
}

/*
 * 참고한 사이트 
 *  - [자바 [JAVA] - Binary Search Tree (이진 탐색 트리) 구현하기](https://k3068.tistory.com/30)
 */