package productivity.paperbilleasy;

import android.util.Log;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * Created by Tanmay_Patil on 2/21/2015.
 * This class will store
 * 1) Paper price
 * 2) Delivery Charges
 * 3) Holiday information
 */
public class PaperPriceInfoRegistry {
    HashMap paperPriceInfo ;
    HashMap paperHolidayInfo;
    HashMap paperDeliveryChargeInfo;
    ArrayList<PaperInfo> paperLists;

    public PaperPriceInfoRegistry()
    {
        paperPriceInfo = new HashMap();
        paperHolidayInfo = new HashMap();
        paperDeliveryChargeInfo = new HashMap();
        paperLists = new ArrayList<PaperInfo>();
    }

    private void initialisePaperPrice()
    {
        HashMap ecotimesprice = new HashMap();
        ecotimesprice.put("Mon", 3);
        ecotimesprice.put("Tue", 3);
        ecotimesprice.put("Wed", 3);
        ecotimesprice.put("Thu", 3);
        ecotimesprice.put("Fri", 3);
        ecotimesprice.put("Sat", 10);
        ecotimesprice.put("Sun", 10);
        HashMap timesprice = new HashMap();
        timesprice.put("Mon", 7);
        timesprice.put("Tue", 7);
        timesprice.put("Wed", 7);
        timesprice.put("Thu", 7);
        timesprice.put("Fri", 7);
        timesprice.put("Sat", 8);
        timesprice.put("Sun", 8);

        paperPriceInfo.put("ECOTIMES",ecotimesprice);
        paperPriceInfo.put("TIMES",timesprice);

    }
    public void initialise()
    {
        initialisePaperPrice();
        initialiseDeliveryCharge();
        initialiseHolidayInfo();
    }

    private  void initialiseDeliveryCharge()
    {
        paperDeliveryChargeInfo.put("TIMES",30);
        paperDeliveryChargeInfo.put("ECOTIMES",30);
    }

    private void initialiseHolidayInfo()
    {
        ArrayList<GregorianCalendar> holidayInfo;
        holidayInfo = new ArrayList<GregorianCalendar>();
        paperHolidayInfo.put("ECOTIMES",holidayInfo);

        holidayInfo = new ArrayList<GregorianCalendar>();
        paperHolidayInfo.put("TIMES",holidayInfo);

    }

    public void AddPaper(PaperInfo newPaper)
    {
        paperLists.add(newPaper);
    }

    public void AddPaperList(ArrayList<PaperInfo> newPaperList)
    {
        paperLists.addAll(newPaperList);
    }

    public ArrayList<PaperInfo> getListOfPapers()
    {
        return paperLists;
    }

    public void setPaperDeliveryChargeInfo(String paperName, int deliveryCharge)
    {
        Log.d("setDeliveryCharge", "paper :" + paperName + " charge :" + deliveryCharge);
        paperDeliveryChargeInfo.put(paperName,deliveryCharge);
    }

    public int getDeliveryChargeOfPaper(String paperName)
    {
        Log.d("getDeliveryChargeOfPaper ","paperName :"+paperName);
        int deliveryCharge = 0 ;
        Integer val =(Integer) paperDeliveryChargeInfo.get(paperName);
        if ( val != null) {
            deliveryCharge = val.intValue();
        }
        return deliveryCharge;
    }

    public HashMap getPaperDeliveryChargeInfo()
    {
        return paperDeliveryChargeInfo;
    }

    public void setPaperPriceInfoOfAPaper(String paperName ,HashMap updatedPrice)
    {
        Log.d("setPaperPriceInfoOfAPaper","paperName "+paperName);
        paperPriceInfo.put(paperName,updatedPrice);
    }

    public HashMap getPaperPriceInfoOfAPaper(String paperName )
    {
        Log.d("setPaperPriceInfoOfAPaper","paperName "+paperName);
        HashMap currentPrice = ( HashMap ) paperPriceInfo.get(paperName);
        return currentPrice;
    }

    public void setPaperPriceInfo(HashMap paperPriceAll)
    {
        Log.d("setPaperPriceInfo","");
        paperPriceInfo.putAll(paperPriceAll);
    }

    public HashMap getPaperPriceInfo()
    {
        return paperPriceInfo;
    }

    public ArrayList<GregorianCalendar> getHolidayInfoForAPaper(String paperName)
    {
        Log.d("getHolidayInfoForAPaper","");
        ArrayList<GregorianCalendar> holidayMap  = (ArrayList<GregorianCalendar>) paperHolidayInfo.get(paperName);
        return holidayMap;
    }

    public void setHolidayInfoForAPaper(String paperName, ArrayList<GregorianCalendar> holidayListForAPaper)
    {
        Log.d("setHolidayInfoForAPaper","");
        paperHolidayInfo.put(paperName,holidayListForAPaper);
    }

}
