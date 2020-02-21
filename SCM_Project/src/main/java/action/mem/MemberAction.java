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

	//setter DI ����
	
	@Autowired
	public void setMemberService(MemberDAOImpl memberService) {
		this.memberService = memberService;
	}
	
	// ID�ߺ��˻� ajax�Լ��� ó���κ�
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
		
	/*���ã��*/
	@RequestMapping(value="/pwd_find.nhn")
	public String pwd_find(){
		return "mem/member/pwd_find";
	}
	
	/*�α��� �� ��*/
	@RequestMapping(value="/member_login.nhn")
	public String member_login(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		//response.setHeader("cache-control","no-cache,no-store");
		return "mem/member/member_login";
		//member ������ member_login.jsp �� ������ ����
	}
	
	/*ȸ������ ��*/
	@RequestMapping(value="/member_join.nhn")
	public String member_join(){
		return "mem/member/member_login";
		//member ������ member_join.jsp �� ������ ����
	}
	
	
	
	/*���ã�� �Ϸ� */
	@RequestMapping(value="/pwd_find_ok.nhn",method=RequestMethod.POST)
	public ModelAndView pwd_find_ok(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=response.getWriter();
        
		String id=request.getParameter("id").trim();
		String name=request.getParameter("name").trim();
		
		Map pm=new HashMap();
		//�÷��� Map�� Ű�� ��  �����մϴ�.
		pm.put("id",id);
		pm.put("name",name);
		
		MemberBean member=this.memberService.findpwd(pm);
		
		if(member == null){//ȸ�� ���̵�� �̸��� ���� �ʴ� ���
			out.println("<script>");
			out.println("alert('ȸ�����̵�� �̸��� ���� �ʽ��ϴ�!')");
			out.println("history.go(-1)");
			out.println("</script>");
		}else{
			ModelAndView pwdM=new ModelAndView("mem/member/pwd_find");
			pwdM.addObject("pwdok",member.getJoin_pwd());
			return pwdM;
		}
		return null;
	}
	
	/*ȸ�� ���� ���� */
	@RequestMapping(value="/member_join_ok.nhn",method=RequestMethod.POST)
	public ModelAndView member_join_ok(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		MemberBean m=new MemberBean();
		
		String saveFolder="D:/nsk/SCM_Project/src/main/webapp/upload";
		//�������� ���� ���ε� ���
		int fileSize=5*1024*1024; //�������� ���ε� �ִ� ũ��
		
			
		String join_id=request.getParameter("join_id").trim();//ȸ�����̵�
		String join_pwd=request.getParameter("join_pwd1").trim();//ȸ�����
		String join_name=request.getParameter("join_name").trim();//ȸ���̸�
        String join_email=request.getParameter("join_email").trim();

        	
            
            Random r=new Random();
            int random=r.nextInt(100000000);//1������� ������ ������ �߻�
            
           m.setJoin_id(join_id);
           m.setJoin_pwd(join_pwd);
           m.setJoin_name(join_name);
           m.setJoin_email(join_email);
           
           this.memberService.insertMember(m);
           this.memberService.prf(m);
           
           //�α��� �������� �̵�
           response.sendRedirect("member_login.nhn");
          
		return null;
	}
	
	/*�α��� ����  */
	@RequestMapping(value="/member_login_ok.nhn",method=RequestMethod.POST)
	public ModelAndView member_login_ok(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session)
					throws Exception{
		
		//HttpSession Ŭ������ ���ǰ�ü�� �����ؼ� �α��� ���� ó���� �ϱ� ���ؼ��Դϴ�.
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();//��½�Ʈ�� ��ü ����
		session=request.getSession();//���� ��ü ����
		
		String id=request.getParameter("id").trim();
		String pwd=request.getParameter("pwd").trim();
		
		MemberBean m=this.memberService.userCheck(id,pwd);
		
		if(m==null){//��ϵ��� ���� ȸ���϶�
			out.println("<script>");
			out.println("alert('��ϵ��� ���� ȸ���Դϴ�!')");
			out.println("history.back()");
			out.println("</script>");
		}else{//��ϵ� ȸ���϶�
			if(m.getJoin_pwd().equals(pwd)){//����� ������
				session.setAttribute("id",id);
				
				String join_name=m.getJoin_name();
				String join_date = m.getJoin_date();
				session.setAttribute("join_date", join_date);
				session.setAttribute("join_name",join_name);
				
				//jsp������ index.jsp�� �̵�
			/*	ModelAndView loginM=new ModelAndView("main");
				loginM.addObject("bodyAdd", "sns/sns_content");
				return loginM;*/
				response.sendRedirect("sns_list.sns");
			}else{//����� �ٸ���
				out.println("<script>");
				out.println("alert('����� �ٸ��ϴ�!')");
				out.println("history.go(-1)");
				out.println("</script>");
			}
		}
		return null;
	}
	
	/*ȸ������ ���� �� 
	@RequestMapping(value="/member_edit.nhn")
	public ModelAndView member_edit(HttpServletRequest request,
			HttpServletResponse response,HttpSession session)
					throws Exception{
		    response.setContentType("text/html;charset=UTF-8");
		    PrintWriter out=response.getWriter();
		    session=request.getSession();
		    
		    //���̵� Ű���� ���Ǿ��̵� ����
		    String id=(String)session.getAttribute("id");
		    
		    String pwd=request.getParameter("pwd").trim();
		    
		    if(id==null){//���� ���̵� ���� ���� ���
		    	out.println("<script>");
		    	out.println("alert('�ٽ� �α��� ���ּ���!')");
		    	out.println("location='member_login.nhn'");
		    	out.println("</script>");
		    }else{
		    	MemberBean editm=this.memberService.userCheck(id,pwd);
		    	//java.util ��Ű���� StringTokenizer Ŭ������ ù��° �������ڸ�
		    	//�ι�° -�� �������� ���ڿ���  �Ľ��� �ݴϴ�.
		    	
		    	String join_email=editm.getJoin_email();
		    	//java.util ��Ű���� StringTokenizer Ŭ������ ù��° �������ڸ�
		    	//�ι�° @�� �������� ���ڿ���  �Ľ��� �ݴϴ�.
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
	
	ȸ������ ���� 
	@RequestMapping(value="/member_edit_ok.nhn",method=RequestMethod.POST)
	public ModelAndView member_edit_ok(
			HttpServletRequest request,
			HttpServletResponse response,HttpSession session)
					throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		session=request.getSession();//���ǰ�ü�� ����
		MemberBean member=new MemberBean();
		
		String saveFolder="C:/jjh/SCM_Project/src/main/webapp/upload";
		int fileSize=5*1024*1024;
		
		MultipartRequest multi=null;
		multi=new MultipartRequest(request,saveFolder,fileSize,"UTF-8");
		
		String id=(String)session.getAttribute("id");
		String pwd=request.getParameter("pwd").trim();
		//���Ǿ��̵��� ���
		if(id==null){
			out.println("<script>");
			out.println("alert('�ٽ� �α��� ���ּ���!')");
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
		    
		    //���̵� �������� ���� ���� ȸ�������� �����ɴϴ�.
		    MemberBean editm=this.memberService.userCheck(id,pwd);
		   
		    File UpFile=multi.getFile("join_profile");//÷���� ���������� ������
		    if(UpFile != null){
		    	String fileName=UpFile.getName();//�������ϸ� ����
		    	File DelFile=new File(saveFolder+editm.getJoin_profile());
		    	if(DelFile.exists()){//�������������� �����ϸ�
		    		DelFile.delete();//���� �������ϸ��� ����
		    	}
		    	Calendar c=Calendar.getInstance();
		    	int year=c.get(Calendar.YEAR);
		    	int month=c.get(Calendar.MONTH)+1;//+1�� �� ������ 1���� 0���� ��ȯ
		    	int date=c.get(Calendar.DATE);
		    	String homedir=saveFolder+"/"+year+"-"+month+"-"+date;
                File path1=new File(homedir);
                if(!(path1.exists())){
                	path1.mkdir();//���ο� ������ ����
                }
                Random r=new Random();
                int random=r.nextInt(100000000);
                
                *//****Ȯ���� ���ϱ� ���� ****//*
    			int index = fileName.lastIndexOf(".");
    			String fileExtension = fileName.substring(index + 1);
    			*//****Ȯ���� ���ϱ� �� ***//*
    			
    			String refileName="MEMBER"+year+month+date+random+"."+
    					fileExtension;//���ο� ���ϸ��� ����
    			String fileDBName="/"+year+"-"+month+"-"+date+"/"+refileName;
                UpFile.renameTo(new File(homedir+"/"+refileName));
    			
    			
                //���ο� ������ �ٲ� �������ϸ����� ���ε�       
                member.setJoin_profile(fileDBName);
                System.out.println("�ٲ� ���ϸ��� = " + member.getJoin_profile());
		    }
		    member.setJoin_id(id);
		    member.setJoin_pwd(join_pwd);
		    member.setJoin_name(join_name);
		    member.setJoin_phone(join_phone);
		    member.setJoin_email(join_email);
		    
		    this.memberService.updateMember(member);//���� �޼��� ȣ��
		    
		  //�̹��� ������ �ٷ� ���� �ȵ˴ϴ�.
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
