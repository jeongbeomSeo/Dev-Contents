package Bellman_Ford_Algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
* 5 9
* 4
* 1 2 10
* 1 3 5
* 2 3 2
* 3 1 1
* 3 2 13
* 4 1 8
* 4 5 3
* 5 4 9
* 5 2 31
* 출력결과
* 8 18 13 0 3
* */

/*
 * 읍수 사이클있는 그래프
 * 입력
 * 5 9
 * 4
 * 1 2 -10
 * 1 3 5
 * 2 3 2
 * 3 1 1
 * 3 2 13
 * 4 1 8
 * 4 5 3
 * 5 4 9
 * 5 2 31
 * 출력 결과
 * 음수 사이클 존재
 * */
public class Main {
  static class Edge {
    int v;
    int w;
    int cost;

    Edge (int v, int w, int cost) {
      this.v = v; // 나가는 정점
      this.w = w; // 들어오는 정점
      this.cost = cost;
    }
  }
  static ArrayList<Edge> graph;

  // 정점의 개수, 간선의 개수, 출발지
  static boolean BellmanFord(int n, int m, int start) {
    int[] dist = new int[n + 1];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[start] = 0;

    // (|V| - 1개)만큼 반복
    // v와 w 사이의 최단 경로는 s와 v일 뿐일수 있고, w를 제외한 그래프의 모든 노드 (|V| - 1개)가 v, w 사이의 최단경로를 구성할 수도 있습니다.
    // 즉, edge-relaxation을 |V|회 만큼 실행 했을 때, 최단 거리가 수정된다는 것은 음수 사이클을 돈다는 것을 의미한다.
    for (int i = 0; i < n - 1; i++) {
      // 간선의 개수만큼 반복
      for (int j = 0; j < m; j++) {
        Edge edge = graph.get(j);

        // 현재 간선을 들어오는 정점에 대해 비교
        if (dist[edge.v] != Integer.MAX_VALUE && dist[edge.w] > dist[edge.v] + edge.cost) {
          dist[edge.w] = dist[edge.v] + edge.cost;
        }
      }
    }

    // 음수 가중치 확인
    for (int i = 0; i < m; i++) {
      Edge edge = graph.get(i);

      //현재 간선의 들어오는 정점에 대해 비교 -> 더 작은 값 생기면 음수 사이클 존재
      if (dist[edge.v] != Integer.MAX_VALUE && dist[edge.w] > dist[edge.v] + edge.cost) {
        System.out.println("음수 사이클 존재");
        return false;
      }
    }

    //출력
    for (int i = 1; i < dist.length; i++) {
      if (dist[i] == Integer.MAX_VALUE) {
        System.out.print("INF ");
      } else
        System.out.print(dist[i] + " ");
    }
    return true;
  }

  public static void main(String[] args) throws IOException {

    // 그래프 입력받기
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    int n = Integer.parseInt(st.nextToken());
    int m = Integer.parseInt(st.nextToken());

    graph = new ArrayList<>();

    for (int i = 0; i < m; i++) {
      st = new StringTokenizer(br.readLine());
      int v = Integer.parseInt(st.nextToken());
      int w = Integer.parseInt(st.nextToken());
      int cost = Integer.parseInt(st.nextToken());

      graph.add(new Edge(v, w, cost));
    }

    BellmanFord(n, m, 4);
  }
}
