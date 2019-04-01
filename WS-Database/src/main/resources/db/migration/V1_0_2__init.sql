delete from task;
ALTER table task add column updateAT timestamp;

INSERT INTO task (text, status, createdAT) VALUES
('First Task', 'inbox', '2001-09-28 01:00:00');