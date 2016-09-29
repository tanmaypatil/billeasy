package productivity.paperbilleasy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Tanmay_Patil on 2/11/2015.
 */
public class CalendarViewAdapter  extends BaseAdapter {
    ArrayList<HolidayDateObject> calendarDays;
    private Context context;
    int month;
    int year;
    public CalendarViewAdapter(Context context,int month ,int year)
    {
        calendarDays = new ArrayList<HolidayDateObject>();
        this.context = context;
        this.month = month ;
        this.year = year;
        calendarDays.add( new HolidayDateObject("S",0));
        calendarDays.add(new HolidayDateObject("M",0));
        calendarDays.add(new HolidayDateObject("T",0));
        calendarDays.add(new HolidayDateObject("W",0));
        calendarDays.add(new HolidayDateObject("T",0));
        calendarDays.add(new HolidayDateObject("F",0));
        calendarDays.add(new HolidayDateObject("S",0));

        Calendar calObj = new GregorianCalendar(year,month-1,1);
        int day_of_week =calObj.get(Calendar.DAY_OF_WEEK);
        int maxDays = calObj.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println("day of week inside calendarViewAdapter "+day_of_week);
        for(int j =1; j < day_of_week; j++)
        {
            calendarDays.add(new HolidayDateObject("",0));
        }
        for(int i = 1; i <= maxDays;i++)
        {
            String dateString = i+"";
            calendarDays.add(new HolidayDateObject(dateString,0));
        }



    }

    public Object getItem(int position) {
        return calendarDays.get(position);
        //return null;
    }

    public long getItemId(int position)
    {
        return 0;
    }
    public int getCount()
    {
        return calendarDays.size();
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View returnedView;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null)
        {
            returnedView = inflater.inflate(R.layout.activity_grid_item,null);

        }
        else
        {
            returnedView = (View)convertView;

        }
        TextView dateVal = (TextView) returnedView.findViewById(R.id.calendarDate);
        System.out.println("Position is "+parent+" Value "+dateVal);
        HolidayDateObject holObj = (HolidayDateObject)calendarDays.get(position);
        dateVal.setText(holObj.getDate());

        return returnedView;
    }
}
