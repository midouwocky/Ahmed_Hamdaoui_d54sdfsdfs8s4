package com.anywr.ahmedtest.repository;

import com.anywr.ahmedtest.domain.Teacher;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Teacher entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
	Optional<Teacher> findOneByStudyClassId(Long Id);
}
