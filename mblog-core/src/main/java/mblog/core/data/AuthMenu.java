package mblog.core.data;

import java.io.Serializable;
import java.util.List;

public class AuthMenu implements Serializable{

	private static final long serialVersionUID = 1L;

	private long id;

	private String name;

	private String url;

	private int sort;

	private String permission;

	private long parentId;

	private String parentIds;

	private String icon;

	private List<AuthMenu> children;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public List<AuthMenu> getChildren() {
		return children;
	}

	public void setChildren(List<AuthMenu> children) {
		this.children = children;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthMenu other = (AuthMenu) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AuthMenu{" +
				"id=" + id +
				", name='" + name + '\'' +
				", sort=" + sort +
				'}';
	}

	public Node toNode(){
		Node node = new Node();
		node.setId(this.getId());
		node.setName(this.getName());
		node.setOpen(true);
		node.setChecked(false);
		node.setpId(this.getParentId());
		return node;
	}

	public class Node{
		private Long id;
		private Long pId;
		private String name;
		private boolean open = true;
		private boolean checked = false;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Long getpId() {
			return pId;
		}

		public void setpId(Long pId) {
			this.pId = pId;
		}

		public boolean isOpen() {
			return open;
		}

		public void setOpen(boolean open) {
			this.open = open;
		}

		public boolean isChecked() {
			return checked;
		}

		public void setChecked(boolean checked) {
			this.checked = checked;
		}

		@Override
		public String toString() {
			return "Node{" +
					"id=" + id +
					", pId=" + pId +
					", name='" + name + '\'' +
					", open=" + open +
					", checked=" + checked +
					'}';
		}
	}
}
