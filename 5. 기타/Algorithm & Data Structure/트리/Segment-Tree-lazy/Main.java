public class Main {
  static int DEFAULT_VALUE = 0;
  static int MAX_VALUE = Integer.MAX_VALUE;  // for min
  static int MIN_VALUE = Integer.MIN_VALUE; // for max

  int merge(int left, int right) {
    return left + right;  // sum
    // return min(left, right);  // min
    // return max(left, right);  // max
  }

  int mergeBlock(int value, int size) {
    return value * size;  // sum
    // return value;  // min
    // return value;  // max
  }

  int N;  // size
  int[] tree; //  Segment Tree
  int[] lazyValue;  //  Lazy Value
  boolean[] lazyExist;


  void init(int arr[], int size) {
    N = size;
    int h = (int)Math.ceil(Math.log(N) / Math.log(2));
    int tree_size = 1 << h;
    tree = new int[tree_size];
    lazyValue = new int[tree_size];
    lazyExist = new boolean[tree_size];

    // 주워진 배열의 범위: 0 ~ N - 1
    initRec(arr, 1, 0, N - 1);
  }

  // inclusive
  int update(int left, int right, int newValue) {
    return updateRec(left, right, newValue, 1,  0, N - 1);
  }

  // inclusive
  int query(int left, int right) {
    return queryRec(left, right, 1, 0, N - 1);
  }


  private int pushDown(int newValue, int node, int nodeLeftRange, int nodeRightRange) {
    if (nodeLeftRange == nodeRightRange)
      return tree[node] = newValue;

    lazyExist[node] = true;
    lazyValue[node] = newValue;
    return tree[node] = mergeBlock(newValue, nodeLeftRange - nodeRightRange + 1);
  }

  private int initRec(int arr[], int node, int nodeLeft, int nodeRight) {
    if (nodeLeft == nodeRight)
      return tree[node] = arr[nodeLeft];

    int mid = (nodeLeft + nodeRight) / 2;

    int leftVal = initRec(arr, node * 2, nodeLeft, mid);
    int rightVal = initRec(arr, node * 2 + 1, mid + 1, nodeRight);
    return tree[node] = merge(leftVal, rightVal);
  }

  private int updateRec(int left, int right, int newValue, int node, int nodeLeft, int nodeRight) {
    if (right < nodeLeft || nodeRight < left)
      return tree[node];

    if (nodeLeft == nodeRight)
      return tree[node] = newValue;

    if (left <= nodeLeft && nodeRight <= right) {
      lazyExist[node] = true;
      lazyValue[node] = newValue;
      return tree[node] = mergeBlock(newValue, nodeRight - nodeLeft + 1);
    }

    int mid = (nodeLeft + nodeRight) / 2;
    if (lazyExist[node]) {
      lazyExist[node] = false;
      pushDown(lazyValue[node], node * 2, nodeLeft, mid);
      pushDown(lazyValue[node], node * 2 + 1, mid + 1, nodeRight);
      lazyValue[node] = DEFAULT_VALUE;
    }

    int leftVal = updateRec(left, right, newValue, node * 2, nodeLeft, mid);
    int rightVal = updateRec(left, right, newValue, node * 2 + 1, mid + 1, nodeRight);
    return tree[node] = merge(leftVal, rightVal);
  }

  private int queryRec(int left, int right, int node, int nodeLeft, int nodeRight) {
    if (right < nodeLeft || nodeRight < left)
      return DEFAULT_VALUE;

    if (left <= nodeLeft && nodeRight <= right)
      return tree[node];

    int mid = (nodeLeft + nodeRight) / 2;

    if (lazyExist[node]) {
      lazyExist[node] = false;
      pushDown(lazyValue[node], node * 2, nodeLeft, mid);
      pushDown(lazyValue[node], node * 2 + 1, mid + 1, nodeRight);
      lazyValue[node] = DEFAULT_VALUE;
    }

    return merge(queryRec(left, right, node * 2, nodeLeft, mid),
            queryRec(left, right, node * 2 + 1, mid + 1, nodeRight));
  }

}