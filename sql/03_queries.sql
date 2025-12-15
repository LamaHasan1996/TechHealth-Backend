-- Tech-Health (SQL) - Queries (CRUD + joins + aggregations + EXPLAIN)
USE tech_health_db;

-- counts after seeding
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

-- -----------------------------------------
-- SQL CRUD demo (Visits)
-- -----------------------------------------

-- CREATE (Insert a test visit)
INSERT INTO visits (patient_id, facility_id, scheduled_by, assigned_to, visit_datetime, status, notes)
SELECT
  (SELECT patient_id FROM patients ORDER BY patient_id LIMIT 1),
  (SELECT facility_id FROM facilities ORDER BY facility_id LIMIT 1),
  (SELECT user_id FROM users ORDER BY user_id LIMIT 1),
  (SELECT user_id FROM users ORDER BY user_id DESC LIMIT 1),
  '2024-12-20 10:00:00',
  'SCHEDULED',
  'Test visit for CRUD demo';

-- READ (Select inserted visit)
SELECT @vid := MAX(visit_id) FROM visits WHERE notes='Test visit for CRUD demo';
SELECT * FROM visits WHERE visit_id=@vid;

-- READ (List latest visits)
SELECT visit_id, patient_id, facility_id, visit_datetime, status, notes
FROM visits
ORDER BY visit_datetime DESC
LIMIT 10;

-- UPDATE
UPDATE visits
SET status='COMPLETED', notes='Test visit updated'
WHERE visit_id=@vid;

SELECT visit_id, status, notes
FROM visits
WHERE visit_id=@vid;

-- DELETE
DELETE FROM visits WHERE visit_id=@vid;
SELECT * FROM visits WHERE visit_id=@vid;

-- -----------------------------------------
-- Retrieval join (Upcoming visits)
-- joins visits + patients + facilities + users
-- -----------------------------------------
SELECT 
  v.visit_id,
  v.visit_datetime,
  v.status,
  CONCAT(p.first_name,' ',p.last_name) AS patient_name,
  f.name AS facility_name,
  CONCAT(u.first_name,' ',u.last_name) AS assigned_staff,
  CONCAT(s.first_name,' ',s.last_name) AS scheduled_by
FROM visits v
JOIN patients p   ON p.patient_id = v.patient_id
JOIN facilities f ON f.facility_id = v.facility_id
LEFT JOIN users u ON u.user_id = v.assigned_to
JOIN users s      ON s.user_id = v.scheduled_by
WHERE v.status IN ('SCHEDULED','IN_PROGRESS')
ORDER BY v.visit_datetime ASC
LIMIT 20;

-- -----------------------------------------
-- Advanced join (Medication administration history)
-- joins patients + visits + medication_administration + prescriptions + medications + users
-- -----------------------------------------
SELECT
  ma.administration_id,
  ma.administered_at,
  v.visit_id,
  v.visit_datetime,
  CONCAT(p.first_name,' ',p.last_name) AS patient_name,
  m.name AS medication_name,
  pr.prescription_type,
  pr.dosage_instruction,
  ma.dose_given,
  ma.was_given,
  CONCAT(u.first_name,' ',u.last_name) AS administered_by,
  ma.comments
FROM medication_administration ma
JOIN visits v           ON v.visit_id = ma.visit_id
JOIN patients p         ON p.patient_id = v.patient_id
JOIN prescriptions pr   ON pr.prescription_id = ma.prescription_id
JOIN medications m      ON m.medication_id = pr.medication_id
JOIN users u            ON u.user_id = ma.administered_by
ORDER BY ma.administered_at DESC
LIMIT 30;

-- -----------------------------------------
-- Aggregations
-- -----------------------------------------

-- 1) Adherence rate per patient
SELECT 
  p.patient_id,
  CONCAT(p.first_name,' ',p.last_name) AS patient_name,
  COUNT(*) AS total_events,
  SUM(CASE WHEN ma.was_given=TRUE THEN 1 ELSE 0 END) AS given_events,
  ROUND(100 * SUM(CASE WHEN ma.was_given=TRUE THEN 1 ELSE 0 END) / COUNT(*), 2) AS adherence_percent
FROM medication_administration ma
JOIN visits v ON v.visit_id = ma.visit_id
JOIN patients p ON p.patient_id = v.patient_id
GROUP BY p.patient_id, patient_name
ORDER BY adherence_percent ASC
LIMIT 20;

-- 2) Facility workload by visit status
SELECT 
  f.name AS facility_name,
  v.status,
  COUNT(*) AS visits_count
FROM visits v
JOIN facilities f ON f.facility_id = v.facility_id
GROUP BY f.name, v.status
ORDER BY f.name, visits_count DESC;

-- 3) Most administered medications
SELECT 
  m.name AS medication_name,
  COUNT(*) AS times_administered
FROM medication_administration ma
JOIN prescriptions pr ON pr.prescription_id = ma.prescription_id
JOIN medications m ON m.medication_id = pr.medication_id
GROUP BY m.name
ORDER BY times_administered DESC
LIMIT 10;

-- 4) Stock risk (low stock OR high demand)
SELECT 
  m.medication_id,
  m.name AS medication_name,
  m.stock_quantity,
  COUNT(pr.prescription_id) AS total_prescriptions
FROM medications m
LEFT JOIN prescriptions pr ON pr.medication_id = m.medication_id
GROUP BY m.medication_id, m.name, m.stock_quantity
HAVING m.stock_quantity < 100 OR total_prescriptions > 30
ORDER BY m.stock_quantity ASC, total_prescriptions DESC
LIMIT 20;

-- -----------------------------------------
-- Optimization evidence (EXPLAIN)
-- -----------------------------------------
EXPLAIN
SELECT visit_id, patient_id, visit_datetime, status
FROM visits
WHERE patient_id = 1
ORDER BY visit_datetime DESC
LIMIT 20;
