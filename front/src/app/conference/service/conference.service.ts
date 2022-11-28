import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Conference} from "../model/conference";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ConferenceService {


  constructor(private httpClient: HttpClient) {
  }

  createConference = (conference: Conference): Observable<number> => this.httpClient.post<number>("conference", conference, {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  });
}
