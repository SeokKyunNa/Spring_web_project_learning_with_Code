package dao.biz;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.biz.*;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class BizDAO {
	Reader reader = null;	
	SqlSessionFactory factory = null;
	SqlSession sqlsession = null;	

	public void getSession() throws Exception {
		String resource = "util/biz/SqlMapConfig.xml";
		reader = Resources.getResourceAsReader(resource);
		factory = new SqlSessionFactoryBuilder().build(reader);		
		sqlsession = factory.openSession(true);	// true : auto commit 
	}

	// 채용공고 개수 구하기
	public int biz_getListCount() throws Exception {
		getSession();
		int ListCount = ((Integer)sqlsession.selectOne("biz_getListCount")).intValue();
		return ListCount;
	}
	
	// 채용공고 목록 보기
	public List<BizBean> biz_getList(Map m) throws Exception{
		getSession();
		List<BizBean> bizList = sqlsession.selectList("biz_getList", m);
		return bizList;
	}
	
	// 전체 목록에서 즐겨찾기한 게시물을 걸러내기 위한 sql
	public List<BizFavoriteBean> biz_getFavoriteList(String biz_user_favorite) throws Exception{
		getSession();
		List<BizFavoriteBean> favoriteList = sqlsession.selectList("biz_getFavoriteList", biz_user_favorite);
		return favoriteList;
	}
	
	// 내가 등록한 채용공고 개수 구하기
	public int biz_getMyListCount(String biz_user) throws Exception {
		getSession();
		int ListCount = ((Integer)sqlsession.selectOne("biz_getMyListCount", biz_user)).intValue();
		return ListCount;
	}
		
	// 내가 등록한 채용공고 목록 보기
	public List<BizBean> biz_getMyList(Map m) throws Exception{
		getSession();
		List<BizBean> bizList = sqlsession.selectList("biz_getMyList", m);
		return bizList;
	}
	
	// 즐겨찾기한 채용공고 개수 구하기
		public int biz_getMyFavoriteListCount(String biz_user_favorite) throws Exception {
			getSession();
			int ListCount = ((Integer)sqlsession.selectOne("biz_getMyFavoriteListCount", biz_user_favorite)).intValue();
			return ListCount;
		}
		
	// 즐겨찾기한 목록 보기
	public List<BizBean> biz_getMyFavoriteList(Map m) throws Exception{
		getSession();
		List<BizBean> bizList = sqlsession.selectList("biz_getMyFavoriteList", m);
		return bizList;
	}
	
	// 채용공고 내용 보기
	public BizBean biz_getCont(int num) throws Exception{
		getSession();
		return (BizBean) sqlsession.selectOne("biz_getCont", num);
	}
	
	// 채용공고 등록하기
	public void biz_Insert(BizBean bizbean) throws Exception{
		getSession();
		sqlsession.insert("biz_Insert", bizbean);
	}
	
	// 채용공고 수정하기
	public void biz_Modify(BizBean bizbean) throws Exception{
		getSession();
		sqlsession.update("biz_Modify", bizbean);
	}
	
	//채용공고 삭제
	public void biz_Delete(int num) throws Exception {
		getSession();
		sqlsession.delete("biz_Delete", num);
	}
	
	// 지원자수 업데이트
	public void biz_CountUpdate(int num) throws Exception{
		getSession();
		sqlsession.update("biz_CountUpdate", num);
	}
	
	// 즐겨찾기 추가
	public void biz_FavoriteOn(Map m) throws Exception{
		getSession();
		sqlsession.insert("biz_FavoriteOn", m);
	}
	
	// 즐겨찾기 취소
	public void biz_FavoriteOff(Map m) throws Exception {
		getSession();
		sqlsession.delete("biz_FavoriteOff", m);
	}
	
	public int biz_contFavorite(Map m) throws Exception {
		getSession();
		int favoriteOnOff = sqlsession.selectOne("biz_contFavorite", m);
		return favoriteOnOff;
	}
	
	/*우편 검색*/
	public List<ZipcodeBean2> findZipcode(String dong) throws Exception{
		getSession();
		List<ZipcodeBean2> list=sqlsession.selectList("zipcodeList",dong);
		return list;
	}
	
	// 회사명 검색 개수 구하기
	public int biz_searchNameCount(String search) throws Exception {
		getSession();
		int ListCount = ((Integer)sqlsession.selectOne("biz_searchNameCount", search)).intValue();
		return ListCount;
	}
		
	// 회사명 검색 목록 보기
	public List<BizBean> biz_searchNameList(Map m) throws Exception{
		getSession();
		List<BizBean> bizList = sqlsession.selectList("bizNameSearch", m);
		return bizList;
	}
	
	// 제목 검색 개수 구하기
	public int biz_searchSubjectCount(String search) throws Exception {
		getSession();
		int ListCount = ((Integer)sqlsession.selectOne("biz_searchSubjectCount", search)).intValue();
		return ListCount;
	}
		
	// 제목 검색 목록 보기
	public List<BizBean> biz_searchSubjectList(Map m) throws Exception{
		getSession();
		List<BizBean> bizList = sqlsession.selectList("bizSubjectSearch", m);
		return bizList;
	}
	
	// 지역 검색 개수 구하기
	public int biz_searchLocCount(String search) throws Exception {
		getSession();
		int ListCount = ((Integer)sqlsession.selectOne("biz_searchLocCount", search)).intValue();
		return ListCount;
	}
		
	// 지역 검색 목록 보기
	public List<BizBean> biz_searchLocList(Map m) throws Exception{
		getSession();
		List<BizBean> bizList = sqlsession.selectList("bizLocSearch", m);
		return bizList;
	}
}
