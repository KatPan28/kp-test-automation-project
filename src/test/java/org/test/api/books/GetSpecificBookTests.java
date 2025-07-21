package org.test.api.books;

import static org.assertj.core.api.Assertions.assertThat;
import static org.test.bo.utility.ObjectTypeUtils.isElementBelongToClass;

import io.restassured.response.Response;
import org.test.bo.entryclients.BooksClient;
import org.test.data.dto.BookDto;
import org.testng.annotations.Test;

public class GetSpecificBookTests {

  private final BooksClient bookClient = new BooksClient();

  @Test(testName = "GET /api/v1/Books/{id} - Test GET request for fetching book by Id",
        groups = {"book", "get"},
        description = "Test GET request for fetching book by Id")
  public void testGetBookById_200() {
    Response response = bookClient.getBookDetailById(1);

    assertThat(response.getStatusCode())
        .as("Check if the response status code is 200")
        .isEqualTo(200);

    Object responseBookDetails = response.jsonPath()
                                         .getJsonObject("$");

    assertThat(isElementBelongToClass(responseBookDetails, BookDto.class))
        .as("Check if the response contains a correct Book details data schema")
        .isTrue();

  }

  @Test(testName = "GET /api/v1/Books/{id} - Test GET request for fetching unexisting book",
        groups = {"book", "get"},
        description = "Test GET request for fetching unexisting book")
  public void testGetBookByIdNotFound_404() {
    Response response = bookClient.getBookDetailById(999999999);

    assertThat(response.getStatusCode())
        .as("Check if the response status code is NOT_FOUND 404")
        .isEqualTo(404);
  }

}