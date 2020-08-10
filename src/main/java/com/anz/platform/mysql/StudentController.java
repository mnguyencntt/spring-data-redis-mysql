package com.anz.platform.mysql;

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
  public Student getRedisStudent(final Long studentId) {
    final Optional<Student> redisExisting = studentRepository.findById(studentId);
    if (redisExisting.isPresent()) {
      // Spring boot 2.3.2 will print out: RedisStudent(id=null, name=null, age=null, creationTime=null)
      // Spring boot 2.3.1 will print out: RedisStudent(id=12345, name=Minh, age=28, creationTime=2020-07-28T21:31:18.318)
      log.info("{}", redisExisting.get());
      return redisExisting.get();
    }
    // Spring boot 2.3.1 will print out: Optional.empty
    log.info("{}", redisExisting);
    Student student = new Student();
    student.setId(studentId);
    student.setName("Minh");
    student.setAge("28");
    student.setCreationTime(LocalDateTime.now());
    return studentRepository.save(student);
  }

  @DeleteMapping("/student")
  public void deleteAllRedisStudent() {
    studentRepository.deleteAll();
  }
}
