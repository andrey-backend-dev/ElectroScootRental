DROP SCHEMA IF EXISTS `electroscootdb` ;

CREATE SCHEMA IF NOT EXISTS `electroscootdb` DEFAULT CHARACTER SET utf8 ;

USE `electroscootdb` ;

CREATE TABLE IF NOT EXISTS `electroscootdb`.`User` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(20) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `phone` VARCHAR(10) NOT NULL,
  `registered_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `firstname` VARCHAR(45) NULL,
  `secondname` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `subscription_till` TIMESTAMP NULL,
  `money` FLOAT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `phone_UNIQUE` (`phone` ASC) VISIBLE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `electroscootdb`.`Role` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `electroscootdb`.`User2Role` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `roleName` VARCHAR(45) NOT NULL,
  `userUsername` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `RoleID_idx` (`roleName` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `UserID_idx` (`userUsername` ASC) VISIBLE,
  CONSTRAINT `RoleName`
    FOREIGN KEY (`roleName`)
    REFERENCES `electroscootdb`.`Role` (`name`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `UserUsername`
    FOREIGN KEY (`userUsername`)
    REFERENCES `electroscootdb`.`User` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `electroscootdb`.`RentalPlace` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `rating` TINYINT(5) UNSIGNED NOT NULL DEFAULT 0,
  `address` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  UNIQUE INDEX `address_UNIQUE` (`address` ASC) VISIBLE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `electroscootdb`.`ScooterModel` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price_per_hour` FLOAT NOT NULL,
  `start_price` FLOAT NOT NULL,
  `discount` INT NOT NULL DEFAULT 0,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `electroscootdb`.`Scooter` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `rental_place_name` VARCHAR(45) NULL,
  `model` VARCHAR(45) NOT NULL,
  `state` ENUM("OK", "BROKEN") NOT NULL DEFAULT 'OK',
  PRIMARY KEY (`id`),
  INDEX `RentalPlaceId_idx` (`rental_place_name` ASC) VISIBLE,
  INDEX `ModelName_idx` (`model` ASC) VISIBLE,
  CONSTRAINT `RentalPlaceId`
    FOREIGN KEY (`rental_place_name`)
    REFERENCES `electroscootdb`.`RentalPlace` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `ModelName`
    FOREIGN KEY (`model`)
    REFERENCES `electroscootdb`.`ScooterModel` (`name`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `electroscootdb`.`ScooterRental` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `scooter_id` INT UNSIGNED NOT NULL,
  `scooter_taken_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `scooter_passed_at` TIMESTAMP NULL,
  `username` VARCHAR(20) NOT NULL,
  `mileage` FLOAT NULL,
  `init_rental_place_name` VARCHAR(45) NOT NULL,
  `final_rental_place_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `ScooterId_idx` (`scooter_id` ASC) VISIBLE,
  INDEX `Username_idx` (`username` ASC) VISIBLE,
  INDEX `RentalPlaceAt_idx` (`init_rental_place_name` ASC) VISIBLE,
  INDEX `RentalPlaceFinal_idx` (`final_rental_place_name` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `ScooterRented`
    FOREIGN KEY (`scooter_id`)
    REFERENCES `electroscootdb`.`Scooter` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `UserRented`
    FOREIGN KEY (`username`)
    REFERENCES `electroscootdb`.`User` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `RentalPlaceInit`
    FOREIGN KEY (`init_rental_place_name`)
    REFERENCES `electroscootdb`.`RentalPlace` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `RentalPlaceFinal`
    FOREIGN KEY (`final_rental_place_name`)
    REFERENCES `electroscootdb`.`RentalPlace` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;