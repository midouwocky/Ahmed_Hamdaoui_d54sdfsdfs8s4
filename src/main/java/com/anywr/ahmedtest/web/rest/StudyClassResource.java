package com.anywr.ahmedtest.web.rest;

import com.anywr.ahmedtest.domain.StudyClass;
import com.anywr.ahmedtest.management.PaginationUtil;
import com.anywr.ahmedtest.repository.StudyClassRepository;
import com.anywr.ahmedtest.service.StudyClassService;
import com.anywr.ahmedtest.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link com.anywr.ahmedtest.domain.StudyClass}.
 */
@RestController
@RequestMapping("/api")
public class StudyClassResource {

	private final Logger log = LoggerFactory.getLogger(StudyClassResource.class);

	private final StudyClassService studyClassService;

	private final StudyClassRepository studyClassRepository;

	public StudyClassResource(StudyClassService studyClassService, StudyClassRepository studyClassRepository) {
		this.studyClassService = studyClassService;
		this.studyClassRepository = studyClassRepository;
	}

	/**
	 * {@code POST  /study-classes} : Create a new studyClass.
	 *
	 * @param studyClassDTO the studyClassDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new studyClassDTO, or with status {@code 400 (Bad Request)}
	 *         if the studyClass has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/study-classes")
	public ResponseEntity<StudyClass> createStudyClass(@Valid @RequestBody StudyClass studyClassDTO)
			throws URISyntaxException {
		log.debug("REST request to save StudyClass : {}", studyClassDTO);
		if (studyClassDTO.getId() != null) {
			throw new BadRequestAlertException("A new studyClass cannot already have an ID");
		}
		StudyClass result = studyClassService.save(studyClassDTO);
		return ResponseEntity.created(new URI("/api/study-classes/" + result.getId())).body(result);
	}

	/**
	 * {@code PUT  /study-classes/:id} : Updates an existing studyClass.
	 *
	 * @param id            the id of the studyClassDTO to save.
	 * @param studyClassDTO the studyClassDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated studyClassDTO, or with status {@code 400 (Bad Request)}
	 *         if the studyClassDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the studyClassDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/study-classes/{id}")
	public ResponseEntity<StudyClass> updateStudyClass(@PathVariable(value = "id", required = false) final Long id,
			@Valid @RequestBody StudyClass studyClassDTO) throws URISyntaxException {
		log.debug("REST request to update StudyClass : {}, {}", id, studyClassDTO);
		if (studyClassDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id");
		}
		if (!Objects.equals(id, studyClassDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID");
		}

		if (!studyClassRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found");
		}

		StudyClass result = studyClassService.update(studyClassDTO);
		return ResponseEntity.ok().body(result);
	}

	/**
	 * {@code GET  /study-classes} : get all the studyClasses.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of studyClasses in body.
	 */
	@GetMapping("/study-classes")
	public ResponseEntity<List<StudyClass>> getAllStudyClasses(Pageable pageable) {
		log.debug("REST request to get a page of StudyClasses");
		Page<StudyClass> page = studyClassService.findAll(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /study-classes/:id} : get the "id" studyClass.
	 *
	 * @param id the id of the studyClassDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the studyClassDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/study-classes/{id}")
	public ResponseEntity<StudyClass> getStudyClass(@PathVariable Long id) {
		log.debug("REST request to get StudyClass : {}", id);
		Optional<StudyClass> studyClassDTO = studyClassService.findOne(id);
		return studyClassDTO.map(studyClass -> ResponseEntity.ok().body(studyClass))
				.orElseThrow(() -> new BadRequestAlertException("study class not found"));
	}

	/**
	 * {@code DELETE  /study-classes/:id} : delete the "id" studyClass.
	 *
	 * @param id the id of the studyClassDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/study-classes/{id}")
	public ResponseEntity<Void> deleteStudyClass(@PathVariable Long id) {
		log.debug("REST request to delete StudyClass : {}", id);
		studyClassService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
