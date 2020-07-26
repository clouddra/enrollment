package com.school.enrollment.repositories;

import com.school.enrollment.entities.SchoolClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClassEntity, Long> {

    Optional<SchoolClassEntity> findByName(final String name);
}
