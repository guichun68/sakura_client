package austin.mysakuraapp.model.bean;

/**单词大分类 比如：动词、名词、形容词等
 * @author Austin
 *
 */
public class TWordType {
	private int typeId;
	private String typeNameEn;
	private String typeNameCn;
	
	
	public TWordType() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TWordType(int typeId, String typeNameEn, String typeNameCn) {
		super();
		this.typeId = typeId;
		this.typeNameEn = typeNameEn;
		this.typeNameCn = typeNameCn;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getTypeNameEn() {
		return typeNameEn;
	}
	public void setTypeNameEn(String typeNameEn) {
		this.typeNameEn = typeNameEn;
	}
	public String getTypeNameCn() {
		return typeNameCn;
	}
	public void setTypeNameCn(String typeNameCn) {
		this.typeNameCn = typeNameCn;
	}
	
}
