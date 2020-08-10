package com.anz.platform.mysql;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Entity
public class Student implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  private String id;

  private String name;
  private String age;
  private LocalDateTime creationTime;
}
