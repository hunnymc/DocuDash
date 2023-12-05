-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema documentdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `documentdb` ;

-- -----------------------------------------------------
-- Schema documentdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `documentdb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `documentdb` ;

-- -----------------------------------------------------
-- Table `documentdb`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `documentdb`.`users` ;

CREATE TABLE IF NOT EXISTS `documentdb`.`users` (
  `UserID` INT NOT NULL AUTO_INCREMENT,
  `Username` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `password` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `FullName` VARCHAR(255) NULL DEFAULT NULL,
  `Role` VARCHAR(255) NULL DEFAULT NULL,
  `Email` VARCHAR(255) NULL DEFAULT NULL,
  `Phone` VARCHAR(255) NULL DEFAULT NULL,
  `Branch` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`UserID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `documentdb`.`usercollections`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `documentdb`.`usercollections` ;

CREATE TABLE IF NOT EXISTS `documentdb`.`usercollections` (
  `UserCollectionID` INT NOT NULL AUTO_INCREMENT,
  `CollectName` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `Description` VARCHAR(255) NULL DEFAULT NULL,
  `DocHasUserCollections_DocUserCollectionID` INT NOT NULL,
  `users_UserID` INT NOT NULL,
  PRIMARY KEY (`UserCollectionID`, `DocHasUserCollections_DocUserCollectionID`),
  INDEX `fk_UserCollections_DocHasUserCollections1_idx` (`DocHasUserCollections_DocUserCollectionID` ASC) VISIBLE,
  INDEX `fk_usercollections_users1_idx` (`users_UserID` ASC) VISIBLE,
  CONSTRAINT `fk_usercollections_users1`
    FOREIGN KEY (`users_UserID`)
    REFERENCES `documentdb`.`users` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `documentdb`.`documents`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `documentdb`.`documents` ;

CREATE TABLE IF NOT EXISTS `documentdb`.`documents` (
  `DocumentID` INT NOT NULL AUTO_INCREMENT,
  `Title` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `FilePath` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `DateAdd` DATETIME NULL DEFAULT NULL,
  `DateUpdate` DATETIME NULL DEFAULT NULL,
  `Category` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `SecrecyLevel` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `Urgency` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `FromSource` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `Status` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `Description` VARCHAR(255) NULL DEFAULT NULL,
  `users_UserID` INT NOT NULL,
  PRIMARY KEY (`DocumentID`),
  INDEX `fk_documents_users1_idx` (`users_UserID` ASC) VISIBLE,
  CONSTRAINT `fk_documents_users1`
    FOREIGN KEY (`users_UserID`)
    REFERENCES `documentdb`.`users` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `documentdb`.`dochasusercollections`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `documentdb`.`dochasusercollections` ;

CREATE TABLE IF NOT EXISTS `documentdb`.`dochasusercollections` (
  `DocUserCollectionID` INT NOT NULL AUTO_INCREMENT,
  `usercollections_UserCollectionID` INT NOT NULL,
  `usercollections_DocHasUserCollections_DocUserCollectionID` INT NOT NULL,
  `documents_DocumentID` INT NOT NULL,
  PRIMARY KEY (`DocUserCollectionID`),
  INDEX `fk_dochasusercollections_usercollections1_idx` (`usercollections_UserCollectionID` ASC, `usercollections_DocHasUserCollections_DocUserCollectionID` ASC) VISIBLE,
  INDEX `fk_dochasusercollections_documents1_idx` (`documents_DocumentID` ASC) VISIBLE,
  CONSTRAINT `fk_dochasusercollections_usercollections1`
    FOREIGN KEY (`usercollections_UserCollectionID` , `usercollections_DocHasUserCollections_DocUserCollectionID`)
    REFERENCES `documentdb`.`usercollections` (`UserCollectionID` , `DocHasUserCollections_DocUserCollectionID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_dochasusercollections_documents1`
    FOREIGN KEY (`documents_DocumentID`)
    REFERENCES `documentdb`.`documents` (`DocumentID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `documentdb`.`fingerprints`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `documentdb`.`fingerprints` ;

CREATE TABLE IF NOT EXISTS `documentdb`.`fingerprints` (
  `FingerprintID` INT NOT NULL AUTO_INCREMENT,
  `Image_Path` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `Date_Create` DATETIME NULL DEFAULT NULL,
  `users_UserID` INT NOT NULL,
  PRIMARY KEY (`FingerprintID`),
  INDEX `fk_fingerprints_users1_idx` (`users_UserID` ASC) VISIBLE,
  CONSTRAINT `fk_fingerprints_users1`
    FOREIGN KEY (`users_UserID`)
    REFERENCES `documentdb`.`users` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `documentdb`.`notification`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `documentdb`.`notification` ;

CREATE TABLE IF NOT EXISTS `documentdb`.`notification` (
  `NotificationID` INT NOT NULL AUTO_INCREMENT,
  `Message` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `DateSent` DATETIME NULL DEFAULT NULL,
  `SecrencyLevel` VARCHAR(255) NULL DEFAULT NULL,
  `Urgency` VARCHAR(255) NULL DEFAULT NULL,
  `users_UserID` INT NOT NULL,
  PRIMARY KEY (`NotificationID`),
  INDEX `fk_notification_users1_idx` (`users_UserID` ASC) VISIBLE,
  CONSTRAINT `fk_notification_users1`
    FOREIGN KEY (`users_UserID`)
    REFERENCES `documentdb`.`users` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `documentdb`.`signatures`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `documentdb`.`signatures` ;

CREATE TABLE IF NOT EXISTS `documentdb`.`signatures` (
  `SignatureID` INT NOT NULL AUTO_INCREMENT,
  `ImagePath` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `DateCreate` DATETIME NULL DEFAULT NULL,
  `users_UserID` INT NOT NULL,
  PRIMARY KEY (`SignatureID`),
  INDEX `fk_signatures_users1_idx` (`users_UserID` ASC) VISIBLE,
  CONSTRAINT `fk_signatures_users1`
    FOREIGN KEY (`users_UserID`)
    REFERENCES `documentdb`.`users` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `documentdb`.`stampandsignings`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `documentdb`.`stampandsignings` ;

CREATE TABLE IF NOT EXISTS `documentdb`.`stampandsignings` (
  `StampingID` INT NOT NULL AUTO_INCREMENT,
  `OperationStatus` VARCHAR(255) NULL DEFAULT NULL,
  `Timestamp` DATETIME NULL DEFAULT NULL,
  `documents_DocumentID` INT NOT NULL,
  `users_UserID` INT NOT NULL,
  PRIMARY KEY (`StampingID`),
  INDEX `fk_stampandsignings_documents1_idx` (`documents_DocumentID` ASC) VISIBLE,
  INDEX `fk_stampandsignings_users1_idx` (`users_UserID` ASC) VISIBLE,
  CONSTRAINT `fk_stampandsignings_documents1`
    FOREIGN KEY (`documents_DocumentID`)
    REFERENCES `documentdb`.`documents` (`DocumentID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_stampandsignings_users1`
    FOREIGN KEY (`users_UserID`)
    REFERENCES `documentdb`.`users` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `documentdb`.`taskdocusers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `documentdb`.`taskdocusers` ;

CREATE TABLE IF NOT EXISTS `documentdb`.`taskdocusers` (
  `verifydoc` INT NOT NULL AUTO_INCREMENT,
  `TaskTitle` VARCHAR(255) NULL DEFAULT NULL,
  `Description` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `DateCreate` DATETIME NULL DEFAULT NULL,
  `DateUpdate` DATETIME NULL DEFAULT NULL,
  `documents_DocumentID1` INT NOT NULL,
  `users_UserID` INT NOT NULL,
  PRIMARY KEY (`verifydoc`),
  INDEX `fk_taskdocusers_documents1_idx` (`documents_DocumentID1` ASC) VISIBLE,
  INDEX `fk_taskdocusers_users1_idx` (`users_UserID` ASC) VISIBLE,
  CONSTRAINT `fk_taskdocusers_documents1`
    FOREIGN KEY (`documents_DocumentID1`)
    REFERENCES `documentdb`.`documents` (`DocumentID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_taskdocusers_users1`
    FOREIGN KEY (`users_UserID`)
    REFERENCES `documentdb`.`users` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `documentdb`.`userdocuments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `documentdb`.`userdocuments` ;

CREATE TABLE IF NOT EXISTS `documentdb`.`userdocuments` (
  `UserDocumentID` INT NOT NULL AUTO_INCREMENT,
  `AccessLevel` VARCHAR(45) NULL DEFAULT NULL,
  `documents_DocumentID1` INT NOT NULL,
  `users_UserID` INT NOT NULL,
  PRIMARY KEY (`UserDocumentID`),
  INDEX `fk_userdocuments_documents_idx` (`documents_DocumentID1` ASC) VISIBLE,
  INDEX `fk_userdocuments_users1_idx` (`users_UserID` ASC) VISIBLE,
  CONSTRAINT `fk_userdocuments_documents`
    FOREIGN KEY (`documents_DocumentID1`)
    REFERENCES `documentdb`.`documents` (`DocumentID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_userdocuments_users1`
    FOREIGN KEY (`users_UserID`)
    REFERENCES `documentdb`.`users` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `documentdb`.`verifydocs`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `documentdb`.`verifydocs` ;

CREATE TABLE IF NOT EXISTS `documentdb`.`verifydocs` (
  `VerifyID` INT NOT NULL AUTO_INCREMENT,
  `isPass` TINYINT NULL DEFAULT NULL,
  `Comment` VARCHAR(255) NULL DEFAULT NULL,
  `documents_DocumentID` INT NOT NULL,
  `users_UserID` INT NOT NULL,
  PRIMARY KEY (`VerifyID`),
  INDEX `fk_verifydocs_documents1_idx` (`documents_DocumentID` ASC) VISIBLE,
  INDEX `fk_verifydocs_users1_idx` (`users_UserID` ASC) VISIBLE,
  CONSTRAINT `fk_verifydocs_documents1`
    FOREIGN KEY (`documents_DocumentID`)
    REFERENCES `documentdb`.`documents` (`DocumentID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_verifydocs_users1`
    FOREIGN KEY (`users_UserID`)
    REFERENCES `documentdb`.`users` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;