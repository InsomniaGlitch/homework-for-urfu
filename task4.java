import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class task4 {
    Scanner in = new Scanner(System.in);
    ArrayList<Double> x = new ArrayList<Double>();
    ArrayList<Double> y = new ArrayList<Double>();

    void yapAboutIt() {
        System.out.println("Ok, so, I'm here to calculate Pearson correlations for you." + "\n"
            + "So, what you do is: input your data for X and, when you're finished, just tap Enter again." +
            "\n" + "I'll confirm your X and let you do the same for Y." + "\n" + "And then I output the coeff., simple, yeah?" + "You can always go back a step by inputting 'b'" + "So, this will be your X:"
        );
        doStateMachine();
    }

    void doStateMachine() {
        String state = "fillX";
        String[] states = {"fillX", "confirmX", "fillY", "confirmY", "correct", "calculate"};
        int goback = -1;

        while(state.equals("calculate") == false) {

            switch(state) {
                case "fillX": x = fillArray("x"); if(x.isEmpty()) {goback = 1;} break;
                case "confirmX": goback = confirmArray(x); if(goback == 1) {x.clear();} break;
                case "fillY": y = fillArray("y"); if(y.isEmpty()) {goback = 1;} break;
                case "confirmY": goback = confirmArray(y); if(goback == 1) {y.clear();} break;
                case "correct": if(x.size() != y.size()) {
                    System.out.println("Soory, don't think your datasets are of equal length, fix that.");
                    goback = 1;
                } break;
            }
            if(state.equals("fillX") && goback == 1) {
                System.out.println("There's nowhere to back off to yet.");
                goback = 0;
            }
            state = states[Arrays.asList(states).indexOf(state) - goback];
        }
        System.out.println(pearsonCorrelationCoefficient(x.stream().mapToDouble(Double::doubleValue).toArray(), y.stream().mapToDouble(Double::doubleValue).toArray()));
    }

    ArrayList<Double> fillArray(String id) {
        ArrayList<Double> res = new ArrayList<>();
        String input = in.nextLine();

        while(input.equals("") == false || res.size() < 2) {
            if(input.equals("b")) {
                if(res.isEmpty()) {
                    break;
                }
                res.clear();
                System.out.println(String.format("So, this will be your %s:", id));
            }
            if(input.equals("")) {
                System.out.println("You need at least 2 pieces of data, try harder.");
            }
            try {
                res.add(Double.parseDouble(input));
            } catch(Exception e) {
                System.out.println("That ain't no proper data; try inputting a double, if you will.");
            }
            input = in.nextLine();
        }
        return res;
    }

    int confirmArray(ArrayList<Double> arr) {
        System.out.println("So, this is your data, right?" + "\n" + arr.toString());
        while(true){
            String input = in.nextLine();
            switch(input) {
                case "b": return 1;
                case "": return -1;
            }
            System.out.println("Bruh, either type 'b' or press Enter, it's not that deep.");
        }
    }

    double pearsonCorrelationCoefficient(double[] x, double[] y) {
        double meanX = 0;
        double meanY = 0;
        for (int i = 0; i < x.length; i++) {
            meanX += x[i];
            meanY += y[i];
        }
        meanX /= x.length;
        meanY /= y.length;
        double numerator = 0;
        double sumX = 0;
        double sumY = 0;
        for (int i = 0; i < x.length; i++) {
            double dx = x[i] - meanX;
            double dy = y[i] - meanY;
            numerator += dx * dy;
            sumX += dx * dx;
            sumY += dy * dy;
        }
        if(sumX != 0 && sumY != 0) {
            return numerator / Math.sqrt(sumX * sumY);
        } else {
            return Double.NaN;
        }
    }
}
