import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("       Mini Trading Platform Verification & Demonstration         ");
        System.out.println("==================================================================");

        // -------------------------------------------------------------
        // 1. Part A: Financial Instruments & Polymorphism
        // -------------------------------------------------------------
        System.out.println("\n[1] Creating Financial Instruments (Part A):");
        Stock apple = new Stock("AAPL", "Apple Inc.", 185.50, 2800000000000.0, "Technology");
        Stock tesla = new Stock("TSLA", "Tesla Inc.", 210.25, 650000000000.0, "Automotive");
        Bond usTreasury = new Bond("US10Y", "US Treasury 10-Year Bond", 980.00, 4.5, 10);
        Option appleCall = new Option("AAPL24C190", "AAPL Call Option 190", 6.20, 190.00, true, 45);
        Future sp500Future = new Future("ESZ4", "E-mini S&P 500 Future", 5120.00, 50.0, 90);

        System.out.printf("  - Stock: %s | Price: $%.2f | Risk Score: %.1f | Asset Class: %s%n",
                apple.getSymbol(), apple.getCurrentPriceValue(), apple.riskScore(), apple.assetClass());
        System.out.printf("  - Bond: %s | Price: $%.2f | Coupon: %.1f%% | Annual Payment (10 units): $%.2f%n",
                usTreasury.getSymbol(), usTreasury.getCurrentPriceValue(), usTreasury.getCouponRate(), usTreasury.annualCouponPayment(10));
        System.out.printf("  - Option: %s | Strike: $%.2f | In-The-Money (spot $195): %b%n",
                appleCall.getSymbol(), appleCall.getStrikePrice(), appleCall.isInTheMoney(195.00));
        System.out.printf("  - Future: %s | Contract Size: %.1f | Expiry: %d days%n",
                sp500Future.getSymbol(), sp500Future.getContractSize(), sp500Future.getExpiryDays());

        // -------------------------------------------------------------
        // 2. Part B: Strategy Pattern (Pricing Strategies)
        // -------------------------------------------------------------
        System.out.println("\n[2] Testing Pricing Strategies (Part B):");
        PricingStrategy simpleStrategy = new SimplePricingStrategy();
        PricingStrategy riskAdjustedStrategy = new RiskAdjustedPricingStrategy();

        System.out.printf("  - %s for AAPL: $%.2f%n",
                simpleStrategy.strategyName(), simpleStrategy.calculateFairValue(apple));
        System.out.printf("  - %s for AAPL: $%.2f%n",
                riskAdjustedStrategy.strategyName(), riskAdjustedStrategy.calculateFairValue(apple));
        System.out.printf("  - %s for US10Y: $%.2f%n",
                riskAdjustedStrategy.strategyName(), riskAdjustedStrategy.calculateFairValue(usTreasury));

        // -------------------------------------------------------------
        // 3. Part C & D: Portfolio Management & Observer Pattern
        // -------------------------------------------------------------
        System.out.println("\n[3] Portfolio Operations & Observer Audit Trail (Parts C & D):");
        Portfolio portfolio = new Portfolio("PF-8888", "Nguyen Pham");

        // Register an Observer to monitor portfolio events
        List<String> eventLogs = new ArrayList<>();
        portfolio.addObserver(event -> {
            eventLogs.add(event);
            System.out.println("  [EVENT OBSERVED] " + event);
        });

        // Add positions
        portfolio.addPosition(apple, 100, 175.00);      // 100 shares @ $175
        portfolio.addPosition(tesla, 50, 220.00);       // 50 shares @ $220
        portfolio.addPosition(usTreasury, 20, 970.00);  // 20 bonds @ $970
        portfolio.addPosition(appleCall, 10, 5.00);     // 10 contracts @ $5.00

        System.out.printf("\n  - Total Market Value: $%.2f%n", portfolio.totalMarketValue());
        System.out.printf("  - Total Unrealized PnL: $%.2f%n", portfolio.totalUnrealizedPnL());

        System.out.println("\n  - Asset Allocation by Asset Class:");
        Map<String, Double> allocation = portfolio.allocationByAssetClass();
        for (Map.Entry<String, Double> entry : allocation.entrySet()) {
            System.out.printf("    * %-15s: %.2f%%%n", entry.getKey(), entry.getValue());
        }

        System.out.println("\n  - Positions Sorted by Market Value:");
        for (Position p : portfolio.getPositionsSortedByValue()) {
            System.out.printf("    * %-10s | Qty: %4d | Value: $%9.2f | PnL: $%7.2f%n",
                    p.getInstrument().getSymbol(), p.getQuantity(), p.marketValue(), p.unrealizedPnL());
        }

        // Revaluing entire portfolio using Risk-Adjusted Strategy
        System.out.println("\n  - Revaluing Portfolio with Risk-Adjusted Strategy...");
        portfolio.revalueAll(riskAdjustedStrategy);

        // -------------------------------------------------------------
        // 4. Generic Risk Analysis
        // -------------------------------------------------------------
        System.out.println("\n[4] Generic Risk Analysis (RiskAnalyzer):");
        RiskAnalyzer<Instrument> riskAnalyzer = new RiskAnalyzer<>();
        riskAnalyzer.add(apple);
        riskAnalyzer.add(tesla);
        riskAnalyzer.add(usTreasury);
        riskAnalyzer.add(appleCall);
        riskAnalyzer.add(sp500Future);

        System.out.printf("  - Average Instrument Risk: %.2f%n", riskAnalyzer.averageRisk());
        System.out.printf("  - Highest Risk Instrument: %s (Risk: %.1f)%n",
                riskAnalyzer.highestRisk().getSymbol(), riskAnalyzer.highestRisk().riskScore());
        System.out.printf("  - Lowest Risk Instrument:  %s (Risk: %.1f)%n",
                riskAnalyzer.lowestRisk().getSymbol(), riskAnalyzer.lowestRisk().riskScore());

        // -------------------------------------------------------------
        // 5. Part E: Visitor Pattern (Tax Report)
        // -------------------------------------------------------------
        System.out.println("\n[5] Regulatory & Tax Report (Visitor Pattern - Part E):");
        TaxReportVisitor taxVisitor = new TaxReportVisitor();
        apple.accept(taxVisitor);
        usTreasury.accept(taxVisitor);
        appleCall.accept(taxVisitor);
        sp500Future.accept(taxVisitor);

        System.out.println(taxVisitor.getReport());

        System.out.println("\n==================================================================");
        System.out.println("  ALL DEMONSTRATION MODULES EXECUTED SUCCESSFULLY!                ");
        System.out.println("==================================================================");
    }
}
