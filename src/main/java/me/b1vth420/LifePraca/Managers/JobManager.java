package me.b1vth420.LifePraca.Managers;

import me.b1vth420.LifePraca.Objects.Job;

import java.util.concurrent.ConcurrentHashMap;

public class JobManager {

    private static ConcurrentHashMap<String, Job> jobs = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, Job> getJobs(){
        return new ConcurrentHashMap<>(jobs);
    }

    public static void addJob(Job j){
        jobs.putIfAbsent(j.getName(), j);
    }

    public static void removeJob(Job j){
        jobs.remove(j.getName());
    }
}
