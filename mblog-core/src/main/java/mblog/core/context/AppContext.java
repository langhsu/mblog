/**
 * 
 */
package mblog.core.context;

/**
 * @author langhsu
 * 
 */
public class AppContext {
	String root = "/data/mblog";
	String origDir = "/store/orig";
	String thumbsDir = "/store/thumbs";
	String avaDir = "/store/ava";
	String tempDir = "/store/temp";

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getOrigDir() {
		return origDir;
	}

	public void setOrigDir(String origDir) {
		this.origDir = origDir;
	}

	public String getThumbsDir() {
		return thumbsDir;
	}

	public void setThumbsDir(String thumbsDir) {
		this.thumbsDir = thumbsDir;
	}

	public String getTempDir() {
		return tempDir;
	}

	public void setTempDir(String tempDir) {
		this.tempDir = tempDir;
	}

	public String getAvaDir() {
		return avaDir;
	}

	public void setAvaDir(String avaDir) {
		this.avaDir = avaDir;
	}

}
