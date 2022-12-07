package com.timbuchalka;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Created by timbuchalka on 2/04/2016.
 */
public class Locations implements Map<Integer, Location> {
    private static final Map<Integer, Location> locations = new LinkedHashMap<>();

    public static void main(String[] args) throws IOException {

        //writing to file only using java.nio
        //when writing using Files.write we are writing bytes to file
        Path newFile = FileSystems.getDefault().getPath("locations_nio.txt");
        StringBuilder builder = new StringBuilder();

        for (Location location : locations.values()) {
            builder.append(location.getLocationID());
            builder.append(",");
            builder.append(location.getDescription());
            builder.append("\n");

            for (String direction : location.getExits().keySet()) {
                builder.append(direction);
                builder.append(",");
                builder.append(location.getExits().get(direction));
                builder.append("\n");
            }
        }
        Files.write(newFile, builder.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND); //default StandardOpenOption.CREATE


        //writing object to file using java.nio and java.io
//        Path locPath = FileSystems.getDefault().getPath("locations.dat");
//
//        try (ObjectOutputStream locFile = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(locPath)))) {
//            for (Location location : locations.values()) {
//                locFile.writeObject(location);
//            }
//        }

        //writing variable by variable to file using java.nio and java.io
//        Path locPath = FileSystems.getDefault().getPath("locations_big.txt");
//        Path dirPath = FileSystems.getDefault().getPath("directions_big.txt");
//
//        try (BufferedWriter locFile = Files.newBufferedWriter(locPath);
//             BufferedWriter dirFile = Files.newBufferedWriter(dirPath)) {
//            for (Location location : locations.values()) {
//                locFile.write(location.getLocationID() + "," + location.getDescription() + "\n");
//                for (String direction : location.getExits().keySet()) {
//                    if (!direction.equalsIgnoreCase("Q")) {
//                        dirFile.write(location.getLocationID() + "," + direction + "," +
//                                location.getExits().get(direction) + "\n");
//                    }
//                }
//            }
//        }
    }

    static {

        //reading from file using only java.nio
        Path locPath = FileSystems.getDefault().getPath("locations.txt");
        Path dirPath = FileSystems.getDefault().getPath("directions.txt");

        try {
            List<String> locationsList = Files.readAllLines(locPath);
            List<String> directionsList = Files.readAllLines(dirPath);

            for (String str : locationsList) {
                int index = str.indexOf(",");  //position of "," in line

                int locID = Integer.parseInt(str.substring(0, index));  //everything before "," is location ID
                String description = str.substring(index + 1); //everything after is location description

                locations.put(locID, new Location(locID, description, null));
            }

            for (String str : directionsList) {
                String[] data = str.split(",");
                int locID = Integer.parseInt(data[0]);
                String direction = data[1];
                int destination = Integer.parseInt(data[2]);

                locations.get(locID).addExit(direction, destination);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


        //reading objects from a file using java.nio and java.io
//        Path locPath = FileSystems.getDefault().getPath("locations.dat");
//
//        try (ObjectInputStream locFile = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(locPath)))) {
//            boolean isEof = false;
//            while (!isEof) {
//                try {
//                    Location location = (Location) locFile.readObject();
//                    locations.put(location.getLocationID(), location);
//                } catch (EOFException e) {
//                    isEof = true;
//                }
//            }
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }


        //reading variable by variable from file using java.nio
//        Path locPath = FileSystems.getDefault().getPath("locations.txt");
//        Path dirPath = FileSystems.getDefault().getPath("directions.txt");
//
//        try (Scanner locFile = new Scanner(Files.newBufferedReader(locPath));
//             BufferedReader dirFile = Files.newBufferedReader(dirPath)) {
//
//            locFile.useDelimiter(",");
//
//            while (locFile.hasNextLine()) {
//                int locID = locFile.nextInt();
//                locFile.skip(locFile.delimiter());
//                String description = locFile.nextLine();
//
//                locations.put(locID, new Location(locID, description, null));
//            }
//
//            String input;
//            while ((input = dirFile.readLine()) != null) {
//                String[] data = input.split(",");
//                int locationID = Integer.parseInt(data[0]);
//                String direction = data[1];
//                int destination = Integer.parseInt(data[2]);
//
//                locations.get(locationID).addExit(direction, destination);
//
//            }
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
