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
CREATE TABLE IF NOT EXISTS "subject" (
	"id"	INTEGER,
	"name"	TEXT,
	PRIMARY KEY("id")
);
INSERT INTO "professor" VALUES (1,'Elma','Šeremet','eseremet1@etf.unsa.ba','eseremet1','proba');
INSERT INTO "professor" VALUES (2,'Samir','Seremet','sseremet1@etf.unsa.ba','sseremet1','IcUbJy6C');
COMMIT;
