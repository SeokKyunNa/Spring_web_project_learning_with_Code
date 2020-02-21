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

select rownum rnum, sns.* from sns where write_user= 11 order by write_num desc

select * from sns
delete sns
drop table sns

create table sns_reply(
reply_wnum number,	--댓글원본글번호
reply_num number,	--댓글 번호
reply_user varchar2(20),	-- 댓글쓴이
reply_body varchar2(500),	--댓글내용
reply_date date,	--댓글쓴 날짜
reply_pic varchar2(200)	--댓글쓴이 프로필 사진
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


