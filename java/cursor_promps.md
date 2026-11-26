## Prompt 1: Prepare database
in the directory java there is a skeleton of the java app. For this one prpare the Postgress java testcontainer with the folowing table structure: Database Setup:
Store all time deposit plans in a database.
Define the following tables:
timeDeposits:
id: Integer (primary key)
planType: String (required)
days: Integer (required)
balance: Decimal (required)
withdrawals:
id: Integer (primary key)
timeDepositId: Integer (foreign key, required)
amount: Decimal (required)
date: Date (required)


## Prompt 2: prepare docker environment
prepare docker environment to run the TimeDepositDatabaseTest.java
add the docker compose.yaml to the project

## Prompt 3: open API contract
Prepare open API contract as described below. as the reference use the datatypes from DB schema. Add the OpenApi yaml to the project
API Endpoints:
Create a RESTful API endpoint to update the balances of all time deposits in the database.
Create a RESTful API endpoint to retrieve all time deposits.
The GET endpoint should return a list of all time deposits with the following schema:
id
planType
balance
days
withdrawals


## Prompt 4: implement get endpoint
Using hexagonal architecture implement the endpoint /time-deposits from openapi.yaml. Place the DTOs and Entities in the proper valid accordingly to hexagonal architecture packages.

## Prompt 5: prepare flyway with sample time deposits
Prepare flyway configuration with a few sample time Deposist and withdraws. Make sure that application starts. Create integration test to verify that /time-deposits endpoints returns valid data

## Prompt 6: /time-deposits/balance-updates endpoint implementation
Following the hexagonal architecture implement /time-deposits/balance-updates endpoint. The monthly calculation is as below:
Interest Calculation:
Implement logic to calculate monthly interest based on the plan type:
Basic Plan: 1% interest
Student Plan: 3% interest (no interest after 1 year)
Premium Plan: 5% interest (interest starts after 45 days)
No interest is applied for the first 30 days for any existing plans.
Do not change the TimeDepositCalculator

## Prompt 7: swagger
Add Open API documentation for swagger for TimeDepositController

