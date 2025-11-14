package net.bounceme.chronos.utils;

import lombok.Getter;

public class Constants {

	public enum ROLES {
		ROLE_ADMIN("admin"), ROLE_USER("user");
		
		@Getter
		private String name;
		
		private ROLES(String name) {
			this.name = name;
		}
	}
}
