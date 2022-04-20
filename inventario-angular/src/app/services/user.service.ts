import { Injectable } from '@angular/core';
import { UserDB } from '../models/userdb';
import {
  HttpClient,
  HttpErrorResponse,
} from "@angular/common/http";

import { Observable, throwError } from "rxjs";
import { catchError } from "rxjs/operators";
import { environment } from "src/environments/environment";

@Injectable({
  providedIn: 'root'
})

export class UserService {
  
  constructor(private http: HttpClient) {
  }

  private handleError(error: HttpErrorResponse): Observable<any> {
    console.log(error);
    return throwError("An error has occurred");
  }

  private get<T>(url:any): Observable<T> {
    console.log("get:", url);
    return this.http.get<T>(url)
      .pipe(
        // retry(5),
        catchError(this.handleError)
      );
  }

  private post<T>(url:any, data: T): Observable<T> {
    console.log("post:", url);
    return this.http.post<T>(url, data)
      .pipe(
        // retry(5),
        catchError(this.handleError)
      );
  }

  private put<T>(url:any, data: T): Observable<T> {
    console.log("put:", url);
    return this.http.put<T>(url, data)
      .pipe(
        // retry(5),
        catchError(this.handleError)
      );
  }

  private delete<T>(url:any): Observable<T> {
    console.log("delete:", url);
    return this.http.delete<T>(url)
      .pipe(
        // retry(5),
        catchError(this.handleError)
      );
  }

  getListUser() {
    const url = environment.inventoryService + "users";
    return this.get<UserDB[]>(url);
  }

  getUserById(id:number) {
    const url = environment.inventoryService + "users/" + id;
    return this.get<UserDB>(url);
  }

  editUser(user:UserDB) {
    const url = environment.inventoryService + "users";
    return this.put<UserDB>(url, user);
  }

  addUser(user:UserDB) {
    const url = environment.inventoryService + "users";
    return this.post<UserDB>(url, user);
  }

  deleteUser(id:number) {
    const url = environment.inventoryService + "users/" + id;
    return this.delete<UserDB>(url);
  }

  findByName(name:string) {
    const url = environment.inventoryService + "users/name/" + name;
    return this.get<UserDB[]>(url);
  }
}
