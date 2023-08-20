-- -----------------------------------------------------
-- Schema electroscootdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `electroscootdb` ;

-- -----------------------------------------------------
-- Schema electroscootdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `electroscootdb` DEFAULT CHARACTER SET utf8 ;
USE `electroscootdb` ;

-- -----------------------------------------------------
-- Table `electroscootdb`.`RentalPlace`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `electroscootdb`.`RentalPlace` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `rating` TINYINT UNSIGNED NOT NULL DEFAULT 0,
  `city` VARCHAR(45) NOT NULL,
  `street` VARCHAR(45) NOT NULL,
  `house` TINYINT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `electroscootdb`.`ScooterModel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `electroscootdb`.`ScooterModel` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price_per_time` FLOAT NOT NULL,
  `start_price` FLOAT NOT NULL,
  `discount` TINYINT NOT NULL DEFAULT 0,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `electroscootdb`.`Scooter`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `electroscootdb`.`Scooter` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `rental_place_name` VARCHAR(45) NULL,
  `model` VARCHAR(45) NOT NULL,
  `state` ENUM("RENTED", "OK", "BROKEN") NOT NULL DEFAULT 'OK',
  PRIMARY KEY (`id`),
  INDEX `RentalPlaceId_idx` (`rental_place_name` ASC) VISIBLE,
  INDEX `ModelName_idx` (`model` ASC) VISIBLE,
  CONSTRAINT `RentalPlaceId`
    FOREIGN KEY (`rental_place_name`)
    REFERENCES `electroscootdb`.`RentalPlace` (`name`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `ModelName`
    FOREIGN KEY (`model`)
    REFERENCES `electroscootdb`.`ScooterModel` (`name`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `electroscootdb`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `electroscootdb`.`User` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(20) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `phone` VARCHAR(11) NOT NULL,
  `registered_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `firstname` VARCHAR(45) NULL,
  `secondname` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `subscription_till` TIMESTAMP NULL,
  `money` FLOAT NOT NULL DEFAULT 0,
  `scooter_id` INT UNSIGNED NULL,
  `status` ENUM("OK", "BANNED") NOT NULL DEFAULT 'OK',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `phone_UNIQUE` (`phone` ASC) VISIBLE,
  INDEX `fk_User_Scooter1_idx` (`scooter_id` ASC) VISIBLE,
  CONSTRAINT `fk_User_Scooter1`
    FOREIGN KEY (`scooter_id`)
    REFERENCES `electroscootdb`.`Scooter` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `electroscootdb`.`Role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `electroscootdb`.`Role` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `electroscootdb`.`User2Role` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_id` INT UNSIGNED NOT NULL,
  `user_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `RoleID_idx` (`role_id` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `UserID_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `RoleName`
    FOREIGN KEY (`role_id`)
    REFERENCES `electroscootdb`.`Role` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `UserUsername`
    FOREIGN KEY (`user_id`)
    REFERENCES `electroscootdb`.`User` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `electroscootdb`.`ScooterRental`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `electroscootdb`.`ScooterRental` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `scooter_id` INT UNSIGNED NOT NULL,
  `scooter_taken_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `scooter_passed_at` TIMESTAMP NULL,
  `username` VARCHAR(20) NOT NULL,
  `init_rental_place_name` VARCHAR(45) NULL,
  `final_rental_place_name` VARCHAR(45) NULL,
  `init_price_per_time` FLOAT UNSIGNED NOT NULL,
  `init_discount` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `ScooterId_idx` (`scooter_id` ASC) VISIBLE,
  INDEX `Username_idx` (`username` ASC) VISIBLE,
  INDEX `RentalPlaceAt_idx` (`init_rental_place_name` ASC) VISIBLE,
  INDEX `RentalPlaceFinal_idx` (`final_rental_place_name` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `ScooterRented`
    FOREIGN KEY (`scooter_id`)
    REFERENCES `electroscootdb`.`Scooter` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `UserRented`
    FOREIGN KEY (`username`)
    REFERENCES `electroscootdb`.`User` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `RentalPlaceInit`
    FOREIGN KEY (`init_rental_place_name`)
    REFERENCES `electroscootdb`.`RentalPlace` (`name`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `RentalPlaceFinal`
    FOREIGN KEY (`final_rental_place_name`)
    REFERENCES `electroscootdb`.`RentalPlace` (`name`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;