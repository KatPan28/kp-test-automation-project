package org.test.bo.entryclients;

import static org.test.bo.config.ClientConstants.API_BASE_URL;
import static org.test.bo.config.ClientConstants.BOOKS_ENTRY_ID_URL;
import static org.test.bo.config.ClientConstants.BOOKS_ENTRY_URL;
import static org.test.bo.config.ClientConstants.ID_PARAMETER;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.test.core.api.client.BaseClient;
import org.test.core.api.client.RequestSpecificationFactory;
import org.test.data.dto.BookDto;

@Slf4j
public class BooksClient {

  private final BaseClient baseClient;
  private final RequestSpecificationFactory specificationFactory;

  public BooksClient() {
    BaseClient.initialize(API_BASE_URL);
    this.baseClient = BaseClient.getInstance();
    this.specificationFactory = new RequestSpecificationFactory();
  }

  public Response getBooksList() {
    log.info("Attempt to retrieve a list of all books by {} endpoint", BOOKS_ENTRY_URL);

    return baseClient.get(specificationFactory.createBasicRequest(), BOOKS_ENTRY_URL);
  }

  public Response getBookDetailById(int bookId) {
    log.info("Attempt to retrieve details of a specific book ID {} by endpoint", bookId);

    RequestSpecification request = specificationFactory.createRequestWithPathParam(ID_PARAMETER,
        bookId);

    return baseClient.get(request, BOOKS_ENTRY_ID_URL);
  }

  public Response addNewBookWithEmptyContentType(BookDto book) {
    log.info("Attempt to add new book to the system with JSON Content-type");

    return baseClient.post(
        specificationFactory.createRequestWithBody(book, null), BOOKS_ENTRY_URL);
  }

  public Response addNewBook(BookDto book) {
    log.info("Attempt to add new book to the system");

    return baseClient.post(
        specificationFactory.createRequestWithBody(book, ContentType.JSON), BOOKS_ENTRY_URL);
  }

  public Response updateBookDetailsById(Object bookId, BookDto book) {
    log.info("Attempt to update book details for specific book ID {} by endpoint {}", bookId,
        BOOKS_ENTRY_ID_URL);

    return baseClient.put(
        specificationFactory.createRequestWithPathParamAndBody(ID_PARAMETER, bookId, book,
            ContentType.JSON),
        BOOKS_ENTRY_ID_URL);
  }

  public Response deleteBookById(Object bookId) {
    log.info("Attempt to delete specific book {} by ID using {} endpoint", bookId,
        BOOKS_ENTRY_ID_URL);

    return baseClient.delete(
        specificationFactory.createRequestWithPathParam(ID_PARAMETER, bookId), BOOKS_ENTRY_ID_URL);
  }
}
