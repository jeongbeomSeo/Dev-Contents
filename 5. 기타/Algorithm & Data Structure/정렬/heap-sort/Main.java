import java.util.Scanner;

public class Main {
  // 힙 정렬

  static void swap(int[] a, int idx1, int idx2) {
    int tmp = a[idx1]; a[idx1] = a[idx2]; a[idx2] = tmp;
  }

  // a[left] ~ a[right]를 힙으로 만듭니다.
  static void downHeap(int[] a, int left, int right) {
    int temp = a[left];   // 루트
    int child;            // 큰 값을 가진 노드
    int parent;           // 부모

    // parnet < (right + 1) / 2: 해당 조건이 의미하는 것은 자식 노드가 존재 하는지를 확인
    // (right + 1)???
    for(parent = left; parent < (right + 1) / 2; parent = child) {
      int cl = parent * 2 + 1;                          // 왼쪽 자식
      int cr = cl + 1;                                  // 오른쪽 자식
      child = (cr <= right && a[cr] > a[cl]) ? cr : cl; // 큰 값을 가진 노드를 자식에 대입
      if (temp >= a[child])
        break;
      a[parent] = a[child];
    }
    a[parent] = temp;
  }

  // 힙 정렬

  // 자식 노드가 있는 노드부터 시작해서 힙 정렬을 Bottom-up 방식으로 실행
  // 자식-자식-부모 정렬 -> 자식-자식-부모 정렬 -> -> 자식-자식-Root 노드 정렬
  // 진행하면서 부모 노드였던 노드가 자식 노드로써 위에서 정렬 된다.
  static void heapSort(int[] a, int n) {
    for (int i = (n - 1) / 2; i >= 0; i--)    // a[i] ~ a[n-1]를 힙으로 만들기
      downHeap(a, i, n-1);

    for (int i = n - 1; i > 0; i--) {
      swap(a, 0, i);
      downHeap(a, 0, i-1);
    }
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    System.out.println("힙 정렬");
    System.out.print("요솟수: ");
    int nx = sc.nextInt();
    int[] x = new int[nx];

    for (int i = 0; i < nx; i++) {
      System.out.print("x[" + i + "]: ");
      x[i] = sc.nextInt();
    }

    heapSort(x, nx);

    System.out.println("오름차순으로 정렬했습니다.");
    for(int i = 0; i < nx; i++) {
      System.out.println("x[" + i + "]= " + x[i]);
    }
  }
}