package com.training.clinica.controller;

import com.training.clinica.controller.service.AnimalService;
import com.training.clinica.controller.service.VisitService;
import com.training.clinica.entities.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

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
        model.addAttribute("visits", visits);
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
        System.out.println(visit);
        visitService.saveVisit(visit);
        return "redirect:/visit/";
    }

    @GetMapping("/update/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Visit visit = visitService.getVisitById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid visit Id:" + id));
        model.addAttribute("visit", visit);
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
