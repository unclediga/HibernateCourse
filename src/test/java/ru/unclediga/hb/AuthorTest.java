package ru.unclediga.hb;


import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.unclediga.JdbcUtil;
import ru.unclediga.hb.entity.Author;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.util.List;

public class AuthorTest {
    private static Session session;

    @Before
    public void init() {
        JdbcUtil.clearDB();
        session = HibernateUtil.getSessionFactory().openSession();
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
}
