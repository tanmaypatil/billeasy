package productivity.paperbilleasy;

/**
 * Created by Tanmay_Patil on 2/15/2015.
 */
public class HolidayDateObject {
    String date;
    int color ;
    boolean colorBackGround ;

    public HolidayDateObject(String Date,int color)
    {
        this.date = Date;
        this.color = color;
        this.colorBackGround = false;
    }

    public boolean isDateAlreadySelected()
    {
        return  this.colorBackGround;
    }

    public String getDate()
    {
        return  this.date;
    }

    public void setColor(int color)
    {
        this.color = color;
        this.colorBackGround = true;
    }

    public void reSetColor(int color)
    {
        this.color = color;
        this.colorBackGround = false   ;
    }

    public int getColor()
    {
        return this.color;
    }

}
