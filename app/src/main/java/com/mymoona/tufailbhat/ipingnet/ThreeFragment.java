package com.mymoona.tufailbhat.ipingnet;

import android.content.Context;
import android.net.NetworkInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThreeFragment extends Fragment {
    public TextView edtUrlresult;
    Button btnCpu;
    Button btnPing;
    Button btnNetStat;
    Button btnTestSet;
    Button btnNeigh;
    Button btnMaddr;
    Button btnMAC;
    Button btnDF;
    EditText edturl;
    String host;
    private static final String IP_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."+
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."+
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."+
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    public ThreeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView= inflater.inflate(R.layout.fragment_three, container, false);
        edtUrlresult=(TextView) rootView.findViewById(R.id.txtUrlResult);
        btnCpu=  (Button) rootView.findViewById(R.id.bttnCPu);
        btnPing=  (Button) rootView.findViewById(R.id.bttnPing);
        btnNetStat=  (Button) rootView.findViewById(R.id.bttnNetstat);
        btnTestSet=  (Button) rootView.findViewById(R.id.bttnTesting);
        btnNeigh= (Button) rootView.findViewById(R.id.bttnNeigh);
        btnMaddr= (Button) rootView.findViewById(R.id.bttnMaddr);
        btnDF= (Button) rootView.findViewById(R.id.bttnDF);
        btnMAC= (Button) rootView.findViewById(R.id.bttnMac);
        edturl=(EditText) rootView.findViewById(R.id.edtUrl);
       btnNeigh.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               edtUrlresult.setText("Please "+"Wait"+"....");
               byte code=5;
               NetworkTask nettask=new NetworkTask(edtUrlresult,"ip route",code);
               nettask.execute(host);
           }
       });
        btnDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtUrlresult.setText("Please "+"Wait"+"....");
                byte code=4;
                NetworkTask nettask=new NetworkTask(edtUrlresult,"df",code);
                nettask.execute(host);
            }
        });
        btnMAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 edtUrlresult.setText("");
                    byte code=1;
                    NetworkTask nettask=new NetworkTask(edtUrlresult,edturl.getText().toString(),code);
                    nettask.execute(host);
            }
        });
        btnMaddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtUrlresult.setText("Please "+"Wait"+"....");
                byte code=4;
                NetworkTask nettask=new NetworkTask(edtUrlresult,"ip addr",code);
                nettask.execute(host);
            }
        });
        btnTestSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtUrlresult.setText("Please "+"Wait"+"....");
               try{ Intent intent =new Intent(Intent.ACTION_MAIN);
                intent.setClassName("com.android.settings","com.android.settings.TestingSettings");
                startActivity(intent);
                   edtUrlresult.setText("IPing"+"Net"+"");                      }
               catch (Exception e){
                   edtUrlresult.setText(R.string.txtError);
               }
            }
        });
        btnNetStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtUrlresult.setText("Please "+"Wait"+"....");
                byte code=4;
                NetworkTask nettask=new NetworkTask(edtUrlresult,"netstat",code);
                nettask.execute(host);
            }
        });
        btnCpu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtUrlresult.setText("Please "+"Wait"+"....");
                byte code=4;
                NetworkTask nettask=new NetworkTask(edtUrlresult,"cat /proc/cpuinfo",code);
                nettask.execute(host);
            }
        });
        btnPing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkInternetConnection())return;
                edtUrlresult.setText("Pinging "+"please"+" wait...");
                String st;
                st=edturl.getText().toString().trim();
                if(validateIp(st)){
                    byte code=3;
                    NetworkTask nettask=new NetworkTask(edtUrlresult,st,code);
                    nettask.execute(host);
                }else if(validateUrl(st))
                {
                    String stt= compString(st);
                    byte code=3;
                    NetworkTask nettask=new NetworkTask(edtUrlresult,stt,code);
                    nettask.execute(host);
                }
                else
                edtUrlresult.setText("Ping Failed!\n"+"It may be due to Wrong Url!!! \n It must be like 8.8.8.8 or www.example.com or example.com"+"");
            close(btnPing);
            }
        });
        return rootView;
    }
    public void close(View vv ){ MainActivity mac= (MainActivity) getActivity();
        mac.closeKeyboard(vv);}
    private  String compString(String str){
      String ulstr=str;
        if(ulstr.startsWith("http://")){
            ulstr=ulstr.replace("http://","");
            return ulstr;
        }else if(ulstr.startsWith("https://"))
        {  ulstr=ulstr.replace("https://","");
            return ulstr;
        }
        else
        return str;
    }
    public boolean validateIp(String ip){
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(IP_PATTERN);
        matcher=pattern.matcher(ip);
        return matcher.matches();
    }
    public boolean validateUrl(String ip){
        Pattern pattern;
        Matcher matcher;
        final String IP_PATTERN = "^(http://|https://)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{2,3}.?([a-z]+)?$";
        pattern = Pattern.compile(IP_PATTERN);
        matcher=pattern.matcher(ip);
        return matcher.matches();
    }

    public boolean checkInternetConnection()
    {
        ConnectivityManager connManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connManager.getActiveNetworkInfo();
        if(networkInfo ==null){
            edtUrlresult.setText(""+ "No Network is Active!"+"");
            return  false;
        }else if(!networkInfo.isConnected()){
            edtUrlresult.setText(""+ "No Network is Connected..!"+"");
            return  false;
        }else if(!networkInfo.isAvailable()){
            edtUrlresult.setText(""+ "No Network is Availble!"+"");
            return  false;
        }else if(networkInfo.isConnected()) {
            edtUrlresult.setText(""+ "Network is OK...."+"");
            host=networkInfo.getTypeName();
            host=host+networkInfo.getExtraInfo();
            return true;}
        else{
            Toast.makeText(getActivity(), "No Network", Toast.LENGTH_SHORT).show();
            return false;
            }
    }
    }