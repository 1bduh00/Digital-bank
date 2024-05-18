package com.example.transactionsservice.repository;

import com.example.transactionsservice.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransRepository extends JpaRepository<Transfer,Long> {
}
