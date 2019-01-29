package com.eai.serviceimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eai.dto.Pagination;
import com.eai.model.Role;
import com.eai.model.User;
import com.eai.repository.UserRepository;
import com.eai.service.UserService;

@Service
public class UserServiceImpl extends CriteriaSql implements UserService {
   
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
    private UserRepository userRepository;
	
	@Override
	@Transactional
	public User findById(Integer idUser) {
		return userRepository.findById(idUser).orElse(null);
	}

    @Override
    @Transactional
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username){
        return  userRepository.findByUserName(username);
    }

    @SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void findAll(Pagination pagination, Long pageSize) {
    	CriteriaBuilder builder = manager.getCriteriaBuilder();
    	CriteriaQuery<User> criteria = builder.createQuery(User.class);
    	Root<User> root = criteria.from(User.class);
    	Join<User, UserDetails> userDetailsJoin = root.join("userDetail", JoinType.INNER);
    	Join<User, Role> roleJoin = root.join("role", JoinType.INNER);
    	
    	sqlEqual(builder, criteria, root.get("idUser"), pagination.getData().get(0));
    	
    	sqlLike(builder, criteria, root.get("userName"), pagination.getData().get(1));    	
    	
    	sqlBool(builder, criteria, root.get("enabled"), pagination.getData().get(2));
        
    	sqlLike(builder, criteria, userDetailsJoin.get("fullName"), pagination.getData().get(3));
    	
    	sqlEqual(builder, criteria, userDetailsJoin.get("birth"), pagination.getData().get(4));
    	
    	sqlLike(builder, criteria, userDetailsJoin.get("email"), pagination.getData().get(5));
    	
    	sqlEqual(builder, criteria, roleJoin.get("name"), pagination.getData().get(6));
    	
    	Query query = manager.createQuery(criteria);
    	List<User> userList = query.getResultList();
    	
    	if(!userList.isEmpty()) {
    		pagination.getPage(query.getResultList().size(), pageSize);
        	
        	PagedListHolder<User> pageList = new PagedListHolder<>(userList);
        	MutableSortDefinition x = new MutableSortDefinition ("name", true, true);
        	pageList.setSort(x);
        	pageList.resort();
        	
        	int page = (int)(pagination.getPage()-1);
        	pageList.setPageSize(pageSize.intValue());
        	pageList.setPage(page); 	
        	pagination.setUserList(pageList.getPageList());
        	
        	for (User user1 : userList) {
				user1.getUserDetail().setUserName(user1.getUsername());
			}
    	}else {
    		pagination.setLength(0);
    		pagination.setPage(0);
    		pagination.setSize(0);
    	}
	}

	@Override
	@Transactional
	public User saveOrUpdate(User userOld) {
		User userNew = this.findById(userOld.getIdUser());
		
		userNew.getUserDetail().setFullName(userOld.getUserDetail().getFullName());
		userNew.setEnabled(userOld.isEnabled());
		userNew.getUserDetail().setEmail(userOld.getUserDetail().getEmail());
		if("ROLE_USER".equals(userOld.getRole().getName())) {
			userNew.setRole(new Role(new Integer("1")));
		}else {
			userNew.setRole(new Role(new Integer("2")));
		}
		
		return userRepository.save(userNew);
	}
}