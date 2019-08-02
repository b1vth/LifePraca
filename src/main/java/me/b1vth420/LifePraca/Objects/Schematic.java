package me.b1vth420.LifePraca.Objects;

import me.b1vth420.LifePraca.Managers.SchematicManager;

public class Schematic {

    private String name;
    private byte[] blocks;
    private byte[] data;
    private short width;
    private short lenght;
    private short height;
    private int time;
    private int level;

    public Schematic(String name, byte[] blocks, byte[] data, short width, short lenght, short height, int time, int level) {
        this.name = name;
        this.blocks = blocks;
        this.data = data;
        this.width = width;
        this.lenght = lenght;
        this.height = height;
        this.time = time;
        this.level = level;
        SchematicManager.addSchematic(this);
    }

    public String getName() { return name; }

    public byte[] getBlocks() {
        return blocks;
    }

    public byte[] getData() {
        return data;
    }

    public short getWidth() {
        return width;
    }

    public short getLenght() {
        return lenght;
    }

    public short getHeight() {
        return height;
    }

    public int getTime() { return time; }

    public int getLevel() { return level; }
}