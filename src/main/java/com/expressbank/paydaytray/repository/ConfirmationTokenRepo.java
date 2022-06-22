package com.expressbank.paydaytray.repository;

import com.expressbank.paydaytray.auth.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepo extends JpaRepository<Long, ConfirmationToken> {
}
