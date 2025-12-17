package locadora;

import locadora.util.ConnectionFactory;
import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;

public class TesteConexao {
    public static void main(String[] args) {
        System.out.println("=== TESTE DE CONEXÃO DETALHADO ===\n");
        
        // 1. Testar carregamento do .env
        try {
            System.out.println("1️⃣ Testando arquivo .env...");
            Dotenv dotenv = Dotenv.load();
            String url = dotenv.get("DB_URL");
            String user = dotenv.get("DB_USER");
            String pass = dotenv.get("DB_PASSWORD");
            
            System.out.println("   ✅ Arquivo .env encontrado!");
            System.out.println("   DB_URL: " + url);
            System.out.println("   DB_USER: " + user);
            System.out.println("   DB_PASSWORD: " + (pass != null ? "****" : "NULL"));
            System.out.println();
        } catch (Exception e) {
            System.out.println("   ❌ Erro ao carregar .env: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        
        // 2. Testar driver PostgreSQL
        try {
            System.out.println("2️⃣ Testando driver PostgreSQL...");
            Class.forName("org.postgresql.Driver");
            System.out.println("   ✅ Driver PostgreSQL carregado!\n");
        } catch (ClassNotFoundException e) {
            System.out.println("   ❌ Driver não encontrado!");
            e.printStackTrace();
            return;
        }
        
        // 3. Testar conexão
        try {
            System.out.println("3️⃣ Tentando conectar ao banco...");
            Connection conn = ConnectionFactory.getConnection();
            
            if (conn != null && !conn.isClosed()) {
                System.out.println("   ✅ CONEXÃO BEM-SUCEDIDA!");
                System.out.println("   Catalog: " + conn.getCatalog());
                System.out.println("   Schema: " + conn.getSchema());
                ConnectionFactory.closeConnection(conn);
                System.out.println("\n🎉 Tudo funcionando perfeitamente!");
            }
        } catch (Exception e) {
            System.out.println("   ❌ ERRO DE CONEXÃO:");
            System.out.println("   Tipo: " + e.getClass().getSimpleName());
            System.out.println("   Mensagem: " + e.getMessage());
            System.out.println("\n   Stack trace completo:");
            e.printStackTrace();
            
            System.out.println("\n💡 POSSÍVEIS CAUSAS:");
            System.out.println("   - Senha incorreta");
            System.out.println("   - Host do Supabase errado");
            System.out.println("   - Firewall bloqueando conexão");
            System.out.println("   - Supabase pausado (plano gratuito)");
            System.out.println("   - Falta de ?sslmode=require na URL");
        }
    }
}
