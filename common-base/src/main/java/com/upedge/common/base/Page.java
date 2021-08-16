package com.upedge.common.base;

public class Page<T> {

    /**
	 * 查询的字段列表，如果为空则为全部字段
	 */
	private String fields;

	/**
	 * 查询的条件，没有自定义的查询条件，可以不设置，默认值null
	 */
	private String condition;
	
	/**
	 * 页码
	 */
    private Integer pageNum;
    
    /**
     * 查询时使用的开始记录游标
     */
    private Integer fromNum;
	
    /**
     * 每页记录数
     */
	private Integer pageSize;
	
	/**
	 * 总记录数，需要单独查询
	 */
	private Integer total;
	
	/**
	 * 排序设置
	 */
	private String orderBy;

	/**
	 * 分组设置
	 */
	private String groupBy;

	/**
	 *  递归分页使用下一页的分界点
	 */
	private String boundary;

	public String getBoundary() {
		return boundary;
	}

	public void setBoundary(String boundary) {
		this.boundary = boundary;
	}


	/**
	 * 查询的实体类，字段筛选条件为 等于
	 */
	private T t;

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
		if(null != this.pageSize) {
			this.fromNum = (this.pageNum-1)*this.pageSize;
		}
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
			this.pageSize = pageSize;
			if(null != this.pageNum) {
				this.fromNum = (this.pageNum-1)*this.pageSize;
			}

	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	public Integer getTotal() {
		return total;
	}
	
//	@Deprecated
//	public void setTotal(Integer total) {
//		this.total = (long) total;
//	}

	public void setTotal(Long total) {
		this.total=total==null?null: Integer.parseInt(String.valueOf(total));
	}

	public Integer getFromNum() {
		return fromNum;
	}

	public void setFromNum(Integer fromNum) {
		this.fromNum = fromNum;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}
	
	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public void initFromNum() {
		if(this.pageNum==null){
			this.pageNum=1;
		}
		if(this.pageSize==null||this.pageSize==0){
			this.pageSize=10;
		}
		if(this.pageSize == -1){
			this.pageSize = null;
		}
		if(null != this.pageNum && null != this.pageSize) {
			this.fromNum = (this.pageNum-1)*this.pageSize;
		}
		else {
			this.fromNum = null;
		}
	}
}
