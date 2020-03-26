package org.zerock;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.MemberVO;
import org.zerock.mapper.MemberMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
class ExSpringbootApplicationTests {

	@Autowired
	private DataSource ds;
	
	@Autowired
	private SqlSessionFactory sqlSession;
	
	@Autowired
	private MemberMapper mapper;
	/*
	@Test
	void contextLoads() {
	}
	
	@Test
	public void testConnection() throws Exception{
		
		System.out.println(ds);
		
		Connection con = ds.getConnection();
		
		System.out.println(con);
		
		con.close();
		
	}

	@Test
	public void testSqlSession() throws Exception{
		
		System.out.println(sqlSession);
		
	}
	
	@Test
	public void testInsert() throws Exception{
		
		MemberVO vo = new MemberVO();
		
		vo.setUserid("user10");
		vo.setUserpw("user10");
		vo.setUsername("Billy");
		vo.setEmail("zerockcode@gmail.com");
		
		mapper.create(vo);
		
	}
	*/
	@Test
	public void testLogin() throws Exception{
		
		MemberVO vo = mapper.login("user10", "user10");
		
		System.out.println(vo);
		
	}
}
