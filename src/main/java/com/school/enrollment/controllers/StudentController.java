package com.school.enrollment.controllers;

import com.school.enrollment.models.Student;
import com.school.enrollment.models.StudentUpdate;
import com.school.enrollment.repositories.StudentRepository;
import com.school.enrollment.services.EnrollmentService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/students")
public class StudentController {
	private final EnrollmentService enrollmentService;

	@GetMapping("{studentId}")
	public Student get(@PathVariable final @NonNull Long studentId) throws EnrollmentService.StudentNotFoundException {
		val studentEntity = enrollmentService.getStudentRecord(studentId);
		return new Student(studentEntity.getId(), studentEntity.getFirstName(), studentEntity.getLastName(), studentEntity.getNationality(), studentEntity.getSchoolClass().getName());
	}

	@PostMapping
	@ApiOperation(
			value = "Add new student to class", notes = "Add new student"
	)
	public ResponseEntity<String> post(@Valid @RequestBody final @NonNull Student student) throws ResponseStatusException, EnrollmentService.StudentExistsException {
		enrollmentService.addStudentRecord(student);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping
	@ApiOperation(
			value = "Update student record", notes = "Update student record"
	)
	public void update(@Valid @RequestBody final @NonNull StudentUpdate student) throws ResponseStatusException, EnrollmentService.StudentNotFoundException {
		enrollmentService.updateStudentRecord(student);
	}

	@DeleteMapping("{studentId}")
	public void delete(@PathVariable final @NonNull Long studentId) throws EnrollmentService.StudentNotFoundException {
		enrollmentService.deleteStudentRecord(studentId);
	}


}