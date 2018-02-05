package IO;

import java.io.File;
import java.sql.*;

public class Database {
    private static String defaultFileName = "Data.sqlite";

    private static String createTableQuery = "CREATE TABLE Information" +
                                                "(" +
                                                "Name   VARCHAR(60)         ," +
                                                "Data   BLOB                 " +
                                                ")";

    private static String createIndices = "CREATE UNIQUE INDEX UniqueName ON Information(Name)";

    private static String insertStatement = "INSERT INTO Information VALUES" +
                                            "(?, ?)";

    private static String selectNamesStatement = "SELECT Name FROM Information";

    private static String getObjectStatement = "SELECT Data FROM Information " +
                                                "WHERE Name = ?";

    private static String deleteObjectStatement = "DELETE FROM Information " +
                                                    "WHERE Name = ?";

    private static Database instance;

    private Connection databaseConnection;

    public static Database getInstance()
    {
        if (instance == null)
            instance = new Database();

        return instance;
    }

    public boolean init()
    {
        boolean create = false;
        File f = new File(defaultFileName);

        if (!f.exists())
            create = true;

        try {
            this.databaseConnection = DriverManager.getConnection("jdbc:sqlite:" + defaultFileName);
        } catch (SQLException e) {
            return false;
        }

        if (create)
        {
            try
            {
                databaseConnection.prepareStatement(createTableQuery).execute();
                databaseConnection.prepareStatement(createIndices).execute();
            } catch (SQLException e) {
                return false;
            }
        }

        return true;
    }

    public String[] getList()
    {
        PreparedStatement selectStatement = null;
        String[] toReturn;
        int count;

        try {
            selectStatement = databaseConnection.prepareStatement("SELECT Count(*) FROM Information");
            selectStatement.execute();
        } catch (SQLException e) {
            return null;
        }

        /**
         * Get count of names to initialize the String array
         * */
        try {
            count = selectStatement.getResultSet().getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        /**
         * The database is empty, so return null
         * */
        if (count == 0)
            return null;

        try {
            selectStatement = databaseConnection.prepareStatement(selectNamesStatement);
            selectStatement.execute();
            toReturn = new String[count];
            ResultSet results = selectStatement.getResultSet();

            for (int i = 0; results.next(); i++)
            {
                toReturn[i] = results.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return toReturn;
    }

    public boolean insertObject(String name, byte[] object)
    {
        try {
            PreparedStatement statement = this.databaseConnection.prepareStatement(insertStatement);
            statement.setString(1, name);
            statement.setBytes(2, object);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public byte[] getObject(String name)
    {
        byte[] toReturn;

        try {
            PreparedStatement statement = this.databaseConnection.prepareStatement(getObjectStatement);
            statement.setString(1,name);
            statement.execute();
            ResultSet result = statement.getResultSet();
            toReturn = result.getBytes(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return toReturn;
    }

    public boolean deleteObject(String name)
    {
        try {
            PreparedStatement statement = this.databaseConnection.prepareStatement(deleteObjectStatement);
            statement.setString(1,name);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
