package mr.cell.incubator.springboottest.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties("storage")
@Data
public class StorageProperties {
	
	private String location = "upload-dir";

}
