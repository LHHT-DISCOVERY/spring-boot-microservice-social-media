import { Injectable } from '@angular/core';

const TOKEN_KEY = 'access-token'
const USER_KEY = 'auth-user'

@Injectable({
  providedIn: 'root'
})
export class TokenStorageServiceService {

  constructor() { }

  public saveTokenToLocalStorage(token : string){
    window.localStorage.clear();
    window.localStorage.setItem(TOKEN_KEY, token)
  }

  public getTokenFromLocalStorage(): string {
    const token = localStorage.getItem(TOKEN_KEY) ?? localStorage.getItem(TOKEN_KEY)
    return token ?? "TOKEN NOT FOUND"
  }

  public saveUserToLocalStorage(getUsernameAccount: string){
    window.localStorage.removeItem(USER_KEY)
    window.localStorage.setItem(USER_KEY, getUsernameAccount)
  }

  public getUserFromLocalStorage():string {
   const user = localStorage.getItem(USER_KEY) ?? localStorage.getItem(USER_KEY)
   return user ?? JSON.stringify("USERNAME NOT FOUND")
  }
}
