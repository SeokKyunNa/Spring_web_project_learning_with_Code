package dao.mem;

import java.io.Reader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.mem.MemberBean;
import model.mem.ZipcodeBean2;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


public class MemberDAOImpl {
	
	Reader reader = null;	
	SqlSessionFactory factory = null;
	SqlSession sqlsession = null;	

	public void getSession() throws Exception {
		String resource = "util/mem/SqlMapConfig.xml";
		reader = Resources.getResourceAsReader(resource);
		factory = new SqlSessionFactoryBuilder().build(reader);		
		sqlsession = factory.openSession(true);	// true : auto commit 
	//	sqlsession = factory.openSession();
	}

	/***** ���̵� �ߺ� üũ *****/
	public int checkMemberId(String id) throws Exception{
		getSession();
		int re=-1; 
		MemberBean mb = (MemberBean) sqlsession.selectOne("member.login_check1",id);
		if(mb != null) re=1;	// �ߺ�id
		return re;
	}

	/***** ���̵� �ߺ� üũ �� *****/

	/* ��� �˻� */
	public MemberBean findpwd(Map pm) throws Exception{
		getSession();
	     return (MemberBean)sqlsession.selectOne("member.pwd_find",pm);
		}

	/*ȸ������*/
	public void insertMember(MemberBean m) throws Exception{
		getSession();
	     sqlsession.insert("member.member_join",m);		
	}
	/*������ ����*/
	public void prf(MemberBean m) throws Exception{
		getSession();
		sqlsession.insert("member.prf",m);		
	}

	/*�α��� ���� üũ */
	public MemberBean userCheck(String id ,String pwd) throws Exception{
		getSession();
		HashMap<String ,String> mv =new HashMap<String, String>();
		mv.put("id", id);
		mv.put("pwd", pwd);
		
		MemberBean m = (MemberBean)sqlsession.selectOne("member.login_check",mv);
		return m;
	}
    /*ȸ������*/
	public void updateMember(MemberBean member) throws Exception{
		getSession();
		sqlsession.update("member.member_edit",member);		
	}
	
	/*ȸ������*/
	public void deleteMember(MemberBean delm) throws Exception{
		getSession();
		sqlsession.update("member.member_delete",delm);		
	}
}
