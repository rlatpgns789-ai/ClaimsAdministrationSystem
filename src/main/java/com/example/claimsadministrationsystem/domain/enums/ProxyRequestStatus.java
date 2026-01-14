package com.example.claimsadministrationsystem.domain.enums;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public enum ProxyRequestStatus {

    PENDING,
    IN_PROGRESS,
    FEE_CLAIM,
    COMPLETED,
    CANCELLED,
    DISCLAIMER;

    private static final Map<ProxyRequestStatus, Set<ProxyRequestStatus>> ALLOWED_TRANSITIONS =
            Map.of(
                    PENDING, Set.of(IN_PROGRESS, CANCELLED, DISCLAIMER),
                    IN_PROGRESS, Set.of(FEE_CLAIM, DISCLAIMER),
                    FEE_CLAIM, Set.of(COMPLETED),
                    COMPLETED, Collections.emptySet(),
                    CANCELLED, Collections.emptySet(),
                    DISCLAIMER, Collections.emptySet()
            );


    public void checkIsTerminal(){
        if (ALLOWED_TRANSITIONS.get(this).isEmpty()){
            throw new IllegalStateException(
                    "Cannot transition from terminal status: " + this
            );
        }
    }

    public void checkCannotTransitionTo(ProxyRequestStatus status){
        Set<ProxyRequestStatus> target = ALLOWED_TRANSITIONS.get(this);

       if (!target.contains(status)){
           throw new IllegalStateException(
                   "Invalid status transition order: " + this + " -> " + status
           );
       }
    }


    public ProxyRequestStatus progress(){
        checkIsTerminal();
        checkCannotTransitionTo(IN_PROGRESS);
        return IN_PROGRESS;

    }

    
    public ProxyRequestStatus feeClaim(boolean isPrePaid){
        checkIsTerminal();
        checkCannotTransitionTo(FEE_CLAIM);
        if (isPrePaid){
            return COMPLETED;
        } else {
            return FEE_CLAIM;
        }
    }

    public ProxyRequestStatus cancel(){
        checkIsTerminal();
        checkCannotTransitionTo(CANCELLED);
        if (this != PENDING){
            throw new IllegalStateException("Unexpected value: " + this.name());
        }

        return CANCELLED;
    }

    public ProxyRequestStatus disclaimer(){
        checkIsTerminal();
        if (this != PENDING && this != IN_PROGRESS){
            throw new IllegalStateException("Unexpected value: " + this.name());
        }
        return DISCLAIMER;
    }

    public ProxyRequestStatus complete(){
        checkIsTerminal();
        checkCannotTransitionTo(COMPLETED);
        return COMPLETED;
    }


}