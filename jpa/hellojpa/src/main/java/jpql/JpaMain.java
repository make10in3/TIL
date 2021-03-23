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


            // 영속성 컨텍스트에 미리 담아 둘수있음
            String query = "select t from Team t";

            // lazy loading 으로 루프 안에서 각각 쿼리를 날리기 때문에 성능이 안나옴
            // 해결 방법1 : 해당 컬렉션 @BatchSize(size=100)
            // 해결 방법2 : 글로벌 세팅 persistence.xml  - <property name="hibernate.default_batch_fetch_size" value="100"/>
            List<Member> result = em.createQuery(query, Member.class)
                    .getResultList();
            for (Member member : result) {
                System.out.println("member = " + member);
                System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
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
