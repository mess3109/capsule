package com.capsulewardrobe.services.util;

import java.util.UUID;

public class UuidUtility {
  public static <T> String generateUuid(T object) {

    return UUID.nameUUIDFromBytes(object.toString().getBytes()).toString();
  }

  public static String generateUuid() {

    return UUID.randomUUID().toString();
  }
}
