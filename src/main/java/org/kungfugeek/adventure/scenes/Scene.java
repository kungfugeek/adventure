package org.kungfugeek.adventure.scenes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kungfugeek.adventure.agents.NPC;
import org.kungfugeek.adventure.combat.CombatResult;
import org.kungfugeek.adventure.options.Option;

/**
 * adventure
 * Scene
 * @author Nate
 * Oct 9, 2016
 */
public class Scene {

	private String title;
	private String text;
	private List<Option> options;
	private List<Effect> effects;
	private List<NPC> npcs;
	private Map<CombatResult, String> combatResultMap;

	private Scene() {
		combatResultMap = new HashMap<CombatResult, String>(5);
		options = new ArrayList<Option>(3);
		effects = new ArrayList<Effect>(1);
		npcs = new ArrayList<NPC>(1);
	}
	
	private Scene(Builder builder) {
		this.title = builder.title;
		this.text = builder.text;
		this.options = Collections.unmodifiableList(builder.options);
		this.effects = Collections.unmodifiableList(builder.effects);
		this.npcs = Collections.unmodifiableList(builder.npcs);
		this.combatResultMap = Collections.unmodifiableMap(builder.combatResultMap);
	}
	
	public String getResult(CombatResult cr) {
		return combatResultMap.get(cr);
	}

	public Set<CombatResult> getValidCombatResults() {
		return new HashSet<CombatResult>(combatResultMap.keySet());
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return the options
	 */
	public List<Option> getOptions() {
		return options;
	}

	/**
	 * @return the effects
	 */
	public List<Effect> getEffects() {
		return effects;
	}

	/**
	 * @return the npcs
	 */
	public List<NPC> getNpcs() {
		return npcs;
	}

	public static class Builder {
		private String text;
		private String title;
		private List<Option> options;
		private List<Effect> effects;
		private List<NPC> npcs;
		private Map<CombatResult, String> combatResultMap;

		public Builder() {
			options = new ArrayList<Option>(3);
			effects = new ArrayList<Effect>(1);
			npcs = new ArrayList<NPC>(1);
			combatResultMap = new HashMap<CombatResult, String>(2);
		}
		
		public Builder title(String title) {
			this.title = title;
			return this;
		}
		
		public Builder text(String text) {
			this.text = text;
			return this;
		}

		public Builder option(Option opt) {
			this.options.add(opt);
			return this;
		}

		public Builder effect(Effect effect) {
			this.effects.add(effect);
			return this;
		}

		public Builder npc(NPC npc) {
			this.npcs.add(npc);
			return this;
		}

		public Builder result(CombatResult cr, String result) {
			this.combatResultMap.put(cr, result);
			return this;
		}

		public Scene build() {
			return new Scene(this);
		}
	}

}
