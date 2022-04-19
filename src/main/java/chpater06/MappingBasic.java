package chpater06;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class MappingBasic {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            //1. 객체를 테이블에 맞추어 모델링 한 경우 (= 외래키 식별자를 직접 다룸)
            Team teamA = Team.builder().name("teamA").build();
            em.persist(teamA);

            Member memberA = Member.builder().userName("memberA")
                                            //.teamId(team.getId())   //외래키 직접다룸
                                            .build();
            em.persist(memberA);

            /*  member의 팀을 알기 위한 조회 과정... (일단 식별자부터 찾아야하는 번거로움 발생)
            Member findMemberA = em.find(Member.class, memberA.getId());
            Long findTeamIdA = findMemberA.getTeamId();
            Team findTeamA = em.find(Team.class, findTeamIdA);
             */


            //2. 단방향 연관관계 저장한 후 (= 객체 지향 모델링)
            Team teamB = Team.builder().name("teamB").build();
            em.persist(teamB);

            Member memberB = Member.builder().userName("memberB").team(teamB).build();
            em.persist(memberB);

            Member findMemberB = em.find(Member.class, memberB.getId());
            Team findTeamB = findMemberB.getTeam();
            System.out.println("====================");
            System.out.println("findTeamB :: " + findTeamB.getName());
            System.out.println("====================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
