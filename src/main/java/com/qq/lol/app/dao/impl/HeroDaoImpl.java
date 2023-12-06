package com.qq.lol.app.dao.impl;

import com.qq.lol.app.dao.HeroDao;
import com.qq.lol.dto.HeroDto;
import com.qq.lol.utils.JdbcUtils;

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
     * @return java.lang.Integer
     * @Description: 保存所有英雄信息
     * @Auther: null
     * @Date: 2023/12/6 - 20:19
     */
    @Override
    public Integer saveHeroes(List<HeroDto> heroes) {
        return null;
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
        return null;
    }
}
