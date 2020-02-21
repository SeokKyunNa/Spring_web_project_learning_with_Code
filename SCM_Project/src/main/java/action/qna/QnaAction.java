package action.qna;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.qna.QnaDAOImpl;
import model.qna.QnaBean;
import model.qna.QnaReBean;

@Controller
public class QnaAction {
	
	private QnaDAOImpl qnaService;
	
	//�ٸ� PC���� ������ ��� �̹��� ���� ���� ��� �����ϱ� ���� �Ͻ� ���� �ʵ庯���� �����´�.
	private String saveFolder = "D:/nsk/SCM_Project/src/main/webapp/upload/qna";
	
	
	@Autowired
	public void setQnaService(QnaDAOImpl qnaService){
		this.qnaService = qnaService;
	}//setter DI ����
	
	//��ü����, JAVA, c++�� �Խ��� ���� �����ؼ� ����(DEFAULT�� ��ü����)
	@RequestMapping(value="/qna_list.qna")
	public ModelAndView qna_list(Model model,
				HttpServletRequest request,
				HttpSession session
			) throws Exception{
		
		
		//Ŭ���� �Խ����� ������� Ȯ���ϴ� ����
		String state = request.getParameter("state");
		if(state == null)	state = "all"; //�⺻������ ��ü���⸦ ������
		else if(state.equals("C  ")){
			state = "C++";
		}

		//���ۺ��⸦ Ŭ���ϰ� ������ ���
		String searchID = null;
		if(request.getParameter("searchID") != null){
			searchID = request.getParameter("searchID");
		}
		
		//Ŭ���� �Խ��ǿ� ������ ���� ��Ÿ���� ����
		int page = 1;//�⺻������ 1�� �ش�.
		int limit = 10;//�� ȭ�鿡 ���̴� ���� ���� �ʱⰪ
		if(request.getParameter("page") != null){
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		//�������� �Ѱ��� ���� �̸� ������ ���´�.
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("state", state);
		m.put("page", page);
		m.put("limit", limit);
		m.put("searchID", searchID);
		
		//����Ʈ�� ������ ��ƿ´�.
		int listcount = qnaService.getListcount(m);
		
		//���� �˻��Ͽ� ����Ʈ�� QnaBean���·� �޾� �´�.
		List<QnaBean> qnalist = new ArrayList<QnaBean>();
		qnalist = qnaService.getList(m);
		
		// �� ������ ��.
		int maxpage = (int) ((double) listcount / limit + 0.95); // 0.95�� ���ؼ� �ø�	
		// ���� �������� ������ ���� ������ ��(1, 11, 21 ��...)
		int startpage = (((int) ((double) page / 10 + 0.9)) - 1) * 10 + 1;
		// ���� �������� ������ ������ ������ ��.(10, 20, 30 ��...)
		int endpage = maxpage;
		if (endpage > startpage + 10 - 1)
		endpage = startpage + 10 - 1;
		
		//���ο� ��Ʈ��Ʈ ��Ű�� ���� �������� model ��
		model.addAttribute("bodyAdd", "qna/qna_list");
		ModelAndView listMD = new ModelAndView("main");
				
		listMD.addObject("page", page);
		listMD.addObject("maxpage", maxpage);
		listMD.addObject("startpage", startpage);
		listMD.addObject("endpage", endpage);
		listMD.addObject("listcount", listcount);
		listMD.addObject("qnalist", qnalist);
		listMD.addObject("state", state);
				
		
		return listMD;
	}
	
	//�۾��� ȭ������ ����
	@RequestMapping(value="/qna_write.qna")
	public String qna_write(Model model){
		model.addAttribute("bodyAdd", "qna/qna_write");
		return "main";
	}
	
	//�۾��� �׼�
	@RequestMapping(value="/qna_write_ok.qna", method=RequestMethod.POST)
	public ModelAndView qna_qrite_ok(
				HttpServletRequest request,
				HttpServletResponse response,
				HttpSession session) throws Exception{
		
		QnaBean qnabean = new QnaBean();
		FileOutputStream fos;
		int filesize = 5*1024*1024; //���ε��� ���� ũ�� ����
		String saveDBName = "";
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multi = multipartRequest.getFile("qna_file1");
				if(multi != null){//÷�������� �ִٸ�
					String filename = multi.getOriginalFilename();
					Calendar c = Calendar.getInstance();
					int year = c.get(Calendar.YEAR);
					int month = c.get(Calendar.MONTH);
					int date = c.get(Calendar.DATE);
					String dir = saveFolder + "/" + year + "-"+month+"-"+date;
					//upload>qna ���� �ȿ� ���� �ø� ��¥�� ������ ����
					
					
					
					File path = new File(dir);
					if(!(path.exists())){//������ ������
						path.mkdirs();//���ο� ���� ����-������ ���� ����
					}
					
					//���ϸ��� ������ ������ �߻���Ŵ
					Random r = new Random();
					int random = r.nextInt(100000000);
								
					//÷���� ���Ͽ� Ȯ���� ���ϱ�
					//Ȯ���ڸ� �����ϴ� "."�� ��ġ�� �����Ѵ�.
					int index = filename.lastIndexOf(".");
					
					//����ڰ� ����� �����̸����� Ȯ���ڸ� ���ؿ��� ��
					String fileExtension = filename.substring(index+1);
					
					//���ο� ���ϸ����� ����
					String refilename = "qna"+year+month+date+random+"."+fileExtension;
	
					//����Ŭ ��� ����� ���ڵ� ��
					saveDBName += "/"+year+"-"+month+"-"+date+"/"+refilename+"'";
					
					//����ڰ� ���ε��� ������ ���ϸ��� ����� ���ϸ����� �ٲ��ش�.
					
					byte fileData[] = multi.getBytes();
					fos = new FileOutputStream(saveFolder + "\\" + saveDBName);
					fos.write(fileData);
				
			}
					
			saveDBName = saveDBName.substring(0, saveDBName.length()-1);
							
			qnabean.setQna_file(saveDBName);
		

		String qna_type = request.getParameter("qna_type").trim();
		if(request.getParameter("qna_type").trim().equals("��Ÿ���")){
			qna_type = "andsoon";
		}
		qnabean.setQna_body(request.getParameter("qna_body").trim());
		qnabean.setQna_code(request.getParameter("qna_code").trim());
		qnabean.setQna_id((String) session.getAttribute("id"));
		qnabean.setQna_subject(request.getParameter("qna_subject").trim());
		qnabean.setQna_type(qna_type);
				
		this.qnaService.insertQna(qnabean);
		
		response.sendRedirect("qna_list.qna?state="+qna_type);
		return null;
	}

	////////////////////////////////////////////////////////////////////
	////�۾��� �� body �κп� �̹����� ���Խ� �̹����� �����ϰ� ���� ��θ� �����ϴ� �޼ҵ�////
	@RequestMapping(value="/qna_write_body_ok.qna", method=RequestMethod.POST)
	public ModelAndView qna_write_body_ok(
				HttpServletRequest request,
				HttpServletResponse response
			) throws Exception{
		
		ModelAndView mv = new ModelAndView("qna/qna_write_body");
		
		
		
		int filesize = 5*1024*1024; //���ε��� ���� ũ�� ����
		
		MultipartRequest multi = new MultipartRequest(request, saveFolder, filesize, "UTF-8", new DefaultFileRenamePolicy());
		
		File upfile = multi.getFile("qna_body_img");
		
		
		if(upfile != null){
			String fileName = upfile.getName();//�����̸� ����
			
			
			mv.addObject("qna_body_img", fileName);
		}
		
		
		
		return mv;
	}
	
	//�Խ��� ���뺸��
	@RequestMapping(value="/qna_detail.qna")
	public ModelAndView qna_detail(
				@RequestParam("num") int qna_num,
				@RequestParam("page") int page,
				@RequestParam("state") String state,//�Խ��� ������ ������ ���
				Model model,
				HttpServletRequest request
			) throws Exception{


		//�Խñ� ��ȸ���� �ø���.
		qnaService.qnaHits(qna_num);
				
		
		//jsp�������� �Ѱ��� ���� list�� ��� �´�.
		List<QnaBean> qnalist = new ArrayList<QnaBean>();
		
		qnalist = qnaService.getQnaDetail(qna_num);
		
		//���� ������ �����´�.
		//QnaBean qnabean = qnaService.getQnaDetail(qna_num);
		
		//��� �ѱ۷� ��ȯ�ؼ� �����ش�.
		//if(qnabean.getQna_type().equals("andsoon")){
		//	qnabean.setQna_type("��Ÿ ���");
	    //}
		
		/* ������������ ����� ���
		//÷�������� ©�� �����ش�.
		String qnaFileStr[] = null;//file���� ©�� ������ ����
		//÷�������� �ִٸ�
		if(qnabean.getQna_file() != null){
			//Qna_file�� �ִ� ���� "'"�� �������� ©���ش�.
			qnaFileStr = qnabean.getQna_file().split("'");
			//��������
			for(int i=0; i<qnaFileStr.length-1; i++){
				//���ϸ����� �ٿ��� ���ڸ� ã�� ���� ����
				int startNum = qnaFileStr[i].lastIndexOf("/")+1;
				//���ϴ� ���� ����
				int l_index = qnaFileStr[i].indexOf("q");
				for(int j=i+1; j<qnaFileStr.length; j++){
					//���ϴ� ���� ������
					int r_index = qnaFileStr[j].indexOf("q");
					if(Integer.parseInt(qnaFileStr[i].substring(startNum, l_index)) > Integer.parseInt(qnaFileStr[j].substring(startNum, r_index))){
						String temp = qnaFileStr[i];
						qnaFileStr[i] = qnaFileStr[j];
						qnaFileStr[j] = temp;
					}
				}
			}
			
			
		}
		*/
		if(qnalist.size()==2){
			qnalist.add(0, null);
		}
		
		model.addAttribute("bodyAdd", "qna/qna_detail");
		ModelAndView detailMV = new ModelAndView("main");
		detailMV.addObject("qnalist", qnalist);
		//detailMV.addObject("qnaFileStr", qnaFileStr);
		//detailMV.addObject("qnabean", qnabean);
		detailMV.addObject("page", page);
		detailMV.addObject("state", state);
		detailMV.addObject("qna_num", qna_num);
		
		
		return detailMV;
	}
	
	//���� ȭ�鿡 �ѷ��ֱ�
	@RequestMapping(value="/qna_reply_view.qna", method=RequestMethod.POST)
	public ModelAndView qna_reply_view(
				HttpServletRequest request
			) throws Exception{
		System.out.println(request.getParameter("qna_num"));
		int qna_num = Integer.parseInt(request.getParameter("qna_num"));
		//������ ������ �����´�.
		List<QnaReBean> relist = new ArrayList<QnaReBean>();
		relist = qnaService.getReplyList(qna_num);
		
		//��� ������ �˷��ֱ� ���� ��� ���� �����´�
		int replyCount = qnaService.getReplyCount(qna_num);
		
		
		//������ ������ ������ �������� qna_detail.jsp ����������
		//�켱 qna_reply�� ������ ������ �� ������ qna_detail.jsp�� append ��Ų��.
		ModelAndView replyMV = new ModelAndView("qna/qna_reply");
		replyMV.addObject("relist", relist);
		replyMV.addObject("replyCount", replyCount);
		
		return replyMV;
	}
	
	//���� �ޱ�
	@RequestMapping(value="/qna_reply_ok.qna", method=RequestMethod.POST)
	public ModelAndView qna_reply_ok(
				HttpServletRequest request,
				HttpSession session
			)throws Exception{
		
		QnaReBean qnarebean = new QnaReBean();
		qnarebean.setQna_re_body(request.getParameter("qna_re_body"));
		qnarebean.setQna_re_id((String)session.getAttribute("id"));
		qnarebean.setQna_re_num(Integer.parseInt(request.getParameter("qna_num")));
		
		//������ ���� ������ ���ؿ´�.
		int replyCount = qnaService.getReplyCount(Integer.parseInt(request.getParameter("qna_num")));
		//������ ���� ������ +1�� ���ش�.
		replyCount = replyCount+1;
		//+1�� ���� ���� �ٽ� DB�� �־��ش�.
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("replyCount", replyCount);
		m.put("qna_num", Integer.parseInt(request.getParameter("qna_num")));
		m.put("id", (String)session.getAttribute("id"));
		//������ ���� �ټ��� �� �ѹ��� �Ѱ��־� �ش� �ۿ� ���� ������ �÷��ش�.
		qnaService.setReplyCount(m);
		
		
		
		if(request.getParameter("qna_re_lev") == null){//�Ϲ� ����� ���
			//���� ���̵�� ������ ������ �ۼ��� ��� ������ �ֱ� ���� ��ȣ�� �ִ´�.
			qnaService.updateRe_ref(m);
			
			qnaService.insertReply(qnarebean);
		}else{//����� ������ ���
			qnarebean.setQna_re_lev(Integer.parseInt(request.getParameter("qna_re_lev")));
			qnarebean.setQna_re_ref(Integer.parseInt(request.getParameter("qna_re_ref")));
			qnaService.insertReply(qnarebean);
		}
		
		
		
				
		
		ModelAndView replyMV = new ModelAndView("qna/qna_reply");
		
		replyMV.addObject("num", Integer.parseInt(request.getParameter("qna_num")));
		
		return replyMV;
	}
	
	//�� ���� 
	@RequestMapping(value="/qna_delete_ok.qna")
	public void qna_delete_ok(
				HttpServletRequest request,
				HttpServletResponse response 
			)throws Exception{
		int qna_num = Integer.parseInt(request.getParameter("qna_num"));
		int page = Integer.parseInt(request.getParameter("page"));
		String state = request.getParameter("state");
		
		qnaService.deleteWrite(qna_num);
		
		response.sendRedirect("qna_list.qna?state="+state+"&page="+page);
		
	}
	
	//��� ����
	@RequestMapping(value="/qna_reply_del_ok.qna", method=RequestMethod.POST)
	public ModelAndView qna_reply_del_ok(
				HttpServletRequest request
			) throws Exception{
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("qna_re_ref", Integer.parseInt(request.getParameter("qna_re_ref")));
		m.put("qna_num", Integer.parseInt(request.getParameter("qna_num")));
		m.put("qna_re_seq", Integer.parseInt(request.getParameter("qna_re_seq")));
		
		qnaService.deleteReply(m);
		
		//������ ���� ������ ���ؿ´�.
		int replyCount = qnaService.getReplyCount(Integer.parseInt(request.getParameter("qna_num")));
				
		//������ ���� ������ -1�� ���ش�.
		replyCount = replyCount-1;
		//-1�� ���� ���� �ٽ� DB�� �־��ش�.
		m.put("replyCount", replyCount);
		m.put("qna_num", Integer.parseInt(request.getParameter("qna_num")));
		//����� �����Ǿ� ��� ���� �ٿ� �ش�.
		qnaService.setReplyCount(m);
						
		
		ModelAndView replyMV = new ModelAndView("qna/qna_reply");
		replyMV.addObject("num", Integer.parseInt(request.getParameter("qna_num")));
		
		return replyMV;
		
	}
	
	//�� �����ϱ� ������ �̵�
	@RequestMapping(value="/qna_edit.qna")
	public ModelAndView qna_edit( 
				@RequestParam("num") int qna_num,
				@RequestParam("page") int page,
				@RequestParam("state") String state,//�Խ��� ������ ������ ���
				Model model,
				HttpServletRequest request
			) throws Exception{
		
		QnaBean qnabean = new QnaBean();
		qnabean = qnaService.getDetailEdit(qna_num);
		
		model.addAttribute("bodyAdd", "qna/qna_edit");
		ModelAndView mv = new ModelAndView("main");
		mv.addObject("num", qna_num);
		mv.addObject("state", state);
		mv.addObject("page", page);
		mv.addObject("qnabean", qnabean);
		
		return mv;
	}
	
	//�����ϱ� �Ϸ� �� ������ �̵�
	@RequestMapping(value="/qna_edit_ok.qna", method=RequestMethod.POST)
	public ModelAndView qna_edit_ok(
				Model model,
				HttpSession session,
				HttpServletRequest request
			) throws Exception{
		
		QnaBean qnabean = new QnaBean();
		int filesize = 5*1024*1024; //���ε��� ���� ũ�� ����
		MultipartRequest multi = new MultipartRequest(request, saveFolder, filesize, "UTF-8", new DefaultFileRenamePolicy());
		
		int qna_num = Integer.parseInt(multi.getParameter("num"));
		
		int page = Integer.parseInt(multi.getParameter("page"));
		
		String state = multi.getParameter("state");
				
		
		String qna_type = multi.getParameter("qna_type").trim();
		
		if(multi.getParameter("qna_type").trim().equals("��Ÿ���")){
			qna_type = "andsoon";
		}
		qnabean.setQna_body(multi.getParameter("qna_body").trim());
		qnabean.setQna_code(multi.getParameter("qna_code").trim());
		qnabean.setQna_id((String) session.getAttribute("id"));
		qnabean.setQna_subject(multi.getParameter("qna_subject").trim());
		qnabean.setQna_type(qna_type);
		qnabean.setQna_num(qna_num);
				
		
		qnaService.updateEdit(qnabean);
				
		model.addAttribute("bodyAdd", "qna/qna_detail");
		ModelAndView detailMV = new ModelAndView("main");
		
		//jsp�������� �Ѱ��� ���� list�� ��� �´�.
		List<QnaBean> qnalist = new ArrayList<QnaBean>();
				
		qnalist = qnaService.getQnaDetail(qna_num);
		if(qnalist.size()==2){
			qnalist.add(0, null);
		}
		
		detailMV.addObject("qnalist", qnalist);
		detailMV.addObject("page", page);
		detailMV.addObject("state", state);
		detailMV.addObject("qna_num", qna_num);
		
		
		return detailMV;
	}
	
	/*�˻����*/
	@RequestMapping(value="/qna_search_ok.qna", method=RequestMethod.POST)
	public ModelAndView qna_search_ok(
				Model model,
				HttpServletRequest request,
				HttpServletResponse response,
				HttpSession session
			) throws Exception{
		List<QnaBean> qnalist = new ArrayList<QnaBean>();
		
		//�˻� ����
		String search = null;
		if(request.getParameter("qna_search_select").trim() != null){
			search = request.getParameter("qna_search_select").trim();
		}
		
		//�˻� ����
		String search_body = null;
		if(request.getParameter("qna_search").trim() != null){
			search_body = request.getParameter("qna_search").trim();
		}
		
		int page = 1;
		int limit = 10;
		if(request.getParameter("page") != null){
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		session.setAttribute("page", page);
		session.setAttribute("search_body", search_body);
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("search", search);
		m.put("search_body", "%"+search_body+"%");
		m.put("page", page);
		m.put("limit", limit);
		
		int listcount = qnaService.getSearchCount(m);
		System.out.println("listcount = " + listcount);
		System.out.println("search = " + search);
		System.out.println("search_body = " + search_body);
		//����Ʈ �޾� �´�
		qnalist = qnaService.getSearchList(m);
		
		//�� ������ ��
		int maxpage = 0;
		if(listcount%limit==0){
			maxpage=listcount/limit;
		}else{
			maxpage=listcount/limit+1;
		}
		int startpage = (((int)((double) page/10+0.9))-1)*10+1;
		int endpage = maxpage;
		if(endpage > startpage+10-1) endpage=startpage+10-1;
		
		
		
		ModelAndView mv = new ModelAndView("main");
		model.addAttribute("bodyAdd", "qna/qna_search");
		
		mv.addObject("page", page);
		mv.addObject("search", search);
		mv.addObject("search_body", search_body);
		mv.addObject("qnalist", qnalist);
		mv.addObject("maxpage", maxpage);
		mv.addObject("startpage", startpage);
		mv.addObject("endpage", endpage);
		mv.addObject("listcount", listcount);
		
		
		return mv;
	}
	
	
}
