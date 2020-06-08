package com.ecin.pserviceii;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PreDetailProActivity extends AppCompatActivity {
    int preId, sid, tid, uid;
    String sName, uName, preStart, preFee, uPhone, petName, preState;
    Connection conn;
    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7;
    Button refuse, accept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predetailpro);

        Bundle bundle = getIntent().getExtras();
        preId = bundle.getInt("preservationId");

        tv1 = findViewById(R.id.text_apptitle7);
        tv2 = findViewById(R.id.textView10);
        tv3 = findViewById(R.id.textView17);
        tv4 = findViewById(R.id.textView18);
        tv5 = findViewById(R.id.textView16);
        tv6 = findViewById(R.id.textView20);
        tv7 = findViewById(R.id.textView21);

        new Thread(new Runnable() {
            @Override
            public void run() {
                conn = DBHelper.getConnection();
                PreparedStatement stmt;
                try{
                    stmt = conn.prepareStatement("SELECT * FROM Preservation WHERE prid=?");
                    stmt.setInt(1, preId);
                    ResultSet result = stmt.executeQuery();
                    if(result.next()){
                        sid = result.getInt("sid");
                        tid = result.getInt("tid");
                        uid = result.getInt("uid");
                        preStart = result.getString("start");
                        preFee = "预约费用:" + Float.toString(result.getFloat("expanse"));
                        preState = result.getString("state");
                    }
                    stmt = conn.prepareStatement("SELECT name FROM Service WHERE sid=?");
                    stmt.setInt(1, sid);
                    result = stmt.executeQuery();
                    if(result.next()){
                        sName = result.getString("name");
                    }
                    stmt = conn.prepareStatement("SELECT * FROM UserInfo WHERE uid=?");
                    stmt.setInt(1, uid);
                    result = stmt.executeQuery();
                    if(result.next()){
                        uName = result.getString("name");
                        uPhone = "联系电话:" + result.getString("phone");
                    }

                    stmt = conn.prepareStatement("SELECT species FROM PetInfo WHERE tid=?");
                    stmt.setInt(1, tid);
                    result = stmt.executeQuery();
                    if(result.next()){
                        petName = result.getString("species");
                    }
                } catch(SQLException ex){

                }
                loadHandler.sendEmptyMessage(1);
            }
        }).start();

        refuse = findViewById(R.id.button5);
        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        conn = DBHelper.getConnection();
                        PreparedStatement stmt2;
                        try{
                            stmt2 = conn.prepareStatement("SELECT state FROM Preservation WHERE prid=?");
                            stmt2.setInt(1, preId);
                            ResultSet currState = stmt2.executeQuery();
                            if(currState.next()){
                                preState = currState.getString("state");
                                if(preState.equals("待商家确认")){
                                    stmt2 = conn.prepareStatement("UPDATE Preservation SET state=? WHERE preid=?");
                                    stmt2.setString(1, "商家已拒绝");
                                    stmt2.setInt(2, preId);
                                    stmt2.executeUpdate();
                                    finish();
                                }
                                else{
                                    loadHandler.sendEmptyMessage(1);
                                }
                            }
                        } catch(SQLException ex){
                            //TODO
                        }
                    }
                }).start();
            }
        });

        accept = findViewById(R.id.button6);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        conn = DBHelper.getConnection();
                        PreparedStatement stmt2;
                        try{
                            stmt2 = conn.prepareStatement("SELECT state FROM Preservation WHERE prid=?");
                            stmt2.setInt(1, preId);
                            ResultSet currState = stmt2.executeQuery();
                            if(currState.next()){
                                preState = currState.getString("state");
                                if(preState.equals("待商家确认")){
                                    stmt2 = conn.prepareStatement("UPDATE Preservation SET state=? WHERE preid=?");
                                    stmt2.setString(1, "进行中");
                                    stmt2.setInt(2, preId);
                                    stmt2.executeUpdate();
                                    finish();
                                }
                                else{
                                    loadHandler.sendEmptyMessage(1);
                                }
                            }
                        } catch(SQLException ex){
                            //TODO
                        }
                    }
                }).start();
            }
        });
    }
    Handler loadHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            tv1.setText(sName);
            tv2.setText(uName);
            tv3.setText(uPhone);
            tv4.setText(preStart);
            tv5.setText(preFee);
            tv6.setText(petName);
            tv7.setText(preState);
        }
    };
}
