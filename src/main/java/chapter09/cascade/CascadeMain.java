package chapter09.cascade;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class CascadeMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Parent parent = new Parent();
            Child child1 = new Child();
            Child child2 = new Child();

            parent.addChild(child1);
            parent.addChild(child2);

            //CASCADE 적용 전
            //em.persist(parent);
            //em.persist(child1);
            //em.persist(child2);

            //CASCADE 적용 후
            em.persist(parent);
            System.out.println("====================");

            em.flush();
            em.clear();

            //orphanRemoval 적용 후 (CascadeType.REMOVE 처럼 동작한다.)
            Parent findParent = em.find(Parent.class, parent.getId());
            findParent.getChildList().remove(0);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
