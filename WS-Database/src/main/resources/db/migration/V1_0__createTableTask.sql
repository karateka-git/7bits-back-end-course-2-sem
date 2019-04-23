CREATE TABLE task (
  id varchar PRIMARY KEY,
  text varchar NOT NULL,
  status varchar DEFAULT 'inbox',
  createdAT timestamp
);