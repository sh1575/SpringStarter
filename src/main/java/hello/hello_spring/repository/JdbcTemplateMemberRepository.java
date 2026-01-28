package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
// @RequiredArgsConstructor = final이 붙은 필드를 객체 생성시 자동 주입
//@Primary 여러 후보 중 이 객체가 1순위로 주입된다
public class JdbcTemplateMemberRepository implements MemberRepository {

    // 스프링 프레임워크가 제공하는 JDBC 이용을 위한 핼퍼 객체
    // 즉, DB사용 코드를 줄이는 기능을 가진 객체
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateMemberRepository(DataSource dataSource){
        jdbcTemplate=new JdbcTemplate(dataSource);
    }


    @Override
    public Member save(Member member) {
        // SimpleJdbcInsert : SQL을 직접 작성하지 않고 데이터를 넣을수 있도록 도와주는 객체

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String,Object> parameters = new HashMap<>();
        parameters.put("name",member.getName()); //컬럼명과 넣을 값 매핑

        // DB가 자동으로 생성한 ID를 받기
         Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        // member 객체에 ID 저장
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // query() 가 조회, 연결, 반납을 모두 알아서 자동으로 처리
        List<Member> result = jdbcTemplate.query("SELECT * FROM member WHERE id = ?",memberRowMapper(),id);

        // 결과가 리스트이므로 stream을 사용하여 findAny() 를 통해리스트에 저장된
        // 첫번째 값을 찾아 반환
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("SELECT * FROM member WHERE name =?",memberRowMapper(),name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {

        return jdbcTemplate.query("SELECT * FROM member",memberRowMapper());
    }

    public void updateName(Long id, String newName){
       jdbcTemplate.update("UPDATE member SET name =? WHERE id = ?",newName,id);
    }

    // RowMapper : DB에서 꺼내온 한 줄(ROW)의 데이터를 자바 객체로 어떻게 바꿀지 정의

    private RowMapper<Member> memberRowMapper(){
        return (ResultSet rs, int rowNum)->{
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
}



