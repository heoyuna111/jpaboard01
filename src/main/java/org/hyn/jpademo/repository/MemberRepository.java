package org.hyn.jpademo.repository;

import org.hyn.jpademo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByName(String name);
//    Member findByUsername(String username); : username이란 필드가 없어서 오류
    List<Member> findByNameLike(String name);

}
