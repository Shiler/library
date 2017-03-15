-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema library
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema library
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `library` DEFAULT CHARACTER SET utf8 ;
USE `library` ;

-- -----------------------------------------------------
-- Table `library`.`book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`book` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `brief` VARCHAR(50) NOT NULL,
  `publish_year` INT(11) NOT NULL,
  `author` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `library`.`employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`employee` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `date_of_birth` DATE NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `library`.`employee_book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`employee_book` (
  `book_id` INT(11) NOT NULL,
  `employee_id` INT(11) NOT NULL,
  `id` INT(11) NULL,
  PRIMARY KEY (`book_id`, `employee_id`),
  INDEX `fk_book_has_employee_employee1_idx` (`employee_id` ASC),
  INDEX `fk_book_has_employee_book_idx` (`book_id` ASC),
  CONSTRAINT `fk_book_has_employee_book`
    FOREIGN KEY (`book_id`)
    REFERENCES `library`.`book` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_book_has_employee_employee1`
    FOREIGN KEY (`employee_id`)
    REFERENCES `library`.`employee` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
