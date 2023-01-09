import java.util.Scanner;

public class Main {
  static void swap(int[] a, int idx1, int idx2) {
    int temp = a[idx1]; a[idx1] = a[idx2]; a[idx2] = temp;
  }

  /*
  !피벗 결정하는 과정에서 중간 값을 이용하려고 할 때 활용 가능
  static int mid(int a, int b, int c) {
    if ( a >= b ) {
      if( b >= c)
        return b;
      else if ( a <= c )
        return a;
      else
        return c;
    }
    else if( a > c)
      return a;
    else if( b > c)
      return c;
    else
      return b;
  }
  */

  static void quickSort(int[] a, int left, int right) {
    int pl = left;                  // 왼쪽 커서
    int pr = right;                 // 오른쪽 커서
    int x = (left + right) / 2;     // 피벗

    do {
      while (a[pl] < x) pl++;
      while (a[pr] > x) pr--;
      if (pl <= pr)
        swap(a, pl++, pr--);
    } while (pl <= pr);

    if (left < pr) quickSort(a, left, pl);
    if (pl < right) quickSort(a, pr, right);
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    System.out.println("퀵 정렬");
    System.out.print("요솟수: ");
    int nx = sc.nextInt();
    int[] x = new int[nx];

    for (int i = 0; i < nx; i++) {
      System.out.print("x[" + i + "]: ");
      x[i] = sc.nextInt();
    }

    quickSort(x, 0, nx - 1);

    System.out.println("오름차순으로 정렬했습니다.");

    for (int i = 0; i < nx; i++)
      System.out.println("x[" + i + "]= " + x[i]);
  }
}