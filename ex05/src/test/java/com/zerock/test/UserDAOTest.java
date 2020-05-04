package com.zerock.test;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.dto.LoginDTO;
import org.zerock.persistence.UserDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class UserDAOTest {

	@Inject
	private UserDAO dao;
	
	private static Logger logger = LoggerFactory.getLogger(UserDAOTest.class);
	
	@Test
	public void testLogin() throws Exception{
		
		LoginDTO dto = new LoginDTO();
		
		dto.setUid("user00");
		dto.setUpw("user00");
		
		dao.login(dto);
	}
}
