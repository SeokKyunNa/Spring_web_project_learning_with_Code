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

	//������Ƽ���� ����ϱ� ���� ����
	public void getSession() throws Exception {
		String resource = "util/qna/SqlMapConfig.xml";
		reader = Resources.getResourceAsReader(resource);
		factory = new SqlSessionFactoryBuilder().build(reader);		
		sqlsession = factory.openSession(true);	
	}
	
	//���� ���� ���ϱ�
	public int getListcount(Map<?,?> m) throws Exception{
		getSession();
		int result = 0;
		if(m.get("searchID")==null){
			if(m.get("state").equals("all")){//��ü�Խñ� ������ ���� �Ű����� ���� ������ ���Ѵ�.
				result = sqlsession.selectOne("qna.qna_allcount");
			}else if(!(m.get("state").equals("all"))){//Ư�� �Խ����� �����ϸ� �Ű������� �Խ��� �̸��� �־��ش�.
				result = sqlsession.selectOne("qna.qna_listcount", m);
			}
		}else{
			result = sqlsession.selectOne("qna.qna_mycount", m);
		}
		return result;
	}
	
	//��� �� ��������
	public int getReplyCount(int qna_num) throws Exception{
		getSession();
		int result = 0;
		result = sqlsession.selectOne("qna.qna_replycount", qna_num);
		return result;
	}
	
	//��� �� ������Ʈ
	public void setReplyCount(Map<?,?> m) throws Exception{
		getSession();
		sqlsession.update("qna.qna_replyCountUpdate", m);
	}
	
	//���� ���̵� ���� �ۿ��� ��� ����� �ۼ��� ��� ��۵��� �����ϱ� ���� ���ڸ� ���� �ش�.
	public void updateRe_ref(Map<?,?> m) throws Exception{
		getSession();
		sqlsession.update("qna.qna_re_ref_update", m);
	}
	
	//����Ʈ���⿡ �� ��ƿ���
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
	
	//�۾��� 
	public void insertQna(QnaBean qnabean) throws Exception{
		getSession();
		sqlsession.insert("qna.qna_insert", qnabean);
	}
	
	//��ȸ�� �ø���
	public void qnaHits(int qna_num) throws Exception{
		getSession();
		sqlsession.update("qna.qna_hits", qna_num);
	}
	
	//�Խñ� �󼼺���
	public List<QnaBean> getQnaDetail(int qna_num) throws Exception{
		getSession();
		List<QnaBean> qnarebean = new ArrayList<QnaBean>();
		qnarebean=sqlsession.selectList("qna.qna_prev_detail", qna_num);
		
		return qnarebean;
	}
	//���� ��������
	public List<QnaReBean> getReplyList(int qna_num) throws Exception{
		getSession();
		List<QnaReBean> qnarebean = new ArrayList<QnaReBean>();
		qnarebean= sqlsession.selectList("qna.qna_getreply", qna_num);
		return qnarebean;
	}
	
	//��� �ޱ�
	public void insertReply(QnaReBean qnarebean) throws Exception{
		getSession();
		if(qnarebean.getQna_re_lev() != 1){//�Ϲ� ����� ���
			sqlsession.insert("qna.qna_reply_write", qnarebean);
		}else if(qnarebean.getQna_re_lev() == 1){//����� ����� ���
			//qns_re_seq ��ȣ�� �÷� �ش�.
			sqlsession.insert("qna.qna_level", qnarebean);
			
			sqlsession.insert("qna.qna_re_reply_write", qnarebean);
			
		}
		
	}
	
	//�� ����
	public void deleteWrite(int qna_num) throws Exception{
		getSession();
		sqlsession.delete("qna.qna_delete", qna_num);
		//��۵� ���� �����Ѵ�.
		sqlsession.delete("qna.qna_reply", qna_num);
	}
	//��� ����
	public void deleteReply(Map<?,?> m) throws Exception{
		getSession();
		sqlsession.delete("qna.qna_delete_reply", m);
	}
	//�����ϱ� ȭ������ �̵�
	public QnaBean getDetailEdit(int qna_num) throws Exception{
		getSession();
		return (QnaBean)sqlsession.selectOne("qna.qna_detail", qna_num);
	}
	//�����ϱ�
	public void updateEdit(QnaBean qnabean) throws Exception{
		getSession();
		sqlsession.update("qna.qna_edit", qnabean);
	}
	//�˻��� ���� ���ϱ�
	public int getSearchCount(Map<?,?> m) throws Exception{
		getSession();
		int num = sqlsession.selectOne("qna.qna_search_count", m);
		return num;
	}
	//�˻��� ����Ʈ�� ��ƿ���
	public List<QnaBean> getSearchList(Map<?,?> m) throws Exception{
		getSession();
		List<QnaBean> list = new ArrayList<QnaBean>();
		list = sqlsession.selectList("qna.qna_search_list", m);
		return list;
	}
}
