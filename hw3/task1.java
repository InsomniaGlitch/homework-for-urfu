package hw3;

public class task1 {
    public void doShit(Object[] a) {
        for (int i = a.length - 1; i >= 0; i -= 2) {
            System.out.print(a[i].toString() + " ");
        }
    }
}
