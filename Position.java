public class Position {
    private final Instrument instrument;
    private int quantity;
    private double averageCostBasis;

    public Position(Instrument instrument, int quantity, double averageCostBasis) {
        this.instrument = instrument;
        this.quantity = quantity;
        this.averageCostBasis = averageCostBasis;
    }

    public double marketValue() {
        return this.quantity * this.instrument.getCurrentPriceValue();
    }

    public double unrealizedPnL() {
        return marketValue() - (this.quantity * this.averageCostBasis);
    }

    public void addQuantity(int qty, double costBasis) {
        int oldQuantity = this.quantity;
        double oldAverageCostBasis = this.averageCostBasis;

        this.quantity += qty;
        this.averageCostBasis = (oldQuantity * oldAverageCostBasis + qty * costBasis) / (double)this.quantity;
    }

    public Instrument getInstrument() {
        return this.instrument;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public double getAverageCostBasis() {
        return this.averageCostBasis;
    }

    @Override
    public String toString() {
        return String.format("Position[symbol=%s, qty=%d, value=%.2f, pnl=%.2f]", 
                             instrument.getSymbol(), quantity, marketValue(), unrealizedPnL());
    }
}
