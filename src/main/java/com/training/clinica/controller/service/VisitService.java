package com.training.clinica.controller.service;

import com.training.clinica.entities.Visit;
import com.training.clinica.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisitService {

    @Autowired
    private VisitRepository visitRepository;

    public Visit saveVisit(Visit visit) {
        return visitRepository.save(visit);
    }

    public void deleteVisit(Long id) throws Exception {
        Optional<Visit> visitOptional = visitRepository.findById(id);
        if (visitOptional.isPresent()) {
            Visit visit = visitOptional.get();
            visitRepository.delete(visit);
        }
    }

    public Visit updateVisit(Long id, Visit updatedVisit) {
        Optional<Visit> existingVisit = visitRepository.findById(id);
        if (existingVisit.isPresent()) {
            Visit visit = existingVisit.get();
            visit.setDescription(updatedVisit.getDescription());
            visit.setDov(updatedVisit.getDov());
            visit.setAnimal(updatedVisit.getAnimal());
            return visitRepository.save(visit);
        }
        return null;
    }

    public List<Visit> getAllVisits() {
        return visitRepository.findAll();
    }

    public Optional<Visit> getVisitById(Long id) {
        return visitRepository.findById(id);
    }
}
