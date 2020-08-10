package com.anz.platform.domain;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import com.anz.platform.util.Constants;
import com.anz.platform.util.ObjectUtils;

public abstract class BaseObject {
  private List<Field> findDeclaredFields() {
    final LinkedList<Field> linkedList = new LinkedList<>();
    final Field[] declaredFields = this.getClass().getDeclaredFields();
    for (int i = 0; i < declaredFields.length; i++) {
      Field field = declaredFields[i];
      field.setAccessible(true);
      if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
        linkedList.add(field);
      }
    }
    return linkedList;
  }

  private String[] findFields() {
    final List<Field> declaredFields = findDeclaredFields();
    final String[] fields = new String[declaredFields.size()];
    for (int i = 0; i < declaredFields.size(); i++) {
      fields[i] = declaredFields.get(i).getName();
    }
    return fields;
  }

  public String[] findValues() {
    try {
      final List<Field> declaredFields = findDeclaredFields();
      final String[] output = new String[declaredFields.size()];
      for (int i = 0; i < declaredFields.size(); i++) {
        final Field field = declaredFields.get(i);
        final Object declaredValue = field.get(this);
        if (declaredValue != null) {
          output[i] = String.valueOf(declaredValue);
        } else {
          output[i] = Constants.EMPTY; // Can be NULL
        }
      }
      return output;
    } catch (IllegalArgumentException | IllegalAccessException e) {
      return new String[0];
    }
  }

  public String findFieldsJoining() {
    return Arrays.stream(findFields()).collect(Collectors.joining(Constants.COMMA));
  }

  public String findMarksJoining() {
    return Arrays.stream(findFields()).map(p -> Constants.QUESTION).collect(Collectors.joining(Constants.COMMA));
  }

  public List<String> findFieldValues(final String... ignoreFields) {
    try {
      final List<Field> declaredFields = findDeclaredFields();
      final LinkedList<String> output = new LinkedList<>();
      for (int i = 0; i < declaredFields.size(); i++) {
        final Field field = declaredFields.get(i);
        final Object declaredValue = field.get(this);
        if (ObjectUtils.isArrContain(field.getName(), ignoreFields)) {
          continue;
        }
        final String updateQuery = "%s='%s'";
        if (declaredValue == null) {
          output.add(String.format(updateQuery, field.getName(), String.valueOf(declaredValue)));
        } else {
          output.add(String.format(updateQuery, field.getName(), String.valueOf(declaredValue)));
        }
      }
      return output;
    } catch (IllegalArgumentException | IllegalAccessException e) {
      return Collections.emptyList();
    }
  }

  public String findFieldValuesJoining(final String... ignoreFields) {
    return findFieldValues(ignoreFields).stream().collect(Collectors.joining(Constants.COMMA));
  }

}
