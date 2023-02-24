public class Main {

  static void swap(int[] a, int idx1, int idx2) {
    int temp = a[idx1]; a[idx1] = a[idx2]; a[idx2] = a[idx1];
  }
  static void selectionSort(int[] a, int n) {
    for(int i = 0; i < n - 1; i++) {
      int min = i;          // 아직 정렬되지 않은 부분에서 가장 작은 요소의 인덱스를 기록한다.
      for(int j = i + 1; j < n; j++) {
        if (a[j] < a[min])
          min = j;
      }
      // 미정렬 요소 중 가장 작은 요소 i번째 인덱스 요소와 위치 바꿔주기
      swap(a, i, min);
    }
  }
}