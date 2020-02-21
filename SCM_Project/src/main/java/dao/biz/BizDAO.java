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

	// ä����� ���� ���ϱ�
	public int biz_getListCount() throws Exception {
		getSession();
		int ListCount = ((Integer)sqlsession.selectOne("biz_getListCount")).intValue();
		return ListCount;
	}
	
	// ä����� ��� ����
	public List<BizBean> biz_getList(Map m) throws Exception{
		getSession();
		List<BizBean> bizList = sqlsession.selectList("biz_getList", m);
		return bizList;
	}
	
	// ��ü ��Ͽ��� ���ã���� �Խù��� �ɷ����� ���� sql
	public List<BizFavoriteBean> biz_getFavoriteList(String biz_user_favorite) throws Exception{
		getSession();
		List<BizFavoriteBean> favoriteList = sqlsession.selectList("biz_getFavoriteList", biz_user_favorite);
		return favoriteList;
	}
	
	// ���� ����� ä����� ���� ���ϱ�
	public int biz_getMyListCount(String biz_user) throws Exception {
		getSession();
		int ListCount = ((Integer)sqlsession.selectOne("biz_getMyListCount", biz_user)).intValue();
		return ListCount;
	}
		
	// ���� ����� ä����� ��� ����
	public List<BizBean> biz_getMyList(Map m) throws Exception{
		getSession();
		List<BizBean> bizList = sqlsession.selectList("biz_getMyList", m);
		return bizList;
	}
	
	// ���ã���� ä����� ���� ���ϱ�
		public int biz_getMyFavoriteListCount(String biz_user_favorite) throws Exception {
			getSession();
			int ListCount = ((Integer)sqlsession.selectOne("biz_getMyFavoriteListCount", biz_user_favorite)).intValue();
			return ListCount;
		}
		
	// ���ã���� ��� ����
	public List<BizBean> biz_getMyFavoriteList(Map m) throws Exception{
		getSession();
		List<BizBean> bizList = sqlsession.selectList("biz_getMyFavoriteList", m);
		return bizList;
	}
	
	// ä����� ���� ����
	public BizBean biz_getCont(int num) throws Exception{
		getSession();
		return (BizBean) sqlsession.selectOne("biz_getCont", num);
	}
	
	// ä����� ����ϱ�
	public void biz_Insert(BizBean bizbean) throws Exception{
		getSession();
		sqlsession.insert("biz_Insert", bizbean);
	}
	
	// ä����� �����ϱ�
	public void biz_Modify(BizBean bizbean) throws Exception{
		getSession();
		sqlsession.update("biz_Modify", bizbean);
	}
	
	//ä����� ����
	public void biz_Delete(int num) throws Exception {
		getSession();
		sqlsession.delete("biz_Delete", num);
	}
	
	// �����ڼ� ������Ʈ
	public void biz_CountUpdate(int num) throws Exception{
		getSession();
		sqlsession.update("biz_CountUpdate", num);
	}
	
	// ���ã�� �߰�
	public void biz_FavoriteOn(Map m) throws Exception{
		getSession();
		sqlsession.insert("biz_FavoriteOn", m);
	}
	
	// ���ã�� ���
	public void biz_FavoriteOff(Map m) throws Exception {
		getSession();
		sqlsession.delete("biz_FavoriteOff", m);
	}
	
	public int biz_contFavorite(Map m) throws Exception {
		getSession();
		int favoriteOnOff = sqlsession.selectOne("biz_contFavorite", m);
		return favoriteOnOff;
	}
	
	/*���� �˻�*/
	public List<ZipcodeBean2> findZipcode(String dong) throws Exception{
		getSession();
		List<ZipcodeBean2> list=sqlsession.selectList("zipcodeList",dong);
		return list;
	}
	
	// ȸ��� �˻� ���� ���ϱ�
	public int biz_searchNameCount(String search) throws Exception {
		getSession();
		int ListCount = ((Integer)sqlsession.selectOne("biz_searchNameCount", search)).intValue();
		return ListCount;
	}
		
	// ȸ��� �˻� ��� ����
	public List<BizBean> biz_searchNameList(Map m) throws Exception{
		getSession();
		List<BizBean> bizList = sqlsession.selectList("bizNameSearch", m);
		return bizList;
	}
	
	// ���� �˻� ���� ���ϱ�
	public int biz_searchSubjectCount(String search) throws Exception {
		getSession();
		int ListCount = ((Integer)sqlsession.selectOne("biz_searchSubjectCount", search)).intValue();
		return ListCount;
	}
		
	// ���� �˻� ��� ����
	public List<BizBean> biz_searchSubjectList(Map m) throws Exception{
		getSession();
		List<BizBean> bizList = sqlsession.selectList("bizSubjectSearch", m);
		return bizList;
	}
	
	// ���� �˻� ���� ���ϱ�
	public int biz_searchLocCount(String search) throws Exception {
		getSession();
		int ListCount = ((Integer)sqlsession.selectOne("biz_searchLocCount", search)).intValue();
		return ListCount;
	}
		
	// ���� �˻� ��� ����
	public List<BizBean> biz_searchLocList(Map m) throws Exception{
		getSession();
		List<BizBean> bizList = sqlsession.selectList("bizLocSearch", m);
		return bizList;
	}
}
