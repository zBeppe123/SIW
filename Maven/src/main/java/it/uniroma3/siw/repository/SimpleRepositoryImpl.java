package it.uniroma3.siw.repository;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

public class SimpleRepositoryImpl<T> implements SimpleRepository<T> {
	private EntityManager em;
	private Class<T> domainClass;

	public SimpleRepositoryImpl(Class<T> domainClass) {
		this.domainClass = domainClass;
	}

	@Override
	public T save(T entity) {
		/*if(entity != null) {
			this.em.merge(entity);
		}
		else {
			this.em.persist(entity);
		}*/
		try {
			em.persist(entity);
		}
		catch(EntityExistsException e) {
			em.merge(entity);
		}
		
		return entity;
		/*T persistentEntity = null;
		//TODO (esercizio)
		 return persistentEntity;*/
	}

	@Override
	public List<T> findAll() { 
		return em.createQuery("select o from " + this.domainClass.getName() + " o", this.domainClass).getResultList();
	}	

	@Override
	public T findById(Long id){
		return em.find(this.domainClass, id); 
	}

	@Override
	public void delete(T t){
		this.em.remove(t);
	}

	@Override
	public void deleteAll(){
		this.em.createQuery("DELETE FROM "+this.domainClass.getName()).executeUpdate();
	}

	@Override
	public long count() {
		return (long)this.em.createQuery("SELECT COUNT(id) FROM "+this.domainClass.getName()).getSingleResult();
	}

	@Override
	public boolean existsById(Long id) {
		return (this.findById(id)!=null);
	}

	@Override
	public EntityManager getEntityManager() {
		return this.em;
	}

	@Override
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
}
