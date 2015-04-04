import com.yastart.papaya.Model.Book;
import com.yastart.papaya.Model.GetListHandler;
import com.yastart.papaya.Model.User;

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

    public void testGetBookByUser() {
        User user = new User();
        user.setId("102363055574899025750");

        Book.getBooksForUser(user, new GetListHandler<Book>() {
            @Override
            public void done(ArrayList<Book> data) {
                assertNotNull(data);
                assertTrue(data.size() != 0);
            }

            @Override
            public void error(String responseError) {

            }
        });
    }
}
