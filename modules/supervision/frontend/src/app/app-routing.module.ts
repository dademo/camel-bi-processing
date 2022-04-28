import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardViewComponent } from './pages/dashboard-view/dashboard-view.component';
import { DashboardsComponent } from './pages/dashboards/dashboards.component';
import { DataBackendsComponent } from './pages/data-backends/data-backends.component';
import { HomeComponent } from './pages/home/home.component';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'data-backends', component: DataBackendsComponent },
  { path: 'dashboards', component: DashboardsComponent },
  { path: 'dashboard/:id', component: DashboardViewComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
