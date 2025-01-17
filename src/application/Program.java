package application;

import db.DB;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Program {
    public static void main(String[] args) {

        // Inserindo Dados no banco de dados

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Connection conn = null;
        PreparedStatement st = null;

        try{
            conn = DB.getConnection();


            // comando para inserir dados no Banco de dados
            st = conn.prepareStatement(
                    "INSERT INTO seller "
                        + "(Name, Email, BirthDate, BaseSalary, DepartmentId)"
                        + "VALUES "
                        + "(?, ?, ?, ?, ?) ",// valores que vou preencher
                    PreparedStatement.RETURN_GENERATED_KEYS); // retorno do ID que sera criado no banco de dados


            // definindo qual vai ser os valores nas (?)
            st.setString(1, "Ícaro Reis");
            st.setString(2, "icarodeveloper@gmail.com");
            st.setDate(3, new java.sql.Date(sdf.parse("13/04/2004").getTime()));
            st.setDouble(4, 90000.0);
            st.setInt(5,4);


            // executando a inserção dos dados
            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();

                // percorrendo toda a tabela
                while (rs.next()) {
                    int id = rs.getInt(1); // coluna 1 dentro da tabela (ID)
                    System.out.println("ID do dado que foi inseridojdbc_inserindoDados: " + id);
                }
            }
            else {
                System.out.println("Nenhuma linha foi alterada!");
            }
        }

        // tratando as possiveis exceptions
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ParseException e){
            e.printStackTrace();
        }

        finally {
            DB.closeStatement(st);
            DB.closeConnection();
        }



    }
}
