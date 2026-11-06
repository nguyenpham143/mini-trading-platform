# Mini Trading Platform (CO2039 - Assignment 1)

A Java-based simulation of a **Mini Trading Platform** and financial portfolio management system designed to demonstrate core Object-Oriented Programming (OOP) principles and classical design patterns. Developed as Assignment 1 for the **Advanced Programming (CO2039)** course at Ho Chi Minh City University of Technology (HCMUT - VNU-HCM), Semester 252.

## Overview

Modern financial trading systems handle diverse instrument classes, real-time price revaluation, portfolio risk analysis, and regulatory tax compliance. This project synthesizes key OOP design patterns into an extensible, decoupled architecture:

### 1. Instrument Hierarchy & Polymorphism
- **Abstract Instrument**: Base class implementing `Priceable` and `Tradeable`, maintaining trading symbols, market status, current prices, and visitor hooks.
- **Equities (`Stock`)**: Computes earnings per share (EPS), price-to-earnings (P/E) ratio, and market capitalization.
- **Fixed-Income (`Bond`)**: Models coupon rates, maturity periods, annual coupon payments, and yield to maturity (YTM).
- **Derivatives (`Derivative`, `Option`, `Future`)**: Models underlying asset dependencies, Call/Put intrinsic values, expiration thresholds, and contract notional values.

### 2. Strategy Pattern — Dynamic Pricing (`PricingStrategy`)
- Decouples pricing algorithms from the instrument hierarchy.
- **`SimplePricingStrategy`**: Evaluates standard mark-to-market prices.
- **`RiskAdjustedPricingStrategy`**: Applies asset-specific volatility and risk hair-cuts dynamically across positions.

### 3. Composition & Portfolio Management (`Portfolio`, `Position`)
- Manages an investor's holdings across varied asset classes.
- Tracks weighted asset allocation percentages (`allocationByAssetClass`), total unrealized Profit & Loss (PnL), and total market value.
- Supports sorted rank queries (`getPositionsSortedByValue`) and raises checked `PositionNotFoundException`.

### 4. Observer Pattern — Risk Monitoring (`Observer`, `Observable`)
- `Portfolio` serves as the `Observable` subject, broadcasting real-time trade events (`ADDED`, `REMOVED`, `REVALUED`).
- `RiskAnalyzer` acts as the `Observer`, recording audit trails, event histories, and real-time exposure notifications without tight coupling.

### 5. Visitor Pattern — Regulatory & Tax Reporting (`InstrumentVisitor`)
- `TaxReportVisitor` calculates jurisdiction-specific tax liabilities for each asset type without polluting the domain models:
  - Stocks: 15% capital gains tax.
  - Bonds: 30% coupon withholding tax.
  - Options & Futures: 20% transaction tax.

## Project Structure

```text
.
├── src/
│   ├── Instrument.java                          # Abstract instrument base
│   ├── Stock.java                               # Equity implementation
│   ├── Bond.java                                # Fixed-income implementation
│   ├── Derivative.java                          # Derivative base class
│   ├── Option.java                              # Option contracts
│   ├── Future.java                              # Futures contracts
│   ├── Position.java                            # Holding positions & PnL
│   ├── Portfolio.java                           # Portfolio engine (Observable)
│   ├── Priceable.java                           # Pricing contract
│   ├── Tradeable.java                           # Trading contract
│   ├── PricingStrategy.java                     # Strategy interface
│   ├── SimplePricingStrategy.java               # Mark-to-market strategy
│   ├── RiskAdjustedPricingStrategy.java         # Risk-adjusted strategy
│   ├── Observable.java                          # Event publisher interface
│   ├── Observer.java                            # Event subscriber interface
│   ├── RiskAnalyzer.java                        # Risk monitoring observer
│   ├── InstrumentVisitor.java                   # Visitor interface
│   ├── TaxReportVisitor.java                    # Tax calculation visitor
│   ├── PositionNotFoundException.java           # Domain exception
│   └── Main.java                                # Application demonstration runner
├── specs/
│   └── AP_Ass1_Spec.pdf                         # Assignment specification
├── .gitignore                                   # Git ignore rules
├── LICENSE                                      # MIT License
├── README.md                                    # Documentation
└── run.sh                                       # Build & execution script
```

## Getting Started

### Prerequisites

- **Java Development Kit (JDK)** version 17 or higher (tested on OpenJDK 21/25).

### Build & Run

Compile and execute the program directly using `javac` and `java`:

```bash
javac -d bin src/*.java
java -cp bin Main
```

Or execute the provided shell script:

```bash
bash run.sh
```

## License

This project is licensed under the [MIT License](LICENSE).
