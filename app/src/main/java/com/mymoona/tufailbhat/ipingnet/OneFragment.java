package com.mymoona.tufailbhat.ipingnet;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class OneFragment extends Fragment{

    private RadioButton rbClassLess;
    private RadioButton rbClassFull;
    private RadioGroup rdIpClass;
    private EditText edtPrefix;
    private EditText edtPrefix2;
    private EditText edtIpAddres1;
    private EditText edtIpAddres2;
    private EditText edtIpAddres3;
    private EditText edtIpAddres4;
    private EditText edtSubnetmask1;
    private EditText edtSubnetmask2;
    private EditText edtSubnetmask3;
    private EditText edtSubnetmask4;
    private Short ipClassFlag;
    private Short ipPreSubnetFlag;
    private Short ipclassfullessFlag;
    private boolean errorflag;
    private TextView txtSubnetmaskResult;
    private TextView txtPrefixResult;
    private TextView txtIpaddResult;
    private TextView txtIpClassResult;
    private TextView txtNetIdResult;
    private TextView txtIstHostResult;
    private TextView txtLastHostIdResult;
    private TextView txtBroarcastResult;
    private TextView txtWildcardResult;
    private TextView txtNoOfHostsResult;
    private TextView txtNoOfSubnetResult;
    private TextView txtMoreInfo;
    private Byte prefixResult;
    private Short subnetid[]=new Short[4];

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_one,container,false);
        RadioGroup rdPreMask;
        RadioGroup rdSubnetType;
        errorflag=false;
        ipClassFlag=1;//1=A;2=B;3=C
        ipPreSubnetFlag=1;//subnet
        ipclassfullessFlag=1;//classfull
        prefixResult=0;//no prefix
        final Button calBttn= (Button) rootView.findViewById(R.id.bttnCalculate);
        edtPrefix=(EditText) rootView.findViewById(R.id.edtPrefix);
        edtPrefix2=(EditText) rootView.findViewById(R.id.edtPrefix2);
        edtSubnetmask1=(EditText) rootView.findViewById(R.id.edtSubnet1);
        edtSubnetmask2=(EditText) rootView.findViewById(R.id.edtSubnet2);
        edtSubnetmask3=(EditText) rootView.findViewById(R.id.edtSubnet3);
        edtSubnetmask4 =(EditText) rootView.findViewById(R.id.edtSubnet4);

        edtIpAddres1=(EditText) rootView.findViewById(R.id.edtIpAddress1);
        edtIpAddres2=(EditText) rootView.findViewById(R.id.edtIpAddress2);
        edtIpAddres3=(EditText) rootView.findViewById(R.id.edtIpAddress3);
        edtIpAddres4=(EditText) rootView.findViewById(R.id.edtIpAddress4);

        rdPreMask=(RadioGroup) rootView.findViewById(R.id.rdPreMask);
        rdSubnetType=(RadioGroup) rootView.findViewById(R.id.rdSubType);
        rdIpClass=(RadioGroup) rootView.findViewById(R.id.rdClasses);

        rbClassLess=(RadioButton) rootView.findViewById(R.id.typeClassless);
        rbClassFull=(RadioButton) rootView.findViewById(R.id.typeClassfull);

        txtSubnetmaskResult=(TextView) rootView.findViewById(R.id.txtSubnetmaskRes);
        txtPrefixResult=(TextView) rootView.findViewById(R.id.txtPrefixRes);
        txtIpaddResult=(TextView) rootView.findViewById(R.id.txtResIP);
        txtIpClassResult=(TextView) rootView.findViewById(R.id.txtClassRes);
        txtNetIdResult=(TextView) rootView.findViewById(R.id.txtNetidRes);
        txtIstHostResult=(TextView) rootView.findViewById(R.id.txtIstHostRes);
        txtLastHostIdResult=(TextView) rootView.findViewById(R.id.txtLastHostidRes);
        txtBroarcastResult=(TextView) rootView.findViewById(R.id.txtBroadcastidRes);
        txtWildcardResult=(TextView) rootView.findViewById(R.id.txtWildcardidRes);
        txtNoOfHostsResult=(TextView) rootView.findViewById(R.id.noofHostsRes);
        txtNoOfSubnetResult=(TextView) rootView.findViewById(R.id.txtNoSubnetsRes);
        txtMoreInfo=(TextView) rootView.findViewById(R.id.txtInfoRes);
        try {
            rdPreMask.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    if (checkedId == R.id.rdPrefix) {
                        ipPreSubnetFlag = 2;
                        edtSubnetmask1.setText("");
                        edtSubnetmask2.setText("");
                        edtSubnetmask3.setText("");
                        edtSubnetmask4.setText("");
                        edtSubnetmask1.setVisibility(View.GONE);
                        edtSubnetmask2.setVisibility(View.GONE);
                        edtSubnetmask3.setVisibility(View.GONE);
                        edtSubnetmask4.setVisibility(View.GONE);
                        edtPrefix.setVisibility(View.VISIBLE);
                        edtPrefix2.setVisibility(View.INVISIBLE);

                    } else if (checkedId == R.id.rdSubnet) {
                        ipPreSubnetFlag = 1;
                        edtPrefix.setText("");
                        edtPrefix.setVisibility(View.GONE);
                        edtPrefix2.setVisibility(View.GONE);
                        edtSubnetmask1.setVisibility(View.VISIBLE);
                        edtSubnetmask2.setVisibility(View.VISIBLE);
                        edtSubnetmask3.setVisibility(View.VISIBLE);
                        edtSubnetmask4.setVisibility(View.VISIBLE);

                        if (ipclassfullessFlag == 2) {
                            setTxt("", "", "", true, true, true);
                        } else if (ipClassFlag == 1) {
                            setTxt("255", "", "", false, true, true);
                        } else if (ipClassFlag == 2) {
                            setTxt("255", "255", "", false, false, true);
                        } else if (ipClassFlag == 3) {

                            setTxt("255", "255", "255", false, false, false);
                        }
                    }
                }
            });
            //radio 2nd type
            rdSubnetType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    if (checkedId == R.id.typeClassfull) {
                        ipclassfullessFlag = 1;
                        rbClassLess.setChecked(false);
                        rdIpClass.setVisibility(View.VISIBLE);
                        if (ipClassFlag == 1 && ipPreSubnetFlag == 1) {
                            edtIpAddres1.setHint("1-127");
                            setTxt("255", "", "", false, true, true);
                        } else if (ipClassFlag == 2 && ipPreSubnetFlag == 1) {
                            edtIpAddres1.setHint("128-191");
                            setTxt("255", "255", "", false, false, true);
                        } else if (ipClassFlag == 3 && ipPreSubnetFlag == 1) {
                            edtIpAddres1.setHint("192-223");
                            setTxt("255", "255", "255", false, false, false);
                        }
                    } else if (checkedId == R.id.typeClassless) {
                        ipclassfullessFlag = 2;
                        rbClassFull.setChecked(false);
                        rdIpClass.setVisibility(View.GONE);
                        edtIpAddres1.setHint("1-255");
                        edtIpAddres2.setHint("0-255");
                        edtIpAddres3.setHint("0-255");
                        edtIpAddres4.setHint("0-255");
                        setTxt("", "", "", true, true, true);
                        edtPrefix.setText("");
                    }
                }
            });
            //event of editview ist
            edtIpAddres1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        if (edtIpAddres1.getText().toString().equals("0") || edtIpAddres1.getText().toString().equals("00") || edtIpAddres1.getText().toString().equals("000")) {
                            alertMsg("Invalid IP Value!", "0.x.x.x is non-routable meta-address used \n to designate an invalid, unknown " +
                                    "\n or non-applicable target.                                   " +
                                    "In the context of routing ,0.0.0.0 \n usually means the default route, i.e, the route which " +
                                    "leads to the rest of the internet \n instead of somewhere on the local network." +
                                    "It is also used to specify the target unavailble");

                            edtIpAddres1.setText("");
                        } else if (edtIpAddres1.getText().toString().equals("127")) {
                            // Toast.makeText(getActivity().getApplicationContext(), "gello", Toast.LENGTH_LONG).show();
                            alertMsg("Invalid IP Value!", "127.0.0.0 -127.255.255.255 address range is reserved for loopback addresses;Can't be used!");
                            edtIpAddres1.setText("");
                        }
                    }

                }
            });
            //radio ip class
            rdIpClass.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                    if (checkedId == R.id.rbClassA) {
                        edtIpAddres1.setHint("1-127");
                        ipClassFlag = 1;
                        if (ipPreSubnetFlag == 1) {
                            setTxt("255", "", "", false, true, true);
                        }

                    } else if (checkedId == R.id.rbClassB) {
                        edtIpAddres1.setHint("128-191");
                        ipClassFlag = 2;
                        if (ipPreSubnetFlag == 1) {
                            setTxt("255", "255", "", false, false, true);
                        }
                    } else if (checkedId == R.id.rbClassC) {
                        edtIpAddres1.setHint("192-223");
                        ipClassFlag = 3;
                        if (ipPreSubnetFlag == 1) {
                            setTxt("255", "255", "255", false, false, false);
                        }
                    } else {
                        edtIpAddres1.setHint("1-255");
                        if (ipPreSubnetFlag == 1) {
                            setTxt("", "", "", true, true, true);
                        }
                    }
                    edtIpAddres2.setHint("0-255");
                    edtIpAddres3.setHint("0-255");
                    edtIpAddres4.setHint("0-255");
                }
            });
            calBttn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ipValidate(v);
                    getinfoafter();
                    if (errorflag) {
                        MainActivity mac = (MainActivity) getActivity();
                        mac.closeKeyboard(calBttn);
                        errorflag = false;
                    }
                }
            });
        }catch (Exception eer){errorresult(eer.getMessage());}
        return rootView;
    }
    public void errorresult(String ess){
        alertMsg("ERROR!","Please restart App "+ess);
    }
    public void setTxt(String ist,String sec,String thd,boolean bist,boolean bsec,boolean bthd){
        edtSubnetmask1.setText(ist); edtSubnetmask1.setEnabled(bist);
        edtSubnetmask2.setText(sec); edtSubnetmask2.setEnabled(bsec);
        edtSubnetmask3.setText(thd); edtSubnetmask3.setEnabled(bthd);
    }
   public void ipValidate(View v)
   {
           if(edtIpAddres1.getText().toString().equals("")  || edtIpAddres2.getText().toString().equals("") || edtIpAddres3.getText().toString().equals("") || edtIpAddres4.getText().toString().equals(""))
           {
               Snackbar.make(v, "Please enter valid IP Address e.g:(1-255).(0-255).(0-255).(0-255)", Snackbar.LENGTH_LONG)
                       .setAction("Action", null).show();
           }
           //clasless
           else if(ipclassfullessFlag == 2 && ( (Short.parseShort( edtIpAddres1.getText().toString())) > 255 || (Short.parseShort( edtIpAddres1.getText().toString()) )< 1 || (Short.parseShort( edtIpAddres2.getText().toString())) > 255 || (Short.parseShort( edtIpAddres2.getText().toString())) < 0 || (Short.parseShort( edtIpAddres3.getText().toString())) > 255 || (Short.parseShort( edtIpAddres3.getText().toString())) < 0 || (Short.parseShort( edtIpAddres4.getText().toString())) > 255 || (Short.parseShort( edtIpAddres4.getText().toString())) < 0 ) )
           {
               Snackbar.make(v, "Please enter valid IP Address e.g:(1-255).(0-255).(0-255).(0-255)", Snackbar.LENGTH_LONG)
                       .setAction("Action", null).show();
           }
           //classfull
           else if(ipclassfullessFlag == 1 && ipClassFlag==1 && ( (Short.parseShort( edtIpAddres1.getText().toString())) > 127 || (Short.parseShort( edtIpAddres1.getText().toString()) )< 1 || (Short.parseShort( edtIpAddres2.getText().toString())) > 255 || (Short.parseShort( edtIpAddres2.getText().toString())) < 0 || (Short.parseShort( edtIpAddres3.getText().toString())) > 255 || (Short.parseShort( edtIpAddres3.getText().toString())) < 0 || (Short.parseShort( edtIpAddres4.getText().toString())) > 255 || (Short.parseShort( edtIpAddres4.getText().toString())) < 0 ) )
           {
               Snackbar.make(v, "Please enter valid IP Address for IP Class A!         Its Range is between:(1-127).(0-255).(0-255).(0-255)", Snackbar.LENGTH_LONG)
                       .setAction("Action", null).show();
           }
           else if(ipclassfullessFlag == 1 && ipClassFlag==2 && ( (Short.parseShort( edtIpAddres1.getText().toString())) > 191 || (Short.parseShort( edtIpAddres1.getText().toString()) )< 128 || (Short.parseShort( edtIpAddres2.getText().toString())) > 255 || (Short.parseShort( edtIpAddres2.getText().toString())) < 0 || (Short.parseShort( edtIpAddres3.getText().toString())) > 255 || (Short.parseShort( edtIpAddres3.getText().toString())) < 0 || (Short.parseShort( edtIpAddres4.getText().toString())) > 255 || (Short.parseShort( edtIpAddres4.getText().toString())) < 0 ) )
           {
               Snackbar.make(v, "Please enter valid IP Address for IP Class B!         Its Range is between:(128-191).(0-255).(0-255).(0-255)", Snackbar.LENGTH_LONG)
                       .setAction("Action", null).show();
           }
           else if(ipclassfullessFlag == 1 && ipClassFlag==3 && ( (Short.parseShort( edtIpAddres1.getText().toString())) > 223 || (Short.parseShort( edtIpAddres1.getText().toString()) )< 192 || (Short.parseShort( edtIpAddres2.getText().toString())) > 255 || (Short.parseShort( edtIpAddres2.getText().toString())) < 0 || (Short.parseShort( edtIpAddres3.getText().toString())) > 255 || (Short.parseShort( edtIpAddres3.getText().toString())) < 0 || (Short.parseShort( edtIpAddres4.getText().toString())) > 255 || (Short.parseShort( edtIpAddres4.getText().toString())) < 0 ) )
           {
               Snackbar.make(v, "Please enter valid IP Address for IP Class C!          Its Range is between:(192-223).(0-255).(0-255).(0-255)", Snackbar.LENGTH_LONG)
                       .setAction("Action", null).show();
           }
           else if((Short.parseShort( edtIpAddres1.getText().toString())) == 127 )
           {
               alertMsg("Invalid IP Value!","127.0.0.0 -127.255.255.255 address range \n is reserved for loopback addresses;Can't be used!");
               edtIpAddres1.setText("");
           }
           else
           {
               subnetValidate();
           }
   }
  public void subnetValidate()
  {
     byte prefcode=0;
                 if (ipPreSubnetFlag ==1)//subnet
                 {
                    if(edtSubnetmask1.getText().toString().equals("") || edtSubnetmask2.getText().toString().equals("") || edtSubnetmask3.getText().toString().equals("") || edtSubnetmask4.getText().toString().equals(""))
                    {
                        prefcode=7;

                    }
                    else if(Short.parseShort(edtSubnetmask1.getText().toString())>0 && Short.parseShort(edtSubnetmask2.getText().toString())==0 && Short.parseShort(edtSubnetmask3.getText().toString())==0 && Short.parseShort(edtSubnetmask4.getText().toString())==0)
                    {
                        prefcode=1;
                     checking(Short.parseShort(edtSubnetmask1.getText().toString()),prefcode);
                    }
                    else if(Short.parseShort(edtSubnetmask1.getText().toString())==255 && Short.parseShort(edtSubnetmask2.getText().toString())>0 && Short.parseShort(edtSubnetmask3.getText().toString())==0 && Short.parseShort(edtSubnetmask4.getText().toString())==0)
                    {
                        prefcode=2;
                        checking(Short.parseShort(edtSubnetmask2.getText().toString()),prefcode);
                    }
                    else if(Short.parseShort(edtSubnetmask1.getText().toString())==255 && Short.parseShort(edtSubnetmask2.getText().toString())==255 && Short.parseShort(edtSubnetmask3.getText().toString())>0 && Short.parseShort(edtSubnetmask4.getText().toString())==0)
                    {
                        prefcode=3;
                        checking(Short.parseShort(edtSubnetmask3.getText().toString()),prefcode);
                    }
                    else if(Short.parseShort(edtSubnetmask1.getText().toString())==255 && Short.parseShort(edtSubnetmask2.getText().toString())==255 && Short.parseShort(edtSubnetmask3.getText().toString())==255 && Short.parseShort(edtSubnetmask4.getText().toString())>0)
                    {
                        prefcode=4;
                        checking(Short.parseShort(edtSubnetmask4.getText().toString()),prefcode);
                    }
                       if(prefcode==7){
                         alertMsg("Empty SubnetMask!","Please enter valid SubnetMask like 255.0.0.0");
                           }
                        else if(!errorflag)
                           {
                            alertMsg("Invalid SubnetMask!","Please enter valid SubnetMask like 255.0.0.0" +
                                    "  \n Subnet Mask must be contiguous \n e.g; 128.0.0.0 prefix /1  " +
                                            " 192.0.0.0 is prefix /2 \n" +
                                            " 224.0.0.0 is prefix /3  \n" +
                                            " upto\n" +
                                            " 255.255.255.255 is prefix /32");
                           }
                        else
                            {
                                txtSubnetmaskResult.setText(edtSubnetmask1.getText()+"."+edtSubnetmask2.getText()+"."+edtSubnetmask3.getText()+"."+edtSubnetmask4.getText()+"");
                                setSubtext(Short.parseShort(edtSubnetmask1.getText().toString()),Short.parseShort(edtSubnetmask2.getText().toString()),Short.parseShort(edtSubnetmask3.getText().toString()),Short.parseShort(edtSubnetmask4.getText().toString()));
                            }
                 }
                 else if(ipPreSubnetFlag==2 && ipclassfullessFlag==2)//prefix 2 mean classless
                 {
                     if (edtPrefix.getText().toString().equals(""))
                         {
                             alertMsg("Invalid Prefix!"," Prefix can't be empty.");
                         }
                         else if(Short.parseShort(edtPrefix.getText().toString())>0 && Short.parseShort(edtPrefix.getText().toString())<=32)
                          {
                              setMsg();
                          }
                          else
                              {
                                  alertMsg("Invalid Prefix","Please enter valid Prefix between 1-32");
                              }
                 }
                 else if(ipPreSubnetFlag==2 && ipclassfullessFlag==1)
                 {
                     if (edtPrefix.getText().toString().equals(""))
                     {
                         alertMsg("Invalid Prefix"," Prefix can't be empty.");
                     }
                     else if(ipClassFlag==1 && Short.parseShort(edtPrefix.getText().toString())>=8 && Short.parseShort(edtPrefix.getText().toString())<33)
                     {
                         setMsg();
                     }
                     else if(ipClassFlag==2 && Short.parseShort(edtPrefix.getText().toString())>=16 && Short.parseShort(edtPrefix.getText().toString())<33)
                     {
                         setMsg();
                     }
                     else if(ipClassFlag==3 && Short.parseShort(edtPrefix.getText().toString())>=24 && Short.parseShort(edtPrefix.getText().toString())<33)
                     {
                         setMsg();
                     }
                     else
                         {
                             alertMsg("Invalid Prefix!","Please enter valid Prefix for given class. \n For class A it's 8-32 \n For class B it's 16-32 \nFor class C it's 24-32");
                         }
                 }

  }
  public void setSubtext(Short ist,Short sec,Short third,Short fourth)
  {
      subnetid[0]= ist;
      subnetid[1]= sec;
      subnetid[2]= third;
      subnetid[3]= fourth;
  }
  public void setMsg()
  {
      errorflag=true;
      txtPrefixResult.setText("/"+Short.parseShort(edtPrefix.getText().toString())+"");
      getInfo();
      setSubnet();
  }
  public void getInfo()
  {
      if(ipclassfullessFlag==2)
      {
          String hh="CIDR/Classless";
       txtIpClassResult.setText(hh);
      }
      else if(ipclassfullessFlag==1)
      {
          switch (ipClassFlag)
          {
              case 1:
                  txtIpClassResult.setText("   A");
                break;
              case 2:
                  txtIpClassResult.setText("   B");
                  break;
              case 3:
                  txtIpClassResult.setText("   C");
                  break;
          }
      }
      txtIpaddResult.setText(edtIpAddres1.getText().toString()+"."+edtIpAddres2.getText().toString()+"."+edtIpAddres3.getText().toString()+"."+edtIpAddres4.getText().toString());

  }
  public void getinfoafter()
  {
      if(txtIpaddResult.getCurrentTextColor()==Color.parseColor("#110044")){
      txtIpaddResult.setTextColor(Color.parseColor("#229922"));}
      else  if(txtIpaddResult.getCurrentTextColor()==Color.parseColor("#229922"))
      {txtIpaddResult.setTextColor(Color.parseColor("#110044"));}

      if(!edtIpAddres1.getText().toString().equals("") && subnetid[0] !=null)
      {
          short netId[] = new short[4];
          short wildId[]=new short[4];
          netId[0]=(short)(Short.parseShort(edtIpAddres1.getText().toString()) & (subnetid[0]));
          netId[1]=(short)(Short.parseShort(edtIpAddres2.getText().toString()) & (subnetid[1]));
          netId[2]=(short)(Short.parseShort(edtIpAddres3.getText().toString()) & (subnetid[2]));
          netId[3]=(short)(Short.parseShort(edtIpAddres4.getText().toString()) & (subnetid[3]));
          wildId[0]=(short) ((~subnetid[0]) & 255);
          wildId[1]=(short) ((~subnetid[1]) & 255);
          wildId[2]=(short) ((~subnetid[2]) & 255);
          wildId[3]=(short) ((~subnetid[3]) & 255);

          txtWildcardResult.setText(""+wildId[0]+"."+ wildId[1]+"."+ wildId[2]+"."+ wildId[3]);
          txtNetIdResult.setText(""+netId[0]+"."+netId[1]+"."+netId[2]+"."+netId[3]);

          String get= txtPrefixResult.getText().toString().substring(1);
          short me=(short) ((~Short.parseShort(get) & 31)+1);
          long data=(long) Math.pow(2,me);
          byte pow;
          String Da="As";
          if(Short.parseShort(get)<8) {
               pow = (byte)Short.parseShort(get);
          }else
              {
                  pow = 8;
              }
          long subdata=(long) Math.pow(2,(Short.parseShort(get)-pow));

             if(txtPrefixResult.getText().toString().equals("/31") || txtPrefixResult.getText().toString().equals("/32"))
              {
                  txtIstHostResult.setText(""+netId[0]+"."+netId[1]+"."+netId[2]+"."+netId[3]);
                  txtLastHostIdResult.setText((wildId[0] | netId[0]) + "." + (wildId[1] | netId[1]) + "." + (wildId[2] | netId[2]) + "." + (wildId[3] | netId[3]));
                  txtNoOfHostsResult.setText(""+"Its"+"=0,for /31 & /32");
                  txtNoOfSubnetResult.setText(""+(subdata)+"");

              }
              else
              {
                  txtIstHostResult.setText(""+netId[0]+"."+netId[1]+"."+netId[2]+"."+(netId[3]+1));
                  txtLastHostIdResult.setText((wildId[0] | netId[0]) + "." + (wildId[1] | netId[1]) + "." + (wildId[2] | netId[2]) + "." + ((wildId[3] | netId[3]) - 1));
                  txtNoOfHostsResult.setText(""+(data-2)+"");
                  switch(Short.parseShort(get)) {
                      case 1:
                          Da="128As";
                          break;
                      case 2:
                          Da="64As";
                          break;
                      case 3:
                          Da="32As";
                          break;
                      case 4:
                          Da="16As";
                          break;
                      case 5:
                          Da="8As";
                          break;
                      case 6:
                          Da="4As";
                          break;
                      case 7:
                          Da="2As";
                          break;
                  }
                  if(Short.parseShort(get)<8)
                  txtNoOfSubnetResult.setText(""+Da+"");
                  else
                      txtNoOfSubnetResult.setText(""+(subdata)+"");
              }
                txtBroarcastResult.setText((wildId[0] | netId[0])+"."+(wildId[1] | netId[1])+"."+(wildId[2] | netId[2])+"."+ (wildId[3] | netId[3]));

          if(Short.parseShort(edtIpAddres1.getText().toString())==10){
              txtMoreInfo.setText(R.string.privateAdd);
          }else if(Short.parseShort(edtIpAddres1.getText().toString())==172 && (Short.parseShort(edtIpAddres2.getText().toString())>15 && Short.parseShort(edtIpAddres2.getText().toString())<32 )){
              txtMoreInfo.setText(R.string.privateAdd);
          }else if(Short.parseShort(edtIpAddres1.getText().toString())==192 && Short.parseShort(edtIpAddres2.getText().toString())==168 ){
              txtMoreInfo.setText(R.string.privateAdd);
          }else if(Short.parseShort(edtIpAddres1.getText().toString())==169 && (Short.parseShort(edtIpAddres2.getText().toString())==254) && (Short.parseShort(edtIpAddres4.getText().toString())>0 && Short.parseShort(edtIpAddres4.getText().toString())<=254) ){
              txtMoreInfo.setText(R.string.APIPAAdd);
          }else if(Short.parseShort(edtIpAddres1.getText().toString())>=224 && Short.parseShort(edtIpAddres1.getText().toString())<=239 ){
              txtMoreInfo.setText(R.string.multiAdd);
          }else{
              txtMoreInfo.setText(R.string.publicAdd);
          }
      }
  }
  public void checking(Short value, byte code)
  {
      switch (value)
      {
          case 128: case 192: case 224: case 240: case 248: case 252: case 254: case 255: {
          if (code == 1) {
              switch (value) {
                  case 128:
                      prefixResult = 1;
                      break;
                  case 192:
                      prefixResult = 2;
                      break;
                  case 224:
                      prefixResult = 3;
                      break;
                  case 240:
                      prefixResult = 4;
                      break;
                  case 248:
                      prefixResult = 5;
                      break;
                  case 252:
                      prefixResult = 6;
                      break;
                  case 254:
                      prefixResult = 7;
                      break;
                  case 255:
                      prefixResult = 8;
                      break;
              }
          } else if (code == 2) {
              switch (value) {
                  case 128:
                      prefixResult = 9;
                      break;
                  case 192:
                      prefixResult = 10;
                      break;
                  case 224:
                      prefixResult = 11;
                      break;
                  case 240:
                      prefixResult = 12;
                      break;
                  case 248:
                      prefixResult = 13;
                      break;
                  case 252:
                      prefixResult = 14;
                      break;
                  case 254:
                      prefixResult = 15;
                      break;
                  case 255:
                      prefixResult = 16;
                      break;
              }
          } else if (code == 3) {
              switch (value) {
                  case 128:
                      prefixResult = 17;
                      break;
                  case 192:
                      prefixResult = 18;
                      break;
                  case 224:
                      prefixResult = 19;
                      break;
                  case 240:
                      prefixResult = 20;
                      break;
                  case 248:
                      prefixResult = 21;
                      break;
                  case 252:
                      prefixResult = 22;
                      break;
                  case 254:
                      prefixResult = 23;
                      break;
                  case 255:
                      prefixResult = 24;
                      break;
              }
          } else if (code == 4) {
              switch (value) {
                  case 128:
                      prefixResult = 25;
                      break;
                  case 192:
                      prefixResult = 26;
                      break;
                  case 224:
                      prefixResult = 27;
                      break;
                  case 240:
                      prefixResult = 28;
                      break;
                  case 248:
                      prefixResult = 29;
                      break;
                  case 252:
                      prefixResult = 30;
                      break;
                  case 254:
                      prefixResult = 31;
                      break;
                  case 255:
                      prefixResult = 32;
                      break;
              }
          }
          txtPrefixResult.setText("/"+prefixResult+"");
          getInfo();
          errorflag=true;
          break;
      }
           default:
           {
               errorflag=false;
               break;
           }
      }
  }
  public void alertMsg(String title,String msg)
  {
      AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
      alertDialog.setTitle(title);
      alertDialog.setMessage(msg);
      alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
              errorflag=false;
              edtPrefix.setText("");
          }
      });
      alertDialog.show();
  }
    public void setSubnet()
    {
        short subterm=0;
        switch(Short.parseShort(edtPrefix.getText().toString())){
            case 1: case 9: case 17: case 25:
                subterm=128;
                break;
            case 2: case 10: case 18: case 26:
                subterm=192;
                break;
            case 3: case 11: case 19: case 27:
                subterm=224;
                break;
            case 4: case 12: case 20: case 28:
                subterm=240;
                break;
            case 5: case 13: case 21: case 29:
                subterm=248;
                break;
            case 6: case 14: case 22: case 30:
                subterm=252;
                break;
            case 7: case 15: case 23: case 31:
                subterm=254;
                break;
            case 8: case 16: case 24: case 32:
                subterm=255;
                break;
        }
        if(Short.parseShort(edtPrefix.getText().toString())<=8)
        {
            txtSubnetmaskResult.setText(subterm+".0"+".0"+".0");
            setSubtext(subterm,(short)0,(short)0,(short)0);
        }
        else if(Short.parseShort(edtPrefix.getText().toString()) >8 && Short.parseShort(edtPrefix.getText().toString())<=16)
        {
            txtSubnetmaskResult.setText("255."+subterm+".0.0");
            setSubtext((short)255,subterm,(short)0,(short)0);
        }
        else if(Short.parseShort(edtPrefix.getText().toString()) >16 && Short.parseShort(edtPrefix.getText().toString())<=24)
        {
            txtSubnetmaskResult.setText("255.255."+subterm+".0");
            setSubtext((short)255,(short)255,subterm,(short)0);
        }
        else
        {
            txtSubnetmaskResult.setText("255.255"+".255."+subterm);
            setSubtext((short)255,(short)255,(short)255,subterm);
        }
       prefixResult= (byte) Short.parseShort(edtPrefix.getText().toString());
    }
}

