package de.hd2tools.humanstore.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.apache.commons.io.FilenameUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.hd2tools.humanstore.core.Human;
import de.hd2tools.humanstore.core.HumanStore;
import de.hd2tools.humanstore.core.HumanStoreHeader;
import de.hd2tools.humanstore.core.SavHeader;
import de.hd2tools.humanstore.misc.Utils;

public class HumanStoreIO {

	public static HumanStore read(File file) throws Exception {

		String extensions = FilenameUtils.getExtension(file.getName());

		switch (extensions) {
		case "json":
			return readFromJson(new FileInputStream(file));
		default:
			return readFromBinary(new BufferedInputStream(new FileInputStream(file)));
		}
	}

	public static HumanStore readFromBinary(InputStream is) throws Exception {
		is.mark(2);
		byte[] header = new byte[2];
		is.read(header);
		is.reset();

		if (Arrays.equals(HumanStoreHeader.HUMANSTORE_HEADER, header)) {
			return readFromDef(is);
		}

		if (Arrays.equals(SavHeader.SAV_HEADER, header)) {
			return readFromSav(is);
		}
		
		if (Arrays.equals(SavHeader.SAVQUICK_HEADER, header)) {
			return readFromSav(is);
		}

		throw new Exception("unknown header " + Utils.bytesToHex(header));
	}

	public static HumanStore readFromDef(InputStream is) throws Exception {
		return new HumanStoreReader().read(is);
	}

	public static HumanStore readFromJson(InputStream is) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(is, HumanStore.class);
	}

	public static HumanStore readFromSav(InputStream is) throws Exception {
		return new HumanStoreReader().readFromSav(is);
	}

	public static Human readHuman(File file) throws Exception {
		String extension = FilenameUtils.getExtension(file.getName());
		extension = extension.toLowerCase();

		FileInputStream in = new FileInputStream(file);
		Human human = null;

		switch (extension) {
		case "json":
			human = readHumanFromJson(in);
			in.close();
			break;
		case "def":
			human = readHumanFromBin(in);
			in.close();
			break;
		default:
			in.close();
			throw new Exception("unknown file extension \"" + extension + "\"");
		}

		return human;
	}

	public static Human readHumanFromBin(InputStream is) throws Exception {
		Human human = new HumanStoreReader().readHuman(new BufferedInputStream(is));
		is.close();
		return human;
	}

	public static Human readHumanFromJson(InputStream is) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper humanMapper = new ObjectMapper();
		Human human = humanMapper.readValue(is, Human.class);
		return human;
	}

	public static void write(HumanStore humanStore, File file) throws Exception {
		String extension = FilenameUtils.getExtension(file.getName());
		System.out.println(file.getName());
		switch (extension) {
		case "json":
			new HumanStoreWriter().writeJson(humanStore, new FileOutputStream(file));
			return;
		case "def":
			new HumanStoreWriter().write(humanStore, new FileOutputStream(file));
			return;
		default:
			throw new Exception("unknown extension \"" + extension + "\"");
		}
	}

	public static void writeHumanToBin(Human human, OutputStream out) throws UnsupportedEncodingException, IOException {
		new HumanStoreWriter().writeHuman(human, out);
	}

	public static void writeHumanToJson(Human human, OutputStream out)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper humanMapper = new ObjectMapper();
		humanMapper.writeValue(out, human);
		out.close();
	}

	public static void writer(HumanStore humanStore, OutputStream out) throws IOException {
		new HumanStoreWriter().write(humanStore, out);
	}

}
