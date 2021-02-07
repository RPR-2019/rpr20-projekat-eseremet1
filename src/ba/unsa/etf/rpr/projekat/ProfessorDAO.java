package ba.unsa.etf.rpr.projekat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ProfessorDAO {
    private static ProfessorDAO instance; //singleton klasa
    private PreparedStatement getProfessorStatement, deleteProfessorStatement, searchProfessorStatement, changeProfessorStatement, getProfessorsStatement, addProfessorStatement, determineIdProfessorStatement;
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public static ProfessorDAO getInstance() {
        if(instance==null) instance = new ProfessorDAO();
        return instance;

    }
    private ProfessorDAO() {
        try {
            connection= DriverManager.getConnection("jdbc:sqlite:baza.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            getProfessorStatement = connection.prepareStatement("SELECT * FROM professor WHERE id=?");

        } catch (SQLException e) {
            regeneration();
            try {
                getProfessorStatement = connection.prepareStatement("SELECT * FROM professor WHERE id=?");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        try {
            //pripremljeni upiti za profesora
            deleteProfessorStatement = connection.prepareStatement("DELETE FROM professor WHERE id = ?");
            searchProfessorStatement = connection.prepareStatement("SELECT * FROM professor WHERE username LIKE ? ");
            changeProfessorStatement = connection.prepareStatement("UPDATE professor SET name = ?, surname = ?, email = ?, username = ?, password = ? WHERE id=?");
            getProfessorsStatement = connection.prepareStatement("SELECT * FROM professor");
            addProfessorStatement = connection.prepareStatement("INSERT INTO professor VALUES(?,?,?,?,?,?) ");
            determineIdProfessorStatement = connection.prepareStatement("SELECT MAX(id)+1 FROM professor");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeInstance() {
        if(instance==null) return;
        instance.close();
        instance=null;
    }


    private void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void regeneration() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("baza.db.sql"));
            String sqlUpit="";
            while(ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if(sqlUpit.length() > 1 && sqlUpit.charAt(sqlUpit.length()-1) == ';') {
                    try {
                        Statement stmt = connection.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit="";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    private Professor getProfessorResultSet(ResultSet rs) throws SQLException {
        return new Professor(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),null);
    }

    private Professor getProfessor(int id) {
        try {
            getProfessorStatement.setInt(1,id);
            ResultSet rs = getProfessorStatement.executeQuery();
            if(!rs.next()) return null;
            return getProfessorResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void changeProfessor(Professor professor) {
        try {

            changeProfessorStatement.setString(1,professor.getName());
            changeProfessorStatement.setString(2,professor.getSurname());
            changeProfessorStatement.setString(3,professor.getEmail());
            changeProfessorStatement.setString(4,professor.getUsername());
            changeProfessorStatement.setString(5,professor.getPassword());
            changeProfessorStatement.setInt(6,professor.getId());

            changeProfessorStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteProfessor(String username) {
        try {
            searchProfessorStatement.setString(1,username);
            ResultSet rs = searchProfessorStatement.executeQuery();
            if(!rs.next()) return;
            Professor professor = getProfessorResultSet(rs);
            deleteProfessorStatement.setInt(1,professor.getId());
            deleteProfessorStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Professor> professors() {
        ArrayList<Professor> result = new ArrayList<>();
        try {
            ResultSet rs = getProfessorsStatement.executeQuery();
            while(rs.next()) {
                Professor professor = getProfessorResultSet(rs);
                result.add(professor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public void addProfessor(Professor professor) {
        try {
            ResultSet rs = determineIdProfessorStatement.executeQuery();
            int id=1;
            if(rs.next()) {
                id=rs.getInt(1);
            }
            addProfessorStatement.setInt(1,id);
            addProfessorStatement.setString(2,professor.getName());
            addProfessorStatement.setString(3,professor.getSurname());
            addProfessorStatement.setString(4,professor.getEmail());
            addProfessorStatement.setString(5,professor.getUsername());
            addProfessorStatement.setString(6,professor.getPassword());

            addProfessorStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public Professor searchProfessor(String username) {
        try {
            searchProfessorStatement.setString(1,username);
            ResultSet rs = searchProfessorStatement.executeQuery();
            if(!rs.next()) return null;
            return getProfessorResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
