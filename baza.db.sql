BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "professor" (
	"id"	INTEGER,
	"name"	TEXT,
	"surname"	TEXT,
	"email"	TEXT,
	"username"	TEXT,
	"password"	TEXT,
	PRIMARY KEY("id")
);

INSERT INTO "professor" VALUES (1,'Elma','Seremet','eseremet1@etf.unsa.ba','eseremet1','proba');
COMMIT;
