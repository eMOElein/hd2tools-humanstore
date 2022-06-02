package de.hd2tools.humanstore.core;

public class SavHeader {

	public static final byte[] SAV_HEADER = new byte[] { (byte) 0xE8, 0x03 };
	public static final byte[] SAVQUICK_HEADER = new byte[] { (byte) 0x8C, 0x4E };
	public static final byte[] SAV_HUMAN = HumanStoreHeader.HUMANSTORE_HUMAN;
}
