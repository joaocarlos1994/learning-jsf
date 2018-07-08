package br.com.infra;

import java.util.Objects;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class EntityManagerFactory {
	
	private final SessionFactory sessionFactory;
	private ThreadLocal<Session> connection = new ThreadLocal<Session>();

	public EntityManagerFactory() {
		final Configuration configuracao = new Configuration().configure();
        final ServiceRegistry registro = new StandardServiceRegistryBuilder().applySettings(configuracao.getProperties()).build();
        this.sessionFactory = configuracao.buildSessionFactory(registro);
	}


	public Session getSession() {
		if (Objects.isNull(connection.get()) || !connection.get().isOpen()) {
			connection.set(sessionFactory.openSession());			
		} 
		return connection.get();
	}

	public void closeConnection() {
		if (!Objects.isNull(connection.get()) || connection.get().isOpen()) {
			connection.get().close();
			connection.set(null);
		}
	}
}
