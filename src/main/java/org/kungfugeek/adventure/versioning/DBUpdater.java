package org.kungfugeek.adventure.versioning;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kungfugeek.adventure.agents.Agent;
import org.kungfugeek.adventure.agents.AgentRepository;
import org.kungfugeek.adventure.agents.NPC;
import org.kungfugeek.adventure.agents.NPCRepository;
import org.kungfugeek.adventure.items.Item;
import org.kungfugeek.adventure.items.ItemRepository;
import org.kungfugeek.adventure.scenes.ScenePack;
import org.kungfugeek.adventure.scenes.ScenePackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

/**
 * adventure
 * DBUpdater
 * @author Nate
 * Oct 8, 2016
 */
@Component
public class DBUpdater {
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(DBUpdater.class);

	@Autowired
	private AdventureEngineVersionRepository versionRepo;
	
	@Autowired
	private NPCRepository npcRepo;
	
	@Autowired
	private AgentRepository agentRepo;
	
	@Autowired
	private ItemRepository itemRepo;
	
	@Autowired
	private ScenePackRepository sceneRepo;
	
	private Gson gson;

	private final ClassLoader classLoader;

	private AdventureEngineVersion currentVersion;

	
	
	/**
	 * 
	 */
	public DBUpdater() {
		classLoader = getClass().getClassLoader();
		GsonBuilder builder = new GsonBuilder();
		gson = builder.create();
		
	}

	@PostConstruct
	private void init() {
		currentVersion = versionRepo.findOne(AdventureEngineVersion.SINGLETON_ID);
	}
	
	public void ensureVersion(String targetVersion) throws Exception {
		if (currentVersion == null 
			|| !targetVersion.equals(currentVersion.getVersion())) 
		{
			forceUpdate(targetVersion);
		}
	}
	
	public void forceUpdate(String targetVersion) throws Exception {
		clearRepos();
		updateNPCRepo();
		updateAgentRepo();
		updateItemRepo();
		updateSceneRepo();
		updateVersion(targetVersion);
	}

	/**
	 * 
	 */
	private void clearRepos() {
		npcRepo.deleteAll();
		agentRepo.deleteAll();
		itemRepo.deleteAll();
		sceneRepo.deleteAll();
	}
	
	public void updateVersion(String targetVersion) {
		currentVersion = new AdventureEngineVersion(targetVersion);
		versionRepo.save(currentVersion);
	}
	
	public void updateNPCRepo() throws Exception {
		JsonReader jsonReader = getJSONReader("npc.json");
		NPC[] npcs = gson.fromJson(jsonReader, NPC[].class);
		List<NPC> npcList = Arrays.asList(npcs);
		npcRepo.save(npcList);
	}
	
	public void updateItemRepo() throws Exception {
		JsonReader jsonReader = getJSONReader("items.json");
		Item[] items = gson.fromJson(jsonReader, Item[].class);
		List<Item> itemList = Arrays.asList(items);
		itemRepo.save(itemList);
	}
	
	public void updateAgentRepo() throws Exception {
		JsonReader jsonReader = getJSONReader("agent.json");
		Agent[] agents = gson.fromJson(jsonReader, Agent[].class);
		List<Agent> agentList = Arrays.asList(agents);
		agentRepo.save(agentList);
	}
	
	public void updateSceneRepo() throws Exception {
		JsonReader jsonReader = getJSONReader("scenes.json");
		ScenePack[] scenes = gson.fromJson(jsonReader, ScenePack[].class);
		List<ScenePack> sceneList = Arrays.asList(scenes);
		sceneRepo.save(sceneList);
	}
	
	/**
	 * @param filename
	 * @return
	 * @throws URISyntaxException
	 * @throws FileNotFoundException
	 */
	private JsonReader getJSONReader(String filename) throws URISyntaxException, FileNotFoundException {
		URL resource = classLoader.getResource(filename);
		File file = new File(resource.toURI());
		JsonReader jsonReader = gson.newJsonReader(new BufferedReader(new FileReader(file)));
		return jsonReader;
	}
}

