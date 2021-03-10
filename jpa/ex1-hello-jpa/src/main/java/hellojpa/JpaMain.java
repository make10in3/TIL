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
            Member findMember = em.find(Member.class, 2L);
            System.out.println("findMember.id = " + findMember.getId()); // findMember.id = 2
            System.out.println("findMember.name = " + findMember.getName()); // findMember.name = HelloA
            findMember.setName("HelloB");
            // persist() 저장안해도 따로 안해도 된다
            System.out.println("findMember.name = " + findMember.getName()); // findMember.name = HelloB
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}
