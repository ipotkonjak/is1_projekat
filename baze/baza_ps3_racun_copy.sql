-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: baza_ps3
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `racun_copy`
--

DROP TABLE IF EXISTS `racun_copy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `racun_copy` (
  `IdR` int NOT NULL,
  `IdK` int NOT NULL,
  `IdMes` int NOT NULL,
  `Stanje` int NOT NULL,
  `DozvMinus` int NOT NULL,
  `DatumVreme` datetime NOT NULL,
  `Status` char(1) NOT NULL,
  `BrTransakcija` int NOT NULL,
  PRIMARY KEY (`IdR`),
  KEY `FK_IdK_Racun_idx` (`IdK`),
  KEY `FK_IdMes_Racun_idx` (`IdMes`),
  CONSTRAINT `FK_IdK_Racun` FOREIGN KEY (`IdK`) REFERENCES `komitent_copy` (`IdK`) ON UPDATE CASCADE,
  CONSTRAINT `FK_IdMes_Racun` FOREIGN KEY (`IdMes`) REFERENCES `mesto_copy` (`IdMes`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `racun_copy`
--

LOCK TABLES `racun_copy` WRITE;
/*!40000 ALTER TABLE `racun_copy` DISABLE KEYS */;
INSERT INTO `racun_copy` VALUES (1,3,1,0,1500,'2022-02-14 14:54:02','Z',0),(2,3,2,600,2000,'2022-02-14 15:07:15','A',8),(3,3,4,-1100,1000,'2022-02-14 20:40:22','B',9),(4,4,2,0,500,'2022-02-18 16:29:22','Z',0),(5,6,2,0,800,'2022-02-19 12:52:34','Z',0),(6,6,3,500,700,'2022-02-19 12:53:02','A',1),(7,7,1,600,1500,'2022-02-20 10:57:18','A',3);
/*!40000 ALTER TABLE `racun_copy` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-20 11:14:48
