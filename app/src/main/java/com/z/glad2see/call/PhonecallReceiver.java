package com.z.glad2see.call;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.z.glad2see.utils.SupportUtils;

import java.util.Date;

import io.reactivex.disposables.CompositeDisposable;

public abstract class PhonecallReceiver extends BroadcastReceiver {

    //The receiver will be recreated whenever android feels like it.  We need a static variable to remember data between instantiations



    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    private static Date callStartTime;
    private static boolean isIncoming;
    private static String savedNumber;  //because the passed incoming is only valid in ringing


    @Override
    public void onReceive(final Context context, Intent intent) {

//        //We listen to two intents.  The new outgoing call only tells us of an outgoing call.  We use it to get the number.
//        if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
//            savedNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");
//        }
//        else{
//            String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
//            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
//            int state = 0;
//            if(stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)){
//                state = TelephonyManager.CALL_STATE_IDLE;
//            }
//            else if(stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
//                state = TelephonyManager.CALL_STATE_OFFHOOK;
//            }
//            else if(stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)){
//                state = TelephonyManager.CALL_STATE_RINGING;
//            }
//
//            ontCallStateChanged(context, state, number);
//        }

        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, final String number) {
                super.onCallStateChanged(state, number);
                Log.d("ontCallStateChanged", " === " + number);
                ontCallStateChanged(context, state, number);
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
    }

    //Derived classes should override these to respond to specific events of interest
    protected void onIncomingCallStarted(Context ctx, String number, Date start){}
    protected void onOutgoingCallStarted(Context ctx, String number, Date start){}
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end){}
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end){}
    protected void onMissedCall(Context ctx, String number, Date start){}

    //Deals with actual events

    //Incoming call-  goes from IDLE to RINGING when it rings, to OFFHOOK when it's answered, to IDLE when its hung up
    //Outgoing call-  goes from IDLE to OFFHOOK when it dials out, to IDLE when hung up
    public void ontCallStateChanged(Context context, int state, String number) {
        if(lastState == state){
            //No change, debounce extras
            return;
        }
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                isIncoming = true;
                callStartTime = new Date();
                savedNumber = number;
                onIncomingCallStarted(context, number, callStartTime);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                //Transition of ringing->offhook are pickups of incoming calls.  Nothing done on them
                if(lastState != TelephonyManager.CALL_STATE_RINGING){
                    isIncoming = false;
                    callStartTime = new Date();
                    onOutgoingCallStarted(context, savedNumber, callStartTime);
                }
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                //Went to idle-  this is the end of a call.  What type depends on previous state(s)
                if(lastState == TelephonyManager.CALL_STATE_RINGING){
                    //Ring but no pickup-  a miss
                    onMissedCall(context, savedNumber, callStartTime);
                }
                else if(isIncoming){
                    onIncomingCallEnded(context, savedNumber, callStartTime, new Date());
                }
                else{
                    onOutgoingCallEnded(context, savedNumber, callStartTime, new Date());
                }
                break;
        }
        lastState = state;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}