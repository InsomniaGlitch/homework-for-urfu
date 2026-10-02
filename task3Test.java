public class task3Test {
    public static void main(String[] args) throws Exception {
        task3 t = new task3();
        float[] nums = {9f, 1f, 5f};
        float real = t.getMid(nums);
        float expected = 5f;
        if (real != expected) {
            throw new AssertionError("getMid([9, 1, 5]) should be 5, not " + real);
        }
        try {
            t.getMid(new float[]{1f, 2f});
            throw new AssertionError("Expected an exception for a 'length !=3' array");
        } catch (Exception e) {}
        System.out.println("task3 test passed");
    }
}
