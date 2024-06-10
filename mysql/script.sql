-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema documentdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `documentdb`;

-- -----------------------------------------------------
-- Schema documentdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `documentdb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `documentdb`;

-- -----------------------------------------------------
-- Table `documentdb`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `documentdb`.`users`
(
    `UserID`   INT                                  NOT NULL AUTO_INCREMENT,
    `Username` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
    `password` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
    `FullName` VARCHAR(255)                         NULL DEFAULT NULL,
    `Role`     VARCHAR(255)                         NULL DEFAULT NULL,
    `Email`    VARCHAR(255)                         NULL DEFAULT NULL,
    `Phone`    VARCHAR(255)                         NULL DEFAULT NULL,
    `Branch`   VARCHAR(255)                         NULL DEFAULT NULL,
    PRIMARY KEY (`UserID`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 4
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `documentdb`.`documents`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `documentdb`.`documents`
(
    `DocumentID`   INT                                  NOT NULL AUTO_INCREMENT,
    `Title`        VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
    `FilePath`     VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
    `DateAdd`      DATETIME                             NULL DEFAULT NULL,
    `DateUpdate`   DATETIME                             NULL DEFAULT NULL,
    `Category`     VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
    `SecrecyLevel` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
    `Urgency`      VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
    `FromSource`   VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
    `Status`       VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
    `Description`  VARCHAR(255)                         NULL DEFAULT NULL,
    `users_UserID` INT                                  NOT NULL,
    `branchSource` VARCHAR(255)                         NULL DEFAULT NULL,
    `emailSource`  VARCHAR(255)                         NULL DEFAULT NULL,
    `phoneSource`  VARCHAR(45)                          NULL DEFAULT NULL,
    PRIMARY KEY (`DocumentID`),
    INDEX `fk_documents_users1_idx` (`users_UserID` ASC) VISIBLE,
    CONSTRAINT `fk_documents_users1`
        FOREIGN KEY (`users_UserID`)
            REFERENCES `documentdb`.`users` (`UserID`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 46
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `documentdb`.`usercollections`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `documentdb`.`usercollections`
(
    `UserCollectionID`                          INT                                  NOT NULL AUTO_INCREMENT,
    `CollectName`                               VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
    `Description`                               VARCHAR(255)                         NULL DEFAULT NULL,
    `DocHasUserCollections_DocUserCollectionID` INT                                  NOT NULL,
    `users_UserID`                              INT                                  NOT NULL,
    PRIMARY KEY (`UserCollectionID`, `DocHasUserCollections_DocUserCollectionID`),
    INDEX `fk_UserCollections_DocHasUserCollections1_idx` (`DocHasUserCollections_DocUserCollectionID` ASC) VISIBLE,
    INDEX `fk_usercollections_users1_idx` (`users_UserID` ASC) VISIBLE,
    CONSTRAINT `fk_usercollections_users1`
        FOREIGN KEY (`users_UserID`)
            REFERENCES `documentdb`.`users` (`UserID`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 4
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `documentdb`.`dochasusercollections`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `documentdb`.`dochasusercollections`
(
    `DocUserCollectionID`                                       INT NOT NULL AUTO_INCREMENT,
    `usercollections_UserCollectionID`                          INT NOT NULL,
    `usercollections_DocHasUserCollections_DocUserCollectionID` INT NOT NULL,
    `documents_DocumentID`                                      INT NOT NULL,
    PRIMARY KEY (`DocUserCollectionID`),
    INDEX `fk_dochasusercollections_usercollections1_idx` (`usercollections_UserCollectionID` ASC,
                                                           `usercollections_DocHasUserCollections_DocUserCollectionID`
                                                           ASC) VISIBLE,
    INDEX `fk_dochasusercollections_documents1_idx` (`documents_DocumentID` ASC) VISIBLE,
    CONSTRAINT `fk_dochasusercollections_documents1`
        FOREIGN KEY (`documents_DocumentID`)
            REFERENCES `documentdb`.`documents` (`DocumentID`),
    CONSTRAINT `fk_dochasusercollections_usercollections1`
        FOREIGN KEY (`usercollections_UserCollectionID`, `usercollections_DocHasUserCollections_DocUserCollectionID`)
            REFERENCES `documentdb`.`usercollections` (`UserCollectionID`, `DocHasUserCollections_DocUserCollectionID`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 4
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `documentdb`.`fingerprints`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `documentdb`.`fingerprints`
(
    `FingerprintID` INT                                  NOT NULL AUTO_INCREMENT,
    `Image_Path`    VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
    `Date_Create`   DATETIME                             NULL DEFAULT NULL,
    `users_UserID`  INT                                  NOT NULL,
    PRIMARY KEY (`FingerprintID`),
    INDEX `fk_fingerprints_users1_idx` (`users_UserID` ASC) VISIBLE,
    CONSTRAINT `fk_fingerprints_users1`
        FOREIGN KEY (`users_UserID`)
            REFERENCES `documentdb`.`users` (`UserID`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 7
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `documentdb`.`notification`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `documentdb`.`notification`
(
    `NotificationID` INT                                  NOT NULL AUTO_INCREMENT,
    `Message`        VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
    `DateSent`       DATETIME                             NULL DEFAULT NULL,
    `SecrencyLevel`  VARCHAR(255)                         NULL DEFAULT NULL,
    `Urgency`        VARCHAR(255)                         NULL DEFAULT NULL,
    `users_UserID`   INT                                  NOT NULL,
    PRIMARY KEY (`NotificationID`),
    INDEX `fk_notification_users1_idx` (`users_UserID` ASC) VISIBLE,
    CONSTRAINT `fk_notification_users1`
        FOREIGN KEY (`users_UserID`)
            REFERENCES `documentdb`.`users` (`UserID`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 7
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `documentdb`.`signatures`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `documentdb`.`signatures`
(
    `SignatureID`  INT                                  NOT NULL AUTO_INCREMENT,
    `ImagePath`    VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
    `DateCreate`   DATETIME                             NULL DEFAULT NULL,
    `users_UserID` INT                                  NOT NULL,
    PRIMARY KEY (`SignatureID`),
    INDEX `fk_signatures_users1_idx` (`users_UserID` ASC) VISIBLE,
    CONSTRAINT `fk_signatures_users1`
        FOREIGN KEY (`users_UserID`)
            REFERENCES `documentdb`.`users` (`UserID`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 7
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `documentdb`.`stampandsignings`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `documentdb`.`stampandsignings`
(
    `StampingID`           INT          NOT NULL AUTO_INCREMENT,
    `OperationStatus`      VARCHAR(255) NULL DEFAULT NULL,
    `Timestamp`            DATETIME     NULL DEFAULT NULL,
    `documents_DocumentID` INT          NOT NULL,
    `users_UserID`         INT          NOT NULL,
    PRIMARY KEY (`StampingID`),
    INDEX `fk_stampandsignings_documents1_idx` (`documents_DocumentID` ASC) VISIBLE,
    INDEX `fk_stampandsignings_users1_idx` (`users_UserID` ASC) VISIBLE,
    CONSTRAINT `fk_stampandsignings_documents1`
        FOREIGN KEY (`documents_DocumentID`)
            REFERENCES `documentdb`.`documents` (`DocumentID`),
    CONSTRAINT `fk_stampandsignings_users1`
        FOREIGN KEY (`users_UserID`)
            REFERENCES `documentdb`.`users` (`UserID`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 7
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `documentdb`.`taskdocusers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `documentdb`.`taskdocusers`
(
    `verifydoc`             INT                                  NOT NULL AUTO_INCREMENT,
    `TaskTitle`             VARCHAR(255)                         NULL DEFAULT NULL,
    `Description`           VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
    `DateCreate`            DATETIME                             NULL DEFAULT NULL,
    `DateUpdate`            DATETIME                             NULL DEFAULT NULL,
    `documents_DocumentID1` INT                                  NOT NULL,
    `users_UserID`          INT                                  NOT NULL,
    PRIMARY KEY (`verifydoc`),
    INDEX `fk_taskdocusers_documents1_idx` (`documents_DocumentID1` ASC) VISIBLE,
    INDEX `fk_taskdocusers_users1_idx` (`users_UserID` ASC) VISIBLE,
    CONSTRAINT `fk_taskdocusers_documents1`
        FOREIGN KEY (`documents_DocumentID1`)
            REFERENCES `documentdb`.`documents` (`DocumentID`),
    CONSTRAINT `fk_taskdocusers_users1`
        FOREIGN KEY (`users_UserID`)
            REFERENCES `documentdb`.`users` (`UserID`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 7
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `documentdb`.`userdocuments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `documentdb`.`userdocuments`
(
    `UserDocumentID`        INT         NOT NULL AUTO_INCREMENT,
    `AccessLevel`           VARCHAR(45) NULL DEFAULT NULL,
    `documents_DocumentID1` INT         NOT NULL,
    `users_UserID`          INT         NOT NULL,
    PRIMARY KEY (`UserDocumentID`),
    INDEX `fk_userdocuments_documents_idx` (`documents_DocumentID1` ASC) VISIBLE,
    INDEX `fk_userdocuments_users1_idx` (`users_UserID` ASC) VISIBLE,
    CONSTRAINT `fk_userdocuments_documents`
        FOREIGN KEY (`documents_DocumentID1`)
            REFERENCES `documentdb`.`documents` (`DocumentID`),
    CONSTRAINT `fk_userdocuments_users1`
        FOREIGN KEY (`users_UserID`)
            REFERENCES `documentdb`.`users` (`UserID`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 36
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `documentdb`.`verifydocs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `documentdb`.`verifydocs`
(
    `VerifyID`             INT          NOT NULL AUTO_INCREMENT,
    `isPass`               TINYINT      NULL DEFAULT NULL,
    `Comment`              VARCHAR(255) NULL DEFAULT NULL,
    `documents_DocumentID` INT          NOT NULL,
    `users_UserID`         INT          NOT NULL,
    PRIMARY KEY (`VerifyID`),
    INDEX `fk_verifydocs_documents1_idx` (`documents_DocumentID` ASC) VISIBLE,
    INDEX `fk_verifydocs_users1_idx` (`users_UserID` ASC) VISIBLE,
    CONSTRAINT `fk_verifydocs_documents1`
        FOREIGN KEY (`documents_DocumentID`)
            REFERENCES `documentdb`.`documents` (`DocumentID`),
    CONSTRAINT `fk_verifydocs_users1`
        FOREIGN KEY (`users_UserID`)
            REFERENCES `documentdb`.`users` (`UserID`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 7
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;


-- เพิ่มข้อมูลลงในตาราง users
INSERT INTO `documentdb`.`users` (`UserID`, `Username`, `password`, `FullName`, `Role`, `Email`, `Phone`, `Branch`)
VALUES (1, 'นภา', 'password1', 'นภา ฟ้าสวย', 'Admin', 'user1@example.com', '1234567890', 'งานเอกสาร'),
       (2, 'ใบฝ้าย', 'password2', 'ใบฝ้าย นุ่มมิ่ม', 'User', 'user2@example.com', '9876543210', 'ฝ่ายขาย'),
       (3, 'กันยา', 'password3', 'กันยา นาปี', 'Manager', 'user3@example.com', '5556667777', 'ฝ่ายผลิต');
#
# -- เพิ่มข้อมูลลงในตาราง usercollections
# INSERT INTO `documentdb`.`usercollections` (`UserCollectionID`, `CollectName`, `Description`,
#                                             `DocHasUserCollections_DocUserCollectionID`, `users_UserID`)
# VALUES (1, 'เกี่ยวกับฉัน', 'อะไรก็ได้ที่เกี่ยวกัยฉันจะอยู่ในนี้', 1, 1),
#        (2, 'งานประจำปี', 'เก็บไว้เฉพาะของปีนี้', 2, 2),
#        (3, 'ยอดค่าใช้จ่ายสูง', 'เอกสารที่มีมูลค่าเกินกว่า 10 ล้านบาท', 3, 3);
#
# -- เพิ่มข้อมูลลงในตาราง documents
# INSERT INTO `documentdb`.`documents` (`DocumentID`, `Title`, `FilePath`, `DateAdd`, `DateUpdate`, `Category`,
#                                       `SecrecyLevel`, `Urgency`, `FromSource`, `Status`, `Description`, `users_UserID`)
# VALUES (1, 'เอกสารใบสี่งซื้อหนังสือพิมพ์', 'INT204-Week-07-Spring-Data-Jpa.pdf', '2023-01-01 08:00:00',
#         '2023-01-02 10:30:00',
#         'เอกสารภายนอก', 'ลับ', 'ปกติ', 'ฝ่ายจัดซื้อ บริษัทน้ำใจงาม', 'ผ่าน',
#         'ใบการจัดซื้อหนังสือประจำปี 2023 มูลค่า 12,000,000 บาท', 1),
#        (2, 'เอกสารนัดประชุมประจำสัปดาห์ของปี 2022', 'Spring Framework-IoC-2.pdf', '2023-01-03 09:15:00',
#         '2023-01-04 11:45:00', 'เอกสารภายใน', 'ลับมาก', 'ด่วน', 'ฝ่ายบุคคล ภายใน', 'ไม่ผ่าน',
#         'แผนนัดประชุมประจำสัปดาห์ของปี 2022 โดยในแต่จะมีหัวข้อภายในอย่างชัดเจน', 2),
#        (3, 'เอกสารโครงสร้างโรงอาหารลูกค้าบริษัทชอบยิ้ม', '해린-고양이.gif', '2023-01-05 10:30:00',
#         '2023-01-06 12:00:00', 'เอกสารภายใน', 'ลับที่สุด', 'ปกปิด', 'ฝ่ายงานวิศวกรรม', 'ผ่าน',
#         'รายงานโครงสร้างโรงอาหารลูกค้าบริษัทชอบยิ้ม ฉบับแก้ไขที่ 12 โดยได้รับการยินยอมจากลูกค้าเรียบร้อย', 3);
#
# -- เพิ่มข้อมูลลงในตาราง dochasusercollections
# INSERT INTO `documentdb`.`dochasusercollections` (`DocUserCollectionID`, `usercollections_UserCollectionID`,
#                                                   `usercollections_DocHasUserCollections_DocUserCollectionID`,
#                                                   `documents_DocumentID`)
# VALUES (1, 1, 1, 1),
#        (2, 2, 2, 2),
#        (3, 3, 3, 3);
#
# -- เพิ่มข้อมูลลงในตาราง fingerprints
# INSERT INTO `documentdb`.`fingerprints` (`Image_Path`, `Date_Create`, `users_UserID`)
# VALUES ('/path/to/fingerprint1.jpg', '2023-01-10 14:00:00', 1),
#        ('/path/to/fingerprint2.jpg', '2023-01-11 15:30:00', 2),
#        ('/path/to/fingerprint3.jpg', '2023-01-12 16:45:00', 3);
#
# -- เพิ่มข้อมูลลงในตาราง notification
# INSERT INTO `documentdb`.`notification` (`Message`, `DateSent`, `SecrencyLevel`, `Urgency`, `users_UserID`)
# VALUES ('ยืนยันตัวตนก่อนการใช้งาน', '2023-01-15 08:30:00', 'ปกติ', 'ปกติ', 1),
#        ('มีเอกสารใหม่ 3 รายการที่ยังไม่อ่าน', '2023-01-16 09:45:00', 'ปกติ', 'ปกติ', 2),
#        ('เตือนความจำ: วันพรุ่งประชุมกับลูกค้าที่บางนา', '2023-01-17 10:00:00', 'ลับ', 'ด่วน', 3);
#
# -- เพิ่มข้อมูลลงในตาราง signatures
# INSERT INTO documentdb.signatures (ImagePath, DateCreate, users_UserID)
# VALUES ('/path/to/signature1.png', '2023-01-20 14:30:00', 1),
#        ('/path/to/signature2.png', '2023-01-21 15:45:00', 2),
#        ('/path/to/signature3.png', '2023-01-22 16:30:00', 3);
#
# -- เพิ่มข้อมูลลงในตาราง stampandsignings
# INSERT INTO documentdb.stampandsignings (OperationStatus, Timestamp, documents_DocumentID, users_UserID)
# VALUES ('ยืนยัน', '2023-01-25 11:00:00', 1, 1),
#        ('ไม่ยินยัน', '2023-01-26 12:30:00', 2, 2),
#        ('ประทับตรา', '2023-01-27 14:45:00', 3, 3);
#
# -- เพิ่มข้อมูลลงในตาราง taskdocusers
# INSERT INTO documentdb.taskdocusers (TaskTitle, Description, DateCreate, DateUpdate, documents_DocumentID1,
#                                      users_UserID)
# VALUES ('สิ่งที่ต้องทำ 1', 'อธิบายสิ่งที่ต้องทำ 1', '2023-02-01 09:00:00', '2023-02-02 10:30:00', 1, 1),
#        ('สิ่งที่ต้องทำ 2', 'อธิบายสิ่งที่ต้องทำ  2', '2023-02-03 10:15:00', '2023-02-04 11:45:00', 2, 2),
#        ('สิ่งที่ต้องทำ 3', 'อธิบายสิ่งที่ต้องทำ  3', '2023-02-05 11:30:00', '2023-02-06 12:00:00', 3, 3);
#
# -- เพิ่มข้อมูลลงในตาราง userdocuments
# INSERT INTO documentdb.userdocuments (AccessLevel, documents_DocumentID1, users_UserID)
# VALUES ('อ่าน/เขียน', 1, 1),
#        ('อ่าน/เขียน', 1, 2),
#        ('อ่่าน', 1, 3),
#        ('อ่าน/เขียน', 2, 1),
#        ('อ่าน/เขียน', 2, 2),
#        ('อ่าน', 3, 3);
#
# -- เพิ่มข้อมูลลงในตาราง verifydocs
# INSERT INTO documentdb.verifydocs (isPass, Comment, documents_DocumentID, users_UserID)
# VALUES (1, 'การยืนยันผ่านแล้ว', 1, 1),
#        (0, 'การยืนยันไม่ผ่าน', 2, 2),
#        (1, 'การยืนยันผ่านแล้ว', 3, 3);

SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;


