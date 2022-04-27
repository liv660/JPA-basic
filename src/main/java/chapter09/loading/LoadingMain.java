package chapter09.loading;

import chapter09.proxy.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class LoadingMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Team teamA = Team.builder().name("teamA").build();
            Member memberA = Member.builder().userName("memberA").team(teamA).build();
            em.persist(teamA);
            em.persist(memberA);
            em.flush();
            em.clear();

            //지연 로딩 LAZY를 사용해서 프록시로 조회 OR 즉시 로딩 EAGER를 사용해서 member & team 함께 조회
            Member member = em.find(Member.class, memberA.getId());
            System.out.println("member 프록시? " + member.getTeam().getClass());   //LAZY일 때 proxy 객체

            //즉시 로딩 EAGER를 사용해서 member & team 함께 조회
            System.out.println("=====================");
            System.out.println("TEAM NAME ::: " + member.getTeam().getName());
            System.out.println("=====================");

            //실무에서는 무조건 지연로딩 사용 (or JPQL fecth join, entity graph 사용)
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
