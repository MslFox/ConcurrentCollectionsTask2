package Netology.Multithread_Functional.Multithread.СoncurrentCollections.Task2;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class DemoMaps {
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String RESET = "\033[0m";  // Text Reset

    private static final String CONCURRENT = YELLOW_BOLD + "ConcurrentHashMap" + RESET;
    private static final String SYNCHRO = GREEN_BOLD + "Collections.synchronizedMap" + RESET;

    private final Map<Integer, Integer> concurrentMap;
    private final Map<Integer, Integer> synchroMap;

    private int mapSize;
    private long begin;

    public DemoMaps(int mapSize) {
        this.mapSize = mapSize;
        this.concurrentMap = new ConcurrentHashMap<>();
        this.synchroMap = Collections.synchronizedMap(new HashMap<>());
    }

    public void setMapSize(int mapSize) {
        this.mapSize = mapSize;
    }

    public void createMaps() {
        System.out.printf(BLUE + "ДОБАВЛЕНИЕ %s ЭЛЕМЕНТОВ\n" + RESET, mapSize);

        begin = System.currentTimeMillis();
        IntStream.range(0, mapSize).
                forEach(x -> concurrentMap.put(x, x));
        printTime("Однопоточное добавление", (System.currentTimeMillis() - begin), CONCURRENT);
        concurrentMap.clear();

        begin = System.currentTimeMillis();
        IntStream.range(0, mapSize).
                forEach(x -> synchroMap.put(x, x));
        printTime("Однопоточное добавление", (System.currentTimeMillis() - begin), SYNCHRO);
        synchroMap.clear();

        begin = System.currentTimeMillis();
        IntStream.range(0, mapSize).
                parallel().
                forEach(x -> concurrentMap.put(x, x));
        printTime("Параллельное добавление", (System.currentTimeMillis() - begin), CONCURRENT);
        concurrentMap.clear();

        begin = System.currentTimeMillis();
        IntStream.range(0, mapSize).
                parallel().
                forEach(x -> synchroMap.put(x, x));
        printTime("Параллельное добавление", (System.currentTimeMillis() - begin), SYNCHRO);
        synchroMap.clear();
    }

    public void removeMapsElements() {
        System.out.printf(BLUE + "УДАЛЕНИЕ %s ЭЛЕМЕНТОВ\n" + RESET, mapSize);

        createConcurrentHashMap();
        begin = System.currentTimeMillis();
        IntStream.range(0, mapSize).
                forEach(concurrentMap::remove);
        printTime("Однопоточное удаление", (System.currentTimeMillis() - begin), CONCURRENT);

        createSynchroMap();
        begin = System.currentTimeMillis();
        IntStream.range(0, mapSize).
                forEach(synchroMap::remove);
        printTime("Однопоточное удаление", (System.currentTimeMillis() - begin), SYNCHRO);

        createConcurrentHashMap();
        begin = System.currentTimeMillis();
        IntStream.range(0, mapSize).
                parallel().
                forEach(concurrentMap::remove);
        printTime("Параллельное удаление", (System.currentTimeMillis() - begin), CONCURRENT);

        createSynchroMap();
        begin = System.currentTimeMillis();
        IntStream.range(0, mapSize).
                parallel().
                forEach(synchroMap::remove);
        printTime("Параллельное удаление", (System.currentTimeMillis() - begin), SYNCHRO);
    }

    private void createSynchroMap() {
        synchroMap.clear();
        IntStream.range(0, mapSize).
                forEach(x -> synchroMap.put(x, x));
    }

    private void createConcurrentHashMap() {
        concurrentMap.clear();
        IntStream.range(0, mapSize).
                parallel().
                forEach(x -> concurrentMap.put(x, x));
    }

    private void printTime(String text, Long time, String type) {
        System.out.printf("%24s:  %7dms%40s\n", text, time, type);
    }

    public Map<Integer, Integer> getConcurrentMap() {
        return concurrentMap;
    }

    public void readMap() {
        createSynchroMap();
        begin = System.currentTimeMillis();
        IntStream.range(0, mapSize).
                forEach(synchroMap::get);
        printTime("Однопоточное чтение", (System.currentTimeMillis() - begin), SYNCHRO);

        begin = System.currentTimeMillis();
        IntStream.range(0, mapSize).parallel().
                forEach(synchroMap::get);
        printTime("Параллельное чтение", (System.currentTimeMillis() - begin), SYNCHRO);
        synchroMap.clear();


        createConcurrentHashMap();
        begin = System.currentTimeMillis();
        IntStream.range(0, mapSize).
                forEach(concurrentMap::get);
        printTime("Однопоточное чтение", (System.currentTimeMillis() - begin), CONCURRENT);

        begin = System.currentTimeMillis();
        IntStream.range(0, mapSize).parallel().
                forEach(concurrentMap::get);
        printTime("Параллельное чтение", (System.currentTimeMillis() - begin), CONCURRENT);
        concurrentMap.clear();

    }

}
