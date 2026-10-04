package hw1;
public class task2Test {
    public static void main(String[] args) {
        task2 t = new task2();
        double x = 0.0;
        double expected = Math.PI / 2;
        double real = t.calculateShit(x);
        if (Math.abs(real - expected) > 1e-9) {
            throw new AssertionError("calculateShit(0.0) should be pi/2, not " + real);
        }
        System.out.println("task2 test passed");
    }
}
