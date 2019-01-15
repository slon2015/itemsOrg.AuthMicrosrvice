DROP TABLE IF EXISTS "Users";

CREATE TABLE "Users" (
  "ID"    BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  "Login" VARCHAR(255)                      NOT NULL,
  "Password" VARCHAR(255)                      NOT NULL,
  firstname VARCHAR(255),
  lastname VARCHAR(255),
  email VARCHAR(255),
  token VARCHAR(255),
  lifetime BIGINT,
  tokenbirthtime BIGINT
);