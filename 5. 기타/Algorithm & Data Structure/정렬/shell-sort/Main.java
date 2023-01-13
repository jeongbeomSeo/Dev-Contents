public class Main {
  static void shellSort(int[] a, int n) {
    int h;
    // 최대한 그룹 섞기 위한 증분값(h값) 결정
    for( h = 1; h < n / 9; h = h * 3 + 1)
      ;

    for( ; h > 0; h /= 3)
      for(int i = h; i < n; i++) {
        int j;
        int tmp = a[i];
        for(j = i - h; j >= 0 && a[j] > tmp; j -= h)
          a[j + h] = a[j];
        // for문의 경우 조건식에 맞았을 경우 for문 안의 코드를 실행 후 증감식을 실행 후 다시 조건문을 확인한다.
        // 따라서, 아래와 같이 코드를 해줘야 바뀌는 것임.
        a[j] = tmp;
      }

    /*
    for( ; h >= 1; h /= 3) {
      for(int i = h; i < n; i++) {
        int j;
        int temp = a[i];
        for(j = i; j - h >= 0 && a[j - h] > temp; j -= h)
          a[j] = a[j - h];
        a[j + h] = temp;
      }
    }
    */
  }
}