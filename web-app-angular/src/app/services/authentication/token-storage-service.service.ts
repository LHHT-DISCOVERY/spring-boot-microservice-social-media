import { Injectable } from '@angular/core';

const TOKEN_KEY = 'access-token'
const USER_KEY = 'auth-user'

@Injectable({
  providedIn: 'root'
})
export class TokenStorageServiceService {

  constructor() { }

  public saveTokenAndUserNameToLocalStorage(token : string , username : string){
    window.localStorage.clear();
    window.localStorage.setItem(TOKEN_KEY, token)
    window.localStorage.setItem(USER_KEY, username)
  }

  public getTokenFromLocalStorage(): string {
    const token = localStorage.getItem(TOKEN_KEY) ?? localStorage.getItem(TOKEN_KEY)
    return token ?? "TOKEN NOT FOUND"
  }

  public getUserFromLocalStorage():string {
   const username = localStorage.getItem(USER_KEY) ?? localStorage.getItem(USER_KEY)
   console.log("log username in localstorage" , username)
   return username ?? JSON.stringify("USERNAME NOT FOUND")
  }
}
