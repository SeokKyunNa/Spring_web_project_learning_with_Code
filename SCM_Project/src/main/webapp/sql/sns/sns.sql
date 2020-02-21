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

select rownum rnum, sns.* from sns where write_user= 11 order by write_num desc

select * from sns
delete sns
drop table sns

create table sns_reply(
reply_wnum number,	--��ۿ����۹�ȣ
reply_num number,	--��� ��ȣ
reply_user varchar2(20),	-- ��۾���
reply_body varchar2(500),	--��۳���
reply_date date,	--��۾� ��¥
reply_pic varchar2(200)	--��۾��� ������ ����
);
select * from sns_REPLY where reply_wnum=5 order by reply_date;
delete sns_REPLY where reply_num=1 and reply_wnum=2;
drop table sns_reply
select * from sns_REPLY;

select * from sns_reply INNER JOIN sns ON sns_reply.reply_wnum = sns.write_num order by REPLY_DATE

create sequence sns_num_seq
increment by 1 start with 1 nocache;

drop sequence sns_num_seq

create sequence sns_seq
increment by 1 start with 1 nocache;

select * from good INNER JOIN sns ON good.goodnum = sns.write_num where goodid=11 order by goodnum

	     select * from
		(select rownum rnum, write_num, write_user,write_body,
		write_date, write_good, write_file ,write_pic, write_reply
		from
		(select * from
		sns order by write_num
		desc)) left outer JOIN good on write_num = good.goodnum 
		where goodid= 11
		and	rnum >=1
		and rnum <=10

select write_user,write_pic from sns order by write_num desc


