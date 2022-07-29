-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: baza_ps2
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
-- Table structure for table `transakcija`
--

DROP TABLE IF EXISTS `transakcija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transakcija` (
  `IdT` int NOT NULL AUTO_INCREMENT,
  `IdR` int NOT NULL,
  `RedniBr` int NOT NULL,
  `Svrha` varchar(45) NOT NULL,
  `Tip` char(1) NOT NULL,
  `IdFil` int DEFAULT NULL,
  `Iznos` int NOT NULL,
  `DatumVreme` datetime NOT NULL,
  PRIMARY KEY (`IdT`),
  KEY `FK_IdR_Transakcija_idx` (`IdR`),
  CONSTRAINT `FK_IdR_Transakcija` FOREIGN KEY (`IdR`) REFERENCES `racun` (`IdR`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transakcija`
--

LOCK TABLES `transakcija` WRITE;
/*!40000 ALTER TABLE `transakcija` DISABLE KEYS */;
INSERT INTO `transakcija` VALUES (1,3,1,'Putovanje','I',1,200,'2022-02-14 21:00:20'),(2,2,1,'Putovanje','U',1,200,'2022-02-14 21:01:06'),(3,3,2,'Putovanje','I',1,900,'2022-02-14 21:02:17'),(4,2,2,'Putovanje','U',1,900,'2022-02-14 21:02:21'),(5,2,3,'Pomoc','I',2,300,'2022-02-14 21:15:13'),(6,3,3,'Pomoc','U',2,300,'2022-02-14 21:15:13'),(7,3,4,'Bonus','U',2,300,'2022-02-14 21:32:43'),(8,3,5,'Porez','I',2,100,'2022-02-14 21:33:35'),(9,2,4,'Prihod','U',1,200,'2022-02-14 23:37:24'),(10,2,5,'Dugovanje','I',6,100,'2022-02-18 16:53:41'),(11,2,6,'Zajam','I',6,100,'2022-02-18 16:57:16'),(12,3,6,'Zajam','U',6,100,'2022-02-18 16:57:16'),(13,2,7,'Porez','I',3,100,'2022-02-18 16:58:21'),(14,3,7,'Prihod','U',7,200,'2022-02-18 16:59:27'),(15,6,1,'Prihod','U',3,500,'2022-02-19 12:55:28'),(16,2,8,'Dugovanje','I',5,100,'2022-02-20 10:58:29'),(17,7,1,'Dugovanje','U',5,100,'2022-02-20 10:58:30'),(18,7,2,'Prihod','U',3,300,'2022-02-20 10:59:17'),(19,7,3,'Putovanje','U',3,200,'2022-02-20 11:01:10'),(20,3,8,'Skolarina','U',1,800,'2022-02-20 11:06:10'),(21,3,9,'Kredit','I',1,1600,'2022-02-20 11:09:38');
/*!40000 ALTER TABLE `transakcija` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-20 11:14:49
