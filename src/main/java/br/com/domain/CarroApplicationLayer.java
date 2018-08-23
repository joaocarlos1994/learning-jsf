package br.com.domain;

import java.util.List;

import org.primefaces.model.LazyDataModel;

import br.com.infra.SimpleRepository;
import br.com.infra.carro.ApplicationLayer;
import br.com.infra.exception.ErroSistema;

public class CarroApplicationLayer implements ApplicationLayer<Carro> {

	private final CarroRepository repository;
	
	public CarroApplicationLayer() throws InstantiationException, IllegalAccessException {
		super();
		this.repository = new SimpleRepository<Long, Carro>().getInstance(CarroRepository.class);
	}

	@Override
	public Carro save(final Carro carro) throws ErroSistema {
		return repository.save(carro);
	}

	@Override
	public void delete(final Carro carro) throws ErroSistema {
		repository.delete(carro);
	}

	@Override
	public List<Carro> findAll() {
		return repository.findAll();
	}

	@Override
	public Carro findById(final Carro carro) {
		return repository.findOne(carro.getId());
	}

	@Override
	public LazyDataModel<Carro> findAllPaged() {
		//return repository.findAllPaged();
		return null;
	}
}
