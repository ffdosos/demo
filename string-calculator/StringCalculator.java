import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {

    public int add(String numbers) throws Exception {
        if (numbers == null || numbers.length() == 0)
            return 0;

        ArrayList<Integer> nums = parse(numbers);
        ArrayList<Integer> negatives = new ArrayList<>();

        int sum = 0;
        for (int num : nums) {
            if (num < 0) {
                negatives.add(num);
            } else if (num > 1000)
                continue;
            else
                sum += num;
        }

        if (negatives.size() > 0)
            throw new Exception("Negatives not allowed: "
                    + negatives.stream().map(String::valueOf).collect(Collectors.joining(",")));

        return sum;
    }

    private ArrayList<Integer> parse(String numbers) {
        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(numbers);
        ArrayList<Integer> nums = new ArrayList<>();
        while (m.find()) {
            String numString = m.group();
            nums.add(Integer.parseInt(numString));
            // System.out.println("found:"+numString);
        }
        return nums;
    }

    public static void main(String[] args) {
        String[] testStrings = { "", "1", "2", "1,2", "1\n2,3", "//;'n1,2", "-1,2", "2,-4,3,-5", "//[|||]\n1|||2|||3",
                "//[|][%]\n1|2%3" };

        for (String testString : testStrings) {

            try {
                StringCalculator calculator = new StringCalculator();
                System.out.println(calculator.add(testString) + "");
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}