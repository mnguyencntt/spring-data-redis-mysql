package com.anz.platform.redis;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "student", timeToLive = 900L)
public class RedisStudent implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  private String id;

  private String name;
  private String age;
  private LocalDateTime creationTime;
}
