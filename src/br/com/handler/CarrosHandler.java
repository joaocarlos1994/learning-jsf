package br.com.handler;

import java.util.LinkedList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.domain.Carro;
import br.com.infra.carro.CarroRepositoryImpl;
import br.com.infra.exception.ErroSistema;

@ManagedBean
@SessionScoped
public class CarrosHandler {
	
	private Carro carro = new Carro();
	private List<Carro> carros = new LinkedList<Carro>();
	private final CarroRepositoryImpl carroRepositoryImpl = new CarroRepositoryImpl();
	
	public void adicionar() {
		try {
			carroRepositoryImpl.save(carro);
			adicionarMensagem("Salvo!", "Carro salvo com sucesso", FacesMessage.SEVERITY_INFO);
		} catch (ErroSistema e) {
			adicionarMensagem(e.getMessage(), e.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		carro = new Carro();
	}
	
	public void deletar(final Long id) {
		try {
			carroRepositoryImpl.delete(id);
			adicionarMensagem("Deletado!", "Carro deletado com sucesso", FacesMessage.SEVERITY_INFO);
		} catch (ErroSistema e) {
			adicionarMensagem(e.getMessage(), e.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void editar(final Carro carro){
		this.carro = carro;
	}
	
	public void adicionarMensagem(final String sumario, final String detalhe, final FacesMessage.Severity tipoErro) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(tipoErro, sumario, detalhe);
		context.addMessage(null, message);
	}
	
	public void buscar() {
		try {
			this.carros = carroRepositoryImpl.findAll();
			if (carros.isEmpty()) {
				adicionarMensagem("Nenhum dado encontrado!", "Sua busca não encontrou nenhum carro", FacesMessage.SEVERITY_WARN);				
			}
		} catch (ErroSistema e) {
			adicionarMensagem(e.getMessage(), e.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}

	public Carro getCarro() {
		return carro;
	}

	public List<Carro> getCarros() {
		return carros;
	}
}
