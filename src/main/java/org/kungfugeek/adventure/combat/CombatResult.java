package org.kungfugeek.adventure.combat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.kungfugeek.adventure.agents.NPC;

/**
 * adventure
 * CombatResult
 * @author Nate
 * Oct 9, 2016
 */
public class CombatResult {
	
	public static enum Outcome {
		PLAYER_VICTORY,
		ENEMY_VICTORY,
		PLAYER_FLEE,
		ENEMY_FLEE,
		PLAYER_SURRENDER,
		ENEMY_SURRENDER;
		
		public CombatResult create() {
			return new CombatResult(this);
		}
	}
	
	private final List<NPC> enemiesAlive;
	private final Outcome outcome;

	private CombatResult(Outcome outcome) {
		super();
		this.outcome = outcome;
		this.enemiesAlive = new ArrayList<>();
	}
	
	public void addEnemies(NPC... npcs) {
		if (npcs != null) {
			enemiesAlive.addAll(Arrays.asList(npcs));
		}
	}
	
	public List<NPC> getEnemies() {
		return enemiesAlive;
	}
	
	public Outcome getOutcome() {
		return outcome;
	}
}

