package Floyd_Warshall_Algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
Sample Input
5 8
1 2 5
1 5 1
1 3 7
1 4 2
2 3 3
2 4 6
3 4 10
4 5 4
*/

// 플로이드 와샬의 경우 Graph List가 필요하지 않다.
// 또한, 모든 Node에서의 최단 거리를 구하는 것이기 때문에 start 변수도 필요하지 않다.
public class Main {
  static int N, M;
  static int[][] dist;
  public static void main(String[] args) throws IOException {

    // 초기화
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    N = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());

    // 플로이드 초기 거리 테이블 초기화
    dist = new int[N + 1][N + 1];

    for(int i = 0; i < N + 1; i++) {
      for(int j = 0; j < N + 1; j++) {
        if(i == j) {
          // 자기 자신으로 가는 길은 최소 비용이 0이다.
          dist[i][j] = 0;
          continue;
        }
        // 자기 자신으로 가는 경우르 ㄹ제외하고는 매우 큰 값(Int값 중에 가장 큰 값으로 설정)
        dist[i][j] = Integer.MAX_VALUE;
      }
    }

    for(int i = 0; i < M; i++) {
      st = new StringTokenizer(br.readLine());
      int n1 = Integer.parseInt(st.nextToken());
      int n2 = Integer.parseInt(st.nextToken());
      int cost = Integer.parseInt(st.nextToken());

      // 가는 경로가 하나가 아닐 수 있다. 따라서 그 중 최소 비용을 저장해두면 된다.
      dist[n1][n2] = Math.min(dist[n1][n2], cost);
      dist[n2][n1] = Math.min(dist[n2][n1], cost);
    }

    // Floyd Warshall Algorithm
    // 노드를 1개부터 N개까지 거쳐가는 경우를 모두 고려한다.
    for(int k = 1; k < N + 1; k++) {
      // 노드 i에서 j로 가는 경우.
      for(int i = 1; i < N + 1; i++) {
        for (int j = 1; j < N + 1; j++) {
          // k번쨰 노드를 거쳐가는 비용이 기존 비용보다 더 작은 경우 갱신
          // 또는 연결이 안되어 있던 경우 (INF) 연결 비용 갱신.
          dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
        }
      }
    }

    // 출력
    for(int i = 1; i < N + 1; i++) {
      for(int j = 1; j < N + 1; j++) {
        if(dist[i][j] == Integer.MAX_VALUE) {
          System.out.print("INF ");
        } else {
          System.out.print(dist[i][j] + " ");
        }
      }
      System.out.println();
    }

  }
}