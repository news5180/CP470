package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    private ListView listView;
    private EditText editText;
    private Button sendButton;
    private ArrayList<String> messages = new ArrayList<String>();
    private ChatDatabaseHelper db;
    private SQLiteDatabase database;
    private String[] chatItems = { ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.KEY_MESSAGE };
    private Cursor cursor;
    private final String ACTIVITY_NAME = "ChatWindow";

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        listView = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.editText);
        sendButton = (Button) findViewById(R.id.sendButton);
        db = new ChatDatabaseHelper(this);
        database = db.getWritableDatabase();
        cursor = database.query(db.TABLE_NAME, chatItems, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            Log.i(ACTIVITY_NAME, "Cursor’s column count =" + cursor.getColumnCount() );
            cursor.moveToNext();
        }

        cursor.moveToFirst();
        for(int i = 0; i <  cursor.getColumnCount(); i++){
            Log.i(ACTIVITY_NAME,"COLUMN: " + cursor.getColumnName(i));
            cursor.moveToNext();
        }
        cursor.close();

        ChatAdapter messageAdapter = new ChatAdapter( this );
        listView.setAdapter (messageAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues contentValues = new ContentValues();
                String messageText = editText.getText().toString();
                contentValues.put(ChatDatabaseHelper.KEY_MESSAGE,messageText);
                long insertId = database.insert(ChatDatabaseHelper.TABLE_NAME, null, contentValues);
                Cursor cursor = database.query(ChatDatabaseHelper.TABLE_NAME, chatItems, ChatDatabaseHelper.KEY_ID + " = " + insertId, null, null, null, null);
                cursor.moveToFirst();
                cursor.close();
                if(messageText != "") {
                    messages.add(messageText);
                }
                messageAdapter.notifyDataSetChanged();
                editText.setText("");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
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
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
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