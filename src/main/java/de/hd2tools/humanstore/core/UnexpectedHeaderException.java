package de.hd2tools.humanstore.core;

import de.hd2tools.humanstore.misc.Utils;

public class UnexpectedHeaderException extends Exception {

	private static final long serialVersionUID = -5771280264123862511L;
	private byte[] actual;
	private int position;

	public UnexpectedHeaderException(byte[] actual, int position) {
		this.actual = actual;
		this.position = position;
	}

	public String getMessage() {
		String msg = "unexpected header %ACT% found at %AT%";
		msg = msg.replace("%AT%", "" + position);
		msg = msg.replace("%ACT%", Utils.bytesToHex(actual));
		return msg;
	}
}
