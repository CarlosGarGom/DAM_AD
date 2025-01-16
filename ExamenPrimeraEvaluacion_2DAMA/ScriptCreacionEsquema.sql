
DROP SCHEMA IF EXISTS ictusSaCyL;
CREATE SCHEMA ictusSaCyL;
USE ictusSaCyL;

CREATE TABLE provincias (
  `id_provincia` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100),
  PRIMARY KEY (`id_provincia`)
) ;

CREATE TABLE hospitales (
  `id_hospital` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100),
  `provincia` int, 
  CONSTRAINT ClaveAjenaProvinciaHospital FOREIGN KEY (provincia) REFERENCES provincias (id_provincia),
  PRIMARY KEY (`id_hospital`)
) ;

CREATE TABLE ictus (
	`id_ictus` int NOT NULL AUTO_INCREMENT,
    `fecha_ingreso` date,
    `hospital` int,
    `edad` int,
    `sexo` varchar(10),
    CONSTRAINT ClaveAjenaHospital FOREIGN KEY (hospital) REFERENCES hospitales (id_hospital),
    PRIMARY KEY (`id_ictus`)
);
