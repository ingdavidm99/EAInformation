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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `ID_USER` int(11) NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(20) NOT NULL,
  `PASSWORD` varchar(100) NOT NULL,
  `ENABLED` bit(1) NOT NULL,
  `ID_USER_DETAIL` int(11) NOT NULL,
  `ID_ROLE` int(11) NOT NULL,
  PRIMARY KEY (`ID_USER`),
  KEY `user_fk1_idx` (`ID_ROLE`),
  KEY `user_fk2_idx` (`ID_USER_DETAIL`),
  CONSTRAINT `user_fk1` FOREIGN KEY (`ID_ROLE`) REFERENCES `role` (`ID_ROLE`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_fk2` FOREIGN KEY (`ID_USER_DETAIL`) REFERENCES `user_detail` (`ID_USER_DETAIL`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'dmarsiglia','$2a$10$ekXFYUXFlflUV7VF/6SN8ugB9R9e28p57GH2Xbk.FQkDXc8FKKCkG','',10,1),(5,'dmarsigl','$2a$10$DXPMMLwSCnyCd4/lMor.n.mUK9CLxoSg/phGMbIxidwqyf8e9P8PS','',14,2),(6,'dm88','$2a$10$a9e6WwWMAtiwigAUvPt26uIaK8noqcopSmX6cxWzc526RBsEbUEBa','',15,2),(7,'dm88','$2a$10$2Xmp/p8n8MpQNoo9kX0z5.9FYBMyVVO3ZjXtMt5Q.pm3r0eFapNfC','',16,2),(8,'qwe','$2a$10$1jk6mUGThc9LkEcMHOucBeBuUxnPgCb8Aj9danUVpBzysKAbdt506','\0',17,2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-02-17 18:36:49
