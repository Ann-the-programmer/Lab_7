public class ResidentialSite extends Site {

    public ResidentialSite(double units, double rate) {
        super(units, rate);
    }

    public double getBillableAmount() {
        double base = getBase();
        double tax = getTax(base);

        return base + tax;
    }

    private static double getTax(double base) {
        return base * Site.TAX_RATE;
    }

    private double getBase() {
        return units * rate;
    }
}