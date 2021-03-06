package org.zerock.test;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.MessageVO;
import org.zerock.persistence.MessageDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class MessageDAOTest {

	@Inject
	private MessageDAO dao;
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(MessageDAOTest.class);
	
	@Test
	public void testAddMessage() throws Exception{
		MessageVO message = new MessageVO();
		message.setMessage("Good Luck!");
		message.setTargetid("user01");
		message.setSender("user00");
		
		dao.create(message);
	}
	/*
	@Test
	public void testCreate() throws Exception{
		BoardVO board = new BoardVO();
		board.setTitle("새로운 제목을 넣습니다.");
		board.setContent("새로운 글을 넣습니다.");
		board.setWriter("user00");
		dao.create(board);
	}
	*/
	/*
	@Test
	public void testRead() throws Exception{
		logger.info(dao.read(1).toString());
	}
	*/
	/*
	@Test
	public void testUpdate() throws Exception{
		BoardVO board = new BoardVO();
		board.setBno(1);
		board.setTitle("수정된 글입니다.");
		board.setContent("수정 테스트");
		dao.update(board);
	}
	*/
	/*
	@Test
	public void testDelete() throws Exception{
		dao.delete(1);
	}
	*/
	/*
	@Test
	public void testListAll() throws Exception{
		logger.info(dao.listAll().toString());
	}
	*/
	/*
	@Test
	public void testListPage() throws Exception{
		
		int page = 3;
		int limit = 10;
		
		List<BoardVO> list = dao.listPage(page, limit);
		
		for(BoardVO boardVO : list) {
			logger.info(boardVO.getBno() + " : " + boardVO.getTitle());
		}
	}
	*/
	/*
	@Test
	public void testListCriteria() throws Exception{
		
		Criteria cri = new Criteria();
		cri.setPage(2);
		cri.setNumPerPage(20);
		
		List<BoardVO> list = dao.listCriteria(cri);
		
		for(BoardVO boardVO : list) {
			logger.info(boardVO.getBno() + " : " + boardVO.getTitle());
		}
	}
	*/
	/*
	@Test
	public void testURI() throws Exception{
		
		UriComponents uriComponents = UriComponentsBuilder.newInstance().path("/board/read").queryParam("bno",  12).queryParam("numPerPage", 20).build();
		
		logger.info("/board/read?bno=12&numPerPage=20");
		logger.info(uriComponents.toString());
	}
	*/
	/*
	@Test
	public void testURI2() throws Exception{
		
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
				.path("{module}/{page}").queryParam("bno", 12).queryParam("numPerPage", 20).build().expand("board", "read").encode();
		
		logger.info("/board/read?bno=12&numPerPage=20");
		logger.info(uriComponents.toString());
	}
	*/
	/*
	@Test
	public void testDynamic1() throws Exception{
		
		SearchCriteria cri = new SearchCriteria();
		cri.setPage(1);
		cri.setKeyword("7");
		cri.setSearchType("t");
		
		logger.info("======================================");
		
		List<BoardVO> list = dao.listSearch(cri);
		
		for(BoardVO boardVO : list) {
			logger.info(boardVO.getBno() + " : " + boardVO.getTitle());
		}
		
		logger.info("======================================");
		
		logger.info("COUNT : " + dao.listSearchCount(cri));
	}
	*/
}
