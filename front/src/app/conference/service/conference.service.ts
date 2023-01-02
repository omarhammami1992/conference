import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {Conference} from '../model/conference';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ConferenceService {

  constructor(private httpClient: HttpClient) { }

  createConference(conference: Conference): Observable<number> {
    this.httpClient.post('/api/conference', conference);
    return this.httpClient.post<number>('/api/conference', conference);
  }
}

