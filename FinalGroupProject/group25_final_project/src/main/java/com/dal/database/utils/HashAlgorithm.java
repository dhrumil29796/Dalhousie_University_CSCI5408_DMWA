package com.dal.database.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashAlgorithm {

  public static String getSHA256Hash(final String source) {
    if (source == null) {
      return null;
    }
    try {
      final MessageDigest messageDigest =
          MessageDigest.getInstance("SHA-256");
      return String.format("%064x",
          new BigInteger(1,
              messageDigest.digest(source.getBytes(StandardCharsets.UTF_8))));
    } catch (final NoSuchAlgorithmException e) {
      return null;
    }
  }
}
