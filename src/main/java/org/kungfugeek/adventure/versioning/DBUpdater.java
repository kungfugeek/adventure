package org.kungfugeek.adventure.versioning;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kungfugeek.adventure.agents.NPC;
import org.kungfugeek.adventure.agents.NPCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
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
	
	private Gson gson = new Gson();

	private final ClassLoader classLoader;

	private AdventureEngineVersion currentVersion;
	
	/**
	 * 
	 */
	public DBUpdater() {
		classLoader = getClass().getClassLoader();
	}

	@PostConstruct
	private void init() {
		currentVersion = versionRepo.findOne(AdventureEngineVersion.SINGLETON_ID);
	}
	
	/**
	 * @param versionRepo the versionRepo to set
	 */
	public void setVersionRepo(AdventureEngineVersionRepository versionRepo) {
		this.versionRepo = versionRepo;
	}

	/**
	 * @param npcRepo the npcRepo to set
	 */
	public void setNpcRepo(NPCRepository npcRepo) {
		this.npcRepo = npcRepo;
	}

	public void initializeToVersion(String targetVersion) throws Exception {
		if (currentVersion == null 
			|| !targetVersion.equals(currentVersion.getVersion())) 
		{
			updateAll(targetVersion);
		}
	}
	
	public void updateAll(String targetVersion) throws Exception {
		updateNPCRepo(targetVersion);
		updateVersion(targetVersion);
	}
	
	public void updateVersion(String targetVersion) {
		currentVersion = new AdventureEngineVersion(targetVersion);
		versionRepo.save(currentVersion);
	}
	
	public void updateNPCRepo(String targetVersion) throws Exception {
		npcRepo.deleteAll();
		JsonReader jsonReader = getJSONReader("npc.json");
		NPC[] npcs = gson.fromJson(jsonReader, NPC[].class);
		npcRepo.insert(Arrays.asList(npcs));
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

