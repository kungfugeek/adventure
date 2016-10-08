package org.kungfugeek.adventure.agents;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Nate
 *
 */
@Document(collection = "Agent")
public class NPC extends Agent {
	
	private String description;
	
	private NPC() {}
	
	private NPC(Agent agent, String description) {
		super(agent);
		this.description = description;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NPC " + super.toString() + " [description=" + description + "]";
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	public static class NPCBuilder extends AgentBuilder {
		private String description;

		public NPCBuilder() {
			super();
		}
		
		public NPC build() {
			return new NPC(super.build(), this.description);
		}

		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}

		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
	}

}
