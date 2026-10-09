package hw3;

public class task2 {
    public void two(Object[] arr) {
        int n = arr.length - 1;
        while(n > 0) {
            Object curr = arr[n];
            for(int i = n - 1; i > -1; i--) {
                if (curr.equals(arr[i])) {
                    System.out.print(String.valueOf(i) + " " + String.valueOf(n));
                }
            }
            n--;
        }
    }
}
