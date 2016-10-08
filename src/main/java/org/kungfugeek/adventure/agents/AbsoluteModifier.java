package org.kungfugeek.adventure.agents;

/**
 * adventure
 * Modifier
 * @author Nate
 * Oct 8, 2016
 */
public class AbsoluteModifier implements Modifier {
	private AgentAttribute attribute;
	private int mod;
	
	public AbsoluteModifier(AgentAttribute attribute, int mod) {
		super();
		this.attribute = attribute;
		this.mod = mod;
	}
	
	/* (non-Javadoc)
	 * @see org.kungfugeek.adventure.agents.Modifier#modify(int)
	 */
	public int modify(int baseValue) {
		return baseValue + mod;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AbsoluteModifier [attribute=" + attribute + ", mod=" + mod + "]";
	}

	/**
	 * @return the attribute
	 */
	public AgentAttribute getAttribute() {
		return attribute;
	}

	/**
	 * @return the mod
	 */
	public int getMod() {
		return mod;
	}

}

