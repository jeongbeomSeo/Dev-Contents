// BFS using Queue
package BFS;

import java.util.LinkedList;
import java.util.Queue;

public class BFS_Queue {
  static int[][] graph = {{}, {2,3,8}, {1,6,8}, {1,5}, {5,7}, {3,4,7}, {2}, {4,5}, {1,2}};

  static boolean[] visitied = new boolean[9];

  public static void main(String[] args) {
    BFS(1);
  }
  static void BFS(int start) {
    Queue<Integer> q = new LinkedList<>();

    q.add(start);
    visitied[start] = true;

    while (!q.isEmpty()) {
      int curNode = q.poll();

      System.out.print(curNode + " -> ");

      for(int adjNode : graph[curNode]) {
        if(!visitied[adjNode]) {
          q.add(adjNode);
          visitied[adjNode] = true;
        }
      }
    }
  }
}