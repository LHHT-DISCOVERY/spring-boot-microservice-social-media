import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserViewDetailNewsComponent } from './user-view-detail-news/user-view-detail-news.component';
import { NavModule } from '../nav/nav.module';
import { UserViewHomeComponent } from './user-view-home/user-view-home.component';
import { SecurityModule } from '../security/security.module';

import { AppRoutingModule } from 'src/app/app-routing.module';
import { AuthModalComponent } from '../security/auth-modal/auth-modal.component';



@NgModule({
  declarations: [
    UserViewDetailNewsComponent,
    UserViewHomeComponent
  ],
  imports: [
    CommonModule,
    SecurityModule,
    AppRoutingModule,
    NavModule
  ],
  exports:[AuthModalComponent]
})
export class UserViewNewsModule { }
