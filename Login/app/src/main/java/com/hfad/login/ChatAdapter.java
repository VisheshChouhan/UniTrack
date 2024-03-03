package com.hfad.login;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    ArrayList<ChatMessage> chatMessages;
    //Student student;
    String studentName;

    public ChatAdapter(String stuName, ArrayList<ChatMessage> chatMessages)
    {
        this.studentName = stuName;
        this.chatMessages = chatMessages;

    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {

        FrameLayout frameLayout;

        public ViewHolder(FrameLayout v) {
            super(v);
            frameLayout = v;
        }
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FrameLayout cv = (FrameLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chat, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessages.get(position);
        FrameLayout frameLayout = holder.frameLayout;
        CardView cardView = frameLayout.findViewById(R.id.receivedCardView);
        cardView.getBackground().setAlpha(0);



        //Received view
        TextView ReceivedMessageView = (TextView) frameLayout.findViewById(R.id.textview_chat_receieved);
        ImageView receivedImageView = frameLayout.findViewById(R.id.receivedImageView);

        //Sent user view
        TextView SentMessageView = (TextView) frameLayout.findViewById(R.id.textview_chat_sent);
        ImageView sentImageView = frameLayout.findViewById(R.id.sentImageView);
        TextView userNameView = (TextView) frameLayout.findViewById(R.id.userName);
        View divider = (View) frameLayout.findViewById(R.id.divider);


        //Make text selectable


        if(studentName.equals(chatMessage.getUser()))
        {
            if(chatMessage.getMessageType().equals("text"))
            {
                SentMessageView.setText(chatMessage.getMessage());
                sentImageView.setVisibility(View.GONE);

            }
            else
            {
                SentMessageView.setVisibility(View.GONE);
                Glide.with(frameLayout).load(chatMessage.getImageUrl()).into(sentImageView);
                sentImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
            SentMessageView.setText(chatMessage.getMessage());
            ReceivedMessageView.setVisibility(View.GONE);
            receivedImageView.setVisibility(View.GONE);
            userNameView.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);

        }
        else
        {
            if(chatMessage.getMessageType().equals("text"))
            {
                ReceivedMessageView.setText(chatMessage.getMessage());
                receivedImageView.setVisibility(View.GONE);

            }
            else
            {
                ReceivedMessageView.setVisibility(View.GONE);
                Glide.with(frameLayout).load(chatMessage.getImageUrl()).into(receivedImageView);
            }
            ReceivedMessageView.setText(chatMessage.getMessage());

            userNameView.setText(chatMessage.getUser());
            SentMessageView.setVisibility(View.GONE);
            sentImageView.setVisibility(View.GONE);

        }






    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }


}
