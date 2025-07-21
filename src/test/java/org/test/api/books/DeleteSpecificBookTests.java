package org.test.api.books;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.Response;
import org.test.bo.entryclients.BooksClient;
import org.testng.annotations.Test;

public class DeleteSpecificBookTests {

  private final BooksClient bookClient = new BooksClient();

  @Test(testName = "DELETE /api/v1/Books/{id} - Test DELETE request for deleting book by Id",
        groups = {"book", "delete"},
        description = "Test DELETE request for deleting book by Id")
  public void testDeleteBook_200() {
    Response response = bookClient.deleteBookById(1);

    assertThat(response.getStatusCode())
        .as("Check if the response status code is 200")
        .isEqualTo(200);

    assertThat(response.getBody()
                       .asString())
        .as("Check if the response do not contains body")
        .isEmpty();
  }

  @Test(testName = "DELETE /api/v1/Books/{id} - Test DELETE request for deleting book by Id",
        groups = {"book", "delete"},
        description = "Test DELETE request for deleting book by Id")
  public void testDeleteUnexistingBook_404() {
    Response response = bookClient.deleteBookById(666666666);

    assertThat(response.getStatusCode())
        .as("Check if the response status code is 404")
        .isEqualTo(404);

    assertThat(response.getBody()
                       .asString())
        .as("Check if the response do not contains body")
        .isEmpty();
  }

  @Test(testName = "DELETE /api/v1/Books/{id} - Test DELETE request for deleting book by Id",
        groups = {"book", "delete"},
        description = "Test DELETE request for deleting book by Id")
  public void testDeleteWithInvalidIdType_400() {
    Response response = bookClient.deleteBookById("abc");

    assertThat(response.getStatusCode())
        .as("Check if the response status code is 404")
        .isEqualTo(400);

    assertThat(response.jsonPath()
                       .getString("errors"))
        .as("Check if the response contains error description")
        .contains("The value 'abc' is not valid.");
  }
}