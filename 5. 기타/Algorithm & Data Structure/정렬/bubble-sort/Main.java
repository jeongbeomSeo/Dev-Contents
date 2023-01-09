import java.util.Scanner;

public class Main {
  static void swap(int[] a, int idx1, int idx2) {
    int temp = a[idx1]; a[idx1] = a[idx2]; a[idx2] = a[idx1];
  }

  static void bubbleSort(int[] a, int n) {
    // 변동 사항 없을 시 정렬 완료
    int exhag = 0;
    for(int i = 0; i < n - 1; i++) {
      for(int j = n - 1; j > i; j--) {
        if(a[j] < a[j - 1]){
          swap(a, j - 1, j);
          exhag++;
        }
      }
      if(exhag == 0) break;
    }
  }

  static void bubbleSortUpgrade(int[] a, int n) {
    int k = 0;
    while (k < n - 1) {
      int last = n - 1;
      for(int j = n - 1; j > k; j--)
        if(a[j] < a[j - 1]){
          swap(a, j - 1, j);
          last = j;
        }
      // 반복문이 끝났을 때 최종적으로 지점까지만 수행
      // 바뀌지 않은 앞의 Index는 이미 오름차순
      k = last;
    }
  }

}