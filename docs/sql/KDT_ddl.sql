DROP TABLE IF EXISTS public_data RESTRICT;

CREATE TABLE public_data
(
    id   INTEGER      NOT NULL COMMENT '데이터번호',
    rnum VARCHAR(255) NOT NULL COMMENT '일련번호',
    name VARCHAR(255) NOT NULL COMMENT '상호명',
    lon  DOUBLE       NOT NULL COMMENT '경도',
    lat  DOUBLE       NOT NULL COMMENT '위도'
)
    COMMENT '공공데이터';

ALTER TABLE public_data
    ADD CONSTRAINT PK_public_data -- 데이터 기본키
        PRIMARY KEY (
                     id -- 데이터 번호
            );

ALTER TABLE public_data
    MODIFY COLUMN id INTEGER NOT NULL AUTO_INCREMENT COMMENT '데이터번호';