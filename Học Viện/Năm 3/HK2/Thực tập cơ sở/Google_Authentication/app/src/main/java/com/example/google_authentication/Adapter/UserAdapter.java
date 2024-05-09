package com.example.google_authentication.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.google_authentication.Helper.AppConfig;
import com.example.google_authentication.Model.User;
import com.example.google_authentication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User> listUsers;
    private OnItemClickListener mListener;
    private Context mContext;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }
//    public void deleteUser(int position) {
//        // Remove the user from the list
//        listUsers.remove(position);
//        // Notify RecyclerView about the item removal
//        notifyItemRemoved(position);
//    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public UserAdapter(Context context, List<User> listUsers) {
        this.mContext = context;
        this.listUsers = listUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Nạp layout cho view biểu diễn phần  user
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_custom_row, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = listUsers.get(position);
        holder.listName.setText(user.getUsername());
        holder.listImage.setImageResource(user.getImgId());



        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfig appConfig = AppConfig.getInstance();
                String ip = appConfig.getServerAddress();
                String url="http://"+ip+":9080/user/delete";
                RequestQueue queue = Volley.newRequestQueue(holder.itemView.getContext());

                JSONObject postData = new JSONObject();
                try {
                    postData.put("username",  user.getUsername());

                }catch (JSONException e){
                    e.printStackTrace();
                }
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String res) {
                                if(res.equalsIgnoreCase("success")){
                                    Toast.makeText(holder.itemView.getContext(), "Deleted", Toast.LENGTH_LONG).show();
                                    // Assuming the deletion was successful, update the UI
                                    listUsers.remove(holder.getBindingAdapterPosition());
                                    notifyItemRemoved(holder.getBindingAdapterPosition());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        System.out.println(error.getMessage());
                        Toast.makeText(holder.itemView.getContext(), "Delete Unsuccessfully" , Toast.LENGTH_LONG).show();
                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("username", user.getUsername());
                        initPreferences();
                        String savedToken = sharedPreferences.getString("token", null);
                        headers.put("Authorization", "Bearer " + savedToken);
                        return headers;
                    }
                };

                queue.add(stringRequest);
            }
        });
    }
    @Override
    public int getItemCount() {
        return listUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView listName;
        private ImageView listImage;
        private Button deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listName = itemView.findViewById(R.id.listName);
            listImage = itemView.findViewById(R.id.listImage);
            deleteBtn = itemView.findViewById(R.id.delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getBindingAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
//            deleteBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mListener != null) {
//                        int position = getBindingAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//                            mListener.onItemClick(position);
//                        }
//                    }
//                }
//            });


        }
    }
    private void initPreferences() {
        sharedPreferences = mContext.getSharedPreferences("tokenSet", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
}
