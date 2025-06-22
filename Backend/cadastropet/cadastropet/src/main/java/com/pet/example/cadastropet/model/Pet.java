package com.pet.example.cadastropet.model;

import com.pet.example.cadastropet.enums.Especie;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "pets")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pet {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;


    @Column(nullable = false)
    private String nome;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Especie especie;


    private LocalDate dataNascimento;


    @Column(nullable = false)
    private String raca;


    @Column(nullable = false)
    private Double peso;


    @ManyToOne
    @JoinColumn(name = "dono_id", nullable = false)
    private Dono dono;
}
