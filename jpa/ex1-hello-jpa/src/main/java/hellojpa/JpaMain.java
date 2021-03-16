package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Movie movie = new Movie();
            movie.setDirector("봉준호");
            movie.setActor("우식이");
            movie.setName("기생충");
            movie.setPrice(10000);
            System.out.println(movie.getId());

            em.persist(movie);

            em.flush();
            em.clear();
            System.out.println(movie.getId());

            Movie findMovie = em.find(Movie.class, movie.getId());
            System.out.println("findMovie=" + findMovie);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}
