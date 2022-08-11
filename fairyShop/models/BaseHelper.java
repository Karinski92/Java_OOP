package fairyShop.models;

import fairyShop.common.ConstantMessages;

import java.util.ArrayList;
import java.util.Collection;

import static fairyShop.common.ExceptionMessages.HELPER_NAME_NULL_OR_EMPTY;

public abstract class BaseHelper implements Helper{

    private String name;
    private int energy;
    private Collection<Instrument> instruments;

    public BaseHelper(String name, int energy) {
        setName(name);
        setEnergy(energy);
        this.instruments = new ArrayList<>();
    }

    private void setName (String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(HELPER_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    public void setEnergy (int energy) {
        this.energy = energy;
    }
    public abstract void work ();

    @Override
    public void addInstrument(Instrument instrument) {
        instruments.add(instrument);

    }

    @Override
    public boolean canWork() {
        return energy > 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getEnergy() {
        return energy;
    }

    @Override
    public Collection<Instrument> getInstruments() {
        return instruments;
    }

    public String getInfo () {
        return String.format("Name: %s" + System.lineSeparator() + "Energy: %s" + System.lineSeparator() +
                "Instruments: %d not broken left", name, energy, instruments.size() - getBrokenInstruments());
    }

    public long getBrokenInstruments () {
        return instruments.stream().filter(Instrument::isBroken).count();
    }
}
