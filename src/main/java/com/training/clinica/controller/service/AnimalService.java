package com.training.clinica.controller.service;

import com.training.clinica.entities.Animal;
import com.training.clinica.repository.AnimalRepository;
import com.training.clinica.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private VisitRepository visitRepository;

    public Animal saveAnimal(Animal animal) {
        return animalRepository.save(animal);
    }


    public void deleteAnimal(Long id) {
        Optional<Animal> animalOptional = animalRepository.findById(id);
        animalOptional.ifPresent(animal -> {
            visitRepository.deleteByAnimal(animal);
            animalRepository.delete(animal);
        });
    }

    public Animal updateAnimal(Long id, Animal updatedAnimal) {
        Optional<Animal> existingAnimal = animalRepository.findById(id);

            Animal animal = existingAnimal.get();
            animal.setName(updatedAnimal.getName());
            animal.setSpecies(updatedAnimal.getSpecies());
            animal.setMastername(updatedAnimal.getMastername());
            return animalRepository.save(animal);

    }

    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    public Optional<Animal> getAnimalById(Long id) {
        return animalRepository.findById(id);
    }
}
