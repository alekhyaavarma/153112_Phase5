package com.cg.spring.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.spring.beans.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, String> {
@Query("select t from Transaction t where t.mobileNumber=:mobileNumber")
public List<Transaction> findByMobileNumber(@Param("mobileNumber")String mobileNumber);
}
