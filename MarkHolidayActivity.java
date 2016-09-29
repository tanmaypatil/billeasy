package productivity.paperbilleasy;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;


public class MarkHolidayActivity extends ActionBarActivity {

    ArrayList<GregorianCalendar> holidayMap = new ArrayList<GregorianCalendar>();


    int year;
    int month;
    protected void onCreate(Bundle savedInstanceState) {
        month =2 ;
        year = 2015 ;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        Intent holiday_intent = getIntent();
        month = holiday_intent.getIntExtra("month",1);
        Log.d("month received","month "+month);
        Log.d("year received","year "+year);
        String month_desc = PaperBillHelper.getMonthandYear(month,year);
        TextView monthView = (TextView)findViewById(R.id.holidayMonth);
        monthView.setText(month_desc);

        GridView gr = (GridView) findViewById(R.id.holidayGrid);
        final CalendarViewAdapter calViewAdapter =  new CalendarViewAdapter(this,month,year);
        gr.setAdapter(calViewAdapter);
        gr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GregorianCalendar holiday;

                System.out.println("clicked "+position);
                HolidayDateObject holidayDate = (HolidayDateObject)calViewAdapter.getItem(position);
                String actualDate = holidayDate.getDate();
                holiday = new GregorianCalendar(year,month-1,Integer.parseInt(actualDate));
                System.out.println("Actual date clicked"+actualDate);
                if (!holidayDate.isDateAlreadySelected()) {
                    System.out.println("setting color to cell");
                    int color = 0xff0000ff;
                    view.setBackgroundColor(color);
                    holidayDate.setColor(color);
                    holidayMap.add(holiday);
                }
                else
                {
                    System.out.println("Resetting color to cell");
                    int color = 0x00000000;
                    view.setBackgroundColor(color);
                    holidayDate.reSetColor(color);
                    holidayMap.remove(holiday);
                }
            }
        });

        Button prev_button = (Button) findViewById(R.id.back_button);
        prev_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Before returning to Main :in HolidayActivity "+holidayMap.size());
                Intent returnIntent = new Intent();
                returnIntent.putExtra("holidayMap",holidayMap);
                setResult(4,returnIntent);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mark_holiday, menu);
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
