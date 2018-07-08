package br.com.handler;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.domain.Carro;
import br.com.domain.CarroRepository;
import br.com.infra.carro.CarroRepositoryImpl;

@ManagedBean
@SessionScoped
public class CarrosHandler extends CrudBean<Carro, CarroRepository> {
	
	private final CarroRepository carroRepository = new CarroRepositoryImpl();

	@Override
	public CarroRepository getDao() {
		return carroRepository;
	}

	@Override
	public Carro criarNovaEntidade() {
		return new Carro();
	}
}
