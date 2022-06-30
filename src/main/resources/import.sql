/* Llenado de rol */
INSERT INTO rol (id, nombre, descripcion) VALUES (1, 'ADMINISTRADOR', 'ADMINISTRADOR');
INSERT INTO rol (id, nombre, descripcion) VALUES (2, 'ASISTENTE', 'ASISTENTE');

/* Llenado de usuarios */
INSERT INTO usuario (id, username, password, rol) VALUES (1, 'admin', '$2a$10$clUzxo6.SOG5xC0CXoWIKOFUbEBF1UXX23G0Z3W4Uu4FwC3gscdv2',1);
INSERT INTO usuario (id, username, password, rol) VALUES (2, 'asesor', '$2a$10$clUzxo6.SOG5xC0CXoWIKOFUbEBF1UXX23G0Z3W4Uu4FwC3gscdv2',2);