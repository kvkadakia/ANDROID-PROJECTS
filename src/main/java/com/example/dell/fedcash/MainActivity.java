package com.example.dell.fedcash;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import com.example.dell.common.ITreasuryServ;



public class MainActivity extends AppCompatActivity {

    private ITreasuryServ mService;
    boolean mIsBound = false;
    private EditText date, month, year, workingdays;
    private Button monthlycash, dailycash, yearlyavg, us, bs, oa;
    private ArrayList<String> apiname= new ArrayList<String>();
    private ArrayList<String> apivalues= new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        year = (EditText) findViewById(R.id.year);date = (EditText) findViewById(R.id.days);month = (EditText) findViewById(R.id.month);workingdays = (EditText) findViewById(R.id.workingdays);monthlycash = (Button) findViewById(R.id.monthlycash);dailycash = (Button) findViewById(R.id.dailycash);yearlyavg = (Button) findViewById(R.id.yearlyaverage);us = (Button) findViewById(R.id.us);bs = (Button) findViewById(R.id.bs);oa = (Button) findViewById(R.id.oa);

        monthlycash.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Thread t = new Thread(new WorkerThread(0));
                t.start();
            }
        });

        dailycash.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Thread t = new Thread(new WorkerThread(1));
                t.start();
            }
        });
        yearlyavg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Thread t = new Thread(new WorkerThread(2));
                t.start();
            }
        });

        us.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mIsBound == true) {
                    unbindService(mConnection);
                    mIsBound = false;
                }
            }
        });

        bs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    Intent i = new Intent(ITreasuryServ.class.getName());
                    ResolveInfo info = getPackageManager().resolveService(i, Context.BIND_AUTO_CREATE);
                    i.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));
                    bindService(i, mConnection, Context.BIND_AUTO_CREATE);

            }
        });

        oa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ApiCalls.class);
                intent.putExtra("Data1", apiname);
                intent.putExtra("Data2", apivalues);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!mIsBound) {
            boolean b = false;
            Intent i = new Intent(ITreasuryServ.class.getName());
            ResolveInfo info = getPackageManager().resolveService(i, Context.BIND_AUTO_CREATE);
            i.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));
            b = bindService(i, this.mConnection, Context.BIND_AUTO_CREATE);
        }
    }

    //Service Connection
    protected final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder iservice) {
            mService = ITreasuryServ.Stub.asInterface(iservice);
            mIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
        }
    };

    public class WorkerThread implements Runnable {
        int val;
        WorkerThread(int n)
        {
            val = n;
        }

        public void run() {
            Looper.prepare();//Looper

            if(val == 0)
            mCash();
            else if(val == 1)
            dCash();
            else if(val==2)
            yAvg();

            Looper.loop();
        }

        //Call the API monthlyCash in the 2nd app
        public void mCash()
        {
            if (year.getText().toString()!=null & !year.getText().toString().isEmpty())
            {
            Intent i = new Intent(ITreasuryServ.class.getName());
            ResolveInfo info = getPackageManager().resolveService(i, Context.BIND_AUTO_CREATE);
            i.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));
            bindService(i, mConnection, Context.BIND_AUTO_CREATE);
                try {
                    List<String> s =new ArrayList<String>();
                    s = mService.monthlyCash(year.getText().toString());
                    String temp="";
                    for(int j =0;j< s.size();j++)
                    temp = temp + "\n" +s.get(j);

                    apivalues.add("Monthly Cash:"+"\n"+temp);
                    apiname.add("Monthly Cash");
                } catch (RemoteException e) {
                    Log.e("ERROR", e.toString());
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Please enter a value for YEAR", Toast.LENGTH_SHORT).show();
            }
        }

        //Call the API dailyCash in the 2nd app
        public void dCash() {
            if (!year.getText().toString().isEmpty() && year.getText().toString()!=null && !month.getText().toString().isEmpty() && month.getText().toString()!=null&& !date.getText().toString().isEmpty()&& date.getText().toString()!=null &&workingdays.getText().toString()!=null&& !workingdays.getText().toString().isEmpty() ) {
                Intent i = new Intent(ITreasuryServ.class.getName());
                ResolveInfo info = getPackageManager().resolveService(i, Context.BIND_AUTO_CREATE);
                i.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));
                bindService(i, mConnection, Context.BIND_AUTO_CREATE);
                try {
                    List<String> s1 =new ArrayList<String>();
                    s1 = mService.dailyCash(date.getText().toString(), month.getText().toString(), year.getText().toString(), workingdays.getText().toString());
                    String temp="";

                    for(int j =0;j< s1.size();j++)
                        temp = temp +"\n" +s1.get(j) ;

                    apivalues.add("Daily Cash:"+"\n"+temp);
                    apiname.add("Daily Cash");
                } catch (RemoteException e) {
                    Log.e("ERROR", e.toString());
                }
            }
             else
            {
            Toast.makeText(getApplicationContext(), "Please enter a value for YEAR, DATE, MONTH & WORKING DAYS", Toast.LENGTH_SHORT).show();
            }
        }

        //Call the API yearlyAverage in the 2nd app
        public void yAvg()
        {
            if (year.getText().toString()!=null & !year.getText().toString().isEmpty()) {
                Intent i = new Intent(ITreasuryServ.class.getName());
                ResolveInfo info = getPackageManager().resolveService(i, Context.BIND_AUTO_CREATE);
                i.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));
                bindService(i, mConnection, Context.BIND_AUTO_CREATE);
                try {
                    String s = mService.yearlyAvg(year.getText().toString());
                    apivalues.add(s);
                    apiname.add("Yearly Average");
                } catch (RemoteException e) {
                    Log.e("ERROR", e.toString());
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Please enter a value for YEAR", Toast.LENGTH_SHORT).show();
            }
        }



    }
}


