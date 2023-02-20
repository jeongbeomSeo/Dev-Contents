// DFS using Stack

package DFS;

import java.util.Stack;

public class DFS_Stack {
  static int[][] graph = {{}, {2,3,8}, {1,6,8}, {1,5}, {5,7}, {3,4,7}, {2}, {4,5}, {1,2}};

  static boolean[] visited = new boolean[9];

  public static void main(String[] args) {
    DFS(1);
  }

  static void DFS(int start) {
    Stack<Integer> stack = new Stack<>();

    stack.add(start);
    System.out.print(start + " -> ");
    visited[start] = true;

    while(!stack.isEmpty()) {
      int curNode = stack.peek();

      boolean hasNearNode = false;
      for(int nearNode : graph[curNode]) {
        if(!visited[nearNode]) {
          visited[nearNode] = true;
          System.out.print(nearNode + " -> ");
          hasNearNode = true;
          stack.add(nearNode);
          break;
        }
      }
      if(!hasNearNode) stack.pop();
    }
  }
}

