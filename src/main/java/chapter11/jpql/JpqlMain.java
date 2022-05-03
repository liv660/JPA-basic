package chapter11.jpql;

import chapter08.example.domain.Item;
import chapter11.jpql.domain.Member;
import chapter11.jpql.domain.Team;

import javax.persistence.*;
import java.util.Collection;
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

            //[4] 조인 - 내부 조인
            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);

            memberA.setTeam(teamA);
            em.flush();
            em.clear();

            String queryD = "SELECT m FROM eleven_member m INNER JOIN m.team t";
            List<Member> resultC = em.createQuery(queryD, Member.class).getResultList();
            System.out.println("[JOIN] resultC SIZE :: " + resultC.size());

            //[5] 서브쿼리 - FROM절에 사용 불가능
            //String queryE = "SELECT x.username FROM (SELECT new chapter11.jpql.domain.Member(m.username, m.age) FROM eleven_member m WHERE m.age = 50) x";
            //String resultD = em.createQuery(queryE, String.class).getSingleResult();
            //System.out.println("[SUBQUERY] resultD :: " + resultD);

            //[6] JPQL 타입 표현 - 엔티티 타입
            String queryF = "SELECT i FROM ex_eight_item i WHERE type(i) = ex_eight_book";  //type(E) = Entity, 상속 관계에서 사용
            List<Item> resultE = em.createQuery(queryF, Item.class).getResultList();
            System.out.println("[JPQL_TYPE] resultE SIZE :: " + resultE.size());

            System.out.println("\n----------------------------------------");
            //객체지향 쿼리 언어2
            //[1] 경로 표현식
            //[1-1] 상태 필드, 경로 탐색의 끝
            List<String> resultAa = em.createQuery("SELECT m.username FROM eleven_member m", String.class).getResultList();
            //[1-2] 단일 값 경로, 묵시적 내부조인 발생(+ 탐색 O)
            List<Team> resultAb = em.createQuery("SELECT m.team FROM eleven_member m", Team.class).getResultList();
            //[1-3] 컬렉션 값 경로, 묵시적 내부조인 발생(탐색 X)
            Collection resultAc = em.createQuery("SELECT t.members FROM eleven_team t", Collection.class).getResultList();


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
