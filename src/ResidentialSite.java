public class ResidentialSite extends Site {

    public ResidentialSite(double units, double rate) {
        super(units, rate);
    }

    public double getBillableAmount() {

        return getBase() + getTax();
    }

    private double getTax() {
        return getBase() * Site.TAX_RATE;
    }

    private double getBase() {
        return units * rate;
    }
}