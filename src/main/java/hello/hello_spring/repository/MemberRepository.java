package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;

import java.util.List;
import java.util.Optional;

//회원 등록과 조회(id 조회 , 이름 조회 , 전체 목록 조회) - 총 4가지 기능 선언
public interface MemberRepository {
   Member save(Member member); // 등록

    //현재 현업에서 null 처리하는 방법으로 null을 그대로 반환하는 것이 아니라
    // Optional 객체로 반환하는 것을 많이 선호
   Optional<Member> findById(Long id);
   Optional<Member> findByName(String name);

   List<Member> findAll();
}
