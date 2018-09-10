package com.eai.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eai.model.UserDetail;

@Repository
public interface UserDetailRepository extends CrudRepository<UserDetail, Integer> {

}