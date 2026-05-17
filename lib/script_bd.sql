-- Crear base de datos
CREATE DATABASE IF NOT EXISTS uasd_carreras;
USE uasd_carreras;

-- Crear tabla de escuelas/facultades
CREATE TABLE escuelas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT
);

-- Crear tabla de carreras
CREATE TABLE carreras (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(150) NOT NULL,
    escuela_id INT,
    duracion_semestres INT,
    creditos_totales INT,
    descripcion TEXT,
    FOREIGN KEY (escuela_id) REFERENCES escuelas(id)
);

-- Insertar datos de escuelas
INSERT INTO escuelas (nombre, descripcion) VALUES
('Ingeniería y Arquitectura', 'Escuela de Ingeniería y Arquitectura - UASD'),
('Ciencias Económicas y Sociales', 'Escuela de Ciencias Económicas y Sociales - UASD'),
('Artes', 'Escuela de Artes - UASD'),
('Ciencias de la Salud', 'Escuela de Ciencias de la Salud - UASD'),
('Humanidades', 'Escuela de Humanidades - UASD');

-- Insertar datos de carreras
INSERT INTO carreras (nombre, escuela_id, duracion_semestres, creditos_totales, descripcion) VALUES
-- Ingeniería (id=1)
('Ingeniería Civil', 1, 10, 180, 'Formación en diseño, construcción y mantenimiento de infraestructuras'),
('Ingeniería en Sistemas', 1, 10, 175, 'Desarrollo de software, redes y tecnologías de información'),
('Ingeniería Eléctrica', 1, 10, 178, 'Generación, transmisión y distribución de energía eléctrica'),
('Ingeniería Industrial', 1, 10, 176, 'Optimización de procesos productivos y logística'),
('Arquitectura', 1, 12, 200, 'Diseño y planificación de espacios arquitectónicos'),

-- Negocios (id=2)
('Administración de Empresas', 2, 8, 150, 'Gestión empresarial y liderazgo organizacional'),
('Contabilidad', 2, 8, 148, 'Registro y análisis de transacciones financieras'),
('Marketing', 2, 8, 145, 'Estrategias de mercado y comunicación comercial'),
('Economía', 2, 8, 152, 'Análisis de sistemas económicos y políticas públicas'),
('Administración Turística', 2, 8, 143, 'Gestión de servicios turísticos y hoteleros'),

-- Artes (id=3)
('Música', 3, 10, 160, 'Formación musical instrumental y vocal'),
('Artes Visuales', 3, 8, 140, 'Pintura, escultura y expresiones artísticas'),
('Teatro', 3, 8, 138, 'Actuación, dirección y producción teatral'),
('Danza', 3, 8, 135, 'Formación en técnicas de danza clásica y contemporánea'),
('Cine y Audiovisuales', 3, 9, 150, 'Producción cinematográfica y medios audiovisuales'),

-- Salud (id=4)
('Medicina', 4, 14, 250, 'Formación médica integral para atención de salud'),
('Enfermería', 4, 10, 165, 'Cuidado y atención de pacientes'),
('Odontología', 4, 12, 200, 'Salud bucal y procedimientos dentales'),
('Farmacia', 4, 10, 170, 'Estudio de medicamentos y su aplicación terapéutica'),
('Bioanálisis', 4, 8, 148, 'Análisis clínicos y diagnósticos de laboratorio'),

-- Humanidades (id=5)
('Derecho', 5, 10, 180, 'Estudio de las leyes y su aplicación'),
('Psicología', 5, 8, 150, 'Estudio del comportamiento humano y procesos mentales'),
('Sociología', 5, 8, 145, 'Análisis de estructuras y fenómenos sociales'),
('Filosofía', 5, 8, 140, 'Reflexión sobre problemas fundamentales del ser y el conocimiento'),
('Historia', 5, 8, 142, 'Estudio de procesos históricos y su interpretación');

-- Consulta de verificación
SELECT e.nombre as escuela, c.nombre as carrera, c.duracion_semestres, c.creditos_totales
FROM carreras c
JOIN escuelas e ON c.escuela_id = e.id
ORDER BY e.nombre, c.nombre;