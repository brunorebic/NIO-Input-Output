package com.timbuchalka;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Created by timbuchalka on 2/04/2016.
 */
public class Locations implements Map<Integer, Location> {
    private static Map<Integer, Location> locations = new LinkedHashMap<>();

    public static void main(String[] args) throws IOException {

        //writing to file using java.nio
        Path locPath = FileSystems.getDefault().getPath("locations_big.txt");
        Path dirPath = FileSystems.getDefault().getPath("directions_big.txt");

        try (BufferedWriter locFile = Files.newBufferedWriter(locPath);
             BufferedWriter dirFile = Files.newBufferedWriter(dirPath)) {
            for (Location location : locations.values()) {
                locFile.write(location.getLocationID() + "," + location.getDescription() + "\n");
                for (String direction : location.getExits().keySet()) {
                    if (!direction.equalsIgnoreCase("Q")) {
                        dirFile.write(location.getLocationID() + "," + direction + "," +
                                location.getExits().get(direction) + "\n");
                    }
                }
            }
        }
    }

    static {
        //reading from file using java.nio
        Path locPath = FileSystems.getDefault().getPath("locations.txt");
        Path dirPath = FileSystems.getDefault().getPath("directions.txt");

        try (Scanner locFile = new Scanner(Files.newBufferedReader(locPath));
             Scanner dirFile = new Scanner(Files.newBufferedReader(dirPath))) {

            locFile.useDelimiter(",");
            dirFile.useDelimiter(",");

            while (locFile.hasNextLine()) {
                int locID = locFile.nextInt();
                locFile.skip(locFile.delimiter());
                String description = locFile.nextLine();

                locations.put(locID, new Location(locID, description, null));
            }

            Map<String, Integer> exits = new LinkedHashMap<>();
            while (dirFile.hasNextLine()) {
                int locationID = dirFile.nextInt();
                dirFile.skip(dirFile.delimiter());
                String direction = dirFile.next();
                dirFile.skip(dirFile.delimiter());
                int destination = Integer.parseInt(dirFile.nextLine());
                exits.put(direction, destination);

                locations.get(locationID).addExit(direction, destination);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int size() {
        return locations.size();
    }

    @Override
    public boolean isEmpty() {
        return locations.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return locations.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return locations.containsValue(value);
    }

    @Override
    public Location get(Object key) {
        return locations.get(key);
    }

    @Override
    public Location put(Integer key, Location value) {
        return locations.put(key, value);
    }

    @Override
    public Location remove(Object key) {
        return locations.remove(key);
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Location> m) {

    }

    @Override
    public void clear() {
        locations.clear();

    }

    @Override
    public Set<Integer> keySet() {
        return locations.keySet();
    }

    @Override
    public Collection<Location> values() {
        return locations.values();
    }

    @Override
    public Set<Entry<Integer, Location>> entrySet() {
        return locations.entrySet();
    }
}
