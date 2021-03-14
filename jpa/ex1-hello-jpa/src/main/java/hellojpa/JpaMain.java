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

            //team.getMembers().add(member); //2

            //em.flush();
            //em.clear();

            // em.flush() 와 em.clear() 를 안하면 1차 캐시에서 데이터를 가져오는데
            // 순수한 객체 상태이기 때문에 아직 컬렉션도 없는 상태라서 값이 저장이 안되어 있다
            
            Team findTeam = em.find(Team.class, team.getId()); // 1차 캐시
            List<Member> members = findTeam.getMembers();
            System.out.println("==================");
            for (Member m : members) {
                System.out.println("m = "+ m.getUsername());
            }
            System.out.println("==================");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}
