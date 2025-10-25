ALTER TABLE verification_logs
  ADD COLUMN normalized_value VARCHAR(512) NULL,
  ADD COLUMN url_host VARCHAR(255) NULL;
CREATE INDEX idx_logs_host ON verification_logs (url_host);
