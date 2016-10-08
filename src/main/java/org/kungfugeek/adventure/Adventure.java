package org.kungfugeek.adventure;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kungfugeek.adventure.db.mongo.NPCRepository;
import org.kungfugeek.adventure.npc.NPC;
import org.kungfugeek.adventure.versioning.DBUpdater;
import org.kungfugeek.adventure.versioning.AdventureEngineVersion;
import org.kungfugeek.adventure.versioning.AdventureEngineVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@SpringBootApplication
public class Adventure extends AbstractMongoConfiguration implements CommandLineRunner {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(Adventure.class);
	private static final String VERSION = "0.0.1";
	
	@Autowired
	private NPCRepository repo;
	
	@Autowired
	private DBUpdater dbUpdater;
	
	@Autowired
	private AdventureEngineVersionRepository versionRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(Adventure.class, args);
	}

	public void run(String... arg0) throws Exception {		
		showDB();
		dbUpdater.initializeToVersion(VERSION);
		showDB();
		
	}

	/**
	 * 
	 */
	private void showDB() {
		for (NPC npc : repo.findAll()) {
			System.out.println("Found NPC " + npc.toString());
		}
		
		for (AdventureEngineVersion version : versionRepo.findAll()) {
			System.out.println("Found Version " + version.getVersion());
		}
	}

	@Override
	protected String getDatabaseName() {
		return "adventure";
	}

	@Override
	public Mongo mongo() throws Exception {
		return new MongoClient("127.0.0.1", 27017);
	}

	
}
