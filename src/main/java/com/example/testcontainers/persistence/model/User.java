package com.example.testcontainers.persistence.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "Users")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQUENCE")
	@SequenceGenerator(name = "USER_SEQUENCE")
	private Integer id;

	private String username;
	private String displayname;

	@Builder
	private User(String username, String displayname) {
		this();
		this.username = username;
		this.displayname = displayname;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
			return false;
		User user = (User) o;
		return id != null && Objects.equals(id, user.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
