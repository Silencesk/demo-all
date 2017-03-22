package com.mine.demo.mybatisplus.privilege;

import java.io.Serializable;

public class DataPrivilegeDtlVO implements Serializable{

	private static final long serialVersionUID = -360614615666687955L;

	private Long id;
	
    private int privilegeId;

    private String resCode;
    
    private String tableName;
    
    private String columnName;

	private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(int privilegeId) {
		this.privilegeId = privilegeId;
	}

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
    
    
}
