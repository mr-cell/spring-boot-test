package mr.cell.incubator.springboottest.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import mr.cell.incubator.springboottest.domain.Bookmark;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

	Collection<Bookmark> findByAccountUsername(String username);
}
