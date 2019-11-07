package generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import generator.MysqlGenerator;
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


        Connection conn = DriverManager.getConnection(SuperGenerator.url, SuperGenerator.username,SuperGenerator.password);
        List<String> tableNames = DatabaseUtilTest.getTableNamesByConnection("bzl_face_db", conn);

        tableNames.forEach(name->{
            MysqlGenerator mysqlGenerator = new MysqlGenerator();
            mysqlGenerator.generator(name);

        });



    }
}
