package mr.cell.incubator.springboottest.batch;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import mr.cell.incubator.springboottest.domain.Person;

@Slf4j
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
	
	private final JdbcTemplate jdbcTemplate;
	
	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() != BatchStatus.COMPLETED) {
			log.error("Batch insert job has ended unsuccessfully!");
			return;
		}
		
		log.info("Batch insert job has been finished. Time to verify the results");
		
		jdbcTemplate.query(
				"SELECT id, name, age FROM person", 
				(rs, rowNum) -> new Person(rs.getLong(1), rs.getString(2), rs.getInt(3)) 
		)
			.stream()
			.forEach(person -> 
				log.info("Found ({}) in the database.", person) 
			);
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		log.info("Starting batch insert job!");
	} 
}
