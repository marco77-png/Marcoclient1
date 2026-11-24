package com.marco.client;

import android.app.*;
import android.os.Bundle;
import android.content.*;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.*;
import android.widget.*;
import android.text.InputType;

public class MainActivity extends Activity {
    private final int BG = Color.rgb(10,7,18), PANEL = Color.rgb(25,17,39), PURPLE = Color.rgb(177,48,255), WHITE = Color.WHITE;
    private LinearLayout root, content, nav;
    private String current = "Home";
    private android.content.SharedPreferences prefs;

    public void onCreate(Bundle b) {
        super.onCreate(b);
        prefs = getSharedPreferences("marco_settings", MODE_PRIVATE);
        buildShell();
        showPage("Home");
    }

    private GradientDrawable shape(int color, int radius, int stroke) {
        GradientDrawable d = new GradientDrawable(); d.setColor(color); d.setCornerRadius(radius);
        if (stroke != 0) d.setStroke(1, stroke); return d;
    }
    private TextView text(String s, int size, boolean bold) {
        TextView t = new TextView(this); t.setText(s); t.setTextColor(WHITE); t.setTextSize(size);
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD); return t;
    }
    private LinearLayout column() { LinearLayout l = new LinearLayout(this); l.setOrientation(1); return l; }
    private void pad(View v, int n) { v.setPadding(n,n,n,n); }
    private void pad(View v, int[] p) { v.setPadding(p[0],p[1],p[2],p[3]); }
    private void buildShell() {
        root = column(); root.setBackgroundColor(BG);
        TextView brand = text("✦  MARCO CLIENT", 22, true); pad(brand,20); root.addView(brand);
        TextView sub = text("BEDROCK COMPANION  •  v1.0", 10, false); sub.setTextColor(0xffbda8d1); pad(sub, new int[]{20,0,20,14}); root.addView(sub);
        ScrollView scroll = new ScrollView(this); content = column(); pad(content,16); scroll.addView(content);
        root.addView(scroll, new LinearLayout.LayoutParams(-1,0,1));
        nav = new LinearLayout(this); nav.setPadding(8,8,8,12); nav.setBackgroundColor(0xff130d1d);
        String[] tabs={"Home","Combat","Visuals","Utilities","Settings"};
        for(String tab:tabs) { TextView b=text(tab,11,true); b.setGravity(Gravity.CENTER); b.setPadding(5,14,5,14);
            b.setOnClickListener(v->showPage(tab)); nav.addView(b,new LinearLayout.LayoutParams(0,-2,1)); }
        root.addView(nav); setContentView(root);
    }
    private void heading(String title, String desc) {
        TextView h=text(title,24,true); pad(h,new int[]{4,8,4,2}); content.addView(h);
        TextView d=text(desc,12,false); d.setTextColor(0xffbda8d1); pad(d,new int[]{4,0,4,16}); content.addView(d);
    }
    private void card(String title, String desc) {
        LinearLayout c=column(); c.setBackground(shape(PANEL,22,0xff38234e)); c.setPadding(16,14,16,14);
        TextView h=text(title,16,true); c.addView(h); TextView d=text(desc,12,false); d.setTextColor(0xffc5b5d3); c.addView(d);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2); lp.bottomMargin=12; content.addView(c,lp);
    }
    private void toggle(String title, String desc, boolean defaultValue) {
        LinearLayout row=new LinearLayout(this); row.setGravity(Gravity.CENTER_VERTICAL); row.setPadding(14,10,12,10);
        row.setBackground(shape(PANEL,18,0xff38234e));
        LinearLayout labels=column(); TextView h=text(title,14,true); labels.addView(h);
        TextView d=text(desc,10,false); d.setTextColor(0xffbda8d1); labels.addView(d);
        row.addView(labels,new LinearLayout.LayoutParams(0,-2,1));
        Switch sw=new Switch(this); sw.setButtonTintList(null);
        sw.setChecked(prefs.getBoolean(title,defaultValue));
        sw.setOnCheckedChangeListener((button,checked)->prefs.edit().putBoolean(title,checked).apply());
        row.addView(sw);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2); lp.bottomMargin=8; content.addView(row,lp);
    }
    private void button(String label, Runnable action) {
        TextView b=text(label,14,true); b.setGravity(Gravity.CENTER); b.setPadding(14,15,14,15); b.setBackground(shape(PURPLE,18,0));
        b.setOnClickListener(v->action.run()); LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2); lp.bottomMargin=12; content.addView(b,lp);
    }
    private void showPage(String page) {
        current=page; content.removeAllViews();
        if(page.equals("Home")) {
            heading("Welcome to Marco", "Your Minecraft Bedrock companion");
            card("MARCO CLIENT", "Purple edition • Your settings, your style");
            button("LAUNCH MINECRAFT", () -> launchMinecraft());
            card("Quick access", "Combat HUD • Visual customization • Utilities");
            card("Note", "This companion app saves preferences locally. Game-side effects require compatible Bedrock resource packs or add-ons.");
        } else if(page.equals("Combat")) {
            heading("Combat & HUD", "HUD and information overlays");
            toggle("Armor HUD","Display armor status setting",true);
            toggle("Totem Counter","Show a totem counter setting",true);
            toggle("CPS Counter","Show clicks-per-second counter setting",false);
            toggle("FPS Counter","Show frame-rate counter setting",true);
            toggle("Ping Counter","Show connection latency setting",false);
            toggle("Coordinates HUD","Show coordinates setting",true);
            toggle("Keystrokes","Show input display setting",false);
            toggle("Durability Warning","Enable item durability reminder setting",true);
            toggle("Low Health Indicator","Enable low-health indicator setting",true);
            toggle("Custom HUD","Enable custom HUD layout setting",false);
        } else if(page.equals("Visuals")) {
            heading("Visuals", "Comfort and appearance options");
            toggle("No Animation","Reduce supported interface animations",false);
            toggle("No Particles","Reduce supported visual particles",false);
            toggle("Full Bright","Brightness preference shortcut",false);
            toggle("Custom Crosshair","Crosshair preference",false);
            toggle("Zoom","Zoom preference shortcut",false);
            toggle("Saturation Control","Colour preference",false);
            toggle("Fog & Weather","Visual preference shortcut",false);
            toggle("Time Changer","World appearance preference",false);
        } else if(page.equals("Utilities")) {
            heading("Utilities", "Helpful companion tools");
            toggle("Waypoints","Save waypoint preference",false);
            toggle("Death Location Reminder","Enable reminder preference",false);
            toggle("Stopwatch","Show stopwatch tool preference",false);
            toggle("Screenshot Shortcut","Enable shortcut preference",false);
            toggle("Resource Pack Manager","Show pack manager entry",true);
            card("Compatibility", "Utilities that need in-game integration may require a supported add-on or resource pack.");
        } else {
            heading("Settings", "Personalize Marco Client");
            EditText name=new EditText(this); name.setSingleLine(true); name.setTextColor(WHITE); name.setHintTextColor(0xffa997b8); name.setHint("Local profile name");
            name.setText(prefs.getString("profile","Player")); name.setPadding(14,12,14,12); name.setBackground(shape(PANEL,16,0xff38234e)); content.addView(name);
            button("SAVE PROFILE",()->{prefs.edit().putString("profile",name.getText().toString()).apply(); Toast.makeText(this,"Profile saved",Toast.LENGTH_SHORT).show();});
            button("RESET ALL SETTINGS",()->new AlertDialog.Builder(this).setTitle("Reset settings?").setMessage("This resets local Marco preferences.").setNegativeButton("Cancel",null).setPositiveButton("Reset",(d,w)->{prefs.edit().clear().apply();showPage("Settings");}).show());
            card("Account safety", "Sign in through the official Minecraft app. Marco does not ask for or store Microsoft passwords.");
            card("About", "Marco Client • Version 1.0");
        }
    }
    private void launchMinecraft() {
        Intent i=getPackageManager().getLaunchIntentForPackage("com.mojang.minecraftpe");
        if(i==null) Toast.makeText(this,"Minecraft was not found on this device.",Toast.LENGTH_LONG).show();
        else startActivity(i);
    }
}

