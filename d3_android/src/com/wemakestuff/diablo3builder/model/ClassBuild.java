
package com.wemakestuff.diablo3builder.model;

import java.util.UUID;

import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;

public class ClassBuild implements Parcelable
{

    private String className;
    private String name;
    private String url;
    private UUID   uuid = UUID.randomUUID();
    private String followersUrl;
    
    public ClassBuild(String name, String className, String url)
    {

        super();
        this.name = name;
        this.className = className;
        this.url = url;
    }
    
    public ClassBuild(String name, String className, String url, String followerUrl)
    {
        
        super();
        this.name = name;
        this.className = className;
        this.url = url;
        this.followersUrl = followerUrl;
    }

    public ClassBuild(Parcel source)
    {
        super();
        this.name = source.readString();
        this.className = source.readString();
        this.url = source.readString();
        this.uuid = ((ParcelUuid) source.readParcelable(ParcelUuid.class.getClassLoader())).getUuid();
        this.followersUrl = source.readString();
    }

    public final Parcelable.Creator<ClassBuild> CREATOR = new Parcelable.Creator<ClassBuild>() {
        public ClassBuild createFromParcel(Parcel in) {
            return new ClassBuild(in);
        }

        public ClassBuild[] newArray(int size) {
            return new ClassBuild[size];
        }
    };

    public String getClassName()
    {

        return className;
    }

    public String getName()
    {

        return name;
    }

    public String getUrl()
    {

        return url;
    }
    
    public String getFollowersUrl()
    {
        return followersUrl;
    }

    public UUID getUuid()
    {

        return uuid;
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(className);
        dest.writeString(url);
        dest.writeParcelable(new ParcelUuid(uuid), 0);
        dest.writeString(followersUrl);
    }


}
