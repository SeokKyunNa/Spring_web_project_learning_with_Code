package dao.qna;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import model.qna.QnaBean;
import model.qna.QnaReBean;

public class QnaDAOImpl {
	Reader reader = null;	
	SqlSessionFactory factory = null;
	SqlSession sqlsession = null;	

	//마이파티스를 사용하기 위한 설정
	public void getSession() throws Exception {
		String resource = "util/qna/SqlMapConfig.xml";
		reader = Resources.getResourceAsReader(resource);
		factory = new SqlSessionFactoryBuilder().build(reader);		
		sqlsession = factory.openSession(true);	
	}
	
	//글의 갯수 구하기
	public int getListcount(Map<?,?> m) throws Exception{
		getSession();
		int result = 0;
		if(m.get("searchID")==null){
			if(m.get("state").equals("all")){//전체게시글 보기일 때는 매개변수 없이 갯수를 구한다.
				result = sqlsession.selectOne("qna.qna_allcount");
			}else if(!(m.get("state").equals("all"))){//특정 게시판을 선택하면 매개변수로 게시판 이름을 넣어준다.
				result = sqlsession.selectOne("qna.qna_listcount", m);
			}
		}else{
			result = sqlsession.selectOne("qna.qna_mycount", m);
		}
		return result;
	}
	
	//댓글 수 가져오기
	public int getReplyCount(int qna_num) throws Exception{
		getSession();
		int result = 0;
		result = sqlsession.selectOne("qna.qna_replycount", qna_num);
		return result;
	}
	
	//댓글 수 업데이트
	public void setReplyCount(Map<?,?> m) throws Exception{
		getSession();
		sqlsession.update("qna.qna_replyCountUpdate", m);
	}
	
	//같은 아이디에 같은 글에서 계속 댓글을 작성할 경우 댓글들을 구분하기 위해 숫자를 높여 준다.
	public void updateRe_ref(Map<?,?> m) throws Exception{
		getSession();
		sqlsession.update("qna.qna_re_ref_update", m);
	}
	
	//리스트보기에 글 담아오기
	public List<QnaBean> getList(Map<?,?> m) throws Exception{
		getSession();
		List<QnaBean> list = new ArrayList<QnaBean>();
		
		if(m.get("searchID")==null){
			if(m.get("state").equals("all")){
				list = sqlsession.selectList("qna.qna_allList", m);
			}else{
				list = sqlsession.selectList("qna.qna_list", m);
			}
		}else{
			list = sqlsession.selectList("qna.qna_mylist", m);
		}
		
		return list;
	}
	
	//글쓰기 
	public void insertQna(QnaBean qnabean) throws Exception{
		getSession();
		sqlsession.insert("qna.qna_insert", qnabean);
	}
	
	//조회수 올리기
	public void qnaHits(int qna_num) throws Exception{
		getSession();
		sqlsession.update("qna.qna_hits", qna_num);
	}
	
	//게시글 상세보기
	public List<QnaBean> getQnaDetail(int qna_num) throws Exception{
		getSession();
		List<QnaBean> qnarebean = new ArrayList<QnaBean>();
		qnarebean=sqlsession.selectList("qna.qna_prev_detail", qna_num);
		
		return qnarebean;
	}
	//리플 가져오기
	public List<QnaReBean> getReplyList(int qna_num) throws Exception{
		getSession();
		List<QnaReBean> qnarebean = new ArrayList<QnaReBean>();
		qnarebean= sqlsession.selectList("qna.qna_getreply", qna_num);
		return qnarebean;
	}
	
	//댓글 달기
	public void insertReply(QnaReBean qnarebean) throws Exception{
		getSession();
		if(qnarebean.getQna_re_lev() != 1){//일반 댓글일 경우
			sqlsession.insert("qna.qna_reply_write", qnarebean);
		}else if(qnarebean.getQna_re_lev() == 1){//댓글의 댓글일 경우
			//qns_re_seq 번호도 올려 준다.
			sqlsession.insert("qna.qna_level", qnarebean);
			
			sqlsession.insert("qna.qna_re_reply_write", qnarebean);
			
		}
		
	}
	
	//글 삭제
	public void deleteWrite(int qna_num) throws Exception{
		getSession();
		sqlsession.delete("qna.qna_delete", qna_num);
		//댓글도 같이 삭제한다.
		sqlsession.delete("qna.qna_reply", qna_num);
	}
	//댓글 삭제
	public void deleteReply(Map<?,?> m) throws Exception{
		getSession();
		sqlsession.delete("qna.qna_delete_reply", m);
	}
	//수정하기 화면으로 이동
	public QnaBean getDetailEdit(int qna_num) throws Exception{
		getSession();
		return (QnaBean)sqlsession.selectOne("qna.qna_detail", qna_num);
	}
	//수정하기
	public void updateEdit(QnaBean qnabean) throws Exception{
		getSession();
		sqlsession.update("qna.qna_edit", qnabean);
	}
	//검색글 갯수 구하기
	public int getSearchCount(Map<?,?> m) throws Exception{
		getSession();
		int num = sqlsession.selectOne("qna.qna_search_count", m);
		return num;
	}
	//검색글 리스트에 담아오기
	public List<QnaBean> getSearchList(Map<?,?> m) throws Exception{
		getSession();
		List<QnaBean> list = new ArrayList<QnaBean>();
		list = sqlsession.selectList("qna.qna_search_list", m);
		return list;
	}
}
