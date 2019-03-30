ALTER table task add column createdAT timestamp;

INSERT INTO task (text, status, createdAt) VALUES
('First Task', 'inbox', '2001-09-28 01:00:00');