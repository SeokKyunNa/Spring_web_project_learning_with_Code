-- 멤버 테이블
create table join_member(
  	join_code number(38) unique not null 
  	/*unique 제약 조건은 중복값을 허용하지 않고 null은 허용*/  	
  , join_id varchar2(20) primary key /*회원아이디*/
  , join_pwd varchar2(20) not null /*회원비번*/
  , join_name varchar2(50) not null /*회원이름*/
  , join_email varchar2(100) not null /*전자우편 주소*/
  , join_date date
);

create sequence join1_member_joincode_seq 
increment by 1 start with 1 nocache;

-- 비지니스 테이블 생성
CREATE TABLE BIZ_BOARD(
	BIZ_NUM				NUMBER(10),		-- 등록 번호
	BIZ_NAME			VARCHAR2(200),	-- 회사명
	BIZ_USER			VARCHAR2(30),	-- 작성자
	BIZ_SUBJECT			VARCHAR2(200),	-- 제목
	BIZ_CONTENT			VARCHAR2(1000),	-- 내용
	BIZ_ZIPCODE			VARCHAR2(20),	-- 우편번호
	BIZ_LOC_1			VARCHAR2(200),	-- 주소 (도, 시, 구)
	BIZ_LOC_2			VARCHAR2(200),	-- 나머지 주소
	BIZ_OCC				VARCHAR2(200),	-- 직무
	BIZ_POSITION		VARCHAR2(100),	-- 직위
	BIZ_PATTERN			VARCHAR2(200),	-- 고용형태
	BIZ_PAY				VARCHAR2(200),	-- 급여
	BIZ_EDUCATION		VARCHAR2(200),	-- 학력
	BIZ_CAREER			VARCHAR2(200),	-- 필요경력
	BIZ_FILE			VARCHAR2(200),	-- 파일
	BIZ_COUNT			NUMBER(5),		-- 지원자수
	BIZ_DATE			VARCHAR2(200),	-- 등록일
	BIZ_EXPIRY_DATE		VARCHAR2(200),	-- 마감일
	BIZ_PRF_IMG			VARCHAR2(200),	-- 프로필 사진
	CONSTRAINT PK_BIZ_BOARD PRIMARY KEY(BIZ_NUM)
);

-- 비지니스 시퀀스 생성
create sequence biz_board_seq
start with 1
increment by 1
nocache;

-- 비지니스 즐겨찾기
CREATE TABLE BIZ_BOARD_FAVORITE(
	BIZ_NUM_FAVORITE	NUMBER(10),		-- 채용공고 등록 번호
	BIZ_USER_FAVORITE	VARCHAR2(50),	-- 즐겨찾기 등록 유저 ID
	CONSTRAINT FK_BIZ_BOARD_FAVORITE FOREIGN KEY(BIZ_NUM_FAVORITE)	-- BIZ_NUM_FAVORITE를 외래키로 지정
	REFERENCES BIZ_BOARD(BIZ_NUM) ON DELETE CASCADE	-- BIZ_BOARD의 BIZ_NUM을 참조키로 갖고 BIZ_BOARD에서 삭제시 같이 삭제됨
);

-- 지원자
create table applicant(
	BIZ_NUM			NUMBER(10),		-- 채용공고 번호
	APPLICANT_ID	VARCHAR2(30),	-- 작성자 아이디
	APPLICANT_PIC	VARCHAR2(200),	-- 작성자 사진
	APPLICANT_AGE	VARCHAR2(30),	-- 작성자 나이
	APPLY_DATE		DATE,			-- 지원 날짜
	CONSTRAINT FK_APPLICANT FOREIGN KEY(BIZ_NUM)	-- BIZ_NUM을 외래키로 지정
	REFERENCES BIZ_BOARD(BIZ_NUM) ON DELETE CASCADE	-- BIZ_BOARD의 BIZ_NUM을 참조키로 갖고 BIZ_BOARD에서 삭제시 같이 삭제됨
);

-- 주소 검색
create table zipcode (
  no number PRIMARY KEY
  ,ZIPCODE varchar2(7)
  ,sido varchar2(100) 
  ,gugun varchar2(100) 
  ,dong varchar2(50) 
  ,bunji varchar2(50) 
);

-- 메시지
CREATE TABLE msg(
	MESSAGE_NUM NUMBER(15),  --고유번호	
	MESSAGE_USER VARCHAR2(50), --보낸사람
	MESSAGE_SUBJECT VARCHAR2(50), --제목
	MESSAGE_BODY VARCHAR2(1000), --내용
	MESSAGE_REC VARCHAR2(50), --받는사람
	MESSAGE_FILE VARCHAR2(50),--첨부될파일명
	MESSAGE_CHECK VARCHAR2(10), --확인, 미확인
	MESSAGE_DATE DATE,          --글작성날짜
	PRIMARY KEY(MESSAGE_NUM)
);

create sequence msg1_seq
start with 1
increment by 1
nocache;

-- 프로필
create table PROFILE_TABLE(
	PROFILE_ID varchar2(30),		--작성자 아이디
	PROFILE_USER varchar2(30),		--작성자 이름
	PROFILE_AGE varchar2(30),		--패스워드
	PROFILE_EDU varchar2(1000),		--학력
	PROFILE_INTRO varchar2(1000),	--자기소개
	PROFILE_LANGU varchar2(1000),	--외국어수준
	PROFILE_INTEREST varchar2(1000),--관심분야
	PROFILE_CARRER varchar2(1000),	--경력사항
	PROFILE_LOCAL varchar2(1000),	--희망 취업지역
	PROFILE_LISENCE varchar2(1000),	--자격증정보
	PROFILE_PIC varchar2(1000),		--프로필사진
	PROFILE_BOOLEAN varchar2(10)	--프로필 작성여부 확인
);

-- 질문게시판
create table qna_board(
	qna_num number,				--글 번호
	qna_id varchar2(30),		--사용자 아이디
	qna_subject varchar2(50),	--글 제목
	qna_type varchar2(20),		--소스코드 타입(java, c++...)
	qna_body varchar2(1000),	--글 내용
	qna_code varchar2(1000),	--소스코드 내용
	qna_file varchar2(1000),	--첨부파일 명
	qna_hits number,			--조회수
	qna_date date,				--작성 날짜
	qna_reply number			--댓글 갯수 저장하는 공간
);
--질문게시판에 작성된 댓글
create table qna_reply(
	qna_re_num number,			--댓글 번호(본문넘버를 참조한다.)
	qna_re_id varchar2(50),		--댓글 작성자
	qna_re_body varchar2(100),	--댓글 내용
	qna_re_ref number,			--참조되는 글의 번호--같은 id로 작성한 글의 갯수
	qna_re_lev number,			--댓글의 깊이
	qna_re_seq number,			--댓글의 순서
	qna_re_date date			--작성 날짜
);

--글번호에 사용한 시퀸스
create sequence qna_seq
start with 1
increment by 1
nocache;

-- sns
create table sns(
write_num number,	--글번호
write_user varchar2(20),	--글쓴이
write_body varchar2(3000),	--글내용
write_date date,	--글쓴 날짜
write_good number,	--좋아요 수
write_pic varchar2(200),	--글쓴이 프로필사진
write_reply number, 		--댓글 수
write_file varchar2(1000)	--다중파일
);

create sequence sns_num_seq
increment by 1 start with 1 nocache;

-- sns 댓글
create table sns_reply(
reply_wnum number,	--댓글원본글번호
reply_num number,	--댓글 번호
reply_user varchar2(20),	-- 댓글쓴이
reply_body varchar2(500),	--댓글내용
reply_date date,	--댓글쓴 날짜
reply_pic varchar2(200)	--댓글쓴이 프로필 사진
);

create sequence sns_seq
increment by 1 start with 1 nocache;

-- sns 좋아요
create table good(
goodnum number(10),		--좋아요의 원글 번호
goodid varchar2(30)	--좋아요누른 아이디
);