package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class MageControllerTest {

    private MageRepository repository;
    private MageController controller;

    @BeforeEach
    public void setup() {
        repository = Mockito.mock(MageRepository.class);
        controller = new MageController(repository);
        Mage mage = new Mage("Gandalf", 50);
        Mockito.when(repository.find("Gandalf")).thenReturn(Optional.of(mage));
    }

    @Test
    public void testFind() {
        //doReturn(mages).when(repository).getCollection();
        String result = controller.find("Gandalf");
        String expected = repository.find("Gandalf").get().toString();

        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testFindNotFound() {
        String result = controller.find("gfdgfd");

        assertThat(result).isEqualTo("not found");
    }

    @Test
    public void testDelete() {
        String result = controller.delete("Gandalf");

        assertThat(result).isEqualTo("done");
    }

    @Test
    public void testDeleteNotFound() {
        String result = controller.delete("gfdgfd");

        assertThat(result).isEqualTo("not found");
    }

    @Test
    public void testSave() {
        String result = controller.save("ddsgdgs", "100");

        assertThat(result).isEqualTo("done");
    }

    @Test
    public void testSaveAlreadyExists(){
        String result = controller.save("Gandalf", "50");

        assertThat(result).isEqualTo("bad request");
    }
}