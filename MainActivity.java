package productivity.paperbilleasy;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {
    final BillState billSt=new BillState();
    PaperPriceInfoRegistry paperPriceRegister = new PaperPriceInfoRegistry();
    HashMap updatedPrices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paperPriceRegister.AddPaper(new PaperInfo("ECOTIMES","ECONOMIC TIMES"));
        paperPriceRegister.AddPaper(new PaperInfo("TIMES","TIMES OF INDIA"));
        paperPriceRegister.initialise();

        /* Display current month and year*/
        String curr_month = PaperBillHelper.getCurrentMonthAndYear(billSt);
        final TextView monthDesc = (TextView)findViewById(R.id.billMonth);
        monthDesc.setText(curr_month);
        /*    Get the bill for current month */
        final double eco_times_bill = PaperBillHelper.getCurrentMonthsBill("ECOTIMES",billSt);
        Log.d("ECO-TIMES-bill", eco_times_bill + "");
        double bill = PaperBillHelper.getCurrentMonthsBill("TIMES",billSt);
        Log.d("TIMES-bill", String.format("%s", bill));
        double total_bill = eco_times_bill + bill;
        final TextView totalBill = (TextView)findViewById(R.id.totalBill);
        String totalBillS = total_bill+"";
        totalBill.setText(totalBillS);
        /* Handle next button click*/
        ImageButton next_button = (ImageButton)findViewById(R.id.next);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billSt.moveNext();
                monthDesc.setText(billSt.getDesc());
                double eco_times_bill = PaperBillHelper.calculateBillForAPaper(billSt.getMonth(),billSt.getYear(),"ECOTIMES");
                double bill = PaperBillHelper.calculateBillForAPaper(billSt.getMonth(),billSt.getYear(),"TIMES");
                double total_bill  = eco_times_bill + bill;
                String totalBillS = total_bill+"";
                totalBill.setText(totalBillS);
            }
        });
          /* Handle previous button click*/
        ImageButton prev_button = (ImageButton)findViewById(R.id.prev);
        prev_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billSt.movePrev();
                monthDesc.setText(billSt.getDesc());
                double eco_times_bill = PaperBillHelper.calculateBillForAPaper(billSt.getMonth(),billSt.getYear(),"ECOTIMES");
                double bill = PaperBillHelper.calculateBillForAPaper(billSt.getMonth(),billSt.getYear(),"TIMES");
                double total_bill  = eco_times_bill + bill;
                String totalBillS = total_bill+"";
                totalBill.setText(totalBillS);
            }
        });

        Button prices_button = (Button) findViewById(R.id.updatePrice);
        prices_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent  prices_intent = new Intent(getApplicationContext(),PricesActivity.class);
              //  HashMap pricesInfo = PaperBillHelper.getPriceInfo();
                HashMap pricesInfo = paperPriceRegister.getPaperPriceInfo();
                prices_intent.putExtra("prices",pricesInfo);
                startActivityForResult(prices_intent,2);
            }

        });

        Button holidays_button = (Button)findViewById(R.id.markholiday);
        holidays_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent holiday_intent = new Intent(getApplicationContext(),MarkHolidayActivity.class);
                holiday_intent.putExtra("month",billSt.getMonth());
                holiday_intent.putExtra("year",billSt.getYear());
                startActivityForResult(holiday_intent,4);
            }
        });



    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 2 )
        {
            TextView viewBill = (TextView)findViewById(R.id.totalBill);
            updatedPrices = (HashMap) data.getSerializableExtra("updatedPrices");
            paperPriceRegister.setPaperPriceInfo(updatedPrices);
            int total_bill = PaperBillHelper.calculateTotalBill(billSt.getMonth(),billSt.getYear(),paperPriceRegister);

            /*
            PaperBillHelper.calculateTotalBill(billSt.getMonth(),billSt.getYear(),paperPriceRegister);
            System.out.println("Return from pricesActivity"+updatedPrices);
            HashMap dayPrices = (HashMap)updatedPrices.get("ECOTIMES");
            System.out.println("dayPrices for ECOTIMES"+dayPrices);
            double eco_times_bill = PaperBillHelper.calculateBillForAPaperWithPrice(billSt.getMonth(),billSt.getYear(),
                                            "ECOTIMES",dayPrices);
            //System.out.println("updated prices"+updatedPrices);
            dayPrices = (HashMap)updatedPrices.get("TIMES");
            double bill = PaperBillHelper.calculateBillForAPaperWithPrice(billSt.getMonth(),billSt.getYear(),"TIMES",dayPrices);
            double total_bill  = eco_times_bill + bill; */
            String totalBillS = total_bill+"";
            Log.d("Post price update-Total bill",totalBillS);
            viewBill.setText(totalBillS);

        }
        if(requestCode == 4)
        {
            TextView viewBill = (TextView)findViewById(R.id.totalBill);
            ArrayList<GregorianCalendar>  holidayMap = (ArrayList<GregorianCalendar>) data.getSerializableExtra("holidayMap");
            HashMap dayPrices = (HashMap)updatedPrices.get("ECOTIMES");
            System.out.println("dayPrices for ECOTIMES"+dayPrices);
            double eco_times_bill = PaperBillHelper.calculateBillForAPaperWithPriceAndHoliday(billSt.getMonth(),billSt.getYear(),
                    "ECOTIMES",dayPrices,holidayMap);
            //System.out.println("updated prices"+updatedPrices);
            dayPrices = (HashMap)updatedPrices.get("TIMES");
            double bill = PaperBillHelper.calculateBillForAPaperWithPriceAndHoliday(billSt.getMonth(),
                    billSt.getYear(),"TIMES",dayPrices,holidayMap);
            double total_bill  = eco_times_bill + bill;
            String totalBillS = total_bill+"";
            Log.d("Post holiday correction-Total bill",totalBillS);
            viewBill.setText(totalBillS);


        }
    }

    private void recalculateBill()
    {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
