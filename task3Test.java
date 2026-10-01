public class task3Test {
    public static void main(String[] args) throws Exception {
        task3 t = new task3();

        float[] nums = {9f, 1f, 5f};
        float actual = t.getMid(nums);
        float expected = 5f;

        if (actual != expected) {
            throw new AssertionError("getMid([9, 1, 5]) should be 5, but was " + actual);
        }

        try {
            t.getMid(new float[]{1f, 2f});
            throw new AssertionError("Expected an exception for a non-3-length array");
        } catch (Exception ignored) {
            // this is the expected result for homework-style testing
        }

        System.out.println("task3 test passed");
    }
}
