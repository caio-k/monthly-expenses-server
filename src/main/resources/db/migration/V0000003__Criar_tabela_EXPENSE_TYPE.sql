CREATE SEQUENCE SQ_EXPENSE_TYPE_IDT
    INCREMENT 1
    MINVALUE 1
    START 1
    CACHE 1;

CREATE TABLE EXPENSE_TYPE (
	IDT_EXPENSE_TYPE    BIGINT NOT NULL,
    IDT_CUSTOMER        BIGINT NOT NULL,
	NAM_EXPENSE_TYPE    VARCHAR(255),
	CONSTRAINT IDT_EXPENSE_TYPE_PK PRIMARY KEY (IDT_EXPENSE_TYPE),
	CONSTRAINT EXPENSE_TYPE_CUSTOMER_FK FOREIGN KEY(IDT_CUSTOMER) REFERENCES CUSTOMER(IDT_CUSTOMER),
	UNIQUE (IDT_CUSTOMER, NAM_EXPENSE_TYPE)
);