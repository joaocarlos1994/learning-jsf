package br.com.infra.carro;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import br.com.domain.Carro;
import br.com.domain.CarroRepository;
import br.com.infra.EntityManagerFactory;
import br.com.infra.exception.ErroSistema;

public class CarroRepositoryImpl implements CarroRepository {

	@Override
	public Carro save(final Carro carro) throws ErroSistema {
		final Connection connection = EntityManagerFactory.getConnection();
		try {
			PreparedStatement preparedStatement = null;
			if (Objects.isNull(carro.getId())) {
				preparedStatement = connection.
						prepareStatement("INSERT INTO `carro` (`modelo`, `fabricante`, `cor`, `ano`) VALUES (?, ?, ?, ?)");				
			} else {
				preparedStatement = connection.
						prepareStatement("UPDATE `carro` set modelo=?, fabricante=?, cor=?, ano=? where id=?");
				preparedStatement.setLong(5, carro.getId());
			}
			
			preparedStatement.setString(1, carro.getModelo());
			preparedStatement.setString(2, carro.getFabricante());
			preparedStatement.setString(3, carro.getCor());
			preparedStatement.setDate(4, new Date(carro.getAno().getTime()));
			preparedStatement.execute();
			EntityManagerFactory.closeConnection();
			return carro;
		} catch (final SQLException e) {
			try {
				connection.rollback();
				EntityManagerFactory.closeConnection();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new ErroSistema("Error ao salvar carro", e);
		}
	}

	@Override
	public List<Carro> findAll() throws ErroSistema {
		final Connection connection = EntityManagerFactory.getConnection();
		final List<Carro> carros = new LinkedList<Carro>();
		try {
			final PreparedStatement prepareStatement = connection.prepareStatement("select * from carro");
			final ResultSet resultSet = prepareStatement.executeQuery();
			
			while (resultSet.next()) {
				final Carro carro = new Carro();
				carro.setId(resultSet.getLong("id"));
				carro.setModelo(resultSet.getString("modelo"));
				carro.setFabricante(resultSet.getString("fabricante"));
				carro.setCor(resultSet.getString("cor"));
				carro.setAno(resultSet.getDate("ano"));
				carros.add(carro);
			}	
			EntityManagerFactory.closeConnection();
			return Collections.unmodifiableList(carros);
		} catch (SQLException e) {
			throw new ErroSistema("Error ao buscar carros", e);		
		}
	}

	@Override
	public void delete(final Carro carro) throws ErroSistema {
		final Connection connection = EntityManagerFactory.getConnection();
		try {
			final PreparedStatement prepareStatement = 
					connection.prepareStatement("delete from carro where id=?");
			prepareStatement.setLong(1, carro.getId());
			prepareStatement.execute();
			EntityManagerFactory.closeConnection();
		} catch (SQLException e) {
			throw new ErroSistema("Error ao deletar carro", e);
		}
	}

	@Override
	public Carro findOne(Long id) {
		return null;
	}
}
