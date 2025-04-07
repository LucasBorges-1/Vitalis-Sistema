-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: tccjava
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `clinica`
--

DROP TABLE IF EXISTS `clinica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clinica` (
  `id_clinica` int NOT NULL AUTO_INCREMENT,
  `cnpj` varchar(45) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `numero` varchar(45) NOT NULL,
  `Endereco` varchar(45) NOT NULL,
  `senha` varchar(255) NOT NULL,
  PRIMARY KEY (`id_clinica`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clinica`
--

LOCK TABLES `clinica` WRITE;
/*!40000 ALTER TABLE `clinica` DISABLE KEYS */;
INSERT INTO `clinica` VALUES (5,'11','clinica','11','11','$2a$10$MQ76DezkkoM882uRABF6PegA93dHRkx4U04nI8py9LWAcoiuxAC7O');
/*!40000 ALTER TABLE `clinica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `consulta`
--

DROP TABLE IF EXISTS `consulta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consulta` (
  `id_consulta` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `data_consulta` datetime NOT NULL,
  `caminho_documentos` varchar(45) NOT NULL,
  `id_medico` int NOT NULL,
  PRIMARY KEY (`id_consulta`),
  KEY `medico_consulta_fk` (`id_medico`),
  KEY `usuario_consulta_fk` (`id_usuario`),
  CONSTRAINT `medico_consulta_fk` FOREIGN KEY (`id_medico`) REFERENCES `medico` (`id_pessoa`),
  CONSTRAINT `usuario_consulta_fk` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consulta`
--

LOCK TABLES `consulta` WRITE;
/*!40000 ALTER TABLE `consulta` DISABLE KEYS */;
/*!40000 ALTER TABLE `consulta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `horarios`
--

DROP TABLE IF EXISTS `horarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `horarios` (
  `id_horario` int NOT NULL AUTO_INCREMENT,
  `id_medico` int NOT NULL,
  `dia` datetime NOT NULL,
  `inicio` time NOT NULL,
  `fim` time NOT NULL,
  PRIMARY KEY (`id_horario`),
  KEY `id_medico` (`id_medico`),
  CONSTRAINT `horarios_ibfk_1` FOREIGN KEY (`id_medico`) REFERENCES `medico` (`id_pessoa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `horarios`
--

LOCK TABLES `horarios` WRITE;
/*!40000 ALTER TABLE `horarios` DISABLE KEYS */;
/*!40000 ALTER TABLE `horarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medico`
--

DROP TABLE IF EXISTS `medico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medico` (
  `id_pessoa` int NOT NULL,
  `crm` varchar(45) NOT NULL,
  `id_clinica` int DEFAULT NULL,
  PRIMARY KEY (`id_pessoa`),
  CONSTRAINT `pessoa_medico_fk` FOREIGN KEY (`id_pessoa`) REFERENCES `pessoa` (`id_pessoa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medico`
--

LOCK TABLES `medico` WRITE;
/*!40000 ALTER TABLE `medico` DISABLE KEYS */;
INSERT INTO `medico` VALUES (5,'teset',1),(6,'teste',1),(7,'teste',5),(8,'123',5);
/*!40000 ALTER TABLE `medico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pessoa`
--

DROP TABLE IF EXISTS `pessoa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pessoa` (
  `id_pessoa` int NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `data_nascimento` date NOT NULL,
  `nome` varchar(45) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `cpf` varchar(45) NOT NULL,
  `type` char(7) DEFAULT NULL,
  PRIMARY KEY (`id_pessoa`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pessoa`
--

LOCK TABLES `pessoa` WRITE;
/*!40000 ALTER TABLE `pessoa` DISABLE KEYS */;
INSERT INTO `pessoa` VALUES (1,'11','2007-10-15','11','11','11',NULL),(2,'11','2007-10-15','11','11','11','Pessoa'),(3,'11','2007-10-11','11','11','11','Pessoa'),(5,'11','2007-10-15','teset','11','11','MEDICO'),(6,'1','2007-10-15','MedicoTeste','$2a$10$MHKgNSZjDVXeu9JvASIh6.GtD3t7XpgdmVitdoyWuDvoUsCktzWGm','1','MEDICO'),(7,'123','2007-10-15','Lucas','$2a$10$niaqpFfO3b624rVZtTFre.monBnFuG4Z1ma06mz6ywCM2XbNVo7s6','123','MEDICO'),(8,'123','2007-10-15','Lucas','$2a$10$YKbh8b4lmsnDWItHjM3zIedwmBSw.zohKR2FdSSngaxTl6Hy/SLc6','123','MEDICO');
/*!40000 ALTER TABLE `pessoa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tokens_recuperacao`
--

DROP TABLE IF EXISTS `tokens_recuperacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tokens_recuperacao` (
  `id_token` int NOT NULL AUTO_INCREMENT,
  `id_pessoa` int NOT NULL,
  `token` varchar(45) NOT NULL,
  `usado` tinyint(1) NOT NULL DEFAULT '0',
  `expiracao` datetime NOT NULL,
  PRIMARY KEY (`id_token`),
  KEY `pessoa_tokens_recuperacao_fk` (`id_pessoa`),
  CONSTRAINT `pessoa_tokens_recuperacao_fk` FOREIGN KEY (`id_pessoa`) REFERENCES `pessoa` (`id_pessoa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tokens_recuperacao`
--

LOCK TABLES `tokens_recuperacao` WRITE;
/*!40000 ALTER TABLE `tokens_recuperacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `tokens_recuperacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` int NOT NULL,
  `data_cadastro` date NOT NULL,
  `endereco` varchar(45) NOT NULL,
  PRIMARY KEY (`id_usuario`),
  CONSTRAINT `pessoa_usuario_fk` FOREIGN KEY (`id_usuario`) REFERENCES `pessoa` (`id_pessoa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-07 18:21:20
