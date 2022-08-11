package fairyShop.models;

public class Sleepy extends BaseHelper{

    private static final int ENERGY = 50;

    public Sleepy(String name) {
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
