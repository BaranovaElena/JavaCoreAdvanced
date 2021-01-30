public interface Moving {
    void run(int length);
    void jump(int height);
    boolean passTheBarrier(Barrier barrier);
}
