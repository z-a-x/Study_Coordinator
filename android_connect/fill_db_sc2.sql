INSERT into user (user_name, user_last_name, username, password, email)
VALUES ("robert",
        "rozina",
        "rr",
        "rr",
        "rr@gmail.com");

INSERT into user (user_name, user_last_name, username, password, email)
VALUES ("ales",
        "bokal",
        "ab",
        "ab",
        "ab@gmail.com");

INSERT into user (user_name, user_last_name, username, password, email)
VALUES ("jure",
        "sorn",
        "js",
        "js",
        "js@gmail.com");

INSERT into user (user_name, user_last_name, username, password, email)
VALUES ("jaka",
        "plut",
        "jp",
        "jp",
        "jp@gmail.com");

INSERT into user (user_name, user_last_name, username, password, email)
VALUES ("france",
        "presern",
        "fp",
        "fp",
        "fp@gmail.com");

INSERT into user (user_name, user_last_name, username, password, email)
VALUES ("valentino",
        "rossi",
        "vr",
        "vr",
        "vr@gmail.com");


INSERT into user (user_name, user_last_name, username, password, email)
VALUES ("anton",
        "askerc",
        "aa",
        "aa",
        "aa@gmail.com");


INSERT into user (user_name, user_last_name, username, password, email)
VALUES ("ghostface",
        "killa",
        "gk",
        "gk",
        "gk@gmail.com");


INSERT into user (user_name, user_last_name, username, password, email)
VALUES ("slim",
        "shady",
        "ss",
        "ss",
        "ss@gmail.com");

INSERT into user (user_name, user_last_name, username, password, email)
VALUES ("charles",
        "xavier",
        "cx",
        "cx",
        "cx@gmail.com");

INSERT into user (user_name, user_last_name, username, password, email)
VALUES ("stric",
        "bedanc",
        "sb",
        "sb",
        "sb@gmail.com");

INSERT into user (user_name, user_last_name, username, password, email)
VALUES ("jules",
        "winnfield",
        "jw",
        "jw",
        "jw@gmail.com");

INSERT into user (user_name, user_last_name, username, password, email)
VALUES ("jack",
        "sparrow",
        "js",
        "js",
        "js@gmail.com");

INSERT into user (user_name, user_last_name, username, password, email)
VALUES ("vincent",
        "vega",
        "vv",
        "vv",
        "vv@gmail.com");



/*--------------------------------------------------------------------------*/
INSERT into groups (group_name, groups_owner)
VALUES ("Biologi", 1);

INSERT into groups (group_name, groups_owner)
VALUES ("Kemiki", 1);

INSERT into groups (group_name, groups_owner)
VALUES ("Matematiki", 1);

INSERT into groups (group_name, groups_owner)
VALUES ("Strojniki", 1);


INSERT into groups (group_name, groups_owner)
VALUES ("Filozofi", 1);


INSERT into groups (group_name, groups_owner)
VALUES ("Zdravstveniki", 1);


INSERT into groups (group_name, groups_owner)
VALUES ("Medicinci", 1);


INSERT into groups (group_name, groups_owner)
VALUES ("Elektrotehniki", 1);


INSERT into groups (group_name, groups_owner)
VALUES ("Glasbeniki", 1);


INSERT into groups (group_name, groups_owner)
VALUES ("Likovniki", 1);


INSERT into groups (group_name, groups_owner)
VALUES ("Pedagogi", 1);


INSERT into groups (group_name, groups_owner)
VALUES ("Obramboslovci", 1);

/*--------------------------------------------------------------------------*/
INSERT into user_group (group_id, user_id)
VALUES (2, 1);

INSERT into user_group (group_id, user_id)
VALUES (1, 2);

INSERT into user_group (group_id, user_id)
VALUES (2, 3);

INSERT into user_group (group_id, user_id)
VALUES (2, 4);

INSERT into user_group (group_id, user_id)
VALUES (1, 5);

INSERT into user_group (group_id, user_id)
VALUES (3, 6);

INSERT into user_group (group_id, user_id)
VALUES (4, 7);

INSERT into user_group (group_id, user_id)
VALUES (4, 8);

INSERT into user_group (group_id, user_id)
VALUES (5, 9);

INSERT into user_group (group_id, user_id)
VALUES (3, 10);

INSERT into user_group (group_id, user_id)
VALUES (6, 11);

INSERT into user_group (group_id, user_id)
VALUES (6, 12);

INSERT into user_group (group_id, user_id)
VALUES (6, 13);

/**/
INSERT into user_group (group_id, user_id)
VALUES ( 6,2);

INSERT into user_group (group_id, user_id)
VALUES ( 3,3);

INSERT into user_group (group_id, user_id)
VALUES ( 2,6);

INSERT into user_group (group_id, user_id)
VALUES ( 1,7);

INSERT into user_group (group_id, user_id)
VALUES ( 1,8);

INSERT into user_group (group_id, user_id)
VALUES ( 2,10);

INSERT into user_group (group_id, user_id)
VALUES ( 5,11);

INSERT into user_group (group_id, user_id)
VALUES ( 4,12);

INSERT into user_group (group_id, user_id)
VALUES ( 3,13);

/**/
INSERT into user_group (group_id, user_id)
VALUES (4,2);

INSERT into user_group (group_id, user_id)
VALUES (2,8);

INSERT into user_group (group_id, user_id)
VALUES (1,10);

INSERT into user_group (group_id, user_id)
VALUES (4,11);

/*----------------------------------------------------------------------------------*/

INSERT into friend (friend_id, user_id)
VALUES (2,1);

INSERT into friend (friend_id, user_id)
VALUES (4,1);

INSERT into friend (friend_id, user_id)
VALUES (3,1);

INSERT into friend (friend_id, user_id)
VALUES (1,2);

INSERT into friend (friend_id, user_id)
VALUES (5,2);

INSERT into friend (friend_id, user_id)
VALUES (1,3);

INSERT into friend (friend_id, user_id)
VALUES (6,3);

INSERT into friend (friend_id, user_id)
VALUES (1,4);

INSERT into friend (friend_id, user_id)
VALUES (3, 5);

INSERT into friend (friend_id, user_id)
VALUES (10, 5);

INSERT into friend (friend_id, user_id)
VALUES (4, 6);

INSERT into friend (friend_id, user_id)
VALUES (10, 6);

INSERT into friend (friend_id, user_id)
VALUES (8, 7);

INSERT into friend (friend_id, user_id)
VALUES (7, 8);

INSERT into friend (friend_id, user_id)
VALUES (9, 8);

INSERT into friend (friend_id, user_id)
VALUES (8, 9);

INSERT into friend (friend_id, user_id)
VALUES (5, 10);

INSERT into friend (friend_id, user_id)
VALUES (6, 10);

INSERT into friend (friend_id, user_id)
VALUES (14, 11);

INSERT into friend (friend_id, user_id)
VALUES (13, 11);

INSERT into friend (friend_id, user_id)
VALUES (14, 12);

INSERT into friend (friend_id, user_id)
VALUES (11, 13);

INSERT into friend (friend_id, user_id)
VALUES (11, 14);

INSERT into friend (friend_id, user_id)
VALUES (12, 14);

/*-------------------------------------------------------------------------------*/

INSERT into location (latitude, longitude, name)
VALUES ("46.0569465", " 14.505751499999974","Ljubljana");

INSERT into location (latitude, longitude, name)
VALUES ("52.52000659999999", "13.404953999999975","Berlin");

INSERT into location (latitude, longitude, name)
VALUES ("45.8150108", "15.981919000000062","Zagreb");

INSERT into location (latitude, longitude, name)
VALUES ("35.7090259", "139.73199249999993","Tokyo");

INSERT into location (latitude, longitude, name)
VALUES ("51.5073509", "-0.12775829999998223","London");

/*---------------------------------------------------------------------------------*/

INSERT into event (location_id, group_id, event_name, time, description)
VALUES(1, 1, "Dogodek za biologe",  "14:15:00", "Opis dogodka tukaj");

INSERT into event (location_id, group_id, event_name, time, description)
VALUES(2, 6, "Dogodek za Strojnike",  "14:15:00", "Opis dogodka tukaj");

INSERT into event (location_id, group_id, event_name, time, description)
VALUES(3, 4, "Dogodek za Obramboslovce 1",  "14:15:00", "Opis dogodka tukaj");

INSERT into event (location_id, group_id, event_name, time, description)
VALUES(3, 4, "Dogodek za Obramboslovce 2", "14:15:00", "Opis dogodka tukaj");

INSERT into event (location_id, group_id, event_name, time, description)
VALUES(5, 3, "Dogodek za Matematike 1", "14:15:00", "Opis dogodka tukaj");

INSERT into event (location_id, group_id, event_name, time, description)
VALUES(5, 3, "Dogodek za Matematike 2", "14:15:00", "Opis dogodka tukaj");


INSERT into event (location_id, group_id, event_name, time, description)
VALUES(5, 3, "Dogodek za Matematike 3", "14:15:00", "Opis dogodka tukaj");

