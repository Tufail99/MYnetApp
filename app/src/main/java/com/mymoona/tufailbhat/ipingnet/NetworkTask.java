package com.mymoona.tufailbhat.ipingnet;

import android.os.AsyncTask;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.util.Enumeration;

class NetworkTask extends AsyncTask<String,Void,String> {
    private TextView textView;
    private String url;
    private byte code;

    NetworkTask(TextView textView,String url,byte code){
        this.textView=textView;
        this.url=url;
        this.code=code;
    }
    @Override
    protected String doInBackground(String... params) {
        if(code==1) {
         return getMac();
     }else if(code ==3){
            try {
              return pingCmd(url);
            } catch (Exception e) {
                return e.getMessage();
            }
        }else if(code ==4) {
            try {
                return pinging(url);
            } catch (Exception e) {
                return e.getMessage();
            }
        }
        else if(code ==5) {
            try {String sb;
                sb="NEAR ROUTER:\n";
                sb=sb+pinging(url);
                sb=sb+"\nROUTING TABLE:\n"+pinging("cat /proc/net/route");
                sb=sb+"\nPRIMARY DNS:\n"+pinging("getprop net.dns1");
                sb=sb+"\nSECONDARY DNS:\n"+pinging("getprop net.dns2");
                return sb;
            } catch (Exception e) {
                return e.getMessage();
            }
        }
        else
         return "Ping failed!";
    }
    private  String pingCmd(String ip){
        return pinging("/system/bin/ping -c 8 "+ip);
    }
    private String pinging(String url)
    {
        String str;
        try{
            Process process=Runtime.getRuntime().exec(
                    url);
            BufferedReader reader=new BufferedReader(new InputStreamReader(process.getInputStream()));
            int i;
            char[] buffer=new char[4096];
            StringBuilder output=new StringBuilder();
            while((i =reader.read(buffer))>0)
                output.append(buffer,0,i);
            reader.close();
            str=output.toString();
        }catch (IOException e){
            return e.getMessage();
        }
        return str;
    }
    private String getMac(){
        StringBuilder sb= new StringBuilder();
        try{
            Enumeration<NetworkInterface> networks= NetworkInterface.getNetworkInterfaces();
            while(networks.hasMoreElements())
            {
             NetworkInterface network =networks.nextElement();
                byte[] mac=network.getHardwareAddress();
                if(mac != null){
                    String apn="\n "+network+" MAC:- ";
                    sb.append(apn);
                    for(int i=0;i<mac.length;i++){
                        sb.append(String.format("%02X%s",mac[i],(i<mac.length-1)?"-":""));
                    }
                }
            }
            sb.insert(0,"Interface_Name:          Address: \n");
            return sb.toString();
          }

        catch (Exception e){
            return e.getMessage();
        }
    }
    @Override
    protected void onPostExecute(String result) {
        if(!result.equals("")){
          switch (url) {
              case "netstat":
                  this.textView.setText("NETWORK STASTISTICS:" + "\n" + result);
                  break;
              case "ip addr":
                  this.textView.setText("IP ADRESSSES & STATUS:" + "\n" + result);
                  break;
              default:
                  this.textView.setText(result);
                  break;
          }
        }
        else
        {this.textView.setText("Oops!"+" Command failed!!"+" \n May due to wrong URL .\n Or it may be Timed out..");}
    }
}
