package chapter04;

import chapter03.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class PersistenceContext {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        //트랜잭션 시작
        tx.begin();

        System.out.println("******* 첫번째 트랜잭션 *******");
        try {
            //1. Entity 생성, 비영속 상태
            Member member1 = Member.builder().id(1L).name("HelloA").build();
            Member member2 = Member.builder().id(2L).name("HelloB").build();

            //2. 영속 상태, DB 저장 전 = BEFORE, AFTER 어디에서도 쿼리문 호출 없음.
            System.out.println("[BEFORE] 비영속일 때 쿼리문 호출?");
            em.persist(member1);
            em.persist(member2);
            System.out.println("[AFTER] 영속일 때 쿼리문 호출?");

            //3. 준영속 상태, 영속성 컨텍스트에서 분리
            //em.detach(member1);

            //4. 삭제, 객체를 영구적으로 제거
            //em.remove(member1);

            //5. 1차 캐시에서 조회하기 (= 조회 쿼리 없이 1차 캐시에서 가져옴.)
            Member findMember = em.find(Member.class, 1L);
            System.out.println("=========================");
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.name = " + findMember.getName());
            System.out.println("=========================");

            tx.commit();

            //5-2. 1차 캐시에 없을 땐 SELECT 문으로 DB에서 조회 (DB에 있다면 결과를 1차 캐시에 저장함)
            em.find(Member.class, 101L);

            //6. 영속 엔티티의 동일성 보장 (같은 트랜잭션 내에서)
            Member a = em.find(Member.class, 1L);
            Member b = em.find(Member.class, 1L);
            System.out.println("=========================");
            System.out.println("a == b ? :: " + (a == b));
            System.out.println("=========================");
        } catch (Exception e) {
            tx.rollback();
        }

        //트랜잭션 시작
        tx.begin();

        System.out.println("\n\n\n\n\n******* 두번째 트랜잭션 *******");
        try {
            //7. 트랜잭션 쓰기 지연
            Member member3 = Member.builder().id(3L).name("HelloC").build();
            Member member4 = Member.builder().id(4L).name("HelloD").build();
            Member member5 = Member.builder().id(5L).name("HelloE").build();

            em.persist(member3);
            em.persist(member4);
            em.persist(member5);

            System.out.println("[INSERT 쿼리 호출 위치 확인]");
            tx.commit();    //영속성 컨텍스트에 저장된 엔티티가 DB에 저장됨(flush)

        } catch (Exception e) {
            tx.rollback();
        }

        //트랜잭션 시작
        tx.begin();

        System.out.println("\n\n\n\n\n******* 세번째 트랜잭션 *******");
        try {
            //8. Update문 없이 엔티티 수정 변경 감지
            Member findMember2 = em.find(Member.class, 3L);
            findMember2.setName("HelloZ");

            tx.commit();    //변경 내용 DB 반영

            //9. 특정 엔티티만 준영속 상태로 만들기
            em.detach(findMember2);
        } catch (Exception e) {

        } finally {
            em.close();
        }
        emf.close();
    }
}
