// 이진 트리 구현
// 크기 상관 쓰지 않는 이진 트리

import java.util.LinkedList;
import java.util.Queue;

class Node {
  int value;
  Node left;
  Node right;

  Node (int value) {
    this.value = value;
    left = null;
    right = null;
  }
}

class BinaryTree {
  Node root;
  int size;

  BinaryTree() {
    root = null;
    size = 0;
  }

  void push(Node node) {
    if(size == 0) {
      this.root = node;
    } else {
      Queue<Node> q = new LinkedList<>();
      q.add(this.root);

      while (!q.isEmpty()) {
        Node curNode = q.poll();

        if(curNode.left == null) {
          curNode.left = node;
          break;
        }
        else {
          q.add(curNode.left);
        }

        if(curNode.right == null) {
          curNode.right = node;
          break;
        }
        else {
          q.add(curNode.right);
        }
      }
    }
    size++;
  }

  public int pop (Node node) {
    if (contain(node)) {
      Node lastNode = removeLastNode();

      if (root != null) {
        if (isSame(root, node)) {
          root.value = lastNode.value;
        }
        else {
          Queue<Node> q = new LinkedList<>();
          q.add(root);

          while (!q.isEmpty()) {
            Node curNode = q.poll();

            if(curNode.left != null) {
              if(isSame(curNode.left, node)) {
                curNode.left.value = lastNode.value;
                break;
              } else {
                q.add(curNode.left);
              }
            }

            if (curNode.right != null) {
              if (isSame(curNode.right, node)) {
                curNode.right.value = lastNode.value;;
                break;
              }
            }
          }
        }
      }
      size--;
      return 1;
    }
    return 0;
  }

  boolean contain(Node node) {
    boolean check = false;

    if (size != 0) {
      Queue<Node> q = new LinkedList<>();
      q.add(this.root);

      while (!q.isEmpty()) {
        Node curNode = q.poll();

        if (isSame(curNode, node)) {
          check = true;
          break;
        }

        if (curNode.left != null) {
          q.add(curNode.left);
        }
        if (curNode.right != null) {
          q.add(curNode.right);
        }
      }
    }
    return check;
  }

  // 해당 함수의 경우 마지막 리프 노드를 찾는 과정과 삭제 과정이 따로 동작한다. 그 이유는 리프 노드의 주소 값을 last에 넣어준다고 해도 트리에서 리프 노드를 삭제하기 위해선 리프 노드의 부모 노드가 필요 하기 때문이다.
  // 그래서 해당 부분을 이해 했으면 이 함수를 좀 더 최적화 하는 것이 가능해진다.
  // 그 방법은 Node 클래스에 멤버 변수로 Node parent를 생성해주고 이용하면 된다.
  private Node removeLastNode() {
    Node last = this.root;

    if(size == 1) {
      root = null;
    }
    else {
      Queue<Node> q = new LinkedList<>();
      q.add(last);

      while (!q.isEmpty()) {
        Node curNode = q.poll();
        last = curNode;

        if(curNode.left != null) {
          q.add(curNode.left);
        }

        if (curNode.right != null) {
          q.add(curNode.right);
        }
      }

      q.add(root);

      while (!q.isEmpty()) {
        Node curNode = q.poll();

        if (curNode.left != null) {
          if(isSame(curNode.left, last)) {
            curNode.left = null;
          } else {
            q.add(curNode.left);
          }
        }

        if (curNode.right != null) {
          if (isSame(curNode.right, last)) {
            curNode.right = null;
            break;
          } else {
            q.add(curNode.right);
          }
        }
      }
    }
    return last;
  }

  private boolean isSame(Node a, Node b) {
    if(a.value == b.value && a.left == b.left && a.right == b.right) {
      return true;
    }
    return false;
  }

  void printBFS(Node node) {

    Queue<Node> q = new LinkedList<>();
    q.add(node);

    while (!q.isEmpty()) {
      Node curNode = q.poll();
      System.out.println(curNode.value);

      if (curNode.left != null) {
        q.add(curNode.left);
      }

      if (curNode.right != null) {
        q.add(curNode.right);
      }
    }
  }

  void printDFS(Node curNode) {

    if (curNode.left == null && curNode.right == null) {
      System.out.println(curNode.value);
    } else {
      if (curNode.left != null) {
        printDFS(curNode.left);
      }
      System.out.println(curNode.value);
      if (curNode.right != null) {
        printDFS(curNode.right);
      }
    }
  }
}

public class Main {
}
/**
 * 참고한 사이트
 * [[Data Structure] Java 이진트리(Binary Tree) 구현](https://math-coding.tistory.com/164)
 */