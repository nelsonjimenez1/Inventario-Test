import { Injectable } from '@angular/core';
import { Product } from '../models/product';
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders
} from "@angular/common/http";

import { Observable, throwError } from "rxjs";
import { catchError } from "rxjs/operators";
import { environment } from "src/environments/environment";

@Injectable({
  providedIn: 'root'
})

export class ProductService {
  public auth:string = '';
  constructor(private http: HttpClient) {
  }

  private handleError(error: HttpErrorResponse): Observable<any> {
    console.log(error);
    return throwError("An error has occurred");
  }

  private get<T>(url:any): Observable<T> {
    console.log("get:", url);
    var auth = localStorage.getItem('authorization');
    var header;
    if (auth != null) {
      header = {
        headers: new HttpHeaders().set('Authorization',  auth)
      }
    }
    return this.http.get<T>(url)
      .pipe(
        // retry(5),
        catchError(this.handleError)
      );
  }

  private post<T>(url:any, data: T): Observable<T> {
    console.log("post:", url);
    var auth = localStorage.getItem('authorization');
    var header;
    if (auth != null) {
      header = {
        headers: new HttpHeaders().set('Authorization',  auth)
      }
    }
    return this.http.post<T>(url, data)
      .pipe(
        // retry(5),
        catchError(this.handleError)
      );
  }

  private put<T>(url:any, data: T): Observable<T> {
    console.log("put:", url);
    var auth = localStorage.getItem('authorization');
    var header;
    if (auth != null) {
      header = {
        headers: new HttpHeaders().set('Authorization',  auth)
      }
    }
    return this.http.put<T>(url, data)
      .pipe(
        // retry(5),
        catchError(this.handleError)
      );
  }

  private delete<T>(url:any): Observable<T> {
    console.log("delete:", url);
    var auth = localStorage.getItem('authorization');
    var header;
    if (auth != null) {
      header = {
        headers: new HttpHeaders().set('Authorization',  auth)
      }
    }
    return this.http.delete<T>(url)
      .pipe(
        // retry(5),
        catchError(this.handleError)
      );
  }

  getListProducts() {
    const url = environment.inventoryService + "product";
    return this.get<Product[]>(url);
  }

  getProductById(id:number) {
    const url = environment.inventoryService + "product/" + id;
    return this.get<Product>(url);
  }

  editProduct(product:Product) {
    const url = environment.inventoryService + "product";
    return this.put<Product>(url, product);
  }

  addProduct(product:Product) {
    const url = environment.inventoryService + "product";
    return this.post<Product>(url, product);
  }

  deleteProduct(id:number) {
    const url = environment.inventoryService + "product/" + id;
    return this.delete<Product>(url);
  }

  findByName(name:string) {
    const url = environment.inventoryService + "product/name/" + name;
    return this.get<Product>(url);
  }
}
