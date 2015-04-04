import com.yastart.papaya.Model.Book;

import junit.framework.TestCase;

import java.util.ArrayList;

public class BookTest extends TestCase {
    protected Book book;

    protected void setUp() {
        book = new Book();
    }

    public void testGetBooksByCity() {
        String city = "Moscow";
        ArrayList<Book> books = Book.getBooksForCity(city);
        assertTrue(books.size() == 0);
    }
}
