public abstract class Site {

    protected double units;
    protected double rate;

    public static final double TAX_RATE = 0.10; // example reasonable value

    public Site(double units, double rate) {
        this.units = units;
        this.rate = rate;
    }

    public final double getBillableAmount() {

        return getBaseAmount() + getTaxAmount();
    }

    protected abstract double getTaxAmount();

    protected abstract double getBaseAmount();
}