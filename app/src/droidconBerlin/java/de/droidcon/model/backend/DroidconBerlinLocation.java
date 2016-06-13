package de.droidcon.model.backend;

import android.os.Parcel;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.openconference.model.Location;
import org.jetbrains.annotations.NotNull;

/**
 * @author Hannes Dorfmann
 */
@JsonObject public class DroidconBerlinLocation implements Location {

  @JsonField(name = "title") String name;
  @JsonField(name = "nid") String id;

  @NotNull @Override public String name() {
    return name;
  }

  @NotNull @Override public String id() {
    return id;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.name);
    dest.writeString(this.id);
  }

  public DroidconBerlinLocation() {
  }

  protected DroidconBerlinLocation(Parcel in) {
    this.name = in.readString();
    this.id = in.readString();
  }

  public static final Creator<DroidconBerlinLocation> CREATOR =
      new Creator<DroidconBerlinLocation>() {
        @Override public DroidconBerlinLocation createFromParcel(Parcel source) {
          return new DroidconBerlinLocation(source);
        }

        @Override public DroidconBerlinLocation[] newArray(int size) {
          return new DroidconBerlinLocation[size];
        }
      };
}
