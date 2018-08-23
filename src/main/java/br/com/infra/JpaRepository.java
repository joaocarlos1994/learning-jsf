package br.com.infra;

import java.io.Serializable;

public interface JpaRepository<ID extends Serializable, T> extends PageRepository<ID, T> {

}
