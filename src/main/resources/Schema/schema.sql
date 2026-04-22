CREATE TYPE gender_type AS ENUM ('MALE', 'FEMALE');
CREATE TYPE occupation_type AS ENUM (
    'JUNIOR',
    'SENIOR',
    'SECRETARY',
    'TREASURER',
    'VICE_PRESIDENT',
    'PRESIDENT'
);

CREATE TABLE collectivities (
                                id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                location        VARCHAR(255) NOT NULL,

                                president_id    UUID,
                                vice_president_id UUID,
                                treasurer_id    UUID,
                                secretary_id    UUID,

                                created_at      TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE members (
                         id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         first_name      VARCHAR(100) NOT NULL,
                         last_name       VARCHAR(100) NOT NULL,
                         birth_date      DATE NOT NULL,
                         gender          gender_type NOT NULL,
                         address         TEXT NOT NULL,
                         profession      VARCHAR(255) NOT NULL,
                         phone_number    BIGINT NOT NULL,
                         email           VARCHAR(255) NOT NULL UNIQUE,
                         occupation      occupation_type NOT NULL,
                         membership_date DATE NOT NULL DEFAULT CURRENT_DATE,
                         collectivity_id UUID REFERENCES collectivities(id) ON DELETE SET NULL,
                         created_at      TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE member_referees (
                                 member_id   UUID NOT NULL REFERENCES members(id) ON DELETE CASCADE,
                                 referee_id  UUID NOT NULL REFERENCES members(id) ON DELETE CASCADE,
                                 PRIMARY KEY (member_id, referee_id),
                                 CHECK (member_id <> referee_id)   -- un membre ne peut pas se parrainer lui-même
)

ALTER TABLE collectivities
    ADD CONSTRAINT fk_president      FOREIGN KEY (president_id)       REFERENCES members(id) ON DELETE SET NULL,
    ADD CONSTRAINT fk_vice_president FOREIGN KEY (vice_president_id)  REFERENCES members(id) ON DELETE SET NULL,
    ADD CONSTRAINT fk_treasurer      FOREIGN KEY (treasurer_id)       REFERENCES members(id) ON DELETE SET NULL,
    ADD CONSTRAINT fk_secretary      FOREIGN KEY (secretary_id)       REFERENCES members(id) ON DELETE SET NULL;



CREATE INDEX idx_members_collectivity ON members(collectivity_id);
CREATE INDEX idx_members_email        ON members(email);
CREATE INDEX idx_members_occupation   ON members(occupation);
CREATE INDEX idx_member_referees_member ON member_referees(member_id);

CREATE TABLE membership_fee (
                                id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                eligible_from DATE NOT NULL,
                                frequency VARCHAR(20) NOT NULL,
                                amount DOUBLE PRECISION NOT NULL,
                                label VARCHAR(255),
                                status VARCHAR(20) NOT NULL
);

CREATE TABLE member_payment (
                                id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                amount INTEGER NOT NULL,
                                payment_mode VARCHAR(30) NOT NULL,
                                account_credited_id UUID,
                                creation_date DATE NOT NULL DEFAULT CURRENT_DATE,

                                membership_fee_id UUID,
                                member_id UUID,

                                CONSTRAINT fk_payment_member
                                    FOREIGN KEY (member_id) REFERENCES members(id),

                                CONSTRAINT fk_payment_fee
                                    FOREIGN KEY (membership_fee_id) REFERENCES membership_fee(id)
);