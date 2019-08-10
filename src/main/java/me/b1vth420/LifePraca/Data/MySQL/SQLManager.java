package me.b1vth420.LifePraca.Data.MySQL;

import me.b1vth420.LifePraca.Main;
import me.b1vth420.LifePraca.Managers.BuildingArenaManager;
import me.b1vth420.LifePraca.Managers.JobUserManager;
import me.b1vth420.LifePraca.Managers.PatternArenaManager;
import me.b1vth420.LifePraca.Objects.BuildingArea;
import me.b1vth420.LifePraca.Objects.Job;
import me.b1vth420.LifePraca.Objects.JobUser;
import me.b1vth420.LifePraca.Objects.PatternArena;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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
                ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS lifeJobUser(UUID varchar(36) not null, name VARCHAR(16) not null, job text, money text not null, level text not null, primary key(uuid))");
                ps.executeUpdate();
            }  catch (SQLException e) { e.printStackTrace(); }

            try {
                ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS lifeDrugFarms(location VARCHAR(120) not null, type text not null, primary key(location))");
                ps.executeUpdate();
            }  catch (SQLException e) { e.printStackTrace(); }

            try {
                ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS lifePatternArena(name VARCHAR(120) not null, center text not null, size int not null, primary key(name))");
                ps.executeUpdate();
            }  catch (SQLException e) { e.printStackTrace(); }

            try {
                ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS lifeBuildArena(name VARCHAR(120) not null, center text not null, patternArena text not null, primary key(name))");
                ps.executeUpdate();
            }  catch (SQLException e) { e.printStackTrace(); }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public void savePatternArenas(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            for(PatternArena pa : PatternArenaManager.getPatternArenas().values()){
                ps = conn.prepareStatement("INSERT INTO lifePatternArena(name, center, size) VALUES (?,?,?) ON DUPLICATE KEY UPDATE name=?, center=?, size=?");
                ps.setString(1, pa.getName());
                ps.setString(2, pa.getCenter().getWorld().getName() + " " + pa.getCenter().getBlockX() + " " + pa.getCenter().getBlockY() + " " + pa.getCenter().getBlockZ());
                ps.setInt(3, pa.getSize());
                ps.setString(4, pa.getName());
                ps.setString(5, pa.getCenter().getWorld().getName() + " " + pa.getCenter().getBlockX() + " " + pa.getCenter().getBlockY() + " " + pa.getCenter().getBlockZ());
                ps.setInt(6, pa.getSize());
                ps.executeUpdate();
            }
        } catch (SQLException e){ e.printStackTrace();
        } finally { pool.close(conn, ps, null); }
    }

    public void saveBuildArenas(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            for(BuildingArea ba : BuildingArenaManager.getArena().values()){
                ps = conn.prepareStatement("INSERT INTO lifeBuildArena(name, center, patternArena) VALUES (?,?,?) ON DUPLICATE KEY UPDATE name=?, center=?, patternArena=?");
                ps.setString(1, ba.getName());
                ps.setString(2, ba.getCenter().getWorld().getName() + " " + ba.getCenter().getBlockX() + " " + ba.getCenter().getBlockY() + " " + ba.getCenter().getBlockZ());
                ps.setString(3, ba.getPa().getName());
                ps.setString(4, ba.getName());
                ps.setString(5, ba.getCenter().getWorld().getName() + " " + ba.getCenter().getBlockX() + " " + ba.getCenter().getBlockY() + " " + ba.getCenter().getBlockZ());
                ps.setString(6, ba.getPa().getName());
                ps.executeUpdate();
            }
        } catch (SQLException e){ e.printStackTrace();
        } finally { pool.close(conn, ps, null); }
    }

    public void loadPatternArenas(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM lifePatternArena");
            result = ps.executeQuery();
            while(result.next()){
                String l[] = result.getString("center").split(" ");
                new PatternArena(result.getString("name"), new Location(Bukkit.getWorld(l[0]), Integer.parseInt(l[1]), Integer.parseInt(l[2]), Integer.parseInt(l[3])), result.getInt("size"));
            }
        } catch (SQLException e){ e.printStackTrace();
        } finally { pool.close(conn, ps, result); }
    }

    public void loadBuildArenas(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM lifeBuildArena");
            result = ps.executeQuery();
            while(result.next()){
                String l[] = result.getString("center").split(" ");
                new BuildingArea(result.getString("name"), new Location(Bukkit.getWorld(l[0]), Integer.parseInt(l[1]), Integer.parseInt(l[2]), Integer.parseInt(l[3])), PatternArena.get(result.getString("patternArena")));
            }
        } catch (SQLException e){ e.printStackTrace();
        } finally { pool.close(conn, ps, result); }
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
        } catch (SQLException e){ e.printStackTrace();
        } finally { pool.close(conn, ps, null);
        }
    }

    public void updateDrugFarms(Location l, Material m){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("INSERT INTO lifeDrugFarms(location, type) VALUES (?,?) ON DUPLICATE KEY UPDATE type=?");
            ps.setString(1, l.getWorld().getName() + " " + l.getBlockX() + " " + l.getBlockY() + " " + l.getBlockZ());
            ps.setString(2, m.toString());
            ps.setString(3, m.toString());
            ps.executeUpdate();
        } catch (SQLException e){ e.printStackTrace();
        } finally { pool.close(conn, ps, null);
        }
    }

    public void deleteDrugFram(Location l){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("DELETE FROM lifeDrugFarms WHERE location=?");
            ps.setString(1, l.getWorld().getName() + " " + l.getBlockX() + " " + l.getBlockY() + " " + l.getBlockZ());
            ps.executeUpdate();
            Main.getInst().getDrugFarms().remove(l);
        } catch (SQLException e){ e.printStackTrace();
        } finally { pool.close(conn, ps, null);
        }
    }

    public void loadDrugFarms(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM lifeDrugFarms");
            result = ps.executeQuery();
            while(result.next()){
                String s = result.getString("location");
                String ss[] = s.split(" ");
                Location l = new Location(Bukkit.getWorld(ss[0]), Integer.parseInt(ss[1]), Integer.parseInt(ss[2]), Integer.parseInt(ss[3]));
                Main.getInst().getDrugFarms().add(l);
            }
        } catch (SQLException e){ e.printStackTrace();
        } finally { pool.close(conn, ps, result);
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
            ps.setString(4, ChatUtil.formatDouble(ju.getMoney()).replace(",", "."));
            for(Map.Entry<Job, Map.Entry<Integer, Integer>> entry : ju.getLevels().entrySet()){
                s+=entry.getKey().getName()+ " " + entry.getValue().getKey() + " " + entry.getValue().getValue() +";";
            }
            ps.setString(5, s);
            ps.setString(6, ju.getName());
            ps.setString(7, ju.getJob().getName());
            ps.setString(8, ChatUtil.formatDouble(ju.getMoney()).replace(",", "."));
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
                    levels.put(Job.get(sss[0]), new AbstractMap.SimpleEntry<>(Integer.parseInt(sss[1]), Integer.parseInt(sss[2])));
                }
                new JobUser(result.getString("name"), UUID.fromString(result.getString("UUID")), Job.get(result.getString("job")), Double.parseDouble(result.getString("money")), levels);
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
                    levels.put(Job.get(sss[0]), new AbstractMap.SimpleEntry<>(Integer.parseInt(sss[1]), Integer.parseInt(sss[2])));
                }
                new JobUser(result.getString("name"), UUID.fromString(result.getString("UUID")), Job.get(result.getString("job")), Double.parseDouble(result.getString("money")), levels);
            }
        } catch (SQLException e){ e.printStackTrace();
        } finally { pool.close(conn, ps, result); }
    }

    public void savePlayers(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            for(JobUser ju : JobUserManager.getJobUsers().values()){
                String s = "";
                for(Map.Entry<Job, Map.Entry<Integer, Integer>> entry : ju.getLevels().entrySet()){
                    s+=entry.getKey().getName() +  " " + entry.getValue().getKey() + " " + entry.getValue().getValue() +";";
                }
                ps = conn.prepareStatement("INSERT INTO lifeJobUser(uuid, name, job, money, level) VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE name=?, job=?, money=?, level=?");
                ps.setString(1, ju.getUuid().toString());
                ps.setString(2, ju.getName());
                ps.setString(3, ju.getJob().getName());
                ps.setString(4, ChatUtil.formatDouble(ju.getMoney()).replace(",", "."));
                ps.setString(5, s);
                ps.setString(6, ju.getName());
                ps.setString(7, ju.getJob().getName());
                ps.setDouble(8, ju.getMoney());
                ps.setString(9, s);
                ps.executeUpdate();
            }
        } catch (SQLException e){ e.printStackTrace();
        } finally { pool.close(conn, ps, null); }
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
