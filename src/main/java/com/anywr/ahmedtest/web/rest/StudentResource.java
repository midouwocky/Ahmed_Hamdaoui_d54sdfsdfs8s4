package com.anywr.ahmedtest.web.rest;

import com.anywr.ahmedtest.domain.Student;
import com.anywr.ahmedtest.domain.StudyClass;
import com.anywr.ahmedtest.management.PaginationUtil;
import com.anywr.ahmedtest.repository.StudentRepository;
import com.anywr.ahmedtest.service.StudentService;
import com.anywr.ahmedtest.service.StudyClassService;
import com.anywr.ahmedtest.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link com.anywr.ahmedtest.domain.Student}.
 */
@RestController
@RequestMapping("/api")
public class StudentResource {

	private final Logger log = LoggerFactory.getLogger(StudentResource.class);

	private final StudentService studentService;

	private final StudentRepository studentRepository;

	private final StudyClassService studyClassService;

	public StudentResource(StudentService studentService, StudentRepository studentRepository,
			StudyClassService studyClassService) {
		this.studentService = studentService;
		this.studentRepository = studentRepository;
		this.studyClassService = studyClassService;
	}

	/**
	 * {@code POST  /students} : Create a new student.
	 *
	 * @param studentDTO the studentDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new studentDTO, or with status {@code 400 (Bad Request)} if
	 *         the student has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/students")
	public ResponseEntity<Student> createStudent(@RequestBody @jakarta.validation.Valid Student student)
			throws URISyntaxException {
		log.debug("REST request to save Student : {}", student);
		if (student.getId() != null) {
			throw new BadRequestAlertException("A new student cannot already have an ID");
		}
		this.checkStudyClassExistance(student);
		Student result = studentService.save(student);
		return ResponseEntity.created(new URI("/api/students/" + result.getId())).body(result);
	}

	/**
	 * {@code PUT  /students/:id} : Updates an existing student.
	 *
	 * @param id         the id of the studentDTO to save.
	 * @param studentDTO the studentDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated studentDTO, or with status {@code 400 (Bad Request)} if
	 *         the studentDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the studentDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/students/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable(value = "id", required = false) final Long id,
			@Valid @RequestBody Student studentDTO) throws URISyntaxException {
		log.debug("REST request to update Student : {}, {}", id, studentDTO);
		if (studentDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id");
		}
		if (!Objects.equals(id, studentDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID");
		}

		if (!studentRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found");
		}

		this.checkStudyClassExistance(studentDTO);

		Student result = studentService.update(studentDTO);
		return ResponseEntity.ok().body(result);
	}

	/**
	 * {@code PATCH  /students/:id} : Partial updates given fields of an existing
	 * student, field will ignore if it is null
	 *
	 * @param id         the id of the studentDTO to save.
	 * @param studentDTO the studentDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated studentDTO, or with status {@code 400 (Bad Request)} if
	 *         the studentDTO is not valid, or with status {@code 404 (Not Found)}
	 *         if the studentDTO is not found, or with status
	 *         {@code 500 (Internal Server Error)} if the studentDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/students/{id}", consumes = { "application/json", "application/merge-patch+json" })
	public ResponseEntity<Student> partialUpdateStudent(@PathVariable(value = "id", required = false) final Long id,
			@NotNull @RequestBody Student studentDTO) throws URISyntaxException {
		log.debug("REST request to partial update Student partially : {}, {}", id, studentDTO);
		if (studentDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id");
		}
		if (!Objects.equals(id, studentDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID");
		}

		if (!studentRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found");
		}

		Optional<Student> result = studentService.partialUpdate(studentDTO);
		return result.map(studentRes -> ResponseEntity.ok().body(studentRes))
				.orElseThrow(() -> new BadRequestAlertException("student not found"));
	}

	/**
	 * {@code GET  /students} : get all the students.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of students in body.
	 */
	@GetMapping("/students")
	public ResponseEntity<List<Student>> getAllStudents(Pageable pageable,
			@RequestParam(value = "studyClassName", required = false) String studyClassName,
			@RequestParam(value = "teacherFullName", required = false) String teacherFullName) {
		log.debug("REST request to get a page of Students");
		Page<Student> page = studentService.findAll(pageable, studyClassName, teacherFullName);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /students/:id} : get the "id" student.
	 *
	 * @param id the id of the studentDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the studentDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/students/{id}")
	public ResponseEntity<Student> getStudent(@PathVariable Long id) {
		log.debug("REST request to get Student : {}", id);
		Optional<Student> studentDTO = studentService.findOne(id);
		return studentDTO.map(studentRes -> ResponseEntity.ok().body(studentRes))
				.orElseThrow(() -> new BadRequestAlertException("student not found"));
	}

	/**
	 * {@code DELETE  /students/:id} : delete the "id" student.
	 *
	 * @param id the id of the studentDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/students/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
		log.debug("REST request to delete Student : {}", id);
		studentService.delete(id);
		return ResponseEntity.noContent().build();
	}

	private void checkStudyClassExistance(Student student) {
		Optional<StudyClass> studyClass = this.studyClassService.findOne(student.getStudyClass().getId());
		if (studyClass.isEmpty()) {
			throw new BadRequestAlertException("Study Class doesn't exist");
		}
	}
}
