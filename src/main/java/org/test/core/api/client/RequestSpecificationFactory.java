package org.test.core.api.client;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestSpecificationFactory {

  private final Map<String, String> defaultHeaders = new HashMap<>();

  public RequestSpecificationFactory(Map<String, String> defaultHeaders) {
    if (defaultHeaders != null) {
      this.defaultHeaders.putAll(defaultHeaders);
    }
  }

  public RequestSpecificationFactory() {
  }

  public RequestSpecification createBasicRequest() {
    log.info("Building default RequestSpecification with default headers");
    return RestAssured.given()
                      .headers(defaultHeaders)
                      .log()
                      .all();
  }

  public RequestSpecification createRequestWithQueryParams(Map<String, Object> queryParams) {
    log.info("Building RequestSpecification with query parameters");

    RequestSpecification requestSpec = createBasicRequest();
    if (queryParams != null && !queryParams.isEmpty()) {
      requestSpec.queryParams(queryParams);
    }
    return requestSpec;
  }

  public RequestSpecification createRequestWithPathParam(String pathParamKey, Object value) {
    log.info("Building RequestSpecification with path parameters");
    RequestSpecification requestSpec = createBasicRequest();

    return requestSpec.pathParam(pathParamKey, value);

  }

  public RequestSpecification createRequestWithBody(Object body, ContentType contentType) {
    log.info("Building RequestSpecification with Body and ContentType");
    RequestSpecification requestSpec = createBasicRequest();

    Optional.ofNullable(contentType)
            .ifPresent(requestSpec::contentType);
    requestSpec.body(body);

    return requestSpec;
  }

  public RequestSpecification createRequestWithPathParamAndBody(String pathParamKey, Object value,
      Object body, ContentType contentType) {
    log.info("Building RequestSpecification with Body and path parameters");
    RequestSpecification requestSpec = createBasicRequest();

    requestSpec.pathParam(pathParamKey, value);

    Optional.ofNullable(contentType)
            .ifPresent(requestSpec::contentType);
    Optional.ofNullable(body)
            .ifPresent(requestSpec::body);

    return requestSpec;

  }

  public RequestSpecification createCustomRequest(Map<String, String> headers,
      Map<String, Object> pathParams,
      Map<String, Object> queryParams,
      Object body,
      ContentType contentType) {

    RequestSpecification requestSpec = createBasicRequest();

    Optional.ofNullable(headers)
            .filter(h -> !h.isEmpty())
            .ifPresent(requestSpec::headers);

    Optional.ofNullable(pathParams)
            .filter(p -> !p.isEmpty())
            .ifPresent(requestSpec::pathParams);

    Optional.ofNullable(queryParams)
            .filter(q -> !q.isEmpty())
            .ifPresent(requestSpec::queryParams);

    Optional.ofNullable(contentType)
            .ifPresent(requestSpec::contentType);

    Optional.ofNullable(body)
            .ifPresent(requestSpec::body);

    return requestSpec;
  }
}