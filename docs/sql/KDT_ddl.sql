DROP TABLE IF EXISTS public_pharmacy_data RESTRICT;
DROP TABLE IF EXISTS public_emergency_data RESTRICT;

-- 약국 공공데이터
CREATE TABLE public_pharmacy_data
(
    id   INTEGER      NOT NULL COMMENT '데이터번호',
    rnum VARCHAR(255) NOT NULL COMMENT '일련번호',
    name VARCHAR(255) NOT NULL COMMENT '상호명',
    lon  DOUBLE       NOT NULL COMMENT '경도',
    lat  DOUBLE       NOT NULL COMMENT '위도'
)
    COMMENT '공공데이터';

ALTER TABLE public_pharmacy_data
    ADD CONSTRAINT PK_public_pharmacy_data -- 데이터 기본키
        PRIMARY KEY (
                     id -- 데이터 번호
            );
ALTER TABLE public_pharmacy_data
    ADD CONSTRAINT UNIQUE KEY UK_public_pharmacy_data(rnum);

ALTER TABLE public_pharmacy_data
    MODIFY COLUMN id INTEGER NOT NULL AUTO_INCREMENT COMMENT '데이터번호';

-- 응급실 공공데이터
CREATE TABLE public_emergency_data
(
    id   INTEGER      NOT NULL COMMENT '데이터번호',
    rnum VARCHAR(255) NOT NULL COMMENT '일련번호',
    name VARCHAR(255) NULL COMMENT '상호명',
    lon  DOUBLE       NOT NULL COMMENT '경도',
    lat  DOUBLE       NOT NULL COMMENT '위도'
)
    COMMENT '공공데이터';

ALTER TABLE public_emergency_data
    ADD CONSTRAINT PK_public_emergency_data -- 데이터 기본키
        PRIMARY KEY (
                     id -- 데이터 번호
            );

ALTER TABLE public_emergency_data
    MODIFY COLUMN id INTEGER NOT NULL AUTO_INCREMENT COMMENT '데이터번호';