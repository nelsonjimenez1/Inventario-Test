import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListComponent } from './user/list/list.component';
import { SearchComponent } from './user/search/search.component';
import { ViewComponent } from './user/view/view.component';

const routes: Routes = [
  {
    path: '', component: SearchComponent
  },
  {
    path: 'list', component: ListComponent
  },
  {
    path: 'view/:id', component: ViewComponent
  }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
