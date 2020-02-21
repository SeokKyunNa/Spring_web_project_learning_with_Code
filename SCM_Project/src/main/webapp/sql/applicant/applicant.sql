create table applicant(
	BIZ_NUM			NUMBER(10),		-- ä����� ��ȣ
	APPLICANT_NAME	VARCHAR2(30),	-- 
	APPLICANT_ID	VARCHAR2(30),	-- �ۼ��� ���̵�
	APPLICANT_PIC	VARCHAR2(200),	-- �ۼ��� ����
	APPLICANT_AGE	VARCHAR2(30),	-- �ۼ��� ����
	APPLY_DATE		DATE,			-- ���� ��¥
	CONSTRAINT FK_APPLICANT FOREIGN KEY(BIZ_NUM)	-- BIZ_NUM�� �ܷ�Ű�� ����
	REFERENCES BIZ_BOARD(BIZ_NUM) ON DELETE CASCADE	-- BIZ_BOARD�� BIZ_NUM�� ����Ű�� ���� BIZ_BOARD���� ������ ���� ������
);

select*from applicant;

drop table applicant;

alter table applicant add (applicant_name varchar2(30));

update APPLICANT set applicant_name ='홍길동' where applicant_id='qwerty';
update APPLICANT set applicant_name ='나석균' where applicant_id='engsk1211';
