package org.zerock;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ExSpringbootApplicationTests {

	@Autowired
	private DataSource ds;
	
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

}
