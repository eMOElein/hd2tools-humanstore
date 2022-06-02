package de.hd2tools.humanstore.core;

public class UnknownHumanProperty {

	private byte[] header;
	private int length;

	private byte[] content;

	public UnknownHumanProperty(byte[] header, int length, byte[] content) {
		this.header = header;
		this.content = content;
		this.length = length;
	}

	public byte[] getContent() {
		return content;
	}

	public byte[] getHeader() {
		return header;
	}

	public int getLength() {
		return length;
	}

}
