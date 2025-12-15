-- Tech-Health (SQL) - Schema
CREATE DATABASE IF NOT EXISTS tech_health_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE tech_health_db;

-- (Optional) allow re-run safely
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS medication_administration;
DROP TABLE IF EXISTS visits;
DROP TABLE IF EXISTS prescriptions;
DROP TABLE IF EXISTS medications;
DROP TABLE IF EXISTS patient_facilities;
DROP TABLE IF EXISTS patients;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS facilities;
SET FOREIGN_KEY_CHECKS = 1;

-- 1) Facilities
CREATE TABLE facilities (
  facility_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  facility_type ENUM('ELDERLY_HOME','CLINIC','HOSPITAL','OTHER') NOT NULL DEFAULT 'ELDERLY_HOME',
  city VARCHAR(100),
  address_line VARCHAR(255),
  phone VARCHAR(30),
  is_active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- 2) Roles
CREATE TABLE roles (
  role_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE,
  description VARCHAR(255)
) ENGINE=InnoDB;

-- 3) Users (staff / admins)
CREATE TABLE users (
  user_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  email VARCHAR(150) NOT NULL UNIQUE,
  phone VARCHAR(30),
  password_hash VARCHAR(255),
  is_active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- 4) User ↔ Role (many-to-many)
CREATE TABLE user_roles (
  user_id INT UNSIGNED NOT NULL,
  role_id INT UNSIGNED NOT NULL,
  assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (user_id, role_id),
  CONSTRAINT fk_user_roles_user
    FOREIGN KEY (user_id) REFERENCES users(user_id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_user_roles_role
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

-- 5) Patients
CREATE TABLE patients (
  patient_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  date_of_birth DATE,
  gender ENUM('MALE','FEMALE','OTHER'),
  national_id VARCHAR(50),
  primary_facility_id INT UNSIGNED,
  emergency_contact_name VARCHAR(150),
  emergency_contact_phone VARCHAR(30),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_patients_primary_facility
    FOREIGN KEY (primary_facility_id) REFERENCES facilities(facility_id)
    ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB;

-- 6) Patient ↔ Facility (many-to-many)
CREATE TABLE patient_facilities (
  patient_id INT UNSIGNED NOT NULL,
  facility_id INT UNSIGNED NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE,
  PRIMARY KEY (patient_id, facility_id, start_date),
  CONSTRAINT fk_pf_patient
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_pf_facility
    FOREIGN KEY (facility_id) REFERENCES facilities(facility_id)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- 7) Medications
CREATE TABLE medications (
  medication_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(150) NOT NULL,
  form ENUM('TABLET','SYRUP','INJECTION','CAPSULE','OTHER') NOT NULL,
  strength VARCHAR(50),
  route ENUM('ORAL','IV','IM','SC','TOPICAL','OTHER') NOT NULL DEFAULT 'ORAL',
  is_controlled BOOLEAN NOT NULL DEFAULT FALSE,
  stock_quantity INT UNSIGNED NOT NULL DEFAULT 0,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- 8) Prescriptions
CREATE TABLE prescriptions (
  prescription_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  patient_id INT UNSIGNED NOT NULL,
  doctor_id INT UNSIGNED NOT NULL,
  medication_id INT UNSIGNED NOT NULL,
  prescription_type ENUM('DAILY','CONDITIONAL') NOT NULL DEFAULT 'DAILY',
  dosage_instruction VARCHAR(255) NOT NULL,
  frequency_per_day DECIMAL(4,2),
  start_date DATE NOT NULL,
  end_date DATE,
  condition_description VARCHAR(255),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_pres_patient
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_pres_doctor
    FOREIGN KEY (doctor_id) REFERENCES users(user_id)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_pres_med
    FOREIGN KEY (medication_id) REFERENCES medications(medication_id)
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

-- 9) Visits
CREATE TABLE visits (
  visit_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  patient_id INT UNSIGNED NOT NULL,
  facility_id INT UNSIGNED NOT NULL,
  scheduled_by INT UNSIGNED NOT NULL,
  assigned_to INT UNSIGNED,
  visit_datetime DATETIME NOT NULL,
  status ENUM('SCHEDULED','IN_PROGRESS','COMPLETED','CANCELLED','MISSED')
    NOT NULL DEFAULT 'SCHEDULED',
  notes TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_vis_patient
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_vis_facility
    FOREIGN KEY (facility_id) REFERENCES facilities(facility_id)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_vis_scheduled_by
    FOREIGN KEY (scheduled_by) REFERENCES users(user_id)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_vis_assigned_to
    FOREIGN KEY (assigned_to) REFERENCES users(user_id)
    ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB;

-- 10) Medication administration during visits
CREATE TABLE medication_administration (
  administration_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  visit_id INT UNSIGNED NOT NULL,
  prescription_id INT UNSIGNED NOT NULL,
  administered_by INT UNSIGNED NOT NULL,
  administered_at DATETIME NOT NULL,
  dose_given VARCHAR(100),
  was_given BOOLEAN NOT NULL DEFAULT TRUE,
  comments TEXT,
  CONSTRAINT fk_ma_visit
    FOREIGN KEY (visit_id) REFERENCES visits(visit_id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_ma_prescription
    FOREIGN KEY (prescription_id) REFERENCES prescriptions(prescription_id)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_ma_admin_by
    FOREIGN KEY (administered_by) REFERENCES users(user_id)
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Helpful indexes
ALTER TABLE patients
  ADD INDEX idx_patients_last_name (last_name);

ALTER TABLE prescriptions
  ADD INDEX idx_prescriptions_patient (patient_id);

ALTER TABLE visits
  ADD INDEX idx_visits_patient_datetime (patient_id, visit_datetime);
