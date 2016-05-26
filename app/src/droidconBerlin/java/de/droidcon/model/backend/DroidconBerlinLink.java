package de.droidcon.model.backend;

import android.os.Parcel;
import android.os.Parcelable;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * @author Hannes Dorfmann
 */
@JsonObject
public class DroidconBerlinLink implements Parcelable{
  @JsonField String url;

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.url);
  }

  public DroidconBerlinLink() {
  }

  protected DroidconBerlinLink(Parcel in) {
    this.url = in.readString();
  }

  public static final Creator<DroidconBerlinLink> CREATOR = new Creator<DroidconBerlinLink>() {
    @Override public DroidconBerlinLink createFromParcel(Parcel source) {
      return new DroidconBerlinLink(source);
    }

    @Override public DroidconBerlinLink[] newArray(int size) {
      return new DroidconBerlinLink[size];
    }
  };
}
