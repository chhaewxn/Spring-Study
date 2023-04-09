package hello.hellospring.repository;


import hello.hellospring.domain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {
    private final EntityManager em; //Jpa를 사용하려면 EntityManager를 주입받아야함
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }
    public Member save(Member member) {
        em.persist(member);
        return member;
    }
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList(); // inline Variable로 합치기
    }
    public Optional<Member> findByName(String name) { //pk 기반이 아니여서 jpql 작성
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class) // 객체 자체를 select
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }
}