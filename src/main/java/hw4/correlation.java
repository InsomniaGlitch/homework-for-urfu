package hw4;

import java.util.Arrays;
import java.util.Objects;

public class correlation {
    public static void main(String[] args) {
        double[] x = {1, 2, 3, 4, 5};
        double[] y = {2, 4, 5, 4, 5};

        // Создайте переменную родительского типа, ...
        correlationAnalysis one = new pearsonAnalysis("Pearson", x, y, true);
        // ... массив родительского типа и разместите в массиве объекты наследников
        correlationAnalysis[] analyses = {
            one,
            new spearmanAnalysis("Spearman", x, y, true),
            new pearsonAnalysis("Pearson copy", x, y, true)
        };

        System.out.println("=== Parent-type variable ===");
        correlationAnalysis parentReference = new pearsonAnalysis("Pearson", x, y, true);
        System.out.println(parentReference);

        // Подготовьте примеры запуска, демонстрацию массива ...
        System.out.println("Array length: " + analyses.length);
        System.out.println(Arrays.toString(analyses));

        // Обработайте массив одним циклом с вызовом переопределённого полиморфного метода
        for (correlationAnalysis analysis : analyses) {
            System.out.printf("%s -> %.4f%n", analysis.getName(), analysis.calculate());
        }

        // ... toString(), equals() и hashCode()
        correlationAnalysis same = new pearsonAnalysis("Pearson", x, y, true);
        System.out.println(same.toString());
        System.out.println("one.equals(same): " + one.equals(same));
        System.out.println("one.hashCode(): " + one.hashCode());
        System.out.println("same.hashCode(): " + same.hashCode());
        System.out.println("one.equals(analyses[1]): " + one.equals(analyses[1]));
    }
}

abstract class correlationAnalysis {
    private final String name;
    private final double[] x;
    private final double[] y;

    protected correlationAnalysis(String name, double[] x, double[] y) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        if (x == null || y == null) {
            throw new IllegalArgumentException("Data arrays must not be null");
        }
        if (x.length < 2 || x.length != y.length) {
            throw new IllegalArgumentException("Arrays must have equal lengths of at least 2");
        }
        validateFinite(x, "X");
        validateFinite(y, "Y");
        this.name = name.trim();
        this.x = x.clone();
        this.y = y.clone();
    }

    private static void validateFinite(double[] values, String label) {
        for (double value : values) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException(label + " values must be finite numbers");
            }
        }
    }

    public final String getName() { return name; }
    protected final double[] getX() { return x.clone(); }
    protected final double[] getY() { return y.clone(); }

    public abstract double calculate();

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{name='" + name + "', x="
                + Arrays.toString(x) + ", y=" + Arrays.toString(y) + '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        correlationAnalysis other = (correlationAnalysis) object;
        return name.equals(other.name)
                && Arrays.equals(x, other.x)
                && Arrays.equals(y, other.y);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getClass(), name);
        result = 31 * result + Arrays.hashCode(x);
        result = 31 * result + Arrays.hashCode(y);
        return result;
    }
}

final class pearsonAnalysis extends correlationAnalysis {
    private final boolean clampToUnitInterval;

    public pearsonAnalysis(String name, double[] x, double[] y, boolean clampToUnitInterval) {
        super(name, x, y);
        this.clampToUnitInterval = clampToUnitInterval;
    }

    @Override
    public double calculate() {
        double[] x = getX();
        double[] y = getY();
        double meanX = mean(x);
        double meanY = mean(y);
        double numerator = 0.0, sumX = 0.0, sumY = 0.0;
        for (int i = 0; i < x.length; i++) {
            double dx = x[i] - meanX;
            double dy = y[i] - meanY;
            numerator += dx * dy;
            sumX += dx * dx;
            sumY += dy * dy;
        }
        if (sumX == 0.0 || sumY == 0.0) return Double.NaN;
        double result = numerator / Math.sqrt(sumX * sumY);
        return clampToUnitInterval ? Math.max(-1.0, Math.min(1.0, result)) : result;
    }

    private static double mean(double[] values) {
        double sum = 0.0;
        for (double value : values) sum += value;
        return sum / values.length;
    }

    @Override
    public String toString() {
        return super.toString().replaceFirst("\\}$", ", clampToUnitInterval=" + clampToUnitInterval + "}");
    }

    @Override
    public boolean equals(Object object) {
        return super.equals(object)
                && clampToUnitInterval == ((pearsonAnalysis) object).clampToUnitInterval;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + Boolean.hashCode(clampToUnitInterval);
    }
}

final class spearmanAnalysis extends correlationAnalysis {
    private final boolean averageTies;

    public spearmanAnalysis(String name, double[] x, double[] y, boolean averageTies) {
        super(name, x, y);
        this.averageTies = averageTies;
    }

    @Override
    public double calculate() {
        double[] ranksX = ranks(getX());
        double[] ranksY = ranks(getY());
        pearsonAnalysis rankCorrelation = new pearsonAnalysis("rank correlation", ranksX, ranksY, true);
        return rankCorrelation.calculate();
    }

    private double[] ranks(double[] values) {
        Integer[] order = new Integer[values.length];
        for (int i = 0; i < order.length; i++) order[i] = i;
        Arrays.sort(order, (a, b) -> Double.compare(values[a], values[b]));
        double[] ranks = new double[values.length];
        int start = 0;
        while (start < order.length) {
            int end = start;
            while (end + 1 < order.length
                    && Double.compare(values[order[end]], values[order[end + 1]]) == 0) {
                end++;
            }
            double rank = averageTies ? ((start + 1) + (end + 1)) / 2.0 : start + 1;
            for (int i = start; i <= end; i++) ranks[order[i]] = rank;
            start = end + 1;
        }
        return ranks;
    }

    @Override
    public String toString() {
        return super.toString().replaceFirst("\\}$", ", averageTies=" + averageTies + "}");
    }

    @Override
    public boolean equals(Object object) {
        return super.equals(object)
                && averageTies == ((spearmanAnalysis) object).averageTies;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + Boolean.hashCode(averageTies);
    }
}
