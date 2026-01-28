package hello.hello_spring;

import hello.hello_spring.repository.JdbcTemplateMemberRepository;
import hello.hello_spring.repository.JpaMemberRepository;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.service.MemberService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    @Autowired
    private DataSource dataSource; // JdbcTemplateMemberRepository 사용하기 위해 DI

    //datasource 대신 EntityManager 추가
    // JpaMemberRepository 사용하기 위해 DI
    @Autowired
    private EntityManager em;

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }


//    @Bean
//    public MemberRepository memberRepository(){
//        // JdbcTemplateMemberRepository에 jdbcTemplate 객체를 담아서 생성
//        return new JdbcTemplateMemberRepository(dataSource);
//    }

    @Bean
    public MemberRepository memberRepository(){
        return new JpaMemberRepository(em);
    }
}
