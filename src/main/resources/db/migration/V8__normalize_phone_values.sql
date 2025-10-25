-- Normaliza PHONE na blacklist e whitelist para apenas dígitos
UPDATE blacklist_items
SET item_value = REGEXP_REPLACE(item_value, '[^0-9]', '')
WHERE item_type = 'PHONE';

UPDATE whitelist_items
SET item_value = REGEXP_REPLACE(item_value, '[^0-9]', '')
WHERE item_type = 'PHONE';
