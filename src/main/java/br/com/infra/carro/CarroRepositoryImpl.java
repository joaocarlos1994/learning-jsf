package br.com.infra.carro;

import java.util.List;
import java.util.Objects;

import br.com.domain.Carro;
import br.com.infra.Repository;
import br.com.infra.exception.ErroSistema;

public class CarroRepositoryImpl extends Repository<Long, Carro> {

	public Carro save(final Carro carro) throws ErroSistema {
		return super.save(carro);
	}

	@Override
	public List<Carro> findAll() {
		return super.findAll();
	}

	@Override
	public void delete(final Carro carro) throws ErroSistema {
		super.delete(carro);
	}

	@Override
	public Carro findOne(final Long id) {
		return super.findOne(id);
	}

	@Override
	public boolean isNew(final Carro carro) {
		return Objects.isNull(carro.getId());
	}
}
