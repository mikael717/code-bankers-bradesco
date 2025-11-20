CREATE TABLE reports (
    id BIGINT NOT NULL AUTO_INCREMENT,
    item_type VARCHAR(50) NOT NULL,
    item_value VARCHAR(255) NOT NULL,
    reason VARCHAR(255),
    reporter_ip VARCHAR(100),
    reported_at DATETIME NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX idx_reports_item ON reports (item_type, item_value);