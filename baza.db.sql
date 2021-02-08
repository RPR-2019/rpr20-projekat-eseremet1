BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "professor" (
	"id"	INTEGER,
	"name"	TEXT,
	"surname"	TEXT,
	"email"	TEXT,
	"username"	TEXT,
	"password"	TEXT,
	"professors_subject" INTEGER,
	PRIMARY KEY("id"),
	FOREIGN KEY("professors_subject") REFERENCES "subject"("id")
);
CREATE TABLE IF NOT EXISTS "subject" (
	"id"	INTEGER,
	"name"	TEXT,
	"subjects_professor" INTEGER,
	PRIMARY KEY("id"),
	FOREIGN KEY("subjects_professor") REFERENCES "professor"("id")
);
INSERT INTO "professor" VALUES (1,'Elma','Šeremet','eseremet1@etf.unsa.ba','eseremet1','proba',1);
INSERT INTO "professor" VALUES (2,'Samir','Šeremet','sseremet1@etf.unsa.ba','sseremet1','proba',2);
INSERT INTO "subject" VALUES (1,'RPR',1);
INSERT INTO "subject" VALUES (2,'OBP',2);
COMMIT;
