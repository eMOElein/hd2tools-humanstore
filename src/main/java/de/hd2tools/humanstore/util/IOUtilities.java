package de.hd2tools.humanstore.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class IOUtilities {

	private static final byte NULL = 0x00;

	public static String fromCString(byte[] bytes, String charset) throws UnsupportedEncodingException {
		int length = bytes.length;
		if (bytes[length - 1] == NULL) {
			if (length == 1) {
				bytes = new byte[0];
			} else {
				bytes = Arrays.copyOf(bytes, length - 1);
			}
		}
		return new String(bytes, charset);
	}

	public static byte[] readBytes(InputStream dis, int byteCount) throws IOException {
		byte[] bytes = new byte[byteCount];
		dis.read(bytes);
		return bytes;
	}

	public static int readBytesAndReset(InputStream i, byte[] bytes) throws IOException {
		i.mark(bytes.length);
		int read = i.read(bytes);
		i.reset();
		return read;
	}

	public static byte[] readBytes(File file, int byteCount) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		byte[] bytes = new byte[byteCount];
		readBytes(fis, byteCount);
		fis.close();
		return bytes;
	}

	public static float readFloatAsLittleEndian(byte[] bytes) {
		return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat();
	}

	public static int readIntAsLittleEndian(byte[] bytes) {
		return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}

	public static byte[] toCString(String string, String charset) throws UnsupportedEncodingException {
		byte[] bytes = string.getBytes(charset);
		int length = bytes.length;

		if (length == 0) {
			bytes = new byte[] { NULL };
		} else {
			if (bytes[length - 1] != NULL) {
				bytes = Arrays.copyOf(bytes, length + 1);
				bytes[length] = NULL;
			}
		}

		return bytes;
	}

	public static boolean forwardTo(InputStream in, byte[] bytes) throws IOException {
		if (!in.markSupported()) {
			throw new IOException("mark is not supported on the given stream");
		}

		byte[] buffer = new byte[2];

		while (in.available() > 0) {
			in.mark(Integer.MAX_VALUE);
			in.read(buffer);
			if (Arrays.equals(buffer, bytes)) {
				in.reset();
				return true;
			}

			in.reset();
			in.read();
		}

		return false;

	}

	public static byte[] writeAsLittleEndian(float f) {
		return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(f).array();
	}

	public static byte[] writeAsLittleEndian(int i) {
		return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(i).array();
	}

	public static void shift(byte[] bytes, InputStream is, int bytesToShift) throws IOException {

		for (int i = 0; i < bytesToShift; i++) {
			for (int j = 0; j < bytes.length - 1; j++) {
				bytes[j] = bytes[j + 1];
			}
			bytes[bytes.length - 1] = (byte) is.read();
		}
	}

}
