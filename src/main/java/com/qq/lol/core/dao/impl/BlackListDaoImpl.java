package com.qq.lol.core.dao.impl;

import com.qq.lol.core.dao.BlackListDao;
import com.qq.lol.dto.BlackPlayerDto;
import com.qq.lol.dto.PageResult;
import com.qq.lol.utils.JdbcUtils;
import com.qq.lol.utils.StandardOutTime;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: null
 * @Date: 2023/12/7 - 12 - 07 - 13:54
 * @Description: TODO
 * @version: 1.0
 */
public class BlackListDaoImpl implements BlackListDao {
    private static final BlackListDao blackListDao = new BlackListDaoImpl();

    private BlackListDaoImpl(){}

    public static BlackListDao getBlackListDao(){return blackListDao;}

    /**
     * @param blackPlayer :
     * @return java.lang.Integer =2添加成功，否则添加失败
     * @Description: 将玩家信息添加到黑名单
     * @Auther: null
     * @Date: 2023/12/7 - 13:45
     */
    @Override
    public Integer addBlackList(BlackPlayerDto blackPlayer) {
        if(blackPlayer == null)
            return 0;

        String blackListSql = "insert into `black_list`(`puuid`, `champion_id`, `kill`," +
                "`deaths`,`assists`,`win`,`reason`,`game_id`,`meet_count`,`is_play_with_friend`,`friend_puuid`) " +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String playerSql = "insert into `player`(`puuid`, `game_name`, `tag_line`, " +
                "`selected_position`, `platform_id`) " +
                "values(?, ?, ?, ?, ?)";

        Connection connection = JdbcUtils.getConnection();
        PreparedStatement ps = null;
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(blackListSql);
            ps.setString(1, blackPlayer.getPuuid());
            ps.setString(2, blackPlayer.getChampionId());
            ps.setInt(3, blackPlayer.getKills());
            ps.setInt(4, blackPlayer.getDeaths());
            ps.setInt(5, blackPlayer.getAssists());
            ps.setString(6, String.valueOf(blackPlayer.getWin()));
            ps.setString(7, blackPlayer.getReason());
            ps.setString(8, blackPlayer.getGameId());
            ps.setString(9, blackPlayer.getMeetCount());
            ps.setString(10, blackPlayer.getIsPlayWithFriend());
            ps.setString(11, blackPlayer.getFriendPuuid());
            int a = ps.executeUpdate();

            ps = connection.prepareStatement(playerSql);
            ps.setString(1, blackPlayer.getPuuid());
            ps.setString(2, blackPlayer.getGameName());
            ps.setString(3, blackPlayer.getTagLine());
            ps.setString(4, blackPlayer.getSelectedPosition());
            ps.setString(5, blackPlayer.getPlatformId());
            int b = ps.executeUpdate();

            connection.commit();
            return a + b;
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
     * @param puuid :
     * @return com.qq.lol.dto.BlackPlayerDto 若在黑名单中，则返回 BlackPlayerDto
     * @Description: 查询玩家是否在黑名单中
     * @Auther: null
     * @Date: 2023/12/7 - 13:46
     */
    @Override
    public BlackPlayerDto queryPlayerByPuuid(String puuid) {
        if(puuid == null || StringUtils.equals("", puuid))
            return null;

        BlackPlayerDto blackPlayer = new BlackPlayerDto();
        String blackListSql = "select * from `black_list` where `puuid` = ?";
        String playerSql = "select `puuid`, `game_name`, `tag_line`, `selected_position`, `platform_id` " +
                "from `player` where `puuid` = ?";

        Connection connection = JdbcUtils.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(blackListSql);
            ps.setString(1, puuid);
            rs = ps.executeQuery();
            while (rs.next()) {
                blackPlayer.setId(rs.getString("id"));
                blackPlayer.setPuuid(rs.getString("puuid"));
                blackPlayer.setChampionId(rs.getString("champion_id"));
                blackPlayer.setKills(rs.getInt("kill"));
                blackPlayer.setDeaths(rs.getInt("deaths"));
                blackPlayer.setAssists(rs.getInt("assists"));
                blackPlayer.setWin(Integer.parseInt(rs.getString("win")));
                blackPlayer.setReason(rs.getString("reason"));
                blackPlayer.setGameId(rs.getString("game_id"));
                blackPlayer.setMeetCount(rs.getString("meet_count"));
                blackPlayer.setIsPlayWithFriend(rs.getString("is_play_with_friend"));
                blackPlayer.setFriendPuuid(rs.getString("friend_puuid"));
                blackPlayer.setCreated_at
                        (StandardOutTime.timestampToStr(rs.getTimestamp("created_at"),
                                "yyyy-MM-dd HH:mm:ss"));
                blackPlayer.setLast_update_time
                        (StandardOutTime.timestampToStr(rs.getTimestamp("last_update_time"),
                                "yyyy-MM-dd HH:mm:ss"));
            }

            ps = connection.prepareStatement(playerSql);
            ps.setString(1, puuid);
            rs = ps.executeQuery();
            while (rs.next()) {
                blackPlayer.setGameName(rs.getString("game_name"));
                blackPlayer.setTagLine(rs.getString("tag_line"));
                blackPlayer.setSelectedPosition(rs.getString("selected_position"));
                blackPlayer.setPlatformId(rs.getString("platform_id"));
            }

            connection.commit();
            return blackPlayer;
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

            JdbcUtils.release(rs, ps, connection);
        }

        return null;
    }

    /**
     * @param puuid : >1移除成功，<=1移除失败
     * @return java.lang.Integer
     * @Description: 将玩家从黑名单移除
     * @Auther: null
     * @Date: 2023/12/7 - 13:47
     */
    @Override
    public Integer removePlayer(String puuid) {
        if(puuid == null || StringUtils.equals("", puuid))
            return 0;

        String blackListSql = "delete from `black_list` where `puuid` = ?";
        String playerSql = "delete from `player` where `puuid` = ?";

        Connection connection = JdbcUtils.getConnection();
        PreparedStatement ps = null;
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(blackListSql);
            ps.setString(1, puuid);
            int a = ps.executeUpdate();
            ps = connection.prepareStatement(playerSql);
            ps.setString(1, puuid);
            int b = ps.executeUpdate();

            connection.commit();
            return a + b;
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
     * @param blackPlayer :
     * @return java.lang.Integer
     * @Description: 修改黑名单中玩家信息,返回 1 成功，0 失败
     * @Auther: null
     * @Date: 2023/12/7 - 13:50
     */
    @Override
    public Integer updateBlackList(BlackPlayerDto blackPlayer) {
        if(blackPlayer == null)
            return 0;

        String blackListSql = "update `black_list` set `kill` = ?,  `deaths` = ?, " +
                "`assists` = ?, `reason` = ?, `meet_count` = ? where `puuid` = ?";

        Connection connection = JdbcUtils.getConnection();
        PreparedStatement ps = null;
        try {
            connection.setAutoCommit(false);
            // 先查后改
            ps = connection.prepareStatement(blackListSql);
            BlackPlayerDto bp = blackListDao.queryPlayerByPuuid(blackPlayer.getPuuid());
            if(bp == null)
                // 要修改的玩家不存在黑名单中
                return 0;

            ps.setInt(1, blackPlayer.getKills());
            ps.setInt(2, blackPlayer.getDeaths());
            ps.setInt(3, blackPlayer.getAssists());
            ps.setString(4, blackPlayer.getReason());
            ps.setString(5, blackPlayer.getMeetCount());
            ps.setString(6, blackPlayer.getPuuid());
            int i = ps.executeUpdate();

            connection.commit();
            return i;
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
     * @Description: 分页查询所有黑名单玩家信息
     * @param pageNo: 第几页
     * @param pageSize: 每页数量
     * @return com.qq.lol.dto.PageResult<com.qq.lol.dto.BlackPlayerDto>
     * @Auther: null
     * @Date: 2023/12/7 - 16:59
     */
    @Override
    public PageResult<BlackPlayerDto> queryAllPlayer(long pageNo, long pageSize) {
        List<BlackPlayerDto> blackPlayers = new ArrayList<>();
        String sql = "select b.*, p.`game_name` game_name, p.`tag_line` tag_line, " +
                "p.`selected_position` selected_position, p.`platform_id` platform_id " +
                "from `black_list` b join `player` p on b.puuid = p.puuid limit ? offset ?";
        String countSql = "select count(*) count from `black_list` b join `player` p on b.puuid = p.puuid";

        Connection connection = JdbcUtils.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        long count = 0;
        try {
            connection.setAutoCommit(false);
            // 获取黑名单
            ps = connection.prepareStatement(sql);
            ps.setLong(1, pageSize);
            ps.setLong(2, pageNo);
            rs = ps.executeQuery();
            while (rs.next()) {
                BlackPlayerDto blackPlayer = new BlackPlayerDto();
                blackPlayer.setId(rs.getString("id"));
                blackPlayer.setPuuid(rs.getString("puuid"));
                blackPlayer.setChampionId(rs.getString("champion_id"));
                blackPlayer.setKills(rs.getInt("kill"));
                blackPlayer.setDeaths(rs.getInt("deaths"));
                blackPlayer.setAssists(rs.getInt("assists"));
                blackPlayer.setWin(Integer.parseInt(rs.getString("win")));
                blackPlayer.setReason(rs.getString("reason"));
                blackPlayer.setGameId(rs.getString("game_id"));
                blackPlayer.setMeetCount(rs.getString("meet_count"));
                blackPlayer.setIsPlayWithFriend(rs.getString("is_play_with_friend"));
                blackPlayer.setFriendPuuid(rs.getString("friend_puuid"));
                blackPlayer.setCreated_at
                        (StandardOutTime.timestampToStr(rs.getTimestamp("created_at"),
                                "yyyy-MM-dd HH:mm:ss"));
                blackPlayer.setLast_update_time
                        (StandardOutTime.timestampToStr(rs.getTimestamp("last_update_time"),
                                "yyyy-MM-dd HH:mm:ss"));
                blackPlayer.setGameName(rs.getString("game_name"));
                blackPlayer.setTagLine(rs.getString("tag_line"));
                blackPlayer.setSelectedPosition(rs.getString("selected_position"));
                blackPlayer.setPlatformId(rs.getString("platform_id"));

                blackPlayers.add(blackPlayer);
            }
            // 获取黑名单总数
            ps = connection.prepareStatement(countSql);
            rs = ps.executeQuery();
            while (rs.next())
                count = rs.getLong("count");

            connection.commit();
            return new PageResult<>(blackPlayers, count, pageNo, pageSize);
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

            JdbcUtils.release(rs, ps, connection);
        }

        return null;
    }


}
