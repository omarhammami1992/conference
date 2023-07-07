import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Conference} from '../model/conference';
import {HttpClient} from '@angular/common/http';
import {environment} from "../../../environments/environment";
import {LightConference} from "../model/light-conference";

@Injectable({
  providedIn: 'root'
})
export class ConferenceService {

  constructor(private httpClient: HttpClient) {
  }

  createConference(conference: Conference): Observable<number> {
    return this.httpClient.post<number>(environment.apiUrl + '/api/conference', conference);
  }

  getConferenceDetailById(idConference: number): Observable<Conference> {
    return this.httpClient.get<Conference>(environment.apiUrl + `/api/conference/${idConference}`);
  }

  getLightConferences(): Observable<LightConference[]> {
    return this.httpClient.get<LightConference[]>(environment.apiUrl + '/api/conference');
  }

}

