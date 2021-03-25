package jpql;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Team team1 = new Team();
            team1.setName("팀A");
            em.persist(team1);

            Team team2 = new Team();
            team2.setName("팀B");
            em.persist(team2);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(team1);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(team1);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원2");
            member3.setTeam(team2);
            em.persist(member3);

            em.flush();
            em.clear();

            String sql = "update Member m set m.age = 20";

            //FLUSH
            int resultCount = em.createQuery(sql)
                    .executeUpdate();
        
            System.out.println("resultCount = " + resultCount);

            // 벌크 연산 수행 후 영속성 컨텍스트 초기화하기
            em.clear();

            Member findMember = em.find(Member.class, member1.getId());
            System.out.println("member1.getAge() = " + findMember.getAge());

            tx.commit();

        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

    }
}
