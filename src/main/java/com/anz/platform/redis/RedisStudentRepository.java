package com.anz.platform.redis;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RedisStudentRepository extends JpaRepository<RedisStudent, String> {

}
