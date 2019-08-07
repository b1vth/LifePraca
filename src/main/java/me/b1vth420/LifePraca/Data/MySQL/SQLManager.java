package me.b1vth420.LifePraca.Data.MySQL;

import me.b1vth420.LifePraca.Main;
import me.b1vth420.LifePraca.Managers.JobUserManager;
import me.b1vth420.LifePraca.Objects.Job;
import me.b1vth420.LifePraca.Objects.JobUser;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SQLManager {

    private final Main plugin;
    private final ConnectionPoolManager pool;

    public SQLManager(Main plugin) {
        this.plugin = plugin;
        pool = new ConnectionPoolManager(plugin);
        createTable();
    }

    private void createTable() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            try {
                ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS lifeLog(UUID varchar(36) not null, name VARCHAR(16) not null, logType text not null, log text not null)");
                ps.executeUpdate();
            }  catch (SQLException e) { e.printStackTrace(); }

            try {
                ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS lifeJobUser(UUID varchar(36) not null, name VARCHAR(16) not null, job text, money double not null, level text not null, primary key(uuid))");
                ps.executeUpdate();
            }  catch (SQLException e) { e.printStackTrace(); }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public void updateLog(JobUser ju, String logType, String log){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("INSERT INTO lifeLog(uuid, name, logType, log) VALUES (?,?,?,?)");
            ps.setString(1, ju.getUuid().toString());
            ps.setString(2, ju.getName());
            ps.setString(3, logType);
            ps.setString(4, log);
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public void updatePlayer(JobUser ju){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            String s = "";
            conn = pool.getConnection();
            ps = conn.prepareStatement("INSERT INTO lifeJobUser(uuid, name, job, money, level) VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE name=?, job=?, money=?, level=?");
            ps.setString(1, ju.getUuid().toString());
            ps.setString(2, ju.getName());
            ps.setString(3, ju.getJob().getName());
            ps.setDouble(4, ju.getMoney());
            for(Map.Entry<Job, Map.Entry<Integer, Integer>> entry : ju.getLevels().entrySet()){
                s+=entry.getKey().getName()+ " " + entry.getValue().getKey() + " " + entry.getValue().getValue() +";";
            }
            ps.setString(5, s);
            ps.setString(6, ju.getName());
            ps.setString(7, ju.getJob().getName());
            ps.setDouble(8, ju.getMoney());
            ps.setString(9, s);
            ps.executeUpdate();
        } catch (SQLException e){ e.printStackTrace();
        } finally { pool.close(conn, ps, null); }
    }

    public void loadPlayer(Player p){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM lifeJobUser WHERE uuid=?");
            ps.setString(1, p.getUniqueId().toString());
            result = ps.executeQuery();
            if(result.next()){
                int i = 0;
                HashMap<Job, Map.Entry<Integer, Integer>> levels = new HashMap<>();
                String s = result.getString("level");
                String ss[] = s.split(";");
                for(char c : s.toCharArray())
                    if(c == ';') i++;

                for(int x=0; x<i-1; x++){
                    String sss[] = ss[x].split(" ");
                    System.out.println(sss[0] + " " + sss[1] + " " + sss[2]);
                    levels.put(Job.get(sss[0]), new AbstractMap.SimpleEntry<>(Integer.parseInt(sss[1]), Integer.parseInt(sss[2])));
                }
                new JobUser(result.getString("name"), UUID.fromString(result.getString("UUID")), Job.get(result.getString("job")), result.getDouble("money"), levels);
            }
        } catch (SQLException e){ e.printStackTrace();
        } finally { pool.close(conn, ps, result); }
    }

    public void loadPlayers(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM lifeJobUser");
            result = ps.executeQuery();
            while(result.next()){
                int i = 0;
                HashMap<Job, Map.Entry<Integer, Integer>> levels = new HashMap<>();
                String s = result.getString("level");
                String ss[] = s.split(";");
                for(char c : s.toCharArray())
                    if(c == ';') i++;

                for(int x=0; x<i-1; x++){
                    String sss[] = ss[x].split(" ");
                    System.out.println(sss[0] + " " + sss[1] + " " + sss[2]);
                    levels.put(Job.get(sss[0]), new AbstractMap.SimpleEntry<>(Integer.parseInt(sss[1]), Integer.parseInt(sss[2])));
                }
                new JobUser(result.getString("name"), UUID.fromString(result.getString("UUID")), Job.get(result.getString("job")), result.getDouble("money"), levels);
            }
        } catch (SQLException e){ e.printStackTrace();
        } finally { pool.close(conn, ps, result); }
    }

    public void savePlayers(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            conn = pool.getConnection();
            for(JobUser ju : JobUserManager.getJobUsers().values()){
                String s = "";
                ps = conn.prepareStatement("INSERT INTO lifeJobUser(uuid, name, job, money, level) VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE name=?, job=?, money=?, level=?");
                ps.setString(1, ju.getUuid().toString());
                ps.setString(2, ju.getName());
                ps.setString(3, ju.getJob().getName());
                ps.setDouble(4, ju.getMoney());
                for(Map.Entry<Job, Map.Entry<Integer, Integer>> entry : ju.getLevels().entrySet()){
                    s+=entry.getKey().getName()+ " " + entry.getValue().getKey() + " " + entry.getValue().getValue() +";";
                }
                ps.setString(5, s);
                ps.setString(6, ju.getName());
                ps.setString(7, ju.getJob().getName());
                ps.setDouble(8, ju.getMoney());
                ps.setString(9, s);
                ps.executeUpdate();
            }
        } catch (SQLException e){ e.printStackTrace();
        } finally { pool.close(conn, ps, result); }
    }

    public String getInfo(Player p){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM tec2login_usertable WHERE UserUniqueID=?");
            ps.setString(1, p.getUniqueId().toString());
            result = ps.executeQuery();
            if(result.next()){
                return new String(result.getString("UserFirstname") + " " + result.getString("Number_ID") + " " + result.getString("UseAge") + " " + result.getString("UserGender") + " " + result.getString("Join_Date"));
            }

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, result);
        }
        return null;
    }

    public void onDisable(){
        pool.closePool();
    }

}
