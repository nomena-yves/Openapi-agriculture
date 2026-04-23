-- ============================================================
-- SCHEMA BASE DE DONNÉES - Fédération de collectivités agricoles
-- Version : 0.0.3
-- ============================================================

-- Nettoyage
DROP TABLE IF EXISTS collectivity_transactions   CASCADE;
DROP TABLE IF EXISTS member_payments             CASCADE;
DROP TABLE IF EXISTS membership_fees             CASCADE;
DROP TABLE IF EXISTS financial_accounts          CASCADE;
DROP TABLE IF EXISTS member_referees             CASCADE;
DROP TABLE IF EXISTS members                     CASCADE;
DROP TABLE IF EXISTS collectivities              CASCADE;

DROP TYPE IF EXISTS gender_type                  CASCADE;
DROP TYPE IF EXISTS occupation_type              CASCADE;
DROP TYPE IF EXISTS frequency_type               CASCADE;
DROP TYPE IF EXISTS activity_status_type         CASCADE;
DROP TYPE IF EXISTS payment_mode_type            CASCADE;
DROP TYPE IF EXISTS account_type                 CASCADE;
DROP TYPE IF EXISTS mobile_banking_service_type  CASCADE;
DROP TYPE IF EXISTS bank_type                    CASCADE;

-- ============================================================
-- TYPES ÉNUMÉRÉS
-- ============================================================

CREATE TYPE gender_type AS ENUM (
    'MALE',
    'FEMALE'
);

CREATE TYPE occupation_type AS ENUM (
    'JUNIOR',
    'SENIOR',
    'SECRETARY',
    'TREASURER',
    'VICE_PRESIDENT',
    'PRESIDENT'
);

CREATE TYPE frequency_type AS ENUM (
    'WEEKLY',
    'MONTHLY',
    'ANNUALLY',
    'PUNCTUALLY'
);

CREATE TYPE activity_status_type AS ENUM (
    'ACTIVE',
    'INACTIVE'
);

CREATE TYPE payment_mode_type AS ENUM (
    'CASH',
    'MOBILE_BANKING',
    'BANK_TRANSFER'
);

CREATE TYPE account_type AS ENUM (
    'CASH',
    'MOBILE_BANKING',
    'BANK'
);

CREATE TYPE mobile_banking_service_type AS ENUM (
    'AIRTEL_MONEY',
    'MVOLA',
    'ORANGE_MONEY'
);

CREATE TYPE bank_type AS ENUM (
    'BRED',
    'MCB',
    'BMOI',
    'BOA',
    'BGFI',
    'AFG',
    'ACCES_BAQUE',
    'BAOBAB',
    'SIPEM'
);

-- ============================================================
-- TABLE : collectivities
-- ============================================================

CREATE TABLE collectivities (
                                id                UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
                                number            VARCHAR(50)  UNIQUE,
                                name              VARCHAR(255) UNIQUE,
                                location          VARCHAR(255) NOT NULL,
                                president_id      UUID,
                                vice_president_id UUID,
                                treasurer_id      UUID,
                                secretary_id      UUID,
                                created_at        TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- ============================================================
-- TABLE : members
-- ============================================================

CREATE TABLE members (
                         id              UUID            PRIMARY KEY DEFAULT gen_random_uuid(),
                         first_name      VARCHAR(100)    NOT NULL,
                         last_name       VARCHAR(100)    NOT NULL,
                         birth_date      DATE            NOT NULL,
                         gender          gender_type     NOT NULL,
                         address         TEXT            NOT NULL,
                         profession      VARCHAR(255)    NOT NULL,
                         phone_number    BIGINT          NOT NULL,
                         email           VARCHAR(255)    NOT NULL UNIQUE,
                         occupation      occupation_type NOT NULL DEFAULT 'JUNIOR',
                         membership_date DATE            NOT NULL DEFAULT CURRENT_DATE,
                         collectivity_id UUID            REFERENCES collectivities(id) ON DELETE SET NULL,
                         created_at      TIMESTAMP       NOT NULL DEFAULT NOW()
);

-- FK structure collectivité -> members
ALTER TABLE collectivities
    ADD CONSTRAINT fk_president
        FOREIGN KEY (president_id)      REFERENCES members(id) ON DELETE SET NULL,
    ADD CONSTRAINT fk_vice_president
        FOREIGN KEY (vice_president_id) REFERENCES members(id) ON DELETE SET NULL,
    ADD CONSTRAINT fk_treasurer
        FOREIGN KEY (treasurer_id)      REFERENCES members(id) ON DELETE SET NULL,
    ADD CONSTRAINT fk_secretary
        FOREIGN KEY (secretary_id)      REFERENCES members(id) ON DELETE SET NULL;

-- ============================================================
-- TABLE : member_referees
-- ============================================================

CREATE TABLE member_referees (
                                 member_id  UUID        NOT NULL REFERENCES members(id) ON DELETE CASCADE,
                                 referee_id UUID        NOT NULL REFERENCES members(id) ON DELETE CASCADE,
                                 relation   VARCHAR(100),
                                 PRIMARY KEY (member_id, referee_id),
                                 CONSTRAINT chk_no_self_referee CHECK (member_id <> referee_id)
);

-- ============================================================
-- TABLE : financial_accounts
-- ============================================================

CREATE TABLE financial_accounts (
                                    id                  UUID                        PRIMARY KEY DEFAULT gen_random_uuid(),
                                    account_type        account_type                NOT NULL,
                                    amount              NUMERIC(15,2)               NOT NULL DEFAULT 0,
                                    collectivity_id     UUID                        REFERENCES collectivities(id) ON DELETE CASCADE,
    -- Champs MobileBankingAccount
                                    holder_name         VARCHAR(255),
                                    mobile_service      mobile_banking_service_type,
                                    mobile_number       BIGINT,
    -- Champs BankAccount
                                    bank_name           bank_type,
                                    bank_code           INTEGER,
                                    bank_branch_code    INTEGER,
                                    bank_account_number BIGINT,
                                    bank_account_key    INTEGER,
                                    created_at          TIMESTAMP                   NOT NULL DEFAULT NOW()
);

-- Une seule caisse par collectivité
CREATE UNIQUE INDEX idx_one_cash_per_collectivity
    ON financial_accounts (collectivity_id)
    WHERE account_type = 'CASH';

-- ============================================================
-- TABLE : membership_fees
-- ============================================================

CREATE TABLE membership_fees (
                                 id              UUID                 PRIMARY KEY DEFAULT gen_random_uuid(),
                                 collectivity_id UUID                 NOT NULL REFERENCES collectivities(id) ON DELETE CASCADE,
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
                                 id                UUID              PRIMARY KEY DEFAULT gen_random_uuid(),
                                 member_id         UUID              NOT NULL REFERENCES members(id) ON DELETE CASCADE,
                                 membership_fee_id UUID              NOT NULL REFERENCES membership_fees(id),
                                 account_id        UUID              NOT NULL REFERENCES financial_accounts(id),
                                 amount            INTEGER           NOT NULL CHECK (amount > 0),
                                 payment_mode      payment_mode_type NOT NULL,
                                 creation_date     DATE              NOT NULL DEFAULT CURRENT_DATE,
                                 created_at        TIMESTAMP         NOT NULL DEFAULT NOW()
);

-- ============================================================
-- TABLE : collectivity_transactions
-- ============================================================

CREATE TABLE collectivity_transactions (
                                           id              UUID              PRIMARY KEY DEFAULT gen_random_uuid(),
                                           collectivity_id UUID              NOT NULL REFERENCES collectivities(id) ON DELETE CASCADE,
                                           member_id       UUID              NOT NULL REFERENCES members(id),
                                           account_id      UUID              NOT NULL REFERENCES financial_accounts(id),
                                           amount          NUMERIC(15,2)     NOT NULL,
                                           payment_mode    payment_mode_type NOT NULL,
                                           creation_date   DATE              NOT NULL DEFAULT CURRENT_DATE,
                                           created_at      TIMESTAMP         NOT NULL DEFAULT NOW()
);

-- ============================================================
-- INDEX
-- ============================================================

CREATE INDEX idx_members_collectivity     ON members(collectivity_id);
CREATE INDEX idx_members_email            ON members(email);
CREATE INDEX idx_members_occupation       ON members(occupation);
CREATE INDEX idx_members_membership_date  ON members(membership_date);
CREATE INDEX idx_referees_member          ON member_referees(member_id);
CREATE INDEX idx_membership_fees_coll     ON membership_fees(collectivity_id);
CREATE INDEX idx_membership_fees_status   ON membership_fees(status);
CREATE INDEX idx_member_payments_member   ON member_payments(member_id);
CREATE INDEX idx_member_payments_fee      ON member_payments(membership_fee_id);
CREATE INDEX idx_transactions_coll        ON collectivity_transactions(collectivity_id);
CREATE INDEX idx_transactions_date        ON collectivity_transactions(creation_date);