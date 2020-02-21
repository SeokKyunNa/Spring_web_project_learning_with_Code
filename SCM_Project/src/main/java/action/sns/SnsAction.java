package action.sns;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.ProcessBuilder.Redirect;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.biz.BizBean;
import model.prf.prfBean;
import model.sns.SnsBean;

import org.junit.runner.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;

import dao.biz.BizDAO;
import dao.prf.PrfDAOImpl;
import dao.sns.SnsDAO;

@Controller
public class SnsAction {

	private SnsDAO snsService;
	private PrfDAOImpl prfService;
	private BizDAO bizService;
	
	@Autowired
	public void setPrfService(PrfDAOImpl prfService) {
		this.prfService = prfService;
	}

	@Autowired
	public void setSnsService(SnsDAO snsService) {
		this.snsService = snsService;
	}
	
	@Autowired
	public void setBizService(BizDAO bizService) {
		this.bizService = bizService;
	}

	// sns 글쓰기 폼
	@RequestMapping(value = "/sns_write.sns")
	public String sns_Write(Model model) {

		model.addAttribute("bodyAdd", "sns/sns_write");
		return "main";
	}

	/* SNS 목록(전체) */
	@RequestMapping(value = "/sns_list.sns")
	public ModelAndView sns_list(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		int limit = 3;
		List<SnsBean> snslist = new ArrayList<SnsBean>();
		List<SnsBean> snsgood = new ArrayList<SnsBean>();
		List<SnsBean> snsuser = new ArrayList<SnsBean>();
		List<BizBean> bizlist = new ArrayList<BizBean>();
		
		
		
		String idprf = (String)session.getAttribute("id");
		
		prfBean prfbean = new prfBean();
		
		prfbean = this.prfService.getDetail(idprf);
		session.setAttribute("prfbean", prfbean);
		
		// 이전에 설정된 limit가 있는지 체크
		if (session.getAttribute("limit") != null) {
			limit = (Integer) session.getAttribute("limit");
		}

		// 변경된 limit가 있는지 체크
		if (request.getParameter("limit") != null) {
			limit = Integer.parseInt(request.getParameter("limit"));
			session.setAttribute("limit", limit);
		}

		// 총 리스트 수를 받아옴.
		int listcount = this.snsService.sns_getListCount();
		int startrow = (page - 1) * limit + 1; // 읽기 시작할 row 번호.
		int endrow = startrow + limit - 1; // 읽을 마지막 row 번호.

		Map m = new HashMap();
		m.put("startrow", startrow);
		m.put("endrow", endrow);
		
		bizlist = this.bizService.biz_getList(m);
		snsuser = snsService.getSnsUser();
		snslist = snsService.getSnsListAll(m);

		String id = (String) session.getAttribute("id");
		int count_sns = snsService.getcount(id);
		session.setAttribute("count_sns", count_sns);
		snsgood = snsService.getSnsListGood(id);

		// 게시글 번호와 좋아요 번호를 비교해서 동일하면 sns_favorite_on을 1로 변경
		for (int i = 0; i < snslist.size(); i++) {
			int good_on = 0;
			int snsListNum = Integer.valueOf(snslist.get(i).getWrite_num());
			for (int j = 0; j < snsgood.size(); j++) {
				int goodListNum = Integer.valueOf(snsgood.get(j).getGoodnum());
				if (snsListNum == goodListNum) {
					good_on = 1;
					break;
				}
			}
			String file[] = snslist.get(i).getWrite_file().split("'");

			int FileNum = file.length;
			snslist.get(i).setFileNum(FileNum);
			snslist.get(i).setGood_on(good_on);

		}

		// 총 페이지 수.
		int maxpage = (int) ((double) listcount / limit + 0.95); // 0.95를 더해서 올림
		

		ModelAndView snsListM = new ModelAndView("main");

		snsListM.addObject("page", page);
		snsListM.addObject("maxpage", maxpage);
		snsListM.addObject("listcount", listcount);
		snsListM.addObject("snsgood", snsgood);
		snsListM.addObject("snslist", snslist);
		snsListM.addObject("bizlist", bizlist);
		snsListM.addObject("snsuser", snsuser);
		snsListM.addObject("limit", limit);
		snsListM.addObject("bodyAdd", "sns/sns_content");

		return snsListM;
	}

	/* SNS 목록(내글보기) */
	@RequestMapping(value = "/sns_list_mine.sns")
	public ModelAndView sns_list_mine(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<SnsBean> snslist = new ArrayList<SnsBean>();
		List<SnsBean> snsgood = new ArrayList<SnsBean>();
		List<SnsBean> snsuser = new ArrayList<SnsBean>();
		List<BizBean> bizlist = new ArrayList<BizBean>();
		
		int limit = 3;
		HttpSession session = request.getSession();
		String id = (String)request.getParameter("id");
		
		if (session.getAttribute("limit") != null) {
			limit = (Integer) session.getAttribute("limit");
		}
		if (request.getParameter("limit") != null) {
			limit = Integer.parseInt(request.getParameter("limit"));
			session.setAttribute("limit", limit);
		}
		int listcount = this.snsService.sns_getListCount();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		Map m = new HashMap();
		m.put("startrow", startrow);
		m.put("endrow", endrow);
		m.put("sns_user", id);

		bizlist = this.bizService.biz_getList(m);
		snslist = snsService.getSnsList(m); // 리스트를 받아옴
		snsuser = snsService.getSnsUser();
		int count_sns = snsService.getcount(id);
		session.setAttribute("count_sns", count_sns);
		snsgood = snsService.getSnsListGood(id);

		for (int i = 0; i < snslist.size(); i++) {
			int good_on = 0;
			int snsListNum = Integer.valueOf(snslist.get(i).getWrite_num());
			for (int j = 0; j < snsgood.size(); j++) {
				int goodListNum = Integer.valueOf(snsgood.get(j).getGoodnum());
				if (snsListNum == goodListNum) {
					good_on = 1;
					break;
				}
			}
			String file[] = snslist.get(i).getWrite_file().split("'");
			int FileNum = file.length;
			snslist.get(i).setFileNum(FileNum);
			snslist.get(i).setGood_on(good_on);

		}
		int maxpage = (int) ((double) listcount / limit + 0.95);
		
		ModelAndView snsListM = new ModelAndView("main");

		snsListM.addObject("page", page);
		snsListM.addObject("maxpage", maxpage);
		snsListM.addObject("listcount", listcount);
		snsListM.addObject("snsgood", snsgood);
		snsListM.addObject("snslist", snslist);
		snsListM.addObject("bizlist", bizlist);
		snsListM.addObject("snsuser", snsuser);
		snsListM.addObject("limit", limit);
		snsListM.addObject("id_p", id);
		snsListM.addObject("bodyAdd", "sns/sns_content_p");

		return snsListM;
	}

	/* SNS 저장 */
	@RequestMapping(value = "/sns_write_ok.sns", method = RequestMethod.POST)
	public ModelAndView sns_write_ok(HttpServletResponse response, HttpServletRequest request,
			MultipartHttpServletRequest mhsq) throws Exception {
		SnsBean snsbean = new SnsBean();

		String realFolder = "";
		String saveFolder = "upload";

		HttpSession session = request.getSession();
		String sns_name = (String) session.getAttribute("id");
		String sns_body = request.getParameter("write_body").trim();
		String sns_pic = request.getParameter("write_pic").trim();
		String sns_file[] = request.getParameterValues("imagefile");
		String imastr = "";
		if(sns_file != null){
		for (int x = 0; x < sns_file.length; x++) {
			imastr += sns_file[x];
			imastr += "|";
		}}
		// 실제 저장 경로를 지정합니다.
		realFolder = request.getRealPath(saveFolder);
		System.out.println("real경로"+realFolder);
		
		// 선택한 이미지 모든 정보 갖고 있는 곳
		if(mhsq.getFile("write_file[]") != null){
		List<MultipartFile> mf = mhsq.getFiles("write_file[]");

		// 삭제된 후의 정보를 갖고 있는 곳
		List<String> fileNames = new ArrayList<String>();
		FileOutputStream fos;
		String fileName = null;

		if (null != mf && mf.size() > 0) {

			for (int i = 0; i < mf.size(); i++) {
				MultipartFile multipartFile = mf.get(i);

				fileName = multipartFile.getOriginalFilename();

				if (!imastr.contains(fileName)) {
					mf.remove(i);
					i--;

				} else {
					byte fileData[] = multipartFile.getBytes();
					if(fileName.length()!=0){
						fos = new FileOutputStream(realFolder + "\\" + fileName);
						fos.write(fileData);
						fileNames.add(fileName);}
					
					
				}

			}

			

			for (int i = 0; i < fileNames.size(); i++) {
				String[] write_file = new String[fileNames.size()];
				write_file = fileNames.toArray(new String[fileNames.size()]);
				snsbean.setWrite_file2(write_file);

			}

		}}
		if(sns_file == null){
			String[] write_file2 = new String[1];
			write_file2[0] = "";
			snsbean.setWrite_file2(write_file2);
		}

		snsbean.setWrite_user(sns_name);
		snsbean.setWrite_body(sns_body);
		snsbean.setWrite_pic(sns_pic);
	

		this.snsService.insertSns(snsbean); // 저장메소드 호출

		response.sendRedirect("sns_list.sns");

		return null;
	}

	/* SNS 내용보기, 수정폼 */
	@RequestMapping(value = "/sns_detail.sns")
	public ModelAndView sns_cont(@RequestParam("num") int num, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String state = request.getParameter("state");// 구분필드
		
		SnsBean snsbean = this.snsService.getSnsCont(num);
		List<SnsBean> snsuser = new ArrayList<SnsBean>();
		
		
		// 글 내용중 엔터키 친부분을 다음줄로 개행
		String sns_cont = snsbean.getWrite_body().replace("\n", "<br/>");
		SnsBean sns = snsService.getSnsCont(num);
		List<SnsBean> sns_reply = snsService.getReplyCont(num);
		snsuser = snsService.getSnsUser();
		
		String file[] = sns.getWrite_file().split("'");

		int FileNum = file.length;

		sns.setFileNum(FileNum);

		ModelAndView contM = new ModelAndView();
		contM.addObject("snsbean", sns);
		contM.addObject("reply_bean", sns_reply);
		contM.addObject("sns_cont", sns_cont);
		contM.addObject("snsuser", snsuser);

		if (state.equals("cont")) {// 내용보기일때
			contM.setViewName("main");// 내용보기 페이지 설정
			contM.addObject("bodyAdd", "sns/sns_detail");
		} else if (state.equals("edit")) {// 수정폼
			contM.setViewName("main");
			contM.addObject("bodyAdd", "sns/sns_edit");
		}
		return contM;
	}

	/* sns글 삭제 */
	@RequestMapping(value = "/sns_delete_ok.sns", method = RequestMethod.GET)
	public String sns_del_ok(@RequestParam("num") int num, HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		// 웹상에 보이는 언어코딩 타입을 지정
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String sns_name = (String) session.getAttribute("id");
		PrintWriter out = response.getWriter();// 출력 스트림 객체생성
		SnsBean sns = snsService.getSnsCont(num);
		String up = "D:/nsk/SCM_Project/src/main/webapp/upload";

		String fname = sns.getWrite_file();

		if (!sns.getWrite_user().equals(sns_name)) {
			out.println("<script>");
			out.println("alert('권한이 없습니다.')");
			out.println("history.go(-1)");
			out.println("</script>");
		} else {
			if (fname != null) {
				File file = new File(up + fname);
				file.delete();// 서버폴더로 부터 기존 이진 파일 삭제합니다.
			}
			snsService.snsDelete(num);

			return "redirect:sns_list.sns";
		}
		return null;
	}

	/* sns글 수정 */
	@RequestMapping(value = "/sns_edit_ok.sns", method = RequestMethod.POST)
	public ModelAndView sns_edit_ok(HttpServletRequest request, HttpServletResponse response,
			MultipartHttpServletRequest mhsq) throws Exception {

		SnsBean snsbean = new SnsBean();
		response.setContentType("text/html;chaset=UTF-8");
		PrintWriter out = response.getWriter();// 출력스트림 생성

		String realFolder = "";
		String saveFolder = "upload";

		HttpSession session = request.getSession();
		String sns_name = (String) session.getAttribute("id");
		String sns_body = request.getParameter("write_body").trim();
		int sns_num = Integer.parseInt(request.getParameter("write_num"));
		String sns_file[] = request.getParameterValues("imagefile");
		String imastr = "";
		for (int x = 0; x < sns_file.length; x++) {
			imastr += sns_file[x];
			imastr += "|";
		}

		realFolder = request.getRealPath(saveFolder);

		// 선택한 이미지 모든 정보 갖고 있는 곳
		List<MultipartFile> mf = mhsq.getFiles("write_file[]");

		List<String> fileNames = new ArrayList<String>();
		FileOutputStream fos;
		String fileName = null;

		if (null != mf && mf.size() > 0) {

			for (int i = 0; i < mf.size(); i++) {
				MultipartFile multipartFile = mf.get(i);

				fileName = multipartFile.getOriginalFilename();

				if (!imastr.contains(fileName)) {
					mf.remove(i);
					i--;

				} else {
					byte fileData[] = multipartFile.getBytes();
					fos = new FileOutputStream(realFolder + "\\" + fileName);
					fos.write(fileData);
					fileNames.add(fileName);
				}

			}

			for (int i = 0; i < fileNames.size(); i++) {
				String[] write_file = new String[fileNames.size()];
				write_file = fileNames.toArray(new String[fileNames.size()]);
				snsbean.setWrite_file2(write_file);

			}
		}
		snsbean.setWrite_user(sns_name);
		snsbean.setWrite_body(sns_body);
		snsbean.setWrite_num(sns_num);
		this.snsService.snsEdit(snsbean);

		response.sendRedirect("sns_list.sns");

		return null;
	}

	/* 댓글달기 */
	@RequestMapping(value = "/sns_reply_ok.sns", method = RequestMethod.POST)
	public void sns_reply_ok(HttpServletResponse response, HttpServletRequest request) throws Exception {
		SnsBean snsbean = new SnsBean();

		HttpSession session = request.getSession();
		String sns_reply_name = (String) session.getAttribute("id");
		String sns_reply_body = request.getParameter("reply_body").trim();
		String sns_reply_pic = request.getParameter("reply_pic");
		int num = Integer.parseInt(request.getParameter("num"));

		snsbean.setReply_user(sns_reply_name);
		snsbean.setReply_body(sns_reply_body);
		snsbean.setReply_pic(sns_reply_pic);

		int wnum = snsService.getReplyCount(num);
		snsbean.setReply_wnum(wnum);

		this.snsService.insertSns_reply(snsbean); // 저장메소드 호출
		this.snsService.reply_update_in(wnum); // 댓글수 카운트
        


		
	}
	/* 댓글달기(더보기) */
	@RequestMapping(value = "/sns_reply_de.sns", method = RequestMethod.POST)
	public void sns_reply_de(HttpServletResponse response, HttpServletRequest request) throws Exception {
		SnsBean snsbean = new SnsBean();

		HttpSession session = request.getSession();
		String sns_reply_name = (String) session.getAttribute("id");
		String sns_reply_body = request.getParameter("reply_body").trim();
		String sns_reply_pic = request.getParameter("reply_pic");
		int num = Integer.parseInt(request.getParameter("num"));

		snsbean.setReply_user(sns_reply_name);
		snsbean.setReply_body(sns_reply_body);
		snsbean.setReply_pic(sns_reply_pic);

		int wnum = snsService.getReplyCount(num);
		snsbean.setReply_wnum(wnum);

		this.snsService.insertSns_reply(snsbean); // 저장메소드 호출
		this.snsService.reply_update_in(wnum); // 댓글수 카운트
        
		response.sendRedirect("sns_detail.sns?num=" + num + "&state=cont");
		
	}

	/* 좋아요 추가 */
	@RequestMapping(value = "/sns_good_on.sns")
	public ModelAndView sns_good_on(@RequestParam(value = "sns_num_good") int sns_num_good, HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value="cont", required = false, defaultValue="list") String cont
			) throws Exception {

		HttpSession session = request.getSession();

		String sns_user_good = (String) session.getAttribute("id");
		Map m = new HashMap();
		m.put("goodnum", sns_num_good);
		m.put("goodid", sns_user_good);

		this.snsService.insertSns_good(m); // 즐겨찾기 추가 메서드 호출
		this.snsService.updateSns_good_in(sns_num_good);
		if(cont.equals("list")){
		response.sendRedirect("sns_list.sns");}
		else if(cont.equals("like")){
			response.sendRedirect("sns_list_good.sns");
		}else if(cont.equals("people")){
			response.sendRedirect("sns_list_mine.sns?id="+sns_user_good);
		}
		return null;
	}

	/* 좋아요 삭제 */
	@RequestMapping(value = "/sns_good_off.sns")
	public ModelAndView sns_good_off(@RequestParam(value = "sns_num_good") int sns_num_good, HttpServletRequest request,
			HttpServletResponse response, @RequestParam(value="cont", required = false, defaultValue="list") String cont
			) throws Exception {

		HttpSession session = request.getSession();
		
		String goodid = (String) session.getAttribute("id");

		Map m = new HashMap();
		m.put("goodnum", sns_num_good);
		m.put("goodid", goodid);
	
		this.snsService.deleteSns_good(m); // 즐겨찾기 삭제 메서드 호출
		this.snsService.updateSns_good_de(sns_num_good);

		if(cont.equals("list")){
			response.sendRedirect("sns_list.sns");}
			else if(cont.equals("like")){
				response.sendRedirect("sns_list_good.sns");
			}
		return null;
	}

	/* 댓글 삭제 */
	@RequestMapping(value = "/reply_delete_ok.sns", method = RequestMethod.GET)
	public String reply_del_ok(@RequestParam("wnum") int wnum, @RequestParam("num") int num,
			HttpServletResponse response, HttpServletRequest request) throws Exception {
		Map m = new HashMap<String, Integer>();
		m.put("wnum", wnum);
		m.put("num", num);
		this.snsService.replyDelete(m);
		this.snsService.reply_update_de(wnum);

		return "redirect:sns_detail.sns?num=" + wnum + "&state=cont";

	}

	/* SNS 무한스크롤 */
	@RequestMapping(value = "/sns_list1.sns", method = RequestMethod.POST)
	public ModelAndView sns_list1(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		int limit = 3;
		List<SnsBean> snslist = new ArrayList<SnsBean>();
		List<SnsBean> snsgood = new ArrayList<SnsBean>();
		List<SnsBean> snsuser = new ArrayList<SnsBean>();

		// 이전에 설정된 limit가 있는지 체크
		if (session.getAttribute("limit") != null) {
			limit = (Integer) session.getAttribute("limit");
		}

		// 변경된 limit가 있는지 체크
		if (request.getParameter("limit") != null) {
			limit = Integer.parseInt(request.getParameter("limit"));
			session.setAttribute("limit", limit);
		}

		// 총 리스트 수를 받아옴.
		int listcount = this.snsService.sns_getListCount();
		int startrow = (page - 1) * limit + 1; // 읽기 시작할 row 번호.
		int endrow = startrow + limit - 1; // 읽을 마지막 row 번호.

		Map m = new HashMap();
		m.put("startrow", startrow);
		m.put("endrow", endrow);

		snslist = snsService.getSnsListAll(m);
		snsuser = snsService.getSnsUser();

		String id = (String) session.getAttribute("id");
		int count_sns = snsService.getcount(id);
		session.setAttribute("count_sns", count_sns);
		snsgood = snsService.getSnsListGood(id);

		// 게시글 번호와 좋아요 번호를 비교해서 동일하면 sns_favorite_on을 1로 변경
		for (int i = 0; i < snslist.size(); i++) {
			int good_on = 0;
			int snsListNum = Integer.valueOf(snslist.get(i).getWrite_num());
			for (int j = 0; j < snsgood.size(); j++) {
				int goodListNum = Integer.valueOf(snsgood.get(j).getGoodnum());
				if (snsListNum == goodListNum) {
					good_on = 1;
					break;
				}
			}
			String file[] = snslist.get(i).getWrite_file().split("'");

			int FileNum = file.length;
	
			snslist.get(i).setFileNum(FileNum);
			snslist.get(i).setGood_on(good_on);

		}

		// 총 페이지 수.
		int maxpage = (int) ((double) listcount / limit + 0.95); // 0.95를 더해서 올림


		ModelAndView snsListM = new ModelAndView("sns/sns_content1");
		snsListM.addObject("page", page);
		snsListM.addObject("maxpage", maxpage);
		snsListM.addObject("listcount", listcount);
		snsListM.addObject("snsgood", snsgood);
		snsListM.addObject("snslist", snslist);
		snsListM.addObject("snsuser", snsuser);
		snsListM.addObject("limit", limit);
		snsListM.addObject("bodyAdd", "sns/sns_content1");

		return snsListM;
	}

	/* SNS 목록(내글보기) 무한 스크롤 */
	@RequestMapping(value = "/sns_list_p.sns", method = RequestMethod.POST)
	public ModelAndView sns_list_p(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<SnsBean> snslist = new ArrayList<SnsBean>();
		List<SnsBean> snsgood = new ArrayList<SnsBean>();
		List<SnsBean> snsuser = new ArrayList<SnsBean>();
		
		int limit = 3;
		HttpSession session = request.getSession();
		String id = (String)request.getParameter("id");
	

		if (session.getAttribute("limit") != null) {
			limit = (Integer) session.getAttribute("limit");
		}
		if (request.getParameter("limit") != null) {
			limit = Integer.parseInt(request.getParameter("limit"));
			session.setAttribute("limit", limit);
		}
		int listcount = this.snsService.sns_getListCount();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		Map m = new HashMap();
		m.put("startrow", startrow);
		m.put("endrow", endrow);
		m.put("sns_user", id);

		snslist = snsService.getSnsList(m); // 리스트를 받아옴
		snsuser = snsService.getSnsUser();

		int count_sns = snsService.getcount(id);
		session.setAttribute("count_sns", count_sns);
		snsgood = snsService.getSnsListGood(id);

		for (int i = 0; i < snslist.size(); i++) {
			int good_on = 0;
			int snsListNum = Integer.valueOf(snslist.get(i).getWrite_num());
			for (int j = 0; j < snsgood.size(); j++) {
				int goodListNum = Integer.valueOf(snsgood.get(j).getGoodnum());
				if (snsListNum == goodListNum) {
					good_on = 1;
					break;
				}
			}
			String file[] = snslist.get(i).getWrite_file().split("'");
			int FileNum = file.length;
			snslist.get(i).setFileNum(FileNum);
			snslist.get(i).setGood_on(good_on);

		}

		int maxpage = (int) ((double) listcount / limit + 0.95);

		ModelAndView snsListM = new ModelAndView("sns/sns_content1");

		snsListM.addObject("page", page);
		snsListM.addObject("maxpage", maxpage);
		snsListM.addObject("listcount", listcount);
		snsListM.addObject("snsgood", snsgood);
		snsListM.addObject("snslist", snslist);
		snsListM.addObject("snsuser", snsuser);
		snsListM.addObject("limit", limit);
		snsListM.addObject("id", id);
		snsListM.addObject("bodyAdd", "sns/sns_content1");

		return snsListM;
	}
	
	/*SNS 좋아요한 글 보기 */
	@RequestMapping(value = "/sns_list_good.sns")
	public ModelAndView sns_list_good(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		int limit = 3;
		List<SnsBean> snslist = new ArrayList<SnsBean>();
		List<SnsBean> snsgood = new ArrayList<SnsBean>();
		List<SnsBean> snsuser = new ArrayList<SnsBean>();
		List<BizBean> bizlist = new ArrayList<BizBean>();

		// 이전에 설정된 limit가 있는지 체크
		if (session.getAttribute("limit") != null) {
			limit = (Integer) session.getAttribute("limit");
		}

		// 변경된 limit가 있는지 체크
		if (request.getParameter("limit") != null) {
			limit = Integer.parseInt(request.getParameter("limit"));
			session.setAttribute("limit", limit);
		}
		String id = (String)session.getAttribute("id");

		// 총 리스트 수를 받아옴.
		int listcount = this.snsService.sns_getgoodCount(id);
		int startrow = (page - 1) * limit + 1; // 읽기 시작할 row 번호.
		int endrow = startrow + limit - 1; // 읽을 마지막 row 번호.

		Map m = new HashMap();
		m.put("startrow", startrow);
		m.put("endrow", endrow);
		m.put("goodid", id);

		bizlist = this.bizService.biz_getList(m);
		snslist = snsService.getSnsListGood1(m);
		snsuser = snsService.getSnsUser();

		int count_sns = snsService.getcount(id);
		session.setAttribute("count_sns", count_sns);
		snsgood = snsService.getSnsListGood(id);

		// 게시글 번호와 좋아요 번호를 비교해서 동일하면 sns_favorite_on을 1로 변경
		for (int i = 0; i < snslist.size(); i++) {
			int good_on = 0;
			int snsListNum = Integer.valueOf(snslist.get(i).getWrite_num());
			for (int j = 0; j < snsgood.size(); j++) {
				int goodListNum = Integer.valueOf(snsgood.get(j).getGoodnum());
				if (snsListNum == goodListNum) {
					good_on = 1;
					break;
				}
			}
			String file[] = snslist.get(i).getWrite_file().split("'");

			int FileNum = file.length;
			snslist.get(i).setFileNum(FileNum);
			snslist.get(i).setGood_on(good_on);

		}

		// 총 페이지 수.
		int maxpage = (int) ((double) listcount / limit + 0.95); // 0.95를 더해서 올림
	

		ModelAndView snsListM = new ModelAndView("main");

		snsListM.addObject("page", page);
		snsListM.addObject("maxpage", maxpage);
		snsListM.addObject("listcount", listcount);
		snsListM.addObject("snsgood", snsgood);
		snsListM.addObject("bizlist", bizlist);
		snsListM.addObject("snslist", snslist);
		snsListM.addObject("snsuser", snsuser);
		snsListM.addObject("limit", limit);
		snsListM.addObject("bodyAdd", "sns/sns_goodcontent");

		return snsListM;
	}
	
	/* SNS 좋아요 무한 스크롤 */
	@RequestMapping(value = "/sns_like.sns", method = RequestMethod.POST)
	public ModelAndView sns_like(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<SnsBean> snslist = new ArrayList<SnsBean>();
		List<SnsBean> snsgood = new ArrayList<SnsBean>();
		List<SnsBean> snsuser = new ArrayList<SnsBean>();
		
		int limit = 3;
		HttpSession session = request.getSession();
		// 이전에 설정된 limit가 있는지 체크
		if (session.getAttribute("limit") != null) {
			limit = (Integer) session.getAttribute("limit");
		}

		// 변경된 limit가 있는지 체크
		if (request.getParameter("limit") != null) {
			limit = Integer.parseInt(request.getParameter("limit"));
			session.setAttribute("limit", limit);
		}
		String id = (String)session.getAttribute("id");

		// 총 리스트 수를 받아옴.
		int listcount = this.snsService.sns_getgoodCount(id);
		int startrow = (page - 1) * limit + 1; // 읽기 시작할 row 번호.
		int endrow = startrow + limit - 1; // 읽을 마지막 row 번호.

		Map m = new HashMap();
		m.put("startrow", startrow);
		m.put("endrow", endrow);
		m.put("goodid", id);

		snslist = snsService.getSnsListGood1(m);
		snsuser = snsService.getSnsUser();

		int count_sns = snsService.getcount(id);
		session.setAttribute("count_sns", count_sns);
		snsgood = snsService.getSnsListGood(id);

		// 게시글 번호와 좋아요 번호를 비교해서 동일하면 sns_favorite_on을 1로 변경
		for (int i = 0; i < snslist.size(); i++) {
			int good_on = 0;
			int snsListNum = Integer.valueOf(snslist.get(i).getWrite_num());
			for (int j = 0; j < snsgood.size(); j++) {
				int goodListNum = Integer.valueOf(snsgood.get(j).getGoodnum());
				if (snsListNum == goodListNum) {
					good_on = 1;
					break;
				}
			}
			String file[] = snslist.get(i).getWrite_file().split("'");

			int FileNum = file.length;
			snslist.get(i).setFileNum(FileNum);
			snslist.get(i).setGood_on(good_on);

		}

		// 총 페이지 수.
		int maxpage = (int) ((double) listcount / limit + 0.95); // 0.95를 더해서 올림
		
		ModelAndView snsListM = new ModelAndView("sns/sns_content1");

		snsListM.addObject("page", page);
		snsListM.addObject("maxpage", maxpage);
		snsListM.addObject("listcount", listcount);
		snsListM.addObject("snsgood", snsgood);
		snsListM.addObject("snslist", snslist);
		snsListM.addObject("snsuser", snsuser);
		snsListM.addObject("limit", limit);
		snsListM.addObject("id", id);
		snsListM.addObject("bodyAdd", "sns/sns_content1");

		return snsListM;
	}
	
	/*새창 띄우기*/
	/* SNS 내용보기, 수정폼 */
	@RequestMapping(value = "/sns_detail_open.sns")
	public ModelAndView sns_detail_open(@RequestParam("num") int num, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		
		
		SnsBean snsbean = this.snsService.getSnsCont(num);
		List<SnsBean> snsuser = new ArrayList<SnsBean>();
		
		
		// 글 내용중 엔터키 친부분을 다음줄로 개행
		String sns_cont = snsbean.getWrite_body().replace("\n", "<br/>");
		SnsBean sns = snsService.getSnsCont(num);
		List<SnsBean> sns_reply = snsService.getReplyCont(num);
		snsuser = snsService.getSnsUser();
		
		String file[] = sns.getWrite_file().split("'");

		int FileNum = file.length;

		sns.setFileNum(FileNum);

		ModelAndView contM = new ModelAndView("sns/sns_detail_open");
		contM.addObject("snsbean", sns);
		contM.addObject("reply_bean", sns_reply);
		contM.addObject("sns_cont", sns_cont);
		contM.addObject("snsuser", snsuser);

		
		return contM;
	}
}
