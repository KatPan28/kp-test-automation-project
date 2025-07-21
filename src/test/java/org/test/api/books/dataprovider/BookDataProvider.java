package org.test.api.books.dataprovider;

import java.util.List;
import org.test.bo.utility.JsonFileUtils;
import org.test.data.dto.BookDto;
import org.testng.annotations.DataProvider;

public class BookDataProvider {

  private final String BOOK_200_PATH_JSON = "src/test/resources/testdata/book/valid-book-data.json";
  private final String BOOKS_400_PATH_JSON = "src/test/resources/testdata/book/invalid-books-list-missing-required-fields.json";

  @DataProvider(name = "bookObjectDataProvider")
  public Object[][] postBookDataProvider() {
    BookDto book = JsonFileUtils.getTestData(BOOK_200_PATH_JSON, BookDto.class);

    return new Object[][]{{book}};
  }

  @DataProvider(name = "missingBooksListObjectDataProvider")
  public Object[][] missingBooksDataProvider() {
    List<BookDto> books = JsonFileUtils.getTestDataList(BOOKS_400_PATH_JSON,
        BookDto.class);

    return books.stream()
                .map(book -> new Object[]{book})
                .toArray(Object[][]::new);
  }
}
