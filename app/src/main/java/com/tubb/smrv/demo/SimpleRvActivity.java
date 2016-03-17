
package com.tubb.smrv.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ScrollerCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tubb.smrv.SwipeMenuLayout;
import com.tubb.smrv.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SimpleRvActivity extends Activity {

    private Context mContext;
    private List<User> users;
    private AppAdapter mAdapter;
    private SwipeMenuRecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    View view;
    SwipeMenuLayout sm;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        sm = new SwipeMenuLayout(getApplicationContext());
        mContext = this;
        users = getUsers();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(mContext, "Refresh success", Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        mRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.listView);


        View swipeMeny = findViewById(R.id.smContentView);
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(3));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setOpenInterpolator(new BounceInterpolator());
        mRecyclerView.setCloseInterpolator(new BounceInterpolator());
        mAdapter = new AppAdapter(this, users);
        mRecyclerView.setAdapter(mAdapter);



    }

    private List<User> getUsers() {
        List<User> userList = new ArrayList<>();
        for (int i=0; i<100; i++){
            User user = new User();
            user.userId = i+1000;
            user.userName = "name"+(i+1);
            userList.add(user);
        }
        return userList;
    }

    public void Click(View view) {

        sm.swipe(100);

    }


    class AppAdapter extends RecyclerView.Adapter {

        private static final int VIEW_TYPE_ENABLE = 0;
        private static final int VIEW_TYPE_DISABLE = 1;

        List<User> users;

        public AppAdapter(Context context, List<User> users){
            this.users = users;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        @Override
        public int getItemViewType(int position) {
            User user = users.get(position);
            if(user.userId % 2 == 0){
                return VIEW_TYPE_DISABLE;
            }else{
                return VIEW_TYPE_ENABLE;
            }
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_simple, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder vh, final int position) {
            final User user = users.get(position);
            final MyViewHolder myViewHolder = (MyViewHolder)vh;
            myViewHolder.tv_name.setText(user.userName);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;
        View bt_button;
        View bt_edit;
        View bt_assign_goal;
        View bt_see_goals;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            bt_button = itemView.findViewById(R.id.bt_button);
            bt_edit = itemView.findViewById(R.id.bt_edit);
            bt_see_goals = itemView.findViewById(R.id.bt_see_goals);
            bt_assign_goal = itemView.findViewById(R.id.bt_assign_goal);

        }
    }


    class User{
        public int userId;
        public String userName;
    }
}
