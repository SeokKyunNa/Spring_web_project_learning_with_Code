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

--DROP SEQUENCE SEQ_BOARD
--DROP TABLE TBL_BOARD
