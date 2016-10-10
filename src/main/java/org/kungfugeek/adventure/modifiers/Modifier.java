package org.kungfugeek.adventure.modifiers;

import org.kungfugeek.adventure.AgentAttribute;

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
	
	private Modifier(Builder builder) {
		this.attribute = builder.attribute;
		this.factor = builder.factor;
		this.mod = builder.mod;
	}
	
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

	public static class Builder {
		private AgentAttribute attribute;
		private float factor;
		private int mod;

		public Builder attribute(AgentAttribute attribute) {
			this.attribute = attribute;
			return this;
		}

		public Builder factor(float factor) {
			this.factor = factor;
			return this;
		}

		public Builder mod(int mod) {
			this.mod = mod;
			return this;
		}

		public Modifier build() {
			return new Modifier(this);
		}
	}

}
