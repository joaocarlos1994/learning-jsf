package br.com.infra;

import java.io.Serializable;

import org.primefaces.model.LazyDataModel;

public interface PageRepository<ID extends Serializable, T> extends Repository<ID, T> {
	public LazyDataModel<T> findAllPaged();
}
