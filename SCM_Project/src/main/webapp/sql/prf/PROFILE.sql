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

select*from PROFILE_TABLE;

drop table PROFILE_TABLE;

select * from PROFILE_TABLE where PROFILE_ID='aa';

delete from PROFILE_TABLE where PROFILE_ID='aaa';

update PROFILE_TABLE set PROFILE_ID='admin' where PROFILE_INTRO=1233;

update PROFILE_TABLE set PROFILE_PIC=' ' where PROFILE_ID='aa';s

insert into PROFILE_TABLE values('admin','ȫ�浿' ,1234 ,null ,null ,null ,null ,null ,null ,null ,null,null );

insert into PROFILE_TABLE values('aaa','���缮' ,1234 ,'''''''''''''''''''''''' ,null ,'''''''' ,'''' ,'''''''''''''''' ,'' ,'''''' ,null );
