package test;

import java.math.BigInteger;
import java.util.Date;
import java.util.Random;
import java.util.zip.CRC32;

public class Keygen {

	private static final int version=14;

	/**
	 * @param s
	 * @param i
	 * @param bytes
	 * @return
	 */
	public static short getCRC(String s, int i, byte bytes[]) {
		CRC32 crc32 = new CRC32();
		if (s != null) {
			for (int j = 0; j < s.length(); j++) {
				char c = s.charAt(j);
				crc32.update(c);
			}
		}
		crc32.update(i);
		crc32.update(i >> 8);
		crc32.update(i >> 16);
		crc32.update(i >> 24);
		for (int k = 0; k < bytes.length - 2; k++) {
			byte byte0 = bytes[k];
			crc32.update(byte0);
		}
		return (short) (int) crc32.getValue();
	}

	/**
	 * @param biginteger
	 * @return String
	 */
	public static String encodeGroups(BigInteger biginteger) {
		BigInteger beginner1 = BigInteger.valueOf(0x39aa400L);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; biginteger.compareTo(BigInteger.ZERO) != 0; i++) {
			int j = biginteger.mod(beginner1).intValue();
			String s1 = encodeGroup(j);
			if (i > 0) {
				sb.append("-");
			}
			sb.append(s1);
			biginteger = biginteger.divide(beginner1);
		}
		return sb.toString();
	}

	/**
	 * @param i
	 * @return
	 */
	public static String encodeGroup(int i) {
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < 5; j++) {
			int k = i % 36;
			char c;
			if (k < 10) {
				c = (char) (48 + k);
			} else {
				c = (char) ((65 + k) - 10);
			}
			sb.append(c);
			i /= 36;
		}
		return sb.toString();
	}

	/**
	 * @param name
	 * @param days
	 * @param id
	 * @return
	 */
	public static String MakeKey(String name, int days, int id) {
		id %= 100000;
		byte bkey[] = new byte[12];
		bkey[0] = (byte) 1; // Product type: IntelliJ IDEA is 1
		bkey[1] = version;
		Date d = new Date();
		long ld = (d.getTime() >> 16);
		bkey[2] = (byte) (ld & 255);
		bkey[3] = (byte) ((ld >> 8) & 255);
		bkey[4] = (byte) ((ld >> 16) & 255);
		bkey[5] = (byte) ((ld >> 24) & 255);
		days &= 0xffff;
		bkey[6] = (byte) (days & 255);
		bkey[7] = (byte) ((days >> 8) & 255);
		bkey[8] = 105;
		bkey[9] = -59;
		bkey[10] = 0;
		bkey[11] = 0;
		int w = getCRC(name, id % 100000, bkey);
		bkey[10] = (byte) (w & 255);
		bkey[11] = (byte) ((w >> 8) & 255);
		BigInteger pow = new BigInteger("89126272330128007543578052027888001981", 10);
		BigInteger mod = new BigInteger("86f71688cdd2612ca117d1f54bdae029", 16);
		BigInteger k0 = new BigInteger(bkey);
		BigInteger k1 = k0.modPow(pow, mod);
		String s0 = Integer.toString(id);
		String sz = "0";
		while (s0.length() != 5) {
			s0 = sz.concat(s0);
		}
		s0 = s0.concat("-");
		String s1 = encodeGroups(k1);
		s0 = s0.concat(s1);
		return s0;
	}

	public static void main(String[] args) {
		Random r = new Random();
		System.out.println("name:" + "langhsu" );
		System.out.println("KEY:" + MakeKey("langhsu", 0, r.nextInt(Integer.MAX_VALUE)));
	}
}
