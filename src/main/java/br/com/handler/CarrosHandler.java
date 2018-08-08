package br.com.handler;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.domain.Carro;
import br.com.domain.CarroApplicationLayer;

@ManagedBean
@SessionScoped
public class CarrosHandler extends CrudBean<Carro> {

	public CarrosHandler() {
		super(new CarroApplicationLayer());
	}
	
	@Override
	public Carro criarNovaEntidade() {
		return new Carro();
	}
}
