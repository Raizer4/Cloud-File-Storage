// import moment from "moment";

import { environment } from '../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

type User = {
  username: string;
  password: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  login(email: string, password: string) {
    return this.http.post<User>(`http://${environment.apiHost}:3000/login`, {username: email, password})
  }

  register(email: string, password: string) {
    return this.http.post<User>(`http://${environment.apiHost}:3000/register`, {username: email, password})
  }

  // private setSession(authResult: {idToken: string, expiresIn: string}) {
  //     const expiresAt = moment().add(authResult.expiresIn,'second');

  //     localStorage.setItem('id_token', authResult.idToken);
  //     localStorage.setItem("expires_at", JSON.stringify(expiresAt.valueOf()) );
  // }

  // logout() {
  //     localStorage.removeItem("id_token");
  //     localStorage.removeItem("expires_at");
  // }

  // public isLoggedIn() {
  //     return moment().isBefore(this.getExpiration());
  // }

  // isLoggedOut() {
  //     return !this.isLoggedIn();
  // }

  // getExpiration() {
  //     const expiration = localStorage.getItem("expires_at");
  //     if (expiration) {
  //       const expiresAt = JSON.parse(expiration);
  //       return moment(expiresAt);
  //     }

  //     return null;
  // } 
}
