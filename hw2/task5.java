package hw2;

import java.util.Scanner;

public class task5 {
    public static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Calculate\n2. App info"
                    + "\n3. Devinfo\n0. Exit");
            String choice = in.nextLine().trim();
            if (isExit(choice) || choice.equals("0")) {
                return;
            }
            switch (choice) {
                case "1":
                    calculate();
                    break;
                case "2":
                    System.out.println("Calculates Pierson correlation coefficient.");
                    break;
                case "3":
                    System.out.println("Rasputin M.A., nothing else you need to know.");
                    break;
                default:
                    System.out.println("Whatever you've just typed in, don't type it in again, I beg of you.");
            }
        }
    }

    private static void calculate() {
        double[] x = readData("X", -1);
        if (x == null) {
            return;
        }
        double[] y = readData("Y", x.length);
        if (y != null) {
            System.out.println("Result: " + pearsonCorrelationCoefficient(x, y));
        }
    }

    private static double[] readData(String name, int expectedLength) {
        while (true) {
            System.out.println("Type in your spaced-out " + name + " values"
                    + (expectedLength < 0 ? " (no less than 2)" : " (exactly " + expectedLength + ")")
                    + "; you can always use exit to take a step back.");
            String input = in.nextLine().trim();
            if (isExit(input)) {
                return null;
            }
            String[] values = input.isEmpty() ? new String[0] : input.split("\\s+");
            if (values.length < 2 || (expectedLength >= 0 && values.length != expectedLength)) {
                System.out.println("SMH you've got those basic requirements wrong.");
                continue;
            }
            double[] result = new double[values.length];
            try {
                for (int i = 0; i < values.length; i++) {
                    result[i] = Double.parseDouble(values[i]);
                }
                return result;
            } catch (NumberFormatException e) {
                System.out.println("SMH you can't even type proper values in, what a bum.");
            }
        }
    }

    private static boolean isExit(String input) {
        return (input.toLowerCase().equals("выход") || input.toLowerCase().equals("exit")
         || input.toLowerCase().equals("q"));
    }

    private static double pearsonCorrelationCoefficient(double[] x, double[] y) {
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
        return sumX != 0 && sumY != 0
                ? numerator / Math.sqrt(sumX * sumY)
                : Double.NaN;
    }
}
