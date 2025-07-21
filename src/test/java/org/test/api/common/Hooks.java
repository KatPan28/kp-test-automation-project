package org.test.api.common;

import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import lombok.extern.slf4j.Slf4j;
import org.test.core.utility.ObjectMapperProvider;
import org.testng.annotations.BeforeClass;

@Slf4j
public class Hooks {

  @BeforeClass
  public void setUp() {
    RestAssured.config = RestAssured.config()
                                    .objectMapperConfig(
                                        ObjectMapperConfig.objectMapperConfig()
                                                          .jackson2ObjectMapperFactory(
                                                              (cls, charset) -> ObjectMapperProvider.getInstance())
                                    );
  }
}
