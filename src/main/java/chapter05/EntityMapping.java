package chapter05;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.Date;

public class EntityMapping {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            //[기본키 매핑] @GeneratedValue(strategy = GenerationType.IDENTITY), DB에서 id 값을 확인할 수 있음
            Member memberA = Member.builder()
                    .userName("userA")
                    .description("")
                    .createDate(new Date())
                    .lastModifiedDate(LocalDateTime.now())
                    .roleType(RoleType.USER)
                    .build();
            Member memberB = Member.builder()
                    .userName("userB")
                    .description("")
                    .createDate(new Date())
                    .lastModifiedDate(LocalDateTime.now())
                    .roleType(RoleType.USER)
                    .build();
            em.persist(memberA);
            em.persist(memberB);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
