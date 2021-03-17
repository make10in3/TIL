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
            Member member = new Member();
            member.setCreateBy("kim");

            em.persist(member);

            em.flush();
            em.clear();

            //Member findMember = em.find(Member.class, member.getId());
            Member findMember = em.getReference(Member.class, member.getId()); // class HibernateProxy - 해당 엔티티 프록시로 상속, 빈껍데기
            System.out.println("before findMember = " + findMember.getClass());
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.username = " + findMember.getUsername());
            System.out.println("after findMember = " + findMember.getClass()); // class HibernateProxy - 영속성 컨텍스트에서 엔티티 값을 가지고 있음

            Member member1 = new Member();
            member1.setUsername("member1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("member3");
            em.persist(member3);

            em.flush();
            em.clear();

            Member m1 = em.find(Member.class, member1.getId());
            Member m1_ref = em.getReference(Member.class, member1.getId());
            Member m2 = em.find(Member.class, member2.getId());
            Member m3 = em.getReference(Member.class, member3.getId());

            System.out.println("m1 == m2 : " + (m1.getClass() == m2.getClass())); // true
            System.out.println("m1 == m3 : " + (m1.getClass() == m3.getClass())); // false
            System.out.println("m1 == m3 : " + (m1 instanceof Member)); //true
            System.out.println("m1 == m1 : " + (m1.getClass() == m1_ref.getClass())); //true
            System.out.println("m1 : " + m1.getClass()); // class hellojpa.Member
            System.out.println("m1_ref : " + m1_ref.getClass()); // class hellojpa.Member




            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}
