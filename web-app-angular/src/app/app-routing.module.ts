import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserViewHomeComponent } from './component/user-view-news/user-view-home/user-view-home.component';
import { UserViewDetailNewsComponent } from './component/user-view-news/user-view-detail-news/user-view-detail-news.component';

const routes: Routes = [
  {
    path: "",
    component: UserViewHomeComponent
  },
  {
    path: "detail",
    component: UserViewDetailNewsComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
