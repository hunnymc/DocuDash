-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema DocumentDB
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `DocumentDB` ;

-- -----------------------------------------------------
-- Schema DocumentDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `DocumentDB` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `DocumentDB` ;

-- -----------------------------------------------------
-- Table `DocumentDB`.`Documents`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `DocumentDB`.`Documents` ;

CREATE TABLE IF NOT EXISTS `DocumentDB`.`Documents` (
  `DocumentID` INT NOT NULL,
  `Title` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `FilePath` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `DateAdd` DATETIME NULL DEFAULT NULL,
  `DateUpdate` DATETIME NULL DEFAULT NULL,
  `Category` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `SecrecyLevel` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `Urgency` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `FromSource` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `Status` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `UploadBy` INT NULL DEFAULT NULL,
  PRIMARY KEY (`DocumentID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


USE documentdb;

INSERT INTO Documents (DocumentID, Title, FilePath, DateAdd, DateUpdate, Category, SecrecyLevel, Urgency, FromSource, Status, UploadBy)
VALUES 
(1, 'แผนธุรกิจสำหรับสตาร์ทอัพ', '/path/to/file1.pdf', '2022-01-01T00:00:00', '2022-01-01T00:00:00', 'ธุรกิจ', 'ปกติ', 'ด่วน', 'สตาร์ทอัพ', 'ผ่าน', 1),
(2, 'เอกสารการสมัครสมาชิก', '/path/to/file2.pdf', '2022-01-01T00:00:00', '2022-01-01T00:00:00', 'การสมัคร', 'สูง', 'ปกติ', 'สตาร์ทอัพ', 'ผ่าน', 1),
(3, 'ข้อเสนอโครงการ', '/path/to/file3.pdf', '2022-01-01T00:00:00', '2022-01-01T00:00:00', 'โครงการ', 'ปกติ', 'ด่วน', 'สตาร์ทอัพ', 'ไม่ผ่าน', 1),
(4, 'เอกสารที่ 4', '/path/to/file4.pdf', '2022-01-01T00:00:00', '2022-01-01T00:00:00', 'ธุรกิจ', 'ปกติ', 'ด่วน', 'สตาร์ทอัพ', 'ผ่าน', 1),
(5, 'เอกสารที่ 5', '/path/to/file5.pdf', '2022-01-01T00:00:00', '2022-01-01T00:00:00', 'การสมัคร', 'สูง', 'ปกติ', 'สตาร์ทอัพ', 'ผ่าน', 1);

