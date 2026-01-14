-- =========================================
-- PROXY_REQUEST
-- =========================================
INSERT INTO proxy_request (
    id,
    user_id,
    status,
    total_missed_insurance_amount,
    commission_amount,
    registered_at,
    modified_at
) VALUES
      (
          '11111111-1111-1111-1111-111111111111',
          1001,
          'PENDING',
          1500000,
          150000,
          CURRENT_TIMESTAMP,
          CURRENT_TIMESTAMP
      ),
      (
          '22222222-2222-2222-2222-222222222222',
          1002,
          'IN_PROGRESS',
          2300000,
          230000,
          CURRENT_TIMESTAMP,
          CURRENT_TIMESTAMP
      );

-- =========================================
-- PROXY_REQUEST_UNIT (FK -> proxy_request)
-- =========================================
INSERT INTO proxy_request_unit (
    id,
    hospital_id,
    unclaimed_insurance_amount,
    proxy_request_id,
    registered_at,
    modified_at
) VALUES
      (
          'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1',
          '99999999-0000-0000-0000-000000000001',
          500000,
          '11111111-1111-1111-1111-111111111111',
          CURRENT_TIMESTAMP,
          CURRENT_TIMESTAMP
      ),
      (
          'aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2',
          '99999999-0000-0000-0000-000000000002',
          1000000,
          '11111111-1111-1111-1111-111111111111',
          CURRENT_TIMESTAMP,
          CURRENT_TIMESTAMP
      ),
      (
          'bbbbbbb1-bbbb-bbbb-bbbb-bbbbbbbbbbb1',
          '99999999-0000-0000-0000-000000000003',
          2300000,
          '22222222-2222-2222-2222-222222222222',
          CURRENT_TIMESTAMP,
          CURRENT_TIMESTAMP
      );

-- =========================================
-- USER_TREATMENT
-- =========================================
INSERT INTO user_treatment (
    id,
    hospital_id,
    user_id,
    hospital_name,
    treatment_date,
    treatment_amount,
    registered_at,
    modified_at
) VALUES
      (
          'ccccccc1-cccc-cccc-cccc-ccccccccccc1',
          '99999999-0000-0000-0000-000000000001',
          '77777777-7777-7777-7777-777777777701',
          'Seoul General Hospital',
          DATE '2026-01-10',
          350000,
          CURRENT_TIMESTAMP,
          CURRENT_TIMESTAMP
      ),
      (
          'ccccccc2-cccc-cccc-cccc-ccccccccccc2',
          '99999999-0000-0000-0000-000000000002',
          '77777777-7777-7777-7777-777777777701',
          'Busan Medical Center',
          DATE '2026-01-12',
          180000,
          CURRENT_TIMESTAMP,
          CURRENT_TIMESTAMP
      ),
      (
          'ccccccc3-cccc-cccc-cccc-ccccccccccc3',
          '99999999-0000-0000-0000-000000000003',
          '77777777-7777-7777-7777-777777777702',
          'Incheon Clinic',
          DATE '2026-01-14',
          220000,
          CURRENT_TIMESTAMP,
          CURRENT_TIMESTAMP
      );
