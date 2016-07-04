package austin.mysakuraapp.model.bean;

import java.io.Serializable;
import java.util.List;

/**单词大分类(手机端返回json用) 比如：动词、名词、形容词等
 * @author Austin
 *
 */
public class WordType implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7623551144241749993L;

	private List<Children> children;

	private int typeId;
	private String typeNameEn;
	private String typeNameCn;
	
	
	public WordType() {
		super();
	}
	public WordType(int typeId, String typeNameEn, String typeNameCn) {
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
	public WordType setTypeNameCn(String typeNameCn) {
		this.typeNameCn = typeNameCn;
		return this;
	}
	
	
	public List<Children> getChildren() {
		return children;
	}
	public WordType setChildren(List<Children> children) {
		this.children = children;
		return this;
	}



	public class Children{
		private Integer id;
		private String title;//动物 or 植物
		private String url;// word/noun/animals
		
		
		
		public Children() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Children(Integer id, String title, String url) {
			super();
			this.id = id;
			this.title = title;
			this.url = url;
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		
	}
}
