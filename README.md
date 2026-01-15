# 제목 없음

### 1. 실행 방법 (로컬)

- 요구 환경
    - JDK 21
    - Gradle
    - (기본) H2 / Spring Boot 3.3.4
- 실행
    - `./gradlew bootRun`
- 테스트
    - `./gradlew test`

### 2. 엔티티 설계 의도

- 엔티티는 요구사항에 나와있는 대로 구현하려 하였고 이를 위해 User 테이블을 분리하는 등의 정규화 작업은 수행하지 않았습니다.
- 추가적으로 JpaAuditing를 공통으로 구현하여 생성 및 업데이트 시각을 저장하도록 했습니다.

### 3. 비즈니스 로직 배치 이유

- 저는 가능한 해당 객체의 속성만으로 판단 가능한 로직은 엔티티에 배치하여 스스로 처리하도록 설계했고, 여러 엔티티를 함꼐 확인해야 하는 경우에는 서비스 레이어에서 처리했습니다.

### 4. 상태 전이 규칙 구현 방식

- 상태 전이 규칙은 ProxyRequestService.updateProxyRequest() 메서드에서 구현했습니다.
- DB에서 조회한 대행 요청의 현재 상태와 요청으로 전달된 목표 상태를 함께 비교하여
  해당 상태 전이가 허용되는지 먼저 검증하였습니다.
- 통과했을 때만 ProxyRequest의 상태 변경 메서드(progress(), cancel(), complete() 등)를 호출하여 실제 상태를 변경하도록 했습니다.

### 5. 고민했던 포인트와 해결 방법

- 고민했던 포인트는 수수료 계산 부분입니다. 현재 비즈니스 로직상 보장타입이나 결제방식에 따라 수수료율과 계산 시점이 달라지기 때문에 수수료 계산 로직의 위치를 어디에 할지를 고민했습니다.
- 결과적으로 수수료 계산 로직을 서비스 레이어가 아닌 CoverageType Enum에 두어,
  각 보장 타입이 자신의 수수료 계산을 직접 책임지도록 설계했습니다. 이를 통해 수수료 정책을 한 곳에서 관리하고, 보장 타입이 추가되더라도 복잡도가 증가하지 않도록 했습니다.

### 6. 테스트 CURL

---

### 1. 유저 진료 내역 조회

```bash
curl -X GET"http://localhost:8080/api/users/99999999-9999-9999-9999-999999999999/treatments" \
  -H"Content-Type: application/json"

```

```bash
curl -X GET"http://localhost:8080/api/users/88888888-8888-8888-8888-888888888888/treatments" \
  -H"Content-Type: application/json"

```

---

### 2. 대행 요청 상세 조회

```bash
curl -X GET"http://localhost:8080/api/proxy-requests/44444444-4444-4444-4444-444444444444" \
  -H"Content-Type: application/json"

```

```bash
curl -X GET"http://localhost:8080/api/proxy-requests/77777777-7777-7777-7777-777777777777" \
  -H"Content-Type: application/json"

```

---

### 3. 대행 요청 생성 (성공 케이스)

```json
curl -X POST"http://localhost:8080/api/proxy-requests" \
  -H"Content-Type: application/json" \
  -d '{
    "userId": "88888888-8888-8888-8888-888888888888",
    "coverageType": "GENERAL_PREPAID",
    "hospitalList": [
      "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa",
      "bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"
    ]
  }'

```

---

### 4. 대행 요청 생성 (실패 케이스 – 활성 요청 존재)

```bash
curl -X POST"http://localhost:8080/api/proxy-requests" \
  -H"Content-Type: application/json" \
  -d '{
    "userId": "99999999-9999-9999-9999-999999999999",
    "coverageType": "GENERAL_PREPAID",
    "hospitalList": [
      "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"
    ]
  }'

```

---

### 5. 상태 변경 (PENDING → IN_PROGRESS)

```bash
curl -X PATCH"http://localhost:8080/api/proxy-requests/44444444-4444-4444-4444-444444444444/status" \
  -H"Content-Type: application/json" \
  -d '{
    "targetStatus": "IN_PROGRESS",
    "reason": null
  }'

```

---

### 6. 상태 변경 (IN_PROGRESS → DISCLAIMER)

```bash
curl -X PATCH"http://localhost:8080/api/proxy-requests/44444444-4444-4444-4444-444444444444/status" \
  -H"Content-Type: application/json" \
  -d '{
    "targetStatus": "DISCLAIMER",
    "reason": "서류 미비"
  }'

```

---

### 7. 대행 요청 취소 (CANCELLED 처리)

```bash
curl -X DELETE"http://localhost:8080/api/proxy-requests/44444444-4444-4444-4444-444444444444" \
  -H"Content-Type: application/json"

```

---

### 8. 상태 전이 실패 예시 (허용되지 않은 상태 변경)

```bash
{
    "targetStatus": "IN_PROGRESS",
    "reason": null
  }
```
# Database Schema

## proxy_request

| Column                         | Type         | Key |
|--------------------------------|--------------|-----|
| id                             | CHAR(36)     | PK  |
| user_id                        | CHAR(36)     |     |
| status                         | VARCHAR(30)  |     |
| coverage_type                  | VARCHAR(50)  |     |
| total_missed_insurance_amount  | BIGINT       |     |
| commission_amount              | BIGINT       |     |
| reason                         | VARCHAR(255) |     |
| registered_at                  | TIMESTAMP    |     |
| modified_at                    | TIMESTAMP    |     |

## proxy_request_unit

| Column                       | Type     | Key                    |
|------------------------------|----------|------------------------|
| id                           | CHAR(36) | PK                     |
| proxy_request_id             | CHAR(36) | FK → proxy_request.id  |
| hospital_id                  | CHAR(36) |                        |
| unclaimed_insurance_amount   | BIGINT   |                        |
| registered_at                | TIMESTAMP|                        |
| modified_at                  | TIMESTAMP|                        |

## user_treatment

| Column           | Type        | Key |
|------------------|-------------|-----|
| id               | CHAR(36)    | PK  |
| user_id          | CHAR(36)    |     |
| hospital_id      | CHAR(36)    |     |
| hospital_name    | VARCHAR(50) |     |
| treatment_date   | DATE        |     |
| treatment_amount | BIGINT      |     |
| registered_at    | TIMESTAMP   |     |
| modified_at      | TIMESTAMP   |     |