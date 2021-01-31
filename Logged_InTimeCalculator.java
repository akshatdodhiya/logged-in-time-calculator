import java.io.*;
public class Logged_InTimeCalculator {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        Logged_InTimeCalculator object = new Logged_InTimeCalculator();  // Create a class object

        System.out.println("Enter total number of users : ");
        int users=Integer.parseInt(br.readLine());

        int []id = new int[users];

        int []days = new int[users];
        for(int i = 0; i<users; i++)
        {
            System.out.println("Enter the ID :");  // Input for ID
            id[i] = Integer.parseInt(br.readLine());
            days[i] = object.subMain();
        }
        int largest_position = object.largestElement(days);

        int total_hours = (days[largest_position]/60) % 24;
        // Converting total logged in time to hours

        int total_minutes = days[largest_position]%60;
        // Converting total logged in time to minutes

        int total_day = days[largest_position]/(60*24);
        // Converting total logged in time to days

        for(int i=0; i<users; i++)
        {
            System.out.println("Your Login ID and Time : \n"+id[i]+" - "+total_day+" days, "+total_hours+" hours and " +
                total_minutes +" minutes.");
            // Showing output
        }

    }

    int largestElement(int[] days)
    {
        int max = days[0];
        int pos = 0;
        for(int i=0;i<days.length;i++){
            if(days[i] > max){
                max = days[i];
                pos = i;
            }
        }
        return pos;
    }

    int subMain()throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        Logged_InTimeCalculator object = new Logged_InTimeCalculator();  // Create a class object

        System.out.println("Enter Login date : (In format dd/mm/yy)");  // Input login date
        String ln_date = br.readLine();

        System.out.println("Enter login time : (In 24 hours format as hh:mm)");  // Input login time
        String ln_time = br.readLine();

        System.out.println("Enter Logout date : (In format dd/mm/yy)");  // Input logout date
        String lt_date = br.readLine();

        System.out.println("Enter logout time : (In 24 hours format as hh:mm)");  // Input logout time
        String lt_time = br.readLine();

        String[] login_date = ln_date.split("/", -2);  // Split string to get date, month and year respectively
        String[] logout_date = lt_date.split("/", -2); // Split string to get date, month and year respectively

        String[] login_time = ln_time.split(":", -2);  // Split string to get hours and minutes respectively
        String[] logout_time = lt_time.split(":", -2);  // Split string to get hours and minutes respectively

        int days1 = object.month(login_date[1], login_date[2]) + Integer.parseInt(login_date[0]);
        // Calling method month which returns no. of days completed from th new year

        int days2 = object.month(logout_date[1], logout_date[2]) + Integer.parseInt(logout_date[0]);
        // Calling method month which returns no. of days completed from th new year

        int days_from_year = object.year(login_date[2], logout_date[2]);
        // Calling a method which returns no. of days between two years

        boolean login_year = Integer.parseInt(login_date[2]) % 4 == 0;
        // variable that stores that a year is leap year or not

        int total_days;

        if(login_year && !login_date[2].equals(logout_date[2]))
            total_days = days_from_year + days2 + (366 - days1);
        // Adding days from year, days after new year in logout year, no. of days left for login year to complete
        else if(!login_year && !login_date[2].equals(logout_date[2]))
            total_days = days_from_year + days2 + (365 - days1);
        // Adding days from year, days after new year in logout year, no. of days left for login year to complete
        else
            total_days = days2 - days1;
        // Subtracting days1 from days2 to get difference between them in the same year

        if((Integer.parseInt(logout_time[0])*60)+Integer.parseInt(logout_time[1]) < (Integer.parseInt(login_time[0])*60)+Integer.parseInt(login_time[1]))
            total_days -= 1;
        // Reducing days by 1 if time1 is greater than time2

        int minutes_from_days = total_days * 24 * 60;  // Converting days to hours and then minutes

        int minutes_from_input = object.timeDifference(login_time[0], login_time[1], logout_time[0], logout_time[1]);
        // Handling time difference by calling method that returns no. of minutes between two different times

        return minutes_from_days + minutes_from_input;
        // Adding minutes from days and minutes from time to get final logged in time and returning the same value

    }

    int month(String m, String y)  // m stores month and y stores year
    // A function that calculates total days completed after new year
    {
        int total_days = 0;  // Declaring the total number of days before beginning the calculations
        int mm = Integer.parseInt(m);  // parsing the string into 'int' data type for calculations
        int yy = Integer.parseInt(y);  // parsing the string into 'int' data type for calculations
        mm -= 1; // Decrementing the number of month as days are not included to current month

        while (mm > 0) {
            if (mm == 1 || mm == 3 || mm == 5 || mm == 7 || mm == 8 || mm == 10 || mm == 12)
                total_days += 31;  // Adding number of days for month having total 31 days
            else if (mm == 2) {
                if (yy % 4 == 0)
                    total_days += 29;  // Adding days of february for leap year
                else
                    total_days += 28;  // Adding days of february for non-leap year
            } else
                total_days += 30;  // Adding the number of days for month having 30 days
            mm--;  // Reducing the month to calculate more number of days
        }
        return total_days;  // Returning the days completed before the same month
    }

    int year (String y1, String y2)  // y1 stores first year and y2 stores second year
    // A function that calculates no. of days between two years
    {
        int total_days = 0;
        int yy1 = Integer.parseInt(y1);  // Parsing string to int for calculations
        int yy2 = Integer.parseInt(y2);

        for (int i = yy1 + 1; i < yy2; i++) {
            if (i % 4 == 0)
                total_days += 366;
            else
                total_days += 365;
        }
        return total_days;
    }

    int timeDifference (String h1, String m1, String h2, String m2)
    {
        int hour1 = Integer.parseInt(h1);  // Parsing String to int for calculations
        int minute1 = Integer.parseInt(m1);
        int hour2 = Integer.parseInt(h2);
        int minute2 = Integer.parseInt(m2);

        int time1 = (hour1 * 60) + minute1;  // Converting time to minutes
        int time2 = (hour2 * 60) + minute2;

        if(time2 >= time1)
            return time2 - time1;  // Returning difference of minutes
        else
        {
            int output_time = 1440 - time1;
            // 1440 is total minutes in one day subtracted by completed minutes to get remaining time in a day

            return output_time + time2;
            // Returning total time between two times
        }
    }
}