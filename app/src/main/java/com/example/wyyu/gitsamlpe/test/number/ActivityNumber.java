package com.example.wyyu.gitsamlpe.test.number;

import android.os.Bundle;
import android.widget.TextView;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;

/**
 * Created by wyyu on 2018/8/3.
 **/

public class ActivityNumber extends FullScreenActivity {

    @BindView(R.id.game_num_score_a) TextView scoreA;
    @BindView(R.id.game_num_score_b) TextView scoreB;
    @BindView(R.id.game_num_target) TextView target;
    @BindView(R.id.game_num_time) TextView time;

    @BindView(R.id.game_num_fun_add) TextView add;
    @BindView(R.id.game_num_fun_sub) TextView subjet;
    @BindView(R.id.game_num_fun_mul) TextView multiply;
    @BindView(R.id.game_num_fun_delete) TextView delete;
    @BindView(R.id.game_num_fun_left) TextView funLeft;
    @BindView(R.id.game_num_fun_right) TextView funRight;

    @BindView(R.id.game_num_data_a) TextView dataA;
    @BindView(R.id.game_num_data_b) TextView dataB;
    @BindView(R.id.game_num_data_c) TextView dataC;
    @BindView(R.id.game_num_data_d) TextView dataD;
    @BindView(R.id.game_num_data_delete) TextView dataDelete;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
    }
}