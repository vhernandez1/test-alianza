import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Client } from 'src/app/model/client.model';
import { ClientService } from 'src/app/services/client.service';


@Component({
  selector: 'app-clients-list',
  templateUrl: './clients-list.component.html',
  styleUrls: ['./clients-list.component.css']
})

export class ClientsListComponent implements OnInit {
  displayedColumns: string[] = ['sharedKey', 'businessId', 'email', 'phone','dateAdded'];
  dataSource: Client[];

  private clientAddedSubscription: Subscription;

  private clientSearchClientSubscription: Subscription;



  constructor(private clientService: ClientService) { }

  ngOnInit(): void {
    this.loadClients();

    this.clientAddedSubscription = this.clientService
      .getNewClientObservable().subscribe((client: Client) => {
        this.loadClients();
    });

    this.clientSearchClientSubscription = this.clientService
      .getSearchValueObservable().subscribe((clients) => {
        this.dataSource = clients;
      });


  }

  loadClients(){
    this.clientService.getClients().subscribe(clients => {
      this.dataSource = clients;
    });
  }

}
