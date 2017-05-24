package mr.cell.incubator.springboottest.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mr.cell.incubator.springboottest.repository.AccountRepository;

@Service
public class BookmarksUserDetailsService implements UserDetailsService {
	
	private AccountRepository accounts;
	
	public BookmarksUserDetailsService(AccountRepository accounts) {
		this.accounts = accounts;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return accounts.findByUsername(username)
				.map(user -> new User(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList("USER", "write")))
				.orElseThrow(() -> new UsernameNotFoundException("Could not find the user '" + username + "'") );
	}

}
