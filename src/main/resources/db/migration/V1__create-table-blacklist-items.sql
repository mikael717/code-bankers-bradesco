CREATE TABLE blacklist_items (
    id BIGINT NOT NULL AUTO_INCREMENT,
    item_type VARCHAR(100) NOT NULL,
    item_value VARCHAR(255) NOT NULL,
    source VARCHAR(255),
    PRIMARY KEY(id),
    UNIQUE(item_value)
);