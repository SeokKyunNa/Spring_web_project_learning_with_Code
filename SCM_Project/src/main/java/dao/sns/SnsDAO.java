package dao.sns;

import java.io.Reader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.sns.SnsBean;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SnsDAO {
	Reader reader = null;	
	SqlSessionFactory factory = null;
	SqlSession sqlsession = null;	

	public void getSession() throws Exception {
		String resource = "util/sns/SqlMapConfig.xml";
		reader = Resources.getResourceAsReader(resource);
		factory = new SqlSessionFactoryBuilder().build(reader);		
		sqlsession = factory.openSession(true);	// true : auto commit 
	//	sqlsession = factory.openSession();
	}
	//게시글 개수 구하기
		public int sns_getListCount() throws Exception {
			getSession();
			int ListCount = ((Integer)sqlsession.selectOne("sns_getListCount")).intValue();
			return ListCount;
		}
		
	//좋아요한 글개수 구하기
		public int sns_getgoodCount(String goodid) throws Exception {
			getSession();
			int goodCount = ((Integer)sqlsession.selectOne("sns_getgoodCount",goodid)).intValue();
			return goodCount;
		}
		
	//게시글 삭제
	public void snsDelete(int num) throws Exception {
		getSession();
		sqlsession.delete("sns_delete", num);	
	}
	
	//sns 내용
	public List<SnsBean> getSnsList(Map m) 
			throws Exception {
		getSession();

		List<SnsBean> list = 
	  	 sqlsession.selectList("sns_list", m);
		return list;
	}
	
	//sns 내용(전체)
	public List<SnsBean> getSnsListAll(Map m) 
			throws Exception {
		getSession();
		List<SnsBean> list = 
	  	 sqlsession.selectList("sns_list_all",m);
		return list;
	}
	
	//sns 최신글 사용자  불러오기
	public List<SnsBean> getSnsUser() throws Exception{
		getSession();
		List<SnsBean> userlist = sqlsession.selectList("sns_user_list");
		return userlist;
	}
	
	//sns 좋아요 글
	public List<SnsBean> getSnsListGood1(Map m) 
			throws Exception {
		getSession();
		List<SnsBean> list = 
	  	 sqlsession.selectList("sns_list_good1",m);
		return list;
	}

	
	/*좋아요  DB가져오기*/
		public List<SnsBean> getSnsListGood(String id) 
				throws Exception {
			getSession();

			List<SnsBean> list = 
		  	 sqlsession.selectList("sns_list_good",id);
			return list;
		}
		
	
	
	/* 글 저장 */
	public void insertSns(SnsBean snsbean) throws Exception {
		getSession();		
		sqlsession.insert("sns_insert", snsbean);
	}

	/*글 자세히 보기*/
	public SnsBean getSnsCont(int num) throws Exception {
		getSession();
		
		return (SnsBean) sqlsession.selectOne("sns_cont",num);
	}
	
	/*sns글 수정하기*/
	public void snsEdit(SnsBean snsbean) throws Exception {
		getSession();
		sqlsession.update("sns_edit", snsbean);

	}
	
	/*댓글 원본글 번호 구하기*/
	public int getReplyCount(int num) throws Exception {
		getSession();
		
		return ((Integer) sqlsession.selectOne("sns_reply",num)).intValue();
	}
	
	/* 댓글 저장 */
	public void insertSns_reply(SnsBean snsbean) throws Exception {
		getSession();		
		sqlsession.insert("sns_re_insert", snsbean);
	}
	
	/*댓글 보기*/
	public List<SnsBean> getReplyCont(int num) throws Exception {
		getSession();
		List<SnsBean> list = 
			  	 sqlsession.selectList("reply_cont",num);
				return list;
	}
	
	/* 좋아요 추가 */
	public void insertSns_good(Map m) throws Exception{
		getSession();
		sqlsession.insert("sns_good_insert",m);
	}
	
	/* 좋아요 삭제 */
	public void deleteSns_good(Map m) throws Exception{
		getSession();
		sqlsession.delete("sns_good_delete", m);
	}
	
	/* 해당글 좋아요 카운트(추가) */
	public void updateSns_good_in(int num) throws Exception{
		getSession();
		sqlsession.update("good_update_in",num);
		
	}
	
	/* 해당글 좋아요 카운트(삭제) */
	public void updateSns_good_de(int num) throws Exception{
		getSession();
		sqlsession.update("good_update_de",num);
		
	}
	
	/* 해당글 좋아요 카운트(추가) */
	public void reply_update_in(int num) throws Exception{
		getSession();
		sqlsession.update("reply_update_in",num);
		
	}
	
	/* 해당글 댓글 카운트(삭제) */
	public void reply_update_de(int num) throws Exception{
		getSession();
		sqlsession.update("reply_update_de",num);
		
	}
	
	/* 댓글 삭제 */
	public void replyDelete(Map m) throws Exception {
		getSession();
		sqlsession.delete("reply_delete", m);
		
	}
	
	public int getcount(String id) throws Exception{
		getSession();
		int count = sqlsession.selectOne("count_sns", id);
		return count;
	}

}
