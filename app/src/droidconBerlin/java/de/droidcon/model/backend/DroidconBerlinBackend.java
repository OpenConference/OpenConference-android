package de.droidcon.model.backend;

import java.util.List;
import retrofit2.http.GET;
import rx.Observable;

/**
 * @author Hannes Dorfmann
 */
public interface DroidconBerlinBackend {

  @GET("sessions.json") Observable<List<DroidconBerlinSession>> getSessions();

  @GET("speakers.json") Observable<List<DroidconBerlinSpeaker>> getSpeakers();

  @GET("rooms.json") Observable<List<DroidconBerlinLocation>> getLocations();
}
