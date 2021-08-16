package com.upedge.common.base;

public class TreeEntity extends BaseEntity {

	private Long parentId;
	
	private String treePath;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getTreePath() {
		return treePath;
	}

	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}
	
	
}
