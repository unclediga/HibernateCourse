import entity.Author;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Author author = session.get(Author.class, 1L);
        System.out.println(author);

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Author> query = criteriaBuilder.createQuery(Author.class);
        Query<Author> query1 = session.createQuery(query.select(query.from(Author.class)));
        List<Author> list = query1.getResultList();
        for (Author author1 : list) {
            System.out.println(author1);
        }

        session.close();
    }
}
