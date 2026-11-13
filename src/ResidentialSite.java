public class ResidentialSite extends Site {

    public ResidentialSite(double units, double rate) {
        super(units, rate);
    }

    public double getBillableAmount() {
        double base = getBase();
        double tax = base * Site.TAX_RATE;

        return base + tax;
    }

    private double getBase() {
        return units * rate;
    }
}