package com.example.wyyu.gitsamlpe.test.audio.player;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.wyyu.gitsamlpe.R;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wyyu on 2018/9/20.
 **/

public class AudioListAdapter extends RecyclerView.Adapter {

    private List<AudioDataBean> audioDataList;
    private Activity activity;

    AudioListAdapter(Activity activity) {
        this.activity = activity;
        this.audioDataList = new LinkedList<>();
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AudioItemHolder(
            LayoutInflater.from(activity).inflate(R.layout.layout_audio_list_item, parent, false));
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((AudioItemHolder) holder).bindValue(audioDataList.get(position));
    }

    @Override public int getItemCount() {
        return audioDataList == null ? 0 : audioDataList.size();
    }

    private class AudioItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.audio_item_duration) TextView duration;
        @BindView(R.id.audio_item_seek_bar) SeekBar seekBar;
        @BindView(R.id.audio_item_name) TextView name;
        @BindView(R.id.audio_item_button) View click;

        AudioItemHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void bindValue(AudioDataBean audioData) {
            if (audioData == null) {
                return;
            }

            name.setText(audioData.name);
        }
    }

    void setAudioDataList(List<AudioDataBean> audioDataList) {
        if (audioDataList == null || audioDataList.isEmpty()) {
            return;
        }
        if (this.audioDataList == null) {
            this.audioDataList = new LinkedList<>();
        }
        this.audioDataList.clear();
        this.audioDataList.addAll(audioDataList);
        notifyDataSetChanged();
    }
}
