/**
 * 
 */
package mblog.base.utils;

/**
 * @author langhsu
 *
 */
public class FilePathUtils {
	private static String template = "%09d";
	private static int[] grids = new int[] {3,2,2,2};
	private static int length = 9;
	
	public static String getAvatar(long key) {
		String r = String.format(template, key);
		StringBuffer buf = new StringBuffer(32);
		
		int pos = 0;
		for (int t: grids) {
			buf.append(r.substring(pos, pos + t));
			pos += t;
			if (pos < length) {
				buf.append('/');
			}
		}
        return buf.toString();
	}
	
	public static void main(String[] args) {
		String base = FilePathUtils.getAvatar(50);
		System.out.println(String.format("/%s_%d.jpg", base, 100));
	}
	
}
