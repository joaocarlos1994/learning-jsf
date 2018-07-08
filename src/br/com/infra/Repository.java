package br.com.infra;

import java.util.List;

import br.com.infra.exception.ErroSistema;

public interface Repository<ID, T> {
	
	public T save(final T t) throws ErroSistema;
	
	public List<T> findAll() throws ErroSistema;
	
	public void delete(final T t) throws ErroSistema;
	
	public T findOne(final ID id) throws ErroSistema;
}
