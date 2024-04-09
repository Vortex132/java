package org.example;

public class MageController {
    private MageRepository repository;
    public MageController(MageRepository repository) {
        this.repository = repository;
    }
    public String find(String name) {
        if (repository.find(name).isPresent()) {
            return repository.find(name).get().toString();
        }
        return "not found";
    }
    public String delete(String name) {
        if (repository.find(name).isPresent()) {
            return "done";
        }
        return "not found";
    }
    public String save(String name, String level) {
        if (repository.find(name).isPresent()) {
            return "bad request";
        }
        //repository.getCollection().add(new Mage(name, Integer.parseInt(level)));
        return "done";
    }
}
