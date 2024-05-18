package com.example.transactionsservice.repository;

import com.example.transactionsservice.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperaRepository extends JpaRepository<Operation,Long> {


}
