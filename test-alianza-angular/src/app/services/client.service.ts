import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { Client } from '../model/client.model';


@Injectable({
  providedIn: 'root'
})


export class ClientService {
  private baseUrl = 'http://localhost:8080/clients';

  // Creamos un nuevo Subject que notificará cuando se cree un nuevo item
  private newClientSubject = new Subject<Client>();

  // Creamos un nuevo Subject que notificará cuando se realice una busqueda
  private searchClientSubject = new Subject<Client[]>();

  private searchValueSubject = new BehaviorSubject<string>('');

  constructor(private http: HttpClient) { }

  getClients(): Observable<Client[]> {
    return this.http.get<Client[]>(this.baseUrl);
  }

  addClient(client: Client): Observable<Client> {
    return this.http.post<Client>(this.baseUrl, client);
  }

  getClientsBySharedId(sharedId: string): Observable<Client[]> {
    if (!sharedId) {
      sharedId='all';
    }
    return this.http.get<Client[]>(this.baseUrl + '/' + sharedId);
  }

  // Creamos una función que notifica que se ha creado un nuevo cliente
  notifyNewClient(client: Client) {
    console.log('Notify Client created');
    this.newClientSubject.next(client);
  }

  // Creamos una función que devuelve el Observable que notifica que se ha creado un nuevo cliente
  getNewClientObservable(): Observable<Client> {
    return this.newClientSubject.asObservable();
  }

  // Nuevo método que actualiza el valor de búsqueda actual
  notifySearchValue(clients: Client[]) {
    console.log('Notify Client search',clients);
    this.searchClientSubject.next(clients);
  }

  // Nuevo método que devuelve un observable que emite el valor de búsqueda actual
  getSearchValueObservable(): Observable<Client[]> {
    return this.searchClientSubject.asObservable();
  }

}


