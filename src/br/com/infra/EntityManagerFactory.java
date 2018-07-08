package br.com.infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

import br.com.infra.exception.ErroSistema;

public class EntityManagerFactory {

	private static ThreadLocal<Connection> connection = new ThreadLocal<Connection>();
	private static final String URL_CONEXAO = "jdbc:mysql://localhost:3306/facesmotors";
	private static final String USUARIO = "root";
	private static final String SENHA = "root";

	public static Connection getConnection() throws ErroSistema {
		try {
			if (Objects.isNull(connection.get()) || connection.get().isClosed()) {
				Class.forName("com.mysql.jdbc.Driver");
				connection.set(DriverManager.getConnection(URL_CONEXAO, USUARIO, SENHA));
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new ErroSistema("Não foi possivel realizar a conexão com o banco de dados", e);
		}
		return connection.get();
	}

	public static void closeConnection() throws ErroSistema {
		try {
			if (!connection.get().isClosed()) {
				connection.get().close();
				connection.set(null);
			}
		} catch (final SQLException e) {
			throw new ErroSistema("Não foi possivel fechar a conexão com o banco de dados", e);
		}
	}
}
