-- 1. Crear la base de datos
CREATE DATABASE CajeroAutomatico_db;
USE CajeroAutomatico_db;

-- 2. Tabla de Usuarios (Paquete modelo.Usuario)
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    numero_cuenta VARCHAR(20) NOT NULL UNIQUE,
    pin_hash VARCHAR(255) NOT NULL,
    pin_salt VARCHAR(255) NOT NULL,
    bloqueado BOOLEAN DEFAULT FALSE
);

-- 3. Tabla de Cuentas (Paquete modelo.Cuenta)
CREATE TABLE cuentas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    saldo DECIMAL(15, 2) DEFAULT 0.00,
    CONSTRAINT fk_usuario FOREIGN KEY (id_usuario) 
        REFERENCES usuarios(id) ON DELETE CASCADE
);

-- 4. Tabla de Transacciones (Paquete modelo.Operacion y sus hijos)
CREATE TABLE transacciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_cuenta INT NOT NULL,
    tipo ENUM('RETIRO', 'DEPOSITO', 'TRANSFERENCIA') NOT NULL,
    monto DECIMAL(15, 2) NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_cuenta_destino INT NULL, -- Solo para transferencias
    CONSTRAINT fk_cuenta_origen FOREIGN KEY (id_cuenta) 
        REFERENCES cuentas(id),
    CONSTRAINT fk_cuenta_destino FOREIGN KEY (id_cuenta_destino) 
        REFERENCES cuentas(id)
);


-- valores de prueba 
INSERT INTO usuarios (nombre, correo, numero_cuenta, pin_hash, pin_salt, bloqueado, intentos_fallidos) 
VALUES ('Luis Moscoso', 
'20252065@itla.edu.do', 
'20252065', 
'sj5RuW4Qp5dWx44gXwrQwzAOucfDg51Rr1it3AVkbGI=', 
'nIdHlsjfrTVVwRuK0mkj4A==', 0, 0);

-- Luego le creamos su cuenta con saldo inicial
INSERT INTO cuentas (id_usuario, saldo) VALUES (11, 50000.00);

select * from usuarios;
select * from cuentas;
select * from transacciones;

-- actualizar 
UPDATE usuarios
SET nombre = 'Surky Baez'
WHERE id = 6;