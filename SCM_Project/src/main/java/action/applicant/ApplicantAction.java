package action.applicant;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import dao.applicant.*;
import dao.prf.PrfDAOImpl;
import dao.sns.SnsDAO;
import model.applicant.*;
import model.biz.BizBean;
import model.prf.prfBean;
import model.sns.SnsBean;

@Component
@Controller
public class ApplicantAction {

	private ApplicantDAO applicantService;
	private PrfDAOImpl prfService;
	private SnsDAO snsService;
	
	@Autowired
	public void setApplicantService(ApplicantDAO applicantService) {
		this.applicantService = applicantService;
	}
	
	@Autowired
	public void setPrfDAOImpl (PrfDAOImpl prfService){
		this.prfService = prfService;
	}
	
	@Autowired
	public void setSnsService(SnsDAO snsService) {
		this.snsService = snsService;
	}
	
	// 마감일 확인 ajax함수로 처리부분
	@RequestMapping(value="/apply_datecheck.biz",method=RequestMethod.POST)
	public void apply_datecheck(
			@RequestParam(value = "biz_num") int biz_num,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		Date day = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		PrintWriter out = response.getWriter();
				
		String expiryDate = this.applicantService.apply_datecheck(biz_num);
		
		int compareDate = day.compareTo(sdf.parse(expiryDate));
		int result = 0;

		if(compareDate <= 0){
			result = -1;
		} else{
			result = 1;
		}
		
		out.print(result);	
	}
	
	// ID 지원자 중복검사 ajax함수로 처리부분
	@RequestMapping(value="/apply_idcheck.biz",method=RequestMethod.POST)
	public void apply_idcheck(
			@RequestParam(value = "biz_num") int biz_num,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		Map m = new HashMap();
		m.put("id", id);
		m.put("biz_num", biz_num);
		
		int result = this.applicantService.apply_idcheck(m);
		out.print(result);	
	}

	/* 채용공고 지원하기 */
	@RequestMapping(value = "/biz_apply.biz")
	public ModelAndView biz_apply(
			@RequestParam(value = "biz_num") int biz_num,
			@RequestParam(value = "page") int page,
			@RequestParam(value = "boardForm") String boardForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ApplicantBean applicantbean = new ApplicantBean();
		prfBean prfbean = new prfBean();
		HttpSession session = request.getSession();
		String applicant_id = (String) session.getAttribute("id");
		
		prfbean = prfService.getDetail(applicant_id);
		String applicant_age = prfbean.getPROFILE_AGE();
		String applicant_pic = prfbean.getPROFILE_PIC();
		String applicant_name = prfbean.getPROFILE_USER();
		
		applicantbean.setApplicant_id(applicant_id);
		applicantbean.setBiz_num(biz_num);
		applicantbean.setApplicant_age(applicant_age);
		applicantbean.setApplicant_pic(applicant_pic);
		applicantbean.setApplicant_name(applicant_name);
		
		this.applicantService.biz_apply(applicantbean); // 저장메서드 호출

		response.sendRedirect("biz_getCont.biz?biz_num="+biz_num+"&page="+page+"&state=cont&boardForm="+boardForm);
		return null;
	}
	
	/* 지원자 목록보기 */
	@RequestMapping(value = "/applicant_list.biz")
	public ModelAndView applicant_list(
			@RequestParam(value = "biz_num") int biz_num,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "boardForm") String boardForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// int page=1;
		int limit = 10; // 목록 보기 초기값
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
		// 총 리스트 수를 받아옴.
		int listcount = this.applicantService.applicant_count(biz_num);
		int startrow = (page - 1) * limit + 1; // 읽기 시작할 row 번호.
		int endrow = startrow + limit - 1; // 읽을 마지막 row 번호.

		Map m = new HashMap();
		m.put("biz_num", biz_num);
		m.put("startrow", startrow);
		m.put("endrow", endrow);
		// 리스트를 받아옴.
		List<ApplicantBean> applicantList = applicantService.applicant_list(m);

		// 총 페이지 수.
		int maxpage = (int) ((double) listcount / limit + 0.95); // 0.95를 더해서 올림
																	// 처리.
		// 현재 페이지에 보여줄 시작 페이지 수(1, 11, 21 등...)
		int startpage = (((int) ((double) page / 10 + 0.9)) - 1) * 10 + 1;

		// 현재 페이지에 보여줄 마지막 페이지 수.(10, 20, 30 등...)
		int endpage = maxpage;

		if (endpage > startpage + 10 - 1)
			endpage = startpage + 10 - 1;

		ModelAndView model = new ModelAndView("main");
		model.addObject("biz_num", biz_num);
		model.addObject("page", page);
		model.addObject("maxpage", maxpage);
		model.addObject("startpage", startpage);
		model.addObject("endpage", endpage);
		model.addObject("listcount", listcount);
		model.addObject("applicantList", applicantList);
		model.addObject("limit", limit);
		model.addObject("boardForm", boardForm);

		// biz_list.jsp의 옵션 index값 구하기
		int index = 0;
		if (limit == 10)
			index = 0;
		else if (limit == 20)
			index = 1;
		else if (limit == 30)
			index = 2;
		else if (limit == 40)
			index = 3;
		model.addObject("index", index);
		model.addObject("bodyAdd", "applicant/applicant_list");
		
		return model;
	}
	
	// 지원자 프로필 보기
	@RequestMapping(value = "/applicant_detail.biz")
	public ModelAndView applicant_detail(
			@RequestParam(value = "biz_num") int biz_num,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "applicant_id") String applicant_id,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 지원자 프로필 빈
		prfBean appbean = new prfBean();
		
		appbean = prfService.getDetail(applicant_id);
		String age[] = appbean.getPROFILE_AGE().split("'");
		String edu[] =  appbean.getPROFILE_EDU().split("'");
		String langu[] = appbean.getPROFILE_LANGU().split("'");
		String interest[] = appbean.getPROFILE_INTEREST().split("'");
		String carrer[] = appbean.getPROFILE_CARRER().split("'");
		String local[] = appbean.getPROFILE_LOCAL().split("'");
		String lisence[] = appbean.getPROFILE_LISENCE().split("'");
		
		ModelAndView contM = new ModelAndView("main");
		
		contM.addObject("appbean", appbean);
		contM.addObject("age", age);
		contM.addObject("edu", edu);
		contM.addObject("langu", langu);
		contM.addObject("interest", interest);
		contM.addObject("carrer", carrer);
		contM.addObject("local", local);
		contM.addObject("lisence", lisence);
		contM.addObject("biz_num", biz_num);
		contM.addObject("page", page);
		contM.addObject("applicant_id", applicant_id);
		contM.addObject("bodyAdd","applicant/applicant_profile");
		
		return contM;
	}
	
	/* SNS 목록(지원자 SNS글보기) */
	@RequestMapping(value = "/applicant_sns.biz")
	public ModelAndView sns_list_mine(
			@RequestParam(value = "biz_num") int biz_num,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "applicant_id") String applicant_id,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<SnsBean> snslist = new ArrayList<SnsBean>();
		List<SnsBean> snsgood = new ArrayList<SnsBean>();
		List<SnsBean> snsuser = new ArrayList<SnsBean>();
		List<BizBean> bizlist = new ArrayList<BizBean>();
		// 지원자 프로필 빈
		prfBean appbean = new prfBean();
				
		appbean = prfService.getDetail(applicant_id);

		int limit = 3;
		HttpSession session = request.getSession();
		
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
		m.put("sns_user", applicant_id);

		snslist = snsService.getSnsList(m); // 리스트를 받아옴
		snsuser = snsService.getSnsUser();
		snsgood = snsService.getSnsListGood(applicant_id);

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

		snsListM.addObject("appbean", appbean);
		snsListM.addObject("biz_num", biz_num);
		snsListM.addObject("page", page);
		snsListM.addObject("maxpage", maxpage);
		snsListM.addObject("listcount", listcount);
		snsListM.addObject("snsgood", snsgood);
		snsListM.addObject("snslist", snslist);
		snsListM.addObject("bizlist", bizlist);
		snsListM.addObject("snsuser", snsuser);
		snsListM.addObject("limit", limit);
		snsListM.addObject("applicant_id", applicant_id);
		snsListM.addObject("sns_user", applicant_id);
		snsListM.addObject("bodyAdd", "applicant/applicant_sns");

		return snsListM;
	}

}
