package de.hd2tools.humanstore.io;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import de.hd2tools.humanstore.core.Human;
import de.hd2tools.humanstore.core.HumanStore;
import de.hd2tools.humanstore.core.HumanStoreHeader;
import de.hd2tools.humanstore.core.Rank;
import de.hd2tools.humanstore.core.Stats;
import de.hd2tools.humanstore.core.UnexpectedHeaderException;
import de.hd2tools.humanstore.core.UnknownHumanProperty;
import de.hd2tools.humanstore.util.IOUtilities;

public class HumanStoreReader {
	public static final String CHARSET = "ISO-8859-2";

	public HumanStore read(InputStream is) throws Exception {
		BufferedInputStream in = new BufferedInputStream(is);

		byte[] header = IOUtilities.readBytes(in, 2);
		if (!Arrays.equals(header, HumanStoreHeader.HUMANSTORE_HEADER)) {
			throw new UnexpectedHeaderException(header, 0);
		}

		in.skip(4); // Skip file length;

		HumanStore humanStore = new HumanStore();

		while (in.available() > 0) {
			in.mark(Integer.MAX_VALUE);
			header = IOUtilities.readBytes(in, 2);

			if (Arrays.equals(header, HumanStoreHeader.HUMANSTORE_HUMAN)) {
				in.reset();
				humanStore.getHumans().add(readHuman(in));
				continue;
			}

			throw new UnexpectedHeaderException(header, 0);
		}

		is.close();
		return humanStore;
	}

	public HumanStore readFromSav(BufferedInputStream in) throws Exception {
		HumanStore humanStore = new HumanStore();

		if (in.available() < 3) {
			throw new Exception("byte length < 3");
		}

		while (in.available() > 0) {
			Human human = null;
			if (IOUtilities.forwardTo(in, HumanStoreHeader.HUMANSTORE_HUMAN)) {
				human = readHuman(in);
				humanStore.getHumans().add(human);

				byte[] nextHeader = new byte[2];
				int bytesRead = IOUtilities.readBytesAndReset(in, nextHeader);

				if (bytesRead < 2 || !Arrays.equals(nextHeader, HumanStoreHeader.HUMANSTORE_HUMAN)) {
					break;
				}
			}
		}

		return humanStore;
	}

	public HumanStore readFromSav(InputStream is) throws Exception {
		return readFromSav(new BufferedInputStream(is));
	}

	public Human readHuman(BufferedInputStream i) throws Exception {
		byte[] header = IOUtilities.readBytes(i, 2);

		if (!Arrays.equals(header, HumanStoreHeader.HUMANSTORE_HUMAN)) {
			throw new UnexpectedHeaderException(header, 0);
		}

		i.skip(4); // Skip Length

		Human human = new Human();
		while (true) {
			if (i.available() == 0) {
				break;
			}
			i.mark(Integer.MAX_VALUE);
			header = IOUtilities.readBytes(i, 2);
			int length = 0;

			if (Arrays.equals(HumanStoreHeader.HUMAN_FIRSTNAME, header)) {
				length = ByteBuffer.wrap(IOUtilities.readBytes(i, 4)).order(ByteOrder.LITTLE_ENDIAN).getInt();
				length = length - 6; // substract header and length bytes
				String firstName = IOUtilities.fromCString(IOUtilities.readBytes(i, length), CHARSET);
				human.firstnameProperty().set(firstName);
				continue;
			}

			if (Arrays.equals(HumanStoreHeader.HUMAN_SURNAME, header)) {
				length = ByteBuffer.wrap(IOUtilities.readBytes(i, 4)).order(ByteOrder.LITTLE_ENDIAN).getInt();
				length = length - 6; // substract header and length bytes
				String surname = IOUtilities.fromCString(IOUtilities.readBytes(i, length), CHARSET);
				human.surnameProperty().set(surname);
				continue;
			}

			if (Arrays.equals(HumanStoreHeader.HUMAN_NICKNAME, header)) {
				length = ByteBuffer.wrap(IOUtilities.readBytes(i, 4)).order(ByteOrder.LITTLE_ENDIAN).getInt();
				length = length - 6; // substract header and length bytes
				String nickname = IOUtilities.fromCString(IOUtilities.readBytes(i, length), CHARSET);
				human.nicknameProperty().set(nickname);
				continue;
			}

			if (Arrays.equals(HumanStoreHeader.HUMAN_PORTRAIT, header)) {
				length = ByteBuffer.wrap(IOUtilities.readBytes(i, 4)).order(ByteOrder.LITTLE_ENDIAN).getInt();
				length = length - 6; // substract header and length bytes
				String portrait = IOUtilities.fromCString(IOUtilities.readBytes(i, length), CHARSET);
				human.portraitProperty().set(portrait);
				continue;
			}

			if (Arrays.equals(HumanStoreHeader.HUMAN_RANK, header)) {
				length = ByteBuffer.wrap(IOUtilities.readBytes(i, 4)).order(ByteOrder.LITTLE_ENDIAN).getInt();
				length = length - 6; // substract header and length bytes
				int rankId = ByteBuffer.wrap(IOUtilities.readBytes(i, 4)).order(ByteOrder.LITTLE_ENDIAN).getInt();
				Rank rank = Rank.forId(rankId);
				human.rankProperty().set(rank);
				continue;
			}

			if (Arrays.equals(HumanStoreHeader.HUMAN_UNKNOWN1, header)) {
				length = ByteBuffer.wrap(IOUtilities.readBytes(i, 4)).order(ByteOrder.LITTLE_ENDIAN).getInt();
				length = length - 6; // substract header and length bytes
				human.setUnknown1(new UnknownHumanProperty(header, length, IOUtilities.readBytes(i, length)).getContent());
				continue;
			}

			if (Arrays.equals(HumanStoreHeader.HUMAN_UNKNOWN2, header)) {
				length = ByteBuffer.wrap(IOUtilities.readBytes(i, 4)).order(ByteOrder.LITTLE_ENDIAN).getInt();
				length = length - 6; // substract header and length bytes
				human.setUnknown2(new UnknownHumanProperty(header, length, IOUtilities.readBytes(i, length)).getContent());
				continue;
			}

			if (Arrays.equals(HumanStoreHeader.HUMAN_UNKNOWN3, header)) {
				length = ByteBuffer.wrap(IOUtilities.readBytes(i, 4)).order(ByteOrder.LITTLE_ENDIAN).getInt();
				length = length - 6; // substract header and length bytes
				human.setUnknown3(new UnknownHumanProperty(header, length, IOUtilities.readBytes(i, length)).getContent());

				continue;
			}

			if (Arrays.equals(HumanStoreHeader.HUMAN_STATS, header)) {
				length = ByteBuffer.wrap(IOUtilities.readBytes(i, 4)).order(ByteOrder.LITTLE_ENDIAN).getInt();
				length = length - 6; // substract header and length bytes
				int biographyId = IOUtilities.readIntAsLittleEndian(IOUtilities.readBytes(i, 4));
				float health = IOUtilities.readFloatAsLittleEndian(IOUtilities.readBytes(i, 4));
				float strength = IOUtilities.readFloatAsLittleEndian(IOUtilities.readBytes(i, 4));
				float endurance = IOUtilities.readFloatAsLittleEndian(IOUtilities.readBytes(i, 4));
				float morale = IOUtilities.readFloatAsLittleEndian(IOUtilities.readBytes(i, 4));
				float shooting = IOUtilities.readFloatAsLittleEndian(IOUtilities.readBytes(i, 4));
				float experience = IOUtilities.readFloatAsLittleEndian(IOUtilities.readBytes(i, 4));
				float stealth = IOUtilities.readFloatAsLittleEndian(IOUtilities.readBytes(i, 4));
				float firstAid = IOUtilities.readFloatAsLittleEndian(IOUtilities.readBytes(i, 4));
				float lockpicking = IOUtilities.readFloatAsLittleEndian(IOUtilities.readBytes(i, 4));

				Stats stats = new Stats();
				stats.setBiographyId(biographyId);
				stats.setHealth(health);
				stats.setStrength(strength);
				stats.setEndurance(endurance);
				stats.setMorale(morale);
				stats.setShooting(shooting);
				stats.setExperience(experience);
				stats.setStealth(stealth);
				stats.setFirstAid(firstAid);
				stats.setLockpicking(lockpicking);

				human.setStatus(stats);
				continue;
			}

			if (Arrays.equals(new byte[] { (byte) 0xFC, 0x26 }, header)) {
				i.read(new byte[8]);
				continue;
			}

			if (Arrays.equals(new byte[] { (byte) 0x80, 0x25 }, header)) {
				i.read(new byte[12]);
				continue;
			}

			i.reset();
			break;
		}

		return human;
	}

}
