package com.anz.platform.mysql;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/mysql")
public class StudentController {
  @Autowired
  private StudentRepository studentRepository;

  @GetMapping("/student")
  public Student getStudent(final String studentId) {
    final Optional<Student> redisExisting = studentRepository.findById(studentId);
    if (redisExisting.isPresent()) {
      log.info("{}", redisExisting.get());
      return redisExisting.get();
    }
    log.info("{}", redisExisting);
    Student student = new Student();
    student.setId(studentId);
    student.setName("Minh");
    student.setAge("28");
    student.setCreationTime(LocalDateTime.now());
    return studentRepository.save(student);
  }

  @DeleteMapping("/student")
  public void deleteAllStudent() {
    studentRepository.deleteAll();
  }

  @Autowired
  private StudentService studentService;

  @GetMapping("/studentNew")
  public com.anz.platform.domain.Student getStudentNew(final String studentId) {
    try {
      com.anz.platform.domain.Student findById = studentService.findById(String.valueOf(studentId), com.anz.platform.domain.Student.class);
      if (findById != null) {
        return findById;
      }
      com.anz.platform.domain.Student student = new com.anz.platform.domain.Student();
      student.setId(studentId);
      student.setName("Minh");
      student.setAge("28");
      student.setCreationTime(Timestamp.valueOf(LocalDateTime.now()));
      Integer persist = studentService.persist(student);
      log.info("persist success {}", persist);
      return studentService.findById(String.valueOf(studentId), com.anz.platform.domain.Student.class);
    } catch (SQLException e) {
      log.error(e.getMessage());
      return null;
    }
  }
}
