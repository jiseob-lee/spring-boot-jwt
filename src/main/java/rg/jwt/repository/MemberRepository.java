package rg.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rg.jwt.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 쿼리 메서드
    Member findMemberByEmail(String email);

}

