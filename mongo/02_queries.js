use tech_health_mongo;

// ===============================
// 1) Counts
// ===============================
print("medication_rules =", db.medication_rules.countDocuments());
print("vital_readings   =", db.vital_readings.countDocuments());

// ===============================
// 2) Show indexes
// ===============================
print("Indexes on vital_readings:");
printjson(db.vital_readings.getIndexes());

print("Indexes on medication_rules:");
printjson(db.medication_rules.getIndexes());

// ===============================
// 3) Mongo CRUD demo (vital_readings)
// ===============================

// CREATE
db.vital_readings.insertOne({
  patient_id: 999,
  visit_id: 999,
  recorded_by: 1,
  timestamp: new Date(),
  vitals: { temperature: 38.2, systolic_bp: 155, diastolic_bp: 95, glucose: 190 },
  notes: "CRUD demo reading"
});

// READ
db.vital_readings.find({ patient_id: 999 }).sort({ timestamp: -1 }).limit(1);

// UPDATE
db.vital_readings.updateOne(
  { patient_id: 999, notes: "CRUD demo reading" },
  { $set: { notes: "CRUD demo reading (updated)" } }
);

// READ after update
db.vital_readings.find({ patient_id: 999 }).sort({ timestamp: -1 }).limit(1);

// DELETE
db.vital_readings.deleteOne({ patient_id: 999 });

// Confirm delete
db.vital_readings.find({ patient_id: 999 }).limit(5);

// ===============================
// 4) Aggregation #1: Latest reading per patient (limit 20)
// ===============================
db.vital_readings.aggregate([
  { $sort: { patient_id: 1, timestamp: -1 } },
  { $group: { _id: "$patient_id", latest: { $first: "$$ROOT" } } },
  { $project: { _id: 0, patient_id: "$_id", timestamp: "$latest.timestamp", vitals: "$latest.vitals", notes: "$latest.notes" } },
  { $sort: { patient_id: 1 } },
  { $limit: 20 }
]);

// ===============================
// 5) Aggregation #2: Avg BP per patient (limit 20)
// ===============================
db.vital_readings.aggregate([
  {
    $group: {
      _id: "$patient_id",
      avg_systolic: { $avg: "$vitals.systolic_bp" },
      avg_diastolic: { $avg: "$vitals.diastolic_bp" },
      readings: { $sum: 1 }
    }
  },
  { $project: { _id: 0, patient_id: "$_id", avg_systolic: { $round: ["$avg_systolic", 2] }, avg_diastolic: { $round: ["$avg_diastolic", 2] }, readings: 1 } },
  { $sort: { patient_id: 1 } },
  { $limit: 20 }
]);

// ===============================
// 6) Aggregation #3: Alert counts (thresholds)
// ===============================
db.vital_readings.aggregate([
  {
    $project: {
      fever: { $gte: ["$vitals.temperature", 37.5] },
      high_bp: { $gt: ["$vitals.systolic_bp", 150] },
      high_glucose: { $gt: ["$vitals.glucose", 180] }
    }
  },
  {
    $group: {
      _id: null,
      fever_count: { $sum: { $cond: ["$fever", 1, 0] } },
      high_bp_count: { $sum: { $cond: ["$high_bp", 1, 0] } },
      high_glucose_count: { $sum: { $cond: ["$high_glucose", 1, 0] } }
    }
  }
]);
