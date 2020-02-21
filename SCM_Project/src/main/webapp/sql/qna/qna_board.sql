--���� �Խ���
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








select * from qna_board;

select * from QNA_REPLY where qna_re_num =45;

select*from qna_reply where qna_re_num=44 
 		order by qna_re_ref asc, 
 		qna_re_seq desc




drop table qna_board;

drop table qna_reply;

drop sequence qna_seq; 




------------����----------------
select*from(
	select rownum rnum, qna_num, qna_id, qna_subject,
	qna_type, qna_body, qna_code,
	qna_file, qna_hits, qna_date, QNA_REPLY from(
		select*from qna_board order by qna_num desc
	)
)where rnum =(select rnum from(
					select rownum rnum, qna_num, qna_id, qna_subject,
					qna_type, qna_body, qna_code,
					qna_file, qna_hits, qna_date, QNA_REPLY from(
						select*from qna_board order by qna_num desc
					)
				)where qna_num = 3+1
			)
			or rnum = (select rnum from(
					select rownum rnum, qna_num, qna_id, qna_subject,
					qna_type, qna_body, qna_code,
					qna_file, qna_hits, qna_date, QNA_REPLY from(
						select*from qna_board order by qna_num desc
					)
				)where qna_num = 3-1
			)or rnum = (select rnum from(
					select rownum rnum, qna_num, qna_id, qna_subject,
					qna_type, qna_body, qna_code,
					qna_file, qna_hits, qna_date, QNA_REPLY from(
						select*from qna_board order by qna_num desc
					)
				)where qna_num = 3
			) 



-----------------------------------------------------

select*from(
			select rownum rnum, qna_num, qna_id, qna_subject,
			qna_type, qna_body, qna_code,
			qna_file, qna_hits, qna_date, qna_reply from(
				select*from qna_board order by qna_num desc)
			where qna_type = 'java'
		)
		where rnum >= ((1-1) * 1+1)  and rnum <= (1 * 10) 

select*from QNA_BOARD
		