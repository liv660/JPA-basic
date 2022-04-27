package chapter09.proxy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ProxyMain {
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

            Member member = em.find(Member.class, memberA.getId());

            //member만 조회
            printMember(member);

            //member & team 모두 조회
            printMemberAndTeam(member);
            System.out.println("*********************");

            //em.find()는 조회 쿼리로 값을 가져온다면, em.getReference()는 사용되는 시점에 조회 쿼리로 값을 반환한다.
            Team teamB = Team.builder().name("teamB").build();
            Member memberB = Member.builder().userName("memberB").team(teamB).build();
            em.persist(teamB);
            em.persist(memberB);
            em.flush();
            em.clear();

            //Member findMember = em.find(Member.class, memberB.getId());  //조회쿼리 확인 가능
            System.out.println("*********************");

            Member getRefMember = em.getReference(Member.class, memberB.getId());
            printMemberIdAndName(getRefMember); //이때 조회쿼리 확인 가능
            System.out.println("*********************");

            //getRefMember는 Proxy로 체크되기 때문에 false 값을 가진다. (하지만 위에 findMember 주석을 해제하면 실제 엔티티를 반환하여 true 값을 가짐)
            System.out.println("memberB == getRefMember ? " + (memberB.getClass() == getRefMember.getClass()));  //타입체크는 instance of로 하기
            System.out.println("*********************");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void printMember(Member member) {
        System.out.println("====================");
        System.out.println("member = " + member.getUserName());
        System.out.println("====================");
    }

    private static void printMemberAndTeam(Member member) {
        System.out.println("====================");
        System.out.println("member = " + member.getUserName());

        Team team = member.getTeam();
        System.out.println("team = " + team.getName());
        System.out.println("====================");

    }

    private static void printMemberIdAndName(Member member) {
        System.out.println("====================");
        System.out.println("member.id = " + member.getId());
        System.out.println("member.name = " + member.getUserName());
        System.out.println("====================");
    }
}
