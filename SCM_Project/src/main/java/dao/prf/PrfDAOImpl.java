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
	//자료실 저장
	public void insertprf(prfBean prfbean) throws Exception{
		getSession();
		sqlsession.update("prf.prf_update",prfbean);
		
	}
	
	//프로필 보기
	public prfBean getDetail(String id) throws Exception{
		getSession();
		prfBean prfbean= new prfBean();
		
		prfbean=(prfBean)sqlsession.selectOne("prf.prf_view",id);
		return prfbean;
	}
	
	//프로필 보기
	public String getImg(String id) throws Exception{
		getSession();
		String prfImg = sqlsession.selectOne("prf.prf_img_view",id);
		
		return prfImg;
	}
	/*		
	//내용삭제
	public void bbsDelete(int MESSAGE_NUM) throws Exception{
		getSession();
		 sqlsession.delete("msg.bbs_del",MESSAGE_NUM);
	}
	//자료실 전체 총게시물 수
	public int getListCount(String id) throws Exception{
		int count=0;
		System.out.println();
		getSession();
		//count= ((Integer)sqlsession.selectOne("msg.bbs_count")).intValue();
		count= sqlsession.selectOne("msg.bbs_count",id);
		return count;
	}
	//자료실 보낸총게시물 수
	public int getListCount1(String id) throws Exception{
		int count=0;
		System.out.println();
		getSession();
		//count= ((Integer)sqlsession.selectOne("msg.bbs_count")).intValue();
		count= sqlsession.selectOne("msg.bbs_count1",id);
		return count;
	}
	//자료실 받은총게시물 수
	public int getListCount2(String id) throws Exception{
		int count=0;
		System.out.println();
		getSession();
		//count= ((Integer)sqlsession.selectOne("msg.bbs_count")).intValue();
		count= sqlsession.selectOne("msg.bbs_count2",id);
		return count;
	}
	//자료실 보낸사람 검색 총게시물 수
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

	//게시판 전체목록
	public List<BbsBean> getBbsList(int page,String id) throws Exception{
		getSession();
		HashMap mv =new HashMap();
		mv.put("id", id);
		mv.put("page", page);
		List<BbsBean> list=
				sqlsession.selectList("msg.bbs_list",mv);
		return list;
	}
	//게시판 보낸목록
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
	//게시판 받은 목록
	public List<BbsBean> getBbsList2(int page,String id) throws Exception{
		getSession();
		HashMap mv =new HashMap();
		mv.put("id", id);
		mv.put("page", page);
		List<BbsBean> list=
				sqlsession.selectList("msg.bbs_list2",mv);
		return list;
	}
	//게시판 보낸사람 검색 목록
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

	//게시판 내용보기
	public BbsBean getBbsCont(int MESSAGE_NUM) throws Exception{
		getSession();
		return (BbsBean)sqlsession.selectOne("msg.bbs_cont", MESSAGE_NUM);
	}*/
}
