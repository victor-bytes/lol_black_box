package com.qq.lol.app.dao.impl;

import com.qq.lol.app.dao.HeroDao;
import com.qq.lol.dto.HeroDto;
import com.qq.lol.utils.JdbcUtils;
import javafx.scene.image.Image;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Auther: null
 * @Date: 2023/12/6 - 12 - 06 - 20:28
 * @Description: TODO
 * @version: 1.0
 */
public class HeroDaoImpl implements HeroDao {
    private static final HeroDao heroDao = new HeroDaoImpl();

    private HeroDaoImpl(){}

    public static HeroDao getHeroDao(){
        return heroDao;
    }

    /**
     * @return java.lang.Integer >1 英雄信息更新到了数据库，=1英雄数量未改变，<1更新数据库失败
     * @Description: 保存所有英雄信息
     * @Auther: null
     * @Date: 2023/12/6 - 20:19
     */
    @Override
    public Integer saveHeroes(List<HeroDto> heroes) {
        if(heroes == null || heroes.size() == 0)
            return 0;

        // 只有 riot 推出了新英雄，英雄总数增加的情况才会真的更新到数据库
        Integer heroCount = heroDao.getHeroCount();
        if(heroes.size() == heroCount)
            return 1;

        // 保存英雄信息
        String deleteTable = "truncate table `hero`";   // truncate操作无法回滚
        String insertSql = "insert into `hero`(`champion_id`,`champion_name`,`alias`,`square_portrait_path`)" +
                " values(?, ?, ?, ?)";
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement ps = null;
        try {
            connection.setAutoCommit(false);
            // 先输出表中所有数据，再插入数据
            ps = connection.prepareStatement(deleteTable);
            ps.executeUpdate();
            ps = connection.prepareStatement(insertSql);
            // 使用批处理 sql
            for (HeroDto hero : heroes) {
                ps.setString(1, hero.getId());
                ps.setString(2, hero.getName());
                ps.setString(3, hero.getAlias());
                ps.setString(4, hero.getSquarePortraitPath());
                // 逐条将 sql 语句加入到批处理包中，此时还没有执行sql语句
                ps.addBatch();
            }
            // 再一次批量执行sql语句, 将批处理包里面的sql语句全部执行
            int[] batch = ps.executeBatch();
            // 执行完成后，清空批处理包中所有的sql语句
            ps.clearBatch();

            connection.commit();
            return batch.length;
        } catch (SQLException e) {
            System.out.println("执行过程发生了异常，撤销执行的 sql语句。");
            try {
                connection.rollback();
            } catch (SQLException throwAbles) {
                throwAbles.printStackTrace();
            }

            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            JdbcUtils.release(null, ps, connection);
        }

        return 0;
    }

    /**
     * @return java.lang.Integer
     * @Description: 获取数据库中英雄数量
     * @Auther: null
     * @Date: 2023/12/6 - 20:23
     */
    @Override
    public Integer getHeroCount() {
        String sql = "select count(id) count_hero from `hero`";
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Integer count = 0;
        try {
            ps = connection.prepareStatement(sql);
            // ============开启事务==========
            connection.setAutoCommit(false);
            resultSet = ps.executeQuery();
            while(resultSet.next())
                count = resultSet.getInt("count_hero");

            // ============提交事务==========
            // 程序运行到此处，说明没有出现任何问题，则提交事务
            connection.commit();
        } catch (SQLException e) {
            System.out.println("执行过程发生了异常，撤销执行的 sql语句。");
            // ============回滚事务==========
            // 程序在执行过程中如果出现了异常，就会tiao到这个地方，此时就需要回滚事务
            try {
                connection.rollback();
            } catch (SQLException throwAbles) {
                throwAbles.printStackTrace();
            }

            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // 关闭资源
            JdbcUtils.release(resultSet, ps, connection);
        }

        return count;
    }

    /**
     * @param championId :
     * @return com.qq.lol.dto.HeroDto
     * @Description: 根据championId获取英雄信息
     * @Auther: null
     * @Date: 2023/12/6 - 20:21
     */
    @Override
    public HeroDto getHeroByChampionId(String championId) {
        if(championId == null || StringUtils.equals("", championId))
            return new HeroDto();

        String sql = "select `id` , champion_id, champion_name, alias, square_portrait_path from `hero` where champion_id = ?";
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        HeroDto hero = new HeroDto();
        try {
            ps = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            ps.setString(1, championId);

            resultSet = ps.executeQuery();
            while(resultSet.next()){
                hero.setDbId(resultSet.getInt("id"));
                hero.setId(resultSet.getString("champion_id"));
                hero.setName(resultSet.getString("champion_name"));
                hero.setAlias(resultSet.getString("alias"));
                hero.setSquarePortraitPath(resultSet.getString("square_portrait_path"));
            }

            connection.commit();
        } catch (SQLException e) {
            System.out.println("执行过程发生了异常，撤销执行的 sql语句。");
            try {
                connection.rollback();
            } catch (SQLException throwAbles) {
                throwAbles.printStackTrace();
            }

            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            JdbcUtils.release(resultSet, ps, connection);
        }

        return hero;
    }

    /**
     * @Description: 保存召唤师头像到数据库
     * @param inputStream:
     * @param imgId: 图片名
     * @return java.lang.Integer -1表示保存失败
     * @Auther: null
     * @Date: 2023/12/20 - 17:52
     */
    @Override
    public Integer saveProfileIcon(InputStream inputStream, String imgId) {
        String sql = "insert into `profile_icon`(`data`,`img_id`) values(?, ?)";
        return saveImg(inputStream, imgId, sql);
    }

    /**
     * @Description: 保存英雄头像到数据库
     * @param inputStream :
     * @param imgId       :
     * @return java.lang.Integer
     * @Auther: null
     * @Date: 2023/12/20 - 17:59
     */
    @Override
    public Integer saveChampionIcon(InputStream inputStream, String imgId) {
        String sql = "insert into `champion_icon`(`data`,`img_id`) values(?, ?)";
        return saveImg(inputStream, imgId, sql);
    }

    /**
     * @Description: 获取召唤师头像
     * @param imgId :
     * @return javafx.scene.image.Image
     * @Auther: null
     * @Date: 2023/12/20 - 18:36
     */
    @Override
    public Image getProfileIcon(String imgId) {
        String sql = "select `data` from `profile_icon` where `img_id` = ?";

        return getImg(imgId, sql);
    }

    /**
     * @Description: 获取英雄头像
     * @param imgId :
     * @return javafx.scene.image.Image
     * @Auther: null
     * @Date: 2023/12/20 - 18:37
     */
    @Override
    public Image getChampionIcon(String imgId) {
        String sql = "select `data` from `champion_icon` where `img_id` = ?";

        return getImg(imgId, sql);
    }

    /**
     * @Description: 保存图片到数据库
     * @param inputStream:
     * @param imgId:
     * @param sql:
     * @return java.lang.Integer
     * @Auther: null
     * @Date: 2023/12/20 - 17:57
     */
    private Integer saveImg(InputStream inputStream, String imgId, String sql) {
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            ps.setBlob(1, inputStream);
            ps.setString(2, imgId);

            int i = ps.executeUpdate();
            connection.commit();
            inputStream.close();
            return i;
        } catch (SQLException | IOException e) {
            System.out.println("执行过程发生了异常，撤销执行的 sql语句。");
            try {
                connection.rollback();
            } catch (SQLException throwAbles) {
                throwAbles.printStackTrace();
            }

            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            JdbcUtils.release(ps, connection);
        }

        return -1;
    }

    private Image getImg(String imgId, String sql) {
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Image image = null;
        try {
            ps = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            ps.setString(1, imgId);

            rs = ps.executeQuery();
            while (rs.next()) {
                InputStream binaryStream = rs.getBinaryStream("data");
                image = new Image(binaryStream);
                binaryStream.close();
            }

            connection.commit();
            return image;
        } catch (SQLException | IOException e) {
            System.out.println("执行过程发生了异常，撤销执行的 sql语句。");
            try {
                connection.rollback();
            } catch (SQLException throwAbles) {
                throwAbles.printStackTrace();
            }

            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            JdbcUtils.release(rs, ps, connection);
        }

        return image;
    }
}
