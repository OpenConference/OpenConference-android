package de.droidcon.model.backend;

import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.openconference.model.Speaker;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * @author Hannes Dorfmann
 */
@JsonObject public class DroidconBerlinSpeaker implements Speaker {

  @JsonField(name = "uid") String id;
  @JsonField(name = "gn") String firstName;
  @JsonField(name = "sn") String lastName;
  @JsonField(name = "org") String company;
  @JsonField(name = "position") String jobTitle;
  @JsonField(name = "image") String profilePic;
  @JsonField(name = "description_short") String info;
  @JsonField(name = "links") List<DroidconBerlinLink> links;

  @Nullable @org.jetbrains.annotations.Nullable @Override public String profilePic() {
    return profilePic;
  }

  @Nullable @org.jetbrains.annotations.Nullable @Override public String link3() {
    return links != null && links.size() >= 1 ? links.get(0).url : null;
  }

  @Nullable @org.jetbrains.annotations.Nullable @Override public String link2() {
    return links != null && links.size() >= 2 ? links.get(1).url : null;
  }

  @Nullable @org.jetbrains.annotations.Nullable @Override public String link1() {
    return links != null && links.size() >= 3 ? links.get(2).url : null;
  }

  @Nullable @org.jetbrains.annotations.Nullable @Override public String jobTitle() {
    return jobTitle == null ? null : Html.fromHtml(jobTitle).toString();
  }

  @Nullable @org.jetbrains.annotations.Nullable @Override public String company() {
    return company == null ? null : Html.fromHtml(company).toString();
  }

  @Nullable @org.jetbrains.annotations.Nullable @Override public String info() {
    return info == null ? null : Html.fromHtml(info).toString();
  }

  @NonNull @NotNull @Override public String name() {
    return firstName + " " + lastName;
  }

  @NonNull @NotNull @Override public String id() {
    return id;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.id);
    dest.writeString(this.firstName);
    dest.writeString(this.lastName);
    dest.writeString(this.company);
    dest.writeString(this.jobTitle);
    dest.writeString(this.profilePic);
    dest.writeString(this.info);
    dest.writeTypedList(this.links);
  }

  public DroidconBerlinSpeaker() {
  }

  public DroidconBerlinSpeaker(String id) {
    this.id = id;
  }

  protected DroidconBerlinSpeaker(Parcel in) {
    this.id = in.readString();
    this.firstName = in.readString();
    this.lastName = in.readString();
    this.company = in.readString();
    this.jobTitle = in.readString();
    this.profilePic = in.readString();
    this.info = in.readString();
    this.links = in.createTypedArrayList(DroidconBerlinLink.CREATOR);
  }

  public static final Creator<DroidconBerlinSpeaker> CREATOR =
      new Creator<DroidconBerlinSpeaker>() {
        @Override public DroidconBerlinSpeaker createFromParcel(Parcel source) {
          return new DroidconBerlinSpeaker(source);
        }

        @Override public DroidconBerlinSpeaker[] newArray(int size) {
          return new DroidconBerlinSpeaker[size];
        }
      };
}
