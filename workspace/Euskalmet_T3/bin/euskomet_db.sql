-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 13-01-2021 a las 20:28:05
-- Versión del servidor: 10.4.14-MariaDB
-- Versión de PHP: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+01:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `euskomet_db`
--
CREATE DATABASE IF NOT EXISTS `euskomet_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `euskomet_db`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `calidad_aire`
--

DROP TABLE IF EXISTS `calidad_aire`;
CREATE TABLE `calidad_aire` (
  `fecha_hora` datetime NOT NULL,
  `calidad` varchar(40) DEFAULT NULL,
  `cod_estacion` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `espacios_naturales`
--

DROP TABLE IF EXISTS `espacios_naturales`;
CREATE TABLE `espacios_naturales` (
  `cod_enatural` int(20) NOT NULL,
  `nombre` varchar(40) DEFAULT NULL,
  `descripcion` varchar(800) DEFAULT NULL,
  `tipo` varchar(100) DEFAULT NULL,
  `latitud` double DEFAULT NULL,
  `longitud` double DEFAULT NULL,
  `foto` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `espacios_naturales`
--

INSERT INTO `espacios_naturales` (`cod_enatural`, `nombre`, `descripcion`, `tipo`, `latitud`, `longitud`, `foto`) VALUES
(0, NULL, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estaciones`
--

DROP TABLE IF EXISTS `estaciones`;
CREATE TABLE `estaciones` (
  `cod_estacion` int(20) NOT NULL,
  `nombre` varchar(40) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `coord_x` double DEFAULT NULL,
  `coord_y` double DEFAULT NULL,
  `latitud` double DEFAULT NULL,
  `longitud` double DEFAULT NULL,
  `foto` varchar(1000) DEFAULT NULL,
  `cod_muni` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `fav_espacios`
--

DROP TABLE IF EXISTS `fav_espacios`;
CREATE TABLE `fav_espacios` (
  `cod_enatural` int(20) NOT NULL,
  `cod_usuario` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `fav_municipio`
--

DROP TABLE IF EXISTS `fav_municipio`;
CREATE TABLE `fav_municipio` (
  `cod_muni` int(20) NOT NULL,
  `cod_usuario` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hash`
--

DROP TABLE IF EXISTS `hash`;
CREATE TABLE `hash` (
  `cod_hash` int(20) NOT NULL,
  `nombre` varchar(40) DEFAULT NULL,
  `hash` varchar(1000) DEFAULT NULL,
  `url` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `municipio`
--

DROP TABLE IF EXISTS `municipio`;
CREATE TABLE `municipio` (
  `cod_muni` int(20) NOT NULL,
  `nombre` varchar(40) DEFAULT NULL,
  `descripcion` varchar(1000) DEFAULT NULL,
  `cod_prov` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `muni_espacios`
--

DROP TABLE IF EXISTS `muni_espacios`;
CREATE TABLE `muni_espacios` (
  `cod_muni` int(20) NOT NULL,
  `cod_enatural` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `provincia`
--

DROP TABLE IF EXISTS `provincia`;
CREATE TABLE `provincia` (
  `cod_prov` int(20) NOT NULL,
  `nombre` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
  `cod_usuario` int(11) NOT NULL,
  `nombre` varchar(40) DEFAULT NULL,
  `contraseña` varchar(40) DEFAULT NULL,
  `p_clave` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `calidad_aire`
--
ALTER TABLE `calidad_aire`
  ADD PRIMARY KEY (`fecha_hora`,`cod_estacion`),
  ADD KEY `FK_ESTACION_CALIDAD` (`cod_estacion`) USING BTREE;

--
-- Indices de la tabla `espacios_naturales`
--
ALTER TABLE `espacios_naturales`
  ADD PRIMARY KEY (`cod_enatural`);

--
-- Indices de la tabla `estaciones`
--
ALTER TABLE `estaciones`
  ADD PRIMARY KEY (`cod_estacion`),
  ADD KEY `FK_MUNI_ESTACION` (`cod_muni`);

--
-- Indices de la tabla `fav_espacios`
--
ALTER TABLE `fav_espacios`
  ADD PRIMARY KEY (`cod_enatural`,`cod_usuario`),
  ADD KEY `FK_ENATURALES_FAV` (`cod_enatural`),
  ADD KEY `FK_USUARIO_FAVENATURALES` (`cod_usuario`);

--
-- Indices de la tabla `fav_municipio`
--
ALTER TABLE `fav_municipio`
  ADD PRIMARY KEY (`cod_muni`,`cod_usuario`),
  ADD KEY `FK_MUNI_FAV` (`cod_muni`),
  ADD KEY `FK_USUARIO_FAVMUNI` (`cod_usuario`);

--
-- Indices de la tabla `hash`
--
ALTER TABLE `hash`
  ADD PRIMARY KEY (`cod_hash`);

--
-- Indices de la tabla `municipio`
--
ALTER TABLE `municipio`
  ADD PRIMARY KEY (`cod_muni`),
  ADD KEY `FK_PROV` (`cod_prov`);

--
-- Indices de la tabla `muni_espacios`
--
ALTER TABLE `muni_espacios`
  ADD PRIMARY KEY (`cod_muni`,`cod_enatural`),
  ADD KEY `FK_MUNI_MUNIESPACIOS` (`cod_muni`),
  ADD KEY `FK_ENATURALES_MUNIESPACIOS` (`cod_enatural`);

--
-- Indices de la tabla `provincia`
--
ALTER TABLE `provincia`
  ADD PRIMARY KEY (`cod_prov`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`cod_usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `cod_usuario` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `calidad_aire`
--
ALTER TABLE `calidad_aire`
  ADD CONSTRAINT `FK_ESTACION_CALIDAD` FOREIGN KEY (`cod_estacion`) REFERENCES `estaciones` (`cod_estacion`);

--
-- Filtros para la tabla `estaciones`
--
ALTER TABLE `estaciones`
  ADD CONSTRAINT `FK_MUNI_ESTACION` FOREIGN KEY (`cod_muni`) REFERENCES `municipio` (`cod_muni`);

--
-- Filtros para la tabla `fav_espacios`
--
ALTER TABLE `fav_espacios`
  ADD CONSTRAINT `FK_ENATURALES_FAV` FOREIGN KEY (`cod_enatural`) REFERENCES `espacios_naturales` (`cod_enatural`),
  ADD CONSTRAINT `FK_USUARIO_FAVENATURALES` FOREIGN KEY (`cod_usuario`) REFERENCES `usuario` (`cod_usuario`);

--
-- Filtros para la tabla `fav_municipio`
--
ALTER TABLE `fav_municipio`
  ADD CONSTRAINT `FK_MUNI_FAV` FOREIGN KEY (`cod_muni`) REFERENCES `municipio` (`cod_muni`),
  ADD CONSTRAINT `FK_USUARIO_FAVMUNI` FOREIGN KEY (`cod_usuario`) REFERENCES `usuario` (`cod_usuario`);

--
-- Filtros para la tabla `municipio`
--
ALTER TABLE `municipio`
  ADD CONSTRAINT `FK_PROV` FOREIGN KEY (`cod_prov`) REFERENCES `provincia` (`cod_prov`);

--
-- Filtros para la tabla `muni_espacios`
--
ALTER TABLE `muni_espacios`
  ADD CONSTRAINT `FK_ENATURALES_MUNIESPACIOS` FOREIGN KEY (`cod_enatural`) REFERENCES `espacios_naturales` (`cod_enatural`),
  ADD CONSTRAINT `FK_MUNI_MUNIESPACIOS` FOREIGN KEY (`cod_muni`) REFERENCES `municipio` (`cod_muni`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
