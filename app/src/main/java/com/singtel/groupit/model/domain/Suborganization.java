
package com.singtel.groupit.model.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Suborganization implements Parcelable {

    @SerializedName("Id")
    @Expose
    private int id;
    @SerializedName("Name")
    @Expose
    private String name;

    /**
     *
     * @return
     *     The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     *     The Id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     *     The Name
     */
    public void setName(String name) {
        this.name = name;
    }


    protected Suborganization(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Suborganization> CREATOR = new Parcelable.Creator<Suborganization>() {
        @Override
        public Suborganization createFromParcel(Parcel in) {
            return new Suborganization(in);
        }

        @Override
        public Suborganization[] newArray(int size) {
            return new Suborganization[size];
        }
    };
}