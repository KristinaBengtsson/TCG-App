DROP TABLE IF EXISTS wishlist_card, wishlist, user_masterset, masterset_card, masterset,
    vault_card, vault, card, card_set, users CASCADE;

CREATE TABLE users
(
    user_id  SERIAL PRIMARY KEY,
    username VARCHAR(50)  NOT NULL UNIQUE,
    email    VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE card_set
(
    set_id       SERIAL PRIMARY KEY,
    set_name     VARCHAR(100) NOT NULL,
    set_code     VARCHAR(10)  NOT NULL,
    release_date DATE
);

CREATE TABLE card
(
    card_id     SERIAL PRIMARY KEY,
    card_name   VARCHAR(100) NOT NULL,
    card_number VARCHAR(20)  NOT NULL,
    dex_number  INTEGER,
    artist      VARCHAR(100),
    price       INTEGER      NOT NULL DEFAULT 0,
    set_id      INTEGER      NOT NULL REFERENCES card_set (set_id)
);

CREATE TABLE vault
(
    vault_id  SERIAL PRIMARY KEY,
    user_id   INTEGER NOT NULL UNIQUE REFERENCES users (user_id) ON DELETE CASCADE,
    is_public BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE vault_card
(
    vault_id       INTEGER     NOT NULL REFERENCES vault (vault_id) ON DELETE CASCADE,
    card_id        INTEGER     NOT NULL REFERENCES card (card_id),
    card_condition VARCHAR(20) NOT NULL CHECK (card_condition IN ('Mint', 'Near Mint', 'Played', 'Damaged')),
    quantity       INTEGER     NOT NULL CHECK (quantity > 0),
    PRIMARY KEY (vault_id, card_id, card_condition)
);

CREATE TABLE masterset
(
  masterset_id      SERIAL PRIMARY KEY,
  masterset_name    VARCHAR(100) NOT NULL,
  is_official       BOOLEAN NOT NULL DEFAULT FALSE,
  created_by_user_id    INTEGER     REFERENCES users (user_id) ON DELETE CASCADE
);

CREATE TABLE masterset_card
(
    masterset_id INTEGER NOT NULL REFERENCES masterset (masterset_id) ON DELETE CASCADE,
    card_id      INTEGER NOT NULL REFERENCES card (card_id),
    PRIMARY KEY (masterset_id, card_id)
);

CREATE TABLE user_masterset
(
    user_id      INTEGER NOT NULL REFERENCES users (user_id) ON DELETE CASCADE,
    masterset_id INTEGER NOT NULL REFERENCES masterset (masterset_id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, masterset_id)
);

CREATE TABLE wishlist
(
    wishlist_id   SERIAL PRIMARY KEY,
    wishlist_name VARCHAR(100) NOT NULL,
    user_id       INTEGER      NOT NULL REFERENCES users (user_id) ON DELETE CASCADE
);

CREATE TABLE wishlist_card
(
    wishlist_id INTEGER NOT NULL REFERENCES wishlist (wishlist_id) ON DELETE CASCADE,
    card_id     INTEGER NOT NULL REFERENCES card (card_id),
    quantity    INTEGER NOT NULL DEFAULT 1 CHECK (quantity > 0),
    PRIMARY KEY (wishlist_id, card_id)
);