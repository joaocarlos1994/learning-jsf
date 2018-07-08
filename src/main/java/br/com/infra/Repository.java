package br.com.infra;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Query;

import br.com.infra.exception.ErroSistema;

public abstract class Repository<ID extends Serializable, T> {
	
	private final EntityManagerFactory entityManagerFactory = new EntityManagerFactory();
	
	public abstract boolean isNew(final T t);

	public T save(final T t) throws ErroSistema {
		beginTransaction();
		if (isNew(t)) {
			entityManagerFactory.getSession().save(t);
			entityManagerFactory.getSession().flush();			
		} else  {
			entityManagerFactory.getSession().merge(t);
			entityManagerFactory.getSession().flush();
		}
		commitTransaction();
		return t;
	}
	
	public T update(final T t) throws ErroSistema {
		beginTransaction();
		entityManagerFactory.getSession().merge(t);
		entityManagerFactory.getSession().flush();
		commitTransaction();
		return t;
	}
	
	public List<T> findAll() {
		beginTransaction();
		final Query namedQuery = entityManagerFactory.getSession().getNamedQuery(getTypeClass().getSimpleName() + ".findAll");
		@SuppressWarnings("unchecked")
		final List<T> list = namedQuery.list();
		entityManagerFactory.closeConnection();
		return list;
	};
	
	public void delete(final T t) throws ErroSistema {
		beginTransaction();
		entityManagerFactory.getSession().delete(entityManagerFactory.getSession().merge(t));
		commitTransaction();
	}
	
	public T findOne(final ID id) {
		beginTransaction();
		@SuppressWarnings("unchecked")
		final T t = (T) entityManagerFactory.getSession().get(getTypeClass(), id);
		entityManagerFactory.closeConnection();
		return t;
	}
	

	public void beginTransaction() {
		entityManagerFactory.getSession().beginTransaction();
	}
	
	public void commitTransaction() throws ErroSistema {
		try {
			entityManagerFactory.getSession().getTransaction().commit();
		} catch (final Exception e) {
			rollBackTransaction();
			throw new ErroSistema(e.getMessage(), e);
		} finally {
			entityManagerFactory.closeConnection();
		}
	}
	
	public void rollBackTransaction() {
		entityManagerFactory.getSession().getTransaction().rollback();
	}
	
	@SuppressWarnings("unchecked")
	private Class<T> getTypeClass() {
        final Class<?> clazz = (Class<?>) ((ParameterizedType) this.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
        return (Class<T>) clazz;
    }
}
