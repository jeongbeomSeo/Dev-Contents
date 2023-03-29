// Using DP 

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    StringTokenizer st;

    int N = Integer.parseInt(br.readLine());

    ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < N + 1; i++) {
      graph.add(new ArrayList<>());
    }

    for (int i = 0; i < N - 1; i++) {
      st = new StringTokenizer(br.readLine());
      int n1 = Integer.parseInt(st.nextToken());
      int n2 = Integer.parseInt(st.nextToken());

      graph.get(n1).add(n2);
      graph.get(n2).add(n1);
    }

    boolean[] isVisited = new boolean[N + 1];
    int[] depth = new int[N + 1];
    int k = (int)Math.ceil(Math.log(N) / Math.log(2)) + 1;
    int[][] parent = new int[N + 1][k];

    DFS(graph, isVisited, depth, parent, 0, 1);

    set_spaTable(parent, N, k);

    int M = Integer.parseInt(br.readLine());

    for (int i = 0; i < M; i++) {
      st = new StringTokenizer(br.readLine());
      int n1 = Integer.parseInt(st.nextToken());
      int n2 = Integer.parseInt(st.nextToken());

      bw.write(LCA(n1, n2, parent, depth, k) + "\n");
    }

    bw.flush();
    bw.close();
  }

  static int LCA(int n1, int n2, int[][] parent, int[] depth, int k) {

    if (depth[n1] > depth[n2]) {
      int temp = n1;
      n1 = n2;
      n2 = temp;
    }

    for (int i = k - 1; i >= 0; i--) {
      if (depth[n2] - depth[n1] >= Math.pow(2,i)) {
        n2 = parent[n2][i];
      }
    }

    if (n1 == n2) return n1;

    for (int i = k - 1; i>= 0; i--) {
      if (parent[n1][i] != parent[n2][i]) {
        n1 = parent[n1][i];
        n2 = parent[n2][i];
      }
    }

    return parent[n1][0];
  }
  static void DFS(ArrayList<ArrayList<Integer>> graph, boolean[] isVisited, int[] depth, int[][] parent, int curDepth, int node) {
    isVisited[node] = true;
    depth[node] = curDepth;

    for (int childNode : graph.get(node)) {
      if (!isVisited[childNode]) {
        parent[childNode][0] = node;
        DFS(graph, isVisited, depth, parent, curDepth + 1, childNode);
      }
    }
  }
  static void set_spaTable(int[][] parent, int N, int k) {
    for (int i = 1; i < k; i++) {
      for (int node = 1; node <= N; node++) {
        parent[node][i] = parent[parent[node][i - 1]][i - 1];
      }
    }
  }
}

// Using Segment Tree
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
  static int INF = Integer.MAX_VALUE;
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    StringTokenizer st;

    int N = Integer.parseInt(br.readLine());

    ArrayList<ArrayList<Integer>> graph = new ArrayList<>();

    for(int i = 0; i < N + 1; i++) graph.add(new ArrayList<>());

    for (int i = 1; i < N; i++) {
      st = new StringTokenizer(br.readLine());

      int n1 = Integer.parseInt(st.nextToken());
      int n2 = Integer.parseInt(st.nextToken());

      graph.get(n1).add(n2);
      graph.get(n2).add(n1);
    }

    boolean[] isVisited = new boolean[N + 1];
    int[] level = new int[N + 1];

    ArrayList<Integer[]> leafNode = new ArrayList<>();

    // DFS + Segment Tree 전처리
    DFS(graph, isVisited, level, 1, 1, leafNode);


    // Tree Size 결정
    int size = leafNode.size();
    int h = (int)Math.ceil(Math.log(size) / Math.log(2));
    int tree_size = 1 << (h + 1);
    // Initialize Segment Tree
    Integer[][] tree = new Integer[tree_size][2];

    // 순서를 0 ~ size - 1까지
    initST(tree, 1, 0, size - 1, leafNode);

    // 입력 받은 Node 번호 -> 해당 Node 최초 방문 순서로 바꿔주는 Table 생성
    int[] visitedOrder = new int[N + 1];
    for (int i = 0 ; i < size; i++) {
      int nodeIdx = leafNode.get(i)[1];
      if (visitedOrder[nodeIdx] == 0) {
        visitedOrder[nodeIdx] = i;
      }
    }

    // Find LCA
    int M = Integer.parseInt(br.readLine());

    for (int i = 0 ; i < M; i++) {
      st = new StringTokenizer(br.readLine());

      int n1 = Integer.parseInt(st.nextToken());
      int n2 = Integer.parseInt(st.nextToken());

      if (n1 == n2)  {
        bw.write(n1+"\n");
        continue;
      }

      int orderN1 = visitedOrder[n1];
      int orderN2 = visitedOrder[n2];

      if (orderN1 > orderN2) {
        int temp = orderN1;
        orderN1 = orderN2;
        orderN2 = temp;
      }
      Integer[] lcaNode = query(tree, 1, 0 , size - 1, orderN1, orderN2);
      bw.write(lcaNode[1]+"\n");
    }
    bw.flush();
    bw.close();
  }

  static void DFS(ArrayList<ArrayList<Integer>> graph, boolean[] isVisited, int[] level, int node, int curLevel, ArrayList<Integer[]> leafNode) {
    Integer[] leaf = {curLevel, node};
    leafNode.add(leaf);

    isVisited[node] = true;
    level[node] = curLevel;

    for (int childNode : graph.get(node)) {
      if (!isVisited[childNode]) {
        DFS(graph, isVisited, level, childNode, curLevel + 1, leafNode);
        leafNode.add(leaf);
      }
    }
  }

  static void initST(Integer[][] tree, int node, int start, int end, ArrayList<Integer[]> leafNode) {
    if (start == end) {
      tree[node] = leafNode.get(start);
    }
    else {
      int mid = (start + end) / 2;
      initST(tree, node * 2, start, mid, leafNode);
      initST(tree, node * 2 + 1, mid + 1, end, leafNode);
      // 두 노드의 Level 비교
      if (tree[node * 2][0] <= tree[node * 2 + 1][0]) {
        tree[node] = tree[node * 2];
      } else {
        tree[node] = tree[node * 2 + 1];
      }
    }
  }

  static Integer[] query(Integer[][] tree, int node, int start, int end, int nodeL, int nodeR) {
    if (nodeL <= start && end <= nodeR) {
      return tree[node];
    }
    if (end < nodeL || nodeR < start) {
      return new Integer[]{INF, INF};
    }
    int mid = (start + end) / 2;
    Integer[] lNode = query(tree, node * 2, start, mid, nodeL, nodeR);
    Integer[] rNode = query(tree, node * 2 + 1, mid + 1, end, nodeL, nodeR);
    if (lNode[0] > rNode[0]) {
      return rNode;
    }
    else {
      return lNode;
    }
  }
}
