package application;

import db.DB;
import db.DbException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Program {
    public static void main(String[] args) {

        // Inserindo Dados no banco de dados

        Connection conn = null;
        Statement st = null;

        try {
            conn = DB.getConnection();

            conn.setAutoCommit(false); // comando para não confirmar as operações automaticamente

            st = conn.createStatement();

            int rows1 = st.executeUpdate(
                    "UPDATE seller SET BaseSalary = 2090 "
                    + "WHERE DepartmentId = 1 "
            );

            // criando uma falha para mostrar na pratica como tratar
//            int x = 1;
//            if (x <2) {
//                throw new SQLException ("Fake Error");
//            }

            int rows2 = st.executeUpdate(
                    "UPDATE seller SET BaseSalary = 3090 "
                            + "WHERE DepartmentId = 2 "
            );

            conn.commit(); // confirmado a transação, só confirma se as duas operações acima forem realizadas com sucesso

            System.out.println("Rows 1 : " + rows1);
            System.out.println("Rows 2 : " + rows2);

        }
        catch (SQLException e) {
            try {
                conn.rollback();
                throw new DbException("Transação recusada,RollBack executado e   programa reiniciado pelo erro: " + e.getMessage()) ;
            }
            catch (SQLException ex) {
                throw new DbException("Erro ao executar o RollBack, pelo erro: " + ex.getMessage());
            }
        }

        finally {
            DB.closeStatement(st);
            DB.closeConnection();
        }
    }


}
