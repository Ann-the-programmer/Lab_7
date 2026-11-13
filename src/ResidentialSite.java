public class ResidentialSite extends Site {

    public ResidentialSite(double units, double rate) {
        super(units, rate);
    }

    @Override
    protected double getTaxAmount() {
        return getBaseAmount() * Site.TAX_RATE;
    }

    @Override
    protected double getBaseAmount() {
        return units * rate;
    }
}