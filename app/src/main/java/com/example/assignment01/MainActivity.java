package com.example.assignment01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    EditText etGoldValue, etGoldWeight;
    Button btnGoldUsageKeep, btnGoldUsageWear, btnCalculate, btnClear;
    TextView tvGoldValue, tvGoldZakatPay, tvZakatTotal;
    private boolean isGoldUsageKeep, isGoldUsageWear = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Set application layout
        setContentView(R.layout.activity_main);

        //Set up toolbar
        setSupportActionBar(findViewById(R.id.toolbar));

        // Link elements with UI
        etGoldValue = findViewById(R.id.etGoldValue);
        btnGoldUsageKeep = findViewById(R.id.btnGoldUsageKeep);
        btnGoldUsageWear = findViewById(R.id.btnGoldUsageWear);
        etGoldWeight = findViewById(R.id.etGoldWeight);
        btnCalculate = findViewById(R.id.btnCalculate);
        tvGoldValue = findViewById(R.id.tvGoldValue);
        tvGoldZakatPay = findViewById(R.id.tvGoldZakatPay);
        tvZakatTotal = findViewById(R.id.tvZakatTotal);
        btnClear = findViewById(R.id.btnClear);

        btnGoldUsageKeep.setOnClickListener(v ->
        {
            isGoldUsageKeep = true;
            isGoldUsageWear = false;
            btnGoldUsageKeep.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.darkYellow));
            // Reset the other button
            btnGoldUsageWear.setBackgroundTintList(null);
        });

        btnGoldUsageWear.setOnClickListener(v ->
        {
            isGoldUsageWear = true;
            isGoldUsageKeep = false;
            btnGoldUsageWear.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.darkYellow));
            // Reset the other button
            btnGoldUsageKeep.setBackgroundTintList(null);
        });

        // Set up buttons
        btnCalculate.setOnClickListener (this);
        btnClear.setOnClickListener (this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.btnCalculate)
        {
            try
            {
                // Verify inputs are not null
                String strGoldWeight = etGoldWeight.getText().toString().trim();
                String strGoldValue = etGoldValue.getText().toString().trim();

                // Null 1st, 2nd and 3rd inputs
                if (strGoldWeight.isEmpty() && (!isGoldUsageKeep && !isGoldUsageWear) && strGoldValue.isEmpty())
                {
                    Toast.makeText(this, "Please provide all the necessary inputs.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Null 1st and 2nd inputs
                else if (strGoldWeight.isEmpty() && (!isGoldUsageKeep && !isGoldUsageWear))
                {
                    Toast.makeText(this, "Please enter weight of your gold and choose usage of gold.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Null 2nd and 3rd inputs
                else if ((!isGoldUsageKeep && !isGoldUsageWear) && strGoldValue.isEmpty())
                {
                    Toast.makeText(this, "Please choose usage of gold and enter its current value.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Null 1st and 3rd inputs
                else if (strGoldWeight.isEmpty() && strGoldValue.isEmpty())
                {
                    Toast.makeText(this, "Please enter weight of your gold and its current value.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Null 1st input
                else if (strGoldWeight.isEmpty())
                {
                    Toast.makeText(this, "Please enter the weight of your gold.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Null 2nd input
                else if (!isGoldUsageKeep && !isGoldUsageWear)
                {
                    Toast.makeText(this, "Please choose whether usage of the gold is keep or wear.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Null 3rd input
                else if (strGoldValue.isEmpty())
                {
                    Toast.makeText(this, "Please enter current value of the gold.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Retrieve inputs from app
                double iGoldWeight = Double.parseDouble(etGoldWeight.getText().toString());
                double iGoldValue = Double.parseDouble(etGoldValue.getText().toString());

                // Calculation for total gold value (First output)
                double oGoldValue = iGoldWeight * iGoldValue;

                if (oGoldValue >= 0)
                {
                    @SuppressLint("DefaultLocale") String formatGoldValue = String.format("%.2f", oGoldValue);
                    tvGoldValue.setText("Total gold value : RM" + formatGoldValue);
                    Toast.makeText(this, "Successful calculation!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    tvGoldValue.setText("Total gold value : RM0");
                    Toast.makeText(this, "Gold value cannot be lower than RM0.", Toast.LENGTH_SHORT).show();
                }

                // Calculation for payable zakat gold payment (Second output)
                double oGoldZakatPay = 0.0;

                if (isGoldUsageKeep)
                {
                    oGoldZakatPay = iGoldWeight - 85;
                    if (oGoldZakatPay >= 0.0)
                    {
                        oGoldZakatPay *= iGoldValue;
                        @SuppressLint("DefaultLocale") String formatGoldZakatPay = String.format("%.2f", oGoldZakatPay);
                        tvGoldZakatPay.setText("Amount of payable zakat zakat : RM" + formatGoldZakatPay);
                        Toast.makeText(this, "Successful calculation!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        tvGoldZakatPay.setText("The amount of zakat gold weight left is under 0g");
                    }
                }
                else if (isGoldUsageWear)
                {
                    oGoldZakatPay = iGoldWeight - 200;
                    if (oGoldZakatPay >= 0.0)
                    {
                        oGoldZakatPay *= iGoldValue;
                        @SuppressLint("DefaultLocale") String formatGoldZakatPay = String.format("%.2f", oGoldZakatPay);
                        tvGoldZakatPay.setText("Amount of payable zakat : RM" + formatGoldZakatPay);
                        Toast.makeText(this, "Successful calculation!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        tvGoldZakatPay.setText("RM0.00 because leftover gold weight is under 0g");
                    }
                }

                // Calculation for total zakat payment (third Output)
                double zakatPerc = 0.025;
                double oZakatTotal;
                oZakatTotal = oGoldZakatPay * zakatPerc;

                if (oZakatTotal >= 0)
                {
                    @SuppressLint("DefaultLocale") String formatZakatTotal = String.format("%.2f", oZakatTotal);
                    tvZakatTotal.setText("Total zakat payment : RM" + formatZakatTotal);
                    Toast.makeText(this, "Successful calculation!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    tvZakatTotal.setText("No need to pay because zakat payment is RM0");
                }
            }

            catch (NumberFormatException e)
            {
                // Failed calculation
                Toast.makeText(this, "Unsuccessful calculation! Please try again.", Toast.LENGTH_SHORT).show();
                tvGoldValue.setText("Total gold value : Unknown");
                tvGoldZakatPay.setText("Amount of zakat payable : Unknown");
                tvZakatTotal.setText("Total zakat payment : Unknown");
            }
        }

        else if (v.getId() == R.id.btnClear)
        {
            if (!etGoldWeight.getText().toString().isEmpty() ||
                (isGoldUsageKeep || isGoldUsageWear) ||
                !etGoldValue.getText().toString().isEmpty()
                )
            {
                Toast.makeText(this, "Inputs have been cleared.", Toast.LENGTH_SHORT).show();
                etGoldWeight.setText("");
                btnGoldUsageKeep.setBackgroundColor(ContextCompat.getColor(this, R.color.brightYellow));
                btnGoldUsageWear.setBackgroundColor(ContextCompat.getColor(this, R.color.brightYellow));
                isGoldUsageKeep = false;
                isGoldUsageWear = false;
                etGoldValue.setText("");
                tvGoldValue.setText("");
                tvGoldZakatPay.setText("");
                tvZakatTotal.setText("");
            }
            else
            {
                Toast.makeText(this, "No inputs to clear.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_drawer, menu);

        // Set up ShareActionProvider
        MenuItem shareItem = menu.findItem(R.id.menu_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        if (shareActionProvider != null)
        {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out the Zakat Gold Calculator app!");
            shareActionProvider.setShareIntent(shareIntent);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.menu_share)
        {
            shareApp();
            return true;
        }

        else if (item.getItemId() == R.id.menu_item_about)
        {
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
            return true;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
    }

    private void shareApp()
    {
        String appWebsiteLink = "https://github.com/Haziri31/ICT602-Assignment01";

        String shareMessage = "Check out the Zakat Gold Calculator app!\n\n" +
                "Open in gitHub:\n" + appWebsiteLink;

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }
}