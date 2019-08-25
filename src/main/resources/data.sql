DROP TABLE IF EXISTS sims;

CREATE TABLE sims (
  id NUMBER(19) PRIMARY KEY,
  iccid NUMBER(19) NOT NULL,
  minutes NUMBER(19),
  minutes_expire TIMESTAMP(6),
  gigabytes NUMBER(19),
  gigabytes_expire TIMESTAMP(6),
  active NUMBER(1,0)
);

INSERT INTO sims (id, iccid, minutes, minutes_expire, gigabytes, gigabytes_expire, active) VALUES
  (1, '8156125892165872169', 300, parsedatetime('17-09-2019 18:47:52', 'dd-MM-yyyy hh:mm:ss'),
   10, parsedatetime('18-09-2019 18:50:52', 'dd-MM-yyyy hh:mm:ss'), 1),
   (2, '9086125892165872280', 150, parsedatetime('19-09-2019 12:37:52', 'dd-MM-yyyy hh:mm:ss'),
   12, parsedatetime('20-09-2019 18:47:52', 'dd-MM-yyyy hh:mm:ss'), 0),
   (3, '5146125892165872320', 120, parsedatetime('17-09-2019 18:47:52', 'dd-MM-yyyy hh:mm:ss'),
   11, parsedatetime('18-09-2019 18:47:52', 'dd-MM-yyyy hh:mm:ss'), 1);

DROP SEQUENCE IF EXISTS SEQ_SIMS;

CREATE SEQUENCE SEQ_SIMS START WITH 32564;