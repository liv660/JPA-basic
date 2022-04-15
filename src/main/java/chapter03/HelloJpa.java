package chapter03;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class HelloJpa {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        //트랜잭션 시작
        tx.begin();

        try {
            //1. 등록 :: member 생성 후 저장
            Member member1 = Member.builder().id(1L).name("HelloA").build();
            Member member2 = Member.builder().id(2L).name("HelloB").build();

            em.persist(member1);
            em.persist(member2);
            //tx.commit();

            //2. 수정
            Member findMember = em.find(Member.class, 1L);
            findMember.setName("HelloZ");
            //tx.commit();

            //3. 삭제
            //em.remove(findMember);
            //tx.commit();

            //4. JPQL, Entity를 대상으로 하는 Query
            List<Member> findMembers = em.createQuery("SELECT m FROM Member AS m", Member.class)
                                        /* pagination 하는데 용이함.
                                        .setFirstResult(1)
                                        .setMaxResults(10)
                                         */
                                        .getResultList();
            System.out.println("\n\n=====================");
            for(Member m : findMembers) {
                System.out.println("m.name = " + m.getName());
            }
            System.out.println("=====================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
