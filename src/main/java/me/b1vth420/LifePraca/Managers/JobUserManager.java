package me.b1vth420.LifePraca.Managers;

import me.b1vth420.LifePraca.Objects.JobUser;

import java.util.concurrent.ConcurrentHashMap;

public class JobUserManager {

    private static ConcurrentHashMap<String, JobUser> jobusers = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, JobUser> getJobUsers(){
        return new ConcurrentHashMap<>(jobusers);
    }
    public static void addJobUser(JobUser ju){
        jobusers.putIfAbsent(ju.getName(), ju);
    }

    public static void removeJobUser(JobUser ju){
        jobusers.remove(ju.getName());
    }
}
