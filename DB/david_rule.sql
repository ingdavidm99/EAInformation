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
-- Table structure for table `rule`
--

DROP TABLE IF EXISTS `rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rule` (
  `ID_RULE` int(11) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(45) NOT NULL,
  `STATUS` varchar(1) NOT NULL,
  `DESCRIPTION` varchar(200) NOT NULL,
  `DATE` date NOT NULL,
  `BASE_URL` text NOT NULL,
  `URL_REGEX` varchar(100) DEFAULT NULL,
  `PAGINATION_URL` bit(1) DEFAULT NULL,
  `PAGINATION_URL_REGEX` varchar(100) DEFAULT NULL,
  `LINK_REGEX` varchar(100) DEFAULT NULL,
  `PAGINATION_LINK` bit(1) DEFAULT NULL,
  `PAGINATION_LINK_REGEX` varchar(100) DEFAULT NULL,
  `SUBLINK_REGEX` varchar(100) DEFAULT NULL,
  `PAGINATION_SUBLINK` bit(1) DEFAULT NULL,
  `PAGINATION_SUBLINK_REGEX` varchar(100) DEFAULT NULL,
  `DESCRIPTION_REGEX` text,
  PRIMARY KEY (`ID_RULE`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rule`
--

LOCK TABLES `rule` WRITE;
/*!40000 ALTER TABLE `rule` DISABLE KEYS */;
INSERT INTO `rule` VALUES (1,'ABC','P','test','2018-12-12','https://www.gamestorrents.tv','[(href)#bs-example-navbar-collapse-1 .nav li a:not(.active)]','\0',NULL,'[(href)#home h6 a]','','[(!?)page/{0}]',NULL,'\0',NULL,'{(title)listencio.*?\\n.*?li.*?<strong>(.*?)<\\/strong><\\/li>}\n{(img)img.*?post_imagem.*?src=\"(.*?)\"}\n{(plataforma)plataforma.*?<strong>(.*?)<\\/strong>}'),(2,'AMAZON','P','Amazon Test','2018-12-13','https://www.amazon.com/dp/B07F1CQ3MY?aaxitk=dkUCZh0mrHe5AdmaXMB9fA&pd_rd_i=B07F1CQ3MY&pf_rd_m=ATVPDKIKX0DER&pf_rd_p=3ff6092e-8451-438b-8278-7e94064b4d42&pf_rd_s=desktop-sx-top-slot&pf_rd_t=301&pf_rd_i=reloj+fossil+hombre&hsa_cr_id=1396923220401&sb-ci-n=asinImage&sb-ci-v=https%3A%2F%2Fimages-na.ssl-images-amazon.com%2Fimages%2FI%2F61502ISplTL.jpg&sb-ci-a=B07F1CQ3MY',NULL,'\0',NULL,NULL,'\0',NULL,NULL,'\0',NULL,'{(plataforma)<span id=\"productTitle\".*?>([\\s\\S]*?)<\\/span>}');
/*!40000 ALTER TABLE `rule` ENABLE KEYS */;
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
