package com.pet.example.cadastropet.repository;

import com.pet.example.cadastropet.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
