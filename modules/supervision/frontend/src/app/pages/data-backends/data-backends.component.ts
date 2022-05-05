import { Component, OnInit } from '@angular/core';
import { PagedResourceCollection } from '@lagoshny/ngx-hateoas-client';
import { PagedValuesProvider, ViewRoute } from 'src/app/components/generic-page-view';
import { GenericPageViewDataCollectionRepresentation, GenericPageViewDataRepresentation } from 'src/app/components/generic-page-view/data-model';
import { DataBackendResource } from 'src/app/modules/backend/dto';
import { DataBackendService } from 'src/app/modules/backend/services/data-backend.service';
import { AppConfigService } from 'src/app/services';
import { ServicePagedValuesProviderHelper } from 'src/app/tools/service-paged-values-provider-helper';

@Component({
  selector: 'app-data-backends',
  templateUrl: './data-backends.component.html',
  styleUrls: ['./data-backends.component.scss']
})
export class DataBackendsComponent implements OnInit {

  public readonly viewRoutes: readonly ViewRoute[] = [
    { applicationRoute: '/data-backends', displayName: 'Data backends' },
  ];
  public readonly pagedValuesProvider: PagedValuesProvider;

  constructor(
    private readonly appConfig: AppConfigService,
    private readonly dataBackendService: DataBackendService) {
    
    this.appConfig.setPageTitle('Data backends');
    this.pagedValuesProvider = ServicePagedValuesProviderHelper
      .of<DataBackendResource>(
        appConfig,
        dataBackendService,
        (pagedResourceCollection: PagedResourceCollection<DataBackendResource>) => <GenericPageViewDataCollectionRepresentation> {
          resources: pagedResourceCollection.resources.map(
            resource => <GenericPageViewDataRepresentation>{
              name: resource.backendName,
              description: `Data backend with URL [${resource.primaryUrl}]`,
              statisticsLink: `/data-backend/${resource.id}/statistics`,
              attributes: {
                primaryUrl: resource.primaryUrl,
                backendState: resource.backendState,
                backendProductName: resource.backendProductName,
                backendProductVersion: resource.backendProductVersion,
                databasesCount: resource.databasesCount,
              },
              mainLink: {
                displayName: 'View database informations',
                link: `/database/${resource.id}`,
                icon: 'storage',
              },
              fullPageDisplayLink: {
                displayName: 'Data backend view',
                link: `/data-backend/${resource.id}`,
                icon: 'open_in_full',
              },
              otherLinks: [
                { displayName: 'Replication informations', link: `/database/${resource.id}`, icon: 'copy_all' },
                { displayName: 'Nodes informations', link: `/database/${resource.id}`, icon: 'share' },
              ],
            },
          ),
          totalElements: pagedResourceCollection.totalElements,
          totalPages: pagedResourceCollection.totalPages,
          sorteableAttributes: {},
        }
      )
      .getPagedValuesProvider();
  }

  ngOnInit(): void {
  }

}
