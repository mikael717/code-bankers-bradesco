-- DADOS PARA WHITELIST (ITENS CONFIÁVEIS)
INSERT INTO whitelist_items (item_type, item_value, source) VALUES
('URL', 'https://www.bradesco.com.br', 'DOMINIO_OFICIAL'),
('EMAIL', 'atendimento@bradesco.com.br', 'EMAIL_OFICIAL'),
('TELEFONE', '40028922', 'CENTRAL_ATENDIMENTO_OFICIAL');


-- DADOS PARA BLACKLIST (ITENS MALICIOSOS CONHECIDOS)
INSERT INTO blacklist_items (item_type, item_value, source) VALUES
('URL', 'http://bradesco-seguranca.xyz/update', 'DENUNCIA_CLIENTE_20241007'),
('URL', 'http://bradesco.info-update.com/login', 'MONITORAMENTO_PHISHING'),
('EMAIL', 'seguranca@bradesco.org', 'DENUNCIA_CLIENTE_20241006'),
('TELEFONE', '+5511987654321', 'DENUNCIA_CLIENTE_20241005');


-- DADO CONFLITANTE PARA TESTAR A PRECEDÊNCIA DA WHITELIST
-- Um telefone que já foi malicioso, mas agora é oficial (ex: número foi adquirido pelo banco).
INSERT INTO blacklist_items (item_type, item_value, source) VALUES
('TELEFONE', '40028922', 'LEGADO_DENUNCIA_2022');