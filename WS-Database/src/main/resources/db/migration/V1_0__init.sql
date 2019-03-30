CREATE TABLE task (
  id bigserial PRIMARY KEY,
  text varchar NOT NULL,
  status varchar DEFAULT 'inbox'
);

INSERT INTO task (text, status) VALUES
('First Task', 'inbox');