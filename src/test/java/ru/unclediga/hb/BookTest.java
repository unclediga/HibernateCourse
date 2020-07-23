package ru.unclediga.hb;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.*;
import ru.unclediga.JdbcUtil;
import ru.unclediga.hb.entity.Book;

import java.util.List;

public class BookTest {
    private static Session session;

    @BeforeClass
    public static void beforeClass() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    @AfterClass
    public static void afterClass() {
        session.close();
    }

    @Before
    public void init() {
        JdbcUtil.clearDB();
        //session.beginTransaction();
    }

    @After
    public void cleanUp() {
        session.clear();
        //session.getTransaction().rollback();
    }

    @Test
    public void getBook() {
        Book book = session.get(Book.class, 11L);
        Assert.assertNotNull(book);
        Assert.assertEquals("Book 11 (Author 1)", book.getName());
        Assert.assertNotNull(book.getAuthor());
        Assert.assertEquals(1L, book.getAuthor().getId());
    }

    @Test
    public void getAllBooksHQL() {
        Query<Book> query = session.createQuery("SELECT b FROM Book b", Book.class);
        List<Book> list = query.getResultList();
        printList(list);
        Assert.assertEquals(6, list.size());
    }

    private int getCountAndPrintAll() {
        Query<Book> query = session.createQuery("select b from Book b", Book.class);
        List<Book> books = query.getResultList();
        for (Book book : books) {
            System.out.println("MY: " + book);
        }
        return books.size();
    }

    private void printList(List<Book> books) {
        for (Book book : books) {
            System.out.println("MY: " + book);
        }
    }
}
