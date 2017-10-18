package kiat.anhong.chat.adapter;
/*
 * Copyright (C) 2017 The KiaT Project
 * Created by Kiat on Oct 17
 */

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kiat.anhong.chat.R;
import kiat.anhong.chat.model.RoundImageView;
import kiat.anhong.chat.utils.AvatarImageUtils;
import kiat.anhong.chat.utils.Utils;
import kiat.anhong.chat.model.Message;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> mMsgList;
    public MessageAdapter(List<Message> msgList){
        this.mMsgList = msgList;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.message_row_item, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Message msg = mMsgList.get(position);

        holder.tvContent.setText(msg.content);
        holder.tvTimeSend.setText(Utils.getDateDetail(msg.timeSend));
        Bitmap bm = AvatarImageUtils.getInstance().get(msg.userUID);
        if (bm == null){
            AvatarImageUtils.loadImg(msg.userUID, holder.imgAvatar);
        }
        holder.imgAvatar.setImageBitmap(bm);
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        RoundImageView imgAvatar;
        TextView tvContent, tvTimeSend;

        public MessageViewHolder(View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvTimeSend = itemView.findViewById(R.id.tvTimeSend);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
        }
    }
}
