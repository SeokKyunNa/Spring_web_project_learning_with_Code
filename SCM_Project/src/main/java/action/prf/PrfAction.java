package action.prf;

import java.io.FileOutputStream;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.prf.PrfDAOImpl;
import model.prf.prfBean;

@Controller
public class PrfAction {
	private PrfDAOImpl prfService;

	@Autowired
	public void setPrfService(PrfDAOImpl prfService) {
		this.prfService = prfService;
	}

	// 프로필 작성 폼
	@RequestMapping(value = "/prf_write.prf")
	public ModelAndView prf_write(HttpServletRequest request, HttpServletResponse response)throws Exception {
		prfBean prfbean = new prfBean();
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		prfbean = prfService.getDetail(id);
		ModelAndView contM = new ModelAndView("main");
		contM.addObject("prfbean", prfbean);
		contM.addObject("bodyAdd","prf/profile_content");
		return contM;
	} 

	// 프로필등록,수정하기
	@RequestMapping(value = "/prf_write_ok.prf", method = RequestMethod.POST)
	public String prf_write_ok(HttpServletRequest request, HttpServletResponse response) throws Exception {
		prfBean prfbean =new prfBean();
		FileOutputStream fos;
		HttpSession session = request.getSession();
		//String saveFolder = "D:/nsk/SCM_Project/src/main/webapp/upload/prf";
		String saveFolder = "D:/nsk/SCM_Project/src/main/webapp/upload/prf";
		String realFolder = "";
		
		int fileSize = 5 * 1024 * 1024;
		
		realFolder=request.getRealPath(saveFolder);	
		
		String id = (String) session.getAttribute("id");
		String name = (String) session.getAttribute("join_name");
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multi = multipartRequest.getFile("PROFILE_PIC");
		
		String[] PROFILE_AGE = request.getParameterValues("PROFILE_AGE");
		String setPROFILE_AGE="";
		for(int i=0;i<PROFILE_AGE.length-1;i++){
			setPROFILE_AGE += PROFILE_AGE[i] + "'";
		}
		setPROFILE_AGE += PROFILE_AGE[PROFILE_AGE.length-1];
		
		String[] PROFILE_EDU = request.getParameterValues("PROFILE_EDU");
		String setProfile_edu="";
		for(int i=0;i<PROFILE_EDU.length-1;i++){
			setProfile_edu += PROFILE_EDU[i] + "'";
		}
		setProfile_edu += PROFILE_EDU[PROFILE_EDU.length-1];
		
		String PROFILE_INTRO = request.getParameter("PROFILE_INTRO").trim();
		
		String[] PROFILE_LANGU = request.getParameterValues("PROFILE_LANGU");
		String setProfile_langu="";
		for(int i=0;i<PROFILE_LANGU.length-1;i++){
			setProfile_langu += PROFILE_LANGU[i] + "'";
		}
		setProfile_langu += PROFILE_LANGU[PROFILE_LANGU.length-1];
		
		String[] PROFILE_INTEREST = request.getParameterValues("PROFILE_INTEREST");
		String setProfile_interest="";
		for(int i=0;i<PROFILE_INTEREST.length-1;i++){
			setProfile_interest += PROFILE_INTEREST[i] + "'";
		}
		setProfile_interest += PROFILE_INTEREST[PROFILE_INTEREST.length-1];
		
		String[] PROFILE_CARRER = request.getParameterValues("PROFILE_CARRER");
		String setProfile_carrer="";
		for(int i=0;i<PROFILE_CARRER.length-1;i++){
			setProfile_carrer += PROFILE_CARRER[i] + "'";
		}
		setProfile_carrer += PROFILE_CARRER[PROFILE_CARRER.length-1];
		
		String[] PROFILE_LOCAL = request.getParameterValues("PROFILE_LOCAL");
		String setProfile_local="";
		for(int i=0;i<PROFILE_LOCAL.length-1;i++){
			setProfile_local += PROFILE_LOCAL[i] + "'";
		}
		setProfile_local += PROFILE_LOCAL[PROFILE_LOCAL.length-1];
		
		String[] PROFILE_LISENCE = request.getParameterValues("PROFILE_LISENCE");
		String setProfile_lisence="";
		for(int i=0;i<PROFILE_LISENCE.length-1;i++){
			setProfile_lisence += PROFILE_LISENCE[i] + "'";
		}
		setProfile_lisence += PROFILE_LISENCE[PROFILE_LISENCE.length-1];
		String PROFILE_BOOLEAN =("OK");
		
		
		prfbean.setPROFILE_PIC(multi.getOriginalFilename());
		prfbean.setPROFILE_ID(id);
		prfbean.setPROFILE_USER(name);
		prfbean.setPROFILE_AGE(setPROFILE_AGE);
		prfbean.setPROFILE_EDU(setProfile_edu);
		prfbean.setPROFILE_INTRO(PROFILE_INTRO);
		prfbean.setPROFILE_LANGU(setProfile_langu);
		prfbean.setPROFILE_INTEREST(setProfile_interest);
		prfbean.setPROFILE_CARRER(setProfile_carrer);
		prfbean.setPROFILE_LISENCE(setProfile_lisence);
		prfbean.setPROFILE_LOCAL(setProfile_local);
		prfbean.setPROFILE_BOOLEAN(PROFILE_BOOLEAN);
			
		this.prfService.insertprf(prfbean);// 저장메서드 호출!
		
		byte fileData[] = multi.getBytes();
		fos = new FileOutputStream(saveFolder + "\\" + multi.getOriginalFilename());
		fos.write(fileData);
		
		response.sendRedirect("prf_view.prf");
		return null;
	}


	
	// 게시판 내용보기
	@RequestMapping(value = "/prf_view.prf")
	public ModelAndView prf_viwe(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		prfBean prfbean = new prfBean();
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		System.out.println("id = " +  id);
		  prfbean = prfService.getDetail(id);
			String age[] = prfbean.getPROFILE_AGE().split("'");
			String edu[] =  prfbean.getPROFILE_EDU().split("'");
			String langu[] = prfbean.getPROFILE_LANGU().split("'");
			String interest[] = prfbean.getPROFILE_INTEREST().split("'");
			String carrer[] = prfbean.getPROFILE_CARRER().split("'");
			String local[] = prfbean.getPROFILE_LOCAL().split("'");
			String lisence[] = prfbean.getPROFILE_LISENCE().split("'");
			ModelAndView contM = new ModelAndView("main");
			contM.addObject("prfbean", prfbean);
			contM.addObject("age", age);
			contM.addObject("edu", edu);
			contM.addObject("langu", langu);
			contM.addObject("interest", interest);
			contM.addObject("carrer", carrer);
			contM.addObject("local", local);
			contM.addObject("lisence", lisence);
			contM.addObject("bodyAdd","prf/profile_view");
			return contM;
	}
	// 게시판 수정폼
	@RequestMapping(value = "/prf_modify.prf")
	public ModelAndView prf_modify(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		prfBean prfbean = new prfBean();
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		prfbean = prfService.getDetail(id);
		if(prfbean==null) 
			System.out.println("빈이 비었어요");
		String age[] = prfbean.getPROFILE_AGE().split("'");
		String edu[] =  prfbean.getPROFILE_EDU().split("'");
		String langu[] = prfbean.getPROFILE_LANGU().split("'");
		String interest[] = prfbean.getPROFILE_INTEREST().split("'");
		String carrer[] = prfbean.getPROFILE_CARRER().split("'");
		String local[] = prfbean.getPROFILE_LOCAL().split("'");
		String lisence[] = prfbean.getPROFILE_LISENCE().split("'");
		ModelAndView contM = new ModelAndView("main");
		contM.addObject("prfbean", prfbean);
		contM.addObject("age", age);
		contM.addObject("edu", edu);
		contM.addObject("langu", langu);
		contM.addObject("interest", interest);
		contM.addObject("carrer", carrer);
		contM.addObject("local", local);
		contM.addObject("lisence", lisence);
		contM.addObject("bodyAdd","prf/profile_modify");
		return contM;
	}

}