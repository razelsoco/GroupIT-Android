
package com.singtel.groupit.model.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Role")
    @Expose
    private String role;
    @SerializedName("Suborganizations")
    @Expose
    private List<Suborganization> suborganizations = new ArrayList<Suborganization>();

    private boolean isSelected;
    /**
     *
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     *     The Id
     */
    public void setId(String id) {
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

    /**
     *
     * @return
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     *     The Email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     *     The role
     */
    public String getRole() {
        return role;
    }

    /**
     *
     * @param role
     *     The Role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     *
     * @return
     *     The suborganizations
     */
    public List<Suborganization> getSuborganizations() {
        return suborganizations;
    }

    /**
     *
     * @param suborganizations
     *     The Suborganizations
     */
    public void setSuborganizations(List<Suborganization> suborganizations) {
        this.suborganizations = suborganizations;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    protected User(Parcel in) {
        id = in.readString();
        name = in.readString();
        email = in.readString();
        role = in.readString();
        if (in.readByte() == 0x01) {
            suborganizations = new ArrayList<Suborganization>();
            in.readList(suborganizations, Suborganization.class.getClassLoader());
        } else {
            suborganizations = null;
        }
        isSelected = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(role);
        if (suborganizations == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(suborganizations);
        }
        dest.writeByte((byte) (isSelected ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
