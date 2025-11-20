TRUNCATE TABLE verification_logs;

ALTER TABLE verification_logs
ADD COLUMN risk_score INT DEFAULT 0;