-- ============================================================
-- TYPES (SAFE)
-- ============================================================

DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'gender_type') THEN
            CREATE TYPE gender_type AS ENUM ('MALE','FEMALE');
        END IF;

        IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'occupation_type') THEN
            CREATE TYPE occupation_type AS ENUM (
                'JUNIOR','SENIOR','SECRETARY','TREASURER','VICE_PRESIDENT','PRESIDENT'
                );
        END IF;

        IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'frequency_type') THEN
            CREATE TYPE frequency_type AS ENUM (
                'WEEKLY','MONTHLY','ANNUALLY','PUNCTUALLY'
                );
        END IF;

        IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'activity_status_type') THEN
            CREATE TYPE activity_status_type AS ENUM ('ACTIVE','INACTIVE');
        END IF;

        IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'payment_mode_type') THEN
            CREATE TYPE payment_mode_type AS ENUM ('CASH','MOBILE_BANKING','BANK_TRANSFER');
        END IF;

        IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'account_type') THEN
            CREATE TYPE account_type AS ENUM ('CASH','MOBILE_BANKING','BANK');
        END IF;

        IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'mobile_banking_service_type') THEN
            CREATE TYPE mobile_banking_service_type AS ENUM ('AIRTEL_MONEY','MVOLA','ORANGE_MONEY');
        END IF;
    END$$;

-- ============================================================
-- TABLES (SAFE)
-- ============================================================

CREATE TABLE IF NOT EXISTS collectivities (
                                              id VARCHAR(50) PRIMARY KEY,
                                              number VARCHAR(50),
                                              name VARCHAR(255),
                                              location VARCHAR(255),
                                              specialization VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS members (
                                       id VARCHAR(50) PRIMARY KEY,
                                       first_name VARCHAR(100),
                                       last_name VARCHAR(100),
                                       birth_date DATE,
                                       gender gender_type,
                                       address TEXT,
                                       profession VARCHAR(255),
                                       phone_number BIGINT,
                                       email VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS member_collectivities (
                                                     member_id VARCHAR(50),
                                                     collectivity_id VARCHAR(50),
                                                     occupation occupation_type,
                                                     PRIMARY KEY(member_id, collectivity_id)
);

CREATE TABLE IF NOT EXISTS financial_accounts (
                                                  id VARCHAR(50) PRIMARY KEY,
                                                  account_type account_type,
                                                  amount NUMERIC(15,2),
                                                  collectivity_id VARCHAR(50),
                                                  mobile_service mobile_banking_service_type,
                                                  mobile_number BIGINT
);

CREATE TABLE IF NOT EXISTS membership_fees (
                                               id VARCHAR(50) PRIMARY KEY,
                                               collectivity_id VARCHAR(50),
                                               frequency frequency_type,
                                               amount NUMERIC(15,2)
);

CREATE TABLE IF NOT EXISTS member_payments (
                                               id VARCHAR(50) PRIMARY KEY,
                                               member_id VARCHAR(50),
                                               account_id VARCHAR(50),
                                               amount INTEGER,
                                               payment_mode payment_mode_type
);

CREATE TABLE IF NOT EXISTS collectivity_transactions (
                                                         id VARCHAR(50) PRIMARY KEY,
                                                         collectivity_id VARCHAR(50),
                                                         member_id VARCHAR(50),
                                                         account_id VARCHAR(50),
                                                         amount NUMERIC(15,2),
                                                         payment_mode payment_mode_type
);

-- ============================================================
-- INSERT SAFE (évite doublons)
-- ============================================================

-- Collectivities
INSERT INTO collectivities (id, number, name, location, specialization)
VALUES
    ('col-1','1','Mpanorina','Ambatondrazaka','Riziculture'),
    ('col-2','2','Dobo voalohany','Ambatondrazaka','Pisciculture'),
    ('col-3','3','Tantely mamy','Brickaville','Apiculture')
ON CONFLICT (id) DO NOTHING;

-- Members
INSERT INTO members (id, first_name, last_name, birth_date, gender, address, profession, phone_number, email)
VALUES
    ('C1-M1','Prénom1','Nom1','1980-02-01','MALE','Ambato','Riziculteur',341234567,'m1@mail'),
    ('C1-M2','Prénom2','Nom2','1982-03-05','MALE','Ambato','Agriculteur',321234567,'m2@mail')
ON CONFLICT (id) DO NOTHING;

-- Relations
INSERT INTO member_collectivities VALUES
                                      ('C1-M1','col-1','PRESIDENT'),
                                      ('C1-M2','col-1','VICE_PRESIDENT')
ON CONFLICT DO NOTHING;