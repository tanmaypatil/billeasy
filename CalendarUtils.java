package productivity.paperbilleasy;
import java.util.*;
import java.lang.Integer;
import java.text.*;

/**
 * Created by Tanmay_Patil on 1/24/2015.
 */
public class CalendarUtils {
        int month ;
        HashMap daysFreq ;
        HashMap	daysPerMonth;
        int tot_days ;

        private void populateDays()
        {
            daysPerMonth = new HashMap();
            daysPerMonth.put("Month1",31);
            daysPerMonth.put("Month2",28);
            daysPerMonth.put("Month3",31);
            daysPerMonth.put("Month4",30);
            daysPerMonth.put("Month5",31);
            daysPerMonth.put("Month6",30);
            daysPerMonth.put("Month7",31);
            daysPerMonth.put("Month8",31);
            daysPerMonth.put("Month9",30);
            daysPerMonth.put("Month10",31);
            daysPerMonth.put("Month11",30);
            daysPerMonth.put("Month12",31);
            System.out.println("daysPerMonth .."+daysPerMonth);
        }

        public int getNumDays(String mon , int year )
        {
            if(mon.equals("Month2"))
            {
                if((year % 4) == 0 )
                {
                    return 29 ;
                }
                else
                {
                    return 28;
                }
            }
            else
            {
                return ((Integer) daysPerMonth.get(mon)).intValue();
            }

        }


        public CalendarUtils(int month , int year)
        {
            daysFreq = new HashMap();
            //For gregorian calendar month starts from zero
            Calendar gr = new GregorianCalendar(year,month-1,1);
            int day_of_week = gr.get(Calendar.DAY_OF_WEEK);
            int maxDays = gr.getActualMaximum(Calendar.DAY_OF_MONTH);
            System.out.println("day of week"+day_of_week);
            System.out.println("max days"+maxDays);
		/* calculate frequency of each day */
            calcDayFreq(maxDays,day_of_week);
            System.out.println(daysFreq);
        }


        private static String getDayName(int dayNo)
        {
            String dayName="";
            switch(dayNo)
            {
                case 1:
                    dayName = "Sun";
                    break;
                case 2:
                    dayName = "Mon";
                    break;
                case 3:
                    dayName = "Tue";
                    break;
                case 4:
                    dayName = "Wed";
                    break;
                case 5:
                    dayName = "Thu";
                    break;
                case 6:
                    dayName = "Fri";
                    break;
                case 7 :
                    dayName = "Sat";
                    break;
                default :
                    System.out.println("Invalid days");

            }
            return dayName;

        }

        public HashMap getFreqOfDays()
        {
            return daysFreq;
        }

        private  void calcDayFreq(int maxDays,int day_of_week )
        {
            int completeWeeks = 0;
            int remaind = 0;
            int freq = 0;
            String dayName;

            completeWeeks= maxDays / 7;
            remaind = maxDays % 7;

            int temp_day_of_week = day_of_week;
            int rem;
            int ubw ;
            int lbw=day_of_week;
            freq = completeWeeks;

            for(rem=1 ; rem <= 7;rem++)
            {
                dayName = getDayName(rem);
                daysFreq.put(dayName, freq);
            }

            for( rem=1 ; rem <= remaind; rem++,temp_day_of_week++)
            {
                if(temp_day_of_week > 7 )
                    temp_day_of_week = 1 ;
                dayName = getDayName(temp_day_of_week);
                //System.out.println(" [ "+rem+"]-> "+dayName);
                daysFreq.put(dayName,  freq+ 1);

            }
        }

   }

