package org.kungfugeek.adventure;

import static org.kungfugeek.adventure.AgentAttribute.AGILITY;
import static org.kungfugeek.adventure.AgentAttribute.STRENGTH;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kungfugeek.adventure.agents.Agent;
import org.kungfugeek.adventure.agents.AgentRepository;
import org.kungfugeek.adventure.agents.NPC;
import org.kungfugeek.adventure.agents.NPCRepository;
import org.kungfugeek.adventure.combat.CombatResult;
import org.kungfugeek.adventure.items.Item;
import org.kungfugeek.adventure.items.ItemRepository;
import org.kungfugeek.adventure.options.Option;
import org.kungfugeek.adventure.scenes.Effect;
import org.kungfugeek.adventure.scenes.Scene;
import org.kungfugeek.adventure.scenes.SceneRepository;
import org.kungfugeek.adventure.versioning.AdventureEngineVersion;
import org.kungfugeek.adventure.versioning.AdventureEngineVersionRepository;
import org.kungfugeek.adventure.versioning.DBUpdater;
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
	
	@Autowired
	private AgentRepository agentRepo;
	
	@Autowired
	private ItemRepository itemRepo;
	
	@Autowired
	private SceneRepository sceneRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(Adventure.class, args);
	}

	public void run(String... arg0) throws Exception {		
		dbUpdater.forceUpdate(VERSION);
		
	}

	/**
	 * 
	 */
	private void showDB() {
		for (AdventureEngineVersion version : versionRepo.findAll()) {
			log("Found Version " + version.getVersion() + "\n");
		}
		
		log("NPCs...");
		for (NPC npc : repo.findAll()) {
			log(npc.toString() + "\n");
		}
		
		log("Agents...");
		for (Agent agent : agentRepo.findAll()) {
			log(agent.toString() + "\n");
		}
		
		log("Items..");
		for (Item item : itemRepo.findAll()) {
			log(item.toString());
		}
	}
	
	private void log(Object o) {
		System.out.println(o.toString());
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
