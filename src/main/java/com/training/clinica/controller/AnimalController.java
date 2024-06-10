package com.training.clinica.controller;

import com.training.clinica.controller.service.AnimalService;
import com.training.clinica.entities.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/animal")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @GetMapping("/")
    public String listAnimals(Model model) {
        List<Animal> animals = animalService.getAllAnimals();
        model.addAttribute("animals", animals);
        return "animal/index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("animal", new Animal());
        return "animal/create";
    }

    @PostMapping("/create")
    public String createAnimal(@Valid @ModelAttribute Animal animal, BindingResult result) {
        animalService.saveAnimal(animal);
        return "redirect:/animal/";
    }
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Animal animal = animalService.getAnimalById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid animal Id:" + id));
        model.addAttribute("animal", animal);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<String> formattedDates = animal.getVisits().stream()
                .map(visit -> visit.getDov().format(formatter))
                .collect(Collectors.toList());
        model.addAttribute("formattedDates", formattedDates);
        return "animal/update";
    }

    @PostMapping("/update/{id}")
    public String updateAnimal(@PathVariable Long id, @ModelAttribute Animal updatedAnimal) {
        animalService.updateAnimal(id, updatedAnimal);
        return "redirect:/animal/";
    }


    @GetMapping("/delete/{id}")
    public String deleteAnimal(@PathVariable("id") Long id) {
        animalService.deleteAnimal(id);
        return "redirect:/animal/";
    }
}
