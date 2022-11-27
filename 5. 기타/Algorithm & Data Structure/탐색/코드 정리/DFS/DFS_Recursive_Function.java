package DFS;

public class DFS_Recursive_Function {
  static int[][] graph = {{}, {2,3,8}, {1,6,8}, {1,5}, {5,7}, {3,4,7}, {2}, {4,5}, {1,2}};
  static boolean[] visited = new boolean[9];

  public static void main(String[] args) {

    DFS(1);
  }
  static void DFS(int node) {

    visited[node] = true;
    System.out.print(node + " -> ");

    for(int childNode : graph[node]) {
      if(!visited[childNode]) {
        DFS(childNode);
      }
    }
  }
}
