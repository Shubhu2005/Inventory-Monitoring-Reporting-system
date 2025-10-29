package mockito;


import com.inventory.util.DBConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestUser {

    private UsersDAO usersDAO;

    @BeforeEach
    void setUp() {
        usersDAO = new UsersDAO();
    }

    @Test
    void testAddUser_Success() throws Exception {

        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockStmt = mock(PreparedStatement.class);

        try (MockedStatic<DBConnection> mockedDB = Mockito.mockStatic(DBConnection.class)) {
            mockedDB.when(DBConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStmt);
            when(mockStmt.executeUpdate()).thenReturn(1); // 1 row inserted

            Users user = new Users(1, "john", "1234", "admin");
            boolean result = usersDAO.addUser(user);

            assertTrue(result);
            verify(mockStmt, times(1)).setString(1, "john");
            verify(mockStmt, times(1)).setString(2, "1234");
            verify(mockStmt, times(1)).setString(3, "admin");
            verify(mockStmt, times(1)).executeUpdate();
        }
    }

    @Test
    void testAddUser_Failure() throws Exception {
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockStmt = mock(PreparedStatement.class);

        try (MockedStatic<DBConnection> mockedDB = Mockito.mockStatic(DBConnection.class)) {
            mockedDB.when(DBConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStmt);
            when(mockStmt.executeUpdate()).thenThrow(new SQLException("Insert failed"));

            Users user = new Users(1, "alice", "pwd", "user");
            boolean result = usersDAO.addUser(user);

            assertFalse(result);
        }
    }

    @Test
    void testGetAllUsers() throws Exception {
        Connection mockConnection = mock(Connection.class);
        Statement mockStatement = mock(Statement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        try (MockedStatic<DBConnection> mockedDB = Mockito.mockStatic(DBConnection.class)) {
            mockedDB.when(DBConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

            when(mockResultSet.next()).thenReturn(true, false);
            when(mockResultSet.getInt("id")).thenReturn(1);
            when(mockResultSet.getString("username")).thenReturn("john");
            when(mockResultSet.getString("password")).thenReturn("1234");
            when(mockResultSet.getString("role")).thenReturn("admin");

            List<Users> userList = usersDAO.getAllUsers();

            assertEquals(1, userList.size());
            assertEquals("john", userList.get(0).getUsername());
            verify(mockStatement, times(1)).executeQuery("SELECT * FROM users");
        }
    }
}
