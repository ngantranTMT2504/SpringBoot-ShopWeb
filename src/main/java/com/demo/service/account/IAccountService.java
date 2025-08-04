package com.demo.service.account;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import com.demo.model.Account;

public interface IAccountService {

	 public Account create(Account account);

	    public List<Account> findAll();

	    public Account update(Long id, Account account);

	    public Account findById(Long id);

	    public Account deleteById(Long id);
	    
	    public Account findByEmail(String email);

	    public UserDetails loadUserByUsername(String email);
	    
	    public boolean checkActive(String email);

}
