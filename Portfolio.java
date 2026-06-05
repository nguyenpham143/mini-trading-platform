import java.util.*;

public class Portfolio implements Observable<String> {
    private final String portfolioId;
    private final String ownerName;
    private final List<Position> positions;
    private final List<Observer<String>> observers;

    public Portfolio(String portfolioId, String ownerName) {
        this.portfolioId = portfolioId;
        this.ownerName = ownerName;
        this.positions = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public void addPosition(Instrument inst, int qty, double costBasis) {
        boolean alreadyHave = false;

        for (Position p : positions) {
            if (p.getInstrument().getSymbol().equals(inst.getSymbol())) {
                p.addQuantity(qty, costBasis);
                alreadyHave = true;
                break;
            }
        }

        if (!alreadyHave) {
            positions.add(new Position(inst, qty, costBasis));
        }

        notifyObservers("ADDED: " + inst.getSymbol() + " x" + qty);
    }

    public void removePosition(String symbol) throws PositionNotFoundException {
        List<Position> toRemove = new ArrayList<>();
        
        for (Position p : positions) {
            if (p.getInstrument().getSymbol().equals(symbol)) {
                toRemove.add(p);
            }
        }
        
        if (toRemove.isEmpty()) {
            throw new PositionNotFoundException("Position not found: " + symbol);
        }
        
        positions.removeAll(toRemove); 
        notifyObservers("REMOVED: " + symbol);
    }

    public double totalMarketValue() {
        double total = 0.0;
        for (Position p : positions) {
            total += p.marketValue();
        }
        return total;
    }

    public double totalUnrealizedPnL() {
        double total = 0.0;
        for (Position p : positions) {
            total += p.unrealizedPnL();
        }
        return total;
    }

    public Position getPosition(String symbol) throws PositionNotFoundException {
        for (Position p : positions) {
            if (p.getInstrument().getSymbol().equals(symbol)) {
                return p;
            }
        }
        throw new PositionNotFoundException("Position not found: " + symbol);
    }

    public List<Position> getPositionsSortedByValue() {
        List<Position> sorted = new ArrayList<>(positions);
        sorted.sort((p1, p2) -> Double.compare(p2.marketValue(), p1.marketValue()));
        return sorted;
    }

    public Map<String, Double> allocationByAssetClass() {
        Map<String, Double> allocation = new HashMap<>();
        double totalVal = totalMarketValue();

        if (totalVal == 0) return allocation;

        for (Position p : positions) {
            String assetClass = p.getInstrument().assetClass();
            double currentValue = allocation.getOrDefault(assetClass, 0.0);
            allocation.put(assetClass, currentValue + p.marketValue());
        }

        for (Map.Entry<String, Double> entry : allocation.entrySet()) {
            allocation.put(entry.getKey(), entry.getValue() / totalVal * 100.0);
        }

        return allocation;
    }

    public void revalueAll(PricingStrategy strategy) {
        for (Position p : positions) {
            double newValue = strategy.calculateFairValue(p.getInstrument());
            p.getInstrument().updatePrice(newValue);
        }

        notifyObservers("REVALUED: " + strategy.strategyName());
    }

    @Override
    public void addObserver(Observer<String> observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<String> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String event) {
        for (Observer<String> obs : observers) {
            obs.onEvent(event);
        }
    }

    public String getPortfolioId() {
        return this.portfolioId;
    }

    public String getOwnerName() {
        return this.ownerName;
    }
}
