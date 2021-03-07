/*import java.util.HashMap;

public class DBAuthService implements AuthService{

    HashMap<String, String> users = new HashMap<>();

    public DBAuthService(){
        users.put("Vasya", "pass");
        users.put("Toto", "pass");
        users.put("Foo", "pass");
    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {

        if(users.containsKey(login)&&users.get(login).equals(password)) {
            return login;
        }
        return null;

    }
}*/
import java.sql.*;

public class DBAuthService implements AuthService{
    private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/GBChat";
    private static final String DB_USER="postgres";
    private static final String DB_PASSWORD="vk461504kl";
    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (connection!=null)
            System.out.println("connected to DB");
        else
            System.out.println("fail connection");
    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        try(PreparedStatement stm = connection.prepareStatement(
                "SELECT * FROM public.\"Clients\" WHERE login='"+login+"' AND password='"+password+"'");
            ResultSet resultSet = stm.executeQuery()){
            if (resultSet.next()){
                return login;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
