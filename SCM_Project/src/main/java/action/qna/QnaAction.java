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
	
	//다른 PC에서 실행할 경우 이미지 저장 폴더 경로 변경하기 쉽게 하시 위해 필드변수로 빼놓는다.
	private String saveFolder = "D:/nsk/SCM_Project/src/main/webapp/upload/qna";
	
	
	@Autowired
	public void setQnaService(QnaDAOImpl qnaService){
		this.qnaService = qnaService;
	}//setter DI 설정
	
	//전체보기, JAVA, c++등 게시판 종류 선택해서 들어가기(DEFAULT는 전체보기)
	@RequestMapping(value="/qna_list.qna")
	public ModelAndView qna_list(Model model,
				HttpServletRequest request,
				HttpSession session
			) throws Exception{
		
		
		//클릭한 게시판이 어디인지 확인하는 변수
		String state = request.getParameter("state");
		if(state == null)	state = "all"; //기본값으로 전체보기를 설정함
		else if(state.equals("C  ")){
			state = "C++";
		}

		//내글보기를 클릭하고 들어왔을 경우
		String searchID = null;
		if(request.getParameter("searchID") != null){
			searchID = request.getParameter("searchID");
		}
		
		//클릭한 게시판에 페이지 수를 나타내는 변수
		int page = 1;//기본값으로 1을 준다.
		int limit = 10;//한 화면에 보이는 글의 갯수 초기값
		if(request.getParameter("page") != null){
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		//쿼리문에 넘겨줄 값을 미리 저장해 놓는다.
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("state", state);
		m.put("page", page);
		m.put("limit", limit);
		m.put("searchID", searchID);
		
		//리스트에 갯수를 담아온다.
		int listcount = qnaService.getListcount(m);
		
		//글을 검색하여 리스트에 QnaBean형태로 받아 온다.
		List<QnaBean> qnalist = new ArrayList<QnaBean>();
		qnalist = qnaService.getList(m);
		
		// 총 페이지 수.
		int maxpage = (int) ((double) listcount / limit + 0.95); // 0.95를 더해서 올림	
		// 현재 페이지에 보여줄 시작 페이지 수(1, 11, 21 등...)
		int startpage = (((int) ((double) page / 10 + 0.9)) - 1) * 10 + 1;
		// 현재 페이지에 보여줄 마지막 페이지 수.(10, 20, 30 등...)
		int endpage = maxpage;
		if (endpage > startpage + 10 - 1)
		endpage = startpage + 10 - 1;
		
		//메인에 인트루트 시키기 위해 가져가는 model 값
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
	
	//글쓰기 화면으로 가기
	@RequestMapping(value="/qna_write.qna")
	public String qna_write(Model model){
		model.addAttribute("bodyAdd", "qna/qna_write");
		return "main";
	}
	
	//글쓰기 액션
	@RequestMapping(value="/qna_write_ok.qna", method=RequestMethod.POST)
	public ModelAndView qna_qrite_ok(
				HttpServletRequest request,
				HttpServletResponse response,
				HttpSession session) throws Exception{
		
		QnaBean qnabean = new QnaBean();
		FileOutputStream fos;
		int filesize = 5*1024*1024; //업로드할 파일 크기 제한
		String saveDBName = "";
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multi = multipartRequest.getFile("qna_file1");
				if(multi != null){//첨부파일이 있다면
					String filename = multi.getOriginalFilename();
					Calendar c = Calendar.getInstance();
					int year = c.get(Calendar.YEAR);
					int month = c.get(Calendar.MONTH);
					int date = c.get(Calendar.DATE);
					String dir = saveFolder + "/" + year + "-"+month+"-"+date;
					//upload>qna 폴더 안에 파일 올린 날짜로 폴더를 생성
					
					
					
					File path = new File(dir);
					if(!(path.exists())){//폴더가 없으면
						path.mkdirs();//새로운 폴더 생성-있으면 생성 안함
					}
					
					//파일명을 정해줄 난수를 발생시킴
					Random r = new Random();
					int random = r.nextInt(100000000);
								
					//첨부한 파일에 확장자 구하기
					//확장자를 구문하는 "."에 위치를 저장한다.
					int index = filename.lastIndexOf(".");
					
					//사용자가 사용한 파일이름에서 확장자를 구해오는 것
					String fileExtension = filename.substring(index+1);
					
					//새로운 파일명으로 저장
					String refilename = "qna"+year+month+date+random+"."+fileExtension;
	
					//오라클 디비에 저장될 레코드 값
					saveDBName += "/"+year+"-"+month+"-"+date+"/"+refilename+"'";
					
					//사용자가 업로드한 파일의 파일명을 변경된 파일명으로 바꿔준다.
					
					byte fileData[] = multi.getBytes();
					fos = new FileOutputStream(saveFolder + "\\" + saveDBName);
					fos.write(fileData);
				
			}
					
			saveDBName = saveDBName.substring(0, saveDBName.length()-1);
							
			qnabean.setQna_file(saveDBName);
		

		String qna_type = request.getParameter("qna_type").trim();
		if(request.getParameter("qna_type").trim().equals("기타언어")){
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
	////글쓰기 중 body 부분에 이미지를 삽입시 이미지를 저장하고 저장 경로를 리턴하는 메소드////
	@RequestMapping(value="/qna_write_body_ok.qna", method=RequestMethod.POST)
	public ModelAndView qna_write_body_ok(
				HttpServletRequest request,
				HttpServletResponse response
			) throws Exception{
		
		ModelAndView mv = new ModelAndView("qna/qna_write_body");
		
		
		
		int filesize = 5*1024*1024; //업로드할 파일 크기 제한
		
		MultipartRequest multi = new MultipartRequest(request, saveFolder, filesize, "UTF-8", new DefaultFileRenamePolicy());
		
		File upfile = multi.getFile("qna_body_img");
		
		
		if(upfile != null){
			String fileName = upfile.getName();//파일이름 저장
			
			
			mv.addObject("qna_body_img", fileName);
		}
		
		
		
		return mv;
	}
	
	//게시판 내용보기
	@RequestMapping(value="/qna_detail.qna")
	public ModelAndView qna_detail(
				@RequestParam("num") int qna_num,
				@RequestParam("page") int page,
				@RequestParam("state") String state,//게시판 종류를 나눌때 사용
				Model model,
				HttpServletRequest request
			) throws Exception{


		//게시글 조회수를 올린다.
		qnaService.qnaHits(qna_num);
				
		
		//jsp페이지로 넘겨줄 값은 list로 담아 온다.
		List<QnaBean> qnalist = new ArrayList<QnaBean>();
		
		qnalist = qnaService.getQnaDetail(qna_num);
		
		//글의 내용을 가져온다.
		//QnaBean qnabean = qnaService.getQnaDetail(qna_num);
		
		//영어를 한글로 변환해서 보내준다.
		//if(qnabean.getQna_type().equals("andsoon")){
		//	qnabean.setQna_type("기타 언어");
	    //}
		
		/* 이전버전에서 사용한 기능
		//첨부파일을 짤라서 보여준다.
		String qnaFileStr[] = null;//file명을 짤라서 보관할 변수
		//첨부파일이 있다면
		if(qnabean.getQna_file() != null){
			//Qna_file에 있는 값을 "'"를 기준으로 짤라준다.
			qnaFileStr = qnabean.getQna_file().split("'");
			//버블정렬
			for(int i=0; i<qnaFileStr.length-1; i++){
				//파일명으로 붙여준 숫자를 찾기 위한 변수
				int startNum = qnaFileStr[i].lastIndexOf("/")+1;
				//비교하는 값에 왼쪽
				int l_index = qnaFileStr[i].indexOf("q");
				for(int j=i+1; j<qnaFileStr.length; j++){
					//비교하는 값에 오른쪽
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
	
	//리플 화면에 뿌려주기
	@RequestMapping(value="/qna_reply_view.qna", method=RequestMethod.POST)
	public ModelAndView qna_reply_view(
				HttpServletRequest request
			) throws Exception{
		System.out.println(request.getParameter("qna_num"));
		int qna_num = Integer.parseInt(request.getParameter("qna_num"));
		//리플의 내용을 가져온다.
		List<QnaReBean> relist = new ArrayList<QnaReBean>();
		relist = qnaService.getReplyList(qna_num);
		
		//댓글 갯수를 알려주기 위해 댓글 수를 가져온다
		int replyCount = qnaService.getReplyCount(qna_num);
		
		
		//실제로 리플이 나오는 페이지는 qna_detail.jsp 페이지지만
		//우선 qna_reply로 내용을 보내고 그 내용을 qna_detail.jsp에 append 시킨다.
		ModelAndView replyMV = new ModelAndView("qna/qna_reply");
		replyMV.addObject("relist", relist);
		replyMV.addObject("replyCount", replyCount);
		
		return replyMV;
	}
	
	//리플 달기
	@RequestMapping(value="/qna_reply_ok.qna", method=RequestMethod.POST)
	public ModelAndView qna_reply_ok(
				HttpServletRequest request,
				HttpSession session
			)throws Exception{
		
		QnaReBean qnarebean = new QnaReBean();
		qnarebean.setQna_re_body(request.getParameter("qna_re_body"));
		qnarebean.setQna_re_id((String)session.getAttribute("id"));
		qnarebean.setQna_re_num(Integer.parseInt(request.getParameter("qna_num")));
		
		//기존에 리플 갯수를 구해온다.
		int replyCount = qnaService.getReplyCount(Integer.parseInt(request.getParameter("qna_num")));
		//기존에 리플 갯수에 +1을 해준다.
		replyCount = replyCount+1;
		//+1을 해준 값을 다시 DB에 넣어준다.
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("replyCount", replyCount);
		m.put("qna_num", Integer.parseInt(request.getParameter("qna_num")));
		m.put("id", (String)session.getAttribute("id"));
		//기존에 리플 겟수와 글 넘버를 넘겨주어 해당 글에 리플 갯수를 올려준다.
		qnaService.setReplyCount(m);
		
		
		
		if(request.getParameter("qna_re_lev") == null){//일반 댓글일 경우
			//같은 아이디로 리플을 여러개 작성할 경우 구분해 주기 위해 번호를 넣는다.
			qnaService.updateRe_ref(m);
			
			qnaService.insertReply(qnarebean);
		}else{//댓글의 갯글일 경우
			qnarebean.setQna_re_lev(Integer.parseInt(request.getParameter("qna_re_lev")));
			qnarebean.setQna_re_ref(Integer.parseInt(request.getParameter("qna_re_ref")));
			qnaService.insertReply(qnarebean);
		}
		
		
		
				
		
		ModelAndView replyMV = new ModelAndView("qna/qna_reply");
		
		replyMV.addObject("num", Integer.parseInt(request.getParameter("qna_num")));
		
		return replyMV;
	}
	
	//글 삭제 
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
	
	//댓글 삭제
	@RequestMapping(value="/qna_reply_del_ok.qna", method=RequestMethod.POST)
	public ModelAndView qna_reply_del_ok(
				HttpServletRequest request
			) throws Exception{
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("qna_re_ref", Integer.parseInt(request.getParameter("qna_re_ref")));
		m.put("qna_num", Integer.parseInt(request.getParameter("qna_num")));
		m.put("qna_re_seq", Integer.parseInt(request.getParameter("qna_re_seq")));
		
		qnaService.deleteReply(m);
		
		//기존에 리플 갯수를 구해온다.
		int replyCount = qnaService.getReplyCount(Integer.parseInt(request.getParameter("qna_num")));
				
		//기존에 리플 갯수에 -1을 해준다.
		replyCount = replyCount-1;
		//-1을 해준 값을 다시 DB에 넣어준다.
		m.put("replyCount", replyCount);
		m.put("qna_num", Integer.parseInt(request.getParameter("qna_num")));
		//댓글이 삭제되어 댓글 수를 줄여 준다.
		qnaService.setReplyCount(m);
						
		
		ModelAndView replyMV = new ModelAndView("qna/qna_reply");
		replyMV.addObject("num", Integer.parseInt(request.getParameter("qna_num")));
		
		return replyMV;
		
	}
	
	//글 수정하기 페이지 이동
	@RequestMapping(value="/qna_edit.qna")
	public ModelAndView qna_edit( 
				@RequestParam("num") int qna_num,
				@RequestParam("page") int page,
				@RequestParam("state") String state,//게시판 종류를 나눌때 사용
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
	
	//수정하기 완료 후 페이지 이동
	@RequestMapping(value="/qna_edit_ok.qna", method=RequestMethod.POST)
	public ModelAndView qna_edit_ok(
				Model model,
				HttpSession session,
				HttpServletRequest request
			) throws Exception{
		
		QnaBean qnabean = new QnaBean();
		int filesize = 5*1024*1024; //업로드할 파일 크기 제한
		MultipartRequest multi = new MultipartRequest(request, saveFolder, filesize, "UTF-8", new DefaultFileRenamePolicy());
		
		int qna_num = Integer.parseInt(multi.getParameter("num"));
		
		int page = Integer.parseInt(multi.getParameter("page"));
		
		String state = multi.getParameter("state");
				
		
		String qna_type = multi.getParameter("qna_type").trim();
		
		if(multi.getParameter("qna_type").trim().equals("기타언어")){
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
		
		//jsp페이지로 넘겨줄 값은 list로 담아 온다.
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
	
	/*검색기능*/
	@RequestMapping(value="/qna_search_ok.qna", method=RequestMethod.POST)
	public ModelAndView qna_search_ok(
				Model model,
				HttpServletRequest request,
				HttpServletResponse response,
				HttpSession session
			) throws Exception{
		List<QnaBean> qnalist = new ArrayList<QnaBean>();
		
		//검색 구분
		String search = null;
		if(request.getParameter("qna_search_select").trim() != null){
			search = request.getParameter("qna_search_select").trim();
		}
		
		//검색 내용
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
		//리스트 받아 온다
		qnalist = qnaService.getSearchList(m);
		
		//총 페이지 수
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
