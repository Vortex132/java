package org.example;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class MageRepository {
    private List<Mage> collection;

    public MageRepository() {
        this.collection = new ArrayList<>();
    }
    public Optional<Mage> find(String name) {
        Mage found_mage = null;
        for (Mage mage : collection) {
            if (mage.getName().equals(name)) {
                found_mage = mage;
                break;
            }
        }
        return Optional.ofNullable(found_mage);
    }
    public void delete(String name) {
        for (Mage mage : collection) {
            if (mage.getName().equals(name)) {
                collection.remove(mage);
                return;
            }
        }
        throw new IllegalArgumentException("Mage not found");
    }
    public void save(Mage mage) {
        for (Mage existing_mage : collection) {
            if (existing_mage.getName().equals(mage.getName())) {
                throw new IllegalArgumentException("Mage already exists");
            }
        }
        collection.add(mage);
    }

    public List<Mage> getCollection() {
        return collection;
    }
}
