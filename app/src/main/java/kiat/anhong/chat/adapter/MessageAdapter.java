package kiat.anhong.chat.adapter;
/*
 * Copyright (C) 2017 The KiaT Project
 * Created by Kiat on Oct 17
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import kiat.anhong.chat.R;
import kiat.anhong.chat.model.BitmapSave;
import kiat.anhong.chat.model.Message;
import kiat.anhong.chat.model.RoundImageView;
import kiat.anhong.chat.utils.AvatarImageUtils;
import kiat.anhong.chat.utils.Utils;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private static final int RIGHT_IMG_CHAT = 0;
    private static final int LEFT_IMG_CHAT = 1;

    private List<Message> mMsgList;

    public MessageAdapter(List<Message> msgList) {
        this.mMsgList = msgList;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if (viewType == RIGHT_IMG_CHAT) {
            view = inflater.inflate(R.layout.message_me_row_item, parent, false);
        } else {
            view = inflater.inflate(R.layout.message_row_item, parent, false);
        }
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Message msg = mMsgList.get(position);

        holder.tvContent.setText(msg.content);
        holder.tvTimeSend.setText(Utils.getDateDetail(msg.timeSend));
        BitmapSave bmSave = AvatarImageUtils.getInstance().get(msg.userUID);
        if (bmSave == null) {
            //dont have
            bmSave = new BitmapSave(msg.userUID);
            bmSave.addImgView(holder.imgAvatar);
            AvatarImageUtils.loadImg(msg.userUID);
        } else {
            if (bmSave.getBitmap() == null) {
                //bitmap is loading
                bmSave.addImgView(holder.imgAvatar);
            } else {
                holder.imgAvatar.setImageBitmap(bmSave.getBitmap());
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        int itemType = LEFT_IMG_CHAT;
        Message msg = mMsgList.get(position);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        if (user.getUid().equals(msg.userUID)) {
            itemType = RIGHT_IMG_CHAT;
        } else {
            itemType = LEFT_IMG_CHAT;
        }
        return itemType;
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
