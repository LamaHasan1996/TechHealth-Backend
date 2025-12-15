-- Tech-Health (SQL) - Seed (base + make >=100 records per table)
USE tech_health_db;

-- -------------------------------------------------
-- Helper numbers table 1..500 (MySQL 5.7 OK)
-- -------------------------------------------------
DROP TEMPORARY TABLE IF EXISTS seq;
CREATE TEMPORARY TABLE seq (n INT PRIMARY KEY);

INSERT INTO seq(n)
SELECT a.d + b.d*10 + c.d*100 + 1 AS n
FROM (SELECT 0 d UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL
      SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) a
CROSS JOIN (SELECT 0 d UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL
            SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) b
CROSS JOIN (SELECT 0 d UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4) c
WHERE (a.d + b.d*10 + c.d*100 + 1) <= 500;

-- -----------------------
-- Base ROLES (must exist for user_roles mapping)
-- -----------------------
INSERT IGNORE INTO roles (name, description) VALUES
('ADMIN', 'System administrator'),
('DOCTOR', 'Medical doctor'),
('NURSE', 'Nurse working in facility'),
('PHARMACIST', 'Pharmacist managing meds'),
('CAREGIVER', 'Caregiver assisting patients');

-- -----------------------
-- Base FACILITIES (ensure facility_id 1..3 exist)
-- -----------------------
INSERT INTO facilities (name, facility_type, city, address_line, phone)
SELECT 'Sunrise Elderly Home', 'ELDERLY_HOME', 'Berlin', 'Street 1', '+49 170 1111111'
WHERE (SELECT COUNT(*) FROM facilities) = 0;

INSERT INTO facilities (name, facility_type, city, address_line, phone)
SELECT 'Healthy Life Clinic', 'CLINIC', 'Potsdam', 'Street 2', '+49 170 2222222'
WHERE (SELECT COUNT(*) FROM facilities) = 1;

INSERT INTO facilities (name, facility_type, city, address_line, phone)
SELECT 'GreenCare Medical Center', 'CLINIC', 'Berlin', 'Street 3', '+49 170 3333333'
WHERE (SELECT COUNT(*) FROM facilities) = 2;

-- -----------------------
-- Base USERS (ensure user_id 1..6 exist)
-- -----------------------
INSERT INTO users (first_name, last_name, email, phone, password_hash)
SELECT 'Ali', 'Khan', 'ali.khan@techhealth.test', '+49 170 1000001', 'pass'
WHERE (SELECT COUNT(*) FROM users) = 0;

INSERT INTO users (first_name, last_name, email, phone, password_hash)
SELECT 'Lina', 'Yousef', 'lina.yousef@techhealth.test', '+49 170 1000002', 'pass'
WHERE (SELECT COUNT(*) FROM users) = 1;

INSERT INTO users (first_name, last_name, email, phone, password_hash)
SELECT 'Omar', 'Saleh', 'omar.saleh@techhealth.test', '+49 170 1000003', 'pass'
WHERE (SELECT COUNT(*) FROM users) = 2;

INSERT INTO users (first_name, last_name, email, phone, password_hash)
SELECT 'Jana', 'Hassan', 'jana.hassan@techhealth.test', '+49 170 1000004', 'pass'
WHERE (SELECT COUNT(*) FROM users) = 3;

INSERT INTO users (first_name, last_name, email, phone, password_hash)
SELECT 'Admin', 'User', 'admin@techhealth.test', '+49 170 1000005', 'pass'
WHERE (SELECT COUNT(*) FROM users) = 4;

INSERT INTO users (first_name, last_name, email, phone, password_hash)
SELECT 'Rafiq', 'Hamad', 'rafiq.hamad@techhealth.test', '+49 170 1000006', 'pass'
WHERE (SELECT COUNT(*) FROM users) = 5;

-- Assign roles to the 6 base users
INSERT IGNORE INTO user_roles (user_id, role_id)
SELECT 1, role_id FROM roles WHERE name='DOCTOR';
INSERT IGNORE INTO user_roles (user_id, role_id)
SELECT 2, role_id FROM roles WHERE name='NURSE';
INSERT IGNORE INTO user_roles (user_id, role_id)
SELECT 3, role_id FROM roles WHERE name='PHARMACIST';
INSERT IGNORE INTO user_roles (user_id, role_id)
SELECT 4, role_id FROM roles WHERE name='CAREGIVER';
INSERT IGNORE INTO user_roles (user_id, role_id)
SELECT 5, role_id FROM roles WHERE name='ADMIN';
INSERT IGNORE INTO user_roles (user_id, role_id)
SELECT 6, role_id FROM roles WHERE name='DOCTOR';

-- -----------------------
-- Base PATIENTS (40 named patients)
-- -----------------------
INSERT INTO patients
(first_name, last_name, date_of_birth, gender, national_id, primary_facility_id, emergency_contact_name, emergency_contact_phone)
VALUES
('Fatima','Rahman','1950-03-12','FEMALE','P001',1,'Ahmed Rahman','+49 170 2000001'),
('Mohamed','Ali','1945-07-25','MALE','P002',1,'Sara Ali','+49 170 2000002'),
('Nour','Hussein','1960-11-05','FEMALE','P003',2,'Yara Hussein','+49 170 2000003'),
('Amina','Salem','1948-02-10','FEMALE','P004',1,'Hani Salem','+49 170 2000004'),
('Karim','Fouad','1955-07-18','MALE','P005',2,'Rana Fouad','+49 170 2000005'),
('Layla','Kamal','1952-09-22','FEMALE','P006',3,'Samir Kamal','+49 170 2000006'),
('Ziad','Farhan','1944-04-14','MALE','P007',1,'Dina Farhan','+49 170 2000007'),
('Ola','Mahmoud','1958-01-30','FEMALE','P008',2,'Fadi Mahmoud','+49 170 2000008'),
('Rami','Tarek','1950-11-08','MALE','P009',3,'Nada Tarek','+49 170 2000009'),
('Sara','Fawzi','1949-06-12','FEMALE','P010',1,'Ahmad Fawzi','+49 170 2000010'),
('Yasir','Hamdan','1951-03-05','MALE','P011',1,'Mona Hamdan','+49 170 2000011'),
('Reem','Daher','1953-08-19','FEMALE','P012',2,'Tamer Daher','+49 170 2000012'),
('Hala','Shami','1947-01-02','FEMALE','P013',3,'Omar Shami','+49 170 2000013'),
('Samir','Adnan','1954-09-27','MALE','P014',1,'Rana Adnan','+49 170 2000014'),
('Maya','Sami','1952-10-16','FEMALE','P015',2,'Majed Sami','+49 170 2000015'),
('Farid','Azzam','1956-12-03','MALE','P016',3,'Nour Azzam','+49 170 2000016'),
('Yasmin','Saad','1958-04-23','FEMALE','P017',1,'Khaled Saad','+49 170 2000017'),
('Tariq','Nasr','1959-05-18','MALE','P018',2,'Mira Nasr','+49 170 2000018'),
('Mona','Hilal','1957-06-12','FEMALE','P019',3,'Rafiq Hilal','+49 170 2000019'),
('Nabil','Hafez','1946-01-10','MALE','P020',1,'Dana Hafez','+49 170 2000020'),
('Hiba','Zein','1950-04-29','FEMALE','P021',2,'Hadi Zein','+49 170 2000021'),
('Adnan','Saif','1953-08-03','MALE','P022',3,'Yara Saif','+49 170 2000022'),
('Rania','Zaher','1954-11-11','FEMALE','P023',1,'Salem Zaher','+49 170 2000023'),
('Majed','Issa','1947-02-17','MALE','P024',2,'Nadia Issa','+49 170 2000024'),
('Lubna','Hani','1955-03-14','FEMALE','P025',3,'Firas Hani','+49 170 2000025'),
('Fouad','Kareem','1958-09-28','MALE','P026',1,'Mira Kareem','+49 170 2000026'),
('Sahar','Masri','1952-12-01','FEMALE','P027',2,'Omar Masri','+49 170 2000027'),
('Waleed','Nasser','1951-10-22','MALE','P028',3,'Lina Nasser','+49 170 2000028'),
('Najwa','Darwish','1956-06-30','FEMALE','P029',1,'Amir Darwish','+49 170 2000029'),
('Sami','Rahim','1949-05-15','MALE','P030',2,'Dana Rahim','+49 170 2000030'),
('Hatem','Jaber','1950-09-07','MALE','P031',1,'Mona Jaber','+49 170 2000031'),
('Rula','Samer','1953-04-04','FEMALE','P032',2,'Hadi Samer','+49 170 2000032'),
('Khaled','Jouda','1952-01-22','MALE','P033',3,'Reem Jouda','+49 170 2000033'),
('Nadine','Hamad','1946-12-19','FEMALE','P034',1,'Rafi Hamad','+49 170 2000034'),
('Bilal','Khatib','1954-07-14','MALE','P035',2,'Yasmin Khatib','+49 170 2000035'),
('Dina','Shaker','1956-11-07','FEMALE','P036',3,'Kareem Shaker','+49 170 2000036'),
('Aref','Mansour','1957-12-29','MALE','P037',1,'Lama Mansour','+49 170 2000037'),
('Rasha','Issam','1951-02-26','FEMALE','P038',2,'Omar Issam','+49 170 2000038'),
('Wassim','Harb','1948-03-09','MALE','P039',3,'Dalia Harb','+49 170 2000039'),
('Alya','Barakat','1959-09-21','FEMALE','P040',1,'Hani Barakat','+49 170 2000040');

-- -----------------------
-- Base MEDICATIONS (ensure medication_id 1..6 exist)
-- -----------------------
INSERT INTO medications (name, form, strength, route, is_controlled, stock_quantity) VALUES
('Paracetamol','TABLET','500 mg','ORAL',FALSE,1000),
('Lisinopril','TABLET','10 mg','ORAL',FALSE,400),
('Insulin','INJECTION','10 IU','SC',TRUE,300),
('Aspirin','TABLET','100 mg','ORAL',FALSE,500),
('Metformin','TABLET','500 mg','ORAL',FALSE,600),
('Atorvastatin','TABLET','20 mg','ORAL',FALSE,350);

-- -----------------------
-- Expand tables to >= 100 (compatible with your seed_more_compat.sql logic)
-- -----------------------

-- FACILITIES -> 100+
INSERT INTO facilities (name, facility_type, city, address_line, phone, is_active)
SELECT
  CONCAT('Facility ', LPAD(s.n, 3, '0')),
  'CLINIC',
  CONCAT('City ', ((s.n - 1) % 10) + 1),
  CONCAT('Address line ', s.n),
  CONCAT('+49 170 ', LPAD(s.n, 7, '0')),
  TRUE
FROM seq s
WHERE s.n <= GREATEST(0, 100 - (SELECT COUNT(*) FROM facilities));

-- ROLES -> 100+ (adds ROLE_### beside the 5 base roles)
INSERT INTO roles (name, description)
SELECT
  CONCAT('ROLE_', LPAD(s.n, 3, '0')),
  CONCAT('Auto role #', s.n)
FROM seq s
WHERE s.n <= GREATEST(0, 100 - (SELECT COUNT(*) FROM roles));

-- USERS -> 100+ (unique email)
INSERT INTO users (first_name, last_name, email, phone, password_hash, is_active)
SELECT
  CONCAT('User', LPAD(s.n, 3, '0')),
  'Auto',
  CONCAT('user', LPAD(s.n, 3, '0'), '@techhealth.test'),
  CONCAT('+49 170 9', LPAD(s.n, 6, '0')),
  'pass',
  TRUE
FROM seq s
WHERE s.n <= GREATEST(0, 100 - (SELECT COUNT(*) FROM users));

-- USER_ROLES -> cover ALL users with one of the 5 base roles (+ extra caregiver for even users)
INSERT IGNORE INTO user_roles (user_id, role_id)
SELECT
  u.user_id,
  r.role_id
FROM users u
JOIN roles r
  ON r.name = CASE (u.user_id % 5)
    WHEN 0 THEN 'ADMIN'
    WHEN 1 THEN 'DOCTOR'
    WHEN 2 THEN 'NURSE'
    WHEN 3 THEN 'PHARMACIST'
    ELSE 'CAREGIVER'
  END;

INSERT IGNORE INTO user_roles (user_id, role_id)
SELECT u.user_id, r.role_id
FROM users u
JOIN roles r ON r.name = 'CAREGIVER'
WHERE u.user_id % 2 = 0;

-- PATIENTS -> 100+ (adds “Patient### Auto” after the 40 named patients)
INSERT INTO patients
(first_name, last_name, date_of_birth, gender, national_id, primary_facility_id, emergency_contact_name, emergency_contact_phone)
SELECT
  CONCAT('Patient', LPAD(s.n, 3, '0')),
  'Auto',
  DATE_SUB('1965-01-01', INTERVAL (s.n * 30) DAY),
  CASE (s.n % 3) WHEN 0 THEN 'MALE' WHEN 1 THEN 'FEMALE' ELSE 'OTHER' END,
  CONCAT('PX', LPAD(s.n, 4, '0')),
  1 + (s.n % 3),
  CONCAT('EC', LPAD(s.n, 3, '0')),
  CONCAT('+49 170 8', LPAD(s.n, 6, '0'))
FROM seq s
WHERE s.n <= GREATEST(0, 100 - (SELECT COUNT(*) FROM patients));

-- PATIENT_FACILITIES -> 100+ (safe)
INSERT IGNORE INTO patient_facilities (patient_id, facility_id, start_date, end_date)
SELECT
  p.patient_id,
  COALESCE(p.primary_facility_id, 1),
  DATE_ADD('2024-01-01', INTERVAL (p.patient_id % 60) DAY),
  NULL
FROM patients p;

-- MEDICATIONS -> 100+ (adds Medication_### after the 6 base meds)
INSERT INTO medications (name, form, strength, route, is_controlled, stock_quantity)
SELECT
  CONCAT('Medication_', LPAD(s.n, 3, '0')),
  CASE (s.n % 5)
    WHEN 0 THEN 'TABLET'
    WHEN 1 THEN 'SYRUP'
    WHEN 2 THEN 'INJECTION'
    WHEN 3 THEN 'CAPSULE'
    ELSE 'OTHER'
  END,
  CONCAT(((s.n % 500) + 1), ' mg'),
  CASE (s.n % 6)
    WHEN 0 THEN 'ORAL'
    WHEN 1 THEN 'IV'
    WHEN 2 THEN 'IM'
    WHEN 3 THEN 'SC'
    WHEN 4 THEN 'TOPICAL'
    ELSE 'OTHER'
  END,
  CASE WHEN (s.n % 20) = 0 THEN TRUE ELSE FALSE END,
  50 + (s.n % 500)
FROM seq s
WHERE s.n <= GREATEST(0, 100 - (SELECT COUNT(*) FROM medications));

-- -----------------------
-- PRESCRIPTIONS -> make plenty (>= 100)
-- -----------------------
INSERT INTO prescriptions
  (patient_id, doctor_id, medication_id, prescription_type,
   dosage_instruction, frequency_per_day, start_date, end_date, condition_description)
SELECT
  p.patient_id,
  CASE WHEN (p.patient_id % 2) = 0 THEN 1 ELSE 6 END AS doctor_id,
  ((p.patient_id - 1) % 6) + 1 AS medication_id,
  CASE WHEN (p.patient_id % 7) = 0 THEN 'CONDITIONAL' ELSE 'DAILY' END AS prescription_type,
  CONCAT('Seed: take ', ((p.patient_id - 1) % 3) + 1, ' tablet(s)') AS dosage_instruction,
  CASE WHEN (p.patient_id % 7) = 0 THEN NULL ELSE ((p.patient_id - 1) % 3) + 1 END AS frequency_per_day,
  DATE_ADD('2024-05-01', INTERVAL p.patient_id DAY) AS start_date,
  NULL AS end_date,
  CASE WHEN (p.patient_id % 7) = 0 THEN 'Conditional rule: vitals check required' ELSE NULL END AS condition_description
FROM patients p;

INSERT INTO prescriptions
  (patient_id, doctor_id, medication_id, prescription_type,
   dosage_instruction, frequency_per_day, start_date, end_date, condition_description)
SELECT
  p.patient_id,
  1 AS doctor_id,
  ((p.patient_id + 2) % 6) + 1 AS medication_id,
  'DAILY' AS prescription_type,
  CONCAT('Seed2: take ', ((p.patient_id + 1) % 2) + 1, ' tablet(s)') AS dosage_instruction,
  ((p.patient_id + 1) % 3) + 1 AS frequency_per_day,
  DATE_ADD('2024-06-01', INTERVAL p.patient_id DAY) AS start_date,
  NULL AS end_date,
  NULL AS condition_description
FROM patients p
WHERE (p.patient_id % 2) = 0;

-- -----------------------
-- VISITS -> create 3 visits per patient (=> >= 300 visits for 100 patients)
-- -----------------------
INSERT INTO visits (patient_id, facility_id, scheduled_by, assigned_to, visit_datetime, status, notes)
SELECT
  p.patient_id,
  COALESCE(p.primary_facility_id, 1) AS facility_id,
  3 AS scheduled_by,
  CASE WHEN (p.patient_id % 2) = 0 THEN 2 ELSE 4 END AS assigned_to,
  DATE_ADD('2024-06-01 08:00:00', INTERVAL ((p.patient_id - 1) * 24 + s.n * 6) HOUR) AS visit_datetime,
  CASE WHEN s.n = 0 THEN 'SCHEDULED' WHEN s.n = 1 THEN 'IN_PROGRESS' ELSE 'COMPLETED' END AS status,
  CONCAT('Seed visit #', s.n + 1, ' for patient ', p.patient_id) AS notes
FROM patients p
CROSS JOIN (SELECT 0 AS n UNION ALL SELECT 1 UNION ALL SELECT 2) s;

-- -----------------------
-- MEDICATION_ADMINISTRATION -> one per visit (=> >= 300 rows)
-- -----------------------
INSERT INTO medication_administration
  (visit_id, prescription_id, administered_by, administered_at, dose_given, was_given, comments)
SELECT
  v.visit_id,
  (SELECT pr.prescription_id
   FROM prescriptions pr
   WHERE pr.patient_id = v.patient_id
   ORDER BY pr.prescription_id
   LIMIT 1) AS prescription_id,
  CASE WHEN (v.patient_id % 2) = 0 THEN 2 ELSE 4 END AS administered_by,
  DATE_ADD(v.visit_datetime, INTERVAL 10 MINUTE) AS administered_at,
  'Seed dose' AS dose_given,
  CASE WHEN (v.visit_id % 3) = 0 THEN TRUE ELSE FALSE END AS was_given,
  'Seeded administration event' AS comments
FROM visits v
WHERE (SELECT COUNT(*) FROM prescriptions pr WHERE pr.patient_id = v.patient_id) > 0;

-- -----------------------
SELECT 'facilities' t, COUNT(*) c FROM facilities
UNION ALL SELECT 'roles', COUNT(*) FROM roles
UNION ALL SELECT 'users', COUNT(*) FROM users
UNION ALL SELECT 'user_roles', COUNT(*) FROM user_roles
UNION ALL SELECT 'patients', COUNT(*) FROM patients
UNION ALL SELECT 'patient_facilities', COUNT(*) FROM patient_facilities
UNION ALL SELECT 'medications', COUNT(*) FROM medications
UNION ALL SELECT 'prescriptions', COUNT(*) FROM prescriptions
UNION ALL SELECT 'visits', COUNT(*) FROM visits
UNION ALL SELECT 'medication_administration', COUNT(*) FROM medication_administration;
