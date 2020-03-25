-- 순번 생성을 위한 sequence 테이블
create sequence seq_board start with 1 increment by 1 nocache

-- BOARD TABLE
CREATE TABLE TBL_BOARD
(
    BNO NUMBER DEFAULT SEQ_BOARD.NEXTVAL NOT NULL,
    TITLE VARCHAR2(200) NOT NULL,
    "CONTENT" VARCHAR2(2000) NULL,	-- CLOB 타입을 사용해야 하지만 일단 VARCAHR2 사용
    WRITER VARCHAR2(50) NOT NULL,
    REGDATE TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
    UPDATEDATE TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
    VIEWCNT NUMBER DEFAULT 0,
    PRIMARY KEY (BNO)
);

alter table tbl_board add replycnt number default 0;

update tbl_board set replycnt =
    (select count(rno) from tbl_reply where bno = tbl_board.bno)
where bno > 0;

--DROP SEQUENCE SEQ_BOARD
--DROP TABLE TBL_BOARD

-- 순번 생성을 위한 SEQUENCE 테이블
CREATE SEQUENCE SEQ_REPLY START WITH 1 INCREMENT BY 1 NOCACHE

-- REPLY TABLE
CREATE TABLE TBL_REPLY(
    RNO NUMBER DEFAULT SEQ_REPLY.NEXTVAL NOT NULL,
    BNO NUMBER DEFAULT 0 NOT NULL,
    REPLYTEXT VARCHAR2(1000) NOT NULL,
    REPLYER VARCHAR2(50) NOT NULL,
    REGDATE TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
    UPDATEDATE TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
    PRIMARY KEY(RNO)
);

-- FOREIGN KEY 적용
ALTER TABLE TBL_REPLY ADD CONSTRAINT FK_BOARD
FOREIGN KEY (BNO) REFERENCES TBL_BOARD(BNO);

--DROP SEQUENCE SEQ_REPLY
--DROP TABLE TBL_REPLY

-- USER TABLE
CREATE TABLE TBL_USER(
  "UID" VARCHAR2(50) NOT NULL,
  UPW VARCHAR2(50) NOT NULL,
  UNAME VARCHAR2(100) NOT NULL,
  UPOINT NUMBER DEFAULT 0 NOT NULL,
  PRIMARY KEY ("UID")
);

--DROP TABLE TBL_USER;

-- 순번 생성을 위한 sequence 테이블
create sequence seq_message start with 1 increment by 1 nocache

-- MESSAGE TABLE
CREATE TABLE TBL_MESSAGE(
	MID NUMBER DEFAULT SEQ_MESSAGE.NEXTVAL NOT NULL,
	TARGETID VARCHAR2(50) NOT NULL,
	SENDER VARCHAR2(50) NOT NULL,
	MESSAGE VARCHAR2(2000) NOT NULL,
	OPENDATE TIMESTAMP,
	SENDDATE TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
	PRIMARY KEY (MID)
);

--DROP SEQUENCE SEQ_MESSAGE;
--DROP TABLE TBL_MESSAGE;

ALTER TABLE TBL_MESSAGE ADD CONSTRAINT FK_USERTARGET
FOREIGN KEY(TARGETID) REFERENCES TBL_USER("UID");

ALTER TABLE TBL_MESSAGE ADD CONSTRAINT FK_USERSENDER
FOREIGN KEY(SENDER) REFERENCES TBL_USER("UID");

-- 첨부파일
CREATE TABLE TBL_ATTACH(
    FULLNAME VARCHAR2(150)  NOT NULL,
    BNO NUMBER DEFAULT SEQ_BOARD.CURRVAL NOT NULL,
    REGDATE TIMESTAMP DEFAULT SYSTIMESTAMP,
    PRIMARY KEY(FULLNAME)
);

--DROP TABLE TBL_ATTACH;

alter table tbl_attach add constraint fk_board_attach
foreign key (bno) references tbl_board (bno);

alter table tbl_user add sessionkey varchar2(50) default 'none' not null;
alter table tbl_user add sessionlimit timestamp;