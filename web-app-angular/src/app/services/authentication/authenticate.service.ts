import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';

const IDENTITY = "http://localhost:8888/api/identity/"

@Injectable({
  providedIn: 'root'
})
export class AuthenticateService {
  httpOption: any


  constructor(private http: HttpClient) { 
    this.httpOption = {
      headers: new HttpHeaders({
         'Content-type' : 'application/json'
      }),
      'Access-Control-Allow-Origin' : 'http://localhost:4200',
      'Access-Control-Allow-Methods': 'GET,PUT,POST,DELETE,PATCH,OPTIONS'
     
    }
  }

  login(credentials : any): Observable<any> {
    console.log(credentials)
    return this.http.post(IDENTITY+'auth/token', credentials, this.httpOption);
  }
}
