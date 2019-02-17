-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: david
-- ------------------------------------------------------
-- Server version	5.7.14-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `user_detail`
--

DROP TABLE IF EXISTS `user_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_detail` (
  `ID_USER_DETAIL` int(11) NOT NULL AUTO_INCREMENT,
  `FULL_NAME` varchar(50) NOT NULL,
  `BIRTH` date NOT NULL,
  `EMAIL` varchar(50) NOT NULL,
  PRIMARY KEY (`ID_USER_DETAIL`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_detail`
--

LOCK TABLES `user_detail` WRITE;
/*!40000 ALTER TABLE `user_detail` DISABLE KEYS */;
INSERT INTO `user_detail` VALUES (10,'David Eduardo Marsiglia','2018-03-26','dmarsiglia@gmail.com'),(14,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(15,'David Eduardo','1990-12-13','ingdavidm99@gmail.com'),(16,'David Eduardo','1990-12-13','ingdavidm99@gmail.com'),(17,'David','2018-09-12','ingdavidm99@gm.com'),(18,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(19,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(20,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(21,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(22,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(23,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(24,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(25,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(26,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(27,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(28,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(29,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(30,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(31,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(32,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(33,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(34,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(35,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(36,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(37,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(38,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(39,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(40,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(41,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(42,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(43,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(44,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(45,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(46,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(47,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(48,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(49,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(50,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(51,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(52,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(53,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(54,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(55,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(56,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(57,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(58,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(59,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(60,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(61,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(62,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(63,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(64,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(65,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(66,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(67,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(68,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(69,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(70,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(71,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(72,'Eduardo Marsiglia','1988-12-13','ingdavidm99a@gmail.com'),(73,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(74,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(75,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(76,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(77,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com'),(78,'Eduardo Marsiglia','1988-12-13','ingdavidm99@gmail.com');
/*!40000 ALTER TABLE `user_detail` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-02-17 18:36:47
