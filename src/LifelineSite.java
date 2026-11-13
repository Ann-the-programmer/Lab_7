public class LifelineSite extends Site {

    public LifelineSite(double units, double rate) {
        super(units, rate);
    }

    @Override
    protected double getTaxAmount() {
        return getBaseAmount() * Site.TAX_RATE * 0.2;
    }

    @Override
    protected double getBaseAmount() {
        return units * rate * 0.5;
    }
}
