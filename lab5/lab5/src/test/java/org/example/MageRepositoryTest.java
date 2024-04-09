package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import java.util.Optional;

public class MageRepositoryTest {

    private MageRepository repository;

    @BeforeEach
    public void setup() {
        repository = new MageRepository();
        repository.save(new Mage("Gandalf", 100));
    }

    @Test
    public void testFind() {
        Optional<Mage> foundMage = repository.find("Gandalf");
        assertThat(foundMage).isPresent();
        assertThat(foundMage.get().getName()).isEqualTo("Gandalf");
    }

    @Test
    public void testFindNotFound() {
        Optional<Mage> foundMage = repository.find("Merlin");
        assertThat(foundMage).isNotPresent();
    }

    @Test
    public void testDelete() {
        repository.delete("Gandalf");
        Optional<Mage> foundMage = repository.find("Gandalf");
        assertThat(foundMage).isNotPresent();
    }

    @Test
    public void testDeleteNotFound() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> repository.delete("Merlin"))
                .withMessage("Mage not found");
    }

    @Test
    public void testSave() {
        Mage newMage = new Mage("Merlin", 150);
        repository.save(newMage);
        Optional<Mage> foundMage = repository.find("Merlin");
        assertThat(foundMage).isPresent();
        assertThat(foundMage.get().getName()).isEqualTo("Merlin");
    }

    @Test
    public void testSaveAlreadyExists() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> repository.save(new Mage("Gandalf", 200)))
                .withMessage("Mage already exists");
    }
}