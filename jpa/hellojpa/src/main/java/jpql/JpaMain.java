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
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            // 영속성 컨텍스트에서 관리
            List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();

            Member findMember = result.get(0);
            findMember.setAge(20);

            // 엔티티 프로젝션 예제
            // join 쿼리 나감
            List<Team> result2 = em.createQuery("select m.team from Member m", Team.class).getResultList();

            // join 명시적으로, 위 문법과 동일
            List<Team> result3 = em.createQuery("select t from Member m join m.team t", Team.class).getResultList();

            // 임베디드 타입 프로젝션 예제
            List<Address> result4 = em.createQuery("select o.address from Order o", Address.class).getResultList();

            // 스칼라 타입 프로젝션 예제
            List<Address> result5 = em.createQuery("select distinct m.username, m.age from Member m").getResultList();

            // 여러값 조회1 - 반환타입이 명확하지 않을 때
            List result6 = em.createQuery("select m.username, m.age from Member m").getResultList();
            Object o = result6.get(0);
            Object[] o1 = (Object[]) o;
            System.out.println("username = " + o1[0]);
            System.out.println("age = " + o1[1]);

            // 여러값 조회2 - 반환타입이 명확하지 않을 때
            // 타입 캐스팅 과정 생략
            List<Object[]> result7 = em.createQuery("select m.username, m.age from Member m").getResultList();
            Object[] o2 = result7.get(0);
            System.out.println("username = " + o2[0]);
            System.out.println("age = " + o2[1]);

            // 여러값 조회 3 - new 명령어로 조회
            // 생성자를 통해서
            // 단순값을 DTO로 바로 조회
            // 패키지 명을 포함한 전체 클래스 명 입력
            // 순서와 타입이 일치하는 생성자 필요
            List<MemberDTO> result8 = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class).getResultList();

            MemberDTO memberDTO = result8.get(0);
            System.out.println("memberDTO username = " + memberDTO.getUsername());
            System.out.println("memberDTO age = " + memberDTO.getAge());

            tx.commit();

        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

    }
}
