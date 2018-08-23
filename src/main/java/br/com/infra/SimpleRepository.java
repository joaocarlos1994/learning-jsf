package br.com.infra;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.util.List;

import javax.persistence.Id;

import org.hibernate.Query;

import br.com.infra.exception.ErroSistema;

public class SimpleRepository<ID extends Serializable, T> implements Repository<ID, T> {
	
	protected final EntityManagerFactory entityManagerFactory = EntityManagerFactory.getInstance();

	@Override
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
	
	@Override
	public List<T> findAll() {
		beginTransaction();
		final Query query = entityManagerFactory.getSession().createQuery("FROM " + getTypeClass().getSimpleName());
		query.setCacheable(true);
		@SuppressWarnings("unchecked")
		final List<T> list = query.list();
		entityManagerFactory.closeConnection();
		return list;
	}
	
	@Override
	public void delete(final T t) throws ErroSistema {
		beginTransaction();
		entityManagerFactory.getSession().delete(entityManagerFactory.getSession().merge(t));
		commitTransaction();
	}
	
	@Override
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
			entityManagerFactory.evictCacheEntiy(getTypeClass());
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
	
	public boolean isNew(final T t) {
		try {
			for (final Field field : t.getClass().getDeclaredFields()) {
				if (field.isAnnotationPresent(Id.class)) {
					field.setAccessible(true);
					final Object object = field.get(t);
					return object == null;
				} 
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		}
		return false;
	}
	
	
	@SuppressWarnings("unchecked")
	public <E extends Repository<ID, T>> E getInstance(final Class<? extends Repository<ID, T>> repositoryInterface) throws InstantiationException, IllegalAccessException {
		final SimpleRepository<ID, T> simpleRepository = this;
		final Object repository = Proxy.newProxyInstance(repositoryInterface.getClassLoader(), new Class[] { repositoryInterface }, new InvocationHandler() {
			@Override
			public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
				return method.invoke(simpleRepository, args);
			}
		});
		return (E) repository;
	}	
	
	@SuppressWarnings("unchecked")
	protected Class<T> getTypeClass() {
        final Class<?> clazz = (Class<?>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
        return (Class<T>) clazz;
    }
}
