package com.example.android_1st_assignment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserDataAdapter extends RecyclerView.Adapter<UserDataAdapter.ViewHolder> {

    private List<UserDataModel> userDataModelList;

    public UserDataAdapter(List<UserDataModel> userDataModels) {
        this.userDataModelList=userDataModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.address_book_item,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String id=userDataModelList.get(position).getId();
        String firstName=userDataModelList.get(position).getFirstName();
        String lastName=userDataModelList.get(position).getLastName();
        String phoneNr=userDataModelList.get(position).getPhoneNr();
        String dateOfBirth=userDataModelList.get(position).getDateOfBirth();
        String email=userDataModelList.get(position).getEmail();
        String address=userDataModelList.get(position).getAddress();
        String latitude=userDataModelList.get(position).getLatitude();
        String longitude=userDataModelList.get(position).getLongitude();

        holder.setData(id,firstName,lastName,phoneNr,dateOfBirth,email,address,latitude,longitude);
    }

    @Override
    public int getItemCount() {
        return userDataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView id;
        private TextView firstName;
        private TextView lastName;
        private TextView phoneNr;
        private TextView dateOfBirth;
        private TextView email;
        private TextView address;
        private TextView latitude;
        private TextView longitude;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.userID);
            firstName=itemView.findViewById(R.id.user_name);
            lastName=itemView.findViewById(R.id.user_lastName);
            phoneNr=itemView.findViewById(R.id.user_phone);
            dateOfBirth=itemView.findViewById(R.id.user_dob);
            email=itemView.findViewById(R.id.user_email);
            address=itemView.findViewById(R.id.user_address);
            latitude=itemView.findViewById(R.id.latitudeM);
            longitude=itemView.findViewById(R.id.longitudeM);


        }

        private void setData(final String userId, String userFirstName, String userLastName, String userPhone, String userDateOfBirth, String userEmail, String userAddress,String userLatitude,String userLongitude){
            id.setText(userId);
            firstName.setText(userFirstName);
            lastName.setText(userLastName);
            phoneNr.setText(userPhone);
            dateOfBirth.setText(userDateOfBirth);
            email.setText(userEmail);
            address.setText(userAddress);
            latitude.setText(userLatitude);
            longitude.setText(userLongitude);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailsIntent=new Intent(itemView.getContext(),UpdateData.class);
                    //  categoryIntent.putExtra("CategoryName","name");
                    productDetailsIntent.putExtra("PRODUCT_ID",userId);
                    itemView.getContext().startActivity(productDetailsIntent);
                }
            });
        }
    }
}
