--join_member.sql

create table join_member(
  	join_code number(38) unique not null 
  	/*unique ���� ������ �ߺ����� ������� �ʰ� null�� ���*/  	
  , join_id varchar2(20) primary key /*ȸ�����̵�*/
  , join_pwd varchar2(20) not null /*ȸ�����*/
  , join_name varchar2(50) not null /*ȸ���̸�*/
  , join_email varchar2(100) not null /*���ڿ��� �ּ�*/
  , join_date date
);

drop table join_member;
/***** join_member ���̺��� join_code ������ ���� *****/
create sequence join1_member_joincode_seq 
increment by 1 start with 1 nocache;

--drop sequence join1_member_joincode_seq; 
update JOIN_MEMBER set join_date= sysdate where join_id='okey';

insert into join_member (join_code,join_id,join_pwd,join_name,
     join_phone,join_email)
      values(join_member_joincode_seq.nextval,
     '123','1','1',
         '33-33','123@naver.com');

select * from join_member;

--delete from join_member where join_code=21;

--update join_member set join_tel='032-999-9999' where join_id='bbbbb';

select * from join_member where join_id='qwerty' and join_state=1


insert into join_member values(        17, 'zxcv',      '123qwe',   '정지호',       '123@naver.com',           '2016-05-01 13:45:44.0')
insert into join_member values(        16, 'asdf',      '123qwe',   '이가연',    '123@naver.com',           '2016-05-01 13:44:15.0')
insert into join_member values(        15, 'qwer',      '123qwe',   '황희천',       '123@123.com',             '2016-05-01 13:43:44.0')
insert into join_member values(        14, 'petaz',     '123qwe',   '페타즈',       'petaz@naver.com',         '2016-05-01 12:59:58.0')
insert into join_member values(        13, 'mobon',    '123qwe',   '인라이플',      'mobon@naver.com',         '2016-05-01 12:57:34.0')
insert into join_member values(        12, 'people',    '123qwe',   '라온피플',      'people@naver.com',        '2016-05-01 12:53:13.0')
insert into join_member values(        11, 'bell',      '123qwe',   '벨아이앤에스',    'bell@naver.com',          '2016-05-01 12:50:00.0')
insert into join_member values(        10, 'diotis',    '123qwe',   '디오티스',      'disotis@diotis.co.kr',    '2016-05-01 12:46:50.0')
insert into join_member values(         9, 'gess',      '123qwe',   '게스',        'ges@ges.co.kr',           '2016-05-01 12:42:32.0')
insert into join_member values(         8, 'appstone',  '123qwe',   '앱스톤',       'appstone@appstone.co.kr', '2016-05-01 12:37:53.0')
insert into join_member values(         7, 'khinfo',    '123qwe',   'KH정보교육원',   'khinfo@iei.or.kr',        '2016-05-01 12:19:30.0')
insert into join_member values(         6, 'qwerty',    '123qwe',   '홍길동',       'qwerty@naver.com',        '2016-04-27 23:52:57.0')
insert into join_member values(         5, 'gosk',      '123qwe',   '구글',        'gosk@go.com',            '2016-04-27 23:05:23.0')
insert into join_member values(         4, 'sask',      '123qwe',   '삼성',        'sask@sa.com',             '2016-04-27 23:05:03.0')
insert into join_member values(         3, 'lgsk',      '123qwe',   'lg',        'lgsk@lg.com',             '2016-04-27 23:04:46.0')
insert into join_member values(         2, 'khsk',      '123qwe',   'KH정보',      'khsk@kh.com',             '2016-04-27 23:04:22.0')
insert into join_member values(         1, 'engsk1211', '123qwe',   '나석균',       'engsk1211@naver.com',     '2016-04-27 22:50:20.0')
