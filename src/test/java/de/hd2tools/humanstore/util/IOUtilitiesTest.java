package de.hd2tools.humanstore.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import de.hd2tools.humanstore.core.HumanStoreHeader;
import de.hd2tools.humanstore.io.HumanStoreIOTest;
import de.hd2tools.humanstore.misc.Utils;

public class IOUtilitiesTest {

	@Test
	public void testForwardTo1() throws IOException {
		InputStream is = HumanStoreIOTest.class.getResourceAsStream("/HumanStore.def");
		IOUtilities.forwardTo(is, HumanStoreHeader.HUMANSTORE_HUMAN);

		byte[] bytes = IOUtilities.readBytes(is, 2);
		System.out.println(Utils.bytesToHex(bytes));
		Assert.assertTrue(Arrays.equals(HumanStoreHeader.HUMANSTORE_HUMAN, bytes));
	}
	
	@Test
	public void shiftTest() throws IOException {
		InputStream is = HumanStoreIOTest.class.getResourceAsStream("/HumanStore.def");
		byte[] bytes = new byte[2];
		
		IOUtilities.shift(bytes, is, 1);
		Assert.assertTrue(Arrays.equals(bytes, new byte[] {0x00,(byte)0x9A}));
		
		
		IOUtilities.shift(bytes, is, 1);
		Assert.assertTrue(Arrays.equals(bytes, new byte[] {(byte)0x9A,0x02}));
	}
}
