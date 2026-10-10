package hw2;

public class task2 {
    void calculateShit(int A, int N) {
        int curr = A;
        while(curr < N) {
            System.out.println(curr);
            curr *= A;
        }
    }
}
