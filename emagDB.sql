-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema emag
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema emag
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `emag` DEFAULT CHARACTER SET utf8 ;
USE `emag` ;

-- -----------------------------------------------------
-- Table `emag`.`categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emag`.`categories` (
  `category_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `category_name` VARCHAR(45) NOT NULL,
  `parent_category_id` INT(10) UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE INDEX `category_id_UNIQUE` (`category_id` ASC),
  INDEX `category_id_idx` (`parent_category_id` ASC),
  CONSTRAINT `categories_category_id`
    FOREIGN KEY (`parent_category_id`)
    REFERENCES `emag`.`categories` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `emag`.`characteristics`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emag`.`characteristics` (
  `characteristic_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `unit` INT(11) NULL DEFAULT NULL,
  `category_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`characteristic_id`),
  UNIQUE INDEX `characteristic_id_UNIQUE` (`characteristic_id` ASC),
  INDEX `fk_category_id_idx` (`category_id` ASC),
  CONSTRAINT `fk_category_id`
    FOREIGN KEY (`category_id`)
    REFERENCES `emag`.`categories` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `emag`.`products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emag`.`products` (
  `product_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `brand` VARCHAR(45) NOT NULL,
  `price` DOUBLE NOT NULL,
  `model` VARCHAR(45) NULL DEFAULT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `discount_percent` INT(11) NULL DEFAULT '0',
  `discount_expiration` DATE NULL DEFAULT NULL,
  `product_picture` VARCHAR(255) NULL DEFAULT NULL,
  `category_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE INDEX `product_id_UNIQUE` (`product_id` ASC),
  INDEX `category_id_idx` (`category_id` ASC),
  CONSTRAINT `fk_categories_category_id`
    FOREIGN KEY (`category_id`)
    REFERENCES `emag`.`categories` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `emag`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emag`.`users` (
  `user_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(60) NOT NULL,
  `is_admin` TINYINT(1) NOT NULL DEFAULT '0',
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(45) NULL DEFAULT NULL,
  `age` INT(10) UNSIGNED NULL DEFAULT NULL,
  `profile_picture` VARCHAR(255) NULL DEFAULT NULL,
  `address` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC),
  UNIQUE INDEX `mobile_UNIQUE` (`phone` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 19
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `emag`.`favourite_products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emag`.`favourite_products` (
  `user_id` INT(10) UNSIGNED NOT NULL,
  `product_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`user_id`, `product_id`),
  INDEX `product_id_idx` (`product_id` ASC),
  CONSTRAINT `fk_favourite_product_id`
    FOREIGN KEY (`product_id`)
    REFERENCES `emag`.`products` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_favourite_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `emag`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `emag`.`statuses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emag`.`statuses` (
  `status_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `status_description` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`status_id`),
  UNIQUE INDEX `status_id_UNIQUE` (`status_id` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `emag`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emag`.`orders` (
  `order_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `total_cost` DOUBLE NOT NULL,
  `delivery_address` VARCHAR(45) NOT NULL,
  `user_id` INT(10) UNSIGNED NOT NULL,
  `status_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`order_id`),
  UNIQUE INDEX `order_id_UNIQUE` (`order_id` ASC),
  INDEX `user_id_idx` (`user_id` ASC),
  INDEX `status_id_idx` (`status_id` ASC),
  CONSTRAINT `fk_order_status_id`
    FOREIGN KEY (`status_id`)
    REFERENCES `emag`.`statuses` (`status_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `emag`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `emag`.`ordered_products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emag`.`ordered_products` (
  `order_id` INT(10) UNSIGNED NOT NULL,
  `product_id` INT(10) UNSIGNED NOT NULL,
  `quantity` INT(11) NOT NULL,
  PRIMARY KEY (`order_id`, `product_id`),
  UNIQUE INDEX `order_id_UNIQUE` (`order_id` ASC),
  UNIQUE INDEX `product_id_UNIQUE` (`product_id` ASC),
  CONSTRAINT `fk_ordered_order_id`
    FOREIGN KEY (`order_id`)
    REFERENCES `emag`.`orders` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ordered_product_id`
    FOREIGN KEY (`product_id`)
    REFERENCES `emag`.`products` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `emag`.`products_characteristics`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emag`.`products_characteristics` (
  `product_id` INT(10) UNSIGNED NOT NULL,
  `characteristic_id` INT(10) UNSIGNED NOT NULL,
  `value` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`product_id`, `characteristic_id`),
  UNIQUE INDEX `product_id_UNIQUE` (`product_id` ASC),
  UNIQUE INDEX `characteristic_id_UNIQUE` (`characteristic_id` ASC),
  CONSTRAINT `fk_characteristics_characteristic_id`
    FOREIGN KEY (`characteristic_id`)
    REFERENCES `emag`.`characteristics` (`characteristic_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_products_product_id`
    FOREIGN KEY (`product_id`)
    REFERENCES `emag`.`products` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `emag`.`reviews`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emag`.`reviews` (
  `review_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `raiting` INT(10) UNSIGNED NOT NULL,
  `date` DATETIME NOT NULL,
  `review_text` VARCHAR(255) NULL DEFAULT NULL,
  `user_id` INT(10) UNSIGNED NOT NULL,
  `product_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`review_id`),
  UNIQUE INDEX `review_id_UNIQUE` (`review_id` ASC),
  INDEX `fk_users_user_id_idx` (`user_id` ASC),
  INDEX `fk_products_reviews_product_id_idx` (`product_id` ASC),
  CONSTRAINT `fk_products_reviews_product_id`
    FOREIGN KEY (`product_id`)
    REFERENCES `emag`.`products` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `emag`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
