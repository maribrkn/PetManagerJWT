package com.pet.example.cadastropet.repository;

import com.pet.example.cadastropet.model.Dono;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DonoRepository extends JpaRepository<Dono, Long> {
    Optional<Dono> findByEmail(String email);
}

