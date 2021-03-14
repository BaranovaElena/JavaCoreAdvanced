public class Cat implements Moving {
    private final int maxRun = 500;
    private final int maxJump = 5;

    @Override
    public void run(int length) {
        System.out.println("Cat runs " + length);
    }

    @Override
    public void jump(int height) {
        System.out.println("Cat jumps " + height);
    }
    @Override
    public boolean passTheBarrier(Barrier barrier) {
        if (barrier instanceof Wall) {
            int barrierHeight = ((Wall) barrier).getHeight();
            if (barrierHeight <= maxJump) {
                jump(barrierHeight);
                return true;
            }
            else
                return false;
        }
        if (barrier instanceof Treadmill) {
            int barrierLength = ((Treadmill) barrier).getLength();
            if (barrierLength <= maxRun) {
                run(barrierLength);
                return true;
            }
            else
                return false;
        }
        return false;
    }
}
