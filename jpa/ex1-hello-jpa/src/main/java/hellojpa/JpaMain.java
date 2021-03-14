package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            // 순수한 객체 관계를 고려하면 항상 양쪽다 값을 입력해야한다.
            Team team = new Team();
            team.setName("TEAM A");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team); //1
            em.persist(member);

            team.getMembers().add(member); //2

            em.flush();
            em.clear();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}
