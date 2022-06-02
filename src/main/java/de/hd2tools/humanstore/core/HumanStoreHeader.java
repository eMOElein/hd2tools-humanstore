package de.hd2tools.humanstore.core;

public class HumanStoreHeader {
	public static final byte[] HUMANSTORE_HEADER = new byte[] { (byte) 0x9A, 0x02 };
	public static final byte[] HUMANSTORE_HUMAN = new byte[] { 0x28, 0x23 };
	public static final byte[] HUMAN_FIRSTNAME = new byte[] { (byte) 0x96, 0x23 };
	public static final byte[] HUMAN_SURNAME = new byte[] { (byte) 0xA0, 0x23 };
	public static final byte[] HUMAN_NICKNAME = new byte[] { (byte) 0xAA, 0x23 };
	public static final byte[] HUMAN_PORTRAIT = new byte[] { (byte) 0xF0, 0x23 };
	public static final byte[] HUMAN_RANK = new byte[] { (byte) 0xDE, 0x26 };
	public static final byte[] HUMAN_UNKNOWN1 = new byte[] { (byte) 0xF3, 0x26 };
	public static final byte[] HUMAN_UNKNOWN2 = new byte[] { (byte) 0xF4, 0x26 };
	public static final byte[] HUMAN_UNKNOWN3 = new byte[] { (byte) 0xF5, 0x26 };
	public static final byte[] HUMAN_STATS = new byte[] { (byte) 0xAD, 0x26 };
}
