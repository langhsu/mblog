/**
 * 
 */
package mblog.base.lang;

/**
 * @author langhsu
 *
 */
public enum EnumLog {
	LOGIN(1, "logined"),
	FAVORED(2, "favored"),
	POSTED(3, "posted"),
	COMMENT(4, "comment"),
	BROWSE(5, "browse");
	
	private int index;
	private String text;
	
	private EnumLog(int index, String text) {
		this.index = index;
		this.text = text;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
