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
-- Table `electroscootdb`.`rentalplace`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `electroscootdb`.`rentalplace` (
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
-- Table `electroscootdb`.`scootermodel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `electroscootdb`.`scootermodel` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price_per_time` FLOAT NOT NULL,
  `start_price` FLOAT NOT NULL,
  `discount` TINYINT NOT NULL DEFAULT 0,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `electroscootdb`.`scooter`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `electroscootdb`.`scooter` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `rental_place_name` VARCHAR(45) NULL,
  `model` VARCHAR(45) NOT NULL,
  `state` ENUM("RENTED", "OK", "BROKEN") NOT NULL DEFAULT 'OK',
  PRIMARY KEY (`id`),
  INDEX `rentalplaceId_idx` (`rental_place_name` ASC) VISIBLE,
  INDEX `ModelName_idx` (`model` ASC) VISIBLE,
  CONSTRAINT `rentalplaceId`
    FOREIGN KEY (`rental_place_name`)
    REFERENCES `electroscootdb`.`rentalplace` (`name`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `ModelName`
    FOREIGN KEY (`model`)
    REFERENCES `electroscootdb`.`scootermodel` (`name`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `electroscootdb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `electroscootdb`.`user` (
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
  INDEX `fk_user_scooter1_idx` (`scooter_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_scooter1`
    FOREIGN KEY (`scooter_id`)
    REFERENCES `electroscootdb`.`scooter` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `electroscootdb`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `electroscootdb`.`role` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `electroscootdb`.`user2role` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_id` INT UNSIGNED NOT NULL,
  `user_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `roleID_idx` (`role_id` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `userID_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `roleName`
    FOREIGN KEY (`role_id`)
    REFERENCES `electroscootdb`.`role` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `userusername`
    FOREIGN KEY (`user_id`)
    REFERENCES `electroscootdb`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `electroscootdb`.`scooterrental`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `electroscootdb`.`scooterrental` (
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
  INDEX `scooterId_idx` (`scooter_id` ASC) VISIBLE,
  INDEX `username_idx` (`username` ASC) VISIBLE,
  INDEX `rentalplaceAt_idx` (`init_rental_place_name` ASC) VISIBLE,
  INDEX `rentalplaceFinal_idx` (`final_rental_place_name` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `scooterRented`
    FOREIGN KEY (`scooter_id`)
    REFERENCES `electroscootdb`.`scooter` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `userRented`
    FOREIGN KEY (`username`)
    REFERENCES `electroscootdb`.`user` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `rentalplaceInit`
    FOREIGN KEY (`init_rental_place_name`)
    REFERENCES `electroscootdb`.`rentalplace` (`name`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `rentalplaceFinal`
    FOREIGN KEY (`final_rental_place_name`)
    REFERENCES `electroscootdb`.`rentalplace` (`name`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;