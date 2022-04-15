import { Injectable } from '@angular/core';
import { HateoasResourceOperation, HttpMethod, PagedGetOption } from '@lagoshny/ngx-hateoas-client';
import { Observable } from 'rxjs';
import { DataBackendDatabaseResource, DataBackendResource } from '../dto';

@Injectable({
  providedIn: 'root'
})
export class DataBackendService extends HateoasResourceOperation<DataBackendResource> {

  constructor() {
    super(DataBackendResource);
  }

  public listAllDatabases(dataBackend: DataBackendResource, options?: PagedGetOption | undefined): Observable<DataBackendDatabaseResource> {
    return this.customQuery<DataBackendDatabaseResource>(HttpMethod.GET, `/${dataBackend.id}/databases`, undefined, options);
  }
}
