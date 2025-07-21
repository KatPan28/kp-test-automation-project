package org.test.api.authors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.test.bo.utility.ObjectTypeUtils.areAllElementsBelongToClass;

import io.restassured.response.Response;
import java.util.List;
import org.test.bo.entryclients.AuthorsClient;
import org.test.data.dto.AuthorDto;
import org.testng.annotations.Test;

public class AuthorsAPITest {
// AUTHORS Tests functionality example....

  private final AuthorsClient authorsClient = new AuthorsClient();

  @Test(groups = {"authors", "getAll"},
        description = "Test GET request for fetching all authors")
  public void testGetAuthorsSuccess_200() {
    Response response = authorsClient.getAuthorsList();

    assertThat(response.getStatusCode())
        .as("Check if the response status code is 200")
        .isEqualTo(200);

    List<?> responseBooksList = response.jsonPath()
                                        .getList("$");

    assertThat(responseBooksList)
        .as("Check if the response contains a non-empty list of books")
        .isNotNull()
        .isNotEmpty();

    assertThat(areAllElementsBelongToClass(responseBooksList, AuthorDto.class))
        .as("Check if the response contains a correct Books data schema for all elements")
        .isTrue();
  }
}
