package org.kungfugeek.adventure.agents;

/**
 * adventure
 * Modifier
 * @author Nate
 * Oct 8, 2016
 */
public class RelativeModifier implements Modifier {
	private AgentAttribute attribute;
	private float factor;
	
	public RelativeModifier(AgentAttribute attribute, float factor) {
		super();
		this.attribute = attribute;
		this.factor = factor;
	}
	
	/* (non-Javadoc)
	 * @see org.kungfugeek.adventure.agents.Modifier#modify(int)
	 */
	public int modify(int baseValue) {
		return Math.round(baseValue * factor);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RelativeModifier [attribute=" + attribute + ", factor=" + factor + "]";
	}

	/**
	 * @return the attribute
	 */
	public AgentAttribute getAttribute() {
		return attribute;
	}
	
	/**
	 * @return the factor
	 */
	public float getFactor() {
		return factor;
	}

	
}

