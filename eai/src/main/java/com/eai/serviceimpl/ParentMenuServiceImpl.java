package com.eai.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eai.model.ParentMenu;
import com.eai.repository.ParentMenuRepository;
import com.eai.service.ParentMenuService;

@Service
public class ParentMenuServiceImpl implements ParentMenuService {
    
	@Autowired
    private ParentMenuRepository parentMenuRepository;
    
    @Override
    @Transactional
    public List<ParentMenu> parentMenuList(Integer idRole){
        
        return parentMenuRepository.parentMenuList(idRole);
    }
}