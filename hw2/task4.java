package hw2;

public class task4 {
    public void doBullshit(int a, int b) {
        System.out.println(Math.min(a, b)
        + "\n" + Math.max(a, b)
        + "\n" + (Math.max(a, b) + 1) + "\n" + (Math.min(a, b) - 1)
        + "\n" + (Math.max(a, b) + 1) + " >= " + Math.max(a, b) + Math.min(a, b) + (Math.min(a, b) - 1));
    }
}
