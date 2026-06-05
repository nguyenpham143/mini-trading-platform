public class TaxReportVisitor implements InstrumentVisitor {
    private double totalTaxLiability = 0.0;
    private StringBuilder report = new StringBuilder();

    @Override
    public void visit(Stock stock) {
        double tax = stock.getCurrentPriceValue() * 0.15;
        this.totalTaxLiability += tax;
        this.report.append("Stock ").append(stock.getSymbol()).append(": ").append(String.format("%.2f", tax)).append("\n");
    }

    @Override
    public void visit(Bond bond) {
        double tax = bond.annualCouponPayment(1) * 0.3;
        this.totalTaxLiability += tax;
        this.report.append("Bond ").append(bond.getSymbol()).append(": ").append(String.format("%.2f", tax)).append("\n");
    }

    @Override
    public void visit(Option option) {
        double tax = option.getCurrentPriceValue() * 0.2;
        this.totalTaxLiability += tax;
        this.report.append("Option ").append(option.getSymbol()).append(": ").append(String.format("%.2f", tax)).append("\n");
    }

    @Override
    public void visit(Future future) {
        double tax = future.getCurrentPriceValue() * 0.2;
        this.totalTaxLiability += tax;
        this.report.append("Future ").append(future.getSymbol()).append(": ").append(String.format("%.2f", tax)).append("\n");
    }

    public double getTotalTaxLiability() {
        return this.totalTaxLiability;
    }

    public String getReport() {
        return this.report.toString() + "Total Tax: " + String.format("%.2f", this.totalTaxLiability);
    }
}