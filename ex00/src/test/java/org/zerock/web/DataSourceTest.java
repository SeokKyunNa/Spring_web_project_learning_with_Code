package org.zerock.web;

import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class DataSourceTest {
	
	@Inject
	private DataSource ds;
	
	@Test
	public void testConnection() throws Exception{
		try (Connection con = ds.getConnection()){
			System.out.println(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/*try {
			String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "zerock", "zerock12#");
			System.out.println(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		*/
	}

}
