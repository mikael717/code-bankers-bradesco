ALTER TABLE verification_logs CHANGE COLUMN verification_logs verification_date DATETIME NOT NULL;
CREATE INDEX idx_blacklist_type_value ON blacklist_items (item_type, item_value);
CREATE INDEX idx_whitelist_type_value ON whitelist_items (item_type, item_value);
