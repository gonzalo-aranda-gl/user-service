package com.core.user_service;

import com.google.gson.JsonParser;
import lombok.experimental.UtilityClass;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

@UtilityClass
public class TestFileUtils {

  public static String getContentFromJsonFile(String filename) throws FileNotFoundException {
    InputStream expectedResponseInputStream = TestFileUtils.class.getClassLoader().getResourceAsStream(
      filename);
    if (expectedResponseInputStream == null) {
      throw new FileNotFoundException(filename + " is not found. Please check /test/resources folder.");
    }
    return JsonParser.parseReader(
      new InputStreamReader(expectedResponseInputStream)).toString();
  }

}
