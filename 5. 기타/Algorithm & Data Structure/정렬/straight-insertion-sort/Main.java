public class Main {

  static void insertionSort(int[] a, int n) {
    for(int i = 1; i < n; i++) {
      int j;
      int tmp = a[i];
      // i가 1씩 증가하면서 요소 값을 하나 선택하고 앞에 정렬 되어 있는 부분과 비교 하면서 위치 찾아주는 정렬 방식
      for(j = i; j > 0 && a[j - 1] > tmp; j--)
        a[j] = a[j - 1];
      // for문의 경우 조건식에 맞았을 경우 for문 안의 코드를 실행 후 증감식을 실행 후 다시 조건문을 확인한다.
      // 따라서, 아래와 같이 코드를 해줘야 바뀌는 것임.
      a[j] = tmp;
    }
  }
}