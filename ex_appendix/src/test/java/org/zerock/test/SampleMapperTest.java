package org.zerock.test;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.persistence.SampleMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class SampleMapperTest {

	@Inject
	private SampleMapper mapper;
	
	@Test
	public void testTime() {
		
		System.out.println(mapper.getClass().getName());
		
		System.out.println(mapper.getTime());
		System.out.println();
		
	}

	@Test
	public void testMail() {
		
		String email = mapper.getEmail("user00",  "user00");
		
		System.out.println(email);
		System.out.println();
		
	}
	
	@Test
	public void testUserName() {
		
		String name = mapper.getUserName("user00", "user00");
		
		System.out.println(name);
		System.out.println();
		
	}
	
	@Test
	public void testSearch() {
		
		String query = mapper.search("id", "user00");
		
		System.out.println(query);
		System.out.println();
		
	}
	
}
