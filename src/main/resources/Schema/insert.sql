INSERT INTO collectivities (id, number, name, location, specialization) VALUES
                                                                            ('col-1', 1, 'Mpanorina', 'Ambatondrazaka', 'Riziculture'),
                                                                            ('col-2', 2, 'Dobo voalohany', 'Ambatondrazaka', 'Pisciculture'),
                                                                            ('col-3', 3, 'Tantely mamy', 'Brickaville', 'Apiculture');

INSERT INTO members (id, last_name, first_name, birth_date, gender, address, profession, phone_number, email, profession, ) VALUES
                                                                                                                                  ('C1-M1', 'Nom membre 1', 'Prénom membre 1', '1980-02-01', 'M', 'Lot II V M Ambato', 'Riziculteur', '0341234567', 'member.1@fed-agri.mg', 'PRESIDENT', 'col-1'),
                                                                                                                                  ('C1-M2', 'Nom membre 2', 'Prénom membre 2', '1982-03-05', 'M', 'Lot II F Ambato', 'Agriculteur', '0321234567', 'member.2@fed-agri.mg', 'VICE_PRESIDENT', 'col-1'),
                                                                                                                                  ('C1-M3', 'Nom membre 3', 'Prénom membre 3', '1992-03-10', 'M', 'Lot II J Ambato', 'Collecteur', '0331234567', 'member.3@fed-agri.mg', 'SECRETARY', 'col-1'),
                                                                                                                                  ('C1-M4', 'Nom membre 4', 'Prénom membre 4', '1988-05-22', 'F', 'Lot A K 50 Ambato', 'Distributeur', '0381234567', 'member.4@fed-agri.mg', 'TREASURER', 'col-1'),
                                                                                                                                  ('C1-M5', 'Nom membre 5', 'Prénom membre 5', '1999-08-21', 'M', 'Lot UV 80 Ambato', 'Riziculteur', '0373434567', 'member.5@fed-agri.mg', 'CONFIRMED', 'col-1'),
                                                                                                                                  ('C1-M6', 'Nom membre 6', 'Prénom membre 6', '1998-08-22', 'F', 'Lot UV 6 Ambato', 'Riziculteur', '0372234567', 'member.6@fed-agri.mg', 'CONFIRMED', 'col-1'),
                                                                                                                                  ('C1-M7', 'Nom membre 7', 'Prénom membre 7', '1998-01-31', 'M', 'Lot UV 7 Ambato', 'Riziculteur', '0374234567', 'member.7@fed-agri.mg', 'CONFIRMED', 'col-1'),
                                                                                                                                  ('C1-M8', 'Nom membre 8', 'Prénom membre 8', '1975-08-20', 'M', 'Lot UV 8 Ambato', 'Riziculteur', '0370234567', 'member.8@fed-agri.mg', 'CONFIRMED', 'col-1');

INSERT INTO membership_fees (id, label, status, frequency, eligible_from, amount, collectivity_id) VALUES
                                                                                                    ('cot-1', 'Cotisation annuelle', 'ACTIVE', 'ANNUALLY', '2026-01-01', 100000, 'col-1'),
                                                                                                    ('cot-2', 'Cotisation annuelle', 'ACTIVE', 'ANNUALLY', '2026-01-01', 100000, 'col-2'),
                                                                                                    ('cot-3', 'Cotisation annuelle', 'ACTIVE', 'ANNUALLY', '2026-01-01', 50000, 'col-3');

INSERT INTO public.financial_accounts (id, account_type, amount, holder_name, mobile_number, collectivity_id) VALUES
                                                                                 ('C1-A-CASH', 'CASH', 0, NULL, NULL, 'col-1'),
                                                                                 ('C1-A-MOBILE-1', 'ORANGE_MONEY', 0, 'Mpanorina', '0370489612', 'col-1'),

                                                                                 ('C2-A-CASH', 'CASH', 0, NULL, NULL, 'col-2'),
                                                                                 ('C2-A-MOBILE-1', 'ORANGE_MONEY', 0, 'Dobo voalohany', '0320489612', 'col-2'),

                                                                                 ('C3-A-CASH', 'CASH', 0, NULL, NULL, 'col-3');

INSERT INTO member_payments ( member_id, amount, account_id, payment_mode, creation_date) VALUES
                                                                                                        ('col-1', 'C1-M1', 100000, 'C1-A-CASH', 'CASH', '2026-01-01'),
                                                                                                        ('col-1', 'C1-M2', 100000, 'C1-A-CASH', 'CASH', '2026-01-01'),
                                                                                                        ('col-1', 'C1-M3', 100000, 'C1-A-CASH', 'CASH', '2026-01-01'),
                                                                                                        ('col-1', 'C1-M4', 100000, 'C1-A-CASH', 'CASH', '2026-01-01'),
                                                                                                        ('col-1', 'C1-M5', 100000, 'C1-A-CASH', 'CASH', '2026-01-01'),
                                                                                                        ('col-1', 'C1-M6', 100000, 'C1-A-CASH', 'CASH', '2026-01-01'),
                                                                                                        ('col-1', 'C1-M7', 60000, 'C1-A-CASH', 'CASH', '2026-01-01'),
                                                                                                        ('col-1', 'C1-M8', 90000, 'C1-A-CASH', 'CASH', '2026-01-01');

INSERT INTO public.collectivity_transactions (collectivity_id, member_id, amount, account_id, payment_mode, created_at) VALUES
                                                                                                          ('col-1', 'C1-M1', 100000, 'C1-A-CASH', 'CASH', '2026-01-01'),
                                                                                                          ('col-1', 'C1-M2', 100000, 'C1-A-CASH', 'CASH', '2026-01-01'),
                                                                                                          ('col-1', 'C1-M3', 100000, 'C1-A-CASH', 'CASH', '2026-01-01'),
                                                                                                          ('col-1', 'C1-M4', 100000, 'C1-A-CASH', 'CASH', '2026-01-01'),
                                                                                                          ('col-1', 'C1-M5', 100000, 'C1-A-CASH', 'CASH', '2026-01-01'),
                                                                                                          ('col-1', 'C1-M6', 100000, 'C1-A-CASH', 'CASH', '2026-01-01'),
                                                                                                          ('col-1', 'C1-M7', 60000, 'C1-A-CASH', 'CASH', '2026-01-01'),
                                                                                                          ('col-1', 'C1-M8', 90000, 'C1-A-CASH', 'CASH', '2026-01-01');