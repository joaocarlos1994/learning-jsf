package br.com.handler;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.domain.Carro;
import br.com.infra.PageRepository;
import br.com.infra.carro.CarroRepositoryImpl;

@ManagedBean
@SessionScoped
public class CarrosHandler extends CrudBean<Carro, PageRepository<Long, Carro>> {
	
	private final CarroRepositoryImpl repository = new CarroRepositoryImpl();

	@SuppressWarnings("rawtypes")
	@Override
	public PageRepository getDao() {
		return repository;
	}

	@Override
	public Carro criarNovaEntidade() {
		return new Carro();
	}
}
