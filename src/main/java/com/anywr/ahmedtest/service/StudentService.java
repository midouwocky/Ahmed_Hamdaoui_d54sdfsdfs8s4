package com.anywr.ahmedtest.service;

import com.anywr.ahmedtest.domain.Student;
import com.anywr.ahmedtest.repository.StudentRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Student}.
 */
@Service
@Transactional
public class StudentService {

    private final Logger log = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Save a student.
     *
     * @param student the entity to save.
     * @return the persisted entity.
     */
    public Student save(Student student) {
        log.debug("Request to save Student : {}", student);
        return studentRepository.save(student);
    }

    /**
     * Update a student.
     *
     * @param student the entity to save.
     * @return the persisted entity.
     */
    public Student update(Student student) {
        log.debug("Request to update Student : {}", student);
        return studentRepository.save(student);
    }

    /**
     * Partially update a student.
     *
     * @param student the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Student> partialUpdate(Student student) {
        log.debug("Request to partially update Student : {}", student);

        return studentRepository
            .findById(student.getId())
            .map(existingStudent -> {
                if (student.getFirstName() != null) {
                    existingStudent.setFirstName(student.getFirstName());
                }
                if (student.getLastName() != null) {
                    existingStudent.setLastName(student.getLastName());
                }

                return existingStudent;
            })
            .map(studentRepository::save);
    }

    /**
     * Get all the students.
     *
     * @param pageable the pagination information.
     * @param studyClassName StudyClass name
     * @param teacherFullName Teacher full name 'firsName lastName'
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Student> findAll(Pageable pageable, String studyClassName, String teacherFullName) {
        log.debug("Request to get all Students");
        if (studyClassName != null && teacherFullName != null) {
        	return studentRepository.findByStudyClassNameAndTeacherFullName(studyClassName, teacherFullName, pageable);
        } else if (studyClassName != null) {
        	return studentRepository.findByStudyClassName(studyClassName, pageable);
        } else if (teacherFullName != null) {
        	return studentRepository.findByTeacherFullName(teacherFullName, pageable);
        }
        
        return studentRepository.findAll(pageable);
    }

    /**
     * Get one student by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Student> findOne(Long id) {
        log.debug("Request to get Student : {}", id);
        return studentRepository.findById(id);
    }

    /**
     * Delete the student by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Student : {}", id);
        studentRepository.deleteById(id);
    }
}
