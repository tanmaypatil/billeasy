package productivity.paperbilleasy;
import android.util.Log;

import java.util.*;



/**
 * Created by Tanmay_Patil on 1/25/2015.
 */
public class PaperBillHelper {

        public static String getMonthandYear(int month_no ,int year_no)
        {
            String desc= null;
            String month_name = "" ;

            switch(month_no)
            {
                case 1:
                    month_name = "Jan";
                    break;
                case 2:
                    month_name = "Feb";
                    break;
                case 3:
                    month_name = "Mar";
                    break;
                case 4:
                    month_name = "Apr";
                    break;
                case 5:
                    month_name = "May";
                    break;
                case 6:
                    month_name = "Jun";
                    break;
                case 7:
                    month_name = "Jul";
                    break;
                case 8:
                    month_name = "Aug" ;
                    break;
                case 9:
                    month_name = "Sep" ;
                    break;
                case 10:
                    month_name = "Oct" ;
                    break;
                case 11:
                    month_name = "Nov" ;
                    break;
                case 12:
                    month_name = "Dec" ;
                    break;

            }
            desc = month_name + " "+year_no;
            return desc;
        }

        public static String getCurrentMonthAndYear(BillState billSt)
        {
            String desc;
            /*
            Calendar cal = Calendar.getInstance();
            int month_no = cal.get(Calendar.MONTH);
            int year_no = cal.get(Calendar.YEAR);*/
            int month_no = billSt.getCurrMonth();
            int year_no = billSt.getCurrYear();
            desc = getMonthandYear(month_no,year_no);
            System.out.println("Current month and year :"+desc);
            return desc;

        }

        public static HashMap getPriceInfo()
        {
            PaperPriceInfo priceInfo = (PaperPriceInfo)  new PaperPriceHardCode();
            return priceInfo.getPriceInfo();
        }

        private static PaperPriceInfo getPaperPricinginfo()
        {
            PaperPriceInfo priceInfo = (PaperPriceInfo)  new PaperPriceHardCode();
            return priceInfo ;
        }

        private  static int calculateBill(HashMap paperPriceInfo, HashMap daysFreq)
        {
            int bill_amt =0 ;
            Iterator it = daysFreq.entrySet().iterator();
            int sum= 0;
            while(it.hasNext())
            {
                Map.Entry freqObj =  (Map.Entry) it.next();
                String dayName = (String)freqObj.getKey();
                int freq = ((Integer)freqObj.getValue()).intValue();
                int price = ((Integer) (paperPriceInfo.get(dayName))).intValue();
                bill_amt +=  freq * price;
            }
            return bill_amt ;
        }

        public static int calculateBillForAPaper(int month , int year, String paper_name)
        {
            // get the frequency of  days
            //* e.g in june-2014
            //* Sun,Mon	- 5
            //* Tue-Fri - 4
            //
            CalendarUtils calUtils = new CalendarUtils(month,year);
            HashMap daysFreq = calUtils.getFreqOfDays();
            PaperPriceInfo priceInfo = getPaperPricinginfo();
            HashMap paperPriceInfo = priceInfo.getPriceInfoOfAPaper(paper_name);
            int total_bill = calculateBill(paperPriceInfo,daysFreq);
            Log.d("Bill",paper_name+": "+total_bill);
            int delivery_charge = priceInfo.getDeliveryChargeOfApaper(paper_name);
            Log.d("delivery charge",paper_name+": "+delivery_charge);
            total_bill += delivery_charge;
            Log.d("Total bill",paper_name+": "+total_bill);
            return total_bill;
        }

        public static int calculateBillForAPaperWithPrice(int month , int year,
                                                          String paper_name, HashMap paperPriceInfo)
        {
            int total_bill = 0;
            CalendarUtils calUtils = new CalendarUtils(month,year);
            HashMap daysFreq = calUtils.getFreqOfDays();
            total_bill = calculateBill(paperPriceInfo,daysFreq);
            PaperPriceInfo priceInfo = getPaperPricinginfo();
            int delivery_charge = priceInfo.getDeliveryChargeOfApaper(paper_name);
            Log.d("delivery charge",paper_name+": "+delivery_charge);
            total_bill += delivery_charge;
            Log.d("Total bill",paper_name+": "+total_bill);
            return total_bill;

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

    private static  void reduceBillableDaysAsPerHolidays(int month,int year,
                                           HashMap dayFreq,
                                           ArrayList<GregorianCalendar> holidayMap)
    {
        Log.d(" reduceBillableDays","month :"+month);
        Log.d(" reduceBillableDays","year  :"+year);
        if( holidayMap != null && holidayMap.size() > 0 ) {
            ListIterator<GregorianCalendar> li = holidayMap.listIterator();
            while (li.hasNext()) {
                GregorianCalendar gcal = (GregorianCalendar) li.next();
                int date_month = gcal.get(Calendar.MONTH);
                int date_year = gcal.get(Calendar.YEAR);
                Log.d(" reduceBillableDays", "holiday month " + date_month);
                Log.d(" reduceBillableDays", "holiday month " + date_year);

            /* month and year are matching reduce frequency*/
                if (month - 1 == date_month && year == date_year) {
                    int day_of_week = gcal.get(Calendar.DAY_OF_WEEK);
                    String dayDesc = getDayName(day_of_week);
                    int freq = ((Integer) dayFreq.get(dayDesc)).intValue();
                    System.out.println("Old frequency for" + dayDesc + " : " + freq);
                    freq--;
                    dayFreq.put(dayDesc, freq);
                }

            }
        }

    }

    public static int calculateBillForAPaperWithPriceAndHoliday(int month ,
                                                                int year,
                                                                String paper_name,
                                                                HashMap paperPriceInfo,
                                                                ArrayList<GregorianCalendar> holidayMap)
    {
        CalendarUtils calUtils = new CalendarUtils(month,year);
        HashMap daysFreq = calUtils.getFreqOfDays();
        reduceBillableDaysAsPerHolidays(month,year,daysFreq,holidayMap);
        int total_bill =calculateBill(daysFreq,paperPriceInfo);
        PaperPriceInfo priceInfo = getPaperPricinginfo();
        int delivery_charge = priceInfo.getDeliveryChargeOfApaper(paper_name);
        Log.d("delivery charge",paper_name+": "+delivery_charge);
        total_bill += delivery_charge;
        Log.d("Total bill",paper_name+": "+total_bill);

        return total_bill;
    }


        public static int getCurrentMonthsBill(String paper_name,BillState billSt)
        {
            int year_no = billSt.getCurrYear();
            int month_no = billSt.getCurrMonth() ;
            int bill_amt = calculateBillForAPaper(month_no , year_no ,paper_name);
            return bill_amt ;

        }

        /* New method to calculate the bill which will replace the existing methods
           public methods . Below methods were having too many arguments .
           1 ) calculateBillForAPaperWithPriceAndHoliday
           2 ) calculateBillForAPaperWithPrice
         */
            public static int calculateBillTotalOfAPaper(int month , int year ,String paperName,
                PaperPriceInfoRegistry paperPriceRegister)
            {

                HashMap priceInfo = (HashMap)paperPriceRegister.getPaperPriceInfoOfAPaper(paperName);
                ArrayList<GregorianCalendar> holidayMap =
                    (ArrayList<GregorianCalendar> )paperPriceRegister.getHolidayInfoForAPaper(paperName);
                int total_bill = calculateBillForAPaperWithPriceAndHoliday(month,year,paperName,priceInfo,holidayMap);

                Log.d("Total bill -calculateBillTotalOfAPaper ",""+total_bill);
                return total_bill;
            }

            public static int calculateTotalBill(int month , int year, PaperPriceInfoRegistry paperPriceRegister)
            {
                Log.d("calculateTotalBill","start");
                ArrayList<PaperInfo> paperList = paperPriceRegister.getListOfPapers();
                int total_bill = 0;
                ListIterator<PaperInfo> li = paperList.listIterator();
                while( li.hasNext())
                {
                    PaperInfo paperObj = (PaperInfo)li.next();
                    String paperName = paperObj.getPaperName();
                    Log.d("calculateTotalBill ","paperName "+paperName);

                    HashMap priceInfo = (HashMap)paperPriceRegister.getPaperPriceInfoOfAPaper(paperName);
                    ArrayList<GregorianCalendar> holidayMap =
                            (ArrayList<GregorianCalendar> )paperPriceRegister.getHolidayInfoForAPaper(paperName);
                    int paper_bill = calculateBillForAPaperWithPriceAndHoliday(month,year,paperName,priceInfo,holidayMap);
                    Log.d("calculateTotalBill","paper name"+paperName+ "Bill ="+paper_bill);
                    total_bill += paper_bill;
                }
                Log.d("CalculateTotalBill ","Total = "+total_bill);
                return total_bill;
            }




    }



