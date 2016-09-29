package productivity.paperbilleasy;
import java.util.HashMap;
import java.lang.Integer;
/**
 * Created by Tanmay_Patil on 1/26/2015.
 */
public class PaperPriceHardCode implements PaperPriceInfo {
    HashMap priceInput;
    HashMap deliveryCharge;

    public PaperPriceHardCode()
    {
        setPriceInfo();
    }

    public HashMap getPriceInfo()
    {
        return priceInput;
    }

    public void setPriceInfo()
    {
        priceInput = new HashMap();
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

        priceInput.put("ECOTIMES", ecotimesprice);
        priceInput.put("TIMES", timesprice);

        deliveryCharge = new HashMap();
        deliveryCharge.put("ECOTIMES",30);
        deliveryCharge.put("TIMES",30);

    }


    public HashMap getPriceInfoOfAPaper(String paper_name) {
        // TODO Auto-generated method stub
        HashMap paperMap = (HashMap) priceInput.get(paper_name);
        return paperMap;

    }

    public HashMap getDeliveryCharges()
    {
        return deliveryCharge;
    }

    public int getDeliveryChargeOfApaper(String paper_name)
    {
        return ((Integer)deliveryCharge.get(paper_name)).intValue();
    }
}
