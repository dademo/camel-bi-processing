import { Component, OnInit } from '@angular/core';
import { DataBackendService } from 'src/app/modules/backend/services/data-backend.service';

@Component({
  selector: 'app-data-backends',
  templateUrl: './data-backends.component.html',
  styleUrls: ['./data-backends.component.scss']
})
export class DataBackendsComponent implements OnInit {

  constructor(private dataBackend: DataBackendService) { }

  ngOnInit(): void {
    this.dataBackend.getPage().subscribe(console.log);
  }

}
