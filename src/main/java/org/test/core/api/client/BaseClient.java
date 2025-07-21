package org.test.core.api.client;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class BaseClient {

  private static volatile BaseClient instance;

  private BaseClient(String baseUrl) {
    RestAssured.baseURI = baseUrl;
  }

  public static void initialize(String baseUrl) {
    if (instance == null) {
      synchronized (BaseClient.class) {
        if (instance == null) {
          log.info("Creating instance of BaseClient for {} base url", baseUrl);
          instance = new BaseClient(baseUrl);
        }
      }
    }
  }

  public static BaseClient getInstance() {
    if (instance == null) {
      log.error("BaseClient is not initialized");
      throw new IllegalStateException("Call initialize() first");
    }
    return instance;
  }

  public Response get(RequestSpecification request, String endpoint) {
    log.info("GET request {} endpoint", endpoint);

    Response response = request.get(endpoint)
                               .then()
                               .extract()
                               .response();
    handleError(response);

    return response;
  }

  public Response post(RequestSpecification request, String endpoint) {
    log.info("POST request {} endpoint", endpoint);

    Response response = request.post(endpoint)
                               .then()
                               .extract()
                               .response();
    handleError(response);

    return response;
  }

  public Response put(RequestSpecification request, String endpoint) {
    log.info("PUT request {} endpoint", endpoint);

    Response response = request.put(endpoint)
                               .then()
                               .extract()
                               .response();
    handleError(response);

    return response;
  }

  public Response delete(RequestSpecification request, String endpoint) {
    log.info("DELETE request {} endpoint", endpoint);

    Response response = request.delete(endpoint)
                               .then()
                               .extract()
                               .response();
    handleError(response);

    return response;
  }

  private void handleError(Response response) {
    int statusCode = response.getStatusCode();

    if (statusCode >= 400) {
      String errorBody = response.getBody()
                                 .asString();

      log.error("Request failed with status code {} and body: {}", statusCode, errorBody);
    }
  }
}