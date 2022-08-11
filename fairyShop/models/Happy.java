package fairyShop.models;


public class Happy extends BaseHelper{

    private static final int ENERGY = 100;

    public Happy(String name) {
        super(name, ENERGY);
    }

    @Override
    public void work() {
        setEnergy(getEnergy() - 10);
        if (getEnergy() <= 0) {
            setEnergy(0);
        }
    }
}
