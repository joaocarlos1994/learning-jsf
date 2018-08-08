package br.com.infra;

import java.io.Serializable;
import java.util.List;

import br.com.infra.exception.ErroSistema;

public interface Repository<ID extends Serializable, T> {
	
	public T save(final T t) throws ErroSistema;
	public List<T> findAll();
	public void delete(final T t) throws ErroSistema;
	public T findOne(final ID id);
}
