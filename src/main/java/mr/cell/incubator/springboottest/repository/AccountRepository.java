package mr.cell.incubator.springboottest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mr.cell.incubator.springboottest.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	
	Optional<Account> findByUsername(String username);

}
