package org.kungfugeek.adventure.options;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kungfugeek.adventure.AgentAttribute;
import org.kungfugeek.adventure.agents.Agent;

import com.google.gson.annotations.SerializedName;

/**
 * adventure
 * Option
 * @author Nate
 * Oct 9, 2016
 */
public class Option {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(Option.class);

	private static final Random rand = new Random();

	private static class Prerequisite {
		@SerializedName("att")
		private AgentAttribute att;
		@SerializedName("min")
		private Integer min;
		@SerializedName("max")
		private Integer max;
		@SerializedName("flag")
		private String flag;
		
		public Prerequisite(AgentAttribute att, Integer min, Integer max) {
			super();
			this.att = att;
			this.min = min;
			this.max = max;
		}

		public Prerequisite(String flag) {
			super();
			this.flag = flag;
		}
		
		@SuppressWarnings("unused")
		public Prerequisite() {}
		
		public boolean meets(Agent agent) {
			int effectiveAtt = agent.getEffectiveAttribute(att);
			return meetsMax(effectiveAtt) && meetsMin(effectiveAtt) && meetsFlag(agent);
		}

		private boolean meetsMin(int effectiveAtt) {
			return min == null || effectiveAtt >= min;
		}

		private boolean meetsMax(int effectiveAtt) {
			return max == null || effectiveAtt <= max;
		}
		
		public boolean meetsFlag(Agent agent) {
			return agent.hasFlag(flag);
		}
	}
	
	private static class PassiveTest {
		private final AgentAttribute testedAttribute;
		private final int difficulty;

		/**
		 * @param att
		 * @param diff
		 */
		public PassiveTest(AgentAttribute testedAttribute, int difficulty) {
			this.testedAttribute = testedAttribute;
			this.difficulty = difficulty;
		}

		public boolean attempt(Agent agent) {
			int effectiveAtt = agent.getEffectiveAttribute(testedAttribute);
			return rand.nextInt(effectiveAtt + difficulty) < effectiveAtt;
		}
	}
	
	private static class OpposedTest {
		private final AgentAttribute testedAttribute;
		private final AgentAttribute opposingAttribute;
		
		public OpposedTest(AgentAttribute testedAttribute, AgentAttribute opposingAttribute) {
			super();
			this.testedAttribute = testedAttribute;
			this.opposingAttribute = opposingAttribute;
		}
		
		public boolean attempt(Agent actor, Agent opposer) {
			int effectiveActorAtt = actor.getEffectiveAttribute(testedAttribute);
			int opposingAtt = opposer.getEffectiveAttribute(opposingAttribute);
			return rand.nextInt(effectiveActorAtt + opposingAtt) < opposingAtt;
		}
	}

	private List<Prerequisite> prereqs;
	private List<PassiveTest> passiveTests;
	private List<OpposedTest> opposedTests;
	private String optionText;
	private String attemptText;
	private String passText;
	private String failText;
	private String passTargetScene;
	private String failTargetScene;
	private boolean forCombat;
	private boolean forAdventure;

	/**
	 * @return the forCombat
	 */
	public boolean isForCombat() {
		return forCombat;
	}

	/**
	 * @return the forAdventure
	 */
	public boolean isForAdventure() {
		return forAdventure;
	}

	private Option() {
		prereqs = new ArrayList<>(3);
		passiveTests = new ArrayList<>(1);
		opposedTests = new ArrayList<>(1);
	}

	public boolean meetsPrereqs(Agent agent) {
		boolean meets = true;
		for (Prerequisite prerequisite : prereqs) {
			meets = meets && prerequisite.meets(agent);
		}
		return meets;
	}

	public boolean attempt(Agent actor, Agent opposer) {
		boolean success = true;
		for (PassiveTest test : passiveTests) {
			success = success && test.attempt(actor);
		}
		for (OpposedTest test : opposedTests) {
			success = success && test.attempt(actor, opposer);
		}
		return success;
	}

	/**
	 * @return the passTargetScene
	 */
	public String getPassTargetScene() {
		return passTargetScene;
	}

	/**
	 * @return the failTargetScene
	 */
	public String getFailTargetScene() {
		return failTargetScene;
	}

	/**
	 * @return the optionText
	 */
	public String getOptionText() {
		return optionText;
	}

	/**
	 * @return the attemptText
	 */
	public String getAttemptText() {
		return attemptText;
	}

	/**
	 * @return the passText
	 */
	public String getPassText() {
		return passText;
	}

	/**
	 * @return the failText
	 */
	public String getFailText() {
		return failText;
	}

	public static class Builder {
		private List<Prerequisite> prereqs;
		private List<PassiveTest> passiveTests;
		private List<OpposedTest> opposedTests;
		private String optionText;
		private String attemptText;
		private String passText;
		private String failText;
		private String passScene;
		private String failScene;
		private boolean forCombat;
		private boolean forAdventure;

		public Builder() {
			prereqs = new ArrayList<>(1);
			passiveTests = new ArrayList<>(1);
			opposedTests = new ArrayList<>(1);
		}
		
		public Builder prereq(String flag) {
			prereqs.add(new Prerequisite(flag));
			return this;
		}

		public Builder prereq(AgentAttribute att, Integer min, Integer max) {
			prereqs.add(new Prerequisite(att, min, max));
			return this;
		}

		public Builder passiveTest(AgentAttribute att, int diff) {
			this.passiveTests.add(new PassiveTest(att, diff));
			return this;
		}
		
		public Builder opposedTest(AgentAttribute actorAtt, AgentAttribute opposingAtt) {
			this.opposedTests.add(new OpposedTest(actorAtt, opposingAtt));
			return this;
		}

		public Builder optionText(String optionText) {
			this.optionText = optionText;
			return this;
		}

		public Builder attemptText(String attemptText) {
			this.attemptText = attemptText;
			return this;
		}

		public Builder passText(String passText) {
			this.passText = passText;
			return this;
		}

		public Builder failText(String failText) {
			this.failText = failText;
			return this;
		}

		/**
		 * @param passScene the passScene to set
		 */
		public Builder passScene(String passScene) {
			this.passScene = passScene;
			return this;
		}

		/**
		 * @param failScene the failScene to set
		 */
		public Builder failScene(String failScene) {
			this.failScene = failScene;
			return this;
		}

		public Builder forCombat(boolean forCombat) {
			this.forCombat = forCombat;
			return this;
		}

		public Builder forAdventure(boolean forAdventure) {
			this.forAdventure = forAdventure;
			return this;
		}

		public Option build() {
			return new Option(this);
		}
	}

	private Option(Builder builder) {
		this.prereqs = Collections.unmodifiableList(builder.prereqs);
		this.passiveTests = Collections.unmodifiableList(builder.passiveTests);
		this.optionText = builder.optionText;
		this.attemptText = builder.attemptText;
		this.passText = builder.passText;
		this.passTargetScene = builder.passScene;
		this.failText = builder.failText;
		this.failTargetScene = builder.failScene;
		this.forCombat = builder.forCombat;
		this.forAdventure = builder.forAdventure;
	}
}
