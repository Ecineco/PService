package com.ecin.pserviceii;

import android.content.Intent;
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

public class PreDetailClientActivity extends AppCompatActivity {
    int preId, sid, tid, pid;
    String sName, pName, pAdress, preStart, preFee, pPhone, petName, preState;
    Connection conn;
    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7;
    EditText et;
    Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predetailclient);

        Bundle bundle = getIntent().getExtras();
        preId = bundle.getInt("preservationId");

        tv1 = findViewById(R.id.text_apptitle6);
        tv2 = findViewById(R.id.textView);
        tv3 = findViewById(R.id.textView7);
        tv4 = findViewById(R.id.textView8);
        tv5 = findViewById(R.id.textView9);
        tv6 = findViewById(R.id.textView12);
        tv7 = findViewById(R.id.textView13);
        et = findViewById(R.id.editText2);

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
                    stmt = conn.prepareStatement("SELECT pid FROM Provider_Service WHERE sid=?");
                    stmt.setInt(1, sid);
                    result = stmt.executeQuery();
                    if(result.next()){
                        pid = result.getInt("pid");
                    }
                    stmt = conn.prepareStatement("SELECT * FROM Provider WHERE pid=?");
                    stmt.setInt(1, pid);
                    result = stmt.executeQuery();
                    if(result.next()){
                        pName = result.getString("name");
                        pPhone = "联系电话:" + result.getString("phone");
                        pAdress = "地址:" + result.getString("address");
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

        cancel = findViewById(R.id.button3);
        cancel.setOnClickListener(new View.OnClickListener() {
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
                                    stmt2 = conn.prepareStatement("DELETE FROM Preservation WHERE prid=?");
                                    stmt2.executeUpdate();
                                    finish();
                                }
                                else{
                                    loadHandler.sendEmptyMessage(1);
                                }
                            }
                        } catch(SQLException ex){
                            AlertDialog.Builder builder = new AlertDialog.Builder(PreDetailClientActivity.this);
                            builder.setTitle("提示");
                            builder.setMessage("取消失败");
                            builder.setPositiveButton("确认", null);
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
            tv2.setText(pName);
            tv3.setText(preFee);
            tv4.setText(pPhone);
            tv5.setText(preStart);
            tv6.setText(petName);
            tv7.setText(preState);
            et.setText(pAdress);
        }
    };
}
