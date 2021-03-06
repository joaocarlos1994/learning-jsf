package br.com.handler;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;

import br.com.infra.carro.ApplicationLayer;
import br.com.infra.exception.ErroSistema;

public abstract class CrudBean<E> {
	
	private String estadoTela = "buscar";
	
	private E entidade;
	private List<E> entidades;
	private final ApplicationLayer<E> application;
	
	public abstract E criarNovaEntidade();
	
	public CrudBean(final ApplicationLayer<E> application) {
		super();
		this.application = application;
	}

	public void novo() {
		entidade = criarNovaEntidade();
		mudarParaInseri();
	}

	public void salvar() {
		try {
			application.save(entidade);
			entidade = criarNovaEntidade();
			adicionarMensagem("Salvo com sucesso", FacesMessage.SEVERITY_INFO);
			mudarParaBusca();
		} catch (final ErroSistema e) {
			adicionarMensagem(e.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void editar(final E entidade) {
		this.entidade = entidade;
		mudarParaEdita();
	}
	
	public void deletar(final E entity) {
		try {
			application.delete(entity);
			adicionarMensagem("Carro deletado com sucesso", FacesMessage.SEVERITY_INFO);
			entidades.remove(entity);
		} catch (final ErroSistema e) {
			adicionarMensagem(e.getMessage(), FacesMessage.SEVERITY_ERROR);
			e.printStackTrace();
		}
	}
	
	public void buscar() {
		if (!isBusca()) {
			mudarParaBusca(); 
 			return;
 		}
		this.entidades = application.findAll();
	}
	
	public LazyDataModel<E> getPagedCarros() {
		return application.findAllPaged();
 	}
	
	public void adicionarMensagem(final String mensagem, final FacesMessage.Severity tipoErro) {
		final FacesMessage facesMessage = new FacesMessage(tipoErro, mensagem, null);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
	
	public E getEntidade() {
		return entidade;
	}
	public void setEntidade(E entidade) {
		this.entidade = entidade;
	}
	public List<E> getEntidades() {
		return entidades;
	}
	public void setEntidades(List<E> entidades) {
		this.entidades = entidades;
	}
	public boolean isInseri() {
		return "inserir".equals(estadoTela);
	}
	
	public boolean isEdita() {
		return "editar".equals(estadoTela);
	}
	
	public boolean isBusca() {
		return "buscar".equals(estadoTela);
	}

	public void mudarParaInseri() {
		this.estadoTela = "inserir";
	}
	
	public void mudarParaEdita() {
		this.estadoTela = "editar";
	}
	
	public void mudarParaBusca() {
		this.estadoTela = "buscar";
	}
}
