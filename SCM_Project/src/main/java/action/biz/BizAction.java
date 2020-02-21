package action.biz;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import dao.applicant.ApplicantDAO;
import dao.biz.*;
import model.biz.*;

@Component
@Controller
public class BizAction {

	private BizDAO bizService;
	
	@Autowired
	public void setBizService(BizDAO bizService) {
		this.bizService = bizService;
	}
	
	private ApplicantDAO applicantService;

	@Autowired
	public void setApplicantService(ApplicantDAO applicantService) {
		this.applicantService = applicantService;
	}

	/* 채용공고 목록 */
	@RequestMapping(value = "/biz_list.biz")
	public ModelAndView biz_list(
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "boardForm",required = false, defaultValue = "all") String boardForm,
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
		int listcount = this.bizService.biz_getListCount();

		int startrow = (page - 1) * limit + 1; // 읽기 시작할 row 번호.
		int endrow = startrow + limit - 1; // 읽을 마지막 row 번호.

		Map m = new HashMap();
		m.put("startrow", startrow);
		m.put("endrow", endrow);

		// 리스트를 받아옴.
		List<BizBean> bizList = bizService.biz_getList(m);
		
		// 세션에서 id를 받아서 biz_user_favorite에 저장
		String biz_user_favorite = (String) session.getAttribute("id");
		
		for(int i=0; i<bizList.size(); i++){
			// 글내용중 엔터키 친부분을 다음줄로 개행
			String biz_loc_1 = bizList.get(i).getBiz_loc_1().replace("시 ", "시<br/>");
			bizList.get(i).setBiz_loc_1(biz_loc_1);
		}

		// 로그인한 id가 즐겨찾기한 게시물의 번호를 받아옴
		List<BizFavoriteBean> favoriteList = bizService.biz_getFavoriteList(biz_user_favorite);
		
		// 게시글 번호와 즐겨찾기 번호를 비교해서 동일하면 biz_favorite_on을 1로 변경
		for(int i=0; i<bizList.size(); i++){
			int biz_favorite_on = 0;
			// bizboard DB의 biz_num을 가져옴
			int bizListNum = Integer.valueOf(bizList.get(i).getBiz_num());
			for(int j=0; j<favoriteList.size(); j++){
				// bizboard_favorite DB의 biz_num을 가져옴
				int favoriteListNum = Integer.valueOf(favoriteList.get(j).getBiz_num_favorite());
				// 비교
				if(bizListNum==favoriteListNum){
						biz_favorite_on = 1;
						break;
				}
			}
			// bizbean의 Biz_favorite_on 변수에 0이나 1을 저장
			bizList.get(i).setBiz_favorite_on(biz_favorite_on);
		}
		
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
		model.addObject("page", page);
		model.addObject("maxpage", maxpage);
		model.addObject("startpage", startpage);
		model.addObject("endpage", endpage);
		model.addObject("listcount", listcount);
		model.addObject("bizList", bizList);
		model.addObject("limit", limit);
		model.addObject("boardForm", boardForm);

		// biz_list.jsp의 옵션 index값 구하기
		int index = 0;
		if (limit == 3)
			index = 0;
		else if (limit == 5)
			index = 1;
		else if (limit == 10)
			index = 2;
		else if (limit == 15)
			index = 3;
		model.addObject("index", index);
		model.addObject("bodyAdd", "biz/biz_board_list");
		return model;
	}

	/* 내가 등록한 채용공고 목록 */
	@RequestMapping(value = "/biz_list_mine.biz")
	public ModelAndView biz_list_mine(
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "boardForm") String boardForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// int page=1;
		int limit = 10; // 목록 보기 초기값
		HttpSession session = request.getSession();
		String biz_user = (String) session.getAttribute("id");
		String biz_user_favorite = (String) session.getAttribute("id");
		
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
		int listcount = this.bizService.biz_getMyListCount(biz_user);

		int startrow = (page - 1) * limit + 1; // 읽기 시작할 row 번호.
		int endrow = startrow + limit - 1; // 읽을 마지막 row 번호.

		Map m = new HashMap();
		m.put("startrow", startrow);
		m.put("endrow", endrow);
		m.put("biz_user", biz_user);

		// 리스트를 받아옴.
		List<BizBean> bizList = bizService.biz_getMyList(m);
		
		// 즐겨찾기 리스트를 받아옴
		List<BizFavoriteBean> favoriteList = bizService.biz_getFavoriteList(biz_user_favorite);
		
		// 게시글 번호와 즐겨찾기 번호를 비교해서 동일하면 biz_favorite_on을 1로 변경
		for(int i=0; i<bizList.size(); i++){
			int biz_favorite_on = 0;
			int bizListNum = Integer.valueOf(bizList.get(i).getBiz_num());
			for(int j=0; j<favoriteList.size(); j++){
				int favoriteListNum = Integer.valueOf(favoriteList.get(j).getBiz_num_favorite());
				if(bizListNum==favoriteListNum){
						biz_favorite_on = 1;
						break;
				}
			}
			bizList.get(i).setBiz_favorite_on(biz_favorite_on);
		}
		
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
		model.addObject("page", page);
		model.addObject("maxpage", maxpage);
		model.addObject("startpage", startpage);
		model.addObject("endpage", endpage);
		model.addObject("listcount", listcount);
		model.addObject("bizList", bizList);
		model.addObject("limit", limit);
		model.addObject("boardForm", boardForm);

		// biz_list.jsp의 옵션 index값 구하기
		int index = 0;
		if (limit == 3)
			index = 0;
		else if (limit == 5)
			index = 1;
		else if (limit == 10)
			index = 2;
		else if (limit == 15)
			index = 3;
		model.addObject("index", index);
		model.addObject("bodyAdd", "biz/biz_board_list");
		return model;
	}
	
	/* 즐겨찾기한 채용공고 목록 */
	@RequestMapping(value = "/biz_list_favorite.biz")
	public ModelAndView biz_list_favorite(
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "boardForm") String boardForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// int page=1;
		int limit = 10; // 목록 보기 초기값
		HttpSession session = request.getSession();
		String biz_user_favorite = (String) session.getAttribute("id");
		
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
		int listcount = this.bizService.biz_getMyFavoriteListCount(biz_user_favorite);

		int startrow = (page - 1) * limit + 1; // 읽기 시작할 row 번호.
		int endrow = startrow + limit - 1; // 읽을 마지막 row 번호.

		Map m = new HashMap();
		m.put("startrow", startrow);
		m.put("endrow", endrow);
		m.put("biz_user_favorite", biz_user_favorite);

		// 리스트를 받아옴.
		List<BizBean> bizList = bizService.biz_getMyFavoriteList(m);

		// 즐겨찾기 리스트를 받아옴
		List<BizFavoriteBean> myFavoriteList = bizService.biz_getFavoriteList(biz_user_favorite);
		
		// 즐겨찾기한 목록을 받아옴
		
		// 게시글 번호와 즐겨찾기 번호를 비교해서 동일하면 biz_favorite_on을 1로 변경
		for(int i=0; i<bizList.size(); i++){
			int biz_favorite_on = 0;
			int bizListNum = Integer.valueOf(bizList.get(i).getBiz_num());
			for(int j=0; j<myFavoriteList.size(); j++){
				int favoriteListNum = Integer.valueOf(myFavoriteList.get(j).getBiz_num_favorite());
				if(bizListNum==favoriteListNum){
						biz_favorite_on = 1;
						break;
				}
			}
			bizList.get(i).setBiz_favorite_on(biz_favorite_on);
		}
		
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
		model.addObject("page", page);
		model.addObject("maxpage", maxpage);
		model.addObject("startpage", startpage);
		model.addObject("endpage", endpage);
		model.addObject("listcount", listcount);
		model.addObject("bizList", bizList);
		model.addObject("limit", limit);
		model.addObject("boardForm", boardForm);

		// biz_list.jsp의 옵션 index값 구하기
		int index = 0;
		if (limit == 3)
			index = 0;
		else if (limit == 5)
			index = 1;
		else if (limit == 10)
			index = 2;
		else if (limit == 15)
			index = 3;
		model.addObject("index", index);
		model.addObject("bodyAdd", "biz/biz_board_list");
		return model;
	}
	
	/* 채용공고 등록폼 */
	@RequestMapping(value = "/biz_write.biz")
	public String biz_write(Model model) {
		model.addAttribute("bodyAdd", "biz/biz_board_write");
		return "main"; // biz 폴더의 biz_write.jsp 뷰 페이지가 실행
	}

	/* 채용공고 등록 */
	@RequestMapping(value = "/biz_write_ok.biz", method = RequestMethod.POST)
	public ModelAndView biz_write_ok(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BizBean bizbean = new BizBean();
		FileOutputStream fos;

		String saveFolder = "D:/nsk/SCM_Project/src/main/webapp/upload/biz";
		// String saveFolder= "upload";

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multi = multipartRequest.getFile("biz_file");
		HttpSession session = request.getSession();
		String biz_user = (String) session.getAttribute("id");

		String biz_name = request.getParameter("biz_name").trim();
		String biz_subject = request.getParameter("biz_subject").trim();
		String biz_content = request.getParameter("biz_content").trim();
		String biz_zipcode_1 = request.getParameter("biz_zipcode_1").trim();
		String biz_zipcode_2 = request.getParameter("biz_zipcode_2").trim();
		String biz_zipcode = biz_zipcode_1 + "-" + biz_zipcode_2;
		String biz_loc_1 = request.getParameter("biz_loc_1").trim();
		String biz_loc_2 = request.getParameter("biz_loc_2").trim();
		String biz_occ = request.getParameter("biz_occ").trim();
		String biz_position = request.getParameter("biz_position").trim();
		String biz_pattern = request.getParameter("biz_pattern").trim();
		String biz_pay = request.getParameter("biz_pay").trim();
		String biz_education = request.getParameter("biz_education").trim();
		String biz_career = request.getParameter("biz_career").trim();
		String biz_expiry_date = request.getParameter("biz_expiry_date").trim();
		String biz_prf_img = request.getParameter("biz_prf_img").trim();
		
		if (multi.getSize() != 0) {// 첨부한 이진파일이 있다면
			String fileName = multi.getOriginalFilename();// 이진파일명 저장
			Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR); // 오늘 년도 구합니다.
			int month = c.get(Calendar.MONTH) + 1; // 오늘 월 구합니다.
			int date = c.get(Calendar.DATE); // 오늘 일 구합니다.

			String homedir = saveFolder + "/" + year + "-" + month + "-" + date;
			// upload폴더 아래에 파일 올린 날짜로 폴더 생성합니다.
			File path1 = new File(homedir);
			if (!(path1.exists())) {
				path1.mkdir();// 새로운 폴더를 생성
			}
			// 난수를 구합니다.
			Random r = new Random();
			int random = r.nextInt(100000000);

			/**** 확장자 구하기 시작 ****/
			int index = fileName.lastIndexOf(".");
			// 문자열에서 특정 문자열의 위치 값(index)를 반환한다.
			// indexOf가 처음 발견되는 문자열에 대한 index를 반환하는 반면,
			// lastIndexOf는 마지막으로 발견되는 문자열의 index를 반환합니다.
			// (파일명에 점에 여러개 있을 경우 맨 마지막에 발견되는 문자열의 위치를 리턴합니다.)

			String fileExtension = fileName.substring(index + 1);

			/**** 확장자 구하기 끝 ***/

			// 새로운 파일명을 저장
			String refileName = "biz" + year + month + date + random + "." + fileExtension;

			// 오라클 디비에 저장될 레코드 값
			String fileDBName = "/" + year + "-" + month + "-" + date + "/" + refileName;
			
			byte fileData[] = multi.getBytes();
			fos = new FileOutputStream(saveFolder + "\\" + fileDBName);
			fos.write(fileData);
			// 바뀐 파일명으로 이진파일 업로드
			bizbean.setBiz_file(fileDBName);
		} else {
			bizbean.setBiz_file(null);
		}

		bizbean.setBiz_user(biz_user);
		bizbean.setBiz_name(biz_name);
		bizbean.setBiz_subject(biz_subject);
		bizbean.setBiz_content(biz_content);
		bizbean.setBiz_zipcode(biz_zipcode);
		bizbean.setBiz_loc_1(biz_loc_1);
		bizbean.setBiz_loc_2(biz_loc_2);
		bizbean.setBiz_occ(biz_occ);
		bizbean.setBiz_position(biz_position);
		bizbean.setBiz_pattern(biz_pattern);
		bizbean.setBiz_pay(biz_pay);
		bizbean.setBiz_education(biz_education);
		bizbean.setBiz_career(biz_career);
		bizbean.setBiz_expiry_date(biz_expiry_date);
		bizbean.setBiz_prf_img(biz_prf_img);

		this.bizService.biz_Insert(bizbean); // 저장메서드 호출

		response.sendRedirect("biz_list.biz?boardForm=all");
		return null;
	}

	/* 채용공고 내용보기, 수정하기 */
	@RequestMapping(value = "/biz_getCont.biz")
	public ModelAndView biz_getCont(@RequestParam(value = "biz_num") int biz_num,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam("state") String state, 
			@RequestParam(value = "boardForm") String boardForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession();
		String biz_user = (String) session.getAttribute("id");
		
		// 오늘 날짜를 가져옴
		Date day = new Date();
		
		// 날짜 포맷 지정
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		// 채용공고의 마감 날짜를 가져옴
		String expiryDate = this.applicantService.apply_datecheck(biz_num);
		
		// 오늘 날짜와 채용공고의 마감날짜 비교
		int compareDate = day.compareTo(sdf.parse(expiryDate));
		int result = 0;

		if(compareDate <= 0){	// 마감 날짜가 안 지났으면
			result = -1;
		} else{					// 마감 날짜가 지났으면
			result = 1;
		}

		// 번호를 기준으로 DB 내용을 가져옵니다.
		BizBean bizbean = this.bizService.biz_getCont(biz_num);
		
		// 번호를 기준으로 지원자 수를 가져옵니다.
		int applicantCount = this.applicantService.applicant_count(biz_num);
		
		Map m = new HashMap();
		m.put("biz_num", biz_num);
		m.put("biz_user", biz_user);
		
		// 번호, 아이디를 기준으로 즐겨찾기 여부 확인
		int favoriteOnOff = this.bizService.biz_contFavorite(m);
		
		ModelAndView contM = new ModelAndView("main");

		if (state.equals("cont")) {// 내용보기
			contM.addObject("applicantCount", applicantCount);
			contM.addObject("bodyAdd", "biz/biz_board_cont");
			// 글내용중 엔터키 친부분을 다음줄로 개행
			String biz_cont = bizbean.getBiz_content().replace("\n", "<br/>");
			contM.addObject("biz_cont", biz_cont);

		} else if (state.equals("modify")) {
			String[] biz_zipcode = bizbean.getBiz_zipcode().split("-");
			contM.addObject("biz_zipcode_0", biz_zipcode[0]);
			contM.addObject("biz_zipcode_1", biz_zipcode[1]);
			contM.addObject("bodyAdd", "biz/biz_board_modify");
		}
		
		contM.addObject("favoriteOnOff", favoriteOnOff);
		contM.addObject("result", result);
		contM.addObject("bizbean", bizbean);
		contM.addObject("page", page);
		contM.addObject("boardForm", boardForm);
		return contM;
	}

	/* 채용공고 수정 */
	@RequestMapping(value = "/biz_ModifyOk.biz", method = RequestMethod.POST)
	public ModelAndView biz_ModifyOk(@RequestParam(value = "biz_num") int biz_num,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page, HttpServletRequest request,
			@RequestParam(value = "boardForm") String boardForm,
			HttpServletResponse response) throws Exception {
		BizBean bizbean = new BizBean();
		FileOutputStream fos;

		String saveFolder = "D:/nsk/SCM_Project/src/main/webapp/upload/biz";
		// String saveFolder= "upload";

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multi = multipartRequest.getFile("biz_file");

		String biz_name = request.getParameter("biz_name").trim();
		String biz_subject = request.getParameter("biz_subject").trim();
		String biz_content = request.getParameter("biz_content").trim();
		String biz_zipcode_1 = request.getParameter("biz_zipcode_1").trim();
		String biz_zipcode_2 = request.getParameter("biz_zipcode_2").trim();
		String biz_zipcode = biz_zipcode_1 + "-" + biz_zipcode_2;
		String biz_loc_1 = request.getParameter("biz_loc_1").trim();
		String biz_loc_2 = request.getParameter("biz_loc_2").trim();
		String biz_occ = request.getParameter("biz_occ").trim();
		String biz_position = request.getParameter("biz_position").trim();
		String biz_pattern = request.getParameter("biz_pattern").trim();
		String biz_pay = request.getParameter("biz_pay").trim();
		String biz_education = request.getParameter("biz_education").trim();
		String biz_career = request.getParameter("biz_career").trim();
		String biz_expiry_date = request.getParameter("biz_expiry_date").trim();
		
		if (multi.getSize() != 0) {// 첨부한 이진파일이 있다면
			String fileName = multi.getOriginalFilename();// 이진파일명 저장
			Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR); // 오늘 년도 구합니다.
			int month = c.get(Calendar.MONTH) + 1; // 오늘 월 구합니다.
			int date = c.get(Calendar.DATE); // 오늘 일 구합니다.

			String homedir = saveFolder + "/" + year + "-" + month + "-" + date;
			// upload폴더 아래에 파일 올린 날짜로 폴더 생성합니다.
			File path1 = new File(homedir);
			if (!(path1.exists())) {
				path1.mkdir();// 새로운 폴더를 생성
			}
			// 난수를 구합니다.
			Random r = new Random();
			int random = r.nextInt(100000000);

			/**** 확장자 구하기 시작 ****/
			int index = fileName.lastIndexOf(".");
			// 문자열에서 특정 문자열의 위치 값(index)를 반환한다.
			// indexOf가 처음 발견되는 문자열에 대한 index를 반환하는 반면,
			// lastIndexOf는 마지막으로 발견되는 문자열의 index를 반환합니다.
			// (파일명에 점에 여러개 있을 경우 맨 마지막에 발견되는 문자열의 위치를 리턴합니다.)

			String fileExtension = fileName.substring(index + 1);

			/**** 확장자 구하기 끝 ***/

			// 새로운 파일명을 저장
			String refileName = "biz" + year + month + date + random + "." + fileExtension;

			// 오라클 디비에 저장될 레코드 값
			String fileDBName = "/" + year + "-" + month + "-" + date + "/" + refileName;
			
			byte fileData[] = multi.getBytes();
			fos = new FileOutputStream(saveFolder + "\\" + fileDBName);
			fos.write(fileData);
			// 바뀐 파일명으로 이진파일 업로드
			bizbean.setBiz_file(fileDBName);
		} else {
			bizbean.setBiz_file(null);
		}
		bizbean.setBiz_num(biz_num);
		bizbean.setBiz_name(biz_name);
		bizbean.setBiz_subject(biz_subject);
		bizbean.setBiz_content(biz_content);
		bizbean.setBiz_zipcode(biz_zipcode);
		bizbean.setBiz_loc_1(biz_loc_1);
		bizbean.setBiz_loc_2(biz_loc_2);
		bizbean.setBiz_occ(biz_occ);
		bizbean.setBiz_position(biz_position);
		bizbean.setBiz_pattern(biz_pattern);
		bizbean.setBiz_pay(biz_pay);
		bizbean.setBiz_education(biz_education);
		bizbean.setBiz_career(biz_career);
		bizbean.setBiz_expiry_date(biz_expiry_date);
		this.bizService.biz_Modify(bizbean);// 수정메서드 호출

		// get방식으로 3개의 피라미터 값이 넘어갑니다.
		response.sendRedirect("biz_getCont.biz?page="+page+"&biz_num="+biz_num+"&state=cont&boardForm="+boardForm );

		return null;
	}

	/* 채용공고 삭제 */
	@RequestMapping(value = "/biz_Delete.biz")
	public ModelAndView biz_delete(@RequestParam(value = "biz_num") int biz_num,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page, HttpServletRequest request,
			@RequestParam(value = "boardForm") String boardForm,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");

		// 글번호에 해당하는 디비 내용을 가져옵니다.
		BizBean bizbean = this.bizService.biz_getCont(biz_num);

		// 이진파일 업로드 된 서버 경로
		String up = "D:/nsk/SCM_Project/src/main/webapp/upload/biz";

		// 기존 이진파일명 저장
		String fname = bizbean.getBiz_file();

		if (fname != null) {// 기존 이진파일이 존재한다면
			File file = new File(up + fname);
			file.delete();// 서버 폴더로 부터 기존 이진파일 삭제합니다.
		}
		this.bizService.biz_Delete(biz_num);// 디비로 부터 레코드 삭제합니다.

		int listcount = this.bizService.biz_getListCount();

		// 예로 총갯수가 11개 일때 1번글(2페이지)을 삭제할 경우 2페이지 내용을 보여줍니다.
		// 페이지 하나 감소시킵니다.
		HttpSession session = request.getSession();
		
		int limit = 10;
		if (session.getAttribute("limit") != null) {
			limit = (Integer) session.getAttribute("limit");
		}

		if (listcount % limit == 0)
			page = page - 1;
		// 예로 11개 일때 11번글(1페이지)을 삭제할 경우 page가 0이 되므로 page를 1로 설정합니다.
		if (page == 0)
			page = 1;

		// 삭제후 게시물 목록으로 이동한다.
		if (boardForm.equals("all")||boardForm.equals("")){
			response.sendRedirect("biz_list.biz?page=" + page + "&boardForm=all");
		} else if (boardForm.equals("myBoard")) {// 내가 등록한 글
			response.sendRedirect("biz_list_mine.biz?page=" + page + "&boardForm=myBoard");
		} else if (boardForm.equals("favoriteBoard")) {
			response.sendRedirect("biz_list_favorite.biz?page=" + page + "&boardForm=favoriteBoard");
		}
		return null;
	}

	/* 즐겨찾기 추가 */
	@RequestMapping(value = "/biz_favorite_on.biz")
	public ModelAndView biz_favorite_on(
			@RequestParam(value = "biz_num_favorite") int biz_num_favorite,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "boardForm") String boardForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		
		String biz_user_favorite = (String) session.getAttribute("id");
		
		Map m = new HashMap();
		m.put("biz_num_favorite", biz_num_favorite);
		m.put("biz_user_favorite", biz_user_favorite);

		this.bizService.biz_FavoriteOn(m); // 즐겨찾기 추가 메서드 호출

		// 즐겨찾기 추가 후 채용공고 목록으로 이동
		if (boardForm.equals("all")||boardForm.equals("")){
			response.sendRedirect("biz_list.biz?page=" + page + "&boardForm=all");
		} else if (boardForm.equals("myBoard")) {// 내가 등록한 글
			response.sendRedirect("biz_list_mine.biz?page=" + page + "&boardForm=myBoard");
		} else if (boardForm.equals("favoriteBoard")) {
			response.sendRedirect("biz_list_favorite.biz?page=" + page + "&boardForm=favoriteBoard");
		} else if (boardForm.equals("searchBoard")) {
			response.sendRedirect("biz_list_search.biz?page=" + page + "&boardForm=searchBoard");
		}
		
		return null;
	}

	/* 즐겨찾기 삭제 */
	@RequestMapping(value = "/biz_favorite_off.biz")
	public ModelAndView biz_favorite_off(
			@RequestParam(value = "biz_num_favorite") int biz_num_favorite,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "boardForm") String boardForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		
		String biz_user_favorite = (String) session.getAttribute("id");
		Map m = new HashMap();
		m.put("biz_num_favorite", biz_num_favorite);
		m.put("biz_user_favorite", biz_user_favorite);
		this.bizService.biz_FavoriteOff(m); // 즐겨찾기 삭제 메서드 호출
		// 즐겨찾기 추가 후 채용공고 목록으로 이동
		if (boardForm.equals("all")||boardForm.equals("")){
			response.sendRedirect("biz_list.biz?page=" + page + "&boardForm=all");
		} else if (boardForm.equals("myBoard")) {// 내가 등록한 글
			response.sendRedirect("biz_list_mine.biz?page=" + page + "&boardForm=myBoard");
		} else if (boardForm.equals("favoriteBoard")) {
			response.sendRedirect("biz_list_favorite.biz?page=" + page + "&boardForm=favoriteBoard");
		} else if (boardForm.equals("searchBoard")) {
			response.sendRedirect("biz_list_search.biz?page=" + page + "&boardForm=searchBoard");
		}
		return null;
	}
	
	// 즐겨찾기 추가 ajax
	@RequestMapping(value="/biz_contFavorite_on.biz",method=RequestMethod.POST)
	public void biz_contFavorite_on(
			@RequestParam(value = "biz_num") int biz_num,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		HttpSession session = request.getSession();
		
		String biz_user_favorite = (String) session.getAttribute("id");
		Map m = new HashMap();
		m.put("biz_num_favorite", biz_num);
		m.put("biz_user_favorite", biz_user_favorite);
		this.bizService.biz_FavoriteOn(m); // 즐겨찾기 추가 메서드 호출
		
		PrintWriter out = response.getWriter();
		
		int result = 1;
			
		out.print(result);	
	}
	
	// 즐겨찾기 제거 ajax
	@RequestMapping(value="/biz_contFavorite_off.biz",method=RequestMethod.POST)
	public void biz_contFavorite_off(
			@RequestParam(value = "biz_num") int biz_num,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		HttpSession session = request.getSession();
		
		String biz_user_favorite = (String) session.getAttribute("id");
		Map m = new HashMap();
		m.put("biz_num_favorite", biz_num);
		m.put("biz_user_favorite", biz_user_favorite);
		this.bizService.biz_FavoriteOff(m); // 즐겨찾기 제거 메서드 호출
		
		PrintWriter out = response.getWriter();
		
		int result = 1;
			
		out.print(result);	
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	/*우편검색 폼*/
	@RequestMapping(value="/zipcode_find.nhn")
	public String zipcode_find(){
		return "biz/zipcode_find";
		//member 폴더의 zipcode_find.jsp 뷰 페이지 실행
	}
	
	
	/*우편번호 DB로 부터 검색 */
	@RequestMapping(value="/zicode_find_ok.nhn",method=RequestMethod.POST)
	public ModelAndView zicode_find_ok(	
			String dong, 
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		List<ZipcodeBean2> zipcodeList=new ArrayList<ZipcodeBean2>();
		//동을 기준으로 주소를 검색해서 컬렉션에 저장합니다.
		zipcodeList=this.bizService.findZipcode("%"+dong+"%");

		List<ZipcodeBean> zipcodeList2=new ArrayList<ZipcodeBean>();

		for(int i=0; i<zipcodeList.size();i++){
			ZipcodeBean2 zipcode_addr=zipcodeList.get(i);

			String zipcode=zipcode_addr.getZipcode();//우편번호 저장
			String sido=zipcode_addr.getSido(); //서울시,경기도 저장
			String gugun=zipcode_addr.getGugun();//구,군
			String dong2=zipcode_addr.getDong(); //동
			String addr=sido+" "+gugun+" "+dong2;//서울시 서초구 서초동

			ZipcodeBean zip=new ZipcodeBean();
			zip.setZipcode(zipcode);
			zip.setAddr(addr);

			//컬렉션에 주소를  저장합니다.
			zipcodeList2.add(zip);
		}

		ModelAndView zipcodeM=new ModelAndView("biz/zipcode_find");
		zipcodeM.addObject("zipcodelist",zipcodeList2);
		zipcodeM.addObject("dong",dong);		

		return zipcodeM;	
	}
	
	/* 채용공고 검색 목록 */
	@RequestMapping(value = "/biz_list_search1.biz")
	public ModelAndView biz_searchList1(
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "boardForm") String boardForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		// int page=1;
		int limit = 10; // 목록 보기 초기값
		HttpSession session = request.getSession();
		
		String bizSearchSelected = request.getParameter("biz_search_select").trim();
		session.setAttribute("bizSearchSelected", bizSearchSelected);
		System.out.println("1");
		String bizSearch = "%" + request.getParameter("biz_search").trim() + "%";
		session.setAttribute("bizSearch", bizSearch);
		System.out.println("2");
		
		System.out.println("bizSearchSelected 2 : " + bizSearchSelected);
		System.out.println("bizSearch 2 : " + bizSearch);
		
		// 이전에 설정된 limit가 있는지 체크
		if (session.getAttribute("limit") != null) {
			limit = (Integer) session.getAttribute("limit");
		}

		// 변경된 limit가 있는지 체크
		if (request.getParameter("limit") != null) {
			limit = Integer.parseInt(request.getParameter("limit"));
			session.setAttribute("limit", limit);
		}
		
		int listcount = 0;
		// 총 리스트 수를 받아옴.
		if(bizSearchSelected.equals("bizName")){
			listcount = this.bizService.biz_searchNameCount(bizSearch);
		} else if(bizSearchSelected.equals("bizSubject")){
			listcount = this.bizService.biz_searchSubjectCount(bizSearch);
		} else if(bizSearchSelected.equals("bizLoc")){
			listcount = this.bizService.biz_searchLocCount(bizSearch);
		}
		
		int startrow = (page - 1) * limit + 1; // 읽기 시작할 row 번호.
		int endrow = startrow + limit - 1; // 읽을 마지막 row 번호.

		Map m = new HashMap();
		m.put("startrow", startrow);
		m.put("endrow", endrow);
		m.put("bizSearch", bizSearch);

		List<BizBean> bizList = new ArrayList<BizBean>();
		// 리스트를 받아옴.
		if(bizSearchSelected.equals("bizName")){
			bizList = bizService.biz_searchNameList(m);
		} else if(bizSearchSelected.equals("bizSubject")){
			bizList = bizService.biz_searchSubjectList(m);
		} else if(bizSearchSelected.equals("bizLoc")){
			bizList = bizService.biz_searchLocList(m);
		}
		
		// 세션에서 id를 받아서 biz_user_favorite에 저장
		String biz_user_favorite = (String) session.getAttribute("id");
		
		for(int i=0; i<bizList.size(); i++){
			// 글내용중 엔터키 친부분을 다음줄로 개행
			String biz_loc_1 = bizList.get(i).getBiz_loc_1().replace("시 ", "시<br/>");
			bizList.get(i).setBiz_loc_1(biz_loc_1);
		}

		// 로그인한 id가 즐겨찾기한 게시물의 번호를 받아옴
		List<BizFavoriteBean> favoriteList = bizService.biz_getFavoriteList(biz_user_favorite);
		
		// 게시글 번호와 즐겨찾기 번호를 비교해서 동일하면 biz_favorite_on을 1로 변경
		for(int i=0; i<bizList.size(); i++){
			int biz_favorite_on = 0;
			// bizboard DB의 biz_num을 가져옴
			int bizListNum = Integer.valueOf(bizList.get(i).getBiz_num());
			for(int j=0; j<favoriteList.size(); j++){
				// bizboard_favorite DB의 biz_num을 가져옴
				int favoriteListNum = Integer.valueOf(favoriteList.get(j).getBiz_num_favorite());
				// 비교
				if(bizListNum==favoriteListNum){
						biz_favorite_on = 1;
						break;
				}
			}
			// bizbean의 Biz_favorite_on 변수에 0이나 1을 저장
			bizList.get(i).setBiz_favorite_on(biz_favorite_on);
		}
		
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
		model.addObject("page", page);
		model.addObject("maxpage", maxpage);
		model.addObject("bizSearch", bizSearch);
		model.addObject("startpage", startpage);
		model.addObject("endpage", endpage);
		model.addObject("listcount", listcount);
		model.addObject("bizList", bizList);
		model.addObject("bizSearchSelected", bizSearchSelected);
		model.addObject("limit", limit);
		model.addObject("boardForm", boardForm);

		// biz_list.jsp의 옵션 index값 구하기
		int index = 0;
		if (limit == 3)
			index = 0;
		else if (limit == 5)
			index = 1;
		else if (limit == 10)
			index = 2;
		else if (limit == 15)
			index = 3;
		model.addObject("index", index);
		model.addObject("bodyAdd", "biz/biz_board_list");
		return model;
	}
	
	/* 채용공고 검색 목록 페이지 이동 */
	@RequestMapping(value = "/biz_list_search.biz")
	public ModelAndView biz_searchList(
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "boardForm") String boardForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		// int page=1;
		int limit = 10; // 목록 보기 초기값
		HttpSession session = request.getSession();
		
		String bizSearchSelected = (String)session.getAttribute("bizSearchSelected");
		String bizSearch = (String)session.getAttribute("bizSearch");
		
		if(bizSearchSelected==null){
			bizSearchSelected = request.getParameter("biz_search_select").trim();
			session.setAttribute("bizSearchSelected", bizSearchSelected);
		}
		if(bizSearch==null){
			bizSearch = "%" + request.getParameter("biz_search").trim() + "%";
			session.setAttribute("bizSearch", bizSearch);
		}
		
		// 이전에 설정된 limit가 있는지 체크
		if (session.getAttribute("limit") != null) {
			limit = (Integer) session.getAttribute("limit");
		}

		// 변경된 limit가 있는지 체크
		if (request.getParameter("limit") != null) {
			limit = Integer.parseInt(request.getParameter("limit"));
			session.setAttribute("limit", limit);
		}
		
		int listcount = 0;
		// 총 리스트 수를 받아옴.
		if(bizSearchSelected.equals("bizName")){
			listcount = this.bizService.biz_searchNameCount(bizSearch);
		} else if(bizSearchSelected.equals("bizSubject")){
			listcount = this.bizService.biz_searchSubjectCount(bizSearch);
		} else if(bizSearchSelected.equals("bizLoc")){
			listcount = this.bizService.biz_searchLocCount(bizSearch);
		}
		
		int startrow = (page - 1) * limit + 1; // 읽기 시작할 row 번호.
		int endrow = startrow + limit - 1; // 읽을 마지막 row 번호.

		Map m = new HashMap();
		m.put("startrow", startrow);
		m.put("endrow", endrow);
		m.put("bizSearch", bizSearch);

		List<BizBean> bizList = new ArrayList<BizBean>();
		// 리스트를 받아옴.
		if(bizSearchSelected.equals("bizName")){
			bizList = bizService.biz_searchNameList(m);
		} else if(bizSearchSelected.equals("bizSubject")){
			bizList = bizService.biz_searchSubjectList(m);
		} else if(bizSearchSelected.equals("bizLoc")){
			bizList = bizService.biz_searchLocList(m);
		}
		
		// 세션에서 id를 받아서 biz_user_favorite에 저장
		String biz_user_favorite = (String) session.getAttribute("id");
		
		for(int i=0; i<bizList.size(); i++){
			// 글내용중 엔터키 친부분을 다음줄로 개행
			String biz_loc_1 = bizList.get(i).getBiz_loc_1().replace("시 ", "시<br/>");
			bizList.get(i).setBiz_loc_1(biz_loc_1);
		}

		// 로그인한 id가 즐겨찾기한 게시물의 번호를 받아옴
		List<BizFavoriteBean> favoriteList = bizService.biz_getFavoriteList(biz_user_favorite);
		
		// 게시글 번호와 즐겨찾기 번호를 비교해서 동일하면 biz_favorite_on을 1로 변경
		for(int i=0; i<bizList.size(); i++){
			int biz_favorite_on = 0;
			// bizboard DB의 biz_num을 가져옴
			int bizListNum = Integer.valueOf(bizList.get(i).getBiz_num());
			for(int j=0; j<favoriteList.size(); j++){
				// bizboard_favorite DB의 biz_num을 가져옴
				int favoriteListNum = Integer.valueOf(favoriteList.get(j).getBiz_num_favorite());
				// 비교
				if(bizListNum==favoriteListNum){
						biz_favorite_on = 1;
						break;
				}
			}
			// bizbean의 Biz_favorite_on 변수에 0이나 1을 저장
			bizList.get(i).setBiz_favorite_on(biz_favorite_on);
		}
		
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
		model.addObject("page", page);
		model.addObject("maxpage", maxpage);
		model.addObject("bizSearch", bizSearch);
		model.addObject("startpage", startpage);
		model.addObject("endpage", endpage);
		model.addObject("listcount", listcount);
		model.addObject("bizList", bizList);
		model.addObject("bizSearchSelected", bizSearchSelected);
		model.addObject("limit", limit);
		model.addObject("boardForm", boardForm);

		// biz_list.jsp의 옵션 index값 구하기
		int index = 0;
		if (limit == 3)
			index = 0;
		else if (limit == 5)
			index = 1;
		else if (limit == 10)
			index = 2;
		else if (limit == 15)
			index = 3;
		model.addObject("index", index);
		model.addObject("bodyAdd", "biz/biz_board_list");
		return model;
	}
}
