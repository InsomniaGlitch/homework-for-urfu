package hw2;

public class task3 {
    boolean determineShit(int N) {
        int len = Integer.toString(N).length();
        boolean flag = false;
        for(int i = 0; i <= len; i++) {
            if((N % 10^(i + 1)) / (10^i) == 2) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}
