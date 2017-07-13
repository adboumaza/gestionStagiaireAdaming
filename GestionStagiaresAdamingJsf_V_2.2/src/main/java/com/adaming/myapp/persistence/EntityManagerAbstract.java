package com.adaming.myapp.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityManagerAbstract {

	@PersistenceContext
	protected EntityManager entityManager;
}
