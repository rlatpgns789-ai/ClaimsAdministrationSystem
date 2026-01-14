package com.example.claimsadministrationsystem.domain.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProxyRequestStatusTest {
    @Nested
    @DisplayName("PENDING")
    class Pending {

        @Test
        @DisplayName("PENDING 상태에서 progress() 호출 시 IN_PROGRESS 상태가 된다.")
        void progress_toInProgress() {
            ProxyRequestStatus next = ProxyRequestStatus.PENDING.progress();
            assertEquals(ProxyRequestStatus.IN_PROGRESS, next);
        }

        @Test
        @DisplayName("PENDING 상태에서 cancel() 호출 시 CANCELLED 상태가 된다.")
        void cancel_toCancelled() {
            ProxyRequestStatus next = ProxyRequestStatus.PENDING.cancel();
            assertEquals(ProxyRequestStatus.CANCELLED, next);
        }

        @Test
        @DisplayName("PENDING 상태에서 disclaimer() 호출 시 DISCLAIMER 상태가 된다.")
        void disclaimer_toDisclaimer() {
            ProxyRequestStatus next = ProxyRequestStatus.PENDING.disclaimer();
            assertEquals(ProxyRequestStatus.DISCLAIMER, next);
        }

        @Test
        @DisplayName("PENDING에서 feeClaim() 호출 시 허용되지 않아 IllegalStateException 예외 발생")
        void feeClaim_shouldThrow() {
            IllegalStateException ex = assertThrows(
                    IllegalStateException.class,
                    () -> ProxyRequestStatus.PENDING.feeClaim(false)
            );
            assertTrue(ex.getMessage().contains("Invalid status transition order"));
        }
    }

    @Nested
    @DisplayName("IN_PROGRESS")
    class InProgress {

        @Test
        @DisplayName("IN_PROGRESS 상태에서 disclaimer() 호출 시 DISCLAIMER 상태가 된다.")
        void disclaimer_toDisclaimer() {
            ProxyRequestStatus next = ProxyRequestStatus.IN_PROGRESS.disclaimer();
            assertEquals(ProxyRequestStatus.DISCLAIMER, next);
        }

        @Test
        @DisplayName("IN_PROGRESS 상태에서 feeClaim(false) 호출 시 FEE_CLAIM 상태가 된다. (선결제 아님)")
        void feeClaim_notPrePaid_toFeeClaim() {
            ProxyRequestStatus next = ProxyRequestStatus.IN_PROGRESS.feeClaim(false);
            assertEquals(ProxyRequestStatus.FEE_CLAIM, next);
        }

        @Test
        @DisplayName("IN_PROGRESS 상태에서 feeClaim(true) 호출 시 COMPLETED 상태가 된다. (선결제)")
        void feeClaim_prePaid_toCompleted() {
            ProxyRequestStatus next = ProxyRequestStatus.IN_PROGRESS.feeClaim(true);
            assertEquals(ProxyRequestStatus.COMPLETED, next);
        }

        @Test
        @DisplayName("IN_PROGRESS에서 progress() 호출 시 허용되지 않아 IllegalStateException 예외 발생")
        void progress_shouldThrow() {
            IllegalStateException ex = assertThrows(
                    IllegalStateException.class,
                    () -> ProxyRequestStatus.IN_PROGRESS.progress()
            );
            assertTrue(ex.getMessage().contains("Invalid status transition order"));
        }

        @Test
        @DisplayName("IN_PROGRESS에서 cancel() 호출 시 허용되지 않아 IllegalStateException 예외 발생")
        void cancel_shouldThrow() {
            IllegalStateException ex = assertThrows(
                    IllegalStateException.class,
                    () -> ProxyRequestStatus.IN_PROGRESS.cancel()
            );
            assertTrue(ex.getMessage().contains("Invalid status transition order"));
        }
    }

    @Nested
    @DisplayName("FEE_CLAIM")
    class FeeClaim {

        @Test @DisplayName("FEE_CLAIM 상태에서 complete() 호출시 COMPLETED 상태가 된다.")
        void inProgress_disclaimer() {
            ProxyRequestStatus next = ProxyRequestStatus.FEE_CLAIM.complete();
            assertEquals(ProxyRequestStatus.COMPLETED, next);
        }

        @Test
        @DisplayName("FEE_CLAIM에서 progress() 호출 시 허용되지 않아 IllegalStateException 예외 발생")
        void progress_shouldThrow() {
            IllegalStateException ex = assertThrows(
                    IllegalStateException.class,
                    () -> ProxyRequestStatus.FEE_CLAIM.progress()
            );
            assertTrue(ex.getMessage().contains("Invalid status transition order"));
        }

        @Test
        @DisplayName("FEE_CLAIM에서 cancel() 호출 시 허용되지 않아 IllegalStateException 예외 발생")
        void cancel_shouldThrow() {
            IllegalStateException ex = assertThrows(
                    IllegalStateException.class,
                    () -> ProxyRequestStatus.FEE_CLAIM.cancel()
            );
            assertTrue(ex.getMessage().contains("Invalid status transition order"));
        }

        @Test
        @DisplayName("FEE_CLAIM에서 disclaimer() 호출 시 Unexpected value 예외 발생")
        void disclaimer_shouldThrowUnexpectedValue() {
            IllegalStateException ex = assertThrows(
                    IllegalStateException.class,
                    () -> ProxyRequestStatus.FEE_CLAIM.disclaimer()
            );
            assertTrue(ex.getMessage().contains("Unexpected value"));
        }
    }

    @Nested
    @DisplayName("Terminal States")
    class Terminal {

        @Test
        @DisplayName("COMPLETED는 terminal 상태이므로 progress() 호출 시 예외 발생")
        void completed_progress_shouldThrowTerminal() {
            IllegalStateException ex = assertThrows(
                    IllegalStateException.class,
                    () -> ProxyRequestStatus.COMPLETED.progress()
            );
            assertTrue(ex.getMessage().contains("Cannot transition from terminal status"));
        }

        @Test
        @DisplayName("CANCELLED는 terminal 상태이므로 feeClaim() 호출 시 예외 발생")
        void cancelled_feeClaim_shouldThrowTerminal() {
            IllegalStateException ex = assertThrows(
                    IllegalStateException.class,
                    () -> ProxyRequestStatus.CANCELLED.feeClaim(false)
            );
            assertTrue(ex.getMessage().contains("Cannot transition from terminal status"));
        }

        @Test
        @DisplayName("DISCLAIMER는 terminal 상태이므로 cancel() 호출 시 예외 발생")
        void disclaimer_cancel_shouldThrowTerminal() {
            IllegalStateException ex = assertThrows(
                    IllegalStateException.class,
                    () -> ProxyRequestStatus.DISCLAIMER.cancel()
            );
            assertTrue(ex.getMessage().contains("Cannot transition from terminal status"));
        }
    }
}
