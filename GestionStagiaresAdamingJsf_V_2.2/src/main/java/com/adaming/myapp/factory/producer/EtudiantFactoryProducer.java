package com.adaming.myapp.factory.producer;

import com.adaming.myapp.entities.Etudiant;
import com.adaming.myapp.factory.impl.EtudiantFactoryImpl;
import com.adaming.myapp.factory.manager.IFactory;

public final class EtudiantFactoryProducer {

	public final static IFactory<Etudiant> getFactoryImpl(String typeImpl){
		if("EtudiantFactoryImpl".equalsIgnoreCase(typeImpl)){
			return new EtudiantFactoryImpl();
		}
		return null;
	}
}
