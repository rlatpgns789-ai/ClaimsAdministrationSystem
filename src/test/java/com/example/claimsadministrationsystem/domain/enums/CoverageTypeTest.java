package com.example.claimsadministrationsystem.domain.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CoverageTypeTest {

    @Nested
    @DisplayName("GENERAL")
    class General {

        @Test
        @DisplayName("GENERAL_PREPAID는 커버리지가 GENERAL이며 수수료는 15%를 계산한다.")
        void generalPrepaid_commissionAndCategory() {
            CoverageType type = CoverageType.GENERAL_PREPAID;

            assertTrue(type.isGeneral());
            assertEquals(150L, type.calculateCommission(1000L)); // 1000 * 15 / 100
        }

        @Test
        @DisplayName("GENERAL_POSTPAID는 커버리지가 GENERAL이며 수수료는 20%를 계산한다.")
        void generalPostpaid_commissionAndCategory() {
            CoverageType type = CoverageType.GENERAL_POSTPAID;

            assertTrue(type.isGeneral());
            assertEquals(200L, type.calculateCommission(1000L)); // 1000 * 20 / 100
        }
    }

    @Nested
    @DisplayName("EXPRESS")
    class Express {

        @Test
        @DisplayName("EXPRESS_PREPAID는 커버리지가 EXPRESS이며 수수료는 25%를 계산한다.")
        void expressPrepaid_commissionAndCategory() {
            CoverageType type = CoverageType.EXPRESS_PREPAID;

            assertFalse(type.isGeneral());
            assertEquals(250L, type.calculateCommission(1000L)); // 1000 * 25 / 100
        }

        @Test
        @DisplayName("EXPRESS_POSTPAID는 커버리지가 EXPRESS이며 수수료는 30%를 계산한다.")
        void expressPostpaid_commissionAndCategory() {
            CoverageType type = CoverageType.EXPRESS_POSTPAID;

            assertFalse(type.isGeneral());
            assertEquals(300L, type.calculateCommission(1000L)); // 1000 * 30 / 100
        }
    }

    @Nested
    @DisplayName("calculateCommission 공통 동작")
    class CalculateCommission {

        @Test
        @DisplayName("value가 0이면 항상 0을 반환한다.")
        void valueZero_returnsZero() {
            assertAll(
                    () -> assertEquals(0L, CoverageType.GENERAL_PREPAID.calculateCommission(0L)),
                    () -> assertEquals(0L, CoverageType.GENERAL_POSTPAID.calculateCommission(0L)),
                    () -> assertEquals(0L, CoverageType.EXPRESS_PREPAID.calculateCommission(0L)),
                    () -> assertEquals(0L, CoverageType.EXPRESS_POSTPAID.calculateCommission(0L))
            );
        }

        @Test
        @DisplayName("value가 null이면 Function.apply에서 NullPointerException이 발생한다.")
        void nullValue_throwsNpe() {
            assertThrows(NullPointerException.class,
                    () -> CoverageType.GENERAL_PREPAID.calculateCommission(null)
            );
        }
    }
}
