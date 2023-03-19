CREATE TABLE IF NOT EXISTS SPRING_SESSION (
                                SESSION_ID CHAR(36) NOT NULL,
                                CREATION_TIME BIGINT NOT NULL,
                                LAST_ACCESS_TIME BIGINT NOT NULL,
                                MAX_INACTIVE_INTERVAL INT NOT NULL,
                                EXPIRY_TIME BIGINT NOT NULL,
                                PRINCIPAL_NAME VARCHAR(100),
                                SESSION_ATTRIBUTES BINARY(20000),
                                CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (SESSION_ID)
);
