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

//import org.junit.runner.Request;
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

	// sns �۾��� ��
	@RequestMapping(value = "/sns_write.sns")
	public String sns_Write(Model model) {

		model.addAttribute("bodyAdd", "sns/sns_write");
		return "main";
	}

	/* SNS ���(��ü) */
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
		
		// ������ ������ limit�� �ִ��� üũ
		if (session.getAttribute("limit") != null) {
			limit = (Integer) session.getAttribute("limit");
		}

		// ����� limit�� �ִ��� üũ
		if (request.getParameter("limit") != null) {
			limit = Integer.parseInt(request.getParameter("limit"));
			session.setAttribute("limit", limit);
		}

		// �� ����Ʈ ���� �޾ƿ�.
		int listcount = this.snsService.sns_getListCount();
		int startrow = (page - 1) * limit + 1; // �б� ������ row ��ȣ.
		int endrow = startrow + limit - 1; // ���� ������ row ��ȣ.

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

		// �Խñ� ��ȣ�� ���ƿ� ��ȣ�� ���ؼ� �����ϸ� sns_favorite_on�� 1�� ����
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

		// �� ������ ��.
		int maxpage = (int) ((double) listcount / limit + 0.95); // 0.95�� ���ؼ� �ø�
		

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

	/* SNS ���(���ۺ���) */
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
		snslist = snsService.getSnsList(m); // ����Ʈ�� �޾ƿ�
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

	/* SNS ���� */
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
		// ���� ���� ��θ� �����մϴ�.
		realFolder = request.getRealPath(saveFolder);
		System.out.println("real���"+realFolder);
		
		// ������ �̹��� ��� ���� ���� �ִ� ��
		if(mhsq.getFile("write_file[]") != null){
		List<MultipartFile> mf = mhsq.getFiles("write_file[]");

		// ������ ���� ������ ���� �ִ� ��
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
	

		this.snsService.insertSns(snsbean); // ����޼ҵ� ȣ��

		response.sendRedirect("sns_list.sns");

		return null;
	}

	/* SNS ���뺸��, ������ */
	@RequestMapping(value = "/sns_detail.sns")
	public ModelAndView sns_cont(@RequestParam("num") int num, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String state = request.getParameter("state");// �����ʵ�
		
		SnsBean snsbean = this.snsService.getSnsCont(num);
		List<SnsBean> snsuser = new ArrayList<SnsBean>();
		
		
		// �� ������ ����Ű ģ�κ��� �����ٷ� ����
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

		if (state.equals("cont")) {// ���뺸���϶�
			contM.setViewName("main");// ���뺸�� ������ ����
			contM.addObject("bodyAdd", "sns/sns_detail");
		} else if (state.equals("edit")) {// ������
			contM.setViewName("main");
			contM.addObject("bodyAdd", "sns/sns_edit");
		}
		return contM;
	}

	/* sns�� ���� */
	@RequestMapping(value = "/sns_delete_ok.sns", method = RequestMethod.GET)
	public String sns_del_ok(@RequestParam("num") int num, HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		// ���� ���̴� ����ڵ� Ÿ���� ����
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String sns_name = (String) session.getAttribute("id");
		PrintWriter out = response.getWriter();// ��� ��Ʈ�� ��ü����
		SnsBean sns = snsService.getSnsCont(num);
		String up = "D:/nsk/SCM_Project/src/main/webapp/upload";

		String fname = sns.getWrite_file();

		if (!sns.getWrite_user().equals(sns_name)) {
			out.println("<script>");
			out.println("alert('������ �����ϴ�.')");
			out.println("history.go(-1)");
			out.println("</script>");
		} else {
			if (fname != null) {
				File file = new File(up + fname);
				file.delete();// ���������� ���� ���� ���� ���� �����մϴ�.
			}
			snsService.snsDelete(num);

			return "redirect:sns_list.sns";
		}
		return null;
	}

	/* sns�� ���� */
	@RequestMapping(value = "/sns_edit_ok.sns", method = RequestMethod.POST)
	public ModelAndView sns_edit_ok(HttpServletRequest request, HttpServletResponse response,
			MultipartHttpServletRequest mhsq) throws Exception {

		SnsBean snsbean = new SnsBean();
		response.setContentType("text/html;chaset=UTF-8");
		PrintWriter out = response.getWriter();// ��½�Ʈ�� ����

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

		// ������ �̹��� ��� ���� ���� �ִ� ��
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

	/* ��۴ޱ� */
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

		this.snsService.insertSns_reply(snsbean); // ����޼ҵ� ȣ��
		this.snsService.reply_update_in(wnum); // ��ۼ� ī��Ʈ
        


		
	}
	/* ��۴ޱ�(������) */
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

		this.snsService.insertSns_reply(snsbean); // ����޼ҵ� ȣ��
		this.snsService.reply_update_in(wnum); // ��ۼ� ī��Ʈ
        
		response.sendRedirect("sns_detail.sns?num=" + num + "&state=cont");
		
	}

	/* ���ƿ� �߰� */
	@RequestMapping(value = "/sns_good_on.sns")
	public ModelAndView sns_good_on(@RequestParam(value = "sns_num_good") int sns_num_good, HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value="cont", required = false, defaultValue="list") String cont
			) throws Exception {

		HttpSession session = request.getSession();

		String sns_user_good = (String) session.getAttribute("id");
		Map m = new HashMap();
		m.put("goodnum", sns_num_good);
		m.put("goodid", sns_user_good);

		this.snsService.insertSns_good(m); // ���ã�� �߰� �޼��� ȣ��
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

	/* ���ƿ� ���� */
	@RequestMapping(value = "/sns_good_off.sns")
	public ModelAndView sns_good_off(@RequestParam(value = "sns_num_good") int sns_num_good, HttpServletRequest request,
			HttpServletResponse response, @RequestParam(value="cont", required = false, defaultValue="list") String cont
			) throws Exception {

		HttpSession session = request.getSession();
		
		String goodid = (String) session.getAttribute("id");

		Map m = new HashMap();
		m.put("goodnum", sns_num_good);
		m.put("goodid", goodid);
	
		this.snsService.deleteSns_good(m); // ���ã�� ���� �޼��� ȣ��
		this.snsService.updateSns_good_de(sns_num_good);

		if(cont.equals("list")){
			response.sendRedirect("sns_list.sns");}
			else if(cont.equals("like")){
				response.sendRedirect("sns_list_good.sns");
			}
		return null;
	}

	/* ��� ���� */
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

	/* SNS ���ѽ�ũ�� */
	@RequestMapping(value = "/sns_list1.sns", method = RequestMethod.POST)
	public ModelAndView sns_list1(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		int limit = 3;
		List<SnsBean> snslist = new ArrayList<SnsBean>();
		List<SnsBean> snsgood = new ArrayList<SnsBean>();
		List<SnsBean> snsuser = new ArrayList<SnsBean>();

		// ������ ������ limit�� �ִ��� üũ
		if (session.getAttribute("limit") != null) {
			limit = (Integer) session.getAttribute("limit");
		}

		// ����� limit�� �ִ��� üũ
		if (request.getParameter("limit") != null) {
			limit = Integer.parseInt(request.getParameter("limit"));
			session.setAttribute("limit", limit);
		}

		// �� ����Ʈ ���� �޾ƿ�.
		int listcount = this.snsService.sns_getListCount();
		int startrow = (page - 1) * limit + 1; // �б� ������ row ��ȣ.
		int endrow = startrow + limit - 1; // ���� ������ row ��ȣ.

		Map m = new HashMap();
		m.put("startrow", startrow);
		m.put("endrow", endrow);

		snslist = snsService.getSnsListAll(m);
		snsuser = snsService.getSnsUser();

		String id = (String) session.getAttribute("id");
		int count_sns = snsService.getcount(id);
		session.setAttribute("count_sns", count_sns);
		snsgood = snsService.getSnsListGood(id);

		// �Խñ� ��ȣ�� ���ƿ� ��ȣ�� ���ؼ� �����ϸ� sns_favorite_on�� 1�� ����
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

		// �� ������ ��.
		int maxpage = (int) ((double) listcount / limit + 0.95); // 0.95�� ���ؼ� �ø�


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

	/* SNS ���(���ۺ���) ���� ��ũ�� */
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

		snslist = snsService.getSnsList(m); // ����Ʈ�� �޾ƿ�
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
	
	/*SNS ���ƿ��� �� ���� */
	@RequestMapping(value = "/sns_list_good.sns")
	public ModelAndView sns_list_good(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		int limit = 3;
		List<SnsBean> snslist = new ArrayList<SnsBean>();
		List<SnsBean> snsgood = new ArrayList<SnsBean>();
		List<SnsBean> snsuser = new ArrayList<SnsBean>();
		List<BizBean> bizlist = new ArrayList<BizBean>();

		// ������ ������ limit�� �ִ��� üũ
		if (session.getAttribute("limit") != null) {
			limit = (Integer) session.getAttribute("limit");
		}

		// ����� limit�� �ִ��� üũ
		if (request.getParameter("limit") != null) {
			limit = Integer.parseInt(request.getParameter("limit"));
			session.setAttribute("limit", limit);
		}
		String id = (String)session.getAttribute("id");

		// �� ����Ʈ ���� �޾ƿ�.
		int listcount = this.snsService.sns_getgoodCount(id);
		int startrow = (page - 1) * limit + 1; // �б� ������ row ��ȣ.
		int endrow = startrow + limit - 1; // ���� ������ row ��ȣ.

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

		// �Խñ� ��ȣ�� ���ƿ� ��ȣ�� ���ؼ� �����ϸ� sns_favorite_on�� 1�� ����
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

		// �� ������ ��.
		int maxpage = (int) ((double) listcount / limit + 0.95); // 0.95�� ���ؼ� �ø�
	

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
	
	/* SNS ���ƿ� ���� ��ũ�� */
	@RequestMapping(value = "/sns_like.sns", method = RequestMethod.POST)
	public ModelAndView sns_like(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<SnsBean> snslist = new ArrayList<SnsBean>();
		List<SnsBean> snsgood = new ArrayList<SnsBean>();
		List<SnsBean> snsuser = new ArrayList<SnsBean>();
		
		int limit = 3;
		HttpSession session = request.getSession();
		// ������ ������ limit�� �ִ��� üũ
		if (session.getAttribute("limit") != null) {
			limit = (Integer) session.getAttribute("limit");
		}

		// ����� limit�� �ִ��� üũ
		if (request.getParameter("limit") != null) {
			limit = Integer.parseInt(request.getParameter("limit"));
			session.setAttribute("limit", limit);
		}
		String id = (String)session.getAttribute("id");

		// �� ����Ʈ ���� �޾ƿ�.
		int listcount = this.snsService.sns_getgoodCount(id);
		int startrow = (page - 1) * limit + 1; // �б� ������ row ��ȣ.
		int endrow = startrow + limit - 1; // ���� ������ row ��ȣ.

		Map m = new HashMap();
		m.put("startrow", startrow);
		m.put("endrow", endrow);
		m.put("goodid", id);

		snslist = snsService.getSnsListGood1(m);
		snsuser = snsService.getSnsUser();

		int count_sns = snsService.getcount(id);
		session.setAttribute("count_sns", count_sns);
		snsgood = snsService.getSnsListGood(id);

		// �Խñ� ��ȣ�� ���ƿ� ��ȣ�� ���ؼ� �����ϸ� sns_favorite_on�� 1�� ����
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

		// �� ������ ��.
		int maxpage = (int) ((double) listcount / limit + 0.95); // 0.95�� ���ؼ� �ø�
		
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
	
	/*��â ����*/
	/* SNS ���뺸��, ������ */
	@RequestMapping(value = "/sns_detail_open.sns")
	public ModelAndView sns_detail_open(@RequestParam("num") int num, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		
		
		SnsBean snsbean = this.snsService.getSnsCont(num);
		List<SnsBean> snsuser = new ArrayList<SnsBean>();
		
		
		// �� ������ ����Ű ģ�κ��� �����ٷ� ����
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
