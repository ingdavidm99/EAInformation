 package com.eai.wizard.serviceimple;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eai.model.Data;
import com.eai.repository.DataRepository;
import com.eai.wizard.service.DataService;

@Service
public class DataServiceImpl  implements DataService{
	
	@Autowired
	DataRepository dataRepository;

	@Override
	@Transactional
	public void saveOrUpdate(Data data) {
		dataRepository.save(data);
	}

}