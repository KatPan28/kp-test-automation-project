package org.test.api.books;

import static org.assertj.core.api.Assertions.assertThat;
import static org.test.bo.utility.ObjectTypeUtils.isElementBelongToClass;

import io.restassured.response.Response;
import org.test.api.books.dataprovider.BookDataProvider;
import org.test.bo.entryclients.BooksClient;
import org.test.data.dto.BookDto;
import org.testng.annotations.Test;

public class PutUpdateBookDetailsTests {

  private final BooksClient bookClient = new BooksClient();

  @Test(testName = "PUT /api/v1/Books/{id} - Test PUT request to update existing book in the system",
        dataProvider = "bookObjectDataProvider",
        dataProviderClass = BookDataProvider.class,
        groups = {
            "book", "put"},
        description = "Test PUT request to update existing book in the system")
  public void testUpdateExistingBook_200(BookDto book) {
    Response response = bookClient.updateBookDetailsById(book.getId(), book);

    assertThat(response.getStatusCode()).as("Check if the response status code is 200")
                                        .isEqualTo(200);

    assertThat(isElementBelongToClass(response.jsonPath()
                                              .get(), BookDto.class))
        .as("Check if the response contains a correct Books data schema for updated book")
        .isTrue();
  }

  @Test(testName = "PUT /api/v1/Books/{id} - Test PUT request to update invalid book id in the system",
        dataProvider = "bookObjectDataProvider",
        dataProviderClass = BookDataProvider.class,
        groups = {
            "book", "put"},
        description = "Test PUT request to update invalid book id in the system")
  public void testUpdateInvalidBookId_400(BookDto book) {
    Response response = bookClient.updateBookDetailsById("abc", book);

    assertThat(response.getStatusCode())
        .as("Check if the response status code is 404")
        .isEqualTo(400);

    assertThat(response.jsonPath()
                       .getString("errors"))
        .as("Check if the response contains error description")
        .contains("The value 'abc' is not valid.");
  }

  @Test(testName = "PUT /api/v1/Books/{id} - Test PUT request to update book with empty body id in the system",
        dataProvider = "bookObjectDataProvider",
        dataProviderClass = BookDataProvider.class,
        groups = {
            "book",
            "put"},
        description = "Test PUT request to update book with empty body id in the system")
  public void testUpdateBookIdWithEmptyBody_400(BookDto book) {
    Response response = bookClient.updateBookDetailsById(book.getId(), null);

    assertThat(response.getStatusCode())
        .as("Check if the response status code is 400")
        .isEqualTo(400);

    assertThat(response.jsonPath()
                       .getString("errors"))
        .as("Check if the response contains error description")
        .contains("A non-empty request body is required");
  }

  @Test(testName = "PUT /api/v1/Books/{id} - Test PUT request to update book with empty body id in the system",
        dataProvider = "bookObjectDataProvider",
        dataProviderClass = BookDataProvider.class,
        groups = {
            "book",
            "put"},
        description = "Test PUT request to update book with empty body id in the system")
  public void testUpdateBookIdWithEmptyJsonBody_2002(BookDto book) {
    Response response = bookClient.updateBookDetailsById(book.getId(), new BookDto());

    assertThat(response.getStatusCode())
        .as("Check if the response status code is 400")
        .isEqualTo(400);
  }
}
