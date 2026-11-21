package hw6;

import java.util.Arrays;
import java.util.Objects;

public class correlation {
    public static void main(String[] args) {
        double[] x = {1, 2, 3, 4, 5};
        double[] y = {2, 4, 5, 4, 5};

        correlationAnalysis one = new pearsonAnalysis("Pearson", x, y, true);
        correlationAnalysis[] analyses = {
            one,
            new spearmanAnalysis("Spearman", x, y, true),
            new pearsonAnalysis("Pearson copy", x, y, true)
        };

        System.out.println("=== Parent-type variable ===");
        correlationAnalysis parentReference = new pearsonAnalysis("Pearson", x, y, true);
        System.out.println(parentReference);

        System.out.println("Array length: " + analyses.length);
        System.out.println(Arrays.toString(analyses));

        for (correlationAnalysis analysis : analyses) {
            CorrelationReport report = analysis.evaluate();
            System.out.println(report.summary());
        }

        CorrelationReport r1 = analyses[0].evaluate();
        CorrelationReport r2 = analyses[1].evaluate();
        System.out.println("Compare r1 vs r2: " + r1.compareTo(r2));

        correlationAnalysis same = new pearsonAnalysis("Pearson", x, y, true);
        System.out.println(same.toString());
        System.out.println("one.equals(same): " + one.equals(same));
        System.out.println("one.hashCode(): " + one.hashCode());
        System.out.println("same.hashCode(): " + same.hashCode());
        System.out.println("one.equals(analyses[1]): " + one.equals(analyses[1]));
    }
}

// 1. Изучите вашу предметную область, найдите значения с конечным набором допустимых вариантов. Реализуйте его в качестве перечисления (enum).
enum CorrelationStrength {
    NONE("Absent", 0.0),
    WEAK("Meager", 0.1),
    MODERATE("Moderate", 0.3),
    STRONG("Major", 0.5),
    VERY_STRONG("Most certain", 0.7),
    UNDEFINED("Undefined", -1.0);

    // 3. Расширьте enum дополнительным полем.
    private final String label;
    private final double minAbsValue;

    CorrelationStrength(String label, double minAbsValue) {
        this.label = label;
        this.minAbsValue = minAbsValue;
    }

    public String getLabel() {
        return label;
    }

    public double getMinAbsValue() {
        return minAbsValue;
    }

    public static CorrelationStrength fromCoefficient(double r) {
        if (Double.isNaN(r) || !Double.isFinite(r)) {
            return UNDEFINED;
        }
        double abs = Math.abs(r);
        if (abs >= VERY_STRONG.minAbsValue) return VERY_STRONG;
        if (abs >= STRONG.minAbsValue) return STRONG;
        if (abs >= MODERATE.minAbsValue) return MODERATE;
        if (abs >= WEAK.minAbsValue) return WEAK;
        return NONE;
    }

    // 2. Используйте enum в switch-инструкции или выражении минимум в одном осмысленном сценарии проекта.
    // 6. Для демонстрации работы используйте запись и перечисление в рабочих сценариях проекта, а не просто в main. Есть ещё 2 момента.
    public String recommendation(double r) {
        return switch (this) {
            case VERY_STRONG -> String.format("[%s, floor %.2f] Perfect corr. (r=%.4f): use it as you will.", label, minAbsValue, r);
            case STRONG -> String.format("[%s, floor %.2f] Strong corr. (r=%.4f): consider using it if that's the best there is.", label, minAbsValue, r);
            case MODERATE -> String.format("[%s, floor %.2f] Noticeable corr. (r=%.4f): check if a side factor produces noise.", label, minAbsValue, r);
            case WEAK -> String.format("[%s, floor %.2f] Weak corr. (r=%.4f): ttest on a bigger volume.", label, minAbsValue, r);
            case NONE -> String.format("[%s, floor %.2f] Nothing here correlates (r=%.4f).", label, minAbsValue, r);
            case UNDEFINED -> String.format("[%s] Undefined (a row has 0 dispersion).", label);
        };
    }
}

// 4. Изучите вашу предметную область, найдите сущность, свойства которой не меняются в течение работы программы, либо их модификация редка, что такое изменение можно смоделировать созданием новой сущности. Реализуйте ее как запись (record).
// 5. ... Реализуйте для записи интерфейс, чтобы можно было применить встроенную сортировку ...
record CorrelationReport(
    String methodName,
    double coefficient,
    CorrelationStrength strength,
    int sampleSize
) implements Comparable<CorrelationReport> {

    CorrelationReport {
        if (methodName == null || methodName.isBlank()) {
            throw new IllegalArgumentException("Method name must not be blank");
        }
        if (sampleSize < 2) {
            throw new IllegalArgumentException("Sample size must be at least 2");
        }
        Objects.requireNonNull(strength, "Strength must not be null");
    }

    public CorrelationReport withMethodName(String newName) {
        return new CorrelationReport(newName, this.coefficient, this.strength, this.sampleSize);
    }

    // 6. Для демонстрации работы используйте запись и перечисление в рабочих сценариях проекта, а не просто в main. Есть ещё 2 момента.
    public String summary() {
        return strength.recommendation(coefficient) + " | Метод: " + methodName + ", n=" + sampleSize;
    }

    @Override
    public int compareTo(CorrelationReport other) {
        Objects.requireNonNull(other, "Cannot compare with null report");
        boolean thisNaN = Double.isNaN(this.coefficient);
        boolean otherNaN = Double.isNaN(other.coefficient);
        if (thisNaN && otherNaN) return 0;
        if (thisNaN) return 1;
        if (otherNaN) return -1;
        int byAbs = Double.compare(Math.abs(other.coefficient), Math.abs(this.coefficient));
        if (byAbs != 0) return byAbs;
        return Double.compare(other.coefficient, this.coefficient);
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

    // 6. Для демонстрации работы используйте запись и перечисление в рабочих сценариях проекта, а не просто в main. Есть ещё 2 момента.
    public CorrelationReport evaluate() {
        double r = calculate();
        CorrelationStrength strength = CorrelationStrength.fromCoefficient(r);
        return new CorrelationReport(name, r, strength, x.length);
    }

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