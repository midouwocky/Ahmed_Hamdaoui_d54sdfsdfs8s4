package com.anywr.ahmedtest.repository;

import com.anywr.ahmedtest.domain.Student;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Student entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	@Query("SELECT s FROM Student s JOIN s.studyClass sc JOIN sc.teacher t WHERE sc.name = :studyClassName AND CONCAT(t.firstName, ' ', t.lastName) = :teacherFullName")
	Page<Student> findByStudyClassNameAndTeacherFullName(@Param("studyClassName") String studyClassName,
			@Param("teacherFullName") String teacherFullName, Pageable pageable);
	
	Page<Student> findByStudyClassName(@Param("studyClassName") String studyClassName, Pageable pageable);
	@Query("SELECT s FROM Student s JOIN s.studyClass sc JOIN sc.teacher t WHERE CONCAT(t.firstName, ' ', t.lastName) = :teacherFullName")
	Page<Student> findByTeacherFullName(@Param("teacherFullName") String teacherFullName, Pageable pageable);
}
