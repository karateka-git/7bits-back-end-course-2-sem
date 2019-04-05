CREATE TABLE task (
  id varchar PRIMARY KEY,
  text varchar NOT NULL,
  status varchar DEFAULT 'inbox',
  createdAT timestamp,
  updateAT timestamp
);

INSERT INTO task (id, text, status, createdAT) VALUES
('deea44c7-a180-4898-9527-58db0ed34683','First Task', 'inbox', '2001-09-28 01:00:00');