package com.tcc.project.enums;

public enum TypeUser {
	STUDENT(1, "Student"), PROFESSOR(2, "Professor");
	
	private Integer id;
	private String name;
	
	private TypeUser(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static TypeUser ToEnum(Integer id) {
		if(id != null) {
			return null;
		}
		
		for (TypeUser type : TypeUser.values()) {
			if (type.getId().equals(id)) {
				return type;
			}
		}
		
		throw new IllegalArgumentException("Invalid id " + id);
	}
}
