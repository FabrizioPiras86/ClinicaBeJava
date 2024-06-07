package com.training.clinica.repository;

import com.training.clinica.entities.Animal;
import com.training.clinica.entities.Visit;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Transactional
    void deleteByAnimal(Animal animal);
}
