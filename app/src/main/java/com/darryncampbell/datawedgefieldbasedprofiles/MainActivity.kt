package com.darryncampbell.datawedgefieldbasedprofiles

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

const val DATAWEDGE_SEND_GET_ACTIVE_PROFILE = "com.symbol.datawedge.api.GET_ACTIVE_PROFILE"
const val DATAWEDGE_RETURN_GET_ACTIVE_PROFILE = "com.symbol.datawedge.api.RESULT_GET_ACTIVE_PROFILE"
const val DATAWEDGE_SWITCH_TO_PROFILE = "com.symbol.datawedge.api.SWITCH_TO_PROFILE"
const val DATAWEDGE_CREATE_PROFILE = "com.symbol.datawedge.api.CREATE_PROFILE"
const val DATAWEDGE_RETURN_ACTION = "com.symbol.datawedge.api.RESULT_ACTION"
const val DATAWEDGE_RETURN_CATEGORY = "android.intent.category.DEFAULT"
const val DATAWEDGE_SEND_ACTION = "com.symbol.datawedge.api.ACTION"
const val PROFILE_NAME_1 = "switch_profile1"
const val PROFILE_NAME_2 = "switch_profile2"
const val PROFILE_NAME_3 = "switch_profile3"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var field1: EditText = findViewById(R.id.editText1)
        field1.setOnFocusChangeListener { view, b-> hasFocus(view, b)}
        var field2: EditText = findViewById(R.id.editText2)
        field2.setOnFocusChangeListener { view, b-> hasFocus(view, b)}
        var field3: EditText = findViewById(R.id.editText3)
        field3.setOnFocusChangeListener { view, b-> hasFocus(view, b)}

        //  Create test profiles to show switching is possible
        sendDataWedgeCommand(DATAWEDGE_CREATE_PROFILE, PROFILE_NAME_1)
        sendDataWedgeCommand(DATAWEDGE_CREATE_PROFILE, PROFILE_NAME_2)
        sendDataWedgeCommand(DATAWEDGE_CREATE_PROFILE, PROFILE_NAME_3)
    }

    override fun onResume() {
        super.onResume()
        //  Register broadcast receiver to listen for responses from DW API
        val intentFilter = IntentFilter()
        intentFilter.addAction(DATAWEDGE_RETURN_ACTION)
        intentFilter.addCategory(DATAWEDGE_RETURN_CATEGORY)
        registerReceiver(receiver, intentFilter)
        determineFocus()
        sendDataWedgeCommand(DATAWEDGE_SEND_GET_ACTIVE_PROFILE, "")
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    fun determineFocus() {
        var field1: EditText = findViewById(R.id.editText1)
        var field2: EditText = findViewById(R.id.editText2)
        var field3: EditText = findViewById(R.id.editText3)
        if (field1.hasFocus())
            hasFocus(field1, true)
        else if (field2.hasFocus())
            hasFocus(field2, true)
        else if (field3.hasFocus())
            hasFocus(field3, true)
    }

    fun hasFocus(view: View, hasFocus: Boolean)
    {
        if (view.id == R.id.editText1 && hasFocus)
            applyProfile(PROFILE_NAME_1);
        else if (view.id == R.id.editText2 && hasFocus)
            applyProfile(PROFILE_NAME_2);
        else if (view.id == R.id.editText3 && hasFocus)
            applyProfile(PROFILE_NAME_3);
    }

    fun applyProfile(profileName: String)
    {
        sendDataWedgeCommand(DATAWEDGE_SWITCH_TO_PROFILE, profileName)
        sendDataWedgeCommand(DATAWEDGE_SEND_GET_ACTIVE_PROFILE, "")
    }

    fun sendDataWedgeCommand(command: String, parameter: String)
    {
        val dwIntent = Intent()
        dwIntent.action = DATAWEDGE_SEND_ACTION
        dwIntent.putExtra(command, parameter)
        sendBroadcast(dwIntent)
    }

    private fun handleActiveProfileChanged(profileName: String) {
        var activeProfileText: TextView = findViewById(R.id.txtActiveProfile)
        activeProfileText.setText(profileName)
    }

    val receiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent) {
            if (intent.hasExtra(DATAWEDGE_RETURN_GET_ACTIVE_PROFILE))
            {
                handleActiveProfileChanged(intent.getStringExtra(DATAWEDGE_RETURN_GET_ACTIVE_PROFILE))
            }
        }
    }

}
