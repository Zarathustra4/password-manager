CREATE TABLE IF NOT EXISTS "user"(
    "id" serial PRIMARY KEY,
    "username" varchar(100) NOT NULL,
    "password" varchar(255) NOT NULL,
    "encryptionKey" varchar(255) NOT NULL,
    "role" smallint NOT NULL
);

CREATE TABLE IF NOT EXISTS "service"(
    "id" SERIAL PRIMARY KEY,
    "title" varchar(255),
    "domain" varchar(100) unique,
    "description" varchar(255),
    "logoPath" varchar(255)
);

CREATE TABLE IF NOT EXISTS "password"(
    "id" SERIAL PRIMARY KEY,
    "password" varchar(255),
    "service_id" int8,
    "user_id" int8,

    FOREIGN KEY("service_id") REFERENCES "service"(id),
    FOREIGN KEY("user_id") REFERENCES "user"(id)
);

