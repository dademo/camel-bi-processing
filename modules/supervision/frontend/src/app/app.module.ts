import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgxHateoasClientConfigurationService, NgxHateoasClientModule } from '@lagoshny/ngx-hateoas-client';
import { getAllAppModules } from './modules';
import { getDeclaredResources } from './modules/backend/dto';
import { getAngularMaterialModules } from './angular-material';
import { FlexLayoutModule } from '@angular/flex-layout';
import { NgxEchartsModule } from 'ngx-echarts';
import { HttpClientModule } from '@angular/common/http';
import { AppConfigService, ApplicationState } from './services';
import { filter, first } from 'rxjs';
import { Location } from '@angular/common';
import { AppGuardComponent } from './components/app-guard/app-guard.component';
import { AppInterfaceComponent } from './components/app-interface/app-interface.component';
import { AppSidenavComponent } from './components/app-sidenav/app-sidenav.component';
import { GenericPageViewComponent } from './components/generic-page-view/generic-page-view.component';
import { GenericPageViewDisplayListComponent } from './components/generic-page-view/generic-page-view-display-list/generic-page-view-display-list.component';
import { GenericPageViewDisplayCardsComponent } from './components/generic-page-view/generic-page-view-display-cards/generic-page-view-display-cards.component';
import { GenericPageViewRouteComponent } from './components/generic-page-view/generic-page-view-route/generic-page-view-route.component';
import { AppThemeComponent } from './components/app-theme/app-theme.component';
import { FormsModule } from '@angular/forms';
import { DataBackendsComponent } from './pages/data-backends/data-backends.component';
import { HomeComponent } from './pages/home/home.component';
import { DashboardsComponent } from './pages/dashboards/dashboards.component';
import { DashboardViewComponent } from './pages/dashboard-view/dashboard-view.component';
import { OverlayContainer } from '@angular/cdk/overlay';
import { AppOverlayContainer } from './tools/app-overlay-container.service';

@NgModule({
  declarations: [
    AppComponent,
    AppGuardComponent,
    AppInterfaceComponent,
    AppSidenavComponent,
    GenericPageViewComponent,
    GenericPageViewDisplayListComponent,
    GenericPageViewDisplayCardsComponent,
    GenericPageViewRouteComponent,
    AppThemeComponent,
    DataBackendsComponent,
    HomeComponent,
    DashboardsComponent,
    DashboardViewComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    NgxEchartsModule.forRoot({
      echarts: () => import('echarts'),
    }),
    HttpClientModule,
    NgxHateoasClientModule.forRoot(),
  ]
  // Angular modules
  .concat(getAngularMaterialModules())
  // Application modules
  .concat(getAllAppModules()),
  providers: [
    { provide: OverlayContainer, useClass: AppOverlayContainer }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
  
  constructor(
    private appConfig: AppConfigService,
    private hateoasConfig: NgxHateoasClientConfigurationService,
    private location: Location,
  ) {

    appConfig.events
      .pipe(
        filter(v => v === ApplicationState.APPLICATION_READY),
        first(),
      )
      .subscribe({ next: this.configureHateoas.bind(this) });
  }

  private configureHateoas(applicationState: ApplicationState): void {

    this.hateoasConfig.configure({
      http: {
        rootUrl: this.appConfig.applicationRuntimeConfiguration?.serviceRootUrl || this.location.prepareExternalUrl('/'),
        proxyUrl: this.appConfig.applicationRuntimeConfiguration?.proxyUrl,
      },
      isProduction: false,
      logs: { verboseLogs: true, },
      cache: {
        enabled: false,
      },
      pagination: {
        defaultPage: {
          size: 50,
          page: 0,
        },
      },
      useTypes: {
        resources: getDeclaredResources(),
        embeddedResources: [],
    }
    });
  }
}
