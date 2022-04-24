import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GenericPageViewComponent } from './components/generic-page-view/generic-page-view.component';

const routes: Routes = [
  { path: '', component: GenericPageViewComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
