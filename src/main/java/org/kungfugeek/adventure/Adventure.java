package org.kungfugeek.adventure;

import static org.kungfugeek.adventure.AgentAttribute.AGILITY;
import static org.kungfugeek.adventure.AgentAttribute.LIFE;
import static org.kungfugeek.adventure.AgentAttribute.STRENGTH;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kungfugeek.adventure.agents.Agent;
import org.kungfugeek.adventure.agents.AgentRepository;
import org.kungfugeek.adventure.agents.NPC;
import org.kungfugeek.adventure.agents.NPCRepository;
import org.kungfugeek.adventure.items.Item;
import org.kungfugeek.adventure.items.ItemRepository;
import org.kungfugeek.adventure.modifiers.AbsoluteModifier;
import org.kungfugeek.adventure.modifiers.RelativeModifier;
import org.kungfugeek.adventure.options.Option;
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
	
	public static void main(String[] args) {
		SpringApplication.run(Adventure.class, args);
	}

	public void run(String... arg0) throws Exception {		
		showDB();
		dbUpdater.forceUpdate(VERSION);
		showDB();
//		
//		Item item = new Item.Builder()
//				.name("Sword")
//				.description("A sword.")
//				.equipable(true)
//				.equippedMod(new RelativeModifier(AgentAttribute.STRENGTH, 1.5f))
//				.equippedOptions(new Option.Builder()
//						.optionText("Swing it.")
//						.attemptText("You try to swing.")
//						.failText("You missed.")
//						.passText("You hit")
//						.forAdventure(false)
//						.forCombat(true)
//						.prereq("Excited")
//						.prereq(AGILITY, 2, null)
//						.test(STRENGTH, 4)
//						.build())
//				.build();
//		itemRepo.save(item);
//		showDB();
		
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
