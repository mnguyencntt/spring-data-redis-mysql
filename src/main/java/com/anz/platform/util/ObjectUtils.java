package com.anz.platform.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ObjectUtils {
  public static boolean isNotEmpty(final Object obj) {
    return !isEmpty(obj);
  }

  @SuppressWarnings("rawtypes")
  public static boolean isEmpty(final Object obj) {
    if (obj == null) {
      return true;
    }

    if (obj instanceof Optional) {
      return !((Optional) obj).isPresent();
    }
    if (obj instanceof CharSequence) {
      return ((CharSequence) obj).length() == 0;
    }
    if (obj.getClass().isArray()) {
      return Array.getLength(obj) == 0;
    }
    if (obj instanceof Collection) {
      return ((Collection) obj).isEmpty();
    }
    if (obj instanceof Map) {
      return ((Map) obj).isEmpty();
    }

    return false;
  }

  public static void isTrue(final boolean expression, final String message) {
    if (!expression) {
      throw new IllegalArgumentException(message);
    }
  }

  public static void notEmpty(final Object object, final String message) {
    if (isEmpty(object)) {
      throw new IllegalArgumentException(message);
    }
  }

  public static boolean isBlank(final String cs) {
    if (cs == null) {
      return true;
    }
    final int strLen = cs.length();
    if (strLen == 0) {
      return true;
    }
    for (int i = 0; i < strLen; i++) {
      if (!Character.isWhitespace(cs.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  public static <T, U> List<U> convertList(List<T> from, Function<T, U> func) {
    return from.stream().map(func).collect(Collectors.toList());
  }

  public static <T, U> U[] convertArray(T[] from, Function<T, U> func, IntFunction<U[]> generator) {
    return Arrays.stream(from).map(func).toArray(generator);
  }

  public static String capitalizeFirstLetter(final String str) {
    if (ObjectUtils.isEmpty(str)) {
      return Constants.EMPTY;
    }
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }

  public static String lowerizeFirstLetter(final String str) {
    if (ObjectUtils.isEmpty(str)) {
      return Constants.EMPTY;
    }
    return str.substring(0, 1).toLowerCase() + str.substring(1);
  }

  public static Boolean isArrContain(final String input, final String... strs) {
    if (ObjectUtils.isEmpty(input) || ObjectUtils.isEmpty(strs)) {
      return Boolean.FALSE;
    }
    return Arrays.stream(strs).filter(ObjectUtils::isNotEmpty).anyMatch(p -> p.equalsIgnoreCase(input));
  }

  public static Boolean isArrStartWith(final String input, final String... strs) {
    if (ObjectUtils.isEmpty(input) || ObjectUtils.isEmpty(strs)) {
      return Boolean.FALSE;
    }
    return Arrays.stream(strs).filter(ObjectUtils::isNotEmpty).anyMatch(p -> p.startsWith(input));
  }

  public static Boolean isListContain(final String input, final List<String> strs) {
    if (ObjectUtils.isEmpty(input) || ObjectUtils.isEmpty(strs)) {
      return Boolean.FALSE;
    }
    return strs.stream().filter(ObjectUtils::isNotEmpty).anyMatch(p -> p.equalsIgnoreCase(input));
  }

  public static Boolean isInputStartWith(final String input, final String... strs) {
    if (ObjectUtils.isEmpty(input) || ObjectUtils.isEmpty(strs)) {
      return Boolean.FALSE;
    }
    return Arrays.stream(strs).filter(ObjectUtils::isNotEmpty).anyMatch(input::startsWith);
  }

  public boolean equals(final String first, final String second) {
    return equals(first, second, true);
  }

  public boolean equals(final String first, final String second, final boolean isIgnoredCase) {
    return isIgnoredCase ? first.equalsIgnoreCase(second) : first.equals(second);
  }

  public String toStringBoolean(final Boolean object) {
    return object == null ? Boolean.FALSE.toString() : object.toString();
  }

  public String toStringBoolean(final String object) {
    return isEmpty(object) ? Boolean.FALSE.toString() : Boolean.valueOf(object).toString();
  }

  public boolean contains(final String source, final String wantedStr) {
    return Pattern.compile(Pattern.quote(wantedStr), Pattern.CASE_INSENSITIVE).matcher(source).find();
  }

  public static <T> Predicate<T> not(Predicate<T> t) {
    return t.negate();
  }
}
