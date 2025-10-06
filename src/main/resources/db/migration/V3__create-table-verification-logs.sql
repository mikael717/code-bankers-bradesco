CREATE TABLE verification_logs(
    id BIGINT NOT NULL AUTO_INCREMENT,
    item_type VARCHAR(100) NOT NULL,
    item_value VARCHAR(255) NOT NULL,
    verdict VARCHAR(100) NOT NULL,
    reasons TEXT,
    verification_logs DATETIME NOT NULL,
    PRIMARY KEY (id)
);