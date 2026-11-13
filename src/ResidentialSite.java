public class ResidentialSite extends Site {

    public ResidentialSite(double units, double rate) {
        super(units, rate);
    }

    public double getBillableAmount() {

        return getBaseAmount() + getTaxAmount();
    }

    protected double getTaxAmount() {
        return getBaseAmount() * Site.TAX_RATE;
    }

    protected double getBaseAmount() {
        return units * rate;
    }
}