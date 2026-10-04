package hw2;
public class task1 {
    double calculateShit(int N) {
        double res = 0;
        for(int i = 2; i <= N; i++) {
            res += 1/(i^2 - 1);
        }
        return res;
    }
}