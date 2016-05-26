package de.droidcon.model.backend;

import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.openconference.model.Session;
import com.openconference.model.Speaker;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.threeten.bp.Instant;
import org.threeten.bp.format.DateTimeFormatter;

/**
 * @author Hannes Dorfmann
 */
@JsonObject public class DroidconBerlinSession implements Session {

  @JsonField String title;
  @JsonField(name = "nid") String id;
  @JsonField(name = "abstract") String description;
  @JsonField(name = "speaker_uids") List<String> speakerIds;
  @JsonField(name = "room_id") List<String> roomIds;
  @JsonField(name = "start_iso") List<String> startTimes;
  @JsonField(name = "end_iso") List<String> endTimes;

  private Instant start;
  private Instant end;
  private ArrayList<Speaker> speakers;

  @NonNull @NotNull @Override public String id() {
    return id;
  }

  @Nullable @org.jetbrains.annotations.Nullable @Override public String title() {
    return title;
  }

  @Nullable @org.jetbrains.annotations.Nullable @Override public String description() {
    return description;
  }

  @Nullable @org.jetbrains.annotations.Nullable @Override public String tags() {
    return null;
  }

  @Nullable @org.jetbrains.annotations.Nullable @Override public String locationId() {
    if (roomIds != null && !roomIds.isEmpty()) {
      return roomIds.get(0);
    }
    return null;
  }

  @Nullable @org.jetbrains.annotations.Nullable @Override public String locationName() {
    return null;
  }

  @Nullable @org.jetbrains.annotations.Nullable @Override public Instant startTime() {
    if (start == null) {
      if (startTimes == null || startTimes.isEmpty()) {
        return null;
      }
      start = Instant.from(DateTimeFormatter.ISO_DATE_TIME.parse(startTimes.get(0)));
    }
    return start;
  }

  @Nullable @org.jetbrains.annotations.Nullable @Override public Instant endTime() {

    if (end == null) {
      if (endTimes == null || endTimes.isEmpty()) {
        return null;
      }
      end = Instant.from(DateTimeFormatter.ISO_DATE_TIME.parse(endTimes.get(0)));
    }
    return end;
  }

  @Nullable @Override public List<Speaker> speakers() {
    if (speakers == null) {
      if (speakerIds == null || speakerIds.isEmpty()) {
        return null;
      }

      speakers = new ArrayList<>(speakerIds.size());
      for (String speakerId : speakerIds) {
        speakers.add(new DroidconBerlinSpeaker(speakerId));
      }
    }

    return speakers;
  }

  @NonNull @Override public boolean favorite() {
    return false;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.title);
    dest.writeString(this.id);
    dest.writeString(this.description);
    dest.writeStringList(this.speakerIds);
    dest.writeStringList(this.roomIds);
    dest.writeStringList(this.startTimes);
    dest.writeStringList(this.endTimes);
    dest.writeSerializable(this.start);
    dest.writeSerializable(this.end);
    dest.writeTypedList(this.speakers);
  }

  public DroidconBerlinSession() {
  }

  protected DroidconBerlinSession(Parcel in) {
    this.title = in.readString();
    this.id = in.readString();
    this.description = in.readString();
    this.speakerIds = in.createStringArrayList();
    this.roomIds = in.createStringArrayList();
    this.startTimes = in.createStringArrayList();
    this.endTimes = in.createStringArrayList();
    this.start = (Instant) in.readSerializable();
    this.end = (Instant) in.readSerializable();
    List<DroidconBerlinSpeaker> sp = new ArrayList<>();
    in.readTypedList(sp, DroidconBerlinSpeaker.CREATOR);
    this.speakers = new ArrayList<>();
    for (int i = 0; i < sp.size(); i++) {
      this.speakers.add(sp.get(i));
    }
  }

  public static final Creator<DroidconBerlinSession> CREATOR =
      new Creator<DroidconBerlinSession>() {
        @Override public DroidconBerlinSession createFromParcel(Parcel source) {
          return new DroidconBerlinSession(source);
        }

        @Override public DroidconBerlinSession[] newArray(int size) {
          return new DroidconBerlinSession[size];
        }
      };
}
