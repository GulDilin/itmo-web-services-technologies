CREATE TABLE city
(
    id                     INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_at             TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at             TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    name                   VARCHAR(255) UNIQUE                      NOT NULL,
    area                   INTEGER                                  NOT NULL,
    population             INTEGER                                  NOT NULL,
    meters_above_sea_level FLOAT,
    population_density     INTEGER,
    car_code               INTEGER,
    CONSTRAINT pk_city PRIMARY KEY (id)
);
