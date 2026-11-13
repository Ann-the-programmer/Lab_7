public class LifelineSite extends Site {

    public LifelineSite(double units, double rate) {
        super(units, rate);
    }

    public double getBillableAmount() {
        double base = getBase();
        double tax = base * Site.TAX_RATE * 0.2;

        return base + tax;
    }

    private double getBase() {
        return units * rate * 0.5;
    }
}