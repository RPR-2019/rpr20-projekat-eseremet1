BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "professor" (
	"id"	INTEGER,
	"name"	TEXT,
	"surname"	TEXT,
	"email"	TEXT,
	"username"	TEXT,
	"password"	TEXT,
	"professors_subject"	INTEGER,
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
	"professor"	INTEGER,
	PRIMARY KEY("id"),
	FOREIGN KEY("professor") REFERENCES "professor"("id")
);
INSERT INTO "professor" VALUES (1,'Elma','Šeremet','eseremet1@etf.unsa.ba','eseremet1','Seremet123',1);
INSERT INTO "professor" VALUES (2,'Samir','Šeremet','sseremet1@etf.unsa.ba','sseremet1','samirproba',2);
INSERT INTO "professor" VALUES (4,'Lejla','Seremet','lseremet1@etf.unsa.ba','lseremet1','r0EPlmJJ',3);
INSERT INTO "professor" VALUES (5,'Ensar','Pilav','epilav1@etf.unsa.ba','epilav1','rgV7D5KD',2);
INSERT INTO "professor" VALUES (6,'Eldar','Šeremet','eseremet3@etf.unsa.ba','eseremet3','sbpW531A',1);
INSERT INTO "professor" VALUES (7,'Ziba','Pilav','zpilav1@etf.unsa.ba','zpilav1','vIXnh52K',2);
INSERT INTO "professor" VALUES (8,'Erdin','Alkic','ealkic1@etf.unsa.ba','ealkic1','WyT9tGbH',2);
INSERT INTO "professor" VALUES (9,'Lamija','Seremet','lseremet2@etf.unsa.ba','lseremet2','hh1A9qcE',2);
INSERT INTO "professor" VALUES (10,'Daris','Suljic','dpilav1@etf.unsa.ba','dpilav1','WUcXUw0A',1);
INSERT INTO "professor" VALUES (11,'Nejra','Pilav','npilav1@etf.unsa.ba','npilav1','zQO7e7PA',3);
INSERT INTO "professor" VALUES (12,'Dajana','Alkic','dalkic1@etf.unsa.ba','dalkic1','WCWw9IvL',2);
INSERT INTO "professor" VALUES (13,'Fatima','Bečirović','fbecirovic1@etf.unsa.ba','fbecirovic1','5z9QCLfH',4);
INSERT INTO "professor" VALUES (14,'Matej','Kolak','mkolak1@etf.unsa.ba','mkolak1','W9jhQydG',5);
INSERT INTO "subject" VALUES (1,'RPR');
INSERT INTO "subject" VALUES (2,'OBP');
INSERT INTO "subject" VALUES (3,'IM2');
INSERT INTO "subject" VALUES (4,'AFJ');
INSERT INTO "subject" VALUES (5,'IM1');
INSERT INTO "subject" VALUES (6,'IF1');
INSERT INTO "subject" VALUES (7,'IF2');
COMMIT;
