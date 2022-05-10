package com.example.file.Bin;

import com.example.file.Model.Filesours;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Filesours_bin extends JpaRepository<Filesours,Integer> {
    Optional<Filesours> findById(Integer id);
}
