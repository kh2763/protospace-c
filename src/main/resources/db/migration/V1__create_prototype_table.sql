CREATE TABLE prototype (
    id SERIAL NOT NULL,
    name VARCHAR(256),
    catchcopy VARCHAR(256),
    concept VARCHAR(512),
    image VARCHAR(256),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

