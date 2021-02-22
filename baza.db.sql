BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "professor" (
	"id"	INTEGER,
	"name"	TEXT,
	"surname"	TEXT,
	"email"	TEXT,
	"username"	TEXT,
	"password"	TEXT,
	"professors_subject"	INTEGER,
	"picture" TEXT,
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
	"visibility" INTEGER,
	PRIMARY KEY("id"),
	FOREIGN KEY("subject") REFERENCES "subject"("id")
);
INSERT INTO "subject" VALUES (1,'RPR');
INSERT INTO "subject" VALUES (2,'OBP');
INSERT INTO "subject" VALUES (3,'IM2');
INSERT INTO "subject" VALUES (4,'AFJ');
INSERT INTO "subject" VALUES (5,'IM1');
INSERT INTO "subject" VALUES (6,'IF1');
INSERT INTO "subject" VALUES (7,'IF2');
COMMIT;
