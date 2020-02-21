-- ��� ���̺�
create table join_member(
  	join_code number(38) unique not null 
  	/*unique ���� ������ �ߺ����� ������� �ʰ� null�� ���*/  	
  , join_id varchar2(20) primary key /*ȸ�����̵�*/
  , join_pwd varchar2(20) not null /*ȸ�����*/
  , join_name varchar2(50) not null /*ȸ���̸�*/
  , join_email varchar2(100) not null /*���ڿ��� �ּ�*/
  , join_date date
);

create sequence join1_member_joincode_seq 
increment by 1 start with 1 nocache;

-- �����Ͻ� ���̺� ����
CREATE TABLE BIZ_BOARD(
	BIZ_NUM				NUMBER(10),		-- ��� ��ȣ
	BIZ_NAME			VARCHAR2(200),	-- ȸ���
	BIZ_USER			VARCHAR2(30),	-- �ۼ���
	BIZ_SUBJECT			VARCHAR2(200),	-- ����
	BIZ_CONTENT			VARCHAR2(1000),	-- ����
	BIZ_ZIPCODE			VARCHAR2(20),	-- �����ȣ
	BIZ_LOC_1			VARCHAR2(200),	-- �ּ� (��, ��, ��)
	BIZ_LOC_2			VARCHAR2(200),	-- ������ �ּ�
	BIZ_OCC				VARCHAR2(200),	-- ����
	BIZ_POSITION		VARCHAR2(100),	-- ����
	BIZ_PATTERN			VARCHAR2(200),	-- �������
	BIZ_PAY				VARCHAR2(200),	-- �޿�
	BIZ_EDUCATION		VARCHAR2(200),	-- �з�
	BIZ_CAREER			VARCHAR2(200),	-- �ʿ���
	BIZ_FILE			VARCHAR2(200),	-- ����
	BIZ_COUNT			NUMBER(5),		-- �����ڼ�
	BIZ_DATE			VARCHAR2(200),	-- �����
	BIZ_EXPIRY_DATE		VARCHAR2(200),	-- ������
	BIZ_PRF_IMG			VARCHAR2(200),	-- ������ ����
	CONSTRAINT PK_BIZ_BOARD PRIMARY KEY(BIZ_NUM)
);

-- �����Ͻ� ������ ����
create sequence biz_board_seq
start with 1
increment by 1
nocache;

-- �����Ͻ� ���ã��
CREATE TABLE BIZ_BOARD_FAVORITE(
	BIZ_NUM_FAVORITE	NUMBER(10),		-- ä����� ��� ��ȣ
	BIZ_USER_FAVORITE	VARCHAR2(50),	-- ���ã�� ��� ���� ID
	CONSTRAINT FK_BIZ_BOARD_FAVORITE FOREIGN KEY(BIZ_NUM_FAVORITE)	-- BIZ_NUM_FAVORITE�� �ܷ�Ű�� ����
	REFERENCES BIZ_BOARD(BIZ_NUM) ON DELETE CASCADE	-- BIZ_BOARD�� BIZ_NUM�� ����Ű�� ���� BIZ_BOARD���� ������ ���� ������
);

-- ������
create table applicant(
	BIZ_NUM			NUMBER(10),		-- ä����� ��ȣ
	APPLICANT_ID	VARCHAR2(30),	-- �ۼ��� ���̵�
	APPLICANT_PIC	VARCHAR2(200),	-- �ۼ��� ����
	APPLICANT_AGE	VARCHAR2(30),	-- �ۼ��� ����
	APPLY_DATE		DATE,			-- ���� ��¥
	CONSTRAINT FK_APPLICANT FOREIGN KEY(BIZ_NUM)	-- BIZ_NUM�� �ܷ�Ű�� ����
	REFERENCES BIZ_BOARD(BIZ_NUM) ON DELETE CASCADE	-- BIZ_BOARD�� BIZ_NUM�� ����Ű�� ���� BIZ_BOARD���� ������ ���� ������
);

-- �ּ� �˻�
create table zipcode (
  no number PRIMARY KEY
  ,ZIPCODE varchar2(7)
  ,sido varchar2(100) 
  ,gugun varchar2(100) 
  ,dong varchar2(50) 
  ,bunji varchar2(50) 
);

-- �޽���
CREATE TABLE msg(
	MESSAGE_NUM NUMBER(15),  --������ȣ	
	MESSAGE_USER VARCHAR2(50), --�������
	MESSAGE_SUBJECT VARCHAR2(50), --����
	MESSAGE_BODY VARCHAR2(1000), --����
	MESSAGE_REC VARCHAR2(50), --�޴»��
	MESSAGE_FILE VARCHAR2(50),--÷�ε����ϸ�
	MESSAGE_CHECK VARCHAR2(10), --Ȯ��, ��Ȯ��
	MESSAGE_DATE DATE,          --���ۼ���¥
	PRIMARY KEY(MESSAGE_NUM)
);

create sequence msg1_seq
start with 1
increment by 1
nocache;

-- ������
create table PROFILE_TABLE(
	PROFILE_ID varchar2(30),		--�ۼ��� ���̵�
	PROFILE_USER varchar2(30),		--�ۼ��� �̸�
	PROFILE_AGE varchar2(30),		--�н�����
	PROFILE_EDU varchar2(1000),		--�з�
	PROFILE_INTRO varchar2(1000),	--�ڱ�Ұ�
	PROFILE_LANGU varchar2(1000),	--�ܱ������
	PROFILE_INTEREST varchar2(1000),--���ɺо�
	PROFILE_CARRER varchar2(1000),	--��»���
	PROFILE_LOCAL varchar2(1000),	--��� �������
	PROFILE_LISENCE varchar2(1000),	--�ڰ�������
	PROFILE_PIC varchar2(1000),		--�����ʻ���
	PROFILE_BOOLEAN varchar2(10)	--������ �ۼ����� Ȯ��
);

-- �����Խ���
create table qna_board(
	qna_num number,				--�� ��ȣ
	qna_id varchar2(30),		--����� ���̵�
	qna_subject varchar2(50),	--�� ����
	qna_type varchar2(20),		--�ҽ��ڵ� Ÿ��(java, c++...)
	qna_body varchar2(1000),	--�� ����
	qna_code varchar2(1000),	--�ҽ��ڵ� ����
	qna_file varchar2(1000),	--÷������ ��
	qna_hits number,			--��ȸ��
	qna_date date,				--�ۼ� ��¥
	qna_reply number			--��� ���� �����ϴ� ����
);
--�����Խ��ǿ� �ۼ��� ���
create table qna_reply(
	qna_re_num number,			--��� ��ȣ(�����ѹ��� �����Ѵ�.)
	qna_re_id varchar2(50),		--��� �ۼ���
	qna_re_body varchar2(100),	--��� ����
	qna_re_ref number,			--�����Ǵ� ���� ��ȣ--���� id�� �ۼ��� ���� ����
	qna_re_lev number,			--����� ����
	qna_re_seq number,			--����� ����
	qna_re_date date			--�ۼ� ��¥
);

--�۹�ȣ�� ����� ������
create sequence qna_seq
start with 1
increment by 1
nocache;

-- sns
create table sns(
write_num number,	--�۹�ȣ
write_user varchar2(20),	--�۾���
write_body varchar2(3000),	--�۳���
write_date date,	--�۾� ��¥
write_good number,	--���ƿ� ��
write_pic varchar2(200),	--�۾��� �����ʻ���
write_reply number, 		--��� ��
write_file varchar2(1000)	--��������
);

create sequence sns_num_seq
increment by 1 start with 1 nocache;

-- sns ���
create table sns_reply(
reply_wnum number,	--��ۿ����۹�ȣ
reply_num number,	--��� ��ȣ
reply_user varchar2(20),	-- ��۾���
reply_body varchar2(500),	--��۳���
reply_date date,	--��۾� ��¥
reply_pic varchar2(200)	--��۾��� ������ ����
);

create sequence sns_seq
increment by 1 start with 1 nocache;

-- sns ���ƿ�
create table good(
goodnum number(10),		--���ƿ��� ���� ��ȣ
goodid varchar2(30)	--���ƿ䴩�� ���̵�
);