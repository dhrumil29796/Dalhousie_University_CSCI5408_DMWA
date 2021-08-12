-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: prob2
-- ------------------------------------------------------
-- Server version	8.0.25

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
-- Table structure for table `animals`
--

DROP TABLE IF EXISTS `animals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `animals` (
  `animal_project_reference` text,
  `datacenter_reference` text,
  `animal_reference_id` text,
  `animal_guid` text,
  `vernacularname` text,
  `scientificname` text,
  `aphiaid` text,
  `tsn` text,
  `animal_origin` text,
  `stock` text,
  `length` text,
  `length_type` text,
  `weight` text,
  `life_stage` text,
  `age` text,
  `sex` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `datacenter_attributes`
--

DROP TABLE IF EXISTS `datacenter_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `datacenter_attributes` (
  `ï»¿datacenter_reference` text,
  `datacenter_name` text,
  `datacenter_citation` text,
  `datacenter_abstract` text,
  `datacenter_pi` text,
  `datacenter_pi_organization` text,
  `datacenter_pi_contact` text,
  `datacenter_infourl` text,
  `datacenter_keywords` text,
  `datacenter_keywords_vocabulary` text,
  `datacenter_doi` text,
  `datacenter_license` text,
  `datacenter_geospatial_lon_min` text,
  `datacenter_geospatial_lon_max` text,
  `datacenter_geospatial_lat_min` text,
  `datacenter_geospatial_lat_max` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `detections`
--

DROP TABLE IF EXISTS `detections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detections` (
  `detection_project_reference` text,
  `datacenter_reference` text,
  `detection_id` text,
  `detection_guid` text,
  `time_UTC` text,
  `latitude_degrees_north` text,
  `longitude_degrees_east` text,
  `tracker_reference` text,
  `detection_reference_id` text,
  `detection_reference_type` text,
  `transmitter_codespace` text,
  `transmitter_id` text,
  `detection_transmittername` text,
  `detection_serial_number` text,
  `sensor_data` text,
  `sensor_data_units` text,
  `deployment_id` text,
  `detection_quality` text,
  `position_data_source` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manmade_platform`
--

DROP TABLE IF EXISTS `manmade_platform`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manmade_platform` (
  `platform_project_reference` text,
  `datacenter_reference` text,
  `platform_reference_id` text,
  `platform_guid` text,
  `platform_type` text,
  `platform_depth` text,
  `platform_name` text,
  `latitude_degrees_north` text,
  `longitude_degrees_east` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project_attributes`
--

DROP TABLE IF EXISTS `project_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_attributes` (
  `project_reference` text,
  `datacenter_reference` text,
  `project_name` text,
  `project_abstract` text,
  `project_citation` text,
  `project_pi` text,
  `project_pi_organization` text,
  `project_pi_contact` text,
  `project_infourl` text,
  `project_keywords` text,
  `project_keywords_vocabulary` text,
  `project_license` text,
  `project_datum` text,
  `project_geospatial_lon_min_degrees_east` text,
  `project_geospatial_lon_max_degrees_east` text,
  `project_geospatial_lat_min_degrees_north` text,
  `project_geospatial_lat_max_degrees_north` text,
  `geospatial_vertical_min` text,
  `geospatial_vertical_max` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `receivers`
--

DROP TABLE IF EXISTS `receivers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receivers` (
  `deployment_project_reference` text,
  `datacenter_reference` text,
  `deployment_id` text,
  `deployment_guid` text,
  `receiver_manufacturer` text,
  `receiver_model` text,
  `receiver_serial_number` text,
  `latitude_degrees_north` text,
  `longitude_degrees_east` text,
  `time_UTC` text,
  `recovery_datetime_UTC` text,
  `array_name` text,
  `receiver_reference_type` text,
  `receiver_reference_id` text,
  `bottom_depth` text,
  `depth` text,
  `deployment_comments` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recover_offload_details`
--

DROP TABLE IF EXISTS `recover_offload_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recover_offload_details` (
  `recovery_project_reference` text,
  `datacenter_reference` text,
  `recovery_id` text,
  `deployment_id` text,
  `recovery_guid` text,
  `recovery_latitude` text,
  `recovery_longitude` text,
  `recovery_datetime_utc` text,
  `recovery_outcome` text,
  `data_offloaded` text,
  `offload_datetime_utc` text,
  `log_filenames` text,
  `recovery_comments` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tag_releases`
--

DROP TABLE IF EXISTS `tag_releases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tag_releases` (
  `release_project_reference` text,
  `datacenter_reference` text,
  `tag_device_id` text,
  `release_guid` text,
  `release_reference_id` text,
  `release_reference_type` text,
  `latitude_degrees_north` text,
  `longitude_degrees_east` text,
  `time_UTC` text,
  `expected_enddate_UTC` text,
  `manufacturer` text,
  `tag_model` text,
  `tag_serial_number` text,
  `tag_coding_system` text,
  `transmitted_id` text,
  `transmittername` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-01 23:38:28
