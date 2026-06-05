import java.time.LocalDateTime;

public abstract class Instrument implements Tradeable, Priceable {
    private final String symbol;
    private String name;
    private double currentPrice;
    private LocalDateTime lastUpdated;

    public Instrument(String symbol, String name, double currentPrice) {
        this.symbol = symbol;
        this.name = name;
        this.currentPrice = currentPrice;
        this.lastUpdated = LocalDateTime.now();
    }

    public abstract double riskScore();

    public abstract String assetClass();

    public abstract void accept(InstrumentVisitor visitor);
    
    public void updatePrice(double newPrice) {
        if (newPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.currentPrice = newPrice;
        this.lastUpdated = LocalDateTime.now();
    }

    public String getSymbol() {
        return this.symbol;        
    }

    public String getName() {
        return this.name;
    }

    public double getCurrentPriceValue() {
        return this.currentPrice;
    }

    public LocalDateTime getLastUpdated() {
        return this.lastUpdated;
    }

    @Override
    public String toString() {
        return String.format("%s[symbol=%s, price=%.2f, risk=%.2f]", 
                             getClass().getSimpleName(), symbol, currentPrice, riskScore());
    }

    @Override
    public double getPriceChange(double previousPrice) {
        return this.getCurrentPriceValue() - previousPrice;
    }

    @Override
    public double getPriceChangePercent(double previousPrice) {
        if (previousPrice == 0.0) {
            throw new IllegalArgumentException("Invalid previous price: division by zero");
        }
        return ((this.getCurrentPriceValue() - previousPrice) / previousPrice) * 100.0;
    }

    @Override
    public boolean isAvailableForTrading() {
        return true;
    }
}
