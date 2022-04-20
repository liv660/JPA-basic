package chapter06.bidirect;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class BiMapping {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            //1. 양방향 연관관계
            Team teamA = Team.builder().name("teamA").build();
            em.persist(teamA);

            Member memberA = Member.builder().userName("memberA").team(teamA).build();
            em.persist(memberA);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, memberA.getId());
            List<Member> members = findMember.getTeam().getMembers();

            System.out.println("====================");
            for(Member m : members) {
                System.out.println("m : " + m.getUserName());
            }
            System.out.println("====================");

            //2. 양방향 매핑 시 연관관계의 주인에 값을 넣어주어야 한다.
            Team teamB = Team.builder().name("teamB").build();
            em.persist(teamB);

            Member memberB = Member.builder().userName("memberB").build();
            em.persist(memberB);

            em.flush();
            em.clear();
            /*  위 코드까지만 적용하면 member 테이블의 memberB.team_id는 null값을 가짐.
                양방향 매핑시에는 연관관계 양쪽 다 값을 넣어주도록 한다.

                memberB.setTeam(teamB);
                teamB.getMembers().add(memberB);
                em.persist(memberB);
             */
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
