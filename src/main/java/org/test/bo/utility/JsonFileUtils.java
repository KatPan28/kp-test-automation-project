package org.test.bo.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.test.core.utility.ObjectMapperProvider;

@UtilityClass
@Slf4j
public class JsonFileUtils {

  public static <T> T getTestData(String dataFilePath, Class<T> clazz) {
    validateData(dataFilePath, clazz);

    try (InputStream inputStream = Files.newInputStream(Paths.get(dataFilePath))) {
      return ObjectMapperProvider.getInstance()
                                 .readValue(inputStream, clazz);
    } catch (IOException e) {
      log.error("Error loading data file: {}", dataFilePath, e);
      throw new RuntimeException(e);
    }
  }

  public static <T> List<T> getTestDataList(String dataFilePath, Class<T> clazz) {
    validateData(dataFilePath, clazz);

    try (InputStream inputStream = Files.newInputStream(Paths.get(dataFilePath))) {
      ObjectMapper mapper = ObjectMapperProvider.getInstance();
      return ObjectMapperProvider.getInstance()
                                 .readValue(inputStream,
                                     mapper.getTypeFactory()
                                           .constructCollectionType(List.class, clazz));
    } catch (IOException e) {
      log.error("Error loading data file: {}", dataFilePath, e);
      throw new RuntimeException(e);
    }
  }

  private static <T> void validateData(String dataFilePath, Class<T> clazz) {
    if (dataFilePath == null || clazz == null) {
      throw new IllegalArgumentException("Data file path and class type must not be null.");
    }

    Path path = Paths.get(dataFilePath);
    if (!Files.exists(path)) {
      log.error("Cannot find data by path {}", dataFilePath);
      throw new RuntimeException("Data file not found at path: " + dataFilePath);
    }
  }
}