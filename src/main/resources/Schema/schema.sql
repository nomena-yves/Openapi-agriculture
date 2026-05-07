-- ============================================================
-- SCHEMA BASE DE DONNÉES - Fédération de collectivités agricoles
-- Version : 0.0.3
-- ============================================================

-- ============================================================
-- NETTOYAGE COMPLET
-- ============================================================

DROP TABLE IF EXISTS collectivity_transactions   CASCADE;
DROP TABLE IF EXISTS member_payments             CASCADE;
DROP TABLE IF EXISTS membership_fees             CASCADE;
DROP TABLE IF EXISTS financial_accounts          CASCADE;
DROP TABLE IF EXISTS member_collectivities       CASCADE;
DROP TABLE IF EXISTS members                     CASCADE;
DROP TABLE IF EXISTS collectivities              CASCADE;

DROP TYPE IF EXISTS gender_type                  CASCADE;
DROP TYPE IF EXISTS occupation_type              CASCADE;
DROP TYPE IF EXISTS frequency_type               CASCADE;
DROP TYPE IF EXISTS activity_status_type         CASCADE;
DROP TYPE IF EXISTS payment_mode_type            CASCADE;
DROP TYPE IF EXISTS account_type                 CASCADE;
DROP TYPE IF EXISTS mobile_banking_service_type  CASCADE;

-- ============================================================
-- TYPES ÉNUMÉRÉS
-- ============================================================

CREATE TYPE gender_type AS ENUM (
    'MALE', 'FEMALE'
);

CREATE TYPE occupation_type AS ENUM (
    'JUNIOR', 'SENIOR', 'SECRETARY', 'TREASURER', 'VICE_PRESIDENT', 'PRESIDENT'
);

CREATE TYPE frequency_type AS ENUM (
    'WEEKLY', 'MONTHLY', 'ANNUALLY', 'PUNCTUALLY'
);

CREATE TYPE activity_status_type AS ENUM (
    'ACTIVE', 'INACTIVE'
);

CREATE TYPE payment_mode_type AS ENUM (
    'CASH', 'MOBILE_BANKING', 'BANK_TRANSFER'
);

CREATE TYPE account_type AS ENUM (
    'CASH', 'MOBILE_BANKING', 'BANK'
);

CREATE TYPE mobile_banking_service_type AS ENUM (
    'AIRTEL_MONEY', 'MVOLA', 'ORANGE_MONEY'
);

-- ============================================================
-- TABLE : collectivities
-- ============================================================

CREATE TABLE collectivities (
                                id              VARCHAR(50)  PRIMARY KEY,
                                number          VARCHAR(50)  UNIQUE,
                                name            VARCHAR(255) UNIQUE,
                                location        VARCHAR(255) NOT NULL,
                                specialization  VARCHAR(100),
                                created_at      TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- ============================================================
-- TABLE : members
-- ============================================================

CREATE TABLE members (
                         id              VARCHAR(50)     PRIMARY KEY,
                         first_name      VARCHAR(100)    NOT NULL,
                         last_name       VARCHAR(100)    NOT NULL,
                         birth_date      DATE            NOT NULL,
                         gender          gender_type     NOT NULL,
                         address         TEXT            NOT NULL,
                         profession      VARCHAR(255)    NOT NULL,
                         phone_number    BIGINT          NOT NULL,
                         email           VARCHAR(255)    NOT NULL UNIQUE,
                         membership_date DATE            NOT NULL DEFAULT CURRENT_DATE,
                         created_at      TIMESTAMP       NOT NULL DEFAULT NOW()
);

-- ============================================================
-- TABLE : member_collectivities (relation membre <-> collectivité)
-- ============================================================

CREATE TABLE member_collectivities (
                                       member_id       VARCHAR(50)     NOT NULL REFERENCES members(id)       ON DELETE CASCADE,
                                       collectivity_id VARCHAR(50)     NOT NULL REFERENCES collectivities(id) ON DELETE CASCADE,
                                       occupation      occupation_type NOT NULL DEFAULT 'JUNIOR',
                                       PRIMARY KEY (member_id, collectivity_id)
);

-- ============================================================
-- TABLE : member_referees
-- ============================================================

CREATE TABLE member_referees (
                                 member_id  VARCHAR(50) NOT NULL REFERENCES members(id) ON DELETE CASCADE,
                                 referee_id VARCHAR(50) NOT NULL REFERENCES members(id) ON DELETE CASCADE,
                                 PRIMARY KEY (member_id, referee_id),
                                 CONSTRAINT chk_no_self_referee CHECK (member_id <> referee_id)
);

-- ============================================================
-- TABLE : financial_accounts
-- ============================================================

CREATE TABLE financial_accounts (
                                    id              VARCHAR(50)                 PRIMARY KEY,
                                    account_type    account_type                NOT NULL,
                                    amount          NUMERIC(15,2)               NOT NULL DEFAULT 0,
                                    collectivity_id VARCHAR(50)                 REFERENCES collectivities(id) ON DELETE CASCADE,
    -- Mobile banking
                                    holder_name     VARCHAR(255),
                                    mobile_service  mobile_banking_service_type,
                                    mobile_number   BIGINT,
    -- Bank
                                    bank_name       VARCHAR(50),
                                    bank_code       INTEGER,
                                    bank_branch_code    INTEGER,
                                    bank_account_number BIGINT,
                                    bank_account_key    INTEGER,
                                    created_at      TIMESTAMP                   NOT NULL DEFAULT NOW()
);

-- Une seule caisse par collectivité
CREATE UNIQUE INDEX idx_one_cash_per_collectivity
    ON financial_accounts (collectivity_id)
    WHERE account_type = 'CASH';

-- ============================================================
-- TABLE : membership_fees
-- ============================================================

CREATE TABLE membership_fees (
                                 id              VARCHAR(50)          PRIMARY KEY,
                                 collectivity_id VARCHAR(50)          NOT NULL REFERENCES collectivities(id) ON DELETE CASCADE,
                                 eligible_from   DATE,
                                 frequency       frequency_type       NOT NULL,
                                 amount          NUMERIC(15,2)        NOT NULL CHECK (amount > 0),
                                 label           VARCHAR(255),
                                 status          activity_status_type NOT NULL DEFAULT 'ACTIVE',
                                 created_at      TIMESTAMP            NOT NULL DEFAULT NOW()
);

-- ============================================================
-- TABLE : member_payments
-- ============================================================

CREATE TABLE member_payments (
                                 id                VARCHAR(50)       PRIMARY KEY,
                                 member_id         VARCHAR(50)       NOT NULL REFERENCES members(id)           ON DELETE CASCADE,
                                 membership_fee_id VARCHAR(50)       REFERENCES membership_fees(id),
                                 account_id        VARCHAR(50)       NOT NULL REFERENCES financial_accounts(id),
                                 amount            INTEGER           NOT NULL CHECK (amount > 0),
                                 payment_mode      payment_mode_type NOT NULL,
                                 creation_date     DATE              NOT NULL DEFAULT CURRENT_DATE,
                                 created_at        TIMESTAMP         NOT NULL DEFAULT NOW()
);

-- ============================================================
-- TABLE : collectivity_transactions
-- ============================================================

CREATE TABLE collectivity_transactions (
                                           id              VARCHAR(50)       PRIMARY KEY,
                                           collectivity_id VARCHAR(50)       NOT NULL REFERENCES collectivities(id) ON DELETE CASCADE,
                                           member_id       VARCHAR(50)       NOT NULL REFERENCES members(id),
                                           account_id      VARCHAR(50)       NOT NULL REFERENCES financial_accounts(id),
                                           amount          NUMERIC(15,2)     NOT NULL,
                                           payment_mode    payment_mode_type NOT NULL,
                                           creation_date   DATE              NOT NULL DEFAULT CURRENT_DATE,
                                           created_at      TIMESTAMP         NOT NULL DEFAULT NOW()
);

-- ============================================================
-- INDEX
-- ============================================================

CREATE INDEX idx_member_collectivities_member ON member_collectivities(member_id);
CREATE INDEX idx_member_collectivities_coll   ON member_collectivities(collectivity_id);
CREATE INDEX idx_members_email                ON members(email);
CREATE INDEX idx_referees_member              ON member_referees(member_id);
CREATE INDEX idx_membership_fees_coll         ON membership_fees(collectivity_id);
CREATE INDEX idx_membership_fees_status       ON membership_fees(status);
CREATE INDEX idx_member_payments_member       ON member_payments(member_id);
CREATE INDEX idx_transactions_coll            ON collectivity_transactions(collectivity_id);
CREATE INDEX idx_transactions_date            ON collectivity_transactions(creation_date);

-- ============================================================
-- INSERTIONS : COLLECTIVITÉS
-- ============================================================

INSERT INTO collectivities (id, number, name, location, specialization) VALUES
                                                                            ('col-1', '1', 'Mpanorina',       'Ambatondrazaka', 'Riziculture'),
                                                                            ('col-2', '2', 'Dobo voalohany',  'Ambatondrazaka', 'Pisciculture'),
                                                                            ('col-3', '3', 'Tantely mamy',    'Brickaville',    'Apiculture');

-- ============================================================
-- INSERTIONS : MEMBRES
-- ============================================================

-- Membres communs aux collectivités 1 et 2
INSERT INTO members (id, first_name, last_name, birth_date, gender, address, profession, phone_number, email, membership_date) VALUES
                                                                                                                                   ('C1-M1', 'Prénom membre 1',  'Nom membre 1',  '1980-02-01', 'MALE',   'Lot II V M Ambato.',  'Riziculteur', 341234567,  'member.1@fed-agri.mg',  '2024-01-01'),
                                                                                                                                   ('C1-M2', 'Prénom membre 2',  'Nom membre 2',  '1982-03-05', 'MALE',   'Lot II F Ambato.',    'Agriculteur', 321234567,  'member.2@fed-agri.mg',  '2024-01-01'),
                                                                                                                                   ('C1-M3', 'Prénom membre 3',  'Nom membre 3',  '1992-03-10', 'MALE',   'Lot II J Ambato.',    'Collecteur',  331234567,  'member.3@fed-agri.mg',  '2024-01-01'),
                                                                                                                                   ('C1-M4', 'Prénom membre 4',  'Nom membre 4',  '1988-05-22', 'FEMALE', 'Lot A K 50 Ambato.',  'Distributeur',381234567,  'member.4@fed-agri.mg',  '2024-01-01'),
                                                                                                                                   ('C1-M5', 'Prénom membre 5',  'Nom membre 5',  '1999-08-21', 'MALE',   'Lot UV 80 Ambato.',   'Riziculteur', 373434567,  'member.5@fed-agri.mg',  '2024-01-01'),
                                                                                                                                   ('C1-M6', 'Prénom membre 6',  'Nom membre 6',  '1998-08-22', 'FEMALE', 'Lot UV 6 Ambato.',    'Riziculteur', 372234567,  'member.6@fed-agri.mg',  '2024-01-01'),
                                                                                                                                   ('C1-M7', 'Prénom membre 7',  'Nom membre 7',  '1998-01-31', 'MALE',   'Lot UV 7 Ambato.',    'Riziculteur', 374234567,  'member.7@fed-agri.mg',  '2024-01-01'),
                                                                                                                                   ('C1-M8', 'Prénom membre 8',  'Nom membre 8',  '1975-08-20', 'MALE',   'Lot UV 8 Ambato.',    'Riziculteur', 370234567,  'member.8@fed-agri.mg',  '2024-01-01');

-- Membres spécifiques à la collectivité 3
INSERT INTO members (id, first_name, last_name, birth_date, gender, address, profession, phone_number, email, membership_date) VALUES
                                                                                                                                   ('C3-M1', 'Prénom membre 9',  'Nom membre 9',  '1988-01-02', 'MALE',   'Lot 33 J Antsirabe',  'Apiculteur',  34034567,   'member.9@fed-agri.mg',  '2024-01-01'),
                                                                                                                                   ('C3-M2', 'Prénom membre 10', 'Nom membre 10', '1982-03-05', 'MALE',   'Lot 2 J Antsirabe',   'Agriculteur', 338634567,  'member.10@fed-agri.mg', '2024-01-01'),
                                                                                                                                   ('C3-M3', 'Prénom membre 11', 'Nom membre 11', '1992-03-12', 'MALE',   'Lot 8 KM Antsirabe',  'Collecteur',  338234567,  'member.11@fed-agri.mg', '2024-01-01'),
                                                                                                                                   ('C3-M4', 'Prénom membre 12', 'Nom membre 12', '1988-05-10', 'FEMALE', 'Lot A K 50 Antsirabe','Distributeur',382334567,  'member.12@fed-agri.mg', '2024-01-01'),
                                                                                                                                   ('C3-M5', 'Prénom membre 13', 'Nom membre 13', '1999-08-11', 'MALE',   'Lot UV 80 Antsirabe', 'Apiculteur',  373365567,  'member.13@fed-agri.mg', '2024-01-01'),
                                                                                                                                   ('C3-M6', 'Prénom membre 14', 'Nom membre 14', '1998-08-09', 'FEMALE', 'Lot UV 6 Antsirabe',  'Apiculteur',  378234567,  'member.14@fed-agri.mg', '2024-01-01'),
                                                                                                                                   ('C3-M7', 'Prénom membre 15', 'Nom membre 15', '1998-01-13', 'MALE',   'Lot UV 7 Antsirabe',  'Apiculteur',  374914567,  'member.15@fed-agri.mg', '2024-01-01'),
                                                                                                                                   ('C3-M8', 'Prénom membre 16', 'Nom membre 16', '1975-08-02', 'MALE',   'Lot UV 8 Antsirabe',  'Apiculteur',  370634567,  'member.16@fed-agri.mg', '2024-01-01');

-- ============================================================
-- INSERTIONS : RELATIONS MEMBRE <-> COLLECTIVITÉ
-- ============================================================

-- Collectivité 1
INSERT INTO member_collectivities (member_id, collectivity_id, occupation) VALUES
                                                                               ('C1-M1', 'col-1', 'PRESIDENT'),
                                                                               ('C1-M2', 'col-1', 'VICE_PRESIDENT'),
                                                                               ('C1-M3', 'col-1', 'SECRETARY'),
                                                                               ('C1-M4', 'col-1', 'TREASURER'),
                                                                               ('C1-M5', 'col-1', 'SENIOR'),
                                                                               ('C1-M6', 'col-1', 'SENIOR'),
                                                                               ('C1-M7', 'col-1', 'SENIOR'),
                                                                               ('C1-M8', 'col-1', 'SENIOR');

-- Collectivité 2 (mêmes membres mais occupations différentes)
INSERT INTO member_collectivities (member_id, collectivity_id, occupation) VALUES
                                                                               ('C1-M1', 'col-2', 'SENIOR'),
                                                                               ('C1-M2', 'col-2', 'SENIOR'),
                                                                               ('C1-M3', 'col-2', 'SENIOR'),
                                                                               ('C1-M4', 'col-2', 'SENIOR'),
                                                                               ('C1-M5', 'col-2', 'PRESIDENT'),
                                                                               ('C1-M6', 'col-2', 'VICE_PRESIDENT'),
                                                                               ('C1-M7', 'col-2', 'SECRETARY'),
                                                                               ('C1-M8', 'col-2', 'TREASURER');

-- Collectivité 3
INSERT INTO member_collectivities (member_id, collectivity_id, occupation) VALUES
                                                                               ('C3-M1', 'col-3', 'PRESIDENT'),
                                                                               ('C3-M2', 'col-3', 'VICE_PRESIDENT'),
                                                                               ('C3-M3', 'col-3', 'SECRETARY'),
                                                                               ('C3-M4', 'col-3', 'TREASURER'),
                                                                               ('C3-M5', 'col-3', 'SENIOR'),
                                                                               ('C3-M6', 'col-3', 'SENIOR'),
                                                                               ('C3-M7', 'col-3', 'SENIOR'),
                                                                               ('C3-M8', 'col-3', 'SENIOR');

-- ============================================================
-- INSERTIONS : PARRAINAGES (member_referees)
-- ============================================================

-- Collectivité 1
INSERT INTO member_referees (member_id, referee_id) VALUES
                                                         ('C1-M3', 'C1-M2'),
                                                        ('C1-M4', 'C1-M1'), ('C1-M4', 'C1-M2'),
                                                        ('C1-M5', 'C1-M1'), ('C1-M5', 'C1-M2'),
                                                        ('C1-M6', 'C1-M1'), ('C1-M6', 'C1-M2'),
                                                        ('C1-M7', 'C1-M1'), ('C1-M7', 'C1-M2'),
                                                        ('C1-M8', 'C1-M6'), ('C1-M8', 'C1-M7');

-- Collectivité 3
INSERT INTO member_referees (member_id, referee_id) VALUES
                                                        ('C3-M3', 'C3-M1'), ('C3-M3', 'C3-M2'),
                                                        ('C3-M4', 'C3-M1'), ('C3-M4', 'C3-M2'),
                                                        ('C3-M5', 'C3-M1'), ('C3-M5', 'C3-M2'),
                                                        ('C3-M6', 'C3-M1'), ('C3-M6', 'C3-M2'),
                                                        ('C3-M7', 'C3-M1'), ('C3-M7', 'C3-M2'),
                                                        ('C3-M8', 'C3-M1'), ('C3-M8', 'C3-M2');

-- ============================================================
-- INSERTIONS : COMPTES FINANCIERS
-- ============================================================

INSERT INTO financial_accounts (id, account_type, amount, collectivity_id, holder_name, mobile_service, mobile_number) VALUES
                                                                                                                           ('C1-A-CASH',     'CASH',          0, 'col-1', NULL,            NULL,           NULL),
                                                                                                                           ('C1-A-MOBILE-1', 'MOBILE_BANKING',0, 'col-1', 'Mpanorina',     'ORANGE_MONEY', 370489612),
                                                                                                                           ('C2-A-CASH',     'CASH',          0, 'col-2', NULL,            NULL,           NULL),
                                                                                                                           ('C2-A-MOBILE-1', 'MOBILE_BANKING',0, 'col-2', 'Dobo voalohany','ORANGE_MONEY', 320489612),
                                                                                                                           ('C3-A-CASH',     'CASH',          0, 'col-3', NULL,            NULL,           NULL);

-- ============================================================
-- INSERTIONS : COTISATIONS
-- ============================================================

INSERT INTO membership_fees (id, collectivity_id, eligible_from, frequency, amount, label, status) VALUES
                                                                                                       ('cot-1', 'col-1', '2026-01-01', 'ANNUALLY', 100000, 'Cotisation annuelle', 'ACTIVE'),
                                                                                                       ('cot-2', 'col-2', '2026-01-01', 'ANNUALLY', 100000, 'Cotisation annuelle', 'ACTIVE'),
                                                                                                       ('cot-3', 'col-3', '2026-01-01', 'ANNUALLY',  50000, 'Cotisation annuelle', 'ACTIVE');

-- ============================================================
-- INSERTIONS : PAIEMENTS (member_payments)
-- ============================================================

-- Collectivité 1
INSERT INTO member_payments (id, member_id, membership_fee_id, account_id, amount, payment_mode, creation_date) VALUES
                                                                                                                    ('pay-c1-m1', 'C1-M1', 'cot-1', 'C1-A-CASH', 100000, 'CASH', '2026-01-01'),
                                                                                                                    ('pay-c1-m2', 'C1-M2', 'cot-1', 'C1-A-CASH', 100000, 'CASH', '2026-01-01'),
                                                                                                                    ('pay-c1-m3', 'C1-M3', 'cot-1', 'C1-A-CASH', 100000, 'CASH', '2026-01-01'),
                                                                                                                    ('pay-c1-m4', 'C1-M4', 'cot-1', 'C1-A-CASH', 100000, 'CASH', '2026-01-01'),
                                                                                                                    ('pay-c1-m5', 'C1-M5', 'cot-1', 'C1-A-CASH', 100000, 'CASH', '2026-01-01'),
                                                                                                                    ('pay-c1-m6', 'C1-M6', 'cot-1', 'C1-A-CASH', 100000, 'CASH', '2026-01-01'),
                                                                                                                    ('pay-c1-m7', 'C1-M7', 'cot-1', 'C1-A-CASH',  60000, 'CASH', '2026-01-01'),
                                                                                                                    ('pay-c1-m8', 'C1-M8', 'cot-1', 'C1-A-CASH',  90000, 'CASH', '2026-01-01');

-- Collectivité 2
INSERT INTO member_payments (id, member_id, membership_fee_id, account_id, amount, payment_mode, creation_date) VALUES
                                                                                                                    ('pay-c2-m1', 'C1-M1', 'cot-2', 'C2-A-CASH',      60000, 'CASH',           '2026-01-01'),
                                                                                                                    ('pay-c2-m2', 'C1-M2', 'cot-2', 'C2-A-CASH',      90000, 'CASH',           '2026-01-01'),
                                                                                                                    ('pay-c2-m3', 'C1-M3', 'cot-2', 'C2-A-CASH',     100000, 'CASH',           '2026-01-01'),
                                                                                                                    ('pay-c2-m4', 'C1-M4', 'cot-2', 'C2-A-CASH',     100000, 'CASH',           '2026-01-01'),
                                                                                                                    ('pay-c2-m5', 'C1-M5', 'cot-2', 'C2-A-CASH',     100000, 'CASH',           '2026-01-01'),
                                                                                                                    ('pay-c2-m6', 'C1-M6', 'cot-2', 'C2-A-CASH',     100000, 'CASH',           '2026-01-01'),
                                                                                                                    ('pay-c2-m7', 'C1-M7', 'cot-2', 'C2-A-MOBILE-1',  40000, 'MOBILE_BANKING', '2026-01-01'),
                                                                                                                    ('pay-c2-m8', 'C1-M8', 'cot-2', 'C2-A-MOBILE-1',  60000, 'MOBILE_BANKING', '2026-01-01');

-- ============================================================
-- INSERTIONS : TRANSACTIONS (collectivity_transactions)
-- ============================================================

-- Collectivité 1
INSERT INTO collectivity_transactions (id, collectivity_id, member_id, account_id, amount, payment_mode, creation_date) VALUES
                                                                                                                            ('tx-c1-m1', 'col-1', 'C1-M1', 'C1-A-CASH', 100000, 'CASH', '2026-01-01'),
                                                                                                                            ('tx-c1-m2', 'col-1', 'C1-M2', 'C1-A-CASH', 100000, 'CASH', '2026-01-01'),
                                                                                                                            ('tx-c1-m3', 'col-1', 'C1-M3', 'C1-A-CASH', 100000, 'CASH', '2026-01-01'),
                                                                                                                            ('tx-c1-m4', 'col-1', 'C1-M4', 'C1-A-CASH', 100000, 'CASH', '2026-01-01'),
                                                                                                                            ('tx-c1-m5', 'col-1', 'C1-M5', 'C1-A-CASH', 100000, 'CASH', '2026-01-01'),
                                                                                                                            ('tx-c1-m6', 'col-1', 'C1-M6', 'C1-A-CASH', 100000, 'CASH', '2026-01-01'),
                                                                                                                            ('tx-c1-m7', 'col-1', 'C1-M7', 'C1-A-CASH',  60000, 'CASH', '2026-01-01'),
                                                                                                                            ('tx-c1-m8', 'col-1', 'C1-M8', 'C1-A-CASH',  90000, 'CASH', '2026-01-01');

-- Collectivité 2
INSERT INTO collectivity_transactions (id, collectivity_id, member_id, account_id, amount, payment_mode, creation_date) VALUES
                                                                                                                            ('tx-c2-m1', 'col-2', 'C1-M1', 'C2-A-CASH',      60000, 'CASH',           '2026-01-01'),
                                                                                                                            ('tx-c2-m2', 'col-2', 'C1-M2', 'C2-A-CASH',      90000, 'CASH',           '2026-01-01'),
                                                                                                                            ('tx-c2-m3', 'col-2', 'C1-M3', 'C2-A-CASH',     100000, 'CASH',           '2026-01-01'),
                                                                                                                            ('tx-c2-m4', 'col-2', 'C1-M4', 'C2-A-CASH',     100000, 'CASH',           '2026-01-01'),
                                                                                                                            ('tx-c2-m5', 'col-2', 'C1-M5', 'C2-A-CASH',     100000, 'CASH',           '2026-01-01'),
                                                                                                                            ('tx-c2-m6', 'col-2', 'C1-M6', 'C2-A-CASH',     100000, 'CASH',           '2026-01-01'),
                                                                                                                            ('tx-c2-m7', 'col-2', 'C1-M7', 'C2-A-MOBILE-1',  40000, 'MOBILE_BANKING', '2026-01-01'),
                                                                                                                            ('tx-c2-m8', 'col-2', 'C1-M8', 'C2-A-MOBILE-1',  60000, 'MOBILE_BANKING', '2026-01-01');

-- Collectivité 3 : aucun paiement ni transaction

-- ============================================================
-- MISE À JOUR DES SOLDES DES COMPTES
-- ============================================================

UPDATE financial_accounts SET amount = 750000 WHERE id = 'C1-A-CASH';     -- 100k x6 + 60k + 90k
UPDATE financial_accounts SET amount = 650000 WHERE id = 'C2-A-CASH';     -- 100k x5 + 90k + 60k
UPDATE financial_accounts SET amount = 100000 WHERE id = 'C2-A-MOBILE-1'; -- 40k + 60k