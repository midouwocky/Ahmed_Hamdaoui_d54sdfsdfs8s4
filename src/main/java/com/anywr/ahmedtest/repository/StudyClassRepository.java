package com.anywr.ahmedtest.repository;

import com.anywr.ahmedtest.domain.StudyClass;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StudyClass entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudyClassRepository extends JpaRepository<StudyClass, Long> {}
