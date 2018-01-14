package com.dmc.pocketbook.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chi on 13/1/18.
 */

public class Transaction implements Comparable<Transaction>, Parcelable {
    public Integer id;
    public String date;     // must be in format dd-MM-yyyy HH:mm:ss, for now
    public String name = "";
    public Double amount = 0.0;
    public String main_category = "";
    public String sub_category = "";
    public String[] photo;     // for now
    public String payment_type = "";        // for simplicity

    protected Transaction(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        date = in.readString();
        name = in.readString();
        if (in.readByte() == 0) {
            amount = null;
        } else {
            amount = in.readDouble();
        }
        main_category = in.readString();
        sub_category = in.readString();
        photo = in.createStringArray();
        payment_type = in.readString();
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    public String getDay() {
        Date d = getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        return sdf.format(d);
    }
    public String getMonth() {
        Date d = getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM");
        return sdf.format(d);
    }
    public String getAmountAsMoney() {
        return "$" + String.format("%.2f", amount);
    }

    public String getDisplayDate() {
        Date d = getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd MMM, yyyy");
        return sdf.format(d);
    }

    public Date getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            Date d = sdf.parse(date);
            return d;
        } catch (ParseException ex) {
            //Logger.getLogger(Prime.class.getName()).log(Level.SEVERE, null, ex);
            return new Date();      // TODO: Fix this
        }

    }

    public boolean hasCategory() {
        return !main_category.isEmpty();
    }

    @Override
    public int compareTo(@NonNull Transaction t) {
        Date thisDate = getDate();
        Date thatDate = t.getDate();

        return thatDate.compareTo(thisDate);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(date);
        parcel.writeString(name);
        if (amount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(amount);
        }
        parcel.writeString(main_category);
        parcel.writeString(sub_category);
        parcel.writeStringArray(photo);
        parcel.writeString(payment_type);
    }
}
