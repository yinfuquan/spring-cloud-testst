package generator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GeneraterApplicationTests {

    @Test
    public void generator() throws SQLException {


        DatabaseUtil.getTableNamesByConnection().forEach(name->{
            MysqlGenerator mysqlGenerator = new MysqlGenerator();
            mysqlGenerator.generator(name);

        });



    }
}
