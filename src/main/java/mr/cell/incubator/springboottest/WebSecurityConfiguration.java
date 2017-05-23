package mr.cell.incubator.springboottest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import mr.cell.incubator.springboottest.repository.AccountRepository;
import mr.cell.incubator.springboottest.security.UserDetailsService;

@Configuration
public class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {
	
	@Autowired
	private AccountRepository accounts;
	
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService());
	}
	
	@Bean
	UserDetailsService userDetailsService() {
		return (username) -> accounts.findByUsername(username)
				.map(user -> new User(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList("USER", "write")))
						.orElseThrow(() -> new UsernameNotFoundException("Could not find the user '" + username + "'") ); 
	}
	
	

}
