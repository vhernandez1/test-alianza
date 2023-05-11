import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { FromClientComponent } from '../from-client/from-client.component';
import { ClientService } from 'src/app/services/client.service';
import { Subscription } from 'rxjs';
import { Client } from 'src/app/model/client.model';

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.css']
})


export class ClientsComponent implements OnInit {

  sharedId: string = '';
  private clientAddedSubscription: Subscription;
  private dialogRef: MatDialogRef<FromClientComponent>;
  constructor(private dialog: MatDialog,
    private clientService: ClientService){}


  ngOnInit(): void {
    this.clientAddedSubscription = this.clientService
      .getNewClientObservable().subscribe((client: Client) => {
        this.dialogRef.close();
      });
  }


  openCreateDialog() {
    this.dialogRef = this.dialog.open(FromClientComponent, {
      width: '300px',
      height: '500px'
    });

    this.dialogRef.afterClosed().subscribe(result => {
      if (result === 'created') {
        console.log('Item creado con éxito');
      }
    });
  };
  searchClient(): void {
    console.log('sharedId='+ this.sharedId);
    this.clientService.getClientsBySharedId(this.sharedId).subscribe(clients => {
      console.log('Busqueda realizada con exito');

      // Notifica que se ha realizado un busqueda
      this.clientService.notifySearchValue(clients);
    });
  }


}
