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
import org.kungfugeek.adventure.modifiers.Modifier;
import org.kungfugeek.adventure.options.Option;
import org.kungfugeek.adventure.scenes.Effect;
import org.kungfugeek.adventure.scenes.Scene;
import org.kungfugeek.adventure.scenes.ScenePack;
import org.kungfugeek.adventure.scenes.ScenePackRepository;
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
	private ScenePackRepository sceneRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(Adventure.class, args);
	}

	public void run(String... arg0) throws Exception {		
		dbUpdater.forceUpdate(VERSION);		
	}

	/**
	 * 
	 */
	protected void createScene() {
		ScenePack sp = new ScenePack.Builder()
				.title("Wall Scene")
				.openingScene(new Scene.Builder()
						.title("Opening Wall Scene")
						.text("You are standing in front of a wall.")
						.npc((NPC)new NPC.NPCBuilder()
								.description("A cute little robot.")
								.name("Wally")
								.flag("Boxy")
								.attribute(STRENGTH, 1)
								.build())
						.effect(new Effect.Builder()
								.flag("Blocked")
								.build())
						.option(new Option.Builder()
								.optionText("Climb it.")
								.attemptText("You try to climb it.")
								.failScene("Fell")
								.passScene("Climbed")
								.prereq("Blocked")
								.prereq(STRENGTH, 2, null)
								.test(STRENGTH, 5)
								.build())
						.option(new Option.Builder()
								.optionText("Go around it.")
								.passScene("Walked")
								.build())
						.result(CombatResult.ENEMY_FLEE, "They Flee")
						.result(CombatResult.PLAYER_VICTORY, "You Win")
						.build())
				.scene(new Scene.Builder()
						.title("Climbed")
						.text("You are standing on top of a wall.")
						.effect(new Effect.Builder()
								.flag("High")
								.build())
						.build())
				.scene(new Scene.Builder()
						.title("Fell")
						.text("You fell down and got hurt on your butt.")
						.effect(new Effect.Builder()
								.mod(new Modifier.Builder()
										.attribute(AGILITY)
										.mod(-1)
										.build())
								.build())
						.build())
				.scene(new Scene.Builder()
						.title("Walked")
						.text("You walked around and got tired.")
						.effect(new Effect.Builder()
								.bump(AGILITY, -1)
								.build())
						.build())
				.build();
		
		sceneRepo.save(sp);
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
