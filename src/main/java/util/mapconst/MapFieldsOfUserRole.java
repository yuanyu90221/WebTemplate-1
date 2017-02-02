package util.mapconst;

public enum MapFieldsOfUserRole implements MapFields {
	USER_ID("userId", "使用者 ID"),
	
	ROLE("role", "角色");
	
	private String fieldName;
	private String note;
	
	private MapFieldsOfUserRole(String fieldName, String note) {
		this.fieldName = fieldName;
		this.note = note;
	}
	
	@Override
	public String getFieldName() {
		return this.fieldName;
	}
	
	@Override
	public String getNote() {
		return this.note;
	}
	
	public static MapFieldsOfUserRole convert(String fieldName) {
		for (MapFieldsOfUserRole e : MapFieldsOfUserRole.values()) {
			if (e.getFieldName().compareToIgnoreCase(fieldName) == 0) {
				return e;
			}
		}
		return null;
	}
}
