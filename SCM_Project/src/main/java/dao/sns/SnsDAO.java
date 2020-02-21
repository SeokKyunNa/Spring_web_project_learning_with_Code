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
	//�Խñ� ���� ���ϱ�
		public int sns_getListCount() throws Exception {
			getSession();
			int ListCount = ((Integer)sqlsession.selectOne("sns_getListCount")).intValue();
			return ListCount;
		}
		
	//���ƿ��� �۰��� ���ϱ�
		public int sns_getgoodCount(String goodid) throws Exception {
			getSession();
			int goodCount = ((Integer)sqlsession.selectOne("sns_getgoodCount",goodid)).intValue();
			return goodCount;
		}
		
	//�Խñ� ����
	public void snsDelete(int num) throws Exception {
		getSession();
		sqlsession.delete("sns_delete", num);	
	}
	
	//sns ����
	public List<SnsBean> getSnsList(Map m) 
			throws Exception {
		getSession();

		List<SnsBean> list = 
	  	 sqlsession.selectList("sns_list", m);
		return list;
	}
	
	//sns ����(��ü)
	public List<SnsBean> getSnsListAll(Map m) 
			throws Exception {
		getSession();
		List<SnsBean> list = 
	  	 sqlsession.selectList("sns_list_all",m);
		return list;
	}
	
	//sns �ֽű� �����  �ҷ�����
	public List<SnsBean> getSnsUser() throws Exception{
		getSession();
		List<SnsBean> userlist = sqlsession.selectList("sns_user_list");
		return userlist;
	}
	
	//sns ���ƿ� ��
	public List<SnsBean> getSnsListGood1(Map m) 
			throws Exception {
		getSession();
		List<SnsBean> list = 
	  	 sqlsession.selectList("sns_list_good1",m);
		return list;
	}

	
	/*���ƿ�  DB��������*/
		public List<SnsBean> getSnsListGood(String id) 
				throws Exception {
			getSession();

			List<SnsBean> list = 
		  	 sqlsession.selectList("sns_list_good",id);
			return list;
		}
		
	
	
	/* �� ���� */
	public void insertSns(SnsBean snsbean) throws Exception {
		getSession();		
		sqlsession.insert("sns_insert", snsbean);
	}

	/*�� �ڼ��� ����*/
	public SnsBean getSnsCont(int num) throws Exception {
		getSession();
		
		return (SnsBean) sqlsession.selectOne("sns_cont",num);
	}
	
	/*sns�� �����ϱ�*/
	public void snsEdit(SnsBean snsbean) throws Exception {
		getSession();
		sqlsession.update("sns_edit", snsbean);

	}
	
	/*��� ������ ��ȣ ���ϱ�*/
	public int getReplyCount(int num) throws Exception {
		getSession();
		
		return ((Integer) sqlsession.selectOne("sns_reply",num)).intValue();
	}
	
	/* ��� ���� */
	public void insertSns_reply(SnsBean snsbean) throws Exception {
		getSession();		
		sqlsession.insert("sns_re_insert", snsbean);
	}
	
	/*��� ����*/
	public List<SnsBean> getReplyCont(int num) throws Exception {
		getSession();
		List<SnsBean> list = 
			  	 sqlsession.selectList("reply_cont",num);
				return list;
	}
	
	/* ���ƿ� �߰� */
	public void insertSns_good(Map m) throws Exception{
		getSession();
		sqlsession.insert("sns_good_insert",m);
	}
	
	/* ���ƿ� ���� */
	public void deleteSns_good(Map m) throws Exception{
		getSession();
		sqlsession.delete("sns_good_delete", m);
	}
	
	/* �ش�� ���ƿ� ī��Ʈ(�߰�) */
	public void updateSns_good_in(int num) throws Exception{
		getSession();
		sqlsession.update("good_update_in",num);
		
	}
	
	/* �ش�� ���ƿ� ī��Ʈ(����) */
	public void updateSns_good_de(int num) throws Exception{
		getSession();
		sqlsession.update("good_update_de",num);
		
	}
	
	/* �ش�� ���ƿ� ī��Ʈ(�߰�) */
	public void reply_update_in(int num) throws Exception{
		getSession();
		sqlsession.update("reply_update_in",num);
		
	}
	
	/* �ش�� ��� ī��Ʈ(����) */
	public void reply_update_de(int num) throws Exception{
		getSession();
		sqlsession.update("reply_update_de",num);
		
	}
	
	/* ��� ���� */
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
