public class LifelineSite extends Site {

    public LifelineSite(double units, double rate) {
        super(units, rate);
    }

    public double getBillableAmount() {
        double base = units * rate * 0.5;
        double tax = base * Site.TAX_RATE * 0.2;

        return base + tax;
    }
}