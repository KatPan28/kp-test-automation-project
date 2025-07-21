package org.test.bo.entryclients;

import static org.test.bo.config.ClientConstants.API_BASE_URL;
import static org.test.bo.config.ClientConstants.AUTHORS_ENTRY_ID_URL;
import static org.test.bo.config.ClientConstants.AUTHORS_ENTRY_URL;
import static org.test.bo.config.ClientConstants.ID_PARAMETER;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.test.core.api.client.BaseClient;
import org.test.core.api.client.RequestSpecificationFactory;
import org.test.data.dto.AuthorDto;

@Slf4j
public class AuthorsClient {

  private final BaseClient baseClient;
  private final RequestSpecificationFactory specificationFactory;

  public AuthorsClient() {
    BaseClient.initialize(API_BASE_URL);
    this.baseClient = BaseClient.getInstance();
    this.specificationFactory = new RequestSpecificationFactory();
  }


  public Response getAuthorsList() {
    log.info("Attempt to retrieve a list of all authors by {} endpoint", AUTHORS_ENTRY_URL);

    return baseClient.get(specificationFactory.createBasicRequest(), AUTHORS_ENTRY_URL);
  }

  public Response getAuthorDetailsById(int authorId) {
    log.info("Attempt to retrieve details of a specific author ID {} by endpoint {}", authorId,
        AUTHORS_ENTRY_ID_URL);

    RequestSpecification request = specificationFactory.createRequestWithPathParam(ID_PARAMETER,
        authorId);

    return baseClient.get(request, AUTHORS_ENTRY_ID_URL);
  }

  public Response addAuthor(AuthorDto author) {
    log.info("Attempt to add new author to the system");

    return baseClient.post(
        specificationFactory.createRequestWithBody(author, ContentType.JSON), AUTHORS_ENTRY_ID_URL);
  }

  public Response updateAuthorDetailsById(AuthorDto author) {
    int authorId = author.getId();

    log.info("Attempt to update author details of a specific ID {} by endpoint {}", authorId,
        AUTHORS_ENTRY_ID_URL);

    return baseClient.put(
        specificationFactory.createRequestWithPathParamAndBody(ID_PARAMETER, authorId, author,
            ContentType.JSON),
        AUTHORS_ENTRY_ID_URL);
  }

  public Response deleteAuthorById(int authorId) {
    log.info("Attempt to delete specific author {} by ID using {} endpoint", authorId,
        AUTHORS_ENTRY_ID_URL);

    return baseClient.delete(
        specificationFactory.createRequestWithPathParam(ID_PARAMETER, authorId),
        AUTHORS_ENTRY_ID_URL);

  }
}
