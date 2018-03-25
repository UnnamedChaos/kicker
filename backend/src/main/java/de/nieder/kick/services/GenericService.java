package de.nieder.kick.services;

import de.nieder.kick.model.BaseEntity;

public abstract class GenericService<T extends BaseEntity> {
	public abstract void verify(T t);
}
