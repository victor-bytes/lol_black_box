import com.qq.lol.utils.JdbcUtils;

import java.sql.Connection;

/**
 * @Auther: null
 * @Date: 2023/12/6 - 12 - 06 - 17:19
 * @Description: TODO
 * @version: 1.0
 */
public class TestJdbc {
    public static void main(String[] args) {
        Connection conn = JdbcUtils.getConnection();
        Connection conn2 = JdbcUtils.getConnection();
        System.out.println(conn);
        System.out.println(conn2);
    }
}
