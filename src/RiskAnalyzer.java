import java.util.*;

public class RiskAnalyzer<T extends Instrument> {
    private final List<T> instruments = new ArrayList<>();

    public void add(T instrument) {
        if (!instruments.contains(instrument)) {
            instruments.add(instrument);
        }
    }

    public double averageRisk() {
        if (instruments.isEmpty()) return 0.0;

        double sum = 0;
        for (T inst : instruments) {
            sum += inst.riskScore();
        }

        return sum / (double)instruments.size();
    }

    public T highestRisk() {
        if (instruments.isEmpty()) {
            throw new NoSuchElementException("No instruments.");
        }

        T highest = instruments.get(0);
        for (T inst : instruments) {
            if (inst.riskScore() > highest.riskScore()) {
                highest = inst;
            }
        }

        return highest;
    }

    public T lowestRisk() {
        if (instruments.isEmpty()) {
            throw new NoSuchElementException("No instruments.");
        }

        T lowest = instruments.get(0);
        for (T inst : instruments) {
            if (inst.riskScore() < lowest.riskScore()) {
                lowest = inst;
            }
        }

        return lowest;
    }

    public List<T> getAboveRiskThreshold(double threshold) {
        List<T> result = new ArrayList<>();
        for (T inst : instruments) {
            if (inst.riskScore() > threshold) {
                result.add(inst);
            }
        }
        
        return result;
    }
}
