package com.eai.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eai.model.SystemParameter;

@Repository
public interface SystemParameterRepository extends CrudRepository<SystemParameter, Integer> {

}