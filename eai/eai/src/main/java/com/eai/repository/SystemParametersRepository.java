package com.eai.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eai.model.SystemParameters;

@Repository
public interface SystemParametersRepository extends CrudRepository<SystemParameters, Integer> {

}