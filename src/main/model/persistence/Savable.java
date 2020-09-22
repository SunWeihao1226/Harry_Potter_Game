package model.persistence;

import model.SavingSlot;

public interface Savable {

    void save(SavingSlot slot);
}
