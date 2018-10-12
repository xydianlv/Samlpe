package com.example.wyyu.gitsamlpe.test.audio.player;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
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

        final AudioDataBean audioData = audioDataList.get(position);
        AudioItemHolder audioHolder = (AudioItemHolder) holder;

        audioHolder.bindValue(audioData);

        audioHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                AudioPlayer.getPlayer().play(new AudioPlayInfo(audioData.id, audioData.path));
            }
        });
    }

    @Override public int getItemCount() {
        return audioDataList == null ? 0 : audioDataList.size();
    }

    private class AudioItemHolder extends RecyclerView.ViewHolder {

        private TextView duration;
        private SeekBar seekBar;
        private TextView name;
        private View click;

        AudioItemHolder(View itemView) {
            super(itemView);

            duration = itemView.findViewById(R.id.audio_item_duration);
            seekBar = itemView.findViewById(R.id.audio_item_seek_bar);
            name = itemView.findViewById(R.id.audio_item_name);
            click = itemView.findViewById(R.id.audio_item_button);
        }

        void bindValue(AudioDataBean audioData) {
            if (audioData == null) {
                return;
            }

            duration.setText(String.valueOf(audioData.duration));
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
