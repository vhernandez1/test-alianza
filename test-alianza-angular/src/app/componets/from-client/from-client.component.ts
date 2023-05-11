import { Component, OnInit } from '@angular/core';
import { Client } from 'src/app/model/client.model';
import { ClientService } from 'src/app/services/client.service';

@Component({
  selector: 'app-from-client',
  templateUrl: './from-client.component.html',
  styleUrls: ['./from-client.component.css']
})

export class FromClientComponent implements OnInit {
  client: Client = {
    sharedKey: '',
    businessId: '',
    email: '',
    phone: '',
    dateAdded: ''
  };

  constructor(private clientService:ClientService) { }

  ngOnInit(): void {
  }

  onSubmit() {
    this.clientService.addClient(this.client).subscribe(client => {
      console.log('Client created successfully',client);

      // Notifica que se ha creado un nuevo cliente
      this.clientService.notifyNewClient(client);
    });
  }
}
