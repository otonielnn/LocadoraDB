package locadora.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import io.github.cdimascio.dotenv.*;

public class ConnectionFactory {
    private static final Dotenv dotenv;
    private static final String DRIVER;
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        // Carrega variáveis do .env
        dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();
        
        DRIVER = dotenv.get("DB_DRIVER", "org.postgresql.Driver");
        URL = dotenv.get("DB_URL");
        USER = dotenv.get("DB_USER");
        PASSWORD = dotenv.get("DB_PASSWORD");
        
        // Carrega o driver PostgreSQL
        try {
            Class.forName(DRIVER);
            System.out.println("✅ Driver PostgreSQL carregado: " + DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("❌ PostgreSQL Driver não encontrado: " + DRIVER, e);
        }
    }

    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASSWORD);
        
        // Só usa SSL se a URL tiver indicação (para Supabase)
        if (URL.contains("sslmode=require") || URL.contains("supabase")) {
            props.setProperty("ssl", "true");
            props.setProperty("sslmode", "require");
        }
        
        // Log da tentativa de conexão
        System.out.println("🔌 Conectando a: " + URL);
        
        return DriverManager.getConnection(URL, props);
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("🔒 Conexão fechada");
            } catch (SQLException e) {
                System.err.println("⚠️ Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
    
    // Método para verificar se a conexão está funcionando
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("❌ Teste de conexão falhou: " + e.getMessage());
            return false;
        }
    }
}
