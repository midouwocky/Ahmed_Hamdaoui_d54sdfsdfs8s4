package com.anywr.ahmedtest.service;

import com.anywr.ahmedtest.domain.Teacher;
import com.anywr.ahmedtest.repository.TeacherRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Teacher}.
 */
@Service
@Transactional
public class TeacherService {

    private final Logger log = LoggerFactory.getLogger(TeacherService.class);

    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    /**
     * Save a teacher.
     *
     * @param teacher the entity to save.
     * @return the persisted entity.
     */
    public Teacher save(Teacher teacher) {
        log.debug("Request to save Teacher : {}", teacher);
        return teacherRepository.save(teacher);
    }

    /**
     * Update a teacher.
     *
     * @param teacher the entity to save.
     * @return the persisted entity.
     */
    public Teacher update(Teacher teacher) {
        log.debug("Request to update Teacher : {}", teacher);
        return teacherRepository.save(teacher);
    }

    /**
     * Partially update a teacher.
     *
     * @param teacher the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Teacher> partialUpdate(Teacher teacher) {
        log.debug("Request to partially update Teacher : {}", teacher);

        return teacherRepository
            .findById(teacher.getId())
            .map(existingTeacher -> {
                if (teacher.getFirstName() != null) {
                    existingTeacher.setFirstName(teacher.getFirstName());
                }
                if (teacher.getLastName() != null) {
                    existingTeacher.setLastName(teacher.getLastName());
                }

                return existingTeacher;
            })
            .map(teacherRepository::save);
    }

    /**
     * Get all the teachers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Teacher> findAll(Pageable pageable) {
        log.debug("Request to get all Teachers");
        return teacherRepository.findAll(pageable);
    }

    /**
     * Get one teacher by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Teacher> findOne(Long id) {
        log.debug("Request to get Teacher : {}", id);
        return teacherRepository.findById(id);
    }

    /**
     * Delete the teacher by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Teacher : {}", id);
        teacherRepository.deleteById(id);
    }
    
    /**
     * Get one teacher by study class id.
     *
     * @param id the id of the entity StudyClass.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Teacher> findOneByStudyClassId(Long id) {
        log.debug("Request to get Teacher of an existing class : {}", id);
        return teacherRepository.findOneByStudyClassId(id);
    }
}
