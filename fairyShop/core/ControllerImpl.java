package fairyShop.core;

import fairyShop.common.ConstantMessages;
import fairyShop.common.ExceptionMessages;
import fairyShop.models.*;
import fairyShop.repositories.HelperRepository;
import fairyShop.repositories.PresentRepository;

import static fairyShop.common.ConstantMessages.*;
import static fairyShop.common.ExceptionMessages.*;

public class ControllerImpl implements Controller{

    private HelperRepository helperRepository;
    private PresentRepository presentRepository;

    public ControllerImpl() {
        this.helperRepository = new HelperRepository();
        this.presentRepository = new PresentRepository();
    }

    @Override
    public String addHelper(String type, String helperName) {
        BaseHelper helper = null;

        switch (type) {
            case "Happy":
                helper = new Happy(helperName);
                break;
            case "Sleepy":
                helper = new Sleepy(helperName);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.HELPER_TYPE_DOESNT_EXIST);
        }

        helperRepository.add(helper);

        return String.format(ConstantMessages.ADDED_HELPER, type, helperName);

    }

    @Override
    public String addInstrumentToHelper(String helperName, int power) {
        Helper helper = helperRepository.findByName(helperName);

        if (helper == null) {
            throw new IllegalArgumentException(HELPER_DOESNT_EXIST);
        }
        Instrument instrument = new InstrumentImpl(power);
        helper.addInstrument(instrument);

        return String.format(SUCCESSFULLY_ADDED_INSTRUMENT_TO_HELPER, power, helperName);

    }

    @Override
    public String addPresent(String presentName, int energyRequired) {
        Present present = new PresentImpl(presentName, energyRequired);
        presentRepository.add(present);

        return String.format(SUCCESSFULLY_ADDED_PRESENT, presentName);
    }

    @Override
    public String craftPresent(String presentName) {
        BaseHelper helper = helperRepository.getModels().stream().filter(h -> h.getEnergy() > 50).findFirst().orElse(null);
        if (helper == null) {
            throw new IllegalArgumentException(NO_HELPER_READY);
        }
        Present present = presentRepository.findByName(presentName);
        ShopImpl shop = new ShopImpl();
        shop.craft(present, helper);

        String ready = present.isDone() ? "done" : "not done";
        return String.format(PRESENT_DONE + COUNT_BROKEN_INSTRUMENTS, presentName,ready, helper.getBrokenInstruments());
    }

    @Override
    public String report() {
        return presentRepository.getModels().stream().filter(Present::isDone).count() + " presents are done!" + System.lineSeparator() +
                "Helpers info:" + ((helperRepository.getModels().size() == 0) ? "" : System.lineSeparator() +
                helperRepository.toString());
    }
}
