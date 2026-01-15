package com.example.claimsadministrationsystem.feature.usertreatment.repository;

import com.example.claimsadministrationsystem.domain.UserTreatment;
import com.example.claimsadministrationsystem.feature.proxyrequest.dto.HospitalAmount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserTreatmentRepository extends JpaRepository<UserTreatment, UUID> {

    List<UserTreatment> findAllByUserId(UUID userId);

    @Query("""
        select new com.example.claimsadministrationsystem.feature.proxyrequest.dto.HospitalAmount(
            ut.hospitalId,
            coalesce(sum(ut.treatmentAmount), 0)
        )
        from UserTreatment ut
        where ut.userId = :userId
          and ut.hospitalId in :hospitalIds
        group by ut.hospitalId
    """)
    List<HospitalAmount> sumTreatmentAmountByHospitalIds(
            UUID userId,
            Collection<UUID> hospitalIds
    );
}
