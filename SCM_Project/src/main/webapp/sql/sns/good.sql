create table good(
goodnum number(10),		--���ƿ��� ���� ��ȣ
goodid varchar2(30)	--���ƿ䴩�� ���̵�
)

drop table good

select * from good
  select * from good INNER JOIN sns ON good.goodnum = sns.write_num where goodid=11 order by goodnum

  select * from
		(select rownum rnum, write_num, write_user,write_body,
		write_date, write_good, write_file ,write_pic, write_reply
		from
		(select * from
		sns order by write_num
		desc)) INNER JOIN good on write_num = good.goodnum where goodid=11 
		
		select * from good INNER JOIN sns
		ON good.goodnum = sns.write_num where goodid=11 order by goodnum