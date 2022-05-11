package com.prem.whatsap_clone.Adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.prem.whatsap_clone.Models.MessageModel;
import com.prem.whatsap_clone.R;

import java.util.ArrayList;

public class ChatAdapter extends  RecyclerView.Adapter{

    ArrayList<MessageModel> messageModels;
    Context context;
    String recId;

    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context, String recId) {
        this.messageModels = messageModels;
        this.context = context;
        this.recId = recId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == SENDER_VIEW_TYPE)
        {
           View view = LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
           return  new SenderViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_reciever,parent,false);
            return new RecieveViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
       if (messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid()))
       {
          return SENDER_VIEW_TYPE;
       }
       else
       {
           return RECEIVER_VIEW_TYPE;
       }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel messageModel = messageModels.get(position);

        if (holder.getClass() == SenderViewHolder.class)
        {
            ((SenderViewHolder)holder).senderMsg.setText(messageModel.getMessage());
        }
        else
        {
            ((RecieveViewHolder)holder).receiverMsg.setText(messageModel.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class RecieveViewHolder extends RecyclerView.ViewHolder{

        TextView receiverMsg,receiverTime;

        public RecieveViewHolder(@NonNull View itemView) {
            super(itemView);

            receiverMsg = itemView.findViewById(R.id.receiverText);
            receiverTime = itemView.findViewById(R.id.receiverTime);

        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder{

        TextView senderMsg,senderTime;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            senderMsg = itemView.findViewById(R.id.senderText);
            senderTime = itemView.findViewById(R.id.receiverTime);

        }
    }
}
