package com.anywr.ahmedtest.web.rest;

import com.anywr.ahmedtest.domain.StudyClass;
import com.anywr.ahmedtest.domain.Teacher;
import com.anywr.ahmedtest.management.PaginationUtil;
import com.anywr.ahmedtest.repository.TeacherRepository;
import com.anywr.ahmedtest.service.StudyClassService;
import com.anywr.ahmedtest.service.TeacherService;
import com.anywr.ahmedtest.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link com.anywr.ahmedtest.domain.Teacher}.
 */
@RestController
@RequestMapping("/api")
public class TeacherResource {

	private final Logger log = LoggerFactory.getLogger(TeacherResource.class);

	private final TeacherService teacherService;

	private final TeacherRepository teacherRepository;
	
	private final StudyClassService studyClassService;

	public TeacherResource(TeacherService teacherService, TeacherRepository teacherRepository, StudyClassService studyClassService) {
		this.teacherService = teacherService;
		this.teacherRepository = teacherRepository;
		this.studyClassService = studyClassService;
	}

	/**
	 * {@code POST  /teachers} : Create a new teacher.
	 *
	 * @param teacherDTO the teacherDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new teacherDTO, or with status {@code 400 (Bad Request)} if
	 *         the teacher has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/teachers")
	public ResponseEntity<Teacher> createTeacher(@Valid @RequestBody Teacher teacherDTO)
			throws URISyntaxException {
		log.debug("REST request to save Teacher : {}", teacherDTO);
		if (teacherDTO.getId() != null) {
			throw new BadRequestAlertException("A new teacher cannot already have an ID");
		}
		
		this.checkStudyClassExistance(teacherDTO);
		
		Teacher result = teacherService.save(teacherDTO);
		return ResponseEntity.created(new URI("/api/teachers/" + result.getId())).body(result);
	}

	/**
	 * {@code PUT  /teachers/:id} : Updates an existing teacher.
	 *
	 * @param id         the id of the teacherDTO to save.
	 * @param teacherDTO the teacherDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated teacherDTO, or with status {@code 400 (Bad Request)} if
	 *         the teacherDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the teacherDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/teachers/{id}")
	public ResponseEntity<Teacher> updateTeacher(@PathVariable(value = "id", required = false) final Long id,
			@Valid @RequestBody Teacher teacherDTO) throws URISyntaxException {
		log.debug("REST request to update Teacher : {}, {}", id, teacherDTO);
		if (teacherDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id");
		}
		if (!Objects.equals(id, teacherDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID");
		}

		if (!teacherRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found");
		}
		
		this.checkStudyClassExistance(teacherDTO);

		Teacher result = teacherService.update(teacherDTO);
		return ResponseEntity.ok().body(result);
	}

	/**
	 * {@code PATCH  /teachers/:id} : Partial updates given fields of an existing
	 * teacher, field will ignore if it is null
	 *
	 * @param id         the id of the teacherDTO to save.
	 * @param teacherDTO the teacherDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated teacherDTO, or with status {@code 400 (Bad Request)} if
	 *         the teacherDTO is not valid, or with status {@code 404 (Not Found)}
	 *         if the teacherDTO is not found, or with status
	 *         {@code 500 (Internal Server Error)} if the teacherDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/teachers/{id}", consumes = { "application/json", "application/merge-patch+json" })
	public ResponseEntity<Teacher> partialUpdateTeacher(@PathVariable(value = "id", required = false) final Long id,
			@NotNull @RequestBody Teacher teacherDTO) throws URISyntaxException {
		log.debug("REST request to partial update Teacher partially : {}, {}", id, teacherDTO);
		if (teacherDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id");
		}
		if (!Objects.equals(id, teacherDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID");
		}

		if (!teacherRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found");
		}

		Optional<Teacher> result = teacherService.partialUpdate(teacherDTO);

		return result.map(teacher -> ResponseEntity.ok().body(teacher))
				.orElseThrow(() -> new BadRequestAlertException("teacher not found"));
	}

	/**
	 * {@code GET  /teachers} : get all the teachers.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of teachers in body.
	 */
	@GetMapping("/teachers")
	public ResponseEntity<List<Teacher>> getAllTeachers(Pageable pageable) {
		log.debug("REST request to get a page of Teachers");
		Page<Teacher> page = teacherService.findAll(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /teachers/:id} : get the "id" teacher.
	 *
	 * @param id the id of the teacherDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the teacherDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/teachers/{id}")
	public ResponseEntity<Teacher> getTeacher(@PathVariable Long id) {
		log.debug("REST request to get Teacher : {}", id);
		Optional<Teacher> teacherDTO = teacherService.findOne(id);
		return teacherDTO.map(teacher -> ResponseEntity.ok().body(teacher))
				.orElseThrow(() -> new BadRequestAlertException("teacher not found"));
	}

	/**
	 * {@code DELETE  /teachers/:id} : delete the "id" teacher.
	 *
	 * @param id the id of the teacherDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/teachers/{id}")
	public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
		log.debug("REST request to delete Teacher : {}", id);
		teacherService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
    private void checkStudyClassExistance(Teacher teacher) {
        Optional<StudyClass> studyClass = this.studyClassService.findOne(teacher.getStudyClass().getId());
        if (studyClass.isEmpty()) {
        	throw new BadRequestAlertException("Study Class doesn't exist");
        }
        // check if study class belong to another teacher
        Optional<Teacher> oldTeacher = this.teacherService.findOneByStudyClassId(teacher.getStudyClass().getId());
        
        if (oldTeacher.isPresent()) {
        	throw new BadRequestAlertException("Study Class has already a teacher");
        }
    }
}
