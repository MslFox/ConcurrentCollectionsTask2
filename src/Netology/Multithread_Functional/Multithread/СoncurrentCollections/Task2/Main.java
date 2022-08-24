package Netology.Multithread_Functional.Multithread.СoncurrentCollections.Task2;

public class Main {
    public static void main(String[] args) {
        System.out.println("******************************************\n" +
                "* Задача 2. Разница в производительности *\n" +
                "******************************************");

        DemoMaps demoMaps;
        demoMaps = new DemoMaps(100_000_000);

        demoMaps.createMaps();
        demoMaps.removeMapsElements();
        demoMaps.readMap();

        demoMaps.setMapSize(1_000_000);
        demoMaps.createMaps();
        demoMaps.removeMapsElements();

        demoMaps.setMapSize(10_000);
        demoMaps.createMaps();
        demoMaps.removeMapsElements();
    }
}


