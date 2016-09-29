package productivity.paperbilleasy;

/**
 * Created by Tanmay_Patil on 1/26/2015.
 */

 import java.util.HashMap;
public interface PaperPriceInfo {
    public HashMap getPriceInfo();
    public void setPriceInfo();
    public HashMap getPriceInfoOfAPaper(String paper_name);
    public HashMap getDeliveryCharges();
    public int getDeliveryChargeOfApaper(String paper_name);

}

