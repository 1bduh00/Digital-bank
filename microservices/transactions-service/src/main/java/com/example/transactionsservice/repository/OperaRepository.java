package com.example.transactionsservice.repository;

import com.example.transactionsservice.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperaRepository extends JpaRepository<Operation,Long> {


}
