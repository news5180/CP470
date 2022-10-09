package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class activity_chat_window extends AppCompatActivity {

    private ListView listView;
    private EditText editText;
    private Button sendButton;
    private ArrayList<String> messages = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        listView = (ListView)findViewById(R.id.listView);
        editText = (EditText)findViewById(R.id.editText);
        sendButton = (Button)findViewById(R.id.sendButton);

        ChatAdapter messageAdapter = new ChatAdapter( this );
        listView.setAdapter (messageAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String messageText = editText.getText().toString();
                if(messageText != "") {
                    messages.add(messageText);
                }
                messageAdapter.notifyDataSetChanged();
                editText.setText("");
            }
        });
    }

    private class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount(){
            return messages.size();
        }

        public String getItem(int position){
            return messages.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = activity_chat_window.this.getLayoutInflater();
            View result = null ;
            if(position%2 == 0) {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }
            else {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            }

            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;
        }
    }
}