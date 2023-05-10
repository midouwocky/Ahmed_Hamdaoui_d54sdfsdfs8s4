package com.anywr.ahmedtest.service;

import com.anywr.ahmedtest.domain.StudyClass;
import com.anywr.ahmedtest.repository.StudyClassRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StudyClass}.
 */
@Service
@Transactional
public class StudyClassService {

    private final Logger log = LoggerFactory.getLogger(StudyClassService.class);

    private final StudyClassRepository studyClassRepository;

    public StudyClassService(StudyClassRepository studyClassRepository) {
        this.studyClassRepository = studyClassRepository;
    }

    /**
     * Save a studyClass.
     *
     * @param studyClass the entity to save.
     * @return the persisted entity.
     */
    public StudyClass save(StudyClass studyClass) {
        log.debug("Request to save StudyClass : {}", studyClass);
        return studyClassRepository.save(studyClass);
    }

    /**
     * Update a studyClass.
     *
     * @param studyClass the entity to save.
     * @return the persisted entity.
     */
    public StudyClass update(StudyClass studyClass) {
        log.debug("Request to update StudyClass : {}", studyClass);
        return studyClassRepository.save(studyClass);
    }

    /**
     * Partially update a studyClass.
     *
     * @param studyClass the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<StudyClass> partialUpdate(StudyClass studyClass) {
        log.debug("Request to partially update StudyClass : {}", studyClass);

        return studyClassRepository
            .findById(studyClass.getId())
            .map(existingStudyClass -> {
                if (studyClass.getName() != null) {
                    existingStudyClass.setName(studyClass.getName());
                }

                return existingStudyClass;
            })
            .map(studyClassRepository::save);
    }

    /**
     * Get all the studyClasses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StudyClass> findAll(Pageable pageable) {
        log.debug("Request to get all StudyClasses");
        return studyClassRepository.findAll(pageable);
    }

    /**
     * Get one studyClass by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StudyClass> findOne(Long id) {
        log.debug("Request to get StudyClass : {}", id);
        return studyClassRepository.findById(id);
    }

    /**
     * Delete the studyClass by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StudyClass : {}", id);
        studyClassRepository.deleteById(id);
    }
}
