package ba.unsa.etf.rpr.project;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MaterialManagementDAO {
    private static MaterialManagementDAO instance; //singleton klasa
    private PreparedStatement getProfessorStatement, deleteProfessorStatement, searchProfessorStatement, changeProfessorStatement, getProfessorsStatement, addProfessorStatement, determineIdProfessorStatement, getSubjectStatement;
    private PreparedStatement deleteSubjectStatement, searchSubjectStatement, changeSubjectStatement, getSubjectsStatement, addSubjectStatement, determineIdSubjectStatement;
    private PreparedStatement getMaterialStatement, deleteMaterialStatement, searchMaterialStatement, getMaterialsStatement, addMaterialStatement, determineIdMaterialStatement, changeMaterialStatement;
    private PreparedStatement getStudentStatement, deleteStudentStatement, searchStudentStatement, changeStudentStatement, getStudentsStatement, addStudentStatement, determineIdStudentStatement, determineIndexStudentStatement;
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public static MaterialManagementDAO getInstance() {
        if(instance==null) instance = new MaterialManagementDAO();
        return instance;

    }
    private MaterialManagementDAO() {
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
            changeProfessorStatement = connection.prepareStatement("UPDATE professor SET name = ?, surname = ?, email = ?, username = ?, password = ?, professors_subject=?, picture=? WHERE id=?");
            getProfessorsStatement = connection.prepareStatement("SELECT * FROM professor");
            addProfessorStatement = connection.prepareStatement("INSERT INTO professor VALUES(?,?,?,?,?,?,?,?) ");
            determineIdProfessorStatement = connection.prepareStatement("SELECT MAX(id)+1 FROM professor");
            getSubjectStatement = connection.prepareStatement("SELECT * FROM subject WHERE id=?");


            //pripremljeni upiti za predmet
            deleteSubjectStatement = connection.prepareStatement("DELETE FROM subject WHERE id = ?");
            searchSubjectStatement = connection.prepareStatement("SELECT * FROM subject WHERE name LIKE ? ");
            changeSubjectStatement = connection.prepareStatement("UPDATE subject SET name = ? WHERE id=?");
            getSubjectsStatement = connection.prepareStatement("SELECT * FROM subject");
            addSubjectStatement = connection.prepareStatement("INSERT INTO subject VALUES(?,?) ");
            determineIdSubjectStatement = connection.prepareStatement("SELECT MAX(id)+1 FROM subject");

            //pripremljeni upiti za materijal
            deleteMaterialStatement = connection.prepareStatement("DELETE FROM material WHERE id = ?");
            searchMaterialStatement = connection.prepareStatement("SELECT * FROM material WHERE name LIKE ? ");
            getMaterialsStatement = connection.prepareStatement("SELECT * FROM material");
            addMaterialStatement = connection.prepareStatement("INSERT INTO material VALUES(?,?,?,?) ");
            determineIdMaterialStatement = connection.prepareStatement("SELECT MAX(id)+1 FROM material");
            getSubjectStatement = connection.prepareStatement("SELECT * FROM subject WHERE id=?");
            getMaterialsStatement = connection.prepareStatement("SELECT * FROM material");
            changeMaterialStatement = connection.prepareStatement("UPDATE material SET visibility = ? WHERE id=?");

            //pripremljeni upiti za studenta
            getStudentStatement = connection.prepareStatement("SELECT * FROM student WHERE id=?");
            deleteStudentStatement = connection.prepareStatement("DELETE FROM student WHERE id = ?");
            searchStudentStatement = connection.prepareStatement("SELECT * FROM student WHERE username LIKE ? ");
            changeStudentStatement = connection.prepareStatement("UPDATE student SET name = ?, surname = ?, email = ?, username = ?, password = ?, picture=?, number_index=? WHERE id=?");
            getStudentsStatement = connection.prepareStatement("SELECT * FROM student");
            addStudentStatement = connection.prepareStatement("INSERT INTO student VALUES(?,?,?,?,?,?,?,?) ");
            determineIdStudentStatement = connection.prepareStatement("SELECT MAX(id)+1 FROM student");
            determineIndexStudentStatement = connection.prepareStatement("SELECT max(number_index)+1 FROM student");

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


    private Professor getProfessorResultSet(ResultSet rs) throws SQLException {
        Professor professor = new  Professor(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(8));
        professor.setSubject(getSubject(rs.getInt(7),professor));
        return professor;

    }

    private Student getStudentResultSet(ResultSet rs) throws SQLException {
        Student student = new  Student(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8));
        return student;

    }

    private Subject getSubject(int id, Professor professor) {
        try {
            getSubjectStatement.setInt(1, id);
            ResultSet rs = getSubjectStatement.executeQuery();
            if (!rs.next()) return null;
            return getSubjectResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Subject getSubjectResultSet(ResultSet rs) throws SQLException {
        return new Subject(rs.getInt(1),rs.getString(2));
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

    private Student getStudent(int id) {
        try {
            getStudentStatement.setInt(1,id);
            ResultSet rs = getStudentStatement.executeQuery();
            if(!rs.next()) return null;
            return getStudentResultSet(rs);
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
            changeProfessorStatement.setInt(6,professor.getSubject().getId());
            changeProfessorStatement.setString(7, professor.getPicture());
            changeProfessorStatement.setInt(8,professor.getId());

            changeProfessorStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeStudent(Student student) {
        try {

            changeStudentStatement.setString(1,student.getName());
            changeStudentStatement.setString(2,student.getSurname());
            changeStudentStatement.setString(3,student.getEmail());
            changeStudentStatement.setString(4,student.getUsername());
            changeStudentStatement.setString(5,student.getPassword());
            changeStudentStatement.setString(6,student.getPicture());
            changeStudentStatement.setString(7, student.getIndex());
            changeStudentStatement.setInt(8, student.getId());

            changeStudentStatement.executeUpdate();
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

    public void deleteStudent(String username) {
        try {
            searchStudentStatement.setString(1,username);
            ResultSet rs = searchStudentStatement.executeQuery();
            if(!rs.next()) return;
            Student student = getStudentResultSet(rs);
            deleteStudentStatement.setInt(1,student.getId());
            deleteStudentStatement.executeUpdate();
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


    public ArrayList<Student> students() {
        ArrayList<Student> result = new ArrayList<>();
        try {
            ResultSet rs = getStudentsStatement.executeQuery();
            while(rs.next()) {
                Student student = getStudentResultSet(rs);
                result.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getIndex() {
        try {
            if (students().isEmpty()) return "10000";
            else {
                ResultSet rs = determineIndexStudentStatement.executeQuery();
                int index = 10000;
                if (rs.next()) {
                    index = rs.getInt(1);
                }
                return String.valueOf(index);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
            addProfessorStatement.setInt(7,professor.getSubject().getId());
            addProfessorStatement.setString(8, professor.getPicture());
            addProfessorStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addStudent(Student student) {
        try {
            ResultSet rs = determineIdStudentStatement.executeQuery();
            int id=1;
            if(rs.next()) {
                id=rs.getInt(1);
            }
            addStudentStatement.setInt(1,id);
            addStudentStatement.setString(2, student.getName());
            addStudentStatement.setString(3, student.getSurname());
            addStudentStatement.setString(4, student.getEmail());
            addStudentStatement.setString(5, student.getUsername());
            addStudentStatement.setString(6, student.getPassword());
            addStudentStatement.setString(7, student.getPicture());
            addStudentStatement.setString(8, student.getIndex());
            addStudentStatement.executeUpdate();
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

    public Student searchStudent(String username) {
        try {
            searchStudentStatement.setString(1,username);
            ResultSet rs = searchStudentStatement.executeQuery();
            if(!rs.next()) return null;
            return getStudentResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private Subject getSubject(int id) {
        try {
            getSubjectStatement.setInt(1, id);
            ResultSet rs = getSubjectStatement.executeQuery();
            if (!rs.next()) return null;
            return getSubjectResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void changeSubject(Subject subject) {
        try {

            changeSubjectStatement.setString(1,subject.getName());
            changeSubjectStatement.setInt(2,subject.getId());

            changeSubjectStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeMaterial(Material material) {
        try {
            searchMaterialStatement.setString(1,material.getName());
            ResultSet rs = searchMaterialStatement.executeQuery();
            if(!rs.next()) return;
            Material materialNew = getMaterialResultSet(rs);

            if(material.getType().equals(Visibility.PUBLIC)) {
               changeMaterialStatement.setInt(1,1);
            } else if(material.getType().equals(Visibility.PRIVATE)) {
                changeMaterialStatement.setInt(1,2);
            } else {
                changeMaterialStatement.setInt(1,3);
            }
            changeMaterialStatement.setInt(2,materialNew.getId());

            changeMaterialStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSubject(String name) {
        try {
            searchSubjectStatement.setString(1,name);
            ResultSet rs = searchSubjectStatement.executeQuery();
            if(!rs.next()) return;
            Subject subject = getSubjectResultSet(rs);

            deleteSubjectStatement.setInt(1, subject.getId());
            deleteSubjectStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public ArrayList<Subject> subjects() {
        ArrayList<Subject> result = new ArrayList<>();
        try {
            ResultSet rs = getSubjectsStatement.executeQuery();
            while(rs.next()) {
                Subject subject = getSubjectResultSet(rs);
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
            addSubjectStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public Subject searchSubject(String name) {
        try {
            searchSubjectStatement.setString(1,name);
            ResultSet rs = searchSubjectStatement.executeQuery();
            if(!rs.next()) return null;
            return getSubjectResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }






    private Material getMaterialResultSet(ResultSet rs) throws SQLException {
        Material material = new Material(rs.getInt(1), rs.getString(2),null,rs.getInt(4));
        material.setSubject(getSubject(rs.getInt(3),material));
        return material;

    }

    private Subject getSubject(int id, Material material) {
        try {
            getSubjectStatement.setInt(1, id);
            ResultSet rs = getSubjectStatement.executeQuery();
            if (!rs.next()) return null;
            return getSubjectResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Material getMaterial(int id) {
        try {
            getMaterialStatement.setInt(1,id);
            ResultSet rs = getMaterialStatement.executeQuery();
            if(!rs.next()) return null;
            return getMaterialResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    public void deleteMaterial(String name) {
        try {
            searchMaterialStatement.setString(1,name);
            ResultSet rs = searchMaterialStatement.executeQuery();
            if(!rs.next()) return;
            Material material = getMaterialResultSet(rs);
            deleteMaterialStatement.setInt(1,material.getId());
            deleteMaterialStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Material> materials() {
        ArrayList<Material> result = new ArrayList<>();
        try {
            ResultSet rs = getMaterialsStatement.executeQuery();
            while(rs.next()) {
                Material material = getMaterialResultSet(rs);
                result.add(material);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public void addMaterial(Material material) {
        try {
            ResultSet rs = determineIdMaterialStatement.executeQuery();
            int id=1;
            if(rs.next()) {
                id=rs.getInt(1);
            }
            addMaterialStatement.setInt(1,id);
            addMaterialStatement.setString(2,material.getName());
            addMaterialStatement.setInt(3,material.getSubject().getId());
            if(material.getType().equals(Visibility.PUBLIC)) {
                addMaterialStatement.setInt(4,1);
            } else if(material.getType().equals(Visibility.PRIVATE)) {
                addMaterialStatement.setInt(4,2);
            } else {
                addMaterialStatement.setInt(4,3);
            }
            addMaterialStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int getId() {
        int id=1;
        try {
            ResultSet rs = determineIdMaterialStatement.executeQuery();
            if(rs.next()) {
                id=rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }


    public Material searchMaterial(String name) {
        try {
            searchMaterialStatement.setString(1,name);
            ResultSet rs = searchMaterialStatement.executeQuery();
            if(!rs.next()) return null;
            return getMaterialResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void defaultData() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM student");
        stmt.executeUpdate("DELETE FROM professor");
        stmt.executeUpdate("DELETE FROM subject");
        stmt.executeUpdate("DELETE FROM material");

        regeneration();
    }
}
