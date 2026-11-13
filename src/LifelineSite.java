public class LifelineSite extends Site {

    public LifelineSite(double units, double rate) {
        super(units, rate);
    }

    public double getBillableAmount() {
        double tax = getTax();

        return getBase() + tax;
    }

    private double getTax() {
        return getBase() * Site.TAX_RATE * 0.2;
    }

    private double getBase() {
        return units * rate * 0.5;
    }
}