CREATE TABLE Author
(
    id         BIGINT      NOT NULL,
    firstname  VARCHAR(50) NOT NULL,
    middlename VARCHAR(50) NOT NULL,
    lastname   VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE book
(
    id        BIGINT      NOT NULL IDENTITY,
    title     VARCHAR(50) NOT NULL,
    publisher VARCHAR(50) NOT NULL,
    city      VARCHAR(50) NOT NULL,
    year      VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_author_id
        FOREIGN KEY (author_id)
        REFERENCES Author
    CONSTRAINT fk_genre_id
        FOREIGN KEY (genre_id)
        REFERENCES Genre
);

CREATE TABLE genre
(
    id    BIGINT      NOT NULL IDENTITY,
    title VARCHAR(50) NOT NULL
);
