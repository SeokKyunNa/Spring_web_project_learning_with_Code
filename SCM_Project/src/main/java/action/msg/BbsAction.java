package action.msg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;

import dao.msg.BbsDAOImpl;
import dao.prf.PrfDAOImpl;
import model.msg.BbsBean;
import model.prf.prfBean;

@Controller
public class BbsAction {
	private BbsDAOImpl bbsService;
	private PrfDAOImpl prfService;
	@Autowired
	public void setBbsService(BbsDAOImpl bbsService) {
		this.bbsService = bbsService;
	}
	@Autowired
	public void setPrfService(PrfDAOImpl prfService) {
		this.prfService = prfService;
	}

	// �Խ��� �۾��� ��
	@RequestMapping(value = "/bbs_write.msg")
	public ModelAndView bbs_write(HttpServletRequest request, HttpServletResponse response) throws Exception {
		prfBean prfbean = new prfBean();
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		prfbean = prfService.getDetail(id);
		ModelAndView contM = new ModelAndView("main");
		contM.addObject("prfbean", prfbean);
		contM.addObject("bodyAdd","msg/bbs_write");
		return contM;
	} 

	// �Խ��� ���� ��
	@RequestMapping(value = "/bbs_write2.msg")
	public ModelAndView bbs_write2(HttpServletRequest request, HttpServletResponse response) throws Exception
		{
		prfBean prfbean = new prfBean();
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		prfbean = prfService.getDetail(id);
		BbsBean bbsbean = new BbsBean();
		String user=(String)request.getParameter("user");
		ModelAndView bbsListM = new ModelAndView("main");
		bbsListM.addObject("user", user);
		bbsListM.addObject("prfbean", prfbean);
		bbsListM.addObject("bodyAdd","msg/bbs_write2");
		return bbsListM;
	}

	// �Խ��� ������
	@RequestMapping(value = "/bbs_write_ok.msg", method = RequestMethod.POST)
	public String bbs_write_ok(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BbsBean bbsbean = new BbsBean();
		HttpSession session = request.getSession();
		String saveFolder = "D:/nsk/SCM_Project/src/main/webapp/upload/msg";
		FileOutputStream fos;
		
		int fileSize = 5 * 1024 * 1024; // �������� �ִ� ���ε� ũ��
		prfBean prfbean = new prfBean();
		String id = (String) session.getAttribute("id");
		prfbean = prfService.getDetail(id);
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multi = multipartRequest.getFile("MESSAGE_FILE");
		String MESSAGE_REC = request.getParameter("MESSAGE_REC").trim();
		String MESSAGE_SUBJECT = request.getParameter("MESSAGE_SUBJECT").trim();
		String MESSAGE_BODY = request.getParameter("MESSAGE_BODY").trim();

		
		if (multi.getSize() != 0) {// ÷���� ���� ������ �ִٸ�
			String fileName = multi.getOriginalFilename(); // �������ϸ� ����
			System.out.println("fileName= " + fileName);
			Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR); // ���ó⵵ ���մϴ�.
			int month = c.get(Calendar.MONTH) + 1; // ���� �� ���մϴ�.
			int date = c.get(Calendar.YEAR); // ���� �� ���մϴ�.

			String homedir = saveFolder + "/" + year + "-" + month + "-" + date;
			System.out.println("homedir =" + homedir);
			// upload���� �Ʒ��� ���� �ø� ��¥�� ���������մϴ�.
			File path1 = new File(homedir);
			if (!(path1.exists())) {
				path1.mkdir();// ���ο� ������ �����մϴ�.
			}
			// ������ ���մϴ�.
			Random r = new Random();
			int random = r.nextInt(100000000);

			/**** Ȯ���� ���ϱ� ���� ****/
			int index = fileName.lastIndexOf(".");
			// ���ڿ����� Ư�� ���ڿ��� ��ġ ��(index)�� ��ȯ�Ѵ�.
			// indexOf�� ó�� �߰ߵǴ� ���ڿ��� ���� index�� ��ȯ�ϴ� �ݸ�,
			// lastIndexOf�� ���������� �߰ߵǴ� ���ڿ��� index�� ��ȯ�մϴ�.
			// (���ϸ� ���� ������ ���� ��� �� �������� �߰ߵǴ� ���ڿ��� ��ġ�� �����մϴ�.)
			System.out.println("index = " + index);

			String fileExtension = fileName.substring(index + 1);
			System.out.println("fileExtension =" + fileExtension);

			/**** Ȯ���� ���ϱ� �� ****/
			// ���ο����ϸ��� ����
			String refileName = "bbs" + year + month + date + random + "." + fileExtension;
			System.out.println("refileName =" + refileName);

			// ����Ŭ ��� ����� ���ڵ� ��
			String fileDBName = "/" + year + "-" + month + "-" + date + "/" + refileName;
			System.out.println("fileDBName=" + fileDBName);

			// ���ϸ� �����մϴ�.
			byte fileData[] = multi.getBytes();
			fos = new FileOutputStream(saveFolder + "\\" + fileDBName);
			fos.write(fileData);
			// �ٲ� ���ϸ����� �������� ���ε�
			bbsbean.setMESSAGE_FILE(fileDBName);
		}else{
			bbsbean.setMESSAGE_FILE(null);
		}
		bbsbean.setMESSAGE_USER(id);
		bbsbean.setMESSAGE_REC(MESSAGE_REC);
		bbsbean.setMESSAGE_SUBJECT(MESSAGE_SUBJECT);
		bbsbean.setMESSAGE_BODY(MESSAGE_BODY);

		this.bbsService.insertBbs(bbsbean);// ����޼��� ȣ��!

		response.sendRedirect("bbs_list.msg");
		return null;
	}

	// �Խ��� ����
	@RequestMapping(value = "/bbs_write2_ok.msg", method = RequestMethod.POST)
	public String bbs_write2_ok(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BbsBean bbsbean = new BbsBean();
		HttpSession session = request.getSession();
		String saveFolder = "D:/nsk/SCM_Project/src/main/webapp/upload/msg";
		FileOutputStream fos;

		int fileSize = 5 * 1024 * 1024; // �������� �ִ� ���ε� ũ��

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multi = multipartRequest.getFile("MESSAGE_FILE");
		String MESSAGE_REC= request.getParameter("MESSAGE_REC").trim();
		String id = (String) session.getAttribute("id");
		String MESSAGE_SUBJECT = request.getParameter("MESSAGE_SUBJECT").trim();
		String MESSAGE_BODY = request.getParameter("MESSAGE_BODY").trim();
		
		if (multi != null) {// ÷���� ���� ������ �ִٸ�
			String fileName = multi.getOriginalFilename(); // �������ϸ� ����
			System.out.println("fileName= " + fileName);
			Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR); // ���ó⵵ ���մϴ�.
			int month = c.get(Calendar.MONTH) + 1; // ���� �� ���մϴ�.
			int date = c.get(Calendar.YEAR); // ���� �� ���մϴ�.

			String homedir = saveFolder + "/" + year + "-" + month + "-" + date;
			System.out.println("homedir =" + homedir);
			// upload���� �Ʒ��� ���� �ø� ��¥�� ���������մϴ�.
			File path1 = new File(homedir);
			if (!(path1.exists())) {
				path1.mkdir();// ���ο� ������ �����մϴ�.
			}
			// ������ ���մϴ�.
			Random r = new Random();
			int random = r.nextInt(100000000);

			/**** Ȯ���� ���ϱ� ���� ****/
			int index = fileName.lastIndexOf(".");
			// ���ڿ����� Ư�� ���ڿ��� ��ġ ��(index)�� ��ȯ�Ѵ�.
			// indexOf�� ó�� �߰ߵǴ� ���ڿ��� ���� index�� ��ȯ�ϴ� �ݸ�,
			// lastIndexOf�� ���������� �߰ߵǴ� ���ڿ��� index�� ��ȯ�մϴ�.
			// (���ϸ� ���� ������ ���� ��� �� �������� �߰ߵǴ� ���ڿ��� ��ġ�� �����մϴ�.)

			String fileExtension = fileName.substring(index + 1);

			/**** Ȯ���� ���ϱ� �� ****/
			// ���ο����ϸ��� ����
			String refileName = "bbs" + year + month + date + random + "." + fileExtension;
			// ����Ŭ ��� ����� ���ڵ� ��
			String fileDBName = "/" + year + "-" + month + "-" + date + "/" + refileName;
			// ���ϸ� �����մϴ�.
			byte fileData[] = multi.getBytes();
			fos = new FileOutputStream(saveFolder + "\\" + fileDBName);
			fos.write(fileData);
			// �ٲ� ���ϸ����� �������� ���ε�
			bbsbean.setMESSAGE_FILE(fileDBName);
		}
		bbsbean.setMESSAGE_REC(MESSAGE_REC);
		bbsbean.setMESSAGE_USER(id);
		bbsbean.setMESSAGE_SUBJECT(MESSAGE_SUBJECT);
		bbsbean.setMESSAGE_BODY(MESSAGE_BODY);
		this.bbsService.insertBbs(bbsbean);// ����޼��� ȣ��!

		response.sendRedirect("bbs_list.msg");
		return null;
	}

	// �Խ��� ��ü�޽��� ����Ʈ ��
	@RequestMapping(value = "/bbs_list.msg")
	public ModelAndView bbs_list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<BbsBean> bbslist = new ArrayList<BbsBean>();
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		prfBean prfbean = new prfBean();
		prfbean = prfService.getDetail(id);
		int page = 1;
		int limit = 10;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}

		if (request.getParameter("limit") != null) {
			limit = Integer.parseInt(request.getParameter("limit"));
		}

		int listcount = bbsService.getListCount(id); // �� ����Ʈ ���� �޾ƿ�
		bbslist = bbsService.getBbsList(page, id); // ����Ʈ�� �޾ƿ�
		// �� ������ ��
		int maxpage = (int) ((double) listcount / limit + 0.95); // 0.95�� ���ؼ� �ø�
																	// ó��

		// ���� �������� ������ ���� ������ ��(1, 11, 21 ��...)
		int startpage = (((int) ((double) page / 10 + 0.9)) - 1) * 10 + 1;

		// ���� �������� ������ ������ ������ ��(10, 20, 30 ��...)
		int endpage = startpage + 10 - 1;

		if (endpage > maxpage)
			endpage = maxpage;

		ModelAndView bbsListM = new ModelAndView("main");
		bbsListM.addObject("page", page);
		bbsListM.addObject("prfbean", prfbean);
		bbsListM.addObject("maxpage", maxpage);
		bbsListM.addObject("startpage", startpage);
		bbsListM.addObject("endpage", endpage);
		bbsListM.addObject("listcount", listcount);
		bbsListM.addObject("bbslist", bbslist);
		bbsListM.addObject("id", id);
		bbsListM.addObject("bodyAdd","msg/bbs_list");

		return bbsListM;
	}

	// �Խ��� ���� ����Ʈ ��
	@RequestMapping(value = "/bbs_list1.msg")
	public ModelAndView bbs_list1(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<BbsBean> bbslist = new ArrayList<BbsBean>();
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		prfBean prfbean = new prfBean();
		prfbean = prfService.getDetail(id);
		
		int page = 1;
		int limit = 10;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		if (request.getParameter("limit") != null) {
			limit = Integer.parseInt(request.getParameter("limit"));
		}

		int listcount = bbsService.getListCount1(id); // �� ����Ʈ ���� �޾ƿ�
		bbslist = bbsService.getBbsList1(page, id); // ����Ʈ�� �޾ƿ�
		// �� ������ ��
		int maxpage = (int) ((double) listcount / limit + 0.95); // 0.95�� ���ؼ� �ø�
																	// ó��

		// ���� �������� ������ ���� ������ ��(1, 11, 21 ��...)
		int startpage = (((int) ((double) page / 10 + 0.9)) - 1) * 10 + 1;

		// ���� �������� ������ ������ ������ ��(10, 20, 30 ��...)
		int endpage = startpage + 10 - 1;

		if (endpage > maxpage)
		endpage = maxpage;

		ModelAndView bbsListM = new ModelAndView("main");
		bbsListM.addObject("page", page);
		bbsListM.addObject("prfbean", prfbean);
		bbsListM.addObject("maxpage", maxpage);
		bbsListM.addObject("startpage", startpage);
		bbsListM.addObject("endpage", endpage);
		bbsListM.addObject("listcount", listcount);
		bbsListM.addObject("bbslist", bbslist);
		bbsListM.addObject("id", id);
		bbsListM.addObject("bodyAdd","msg/bbs_list1");

		return bbsListM;
	}

	// �Խ��� ���� ����Ʈ ��
	@RequestMapping(value = "/bbs_list2.msg")
	public ModelAndView bbs_list2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<BbsBean> bbslist = new ArrayList<BbsBean>();
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		prfBean prfbean = new prfBean();
		prfbean = prfService.getDetail(id);
		int page = 1;
		int limit = 10;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}

		if (request.getParameter("limit") != null) {
			limit = Integer.parseInt(request.getParameter("limit"));
		}

		int listcount = bbsService.getListCount2(id); // �� ����Ʈ ���� �޾ƿ�
		bbslist = bbsService.getBbsList2(page, id); // ����Ʈ�� �޾ƿ�
		// �� ������ ��
		int maxpage = (int) ((double) listcount / limit + 0.95); // 0.95�� ���ؼ� �ø�
																	// ó��

		// ���� �������� ������ ���� ������ ��(1, 11, 21 ��...)
		int startpage = (((int) ((double) page / 10 + 0.9)) - 1) * 10 + 1;

		// ���� �������� ������ ������ ������ ��(10, 20, 30 ��...)
		int endpage = startpage + 10 - 1;

		if (endpage > maxpage)
			endpage = maxpage;

		ModelAndView bbsListM = new ModelAndView("main");
		bbsListM.addObject("page", page);
		bbsListM.addObject("prfbean", prfbean);
		bbsListM.addObject("maxpage", maxpage);
		bbsListM.addObject("startpage", startpage);
		bbsListM.addObject("endpage", endpage);
		bbsListM.addObject("listcount", listcount);
		bbsListM.addObject("bbslist", bbslist);
		bbsListM.addObject("id", id);
		bbsListM.addObject("bodyAdd","msg/bbs_list2");

		return bbsListM;
	}
	
	// �Խ��� ������� �˻� ����Ʈ ��
	@RequestMapping(value = "/bbs_list3.msg")
	public ModelAndView bbs_list3(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<BbsBean> bbslist = new ArrayList<BbsBean>();
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		String text= (String)request.getParameter("text").trim();
		prfBean prfbean = new prfBean();
		prfbean = prfService.getDetail(id);
		int page = 1;
		int limit = 10;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		if (request.getParameter("limit") != null) {
			limit = Integer.parseInt(request.getParameter("limit"));
		}
		
		int listcount = bbsService.getListCount3(text,id); // �� ����Ʈ ���� �޾ƿ�
		bbslist = bbsService.getBbsList3(page, id,text); // ����Ʈ�� �޾ƿ�
		// �� ������ ��
		int maxpage = (int) ((double) listcount / limit + 0.95); // 0.95�� ���ؼ� �ø�
		// ó��
		
		// ���� �������� ������ ���� ������ ��(1, 11, 21 ��...)
		int startpage = (((int) ((double) page / 10 + 0.9)) - 1) * 10 + 1;
		
		// ���� �������� ������ ������ ������ ��(10, 20, 30 ��...)
		int endpage = startpage + 10 - 1;
		
		if (endpage > maxpage)
			endpage = maxpage;
		
		ModelAndView bbsListM = new ModelAndView("main");
		bbsListM.addObject("page", page);
		bbsListM.addObject("prfbean", prfbean);
		bbsListM.addObject("maxpage", maxpage);
		bbsListM.addObject("startpage", startpage);
		bbsListM.addObject("endpage", endpage);
		bbsListM.addObject("listcount", listcount);
		bbsListM.addObject("bbslist", bbslist);
		bbsListM.addObject("id", id);
		bbsListM.addObject("text", text);
		bbsListM.addObject("bodyAdd","msg/bbs_list3");
		
		return bbsListM;
	}
	
	
	
	// �Խ��� ���뺸��
	@RequestMapping(value = "/bbs_cont.msg")
	public ModelAndView bbs_cont(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int MESSAGE_NUM = Integer.parseInt(request.getParameter("num"));
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		prfBean prfbean = new prfBean();
		prfbean = prfService.getDetail(id);
		int page = 1;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		} // parseInt()�޼���� ������ ���ڷ� �ٲ㼭 ����
		
		// ��ȣ�� �������� DB������ �����ɴϴ�.
		BbsBean bbsbean = this.bbsService.getBbsCont(MESSAGE_NUM);
		// System.out.println("1");
		bbsService.boardModify(bbsbean);
		ModelAndView contM = new ModelAndView("main");
		
		contM.addObject("bbsbean", bbsbean);
		contM.addObject("prfbean", prfbean);
		contM.addObject("page", page);
		contM.addObject("num", MESSAGE_NUM);
		contM.addObject("bodyAdd","msg/bbs_cont");
		
		return contM;
	}

	// �Խ��� ���뺸��
	@RequestMapping(value = "/bbs_cont1.msg")
	public ModelAndView bbs_cont1(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int MESSAGE_NUM = Integer.parseInt(request.getParameter("num"));
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		prfBean prfbean = new prfBean();
		prfbean = prfService.getDetail(id);
		int page = 1;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		} // parseInt()�޼���� ������ ���ڷ� �ٲ㼭 ����

		// ��ȣ�� �������� DB������ �����ɴϴ�.
		BbsBean bbsbean = this.bbsService.getBbsCont(MESSAGE_NUM);
		// System.out.println("1");
		bbsService.boardModify(bbsbean);
		ModelAndView contM = new ModelAndView("main");

		contM.addObject("bbsbean", bbsbean);
		contM.addObject("prfbean", prfbean);
		contM.addObject("page", page);
		contM.addObject("num", MESSAGE_NUM);
		contM.addObject("bodyAdd","msg/bbs_cont1");

		return contM;
	}
	
	// ��������ϱ�
	@RequestMapping(value = "/bbs_del_ok.msg")
	public String bbs_del_ok(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BbsBean bbsbean = new BbsBean();
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		// ���� ���̴� ����ڵ� Ÿ���� ����
		response.setContentType("text/html;chaset=UTF-8");
		PrintWriter out = response.getWriter();// ��½�Ʈ�� ����
		int MESSAGE_NUM = Integer.parseInt(request.getParameter("num"));
		int page = Integer.parseInt(request.getParameter("page"));
		// �۹�ȣ�� �ش��ϴ� ��񳻿��� �����ɴϴ�.
		BbsBean bbs = this.bbsService.getBbsCont(MESSAGE_NUM);

		this.bbsService.bbsDelete(MESSAGE_NUM); // DB�� ���� ���ڵ� �����մϴ�.
		int listcount = this.bbsService.getListCount(id);

		// ���� �Ѱ����� 11�� �϶� 1���� (2������)�� ������ ��� 2������ ������ �����ݴϴ�.
		// ������ �ϳ� ���� ��ŵ�ϴ�.
		if (listcount % 10 == 0)
			page = page - 1;
		// ���� 11�� �ϋ� 11���� (1������)�� ������ ��� page�� 0�� �ǹǷ� page�� 1�� ����
		if (page == 0)
			page = 1;

		// ������ �Խù� ������� �̵��մϴ�.
		response.sendRedirect("bbs_list.msg?page=" + page);

		return null;
	}
	
}