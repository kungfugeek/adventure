package org.kungfugeek.adventure.modifiers;

import org.kungfugeek.adventure.AgentAttribute;
import org.kungfugeek.adventure.agents.Agent;

import com.google.gson.annotations.SerializedName;

/**
 * adventure
 * Modifier
 * @author Nate
 * Oct 8, 2016
 */
public class Modifier {
	@SerializedName("attribute")
	private AgentAttribute attribute;
	@SerializedName("factor")
	private float factor;
	@SerializedName("mod")
	private int mod;
	
	public Modifier() {
		factor = 1.0f;
	}
	
	public AgentAttribute getAttribute() {
		return attribute;
	}

	/**
	 * @return the mod
	 */
	public int getMod() {
		return mod;
	}

	/**
	 * @return the factor
	 */
	public float getFactor() {
		return factor;
	}

}

