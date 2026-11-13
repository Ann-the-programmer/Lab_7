public class LifelineSite extends Site {

    public LifelineSite(double units, double rate) {
        super(units, rate);
    }

    public double getBillableAmount() {
        double base = getBase();
        double tax = getTax(base);

        return base + tax;
    }

    private double getTax(double base) {
        return base * Site.TAX_RATE * 0.2;
    }

    private double getBase() {
        return units * rate * 0.5;
    }
}