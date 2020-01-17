create table PRICING_POLICY (
                               ID int not null,
                               CODE varchar(256) not null unique,
                               NAME varchar(1000),
                               DESCRIPTION varchar(1000),
                               CREATION_DATE DATE,
                               APPLIED_START_DATE DATE,
                               APPLIED_END_DATE DATE,
                               primary key (ID)
);

create sequence PP_SEQ
    start with 1
    increment by 1
    maxvalue 99999
    cycle;

create table CATEGORY (
                          ID int not null,
                          CODE varchar(256) not null unique,
                          NAME varchar(1000),
                          DESCRIPTION varchar(1000),
                          primary key (ID)
);

create sequence CATEGORY_SEQ
    start with 1
    increment by 1
    maxvalue 99999
    cycle;


create table ITEM (
                      ID int not null,
                      CODE varchar(256) not null unique,
                      NAME varchar(1000),
                      DESCRIPTION varchar(1000),
                      CATEGORY_ID int,
                      LABELS varchar(1000),
                      PRICE NUMERIC(18, 2),
                      PRICING_POLICY_ID int not null,
                      primary key (ID),
                      constraint FK_ITEM_CATEGORY foreign key (CATEGORY_ID) references CATEGORY(ID),
                      constraint FK_ITEM_PRICING_POLICY foreign key (PRICING_POLICY_ID) references PRICING_POLICY(ID)
);

create sequence ITEM_SEQ
    start with 1
    increment by 1
    maxvalue 99999
    cycle;
	

create table PRICING_HISTORY (
					ID int not null,
					ITEM_ID int not null, 
					PRICING_POLICY_ID int not null, 
					CREATION_DATE DATE,
					primary key (ID), 
					constraint FK_P_HISTORY_ITEM foreign key (ITEM_ID) references ITEM(ID), 
					constraint FK_P_HISTORY_PP foreign key (PRICING_POLICY_ID) references PRICING_POLICY(ID)
);

create sequence PH_SEQ
    start with 1
    increment by 1
    maxvalue 99999
    cycle;
