package org.zerock.persistence;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SampleMapper {

	@Select("select sysdate from dual")
	public String getTime();
	
	@Select("select email from tbl_member where userid = #{id} and userpw = #{pw}")
	public String getEmail(
		@Param("id") String id,
		@Param("pw") String pw
	);
	
}
