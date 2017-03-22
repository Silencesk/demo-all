package com.mine.demo.mybatisplus.privilege;

import java.io.Serializable;

public class DataPrivilegeVO implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 6804630576741237219L;
	
	private Long id;
	
	private String privilegeName;

    private String privilegeSql;
    
    /**
     * 10001,10002,10005
     */
    private String useResCodeList;

    /**
     * 状态
     */
    private String status;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	public String getPrivilegeSql() {
		return privilegeSql;
	}

	public void setPrivilegeSql(String privilegeSql) {
		this.privilegeSql = privilegeSql;
	}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUseResCodeList() {
		return useResCodeList;
	}

	public void setUseResCodeList(String useResCodeList) {
		this.useResCodeList = useResCodeList;
	}
}
