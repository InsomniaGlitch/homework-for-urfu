package hw2;

public class task2 {
    void calculate(int A, int N) {
        int curr = A;
        while(curr < N) {
            System.out.println(curr);
            curr *= A;
        }
    }
}
