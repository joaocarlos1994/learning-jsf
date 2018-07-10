package br.com.infra;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public abstract class PageRepository<ID, T> extends Repository<ID, T> {
	
	public Page<T> findAllPaged() {
		return new Page<>(getTypeClass());
	}

	@SuppressWarnings("hiding")
	public class Page<T> extends LazyDataModel<T> {
		
		private static final long serialVersionUID = 1L;
		
		private final Class<T> clazz;
		
		public Page(final Class<T> clazz) {
			super();
			this.clazz = clazz;
			setRowCount(getCount());
		}

		@Override
		public List<T> load(final int first, final int pageSize, final String sortField, final SortOrder sortOrder,
				final Map<String, Object> filters) {
			beginTransaction();
			@SuppressWarnings("unchecked")
			final List<T> list = entityManagerFactory.getSession()
									.createQuery("from " + clazz.getSimpleName())
									.setFirstResult(first)
									.setMaxResults(pageSize)
									.list();
			entityManagerFactory.closeConnection();
			return list;
		}
		
		
		private int getCount() {
			beginTransaction();
			final Long count = (Long) entityManagerFactory.getSession()
					.createQuery("select count(m.id) from " + clazz.getSimpleName() + " m").uniqueResult();
			entityManagerFactory.closeConnection();
			return count.intValue();
		}
	}
}
