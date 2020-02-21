

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

select * from msg;

delete from Msg;

drop table msg;
drop sequence msg_seq

create sequence msg1_seq
start with 1
increment by 1
nocache;

select * from (select rownum rnum,MESSAGE_NUM,MESSAGE_USER,MESSAGE_SUBJECT,MESSAGE_BODY,MESSAGE_FILE,MESSAGE_REC,MESSAGE_CHECK,MESSAGE_DATE from (select * from MESSAGE1 order by MESSAGE_NUM asc)) where rnum>=1 and rnum<=10;

update MESSAGE1 set MESSAGE_CHECK='Ȯ��' where MESSAGE_NUM=3 and MESSAGE_CHECK='��Ȯ��';

 insert into msg (MESSAGE_NUM,MESSAGE_USER,MESSAGE_REC,
      MESSAGE_SUBJECT,MESSAGE_BODY, MESSAGE_FILE,MESSAGE_CHECK,
      MESSAGE_DATE) 
      values
      (Msg1_seq.NEXTVAL,'�۾���','1',
      '1','1',
      '1','��Ȯ��',
      sysdate)
update msg set MESSAGE_CHECK='Ȯ��' where MESSAGE_NUM=5 and MESSAGE_CHECK='��Ȯ��';

 select * from
		(select rownum rnum,MESSAGE_NUM,MESSAGE_USER,MESSAGE_SUBJECT,
		MESSAGE_BODY,MESSAGE_FILE,MESSAGE_REC,MESSAGE_CHECK,MESSAGE_DATE from 
		(select * from msg where MESSAGE_REC='123'  or MESSAGE_USER='123' order by MESSAGE_NUM desc))
				where rnum>= '1' and rnum<= '2'
				
				
				
select rownum rnum,MESSAGE_NUM,MESSAGE_USER,MESSAGE_SUBJECT,
				MESSAGE_BODY,MESSAGE_FILE,MESSAGE_REC,MESSAGE_CHECK,MESSAGE_DATE from
				(select * from MESSAGE1 where MESSAGE_REC= 'f111' order by MESSAGE_NUM desc)				
				