CREATE table "VISIT" (
    "ID"             NUMBER NOT NULL,
    "COUNTRY"        VARCHAR2(200),
    "DATETIME_FIELD" DATE,
    "JAVAVERSION"    VARCHAR2(50),
    "LATITUDE"       FLOAT,
    "LONGITUDE"      FLOAT,
    constraint  "VISIT_PK" primary key ("ID")
)
/

CREATE sequence "VISIT_SEQ" 
/

CREATE trigger "BI_VISIT"  
  before insert on "VISIT"              
  for each row 
begin  
  if :NEW."ID" is null then
    select "VISIT_SEQ".nextval into :NEW."ID" from sys.dual;
  end if;
end;
/   

