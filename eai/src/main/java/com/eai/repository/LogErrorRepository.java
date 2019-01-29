package com.eai.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eai.model.LogError;

@Repository
public interface LogErrorRepository extends CrudRepository<LogError, Integer> {

}