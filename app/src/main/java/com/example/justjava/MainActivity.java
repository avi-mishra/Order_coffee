package com.example.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import java.text.NumberFormat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); /** R.layout.activity_main is an example of accessing resourse....linked*/
    }
    int quantity=0;

    public void submitOrder(View view)
    {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_checkbox);  /**accesses checkbox*/
        boolean hasWhippedCream= whippedCream.isChecked();                     /**finds if whipped cream is checked or not*/
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate= chocolate.isChecked();
        int pricePerCup=calculatePricePerCup(quantity,hasChocolate,hasWhippedCream);  /**calculates the pricePerCup */
        EditText name= (EditText) findViewById(R.id.editText_view);
        String getName= name.getText().toString();
        String priceMessage=createOrderSummary(pricePerCup,hasWhippedCream,hasChocolate,getName);
//        displayMessage(priceMessage);
        Intent intent= new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));    // for only to be handled by an email app
        intent.putExtra(Intent.EXTRA_SUBJECT,"JustJava order for "+getName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }
    /**
     * Calculates the pricePerCup of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private int calculatePricePerCup(int quantity,boolean hasChocolate,boolean hasWhippedCream) {
        int pricePerCup=5;
        if(hasChocolate){
            pricePerCup=pricePerCup+2;
        }
        if(hasWhippedCream) {
            pricePerCup=pricePerCup+1;
        }
        return pricePerCup;
    }

    /**
     * this function creates the summary of the order
     */
    private String createOrderSummary(int pricePerCup, boolean hasWhippedCream,boolean hasChocolate,String name) {
        String priceMessage="Name: "+name;
        priceMessage=priceMessage+"\nAdd Whipped Cream: "+hasWhippedCream;
        priceMessage=priceMessage+"\nAdd Chocolate: "+hasChocolate;
        priceMessage=priceMessage+ "\nQuantity: "+quantity;
        priceMessage=priceMessage+ "\nTotal: $"+(quantity*pricePerCup);
        priceMessage=priceMessage+ "\nThank You!";
        return priceMessage;
    }

    /**
     * This function increases the order value by 1
     */
    public void increment(View View)
    {
        quantity=quantity+1;
        if(quantity>99) {
            Toast toastMessage= Toast.makeText(getApplicationContext(),"You can't order more than 99 cups of coffee",Toast.LENGTH_SHORT);
            toastMessage.show();
            return;
        }
        displayQuantity(quantity);
    }
    public void decrement(View View)
    {
        quantity=quantity-1;
        if(quantity<1) {
            Toast toastMessage= Toast.makeText(getApplicationContext(),"You can't order less than 1 cup of coffee",Toast.LENGTH_SHORT);
            toastMessage.show();
            return;
        }
        displayQuantity(quantity);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);  /** R.id.quantity_text_view-ex. of accessing resourse*/
        quantityTextView.setText("" + number);
    }

    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}