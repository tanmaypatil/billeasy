package productivity.paperbilleasy;

/**
 * Created by Tanmay_Patil on 2/21/2015.
 */
public class PaperInfo {
    String paperName ;
    String paperFullName ;

    public PaperInfo(String paperName,String paperFullName  )
    {
        this.paperName = paperName;
        this.paperFullName = paperFullName;
    }

    public String getPaperName()
    {
        return paperName;
    }

    public String getPaperFullName()
    {
        return paperFullName;
    }
}
