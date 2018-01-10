public class TimeHandler {
    private static Long time;

    public static void startTime() {
        time = System.currentTimeMillis();
    }

    public static String fetchTime() {
        time = System.currentTimeMillis() - time;
        long second = (time / 1000) % 60;
        long minute = (time / (1000 * 60)) % 60;
        long hour = (time / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d:%d", hour, minute, second, time);
    }
}
