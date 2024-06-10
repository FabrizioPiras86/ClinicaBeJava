package com.training.clinica.controller.service.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class VisitDto {
    private Long id;
    private String description;
    private String formattedDov;
    private String animalName;}
