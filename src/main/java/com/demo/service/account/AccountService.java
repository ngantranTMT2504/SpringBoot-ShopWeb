package com.demo.service.account;

import java.util.List;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.demo.exception.ResourceNotFoundException;
import com.demo.model.Account;
import com.demo.model.AccountDetails;
import com.demo.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService, UserDetailsService  {
	private final AccountRepository accountRepository;
	private final PasswordEncoder encoder;

	
	@Override
	public Account create(Account account) {
		account.setPassword(encoder.encode(account.getPassword()));
		Account saved = accountRepository.save(account);
		return saved;
	}

	@Override
	public List<Account> findAll() {
		List<Account> accounts = accountRepository.findAll();

		return accounts;
	}

	@Override
	public Account update(Long id, Account account) {
		Account accountFindById = findById(id);
		account.setId(id);
		account.setPassword(accountFindById.getPassword());
		Account updated = accountRepository.save(account);
		return updated;
	}

	@Override
	public Account findById(Long id) {
		return accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found"));
	}

	@Override
	public Account deleteById(Long id) {
		Account acc = findById(id);
		accountRepository.deleteById(id);
		return acc;
	}

	@Override
	public Account findByEmail(String email) {
		Account account = accountRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tài khoản với email: " + email));
		return account;
	}

	@Override
	public UserDetails loadUserByUsername(String email) {
		Optional<Account> userDetail = accountRepository.findByEmail(email);

		return userDetail.map(AccountDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
	}

	@Override
	public boolean checkActive(String email) {
		Account user = accountRepository.findByEmail(email).get();
		return user.isActive();
	}

}
