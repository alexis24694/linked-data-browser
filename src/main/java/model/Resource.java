package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="resource")
public class Resource {
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	public int id;
	
	public String prefix;
	public String prefixUri;
	
	public String name;
	public String type;
	public String uri;
	//publication url
	public String url;
	
	public Resource(){}
	
	//from query result
	public Resource(String name){
		this.name = name;
		if (name.startsWith("http://")){
			this.uri = name;
			this.type = "resource";
		}else
			this.type = "literal";
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefixUri() {
		return prefixUri;
	}

	public void setPrefixUri(String prefixUri) {
		this.prefixUri = prefixUri;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
