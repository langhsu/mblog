/**
 * 
 */
package mblog.core.context;

/**
 * @author langhsu
 * 
 */
public class AppContext {
	String root = "/store";
	String oriDir = "/store/ori";
	String thumbsDir = "/store/thumbs";
	String tempDir = "/store/temp";

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getOriDir() {
		return oriDir;
	}

	public void setOriDir(String oriDir) {
		this.oriDir = oriDir;
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

}
