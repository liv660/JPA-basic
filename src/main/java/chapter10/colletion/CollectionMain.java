package chapter10.colletion;

import chapter10.embeded.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Member memberA = Member.builder()
                                        .name("memberA")
                                        .homeAddress(Address.builder()
                                                            .city("homeCity").street("street").zipcode("10000")
                                                        .build())
                                        .favoriteFoods(new HashSet<String>())
                                        .addressHistory(new ArrayList<Address>())
                                        .altAddressHistory(new ArrayList<AddressEntity>())  //[2-5-1] 값 타입 컬렉션 대안 적용
                                    .build();
            memberA.getFavoriteFoods().add("치킨");
            memberA.getFavoriteFoods().add("족발");
            memberA.getFavoriteFoods().add("피자");

            memberA.getAddressHistory().add(new Address("old1", "street", "10000"));
            memberA.getAddressHistory().add(new Address("old2", "street", "10000"));

            //[2-5-2] 값 타입 컬렉션 대안 적용
            memberA.getAltAddressHistory().add(new AddressEntity("old3", "street", "20000"));
            memberA.getAltAddressHistory().add(new AddressEntity("old4", "street", "20000"));

            em.persist(memberA);
            em.flush();
            em.clear();

            System.out.println("====================[START]====================");
            //[1] 조회
            Member findMember = em.find(Member.class, memberA.getId()); //LAZY LOADING
            System.out.println("-----------------------------------------------");
            List<Address> addressHistory = findMember.getAddressHistory();
            for(Address address : addressHistory) {
                System.out.println("address = " + address.getCity());
            }
            System.out.println("-----------------------------------------------");
            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for(String favoriteFood : favoriteFoods) {
                System.out.println("favoriteFood = " + favoriteFood);
            }
            System.out.println("-----------------------------------------------");
            //[2] 수정
            //[2-1] homeCity -> newCity 잘못된 수정 방법, 부작용 발생
            //findMember.getHomeAddress().setCity("newCity");

            //[2-2] 값타입의 올바른 수정 방법, 값타입 객체 새로 생성
            Address preAdress = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", preAdress.getStreet(), preAdress.getZipcode()));

            //[2-3] 치킨 -> 한식
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            //[2-4] old1 삭제 및 newCity2 추가 :: Address 테이블의 데이터를 완전 삭제 후, 데이터를 INSERT 한다.
            findMember.getAddressHistory().remove(new Address("old1", "street", "10000"));
            findMember.getAddressHistory().add(new Address("newCity2", "street", "10000"));

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
