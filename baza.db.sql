BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "professor" (
	"id"	INTEGER,
	"name"	TEXT,
	"surname"	TEXT,
	"email"	TEXT,
	"username"	TEXT,
	"password"	TEXT,
	"professors_subject"	INTEGER,
	"picture"	TEXT,
	PRIMARY KEY("id"),
	FOREIGN KEY("professors_subject") REFERENCES "subject"("id")
);
CREATE TABLE IF NOT EXISTS "subject" (
	"id"	INTEGER,
	"name"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "material" (
	"id"	INTEGER,
	"name"	TEXT,
	"subject"	INTEGER,
	"visibility"	INTEGER,
	PRIMARY KEY("id"),
	FOREIGN KEY("subject") REFERENCES "subject"("id")
);
CREATE TABLE IF NOT EXISTS "student" (
	"id"	INTEGER,
	"name"	TEXT,
	"surname"	TEXT,
	"email"	TEXT,
	"username"	TEXT,
	"password"	TEXT,
	"picture"	TEXT,
	"index"	TEXT,
	PRIMARY KEY("id")
);
INSERT INTO "professor" VALUES (1,'Elma','Šeremet','eseremet1@etf.unsa.ba','eseremet1','Seremet123',2,'https://i.giphy.com/media/yFQ0ywscgobJK/giphy_s.gif');
INSERT INTO "subject" VALUES (1,'OBP');
INSERT INTO "subject" VALUES (2,'RPR');
INSERT INTO "subject" VALUES (3,'AFJ');
INSERT INTO "subject" VALUES (4,'IM1');
INSERT INTO "material" VALUES (0,'Elma',2,1);
INSERT INTO "material" VALUES (1,'Elma',2,1);
INSERT INTO "material" VALUES (2,'Proba-quiz',2,1);
COMMIT;
