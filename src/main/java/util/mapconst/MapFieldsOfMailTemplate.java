package util.mapconst;

public enum MapFieldsOfMailTemplate implements MapFields {
	TEMPLATE_ID("templateId", "模板 ID"),
	
	TYPE("type", "模板類別"),
	
	HEADER("header", "模板 Header"),
	
	BODY_HEADER("bodyHeader", "內容 Header"),
	
	BODY_TAIL("bodyTail", "內容 Tail"),
	
	TAIL("tail", "模板 Tail")
	;

	private String fieldName;
	private String note;
	
	private MapFieldsOfMailTemplate(String fieldName, String note) {
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

}
