import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DataBackendsComponent } from './pages/data-backends/data-backends.component';

const routes: Routes = [
  { path: '', component: DataBackendsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
