package com.example.transactionsservice.repository;

import com.example.transactionsservice.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransRepository extends JpaRepository<Transfer,Long> {
}
