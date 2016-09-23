package util.mapconst;

public enum MapFieldsOfProduct implements MapFields {
	PROD_ID("prodId", "商品 ID"),
	
	PROD_NAME("prodName", "商品名稱");
	
	private String fieldName;
	private String note;
	
	private MapFieldsOfProduct(String fieldName, String note) {
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
	
	public static MapFieldsOfProduct convert(String fieldName) {
		for (MapFieldsOfProduct e : MapFieldsOfProduct.values()) {
			if (e.getFieldName().compareToIgnoreCase(fieldName) == 0) {
				return e;
			}
		}
		return null;
	}
}
