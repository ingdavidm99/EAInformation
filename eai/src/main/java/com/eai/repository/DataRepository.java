package com.eai.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eai.model.Data;

@Repository
public interface DataRepository extends CrudRepository<Data, Integer> {

}