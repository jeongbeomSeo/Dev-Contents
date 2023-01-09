import java.util.Scanner;

public class Main {
  /*
  // 정렬을 마친 배열의 병합
  static void alreadySortMerge(int[] a, int na, int[] b, int nb, int[] c) {
    // 정렬을 마친 배열 a, b를 병합하여 배열 c에 저장한다.
    int pa = 0;
    int pb = 0;
    int pc = 0;

    while (pa < na && pb < nb)    // 작은 값을 저장한다.
      c[pc++] = (a[pa] <= b[pb]) ? a[pa++] : b[pb++];

    while (pa < na)       // a에 남아 있는 요소를 복사한다.
      c[pc++] = a[pa++];

    while (pb < nb)       // b에 남아 있는 요소를 복사한다.
      c[pc++] = b[pb++];
  }
  */

  // 병합 정렬
  static int[] buff;    // 작업용 배열

  // a[left] ~ a[right]를 재귀적으로 병합 정렬
  static void __mergeSort(int[] a, int left, int right) {
    if(left < right) {
      int i;
      int center = (left + right) / 2;
      int p = 0;
      int j = 0;
      int k = left;

      __mergeSort(a, left, center);               // 배열의 앞부분을 병합 정렬합니다.
      __mergeSort(a, center + 1, right);     // 배열의 뒷부분을 병합 정렬합니다.

      /* 부연 설명
      재귀 함수 부분에서 left에서 center까지 정렬하고 그 이후 뒷 부분 매열을 정렬할 때 center + 1 부터 right까지 정렬하므로
      밑에 두 배열을 병합하는 부분에서 i <= right에서 등호를 넣어주는 것이다.
      Cf) 밑에 mergeSort()에서 __mergeSort(a, 0, n-1)로 실행했기 때문에 마지막 Index로 right가 설정되어 있다.
       */

      // 앞부분을 작업용 배열에 넣어주기
      for(i = left; i <= center; i++)
        buff[p++] = a[i];

      // 앞부분과 뒷부분 배열 비교하면서 배열 a에 저장
      while (i <= right && j < p)
        a[k++] = (buff[j] <= a[i]) ? buff[j++] : a[i++];

      // 앞부분에 남아 있는 요소 배열 a에 복사
      while (j < p)
        a[k++] = buff[j++];
    }
  }

  // 병합 정렬
  static void mergeSort(int[] a, int n) {
    buff = new int[n];                      // 작업용 배열을 생성합니다.

    __mergeSort(a, 0, n - 1);    // 배열 전체를 병합 정렬 합니다.

    buff = null;                           // 작업용 배열을 해체합니다.
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    System.out.println("병합 정렬");
    System.out.print("요솟수: ");
    int nx = sc.nextInt();
    int[] x = new int[nx];

    for(int i = 0; i < nx; i++) {
      System.out.print("x[" +  i + "]: ");
      x[i] = sc.nextInt();
    }

    mergeSort(x, nx);

    System.out.println("오름차순으로 정렬했습니다.");
    for(int i = 0; i < nx; i++)
      System.out.println("x[" + i + "]= " + x[i]);
  }
}