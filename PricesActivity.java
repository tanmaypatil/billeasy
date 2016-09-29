package productivity.paperbilleasy;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;


public class PricesActivity extends ActionBarActivity {

    Iterator pi;
    ArrayList priceList;
    /* This node has the prices for information
    for currently displayed paper.
     */
    PaperPriceListNode displayNode;
    ListIterator li;

    public enum operation { NEXT , PREV};
    operation lastOperation;

    private void convertHashMapToList(HashMap paper_prices)
    {
        Iterator it  = paper_prices.entrySet().iterator();
         priceList = new ArrayList();
        while ( it.hasNext())
        {
            Map.Entry priceObj = (Map.Entry) it.next();
            String paperName = (String)priceObj.getKey();
            HashMap priceMap = (HashMap)priceObj.getValue();
            PaperPriceListNode node = new PaperPriceListNode(paperName,priceMap,0);
            priceList.add(node);
        }
        li = priceList.listIterator();
    }
    private void populatePrices(HashMap paper_prices)
    {
        convertHashMapToList(paper_prices);

        if(li.hasNext())
        {
            displayNode = (PaperPriceListNode)li.next();
            String paperName =  displayNode.getPaperName();
            HashMap priceMap = displayNode.getPriceMap();
            pricesPopulationForDaysOfWeek(priceMap,paperName);
        }
    }

    private int getDayCount(String dayName)
    {
        int count  =1 ;
        if(dayName.equals("Mon"))
        {
            count = 1 ;
        }else if(dayName.equals("Tue"))
        {
            count =2 ;
        }
        else if(dayName.equals("Wed"))
        {
            count = 3 ;
        }else if(dayName.equals("Thu"))
        {
            count =4 ;
        }
        else if(dayName.equals("Fri"))
        {
            count = 5 ;
        }
        else if(dayName.equals("Sat"))
        {
            count = 6 ;
        }else if(dayName.equals("Sun"))
        {
            count = 7 ;
        }
        return count;

    }
    private void pricesPopulationForDaysOfWeek(HashMap paperPrice,String paperName)
    {
        int count = 1;
        TextView dayNameText;
        EditText dayPrice;
        TextView paperNameText = (TextView)findViewById(R.id.paperName);
        paperNameText.setText(paperName);
        Iterator it = paperPrice.entrySet().iterator();
        while   (it.hasNext())
        {
            Map.Entry dayObj = (Map.Entry)it.next();
            String dayName = (String)dayObj.getKey();
            int price = ((Integer)dayObj.getValue()).intValue();
            count = getDayCount(dayName);
            switch(count)
            {
                case 1:
                    dayNameText = (TextView) findViewById(R.id.dayName1);
                    dayPrice = (EditText)findViewById(R.id.dayPrice1);
                    break;
                case 2:
                    dayNameText = (TextView) findViewById(R.id.dayName2);
                    dayPrice = (EditText)findViewById(R.id.dayPrice2);
                    break;
                case 3:
                    dayNameText = (TextView) findViewById(R.id.dayName3);
                    dayPrice = (EditText)findViewById(R.id.dayPrice3);
                    break;
                case 4:
                    dayNameText = (TextView) findViewById(R.id.dayName4);
                    dayPrice = (EditText)findViewById(R.id.dayPrice4);
                    break;
                case 5:
                    dayNameText = (TextView) findViewById(R.id.dayName5);
                    dayPrice = (EditText)findViewById(R.id.dayPrice5);
                    break;
                case 6:
                    dayNameText = (TextView) findViewById(R.id.dayName6);
                    dayPrice = (EditText)findViewById(R.id.dayPrice6);
                    break;
                case 7:
                    dayNameText = (TextView) findViewById(R.id.dayName7);
                    dayPrice = (EditText)findViewById(R.id.dayPrice7);
                    break;
                default:
                    dayNameText = (TextView) findViewById(R.id.dayName1);
                    dayPrice = (EditText)findViewById(R.id.dayPrice1);
                    break;
            }
            count++;
            dayNameText.setText(dayName);
            dayPrice.setText(price+"");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prices);
        Intent prices_intent = getIntent();
        Log.d("received intent","intent");
        HashMap paper_prices =(HashMap)  prices_intent.getSerializableExtra("prices");
        populatePrices(paper_prices);
        ImageButton next_button = (ImageButton)findViewById(R.id.next);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (li.hasNext())
                {
                    Log.d("PricesActivity","next clicked");
                    if(lastOperation == operation.PREV)
                        li.next();
                    //HashMap priceOfaPaper = (HashMap)pi.next();
                    displayNode = (PaperPriceListNode)li.next();
                    String paperName = displayNode.getPaperName();
                    HashMap priceMap = displayNode.getPriceMap();
                    pricesPopulationForDaysOfWeek(priceMap,paperName);
                    lastOperation = operation.NEXT;

                }
            }
        });
        ImageButton prev_button = (ImageButton)findViewById(R.id.prev);
        prev_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (li.hasPrevious())
                {
                    if(lastOperation == operation.NEXT)
                        li.previous();
                    Log.d("PricesActivity","previous clicked");
                    displayNode = (PaperPriceListNode)li.previous();
                    String paperName = displayNode.getPaperName();
                    HashMap priceMap = displayNode.getPriceMap();
                    pricesPopulationForDaysOfWeek(  priceMap,paperName);
                    lastOperation = operation.PREV;

                }
            }

        });
        /** Save the price change */
        ImageButton save_button = (ImageButton)findViewById(R.id.save);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePricesFromUserInput(displayNode);

            }
        });
        /* go back to parent and show the bill with latest prices*/
        ImageButton back_button = (ImageButton)findViewById(R.id.back);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap updatedPriceList = convertPriceListToHashMap(priceList);
                System.out.println("Before returning to Main :in pricesActivity "+updatedPriceList);
                Intent returnIntent = new Intent();
                returnIntent.putExtra("updatedPrices",updatedPriceList);
                setResult(2,returnIntent);
                finish();
            }
        });

    }

    public HashMap convertPriceListToHashMap(ArrayList priceList)
    {
        HashMap returnMap = new HashMap();
        ListIterator lit = priceList.listIterator();
        while(lit.hasNext())
        {
            PaperPriceListNode node = (PaperPriceListNode)lit.next();

            HashMap pricesMap = node.getPriceMap();
            String paperName = node.getPaperName();
            System.out.println("convertPriceListToHashMap :paper name > "+paperName);
            System.out.println("convertPriceListToHashMap :hashMap > "+pricesMap);


            returnMap.put(paperName,pricesMap);
        }
        return returnMap;
    }

    /* This method will update the prices for a given Paper */
    public void updatePricesFromUserInput(PaperPriceListNode displayNode)
    {
        EditText dayPrice;
        int paperPrice;

        String paperName = displayNode.getPaperName();
        System.out.println("Updating prices for "+paperName);
        HashMap pricesMap = displayNode.getPriceMap();
        /* update price of monday */
        dayPrice = (EditText)findViewById(R.id.dayPrice1);
        paperPrice = Integer.parseInt((String ) dayPrice.getText().toString());
        pricesMap.put("Mon",paperPrice);

        /* update price of tuesday */
        dayPrice = (EditText)findViewById(R.id.dayPrice2);
        paperPrice = Integer.parseInt((String ) dayPrice.getText().toString());
        pricesMap.put("Tue",paperPrice);

        /* update price of wednesday */
        dayPrice = (EditText)findViewById(R.id.dayPrice3);
        paperPrice = Integer.parseInt((String ) dayPrice.getText().toString());
        pricesMap.put("Wed",paperPrice);

    /* update price of thursday */
        dayPrice = (EditText)findViewById(R.id.dayPrice4);
        paperPrice = Integer.parseInt((String ) dayPrice.getText().toString());
        pricesMap.put("Thu",paperPrice);

         /* update price of Friday */
        dayPrice = (EditText)findViewById(R.id.dayPrice5);
        paperPrice = Integer.parseInt((String ) dayPrice.getText().toString());
        pricesMap.put("Fri",paperPrice);

        /* update price of Saturday */
        dayPrice = (EditText)findViewById(R.id.dayPrice6);
        paperPrice = Integer.parseInt((String ) dayPrice.getText().toString());
        pricesMap.put("Sat",paperPrice);

     /* update price of Sunday */
        dayPrice = (EditText)findViewById(R.id.dayPrice7);
        paperPrice = Integer.parseInt((String ) dayPrice.getText().toString());
        pricesMap.put("Sun",paperPrice);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prices, menu);
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
