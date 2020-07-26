package com.school.enrollment.services;

import com.school.enrollment.entities.SchoolClassEntity;
import com.school.enrollment.entities.StudentEntity;
import com.school.enrollment.models.Student;
import com.school.enrollment.models.StudentUpdate;
import com.school.enrollment.repositories.SchoolClassRepository;
import com.school.enrollment.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
@AllArgsConstructor
public class EnrollmentService {
    private StudentRepository studentRepository;
    private SchoolClassRepository schoolClassRepository;

    public void addStudentRecord(@NonNull final Student student) throws StudentExistsException {
        boolean exists = studentRepository.existsById(student.getId());
        if (exists) {
            throw new StudentExistsException(student.getId());
        }
        val schoolClass = getSchoolClassOrCreate(student.getSchoolClass());
        val studentEntity = new StudentEntity(student.getId(), student.getFirstName(), student.getLastName(), student.getNationality(), schoolClass);
        studentRepository.save(studentEntity);
    }

    public void updateStudentRecord(@NonNull final StudentUpdate student) throws StudentNotFoundException {
        val studentEntity = getStudentRecord(student.getId());

        if (StringUtils.isNotBlank(student.getSchoolClass())) {
            val schoolClass = getSchoolClassOrCreate(student.getSchoolClass());
            studentEntity.setSchoolClass(schoolClass);
        }
        if (StringUtils.isNotBlank(student.getLastName())) {
            studentEntity.setLastName(student.getLastName());
        }
        if (StringUtils.isNotBlank(student.getFirstName())) {
            studentEntity.setFirstName(student.getFirstName());
        }
        if (StringUtils.isNotBlank(student.getNationality())) {
            studentEntity.setNationality(student.getNationality());
        }

        studentRepository.save(studentEntity);
    }

    public void deleteStudentRecord(final long studentId) throws StudentNotFoundException {
        try {
            studentRepository.deleteById(studentId);
        } catch (final EmptyResultDataAccessException e) {
            throw new StudentNotFoundException(studentId);
        }
    }

    public StudentEntity getStudentRecord(final long studentId) throws StudentNotFoundException {
        return studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException(studentId));
    }

    private SchoolClassEntity getSchoolClassOrCreate(final String classname) {
        return schoolClassRepository
                .findByName(classname)
                .orElseGet(() -> {
                    val newSchoolClass = new SchoolClassEntity();
                    newSchoolClass.setName(classname);
                    return newSchoolClass;
                });
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    public static class StudentExistsException extends Exception {
        StudentExistsException(final Long studentId) {
            super(String.format("Student %s already exists", studentId.toString()));
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public static class StudentNotFoundException extends Exception {
        StudentNotFoundException(final Long studentId) {
            super(String.format("Student %s already exists", studentId.toString()));
        }
    }
}
