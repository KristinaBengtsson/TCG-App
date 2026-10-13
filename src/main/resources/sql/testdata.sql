-- Testdata til MasterCollect. Kør efter create_tables.sql.
-- OBS: passwords står i klartekst her. Det er kun til test (se FORKLARING.md).

INSERT INTO users (username, email, password)
VALUES ('kristina', 'kristina@test.dk', 'password1'),
       ('sofus', 'sofus@test.dk', 'password2'),
       ('test', 'test@test.dk', 'test1234');

INSERT INTO vault (user_id)
VALUES (1), (2), (3);

INSERT INTO card_set (set_name, set_code, release_date)
VALUES ('Base Set', 'BS', '1999-01-09'),
       ('Obsidian Flames', 'OBF', '2023-08-11'),
       ('151', 'MEW', '2023-09-22');

INSERT INTO card (card_name, card_number, dex_number, artist, price, set_id)
VALUES ('Charizard', '4/102', 6, 'Mitsuhiro Arita', 2500, 1),       -- 1
       ('Blastoise', '2/102', 9, 'Ken Sugimori', 900, 1),            -- 2
       ('Venusaur', '15/102', 3, 'Mitsuhiro Arita', 700, 1),         -- 3
       ('Pikachu', '58/102', 25, 'Mitsuhiro Arita', 120, 1),         -- 4
       ('Charizard ex', '125/197', 6, '5ban Graphics', 350, 2),      -- 5
       ('Cleffa', '202/197', 173, 'Mina Nakai', 122, 2),             -- 6
       ('Pidgeot ex', '164/197', 18, 'Eske Yoshinob', 40, 2),        -- 7
       ('Mew ex', '151/165', 151, '5ban Graphics', 150, 3),          -- 8
       ('Bulbasaur', '166/165', 1, 'Yuu Nishida', 90, 3),            -- 9
       ('Mewtwo', '150/165', 150, 'Kouki Saitou', 20, 3);            -- 10

-- Kristina (vault 1) ejer nogle kort
INSERT INTO vault_card (vault_id, card_id, card_condition, quantity)
VALUES (1, 1, 'Near Mint', 1),
       (1, 4, 'Played', 2),
       (1, 5, 'Mint', 1),
       (1, 6, 'Near Mint', 2),
       (1, 7, 'Near Mint', 1),
       (1, 8, 'Mint', 1);

-- Officielle master-sets (et pr. kortsæt) + ét som Kristina selv har lavet
INSERT INTO masterset (masterset_name, is_official, created_by_user_id)
VALUES ('Base Set', TRUE, NULL),
       ('Obsidian Flames', TRUE, NULL),
       ('151', TRUE, NULL),
       ('Mine Charizards', FALSE, 1);

INSERT INTO masterset_card (masterset_id, card_id)
VALUES (1, 1), (1, 2), (1, 3), (1, 4),
       (2, 5), (2, 6), (2, 7),
       (3, 8), (3, 9), (3, 10),
       (4, 1), (4, 5);

-- Kristina følger alle fire master-sets
INSERT INTO user_masterset (user_id, masterset_id)
VALUES (1, 1), (1, 2), (1, 3), (1, 4);

INSERT INTO wishlist (wishlist_name, user_id)
VALUES ('Mangler til Base Set', 1);

INSERT INTO wishlist_card (wishlist_id, card_id, quantity)
VALUES (1, 2, 1),
       (1, 3, 1),
       (1, 9, 2);
