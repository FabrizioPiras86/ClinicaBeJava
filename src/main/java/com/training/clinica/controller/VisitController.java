package com.training.clinica.controller;

import com.training.clinica.controller.service.AnimalService;
import com.training.clinica.controller.service.VisitService;
import com.training.clinica.controller.service.dto.VisitDto;
import com.training.clinica.entities.Visit;
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
@RequestMapping("/visit")
public class VisitController {

    @Autowired
    private VisitService visitService;

    @Autowired
    private AnimalService animalService;

    @GetMapping("/")
    public String index(Model model) {
        List<Visit> visits = visitService.getAllVisits();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<VisitDto> visitDtos = visits.stream().map(visit -> new VisitDto(
                visit.getId(),
                visit.getDescription(),
                visit.getDov().format(formatter),
                visit.getAnimal().getName()
        )).collect(Collectors.toList());
        model.addAttribute("visits", visitDtos);
        return "visit/index";
    }

    @GetMapping("/create")
    public String showCreateVisitForm(Model model) {
        model.addAttribute("visit", new Visit());
        model.addAttribute("animals", animalService.getAllAnimals());
        return "visit/create";
    }

    @PostMapping("/create")
    public String createVisit(@ModelAttribute Visit visit) {
        visitService.saveVisit(visit);
        return "redirect:/visit/";
    }

    @GetMapping("/update/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Visit visit = visitService.getVisitById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid visit Id:" + id));

        // Formattazione della data
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = visit.getDov().format(formatter);

        model.addAttribute("visit", visit);
        model.addAttribute("formattedDate", formattedDate);
        return "visit/update";
    }

    @PostMapping("/update/{id}")
    public String updateVisit(@PathVariable Long id, @Valid @ModelAttribute Visit visit, BindingResult result) {
        if (result.hasErrors()) {
            visit.setId(id);
            return "visit/update";
        }
        visitService.updateVisit(id, visit);
        return "redirect:/visit/";
    }

    @GetMapping("/delete/{id}")
    public String deleteVisit(@PathVariable Long id) {
        try {
            visitService.deleteVisit(id);
        } catch (Exception e) {
            return "redirect:/visit/";
        }
        return "redirect:/visit/";
    }
}
