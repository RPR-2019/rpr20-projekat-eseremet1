package ba.unsa.etf.rpr.projekat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MaterialDAO {
    private static MaterialDAO instance; //singleton klasa
    private PreparedStatement getMaterialStatement, deleteMaterialStatement, searchMaterialStatement, getMaterialsStatement, addMaterialStatement, determineIdMaterialStatement, getSubjectStatement;
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public static MaterialDAO getInstance() {
        if(instance==null) instance = new MaterialDAO();
        return instance;

    }
    private MaterialDAO() {
        try {
            connection= DriverManager.getConnection("jdbc:sqlite:baza.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            getMaterialsStatement = connection.prepareStatement("SELECT * FROM material WHERE id=?");

        } catch (SQLException e) {
            regeneration();
            try {
                getMaterialStatement = connection.prepareStatement("SELECT * FROM material WHERE id=?");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        try {
            //pripremljeni upiti za materijal
            deleteMaterialStatement = connection.prepareStatement("DELETE FROM material WHERE id = ?");
            searchMaterialStatement = connection.prepareStatement("SELECT * FROM material WHERE name LIKE ? ");
            getMaterialsStatement = connection.prepareStatement("SELECT * FROM material");
            addMaterialStatement = connection.prepareStatement("INSERT INTO material VALUES(?,?,?) ");
            determineIdMaterialStatement = connection.prepareStatement("SELECT MAX(id)+1 FROM material");
            getSubjectStatement = connection.prepareStatement("SELECT * FROM subject WHERE id=?");

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


    private Material getMaterialResultSet(ResultSet rs) throws SQLException {
        Material material = new Material(rs.getInt(1), rs.getString(2),null);
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

    private Subject getSubjectResultSet(ResultSet rs) throws SQLException {
        return new Subject(rs.getInt(1),rs.getString(2));
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

            addMaterialStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
}
