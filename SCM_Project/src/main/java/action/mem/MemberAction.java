package action.mem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.mem.MemberBean;
import model.mem.ZipcodeBean;
import model.mem.ZipcodeBean2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;

import dao.mem.MemberDAOImpl;

@Controller
public class MemberAction {

	private MemberDAOImpl memberService;

	//setter DI 설정
	
	@Autowired
	public void setMemberService(MemberDAOImpl memberService) {
		this.memberService = memberService;
	}
	
	// ID중복검사 ajax함수로 처리부분
		@RequestMapping(value="/member_idcheck.nhn",method=RequestMethod.POST)
		public void member_idcheck(
				HttpServletRequest request,
				HttpServletResponse response) throws Exception{
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			String id=request.getParameter("memid");
			System.out.println("id="+id);
			int re=memberService.checkMemberId(id);	
			out.print(re);	
		}	
		
	/*비번찾기*/
	@RequestMapping(value="/pwd_find.nhn")
	public String pwd_find(){
		return "mem/member/pwd_find";
	}
	
	/*로그인 폼 뷰*/
	@RequestMapping(value="/member_login.nhn")
	public String member_login(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		//response.setHeader("cache-control","no-cache,no-store");
		return "mem/member/member_login";
		//member 폴더의 member_login.jsp 뷰 페이지 실행
	}
	
	/*회원가입 폼*/
	@RequestMapping(value="/member_join.nhn")
	public String member_join(){
		return "mem/member/member_login";
		//member 폴더의 member_join.jsp 뷰 페이지 실행
	}
	
	
	
	/*비번찾기 완료 */
	@RequestMapping(value="/pwd_find_ok.nhn",method=RequestMethod.POST)
	public ModelAndView pwd_find_ok(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=response.getWriter();
        
		String id=request.getParameter("id").trim();
		String name=request.getParameter("name").trim();
		
		Map pm=new HashMap();
		//컬렉션 Map에 키와 값  저장합니다.
		pm.put("id",id);
		pm.put("name",name);
		
		MemberBean member=this.memberService.findpwd(pm);
		
		if(member == null){//회원 아이디와 이름이 맞지 않는 경우
			out.println("<script>");
			out.println("alert('회원아이디와 이름이 맞지 않습니다!')");
			out.println("history.go(-1)");
			out.println("</script>");
		}else{
			ModelAndView pwdM=new ModelAndView("mem/member/pwd_find");
			pwdM.addObject("pwdok",member.getJoin_pwd());
			return pwdM;
		}
		return null;
	}
	
	/*회원 가입 저장 */
	@RequestMapping(value="/member_join_ok.nhn",method=RequestMethod.POST)
	public ModelAndView member_join_ok(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		MemberBean m=new MemberBean();
		
		String saveFolder="D:/nsk/SCM_Project/src/main/webapp/upload";
		//이진파일 서버 업로드 경로
		int fileSize=5*1024*1024; //이진파일 업로드 최대 크기
		
			
		String join_id=request.getParameter("join_id").trim();//회원아이디
		String join_pwd=request.getParameter("join_pwd1").trim();//회원비번
		String join_name=request.getParameter("join_name").trim();//회원이름
        String join_email=request.getParameter("join_email").trim();

        	
            
            Random r=new Random();
            int random=r.nextInt(100000000);//1억사이의 정수형 난수를 발생
            
           m.setJoin_id(join_id);
           m.setJoin_pwd(join_pwd);
           m.setJoin_name(join_name);
           m.setJoin_email(join_email);
           
           this.memberService.insertMember(m);
           this.memberService.prf(m);
           
           //로그인 페이지로 이동
           response.sendRedirect("member_login.nhn");
          
		return null;
	}
	
	/*로그인 인증  */
	@RequestMapping(value="/member_login_ok.nhn",method=RequestMethod.POST)
	public ModelAndView member_login_ok(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session)
					throws Exception{
		
		//HttpSession 클래스는 세션객체를 생성해서 로그인 인증 처리를 하기 위해서입니다.
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();//출력스트림 객체 생성
		session=request.getSession();//세션 객체 생성
		
		String id=request.getParameter("id").trim();
		String pwd=request.getParameter("pwd").trim();
		
		MemberBean m=this.memberService.userCheck(id,pwd);
		
		if(m==null){//등록되지 않은 회원일때
			out.println("<script>");
			out.println("alert('등록되지 않은 회원입니다!')");
			out.println("history.back()");
			out.println("</script>");
		}else{//등록된 회원일때
			if(m.getJoin_pwd().equals(pwd)){//비번이 같을때
				session.setAttribute("id",id);
				
				String join_name=m.getJoin_name();
				String join_date = m.getJoin_date();
				session.setAttribute("join_date", join_date);
				session.setAttribute("join_name",join_name);
				
				//jsp폴더의 index.jsp로 이동
			/*	ModelAndView loginM=new ModelAndView("main");
				loginM.addObject("bodyAdd", "sns/sns_content");
				return loginM;*/
				response.sendRedirect("sns_list.sns");
			}else{//비번이 다를때
				out.println("<script>");
				out.println("alert('비번이 다릅니다!')");
				out.println("history.go(-1)");
				out.println("</script>");
			}
		}
		return null;
	}
	
	/*회원정보 수정 폼 
	@RequestMapping(value="/member_edit.nhn")
	public ModelAndView member_edit(HttpServletRequest request,
			HttpServletResponse response,HttpSession session)
					throws Exception{
		    response.setContentType("text/html;charset=UTF-8");
		    PrintWriter out=response.getWriter();
		    session=request.getSession();
		    
		    //아이디 키값의 세션아이디를 구함
		    String id=(String)session.getAttribute("id");
		    
		    String pwd=request.getParameter("pwd").trim();
		    
		    if(id==null){//세션 아이디 값이 없는 경우
		    	out.println("<script>");
		    	out.println("alert('다시 로그인 해주세요!')");
		    	out.println("location='member_login.nhn'");
		    	out.println("</script>");
		    }else{
		    	MemberBean editm=this.memberService.userCheck(id,pwd);
		    	//java.util 패키지의 StringTokenizer 클래스는 첫번째 전달인자를
		    	//두번째 -를 기준으로 문자열을  파싱해 줍니다.
		    	
		    	String join_email=editm.getJoin_email();
		    	//java.util 패키지의 StringTokenizer 클래스는 첫번째 전달인자를
		    	//두번째 @를 기준으로 문자열을  파싱해 줍니다.
		    	StringTokenizer st03=new StringTokenizer(join_email,"@");
		    	String join_mailid=st03.nextToken();
		    	String join_maildomain=st03.nextToken();
		    			    	
		    	ModelAndView m=new ModelAndView("member/member_edit");
		    	m.addObject("editm",editm);
		    	m.addObject("join_mailid",join_mailid);
		    	m.addObject("join_maildomain",join_maildomain);
		    	
		    	return m;
		    }
			return null;
	}
	
	회원정보 수정 
	@RequestMapping(value="/member_edit_ok.nhn",method=RequestMethod.POST)
	public ModelAndView member_edit_ok(
			HttpServletRequest request,
			HttpServletResponse response,HttpSession session)
					throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		session=request.getSession();//세션객체를 만듬
		MemberBean member=new MemberBean();
		
		String saveFolder="C:/jjh/SCM_Project/src/main/webapp/upload";
		int fileSize=5*1024*1024;
		
		MultipartRequest multi=null;
		multi=new MultipartRequest(request,saveFolder,fileSize,"UTF-8");
		
		String id=(String)session.getAttribute("id");
		String pwd=request.getParameter("pwd").trim();
		//세션아이디값을 취득
		if(id==null){
			out.println("<script>");
			out.println("alert('다시 로그인 해주세요!')");
			out.println("location='member_login.nhn'");
			out.println("</script>");
		}else{
			String join_pwd=multi.getParameter("join_pwd1").trim();
			String join_name=multi.getParameter("join_name").trim();
			String join_phone1=multi.getParameter("join_phone1").trim();
			String join_phone2=multi.getParameter("join_phone2").trim();
			String join_phone3=multi.getParameter("join_phone3").trim();
			String join_phone=join_phone1+"-"+join_phone2+"-"+join_phone3;
			String join_mailid=multi.getParameter("join_mailid").trim();
		    String join_maildomain=multi.getParameter("join_maildomain").trim();
		    String join_email=join_mailid+"@"+join_maildomain;
		    
		    //아이디를 기준으로 디비로 부터 회원정보를 가져옵니다.
		    MemberBean editm=this.memberService.userCheck(id,pwd);
		   
		    File UpFile=multi.getFile("join_profile");//첨부한 이진파일을 가져옴
		    if(UpFile != null){
		    	String fileName=UpFile.getName();//이진파일명 저장
		    	File DelFile=new File(saveFolder+editm.getJoin_profile());
		    	if(DelFile.exists()){//기존이진파일이 존재하면
		    		DelFile.delete();//기존 이진파일명을 삭제
		    	}
		    	Calendar c=Calendar.getInstance();
		    	int year=c.get(Calendar.YEAR);
		    	int month=c.get(Calendar.MONTH)+1;//+1을 한 이유는 1월이 0으로 반환
		    	int date=c.get(Calendar.DATE);
		    	String homedir=saveFolder+"/"+year+"-"+month+"-"+date;
                File path1=new File(homedir);
                if(!(path1.exists())){
                	path1.mkdir();//새로운 폴더를 생성
                }
                Random r=new Random();
                int random=r.nextInt(100000000);
                
                *//****확장자 구하기 시작 ****//*
    			int index = fileName.lastIndexOf(".");
    			String fileExtension = fileName.substring(index + 1);
    			*//****확장자 구하기 끝 ***//*
    			
    			String refileName="MEMBER"+year+month+date+random+"."+
    					fileExtension;//새로운 파일명을 저장
    			String fileDBName="/"+year+"-"+month+"-"+date+"/"+refileName;
                UpFile.renameTo(new File(homedir+"/"+refileName));
    			
    			
                //새로운 폴더에 바뀐 이진파일명으로 업로드       
                member.setJoin_profile(fileDBName);
                System.out.println("바뀐 파일명은 = " + member.getJoin_profile());
		    }
		    member.setJoin_id(id);
		    member.setJoin_pwd(join_pwd);
		    member.setJoin_name(join_name);
		    member.setJoin_phone(join_phone);
		    member.setJoin_email(join_email);
		    
		    this.memberService.updateMember(member);//수정 메서드 호출
		    
		  //이미지 폴더가 바로 적용 안됩니다.
		    ModelAndView mv = new ModelAndView("index");
		    mv.addObject("join_name", member.getJoin_name() );
		    mv.addObject("join_profile", member.getJoin_profile() );
		    mv.addObject("state", "edit");
		    return mv;
		}
		return null;
	}
	*/
	@RequestMapping(value="/member_logout.nhn")
	public String member_logout(
			HttpServletRequest request,
			HttpServletResponse response)
					throws Exception{
		
		return "mem/member/member_logout";
	}
	
}
