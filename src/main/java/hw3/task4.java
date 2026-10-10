package hw3;

public class task4 {
    public int[][] matrix(int m, int d, int n, int[] arr) {
        n = arr.length >= n ? n : arr.length;
        int[][] a = new int[m][n];
        a[0] = arr;

        for (int i = 1; i < m; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = a[i - 1][j] * d;
            }
        }
        return a;
    }
}
