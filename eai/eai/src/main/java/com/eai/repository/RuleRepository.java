package com.eai.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eai.model.Rule;

@Repository
public interface RuleRepository extends CrudRepository<Rule, Integer> {

}