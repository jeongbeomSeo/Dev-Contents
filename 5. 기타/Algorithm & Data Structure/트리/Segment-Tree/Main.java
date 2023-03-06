public class Main {
  /**
   *
   * @param a: 배열 A
   * @param tree: 세그먼트 트리
   * @param node: 노드 번호
   * @param start
   * @param end : node에 저장되어 있는 합의 범위가 start - end
   */
  void init(long[] a, long[] tree, int node, int start, int end) {
    if (start == end) {
      tree[node] = a[start];
    } else {
      init(a, tree, node * 2, start, (start + end) / 2);
      init(a, tree, node * 2 + 1, (start + end)/ 2 + 1, end);
      tree[node] = tree[node * 2] + tree[node * 2 + 1];
    }
  }

  long query(long[] tree, int node, int start, int end, int left, int right) {
    if (left > end || right < start) {
      return 0;
    }
    if (left <= start && end <= right) {
      return tree[node];
    }
    long lsum = query(tree, node*2, start, (start + end) / 2, left, right);
    long rsum = query(tree, node * 2 + 1, (start + end) / 2, end, left, right);
    return lsum+rsum;
  }

  void update(long[] a, long[] tree, int node, int start, int end, int index, long val) {
    if (index < start || index > end) {
      return;
    }
    if (start == end) {
      a[index] = val;
      tree[node] = val;
      return;
    }
    update(a, tree, node * 2, start, (start + end) / 2, index, val);
    update(a, tree, node * 2 + 1, (start + end) / 2 + 1, end, index, val);
    tree[node] = tree[node * 2] + tree[node * 2 + 1];
  }
}