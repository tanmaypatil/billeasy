package productivity.paperbilleasy;

import java.util.ArrayList;

/**
 * Created by Tanmay_Patil on 2/11/2015.
 * Class specifically meant for displaying calendar for
 * Given month
 */
public class CalendarGridDisplay {
    int calMonth;
    int calYear ;
    ArrayList<String> calGridItemvalue;

    public CalendarGridDisplay(int month,int year)
    {
        this.calMonth = month;
        this.calYear = year;
        calGridItemvalue.add("S");
        calGridItemvalue.add("M");
        calGridItemvalue.add("T");
        calGridItemvalue.add("W");
        calGridItemvalue.add("T");
        calGridItemvalue.add("F");
        calGridItemvalue.add("S");
        for( int i = 1 ; i<=30 ;i++)
        {
            String val = i + "";
            calGridItemvalue.add(val);
        }

    }

    public String get(int index)
    {
        return calGridItemvalue.get(index);
    }
}
