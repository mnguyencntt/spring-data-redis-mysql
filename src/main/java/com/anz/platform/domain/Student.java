package com.anz.platform.domain;

import java.sql.Timestamp;
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
public class Student extends BaseObject {
  private String id;

  private String name;
  private String age;
  private Timestamp creationTime;
}
