package org.kungfugeek.adventure.agents;

/**
 * adventure
 * Modifier
 * @author Nate
 * Oct 8, 2016
 */
public class RelativeModifier extends Modifier {
	private float modFactor;
	
	public RelativeModifier() {}
	
	public RelativeModifier(AgentAttribute attribute, float factor) {
		super(attribute);
		this.modFactor = factor;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RelativeModifier [attribute=" + getAttribute() + ", factor=" + modFactor + "]";
	}
	
	/**
	 * @return the factor
	 */
	public float getFactor() {
		return modFactor;
	}

	
}

