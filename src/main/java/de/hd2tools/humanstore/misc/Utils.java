package de.hd2tools.humanstore.misc;

import java.io.IOException;
import java.io.Writer;

import de.hd2tools.humanstore.core.Human;
import de.hd2tools.humanstore.core.Stats;

public class Utils {

	public static String bytesToHex(byte[] bytes) {
		char[] hexArray = "0123456789ABCDEF".toCharArray();
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
	
	public static void printHumanInfo(Human human, Writer writer) throws IOException {
			writer.write("Firstname: " + human.getFirstname() + "\n");
			writer.write("Surname: " + human.getSurname()+ "\n");
			writer.write("Nichname: " + human.getNickname()+ "\n");
			writer.write("Portrait: " + human.getPortrait()+ "\n");
			writer.write("Rank: " + human.getRank()+ "\n");
			writer.write("Unknown 1: " + bytesToHex(human.getUnknown1()) + "\n");
			writer.write("Unknown 2: " + bytesToHex(human.getUnknown1()) + "\n");
			writer.write("Unknown 3: " + bytesToHex(human.getUnknown1()) + "\n");

			Stats status = human.getStatus();
			writer.write("Biography ID: " + status.getBiographyId() + "\n");
			writer.write("Health: " + status.getHealth() + "\n");
			writer.write("Shooting: " + status.getShooting() + "\n");
			writer.write("Endurance: " + status.getEndurance() + "\n");
			writer.write("Experience: " + status.getExperience() + "\n");
			writer.write("First Aid: " + status.getFirstAid() + "\n");
			writer.write("Lockpicking:" + status.getLockpicking() + "\n");
			writer.write("Morale: " + status.getMorale() + "\n");
			writer.write("Stealth: " + status.getStealth() + "\n");
			writer.write("Strength: " + status.getStrength() + "\n");
			writer.flush();
	}

}
