package org.zerock.presistence;

import org.zerock.domain.MemberVO;

public interface MemberDAO {

	public String getTime();
	
	public void insertMember(MemberVO vo);
}
