package dao.applicant;

import java.io.Reader;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import model.applicant.*;

public class ApplicantDAO {
	Reader reader = null;	
	SqlSessionFactory factory = null;
	SqlSession sqlsession = null;	

	public void getSession() throws Exception {
		String resource = "util/applicant/SqlMapConfig.xml";
		reader = Resources.getResourceAsReader(resource);
		factory = new SqlSessionFactoryBuilder().build(reader);		
		sqlsession = factory.openSession(true);	// true : auto commit 
	}

	// ä����� �����ϱ�
	public void biz_apply(ApplicantBean applicantbean) throws Exception{
		getSession();
		sqlsession.insert("biz_apply", applicantbean);
	}
	
	// ������ �� Ȯ��
	public int applicant_count(int biz_num) throws Exception {
		getSession();
		int ListCount = ((Integer)sqlsession.selectOne("applicant_count", biz_num)).intValue();
		return ListCount;
	}
	
	// ������ ��� ����
	public List<ApplicantBean> applicant_list(Map m) throws Exception{
		getSession();
		List<ApplicantBean> applicantList = sqlsession.selectList("applicant_list", m);
		return applicantList;
	}
	
	// ���� ���̵� �ߺ� üũ
	public int apply_idcheck(Map m) throws Exception{
		getSession();
		int result = 0; 
		result = sqlsession.selectOne("apply_idcheck", m);
		return result;
	}
	
	// ������¥ üũ
	public String apply_datecheck(int biz_num) throws Exception{
		getSession();
		String expiryDate = sqlsession.selectOne("apply_datecheck", biz_num);
		return expiryDate;
	}
	
}
