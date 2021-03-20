package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("teamA");
            member.setAge(10);
            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            // inner 조인 - inner 생략가능
            String query1 = "select m from Member m inner join m.team t";
            // outer 조인 - outer 생략 가능
            String query2 = "select m from Member m left outer join m.team t";
            // 세타 조인 - 상관없는 두 테이블 조인
            String query3 = "select m from Member m , Team t where m.username = t.name";

            // ON 절
            // 1. 조인 대상 필터링
            String query4 = "select m from Member m left join m.team t on t.name = 'A'";
            // 2. 연관관계과 없는 엔티티 외부 조인
            String query = "select m from Member m left join Team t on m.username = t.name";

            List<Member> result = em.createQuery(query, Member.class)
                    .getResultList();

            System.out.println("result.size() = " + result.size());
            for (Member member1 : result) {
                System.out.println("member1 = " + member1);
            }

            tx.commit();

        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

    }
}
