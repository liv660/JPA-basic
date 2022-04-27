package chapter10.embeded;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class EmbededMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            //임베디드 타입 활용
            Member memberA = Member.builder()
                                        .name("memberA")
                                        .homeAddress(
                                                Address.builder().city("city").street("street").zipcode("10000").build()
                                        ).workPeriod(new Period())
                                    .build();
            em.persist(memberA);

            //값 타입 공유 참조의 side effect
            Address address = Address.builder().city("city2").street("street2").zipcode("20000").build();
            Member memberB = Member.builder()
                                    .name("memberB")
                                    .homeAddress(address)
                                    .build();
            Member memberC = Member.builder()
                                    .name("memberC")
                                    .homeAddress(address)
                                    .build();
            em.persist(memberB);
            em.persist(memberC);

            //memberB.getHomeAddress().setCity("newCity");    //memberB 와 memberC의 city 값이 모두 newCity로 변경된다.

            //따라서 값 타입은 공유 대신 복사를 사용
            memberC.setHomeAddress(
                    // == new Address() ...
                    Address.builder()
                            .city("city3")
                            .street(address.getStreet())
                            .zipcode(address.getZipcode())
                            .build()
                    );
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
