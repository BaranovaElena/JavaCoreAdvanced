public class Main {
    public static void main(String[] args) {
        //участники
        Human human1 = new Human();
        Cat cat1 = new Cat();
        Robot robot1 = new Robot();
        Moving[] participants = {human1, cat1, robot1};

        //препятствия
        Wall wall1 = new Wall(1);
        Wall wall2 = new Wall(2);
        Wall wall5 = new Wall(5);
        Treadmill treadmill500 = new Treadmill(500);
        Treadmill treadmill1000 = new Treadmill(1000);
        Treadmill treadmill5000 = new Treadmill(5000);

        Barrier[] barriers = {treadmill500, wall1,
                              treadmill1000, wall2,
                              treadmill5000, wall5};

        for (Moving participant : participants) {
            for (Barrier barrier : barriers) {
                if (!participant.passTheBarrier(barrier))
                    break;
            }
        }
    }
}
