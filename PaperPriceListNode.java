package productivity.paperbilleasy;

/**
 * Created by Tanmay_Patil on 1/31/2015.
 */

import java.util.HashMap;

/**
 * This is just wrapper class for holding paper price info
 */
public class PaperPriceListNode {
    String paperName;
    HashMap paperPrice;
    int deliveryCharge;

    public PaperPriceListNode(String paperName,HashMap paperPrice,int deliveryCharge)
    {
        this.paperName = paperName;
        this.paperPrice = paperPrice;
        this.deliveryCharge = deliveryCharge;

    }

    public String getPaperName()
    {
        return paperName;
    }

    public HashMap getPriceMap()
    {
        return paperPrice;
    }

    public int getDeliveryCharge()
    {
        return deliveryCharge;
    }
}
