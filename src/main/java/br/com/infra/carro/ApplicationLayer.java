package br.com.infra.carro;

import java.util.List;

import org.primefaces.model.LazyDataModel;

import br.com.infra.exception.ErroSistema;

public interface ApplicationLayer<T> {
	
	public T save(final T t) throws ErroSistema;
	
	public void delete(final T t) throws ErroSistema;
	
	public List<T> findAll();
	
	public T findById(final T t);
	
	public LazyDataModel<T> findAllPaged();
}
