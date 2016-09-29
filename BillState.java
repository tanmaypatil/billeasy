package productivity.paperbilleasy;

/**
 * Created by Tanmay_Patil on 1/26/2015.
 */

import java.util.Calendar;

/** holds the current state of the Bill*/
public class BillState {
    /* Field stores the current month which will not change */
    int currMonth;
    /* Field stores the current month which will not change */
    int currYear;
    int month;
    int year;
    String monthDesc;

    public BillState()
    {
        Calendar cal = Calendar.getInstance();
        /* Calendar class starts month from 0 ,
           However actual month starts from 1
           Internally it is maintained as 1 and CalendarUtils reduces by 1 .
         */
        this.month = cal.get(Calendar.MONTH)+1;
        this.year = cal.get(Calendar.YEAR);
        this.monthDesc = PaperBillHelper.getMonthandYear(month,year);
        this.currMonth = this.month ;
        this.currYear = this.year;
    }
    public BillState(int month,int year,String monthDesc)
    {
        this.month = month;
        this.year = year;
        this.monthDesc = monthDesc;
    }
    /** find out the next month */
    public void moveNext ()
    {
        this.month++ ;
        if(this.month >= 12)
        {
            this.month =1 ;
            this.year++;
        }
        this.monthDesc = PaperBillHelper.getMonthandYear(this.month,this.year);
    }
    /** find out the prev month */
    public void movePrev()
    {
        this.month--;
        if (this.month < 1) {
            this.month = 12;
            this.year --;
        }
        this.monthDesc = PaperBillHelper.getMonthandYear(this.month,this.year);
    }

    public int getMonth() {
        return month;
    }

    public int getYear()
    {
        return year;
    }

    public String getDesc()
    {
        return this.monthDesc;
    }

    public int getCurrMonth(){ return this.currMonth;}

    public int getCurrYear()
    {
        return this.currYear;
    }
}
