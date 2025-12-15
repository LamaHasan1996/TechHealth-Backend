use tech_health_mongo;

// ===============================
// Indexes (for performance evidence)
// ===============================
db.vital_readings.createIndex({ patient_id: 1, timestamp: -1 });
db.vital_readings.createIndex({ visit_id: 1 });
db.medication_rules.createIndex({ rule_id: 1 }, { unique: true });

// ===============================
// medication_rules: make >= 120
// ===============================
const rulesTarget = 120;
const rulesNow = db.medication_rules.countDocuments();

if (rulesNow < rulesTarget) {
  const bulkRules = [];
  for (let i = rulesNow + 1; i <= rulesTarget; i++) {
    const type = i % 3;
    let condition;
    if (type === 0) condition = { vital: "temperature", op: ">=", value: 37.5 };
    else if (type === 1) condition = { vital: "systolic_bp", op: ">", value: 150 };
    else condition = { vital: "glucose", op: ">", value: 180 };

    bulkRules.push({
      rule_id: `AUTO_RULE_${String(i).padStart(3, "0")}`,
      medication_id: (i % 6) + 1,
      description: `Auto rule #${i}`,
      condition: condition,
      active: true
    });
  }
  if (bulkRules.length) db.medication_rules.insertMany(bulkRules);
}

// ===============================
// vital_readings: make >= 200
// ===============================
const vitalsTarget = 200;
const vitalsNow = db.vital_readings.countDocuments();

if (vitalsNow < vitalsTarget) {
  const bulkVitals = [];
  const start = new Date("2024-06-01T08:00:00Z");

  for (let i = vitalsNow + 1; i <= vitalsTarget; i++) {
    const d = new Date(start.getTime() + i * 60 * 60 * 1000); // +1 hour each
    const patientId = (i % 120) + 1;
    const visitId = (i % 200) + 1;
    const recordedBy = (i % 100) + 1;

    bulkVitals.push({
      patient_id: patientId,
      visit_id: visitId,
      recorded_by: recordedBy,
      timestamp: d,
      vitals: {
        temperature: 36 + (i % 30) / 10,     // 36.0 .. 38.9
        systolic_bp: 110 + (i % 70),         // 110 .. 179
        diastolic_bp: 70 + (i % 40),         // 70 .. 109
        glucose: 90 + (i % 140)              // 90 .. 229
      },
      notes: `Seed reading #${i}`
    });
  }
  if (bulkVitals.length) db.vital_readings.insertMany(bulkVitals);
}

// ===============================
// Print counts
// ===============================
print("medication_rules =", db.medication_rules.countDocuments());
print("vital_readings   =", db.vital_readings.countDocuments());
