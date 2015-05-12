package de.dis2011.data;

import java.sql.Connection;

/**
 * Makler-Bean
 *
 * Beispiel-Tabelle:
 * CREATE TABLE makler(id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
 * name varchar(255),z
 * address varchar(255),
 * login varchar(40) UNIQUE,
 * password varchar(40));
 */
public abstract class Entity {

	abstract public int getId();

	abstract public void setId(int id);

	/**
	 * Should not be used anymore!
	 */
	@Deprecated
	protected static Connection getConnection() {
		return null;
	}
}
