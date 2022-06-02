package de.hd2tools.humanstore.core;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Stats {

	private IntegerProperty biographyIdProperty = new SimpleIntegerProperty();
	private FloatProperty healthProperty = new SimpleFloatProperty();
	private FloatProperty strengthProperty = new SimpleFloatProperty();
	private FloatProperty enduranceProperty = new SimpleFloatProperty();
	private FloatProperty moraleProperty = new SimpleFloatProperty();
	private FloatProperty shootingProperty = new SimpleFloatProperty();
	private FloatProperty experienceProperty = new SimpleFloatProperty();
	private FloatProperty stealthProperty = new SimpleFloatProperty();
	private FloatProperty firstAidProperty = new SimpleFloatProperty();
	private FloatProperty lockpickingProperty = new SimpleFloatProperty();

	public IntegerProperty biographyIdProperty() {
		return biographyIdProperty;
	}

	public FloatProperty enduranceProperty() {
		return enduranceProperty;
	}

	public FloatProperty experienceProperty() {
		return experienceProperty;
	}

	public FloatProperty firstAidProperty() {
		return firstAidProperty;
	}

	public int getBiographyId() {
		return biographyIdProperty().get();
	}

	public float getEndurance() {
		return enduranceProperty().get();
	}

	public float getExperience() {
		return experienceProperty().get();
	}

	public float getFirstAid() {
		return firstAidProperty().get();
	}

	public float getHealth() {
		return healthProperty().get();
	}

	public float getLockpicking() {
		return lockpickingProperty().get();
	}

	public float getMorale() {
		return moraleProperty().get();
	}

	public float getShooting() {
		return shootingProperty().get();
	}

	public float getStealth() {
		return stealthProperty().get();
	}

	public float getStrength() {
		return strengthProperty().get();
	}

	public FloatProperty healthProperty() {
		return healthProperty;
	}

	public FloatProperty lockpickingProperty() {
		return lockpickingProperty;
	}

	public FloatProperty moraleProperty() {
		return moraleProperty;
	}

	public void setBiographyId(int biographyId) {
		biographyIdProperty().set(biographyId);
	}

	public void setEndurance(float endurance) {
		enduranceProperty().set(endurance);
	}

	public void setExperience(float experience) {
		experienceProperty().set(experience);
	}

	public void setFirstAid(float firstAid) {
		firstAidProperty().set(firstAid);
	}

	public void setHealth(float health) {
		healthProperty().set(health);
	}

	public void setLockpicking(float lockpicking) {
		lockpickingProperty().set(lockpicking);
	}

	public void setMorale(float morale) {
		moraleProperty().set(morale);
	}

	public void setShooting(float shooting) {
		shootingProperty().set(shooting);
	}

	public void setStealth(float stealth) {
		stealthProperty().set(stealth);
	}

	public void setStrength(float strength) {
		strengthProperty().set(strength);
	}

	public FloatProperty shootingProperty() {
		return shootingProperty;
	}

	public FloatProperty stealthProperty() {
		return stealthProperty;
	}

	public FloatProperty strengthProperty() {
		return strengthProperty;
	}

}
