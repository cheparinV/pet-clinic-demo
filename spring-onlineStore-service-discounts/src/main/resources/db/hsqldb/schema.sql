DROP TABLE discount IF EXISTS;

CREATE TABLE discount (
  id         INTEGER IDENTITY PRIMARY KEY,
  item_id    INTEGER NOT NULL,
  discount   INTEGER NOT NULL
);
