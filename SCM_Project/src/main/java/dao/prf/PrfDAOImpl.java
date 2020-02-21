package dao.prf;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import model.prf.prfBean;
public class PrfDAOImpl {
	
	SqlSessionFactory factory=null;
	SqlSession sqlsession = null;
	Reader reader = null;

	public void getSession() throws Exception{
		String resource ="util/prf/SqlMapConfig.xml";
		reader = Resources.getResourceAsReader(resource);
		factory = new SqlSessionFactoryBuilder().build(reader);
		sqlsession = factory.openSession(true);
		//sqlsession = factory.openSession();
	}
	//�ڷ�� ����
	public void insertprf(prfBean prfbean) throws Exception{
		getSession();
		sqlsession.update("prf.prf_update",prfbean);
		
	}
	
	//������ ����
	public prfBean getDetail(String id) throws Exception{
		getSession();
		prfBean prfbean= new prfBean();
		
		prfbean=(prfBean)sqlsession.selectOne("prf.prf_view",id);
		return prfbean;
	}
	
	//������ ����
	public String getImg(String id) throws Exception{
		getSession();
		String prfImg = sqlsession.selectOne("prf.prf_img_view",id);
		
		return prfImg;
	}
	/*		
	//�������
	public void bbsDelete(int MESSAGE_NUM) throws Exception{
		getSession();
		 sqlsession.delete("msg.bbs_del",MESSAGE_NUM);
	}
	//�ڷ�� ��ü �ѰԽù� ��
	public int getListCount(String id) throws Exception{
		int count=0;
		System.out.println();
		getSession();
		//count= ((Integer)sqlsession.selectOne("msg.bbs_count")).intValue();
		count= sqlsession.selectOne("msg.bbs_count",id);
		return count;
	}
	//�ڷ�� �����ѰԽù� ��
	public int getListCount1(String id) throws Exception{
		int count=0;
		System.out.println();
		getSession();
		//count= ((Integer)sqlsession.selectOne("msg.bbs_count")).intValue();
		count= sqlsession.selectOne("msg.bbs_count1",id);
		return count;
	}
	//�ڷ�� �����ѰԽù� ��
	public int getListCount2(String id) throws Exception{
		int count=0;
		System.out.println();
		getSession();
		//count= ((Integer)sqlsession.selectOne("msg.bbs_count")).intValue();
		count= sqlsession.selectOne("msg.bbs_count2",id);
		return count;
	}
	//�ڷ�� ������� �˻� �ѰԽù� ��
	public int getListCount3(String text ,String id) throws Exception{
		int count=0;
		getSession();
		HashMap mv =new HashMap();
		mv.put("id", id);
		mv.put("text", text);
		//count= ((Integer)sqlsession.selectOne("msg.bbs_count")).intValue();
		count= sqlsession.selectOne("msg.bbs_count3",mv);
		return count;
	}

	//�Խ��� ��ü���
	public List<BbsBean> getBbsList(int page,String id) throws Exception{
		getSession();
		HashMap mv =new HashMap();
		mv.put("id", id);
		mv.put("page", page);
		List<BbsBean> list=
				sqlsession.selectList("msg.bbs_list",mv);
		return list;
	}
	//�Խ��� �������
	public List<BbsBean> getBbsList1(int page,String id) throws Exception{
		getSession();
		HashMap mv =new HashMap();
		mv.put("id", id);
		System.out.println("id="+id);
		mv.put("page", page);
		System.out.println("page="+page);
		List<BbsBean> list=
				sqlsession.selectList("msg.bbs_list1",mv);
		return list;
	}
	//�Խ��� ���� ���
	public List<BbsBean> getBbsList2(int page,String id) throws Exception{
		getSession();
		HashMap mv =new HashMap();
		mv.put("id", id);
		mv.put("page", page);
		List<BbsBean> list=
				sqlsession.selectList("msg.bbs_list2",mv);
		return list;
	}
	//�Խ��� ������� �˻� ���
	public List<BbsBean> getBbsList3(int page,String id, String text) throws Exception{
		getSession();
		HashMap mv =new HashMap();
		mv.put("id", id);
		mv.put("page", page);
		mv.put("text", text);
		List<BbsBean> list=
				sqlsession.selectList("msg.bbs_list3",mv);
		return list;
	}

	//�Խ��� ���뺸��
	public BbsBean getBbsCont(int MESSAGE_NUM) throws Exception{
		getSession();
		return (BbsBean)sqlsession.selectOne("msg.bbs_cont", MESSAGE_NUM);
	}*/
}
