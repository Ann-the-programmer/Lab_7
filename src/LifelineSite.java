public class LifelineSite extends Site {

    public LifelineSite(double units, double rate) {
        super(units, rate);
    }

    public double getBillableAmount() {

        return getBaseAmount() + getTaxAmount();
    }

    protected double getTaxAmount() {
        return getBaseAmount() * Site.TAX_RATE * 0.2;
    }

    protected double getBaseAmount() {
        return units * rate * 0.5;
    }
}
