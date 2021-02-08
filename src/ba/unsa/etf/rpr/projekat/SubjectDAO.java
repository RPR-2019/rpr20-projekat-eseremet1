package ba.unsa.etf.rpr.projekat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SubjectDAO {
    private static SubjectDAO instance; //singleton klasa
    private PreparedStatement getSubjectStatement, deleteSubjectStatement, searchSubjectStatement, changeSubjectStatement, getSubjectsStatement, addSubjectStatement, addProfessorStatement, determineIdSubjectStatement, deleteProfessorForSubjectStatement, getProfessorStatement, determineIdProfessorStatement;
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public static SubjectDAO getInstance() {
        if(instance==null) instance = new SubjectDAO();
        return instance;

    }
    private SubjectDAO() {
        try {
            connection= DriverManager.getConnection("jdbc:sqlite:baza.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            getSubjectStatement = connection.prepareStatement("SELECT * FROM subject WHERE id=?");

        } catch (SQLException e) {
            regeneration();
            try {
                getSubjectStatement = connection.prepareStatement("SELECT * FROM subject WHERE id=?");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
//drzava je predmet
        try {
            //pripremljeni upiti za profesora
            deleteSubjectStatement = connection.prepareStatement("DELETE FROM subject WHERE id = ?");
            searchSubjectStatement = connection.prepareStatement("SELECT * FROM subject WHERE name LIKE ? ");
            changeSubjectStatement = connection.prepareStatement("UPDATE subject SET name = ?, subjects_professor=? WHERE id=?");
            getSubjectsStatement = connection.prepareStatement("SELECT * FROM subject");
            addSubjectStatement = connection.prepareStatement("INSERT INTO subject VALUES(?,?,?) ");
            determineIdSubjectStatement = connection.prepareStatement("SELECT MAX(id)+1 FROM subject");
            deleteProfessorForSubjectStatement = connection.prepareStatement("DELETE from professor where professors_subject = ?");
            getProfessorStatement = connection.prepareStatement("SELECT * FROM professor WHERE id=?");
            addProfessorStatement = connection.prepareStatement("INSERT INTO professor VALUES(?,?,?,?,?,?,?) ");
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


    public void close() {
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


    private Subject getSubjectResultSet(ResultSet rs, Professor professor) throws SQLException {
        Subject newSubject = new Subject(rs.getInt(1), rs.getString(2), professor);
        return newSubject;
    }

    private Subject getSubject(int id, Professor professor) {
        try {
            getSubjectStatement.setInt(1, id);
            ResultSet rs = getSubjectStatement.executeQuery();
            if (!rs.next()) return null;
            return getSubjectResultSet(rs, professor);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void changeSubject(Subject subject) {
        try {

            changeSubjectStatement.setString(1,subject.getName());
            changeSubjectStatement.setInt(3,subject.getId());
            changeSubjectStatement.setInt(2,subject.getProfessor().getId());

            changeSubjectStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteSubject(String name) {
        try {
            searchSubjectStatement.setString(1,name);
            ResultSet rs = searchSubjectStatement.executeQuery();
            if(!rs.next()) return;
            Subject subject = getSubjectResultSet(rs,null);
            deleteProfessorForSubjectStatement.setInt(1,subject.getId());
            deleteProfessorForSubjectStatement.executeUpdate();

            deleteSubjectStatement.setInt(1, subject.getId());
            deleteSubjectStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public ArrayList<Subject> subjects() {
        ArrayList<Subject> result = new ArrayList<>();
        try {
            ResultSet rs =getSubjectsStatement.executeQuery();
            while(rs.next()) {
                Professor professor = getProfessor(rs.getInt(3));
                Subject subject = getSubjectResultSet(rs, professor);
                result.add(subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public void addSubject(Subject subject) {
        try {
            ResultSet rs =determineIdSubjectStatement.executeQuery();
            int id=1;
            if(rs.next()) {
                id=rs.getInt(1);
            }
            addSubjectStatement.setInt(1,id);
            addSubjectStatement.setString(2,subject.getName());
            addSubjectStatement.setInt(3,subject.getProfessor().getId());
            addSubjectStatement.executeUpdate();

            ResultSet rs1 = determineIdProfessorStatement.executeQuery();
            int id1=1;
            if(rs1.next()) {
                id1=rs1.getInt(1);
            }
            Professor professor = subject.getProfessor();
            addProfessorStatement.setInt(1,id1);
            addProfessorStatement.setString(2,professor.getName());
            addProfessorStatement.setString(3,professor.getSurname());
            addProfessorStatement.setString(4,professor.getEmail());
            addProfessorStatement.setString(5,professor.getUsername());
            addProfessorStatement.setString(6,professor.getPassword());
            addProfessorStatement.setInt(7,id);

            addProfessorStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public Subject searchSubject(String name) {
        try {
            searchSubjectStatement.setString(1,name);
            ResultSet rs = searchSubjectStatement.executeQuery();
            if(!rs.next()) return null;
            return getSubjectResultSet(rs, getProfessor(rs.getInt(3)));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private Professor getProfessor(int id) {
        try {
            getProfessorStatement.setInt(1, id);
            ResultSet rs = getProfessorStatement.executeQuery();
            if (!rs.next()) return null;
            return getProfessorResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private Professor getProfessorResultSet(ResultSet rs) throws SQLException {
        Professor professor = new Professor(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),null);
        professor.setSubject(getSubject(rs.getInt(7),professor));
        return professor;
    }
}
