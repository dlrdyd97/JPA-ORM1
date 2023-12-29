package jpa_basic.hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();


        // code
        tx.begin();

        try {

            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.changeTeam(team);
            em.persist(member);

            // 양방향 연관관계에는 양쪽에 값을 세팅해주는것이 좋음
//            team.getMembers().add(member);

            Team findTeam = em.find(Team.class, team.getId());
            List<Member> members = findTeam.getMembers();
            System.out.println("=============");
            for (Member m : members) {
                System.out.println("m = " + m.getUsername());
            }
            System.out.println("=============");

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();

    }
}
