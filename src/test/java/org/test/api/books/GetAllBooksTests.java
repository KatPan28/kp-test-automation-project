package org.test.api.books;

import static org.assertj.core.api.Assertions.assertThat;
import static org.test.bo.utility.ObjectTypeUtils.areAllElementsBelongToClass;

import io.restassured.response.Response;
import java.util.List;
import org.test.bo.entryclients.BooksClient;
import org.test.data.dto.BookDto;
import org.testng.annotations.Test;

public class GetAllBooksTests {

  private final BooksClient bookClient = new BooksClient();

  @Test(testName = "GET /api/v1/Books - Test GET request for fetching books list",
        groups = {"book",
            "getAll"},
        description = "Test GET request for fetching books list")
  public void testGetBooksSuccess_200() {
    Response response = bookClient.getBooksList();

    assertThat(response.getStatusCode())
        .as("Check if the response status code is 200")
        .isEqualTo(200);

    List<?> responseBooksList = response.jsonPath()
                                        .getList("$");

    assertThat(responseBooksList)
        .as("Check if the response contains a non-empty list of books")
        .isNotNull()
        .isNotEmpty();

    assertThat(areAllElementsBelongToClass(responseBooksList, BookDto.class))
        .as("Check if the response contains a correct Books data schema for all elements")
        .isTrue();
  }
}