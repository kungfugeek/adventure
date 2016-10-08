package org.kungfugeek.adventure.versioning;
import org.springframework.data.annotation.Id;

public class AdventureEngineVersion {
	
	public static final String SINGLETON_ID = "Singleton";
	
	@Id
	private final String id = SINGLETON_ID;
	private String version;
	
	public AdventureEngineVersion(String version) {
		super();
		this.version = version;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

}
