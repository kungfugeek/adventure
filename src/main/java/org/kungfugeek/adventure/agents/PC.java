package org.kungfugeek.adventure.agents;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * adventure
 * PC
 * @author Nate
 * Oct 10, 2016
 */
public class PC extends Agent {

	private String currentScene;
	private List<NPC> currentNPCs;
	private boolean inCombat;
	private List<String> seenPacks;
}

