/**
 * 
 */
package mblog.core.persist.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author langhsu
 *
 */
@Entity
@Table(name = "tb_tag")
public class TagPO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
