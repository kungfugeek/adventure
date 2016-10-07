package org.kungfugeek.adventure;

import org.kungfugeek.adventure.db.mongo.NPCRepository;
import org.kungfugeek.adventure.npc.NPC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@SpringBootApplication
public class Adventure extends AbstractMongoConfiguration implements CommandLineRunner {

	@Autowired
	private NPCRepository repo;
	
	public static void main(String[] args) {
		SpringApplication.run(Adventure.class, args);
	}

	public void run(String... arg0) throws Exception {
		repo.deleteAll();
		
		repo.save(new NPC("Goblin", "Small and ugly"));
		
		for (NPC npc : repo.findAll()) {
			System.out.println("Found " + npc.toString());
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
