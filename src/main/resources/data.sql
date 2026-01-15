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
          '11111111-1111-1111-1111-111111111111',
          'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
          '99999999-9999-9999-9999-999999999999',
          'Seoul General Hospital',
          DATE '2026-01-05',
          350000,
          CURRENT_TIMESTAMP,
          CURRENT_TIMESTAMP
      ),
      (
          '22222222-2222-2222-2222-222222222222',
          'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
          '99999999-9999-9999-9999-999999999999',
          'Busan Medical Center',
          DATE '2026-01-10',
          180000,
          CURRENT_TIMESTAMP,
          CURRENT_TIMESTAMP
      ),
      (
          '33333333-3333-3333-3333-333333333333',
          'cccccccc-cccc-cccc-cccc-cccccccccccc',
          '99999999-9999-9999-9999-999999999999',
          'Incheon Clinic',
          DATE '2026-01-12',
          220000,
          CURRENT_TIMESTAMP,
          CURRENT_TIMESTAMP
      );

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
          '12121212-1212-1212-1212-121212121212',
          'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
          '88888888-8888-8888-8888-888888888888',
          'Seoul General Hospital',
          DATE '2026-01-06',
          100000,
          CURRENT_TIMESTAMP,
          CURRENT_TIMESTAMP
      ),
      (
          '13131313-1313-1313-1313-131313131313',
          'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
          '88888888-8888-8888-8888-888888888888',
          'Busan Medical Center',
          DATE '2026-01-11',
          200000,
          CURRENT_TIMESTAMP,
          CURRENT_TIMESTAMP
      );


INSERT INTO proxy_request (
    id,
    user_id,
    status,
    total_missed_insurance_amount,
    commission_amount,
    coverage_type,
    reason,
    registered_at,
    modified_at
) VALUES
    (
        '44444444-4444-4444-4444-444444444444',
        '99999999-9999-9999-9999-999999999999',
        'PENDING',
        530000,
        79500,
        'GENERAL_PREPAID',
        NULL,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );

INSERT INTO proxy_request (
    id,
    user_id,
    status,
    total_missed_insurance_amount,
    commission_amount,
    coverage_type,
    reason,
    registered_at,
    modified_at
) VALUES
    (
        '77777777-7777-7777-7777-777777777777',
        '88888888-8888-8888-8888-888888888888',
        'DISCLAIMER',
        220000,
        66000,
        'EXPRESS_POSTPAID',
        'Insufficient documents',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );

INSERT INTO proxy_request_unit (
    id,
    hospital_id,
    unclaimed_insurance_amount,
    proxy_request_id,
    registered_at,
    modified_at
) VALUES
      (
          '55555555-5555-5555-5555-555555555555',
          'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
          350000,
          '44444444-4444-4444-4444-444444444444',
          CURRENT_TIMESTAMP,
          CURRENT_TIMESTAMP
      ),
      (
          '66666666-6666-6666-6666-666666666666',
          'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
          180000,
          '44444444-4444-4444-4444-444444444444',
          CURRENT_TIMESTAMP,
          CURRENT_TIMESTAMP
      ),
      (
          '88888888-8888-8888-8888-888888888888',
          'cccccccc-cccc-cccc-cccc-cccccccccccc',
          220000,
          '77777777-7777-7777-7777-777777777777',
          CURRENT_TIMESTAMP,
          CURRENT_TIMESTAMP
      );
