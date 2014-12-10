/**
 * 
 */
package mblog.core.pojos;

/**
 * @author langhsu
 *
 */
public class Tag {
	private long id;
	private String name;
	private long ownId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getOwnId() {
		return ownId;
	}
	public void setOwnId(long ownId) {
		this.ownId = ownId;
	}
	
}
