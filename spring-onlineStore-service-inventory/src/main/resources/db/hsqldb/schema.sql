DROP TABLE payments IF EXISTS;
DROP TABLE types IF EXISTS;
DROP TABLE items IF EXISTS;

CREATE TABLE types (
  id   INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(80)
);
CREATE INDEX types_name ON types (name);

CREATE TABLE items (
  id         INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(255),
  price VARCHAR(255),
  description    VARCHAR(255)
);

CREATE TABLE payments (
  id         INTEGER IDENTITY PRIMARY KEY,
  name       VARCHAR(30),
  some_date DATE,
  type_id    INTEGER NOT NULL,
  owner_id   INTEGER NOT NULL
);
ALTER TABLE payments ADD CONSTRAINT fk_pets_owners FOREIGN KEY (owner_id) REFERENCES items (id);
ALTER TABLE payments ADD CONSTRAINT fk_pets_types FOREIGN KEY (type_id) REFERENCES types (id);
CREATE INDEX payments_name ON payments (name);
