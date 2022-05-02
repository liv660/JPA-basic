package chapter11.jpql;

import chapter11.jpql.domain.Member;

import javax.persistence.*;
import java.util.List;

public class JpqlMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpql");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Member memberA = new Member();
            memberA.setUsername("memberA");
            memberA.setAge(10);
            em.persist(memberA);

            //[1] TypedQuery & Query
            TypedQuery<Member> queryA = em.createQuery("SELECT m FROM eleven_member m", Member.class);
            TypedQuery<String> queryB = em.createQuery("SELECT m.username FROM eleven_member m", String.class);
            Query queryC = em.createQuery("SELECT m.username, m.age FROM eleven_member m");

            //[2] 프로젝션 - new 명령어로 조회
            List<Member> resultA = em.createQuery("SELECT new chapter11.jpql.domain.Member(m.username, m.age) FROM eleven_member m", Member.class).getResultList();
            for(Member m : resultA) {
                System.out.println("m.username = " + m.getUsername());
                System.out.println("m.age = " + m.getAge());
            }

            //[3] 페이징
            for(int i = 2; i < 100; i++) {
                Member dummyMember = new Member();
                dummyMember.setUsername("member" + i);
                dummyMember.setAge(i);
                em.persist(dummyMember);
            }
            List<Member> resultB = em.createQuery("SELECT m FROM eleven_member m ORDER BY m.age DESC", Member.class)
                                        .setFirstResult(0)
                                        .setMaxResults(10)
                                        .getResultList();
            System.out.println("[Paging] resultB SIZE :: " + resultB.size());
            for(Member m : resultB) {
                System.out.println("member = " + m);
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
