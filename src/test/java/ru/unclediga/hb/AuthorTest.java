package ru.unclediga.hb;


import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.*;
import ru.unclediga.JdbcUtil;
import ru.unclediga.hb.entity.Author;

import javax.persistence.criteria.*;
import java.util.List;

public class AuthorTest {
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
    public void getAuthor() {
        Author author = session.get(Author.class, 1L);
        Assert.assertEquals(author.getName(), "Author 1");
        Assert.assertEquals(author.getSecondName(), "Author 1 SCD");
    }

    @Test
    public void updateAuthor() {
        Author author = session.get(Author.class, 1L);
        author.setName(author.getName() + " UPD");
        session.save(author);
        session.get(Author.class, 1L);
        Assert.assertEquals(author.getName(), "Author 1 UPD");
    }

    @Test
    public void insertAuthor() {
        Author author = new Author("Author 5", "Author 5 SCD");
        Long id = (Long) session.save(author);
        author = session.get(Author.class, id);
        Assert.assertEquals(author.getName(), "Author 5");
        Assert.assertEquals(author.getSecondName(), "Author 5 SCD");
    }

    @Test
    public void getAllAuthorsHQL() {
        Query<Author> query = session.createQuery("select a from Author a", Author.class);
        List<Author> authors = query.getResultList();
        Assert.assertEquals(authors.size(), 4);
    }

    @Test
    public void getAllAuthorsCriteriaAPI() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Author> cquery = builder.createQuery(Author.class);
        cquery.select(cquery.from(Author.class));
        Query<Author> query = session.createQuery(cquery);
        List<Author> authors = query.getResultList();
        Assert.assertEquals(authors.size(), 4);
    }

    @Test
    public void getAllAuthorsCriteriaAPISelection() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Author> cquery = builder.createQuery(Author.class);

        Root<Author> root = cquery.from(Author.class);
        Selection[] selections = new Selection[]{root.get("name")};
        cquery.select(builder.construct(Author.class, selections));
        Query<Author> query = session.createQuery(cquery);
        List<Author> authors = query.getResultList();
        Assert.assertEquals(authors.size(), 4);
        for (Author author : authors) {
            Assert.assertEquals(0L, author.getId());
            Assert.assertNotNull(author.getName());
            Assert.assertNull(author.getSecondName());
        }
    }

    @Test
    public void getAuthorCriteriaAPIParameter() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Author> cquery = builder.createQuery(Author.class);

        Root<Author> root = cquery.from(Author.class);

        ParameterExpression<String> nameParam = builder.parameter(String.class, "name");
        ParameterExpression<Long> idParam = builder.parameter(Long.class, "id");

        cquery
                .where(
                        builder.and(
                                builder.like(root.get("name"), nameParam),
                                builder.gt(root.get("id"), idParam)));

        Query<Author> query = session.createQuery(cquery);
        query.setParameter("name", "A%");
        query.setParameter("id", 2L);

        List<Author> authors = query.getResultList();
        Assert.assertEquals(2, authors.size());
        long count = authors.stream().filter(it -> it.getId() != 3 && it.getId() != 4).count();
        Assert.assertEquals(0L, count);
        for (Author author : authors) {
            System.out.println(author);
        }
    }
}
