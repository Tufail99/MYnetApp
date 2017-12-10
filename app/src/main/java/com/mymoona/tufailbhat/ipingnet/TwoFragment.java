package com.mymoona.tufailbhat.ipingnet;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TwoFragment extends Fragment {
    TextView ipv6Res;
    public TwoFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_two,container,false);
        final EditText ipv6Edt;
        final EditText prefix;
         final Button btnIP6Res;
        ipv6Edt=(EditText) rootView.findViewById(R.id.edtIpAdd6);
        prefix=(EditText) rootView.findViewById(R.id.prefval);
        ipv6Res=(TextView) rootView.findViewById(R.id.txtresIPV6);
        btnIP6Res=(Button) rootView.findViewById(R.id.bttnCalIP6);
        btnIP6Res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ipbool=validateipv6(ipv6Edt.getText().toString().trim());
                if(!ipbool){
                    Toast.makeText(getActivity(),"Please enter valid IPV6 Address! \n Enter proper format",Toast.LENGTH_SHORT).show();
                }else if(validPrefix(prefix.getText().toString())){
                    Toast.makeText(getActivity(),"Please enter valid Prefix! \n Between 1-128",Toast.LENGTH_SHORT).show();
                }else
                {
                    StringBuilder sb=new StringBuilder(ipv6Edt.getText().toString().trim());
                    String val=getFull(sb);
                    ipv6Res.setText("IPV6 ADDDRESS:\n> "+val+"\n> "+ipv6Edt.getText().toString().trim()+"");
                    maskIPV(val,Integer.parseInt(prefix.getText().toString()));
                    if(ipv6Res.getCurrentTextColor()== Color.parseColor("#553344")){
                        ipv6Res.setTextColor(Color.parseColor("#113234"));}
                    else  if(ipv6Res.getCurrentTextColor()==Color.parseColor("#113234"))
                    {ipv6Res.setTextColor(Color.parseColor("#553344"));}
                }
               MainActivity mac= (MainActivity) getActivity();
                mac.closeKeyboard(btnIP6Res);
            }
        });
        return rootView;
    }
    public boolean validateipv6(String ip){
        Pattern pattern;
        Matcher matcher;
        final String IP_PATTERN = "(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|"+
                "([0-9a-fA-F]{1,4}:){1,7}:|"+
                "([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|"+
                "([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|"+
                "([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|"+
                "([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|"+
                "([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|"+
                "[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|"+
                ":((:[0-9a-fA-F]{1,4}){1,7}|:))";
        pattern = Pattern.compile(IP_PATTERN);
        matcher=pattern.matcher(ip);
        return matcher.matches();
    }
    public boolean validPrefix(String pf) {
        return ((pf.equals("")) || (Short.parseShort(pf) < 1 || Short.parseShort(pf) > 128));
    }
    public String getFull(StringBuilder ipv){
        int poscode,colcount=0,lcol=0,rtcol;
        String uncompressedAddress=ipv.toString();
        String strEx=ipv.toString();
        if(uncompressedAddress.startsWith("::")){
            poscode=1;
            uncompressedAddress= uncompressedAddress.replace("::","0000:");
            strEx=strEx.replace("::","Z");
        }else if(uncompressedAddress.endsWith("::")){
            poscode=2;
            uncompressedAddress= uncompressedAddress.replace("::",":0000");
            strEx=strEx.replace("::","Z");
        }else
        {
            poscode=0;
            uncompressedAddress= uncompressedAddress.replace("::",":0000:");
            strEx=strEx.replace("::",":Z:");
        }
        for(int a=0;a<strEx.length();a++){
            if(strEx.charAt(a)==':')
            {
                colcount++;
            }
            if(strEx.charAt(a)=='Z'){
                lcol=colcount;
            }
        }
           rtcol=colcount-lcol;
        String[] acomp=uncompressedAddress.split(":");
        String[] newcomp=new String[8];

        if(colcount==7){
          System.arraycopy(acomp,0,newcomp,0,8);
        }
        else{
         int sum=lcol+rtcol;
            int u=lcol;
        for(int o=0;o<8;o++) {
            if(poscode==2){
            if(o<acomp.length){newcomp[o]=acomp[o];}
            else
            newcomp[o] = "0000";}

            else if(poscode==1){
                if(o<8-acomp.length){newcomp[o] = "0000";}
                else{
                    newcomp[o]=acomp[lcol];
                lcol++;}
            }else{
                if(lcol>0){newcomp[o] = acomp[o];lcol--;}
                else if(sum<8){newcomp[o] = "0000";sum++;}
                else{u++;
                    newcomp[o]=acomp[u];}
            }
          }
        }
           int test;
        for(int i=0;i<newcomp.length;i++) {
            test = newcomp[i].length();
            for (int j = 0; j < 4 - test; j++) {
                newcomp[i] = "0" + newcomp[i];}
        }
        int len=acomp.length;
            if(poscode==2){
               for(int k=0;k<8-len;k++){
                   newcomp[len+k]="0000";
               }
               return colAdd(newcomp);
            }
            else if(poscode==1)
            {
                return colAdd(newcomp);
            }else
                return colAdd(newcomp);
    }
    private String colAdd(String[] stt){
          String get="";
           for (int i=0;i<8;i++) {
            if(i==0){get=stt[i];}
            else if(i==7){get=get+":"+stt[7];}
            else
            get=get+":"+stt[i];
                       }
            return get;
           }
     private void maskIPV(String ip,int pfx){
         String[] gtval=ip.split(":");
         int[] intip=new int[gtval.length];
         int[] intperfix=new int[gtval.length];
         ip="";

         for (int i=0;i<gtval.length;i++) {
            intip[i]= Integer.parseInt(gtval[i],16);
             gtval[i]=Integer.toHexString(intip[i]);
             ip=ip+gtval[i];
            }
         if(intip[0]==65408){ ipv6Res.setText(""+ipv6Res.getText().toString()+"\n>TYPE:  Link-local unicast ");}
         else if(intip[0]==65216){ ipv6Res.setText(""+ipv6Res.getText().toString()+"\n>TYPE:  Site-local unicast ");}
         else if(intip[0]==65280){ ipv6Res.setText(""+ipv6Res.getText().toString()+"\n>TYPE:  Multicast");}
         else if(intip[0]==0 && intip[1]==0 && intip[2]==0 && intip[3]==0 && intip[4]==0 && intip[5]==0 && intip[6]==0 && intip[7]==0 ){ ipv6Res.setText(""+ipv6Res.getText().toString()+"\n>TYPE:  Unspecified ");}
         else if(intip[0]==0 && intip[1]==0 && intip[2]==0 && intip[3]==0 && intip[4]==0 && intip[5]==0 && intip[6]==0 && intip[7]==1){ ipv6Res.setText(""+ipv6Res.getText().toString()+"\n>TYPE:  LocalHost ");}
         else{ ipv6Res.setText(""+ipv6Res.getText().toString()+"\n>TYPE:  Global Unicast ");}
            //prefix
        if(pfx<=16){
            intperfix[0]=comPrefix(pfx);
            for(int y=1;y<8;y++){
                intperfix[y]=0;
            }
        }
         else if(pfx>=17 && pfx <=32){
           intperfix[0]=65535;
            intperfix[1]=comPrefix(pfx-16);
            for(int y=2;y<8;y++){
                intperfix[y]=0;
            }
        }
         else if(pfx>=33 && pfx <=48){
            for(int b=0;b<2;b++)
                intperfix[b]=65535;
            intperfix[2]=comPrefix(pfx-32);
            for(int y=3;y<8;y++){
                intperfix[y]=0;
            }
        }
         else if(pfx>=49 && pfx <=64){
            for(int b=0;b<3;b++)
                intperfix[b]=65535;
            intperfix[3]=comPrefix(pfx-48);
            for(int y=4;y<8;y++){
                intperfix[y]=0;
            }
        }
        else if(pfx>=65 && pfx <=80){
            for(int b=0;b<4;b++)
                intperfix[b]=65535;
            intperfix[4]=comPrefix(pfx-64);
            for(int y=5;y<8;y++){
                intperfix[y]=0;
            }
        }
        else if(pfx>=81 && pfx <=96){
            for(int b=0;b<5;b++)
                intperfix[b]=65535;
            intperfix[5]=comPrefix(pfx-80);
            for(int y=6;y<8;y++){
                intperfix[y]=0;
            }
        }
        else if(pfx>=97 && pfx <=112){
            for(int b=0;b<6;b++)
                intperfix[b]=65535;
            intperfix[6]=comPrefix(pfx-96);
            for(int y=7;y<8;y++){
                intperfix[y]=0;
            }
        }
        else if(pfx>=113 && pfx <=128){
            for(int b=0;b<7;b++)
                intperfix[b]=65535;
            intperfix[7]=comPrefix(pfx-112);
        }

         int[] intWild=new int[8];
         String hexInt[]=new String[8];
       for(int l=0;l<8;l++){
           intWild[l]=((~intperfix[l]) & 65535);
           intip[l]=intip[l] & intperfix[l];
           hexInt[l]=Integer.toHexString(intip[l]);
           gtval[l]=Integer.toHexString(intWild[l] | intip[l]);
       }
         ipv6Res.setText(ipv6Res.getText().toString()+"\n\n>FIRST ADDRESS:"+colAdd(hexInt)+"\n\n>LAST ADDRESS:"+colAdd(gtval));

         BigInteger b1,b2;
         int exp=128;
         b1=new BigInteger("2");
         exp=exp-pfx;
         b2=b1.pow(exp);
         ipv6Res.setText(""+ipv6Res.getText().toString()+"\n\n>HOSTS:"+b2.toString()+"\n");
     }
     public int comPrefix(int pf){
    int data=0;
    int val=15;
         for(int i=0;i<pf;i++){
        data =(int)(data+(Math.pow(2,val)));
        val--;}
    return data;}
}

