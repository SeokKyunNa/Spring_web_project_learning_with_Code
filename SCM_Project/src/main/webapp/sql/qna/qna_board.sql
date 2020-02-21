--질문 게시판
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








select * from qna_board;

select * from QNA_REPLY where qna_re_num =45;

select*from qna_reply where qna_re_num=44 
 		order by qna_re_ref asc, 
 		qna_re_seq desc




drop table qna_board;

drop table qna_reply;

drop sequence qna_seq; 




------------연습----------------
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
		