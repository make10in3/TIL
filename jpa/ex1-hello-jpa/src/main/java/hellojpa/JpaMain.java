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
            // 비영속
            Member member = new Member();
            member.setId(101L);
            member.setName("HelloA");

            // 영속
            System.out.println("===BEFORE===");
            em.persist(member); // -> 1차캐시 저장
            System.out.println("===AFTER===");

            Member findMember = em.find(Member.class, 101L); // -> 1차캐시에 저장한 것을 읽어옴, select 문 안나옴
            System.out.println(findMember.getId());
            System.out.println(findMember.getName());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}
