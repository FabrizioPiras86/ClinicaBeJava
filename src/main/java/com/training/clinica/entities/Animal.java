package com.training.clinica.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Animal")
public class Animal {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        private Long id;

        @Size(max = 100)
        @NotNull
        @Column(name = "name", nullable = false, length = 100)
        private String name;

        @Size(max = 100)
        @NotNull
        @Column(name = "species", nullable = false, length = 100)
        private String species;

        @Size(max = 100)
        @Column(name = "mastername", nullable = false, length = 100)
        private String mastername;

        @OneToMany(mappedBy = "animal")
        private Set<Visit> visits = new LinkedHashSet<>();
}
