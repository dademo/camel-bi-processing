import { HateoasResourceOperation, PagedResourceCollection, Resource } from "@lagoshny/ngx-hateoas-client";
import { SortedPageParam } from "@lagoshny/ngx-hateoas-client/lib/model/declarations";
import { map, Observable, tap } from "rxjs";
import { PagedValuesProvider } from "../components/generic-page-view";
import { GenericPageViewDataCollectionRepresentation } from "../components/generic-page-view/data-model";
import { AppConfigService } from "../services";

type ServicePagedValuesProviderHelperMapper<T extends Resource> = (pagedResourceCollection: PagedResourceCollection<T>) => GenericPageViewDataCollectionRepresentation;

export class ServicePagedValuesProviderHelper<T extends Resource> {

  public constructor(
    private readonly appConfig: AppConfigService,
    private readonly service: HateoasResourceOperation<T>,
    private readonly mapper: ServicePagedValuesProviderHelperMapper<T>) { }

  public static of<T extends Resource>(
    appConfig: AppConfigService,
    service: HateoasResourceOperation<T>,
    mapper: ServicePagedValuesProviderHelperMapper<T>): ServicePagedValuesProviderHelper<T> {
    return new ServicePagedValuesProviderHelper(appConfig, service, mapper);
  }

  public getPagedValuesProvider(): PagedValuesProvider {
    return this.getValuesForPage.bind(this);
  }

  // PagedResourceCollection<T>
  private getValuesForPage(sortedPageParam: SortedPageParam): Observable<GenericPageViewDataCollectionRepresentation> {

    this.appConfig.setIsLoading(true);
    this.appConfig.setLoadingStatus('buffer');
    return this.service.getPage(sortedPageParam)
      .pipe(
        map(this.mapper),
        tap({
          next: () => this.appConfig.setIsLoading(false),
          error: () => this.appConfig.setIsLoading(false),
        }),
      );
  }
}
