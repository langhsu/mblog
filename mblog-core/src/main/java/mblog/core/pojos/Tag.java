/**
 * 
 */
package mblog.core.pojos;

import java.io.Serializable;

/**
 * @author langhsu
 *
 */
public class Tag implements Serializable {
	private static final long serialVersionUID = 3262289824211326798L;

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
