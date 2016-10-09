package org.kungfugeek.adventure.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kungfugeek.adventure.modifiers.Modifier;
import org.kungfugeek.adventure.options.Option;
import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

/**
 * adventure Item
 * 
 * @author Nate Oct 9, 2016
 */
@Builder
@ToString(exclude = "id")
@EqualsAndHashCode(exclude = "id")
@Log4j
public class Item {

	@Id
	private String id;

	private @Getter String name;
	private @Getter String description;
	private @Getter boolean equipable;

	private @Getter @Singular List<Modifier> carriedMods;
	private @Getter @Singular List<Modifier> equippedMods;
	private @Getter @Singular List<Option> carriedOptions;
	private @Getter @Singular List<Option> equippedOptions;

	private Item() {}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the equipable
	 */
	public boolean isEquipable() {
		return equipable;
	}


	/**
	 * @return the carriedMods
	 */
	public List<Modifier> getCarriedMods() {
		return carriedMods;
	}

	/**
	 * @return the equippedMods
	 */
	public List<Modifier> getEquippedMods() {
		return equippedMods;
	}

	/**
	 * @return the carriedOptions
	 */
	public List<Option> getCarriedOptions() {
		return carriedOptions;
	}

	/**
	 * @return the equippedOptions
	 */
	public List<Option> getEquippedOptions() {
		return equippedOptions;
	}


	public static class Builder {
		private String name;
		private String description;
		private boolean equipable;
		private List<Modifier> carriedMods;
		private List<Modifier> equippedMods;
		private List<Option> carriedOptions;
		private List<Option> equippedOptions;

		public Builder() {
			super();
			carriedMods = new ArrayList<Modifier>(3);
			equippedMods = new ArrayList<Modifier>(3);
			carriedOptions = new ArrayList<Option>(3);
			equippedOptions = new ArrayList<Option>(3);
	}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder equipable(boolean equipable) {
			this.equipable = equipable;
			return this;
		}

		public Builder carriedMod(Modifier mod) {
			this.carriedMods.add(mod);
			return this;
		}

		public Builder equippedMod(Modifier mod) {
			this.equippedMods.add(mod);
			return this;
		}

		public Builder carriedOption(Option opt) {
			this.carriedOptions.add(opt);
			return this;
		}

		public Builder equippedOptions(Option opt) {
			this.equippedOptions.add(opt);
			return this;
		}

		public Item build() {
			return new Item(this);
		}
	}

	private Item(Builder builder) {
		this.name = builder.name;
		this.description = builder.description;
		this.equipable = builder.equipable;
		this.carriedMods = Collections.unmodifiableList(builder.carriedMods);
		this.equippedMods = Collections.unmodifiableList(builder.equippedMods);
		this.carriedOptions = Collections.unmodifiableList(builder.carriedOptions);
		this.equippedOptions = Collections.unmodifiableList(builder.equippedOptions);
	}
}
