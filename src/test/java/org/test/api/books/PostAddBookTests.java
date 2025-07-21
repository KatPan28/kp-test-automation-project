package org.test.api.books;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.Response;
import org.test.api.books.dataprovider.BookDataProvider;
import org.test.bo.entryclients.BooksClient;
import org.test.data.dto.BookDto;
import org.testng.annotations.Test;

public class PostAddBookTests {

  private final BooksClient bookClient = new BooksClient();

  @Test(testName = "POST /api/v1/Books - Test POST request to add new book to the system",
        dataProvider = "bookObjectDataProvider",
        dataProviderClass = BookDataProvider.class,
        groups = {"book", "post"},
        description = "Test POST request to add new book to the system")
  public void testAddNewBook_200(BookDto book) {
    Response response = bookClient.addNewBook(book);

    assertThat(response.getStatusCode()).as("Check if the response status code is 200")
                                        .isEqualTo(200);
  }

  @Test(testName = "POST /api/v1/Books - Test POST request to add new book to the system with invalid Content-Type Header",
        dataProvider = "bookObjectDataProvider",
        dataProviderClass = BookDataProvider.class,
        groups = {"book", "post"},
        description = "Test POST request to add new book to the system with invalid Content-Type Header")
  public void testAddNewBookInvalidHeaderContentType_415(BookDto book) {
    Response response = bookClient.addNewBookWithEmptyContentType(book);

    assertThat(response.getStatusCode()).as("Expect 415 Unsupported Media Type")
                                        .isEqualTo(415);

    assertThat(response.jsonPath()
                       .getString("title")).as("Check error title")
                                           .isEqualTo("Unsupported Media Type");
  }

  @Test(testName = "POST /api/v1/Books - Test POST request to add new book with empty JSON body",
        groups = {"book", "post"},
        description = "Test POST request to add new book with empty JSON body")
  public void testAddNewBookEmptyJsonBody_400() {
    Response response = bookClient.addNewBook(new BookDto());

    assertThat(response.getStatusCode()).as("Expect 400 for empty JSON Book data request body")
                                        .isEqualTo(400);

    assertThat(response.jsonPath()
                       .getString("title")).as("Check title for empty JSON Book details")
                                           .isEqualTo("One or more validation errors occurred.");
  }

  @Test(testName = "POST /api/v1/Books - Test POST request to add new book with missing required Book data fields in body",
        dataProvider = "missingBooksListObjectDataProvider",
        dataProviderClass = BookDataProvider.class,
        groups = {"book", "post"},
        description = "Test POST request to add new book with missing required Book data fields in body")
  public void testAddNewBookWithMissingRequiredFieldsRequestBody_400(
      BookDto invalidBookData) {

    Response response = bookClient.addNewBook(invalidBookData);

    assertThat(response.getStatusCode())
        .as("Expect 400 for missing required JSON Book data request body")
        .isEqualTo(400);
  }
}
