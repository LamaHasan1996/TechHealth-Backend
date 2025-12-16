# Tech-Health Database Project (SQL + MongoDB)

## Project brief

Tech-Health is a hybrid database project that models a small healthcare monitoring system.

- **MySQL (Relational):** stores structured operational data such as facilities, staff/users, patients, visits, prescriptions, and medication administration events.
- **MongoDB (NoSQL):** stores semi-structured data for high-frequency measurements and rules, including vital readings per patient/visit and medication threshold rules.
- **Goal:** demonstrate SQL + NoSQL design, seeding (100+ rows/documents), CRUD operations, joins/aggregations, indexing, and query optimization (EXPLAIN), plus an example integration flow (SQL + Mongo used together).

## What is in this repository?

This repository contains the database deliverables for Tech-Health (Hybrid SQL + NoSQL):

- MySQL schema + seed data (100+ rows per table)
- SQL queries (CRUD + joins + aggregations + EXPLAIN)
- MongoDB seed + queries (CRUD + aggregations + indexes)

> The full documentation (screenshots, figures, explanations) is submitted separately as a Word/PDF file, and includes the GitHub link.

## Files

### MySQL

- `database/mysql/schema.sql`
- `database/mysql/seed.sql`
- `database/mysql/queries.sql`

### MongoDB

- `database/mongo/01_seed.js`
- `database/mongo/02_queries.js`

## How to run (order)

1. Run MySQL schema: `schema.sql`
2. Run MySQL seed: `seed.sql`
3. Run MySQL queries: `queries.sql`
4. Run Mongo seed: `01_seed.js`
5. Run Mongo queries: `02_queries.js`
