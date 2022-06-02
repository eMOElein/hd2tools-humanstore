package de.hd2tools.humanstore.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.hd2tools.humanstore.core.Human;
import de.hd2tools.humanstore.core.HumanStore;
import de.hd2tools.humanstore.core.HumanStoreHeader;
import de.hd2tools.humanstore.core.Stats;
import de.hd2tools.humanstore.util.IOUtilities;

public class HumanStoreWriter {
	public static final String CHARSET = "ISO-8859-2";

	private static byte[] getLength(byte[] header, byte[] content) {
		int totalLength = header.length + content.length + 4;
		return IOUtilities.writeAsLittleEndian(totalLength);
	}

	private void write(byte[] header, byte[] content, OutputStream out) throws IOException {
		out.write(header);
		out.write(getLength(header, content));
		out.write(content);
	}

	public void write(HumanStore humanStore, OutputStream out) throws IOException {
		ByteArrayOutputStream baosHumans = new ByteArrayOutputStream();

		for (Human h : humanStore.getHumans()) {
			baosHumans.write(writeHumanToByteArray(h));
		}

		byte[] content = baosHumans.toByteArray();

		write(HumanStoreHeader.HUMANSTORE_HEADER, content, out);
		out.close();
	}

	public void writeHuman(Human human, OutputStream out) throws UnsupportedEncodingException, IOException {
		ByteArrayOutputStream baosContent = new ByteArrayOutputStream();

		write(HumanStoreHeader.HUMAN_FIRSTNAME, IOUtilities.toCString(human.getFirstname(), CHARSET), baosContent);
		;
		write(HumanStoreHeader.HUMAN_SURNAME, IOUtilities.toCString(human.getSurname(), CHARSET), baosContent);
		write(HumanStoreHeader.HUMAN_NICKNAME, IOUtilities.toCString(human.getNickname(), CHARSET), baosContent);
		write(HumanStoreHeader.HUMAN_PORTRAIT, IOUtilities.toCString(human.getPortrait(), CHARSET), baosContent);
		write(HumanStoreHeader.HUMAN_RANK, IOUtilities.writeAsLittleEndian(human.getRank().getRankId()), baosContent);
		write(HumanStoreHeader.HUMAN_UNKNOWN1, human.getUnknown1(), baosContent);
		write(HumanStoreHeader.HUMAN_UNKNOWN2, human.getUnknown2(), baosContent);
		write(HumanStoreHeader.HUMAN_UNKNOWN3, human.getUnknown3(), baosContent);
		baosContent.write(writeHumanStatusToByteArray(human.getStatus()));

		byte[] content = baosContent.toByteArray();

		write(HumanStoreHeader.HUMANSTORE_HUMAN, content, out);
	}

	private byte[] writeHumanStatusToByteArray(Stats status) throws IOException {
		ByteArrayOutputStream baosContent = new ByteArrayOutputStream();
		baosContent.write(IOUtilities.writeAsLittleEndian(status.getBiographyId()));
		baosContent.write(IOUtilities.writeAsLittleEndian(status.getHealth()));
		baosContent.write(IOUtilities.writeAsLittleEndian(status.getStrength()));
		baosContent.write(IOUtilities.writeAsLittleEndian(status.getEndurance()));
		baosContent.write(IOUtilities.writeAsLittleEndian(status.getMorale()));
		baosContent.write(IOUtilities.writeAsLittleEndian(status.getShooting()));
		baosContent.write(IOUtilities.writeAsLittleEndian(status.getExperience()));
		baosContent.write(IOUtilities.writeAsLittleEndian(status.getStealth()));
		baosContent.write(IOUtilities.writeAsLittleEndian(status.getFirstAid()));
		baosContent.write(IOUtilities.writeAsLittleEndian(status.getLockpicking()));

		byte[] content = baosContent.toByteArray();

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		write(HumanStoreHeader.HUMAN_STATS, content, out);

		return out.toByteArray();
	}

	private byte[] writeHumanToByteArray(Human human) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		writeHuman(human, baos);
		return baos.toByteArray();
	}

	public void writeJson(HumanStore humanStore, OutputStream out)
			throws JsonGenerationException, JsonMappingException, IOException {
		new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(out, humanStore);
	}
}
