package mybatistest.bean;

public class User {

	private Integer id;

	private String username;

	private String password;

	private String realname;

	private Integer userPermissionGroupId;

	private String emailAddress;

	private String status;

	private Integer createTime;

	private Integer expiryTime;

	private String description;

	private Integer lastUpdateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Integer getUserPermissionGroupId() {
		return userPermissionGroupId;
	}

	public void setUserPermissionGroupId(Integer userPermissionGroupId) {
		this.userPermissionGroupId = userPermissionGroupId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Integer expiryTime) {
		this.expiryTime = expiryTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Integer lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("\n");
		buffer.append("createTime:");
		buffer.append(createTime == null ? "null" : createTime.toString());
		buffer.append("\n");
		buffer.append("description:");
		buffer.append(description == null ? "null" : description.toString());
		buffer.append("\n");
		buffer.append("emailAddress:");
		buffer.append(emailAddress == null ? "null" : emailAddress.toString());
		buffer.append("\n");
		buffer.append("expiryTime:");
		buffer.append(expiryTime == null ? "null" : expiryTime.toString());
		buffer.append("\n");
		buffer.append("id:");
		buffer.append(id == null ? "null" : id.toString());
		buffer.append("\n");
		buffer.append("lastUpdateTime:");
		buffer.append(lastUpdateTime == null ? "null" : lastUpdateTime
				.toString());
		buffer.append("\n");
		buffer.append("password:");
		buffer.append(password == null ? "null" : password.toString());
		buffer.append("\n");
		buffer.append("realname:");
		buffer.append(realname == null ? "null" : realname.toString());
		buffer.append("\n");
		buffer.append("status:");
		buffer.append(status == null ? "null" : status.toString());
		buffer.append("\n");
		buffer.append("userPermissionGroupId:");
		buffer.append(userPermissionGroupId == null ? "null"
				: userPermissionGroupId.toString());
		buffer.append("\n");
		buffer.append("username:");
		buffer.append(username == null ? "null" : username.toString());

		return buffer.toString();
	}

}