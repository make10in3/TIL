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
            Team team = new Team();
            team.setName("TEAM A");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team);
            em.persist(member);

            // 디비에서 가져오는 것을 보고싶을 때
            em.flush(); // 영속성 컨텍스트에 있는 디비쿼리를 디비에 다 전송해서 싱크를 맞춰놓고
            em.clear(); // 영속성 컨텍스트 초기화

            Member findMember = em.find(Member.class, member.getId());
            Team findTeam = findMember.getTeam();
            System.out.println(findTeam.getName());

            // 새로운 팀으로 설정하고 싶을 때
            Team newTeam = em.find(Team.class, 100L);
            findMember.setTeam(newTeam);

            // 양방향 연관 관계 설정
            List<Member> members = findMember.getTeam().getMembers();

            for (Member m: members){
                System.out.println("m = " + m.getUsername());
            }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}
