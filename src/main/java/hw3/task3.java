package hw3;

public class task3 {
    public static double[] build(int[] a) {
        int n = a.length;
        double[] b = new double[n];
        double sum = 0;
        int count = 0;

        for (int k = n - 1; k >= 0; k--) {
            sum += a[k];
            count++;
            b[k] = sum / count;
        }
        return b;
    }
}
