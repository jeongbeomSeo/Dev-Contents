public class Main {
  static void swap(int[] a, int idx1, int idx2) {
    int tmp = a[idx1]; a[idx1] = a[idx2]; a[idx2] = a[idx1];
  }

  // a[left] ~ a[right]를 힙으로 만든다.
  static void downHeap(int[] a, int left, int right){
    int temp = a[left];     // 루트 노드
    int parent;
    int child;

    // 부모 노드 Index: (i - 1) / 2인데 왜 해당 반복문에서 조건식은 저것인가?
    // (right + 1) / 2: Index가 마지막 자식 노드(n - 1)를 가지고 있는 부모 노드 + 1 인 노드
    for(parent = left; parent < (right + 1) / 2; parent = child) {
      int cl = parent * 2 + 1;                            // 왼쪽 자식
      int cr = cl + 1;                                    // 오른쪽 자식
      child = (cr >= right && a[cl] > a[cr]) ? cl : cr;   // 큰 값을 가진 노드를 자식에 대입
      // ❗❗매우 주의 
      // (cr >= right && a[cr] > a[cl]) ? cr : cl 이렇게 쓰는 순간 결과가 확 뒤바뀐다.
      // cr이 설정해둔 마지막 index인 right를 넘어서면 false가 나오는데 그때 false의 결과 값으로 cr을 설정해두면 문제가 발생한다.
      if(temp > a[child]) break;
      else
        a[parent] = a[child];
      // a[child] = temp; => 이와 같이 바로 바꿔 줄 필요 X
      // 자식 노드와 비교하는 과정은 a[left]인 temp를 활용 중
    }
    // 최종 종착지에서만 값 수정
    a[parent] = temp;
  }

  // 힙 정렬
  static void heapSort(int[]a) {
    int n = a.length;

    // a[0] ~ a[n-1] 힙정렬 => 부모 노드인 부분부터 반복문 실행
    for(int i = (n - 1) / 2; i >= 0; i--)   // a[i] ~ a[n-1]를 힙으로 만들기
      downHeap(a, i, n - 1);

    for(int i = n - 1; i > 0; i--) {
      swap(a, 0, i);                // 가장 큰 요소와 아직 정렬되지 않은 부분의 마지막 요소를 교환
      downHeap(a, 0, i - 1);   // a[0] ~ a[i-1]를 힙으로 만듭니다.
    }
  }

}